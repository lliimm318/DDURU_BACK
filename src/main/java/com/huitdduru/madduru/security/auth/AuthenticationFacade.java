package com.huitdduru.madduru.security.auth;

import com.huitdduru.madduru.exception.exceptions.UserNotFoundException;
import com.huitdduru.madduru.user.domain.User;
import com.huitdduru.madduru.user.domain.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    private UserRepository userRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUserEmail() {
        return this.getAuthentication().getName();
    }

    public User getUser() {
        return userRepository.findByEmail(getUserEmail())
                .orElseThrow(UserNotFoundException::new);
    }

}
