package com.huitdduru.madduru.websocket.security;

import com.corundumstudio.socketio.SocketIOClient;
import com.huitdduru.madduru.matching.queue.WaitingQueue;
import com.huitdduru.madduru.security.TokenProvider;
import com.huitdduru.madduru.websocket.SocketProperty;
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
        System.out.println("authentication.getName() = " + authentication.getName());
        client.set(AuthenticationProperty.USER_KEY, authentication.getName());
        client.set(SocketProperty.ACCEPT_KEY, null);
    }

    public void onDisconnect(SocketIOClient client) {
        waitingQueue.removeUser(client.getSessionId());
        client.leaveRoom(SocketProperty.WAITING_KEY);
    }

}

