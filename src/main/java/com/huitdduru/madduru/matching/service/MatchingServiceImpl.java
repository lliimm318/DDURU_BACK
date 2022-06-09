package com.huitdduru.madduru.matching.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.huitdduru.madduru.diary.entity.Diary;
import com.huitdduru.madduru.diary.repository.DiaryRepository;
import com.huitdduru.madduru.exception.exceptions.AlreadyRelationContinuesException;
import com.huitdduru.madduru.exception.exceptions.UserNotFoundException;
import com.huitdduru.madduru.matching.entity.Matching;
import com.huitdduru.madduru.matching.entity.MatchingId;
import com.huitdduru.madduru.matching.entity.MatchingRepository;
import com.huitdduru.madduru.matching.message.SimpleMessage;
import com.huitdduru.madduru.matching.message.UserinfoMessage;
import com.huitdduru.madduru.matching.payload.AcceptRequest;
import com.huitdduru.madduru.matching.payload.FriendCodeRequest;
import com.huitdduru.madduru.matching.queue.UniqueUser;
import com.huitdduru.madduru.matching.queue.WaitingQueue;
import com.huitdduru.madduru.matching.service.dto.MatePair;
import com.huitdduru.madduru.user.entity.User;
import com.huitdduru.madduru.user.repository.UserRepository;
import com.huitdduru.madduru.websocket.SocketProperty;
import com.huitdduru.madduru.websocket.security.ClientProperty;
import com.huitdduru.madduru.websocket.security.SocketAuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MatchingServiceImpl implements MatchingService {

    private final SocketAuthenticationFacade authenticationFacade;
    private final WaitingQueue waitingQueue;
    private final MatchingRepository matchingRepository;
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    @Override
    public void start(SocketIOClient client1, SocketIOServer server) {
        User currentUser = authenticationFacade.getCurrentUser(client1);

        UniqueUser mate = waitingQueue.matching(currentUser.getId());

        if (checkRelationContinues(client1, currentUser)) return;

        if (mate != null) {
            SocketIOClient client2 = server.getClient(mate.getUuid());

            MatePair matePair = buildMatePair(client1, client2);

            String roomId = matePair.getRoomId();

            client1.joinRoom(roomId);
            client2.joinRoom(roomId);

            client1.set(ClientProperty.ROOM_ID_KEY, roomId);
            client2.set(ClientProperty.ROOM_ID_KEY, roomId);

            matchingRepository.save(Matching.builder()
                    .matchingId(MatchingId.builder()
                            .user1(matePair.getUser1().getId())
                            .user2(matePair.getUser2().getId())
                            .build())
                    .user1(matePair.getUser1())
                    .user2(matePair.getUser2())
                    .isAccepted(false)
                    .roomId(matePair.getRoomId())
                    .build());

            sendInfoToClient(matePair.getUser1(), matePair.getClient2());
            sendInfoToClient(matePair.getUser2(), matePair.getClient1());
        } else {
            waitingQueue.addUser(new UniqueUser(currentUser.getStringId(), client1.getSessionId()));
        }
    }

    private boolean checkRelationContinues(SocketIOClient client, User user) {
        Optional<Diary> diary = diaryRepository.findByUser1OrUser2AndRelationContinuesIsTrue(user);

        if (diary.isPresent() && diary.get().isRelationContinues()) {
            client.sendEvent(SocketProperty.ERROR_KEY, new SimpleMessage("이미 진행중인 일기가 존재합니다."));
            return true;
        }

        return false;
    }

    @Override
    public void cancel(SocketIOClient client, SocketIOServer server) {
        waitingQueue.removeUser(client.getSessionId());
        String roomId = client.get(ClientProperty.ROOM_ID_KEY);
        if (!roomId.equals(AcceptProperty.NULL)) {
            server.getRoomOperations(roomId)
                    .getClients().forEach(c -> {
                c.leaveRoom(client.get(ClientProperty.ROOM_ID_KEY));
                c.sendEvent(SocketProperty.CANCEL_KEY, new SimpleMessage("매칭이 취소되었습니다."));
                c.set(ClientProperty.ROOM_ID_KEY, AcceptProperty.NULL);
                c.set(ClientProperty.ACCEPT_KEY, AcceptProperty.NULL);
            });
        } else {
            client.sendEvent(SocketProperty.CANCEL_KEY, new SimpleMessage("매칭을 취소했습니다."));
            client.set(ClientProperty.ROOM_ID_KEY, AcceptProperty.NULL);
            client.set(ClientProperty.ACCEPT_KEY, AcceptProperty.NULL);
        }
    }

    private void sendInfoToClient(User user, SocketIOClient client) {
        UserinfoMessage userInfo = UserinfoMessage.builder()
                .img(user.getImagePath() == null ? "" : user.getImagePath())
                .intro(user.getIntro() == null ? "" : user.getIntro())
                .name(user.getName())
                .build();

        client.set(ClientProperty.ACCEPT_KEY, AcceptProperty.NULL);
        client.sendEvent(SocketProperty.USERINFO_KEY, userInfo);
    }

    @Override
    public void accept(SocketIOClient client, SocketIOServer server, AcceptRequest request) {
        client.set(ClientProperty.ACCEPT_KEY, request.getAccept().toString());

        String room_id = client.get(ClientProperty.ROOM_ID_KEY);

        if (room_id.equals(AcceptProperty.NULL)) {
            client.sendEvent(SocketProperty.ERROR_KEY, new SimpleMessage("당신은 아직 매칭되지 않았습니다."));
            return;
        }

        List<SocketIOClient> clients = new ArrayList<>(
                server.getRoomOperations(room_id).getClients()
        );

        if (clients.size() < 2) {
            client.sendEvent(SocketProperty.ERROR_KEY, new SimpleMessage("매칭 상대가 떠난거 같습니다."));
            cancel(client, server);
            return;
        }

        MatePair matePair = buildMatePair(clients.get(0), clients.get(1));

        String user1Accepted = matePair.getClient1().get(ClientProperty.ACCEPT_KEY);
        String user2Accepted = matePair.getClient2().get(ClientProperty.ACCEPT_KEY);

        if (user1Accepted.equals(AcceptProperty.FALSE) || user2Accepted.equals(AcceptProperty.FALSE)) {
            clients
                    .forEach(c -> {
                        c.sendEvent(SocketProperty.CANCEL_KEY, new SimpleMessage("매칭이 취소되었습니다."));
                        client.set(ClientProperty.ROOM_ID_KEY, AcceptProperty.NULL);
                        client.set(ClientProperty.ACCEPT_KEY, AcceptProperty.NULL);
                        client.leaveRoom(matePair.getRoomId());
                    });
        }
        else if (user1Accepted.equals(AcceptProperty.TRUE) && user2Accepted.equals(AcceptProperty.TRUE)) {
            Optional<Diary> diary1 = diaryRepository.findByUser1OrUser2AndRelationContinuesIsTrue(matePair.getUser1());
            Optional<Diary> diary2 = diaryRepository.findByUser1OrUser2AndRelationContinuesIsTrue(matePair.getUser2());

            if ((diary1.isPresent() && diary1.get().isRelationContinues())
                    || (diary2.isPresent() && diary2.get().isRelationContinues()))
                throw new AlreadyRelationContinuesException();

            diaryRepository.save(Diary.builder()
                    .user1(matePair.getUser1())
                    .user2(matePair.getUser2())
                    .createdAt(LocalDateTime.now())
                    .relationContinues(true)
                    .build());

            clients.forEach(c -> c.sendEvent(SocketProperty.SUCCESS_KEY, new SimpleMessage("매칭을 성공적으로 마쳤습니다.")));
        }
    }

    @Override
    public void friendMatching(SocketIOClient client, FriendCodeRequest request) {
        String code = request.getCode();

        User user = authenticationFacade.getCurrentUser(client);

        if (checkRelationContinues(client, user)) return;

        User mate = userRepository.findByCode(code).orElseThrow(UserNotFoundException::new);

        boolean cond = user.getId() < mate.getId();

        diaryRepository.save(Diary.builder()
                .user1(cond ? user : mate)
                .user2(cond ? mate : user)
                .createdAt(LocalDateTime.now())
                .relationContinues(true)
                .build());

        client.sendEvent(SocketProperty.SUCCESS_KEY, new SimpleMessage("매칭에 성공했습니다."));
    }

    private MatePair buildMatePair(SocketIOClient client1, SocketIOClient client2) {
        User user1 = authenticationFacade.getCurrentUser(client1);
        User user2 = authenticationFacade.getCurrentUser(client2);

        boolean cond = user1.getId() < user2.getId();

        return MatePair.builder()
                .roomId(cond ? user1.getStringId() : user2.getStringId())
                .user1(cond ? user1 : user2)
                .user2(cond ? user2 : user1)
                .client1(cond ? client1 : client2)
                .client2(cond ? client2 : client1)
                .build();
    }
}
