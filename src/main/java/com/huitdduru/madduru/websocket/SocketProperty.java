package com.huitdduru.madduru.websocket;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocketProperty {

    public static final String USERINFO_KEY = "userinfo";

    public static final String ERROR_KEY = "error";

    public static final String ACCEPT_KEY = "accept";

    public static final String ROOM_ID_KEY = "room_id";

    public static final String CANCEL_KEY = "cancel";

    public static final String WAITING_KEY = "waiting";
}
