package com.huitdduru.madduru.websocket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.huitdduru.madduru.websocket.exception.SocketExceptionListener;
import com.huitdduru.madduru.websocket.security.WebSocketConnectController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SocketConfig {

    private final WebSocketAddMappingSupporter mappingSupporter;
    private final WebSocketConnectController connectController;
    private final SocketExceptionListener exceptionListener;

    private Integer port = 8888;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setPort(port);
        config.setOrigin("*");
        config.setExceptionListener(exceptionListener);

        SocketIOServer server = new SocketIOServer(config);
        mappingSupporter.addListeners(server);
        server.addConnectListener(connectController::onConnect);
        server.addDisconnectListener(connectController::onDisconnect);

        return server;
    }

}
