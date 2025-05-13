package com.github.dmkyr20.climb2n.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;
import java.util.Objects;

public class UserService implements UserDetailsService {

    private final Map<String, User> users;

    UserService(Map<String, User> users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.get(Objects.requireNonNull(username));
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
