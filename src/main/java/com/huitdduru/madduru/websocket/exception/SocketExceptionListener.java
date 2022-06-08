package com.huitdduru.madduru.websocket.exception;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListener;
import com.huitdduru.madduru.exception.BaseException;
import com.huitdduru.madduru.exception.ErrorResponse;
import com.huitdduru.madduru.websocket.SocketProperty;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SocketExceptionListener implements ExceptionListener {

    @Override
    public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
        runExceptionHandling(e, client);
    }

    @Override
    public void onDisconnectException(Exception e, SocketIOClient client) {
        runExceptionHandling(e, client);
    }

    @Override
    public void onConnectException(Exception e, SocketIOClient client) {
        client.disconnect();
        runExceptionHandling(e, client);
    }

    @Override
    public void onPingException(Exception e, SocketIOClient client) {
        runExceptionHandling(e, client);
    }

    @Override
    public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        return false;
    }

    private void runExceptionHandling(Exception e, SocketIOClient client) {
        final ErrorResponse message;

        e.printStackTrace();

        if (e.getCause() instanceof BaseException) {
            BaseException error = (BaseException) e.getCause();
            message = ErrorResponse.builder()
                    .message(error.getErrorCode().getMessage())
                    .status(error.getErrorCode().getStatus())
                    .build();
        } else {
            message = ErrorResponse.builder()
                    .status(500)
                    .message(e.getCause().toString())
                    .build();
        }

        client.sendEvent(SocketProperty.ERROR_KEY, message);
    }
}
