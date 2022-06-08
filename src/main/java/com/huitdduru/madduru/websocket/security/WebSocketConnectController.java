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
        String token = client.getHandshakeData().getHttpHeaders().get("Authorization");
        Authentication authentication = tokenProvider.getAuthentication(token);
        client.set(ClientProperty.USER_KEY, authentication.getName());
        client.set(ClientProperty.ACCEPT_KEY, AcceptProperty.NULL);
        client.set(ClientProperty.ROOM_ID_KEY, AcceptProperty.NULL);
    }

    public void onDisconnect(SocketIOClient client) {
        waitingQueue.removeUser(client.getSessionId());
    }

}
