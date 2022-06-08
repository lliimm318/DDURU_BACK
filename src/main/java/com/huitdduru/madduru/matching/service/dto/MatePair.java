package com.huitdduru.madduru.matching.service.dto;

import com.corundumstudio.socketio.SocketIOClient;
import com.huitdduru.madduru.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatePair {
    private final String roomId;

    private final User user1;

    private final User user2;

    private final SocketIOClient client1;

    private final SocketIOClient client2;
}
