package com.huitdduru.madduru.websocket.security;

import com.corundumstudio.socketio.SocketIOClient;
import com.huitdduru.madduru.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WebSocketConnectController {

    private final TokenProvider tokenProvider;

    public void onConnect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("Authorization");
        Authentication authentication = tokenProvider.getAuthentication(token);
        client.set(AuthenticationProperty.USER_KEY, authentication.getName());
    }

}

