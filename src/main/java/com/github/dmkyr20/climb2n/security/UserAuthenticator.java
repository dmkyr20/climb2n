package com.github.dmkyr20.climb2n.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticator {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    UserAuthenticator(
            UserService userService,
            AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    public User authenticate(String username, String password) {
        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        username,
                        password));
        return userService.loadUserByUsername(username);
    }
}
