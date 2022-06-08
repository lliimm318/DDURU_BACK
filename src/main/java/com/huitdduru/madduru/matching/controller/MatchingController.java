package com.huitdduru.madduru.matching.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.huitdduru.madduru.matching.payload.AcceptRequest;
import com.huitdduru.madduru.matching.service.MatchingService;
import com.huitdduru.madduru.websocket.annotations.SocketController;
import com.huitdduru.madduru.websocket.annotations.SocketMapping;
import com.huitdduru.madduru.websocket.security.SocketAuthenticationFacade;
import lombok.RequiredArgsConstructor;

@SocketController
@RequiredArgsConstructor
public class MatchingController {

    private final SocketAuthenticationFacade authenticationFacade;
    private final MatchingService socketService;

    @SocketMapping(endpoint = "matching.start")
    public void start(SocketIOClient client, SocketIOServer server) {
        socketService.start(client, server);
    }

    @SocketMapping(endpoint = "matching.cancel")
    public void stop(SocketIOClient client, SocketIOServer server) {
        socketService.cancel(client, server);
    }

    @SocketMapping(endpoint = "matching.accept", requestCls = AcceptRequest.class)
    public void accept(SocketIOClient client, SocketIOServer server, AcceptRequest request) {
        socketService.accept(client, server, request);
    }
}
