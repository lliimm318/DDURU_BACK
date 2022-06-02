package com.huitdduru.madduru.matching.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.huitdduru.madduru.matching.annotations.SocketController;
import com.huitdduru.madduru.matching.annotations.SocketMapping;
import com.huitdduru.madduru.matching.payload.CreateRoomRequest;
import com.huitdduru.madduru.matching.security.SocketAuthenticationFacade;
import com.huitdduru.madduru.matching.service.RoomService;
import com.huitdduru.madduru.user.entity.User;
import lombok.RequiredArgsConstructor;

@SocketController
@RequiredArgsConstructor
public class ChatController {

    private final SocketAuthenticationFacade authenticationFacade;
    private final RoomService roomService;

    @SocketMapping(endpoint = "create", requestCls = CreateRoomRequest.class)
    public void create(SocketIOClient client, CreateRoomRequest request) {
        User user = authenticationFacade.getCurrentUser(client);
        roomService.createChatRoom(request, user);
    }
}
