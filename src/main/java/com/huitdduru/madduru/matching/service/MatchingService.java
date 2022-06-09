package com.huitdduru.madduru.matching.service;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.huitdduru.madduru.matching.payload.AcceptRequest;
import com.huitdduru.madduru.matching.payload.FriendCodeRequest;

public interface MatchingService {
    void start(SocketIOClient client, SocketIOServer server);

    void cancel(SocketIOClient client, SocketIOServer server);

    void accept(SocketIOClient client, SocketIOServer server, AcceptRequest request);

    void friendMatching(SocketIOClient client, FriendCodeRequest request);
}
