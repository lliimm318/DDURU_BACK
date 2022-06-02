package com.huitdduru.madduru.matching.security;

import com.corundumstudio.socketio.SocketIOClient;
import com.huitdduru.madduru.exception.exceptions.UserNotFoundException;
import com.huitdduru.madduru.user.entity.User;
import com.huitdduru.madduru.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocketAuthenticationFacadeImpl implements SocketAuthenticationFacade{

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser(SocketIOClient client) {
        String email = client.get(AuthenticationProperty.USER_KEY);
        return userRepository.findByEmail(email)
                    .orElseThrow(UserNotFoundException::new);
    }
}
