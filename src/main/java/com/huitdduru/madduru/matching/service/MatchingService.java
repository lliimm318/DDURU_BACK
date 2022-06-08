package com.huitdduru.madduru.matching.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.huitdduru.madduru.matching.payload.AcceptRequest;

public interface MatchingService {
    void start(SocketIOClient client, SocketIOServer server);

    void cancel(SocketIOClient client, SocketIOServer server);

    void accept(SocketIOClient client, SocketIOServer server, AcceptRequest request);
}
