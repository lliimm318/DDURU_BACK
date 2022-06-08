package com.huitdduru.madduru.matching.queue;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

import static java.lang.Integer.parseInt;

@Component
public class WaitingQueue {

    private final Queue<UniqueUser> waitingQueue = new LinkedList<>();

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

            waitingQueue.remove(u);
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
