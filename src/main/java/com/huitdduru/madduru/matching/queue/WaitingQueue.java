package com.huitdduru.madduru.matching.queue;

import com.huitdduru.madduru.matching.entity.MatchingId;
import com.huitdduru.madduru.matching.entity.MatchingRepository;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import static java.lang.Integer.parseInt;

@Component
public class WaitingQueue {

    private final Queue<UniqueUser> waitingQueue = new LinkedList<>();
    private final MatchingRepository matchingRepository;

    public WaitingQueue(MatchingRepository matchingRepository) {
        this.matchingRepository = matchingRepository;
    }

    public void addUser(UniqueUser user) {
        if (!waitingQueue.contains(user))
            waitingQueue.add(user);
        else
            System.out.println("이미 대기큐에 있는 유저입니다.");
    }

    public UniqueUser matching(Integer currentUserId) {
        for (UniqueUser u: waitingQueue) {

            if (currentUserId.equals(parseInt(u.getUserId())))
                continue;

            boolean cond = currentUserId < parseInt(u.getUserId());

            boolean exists = matchingRepository.existsById(MatchingId.builder()
                    .user1(cond ? currentUserId : parseInt(u.getUserId()))
                    .user2(cond ? parseInt(u.getUserId()) : currentUserId)
                    .build());

            waitingQueue.remove(u);
            if (exists)
                System.out.println(u.getUserId() + " " + currentUserId + " 의 일기가 이미 존재하지만 매칭되었습니다.");
            return u;
        }
        return null;
    }

    public void removeUser(UUID uuid) {
        UniqueUser user = findByUUID(uuid);
        waitingQueue.remove(user);
    }

    private UniqueUser findByUUID(UUID uuid) {
        for (UniqueUser u: waitingQueue) {
            if (u.getUuid().equals(uuid))
                return u;
        }
        return null;
    }

}
