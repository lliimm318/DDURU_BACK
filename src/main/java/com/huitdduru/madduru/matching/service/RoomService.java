package com.huitdduru.madduru.matching.service;

import com.huitdduru.madduru.matching.payload.CreateRoomRequest;
import com.huitdduru.madduru.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

    @Transactional
    public void createChatRoom(CreateRoomRequest request, User user) {
        System.out.println("hi");

    }
}
