package com.huitdduru.madduru.websocket.security;

import com.corundumstudio.socketio.SocketIOClient;
import com.huitdduru.madduru.matching.queue.WaitingQueue;
import com.huitdduru.madduru.matching.service.AcceptProperty;
import com.huitdduru.madduru.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WebSocketConnectController {

    private final TokenProvider tokenProvider;
    private final WaitingQueue waitingQueue;

    public void onConnect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("Authorization");
        Authentication authentication = tokenProvider.getAuthentication(token);
        System.out.println(authentication.getName() + " 님이 연결되었습니다.");
        client.set(ClientProperty.USER_KEY, authentication.getName());
        client.set(ClientProperty.ACCEPT_KEY, AcceptProperty.NULL);
        client.set(ClientProperty.ROOM_ID_KEY, AcceptProperty.NULL);
    }

    public void onDisconnect(SocketIOClient client) {
        System.out.println(client.get(ClientProperty.USER_KEY) + " 님의 연결이 끊어졌습니다.");
        waitingQueue.removeUser(client.getSessionId());
    }

}
