package com.github.dmkyr20.climb2n.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final Map<String, User> usersByName;
    private final Map<UUID, User> usersById;

    @Autowired
    UserService(
            PasswordEncoder passwordEncoder,
            SecurityProperties properties) {
        this.usersByName = index(User::getUsername, properties.users(), passwordEncoder);
        this.usersById = index(User::getId, properties.users(), passwordEncoder);
    }

    private static <T> Map<T, User> index(
            Function<User, T> keyFunction,
            List<UserProperties> properties,
            PasswordEncoder passwordEncoder) {
        return properties.stream()
                .map(user -> convert(user, passwordEncoder))
                .collect(Collectors.toMap(
                        keyFunction,
                        Function.identity()));
    }

    private static User convert(UserProperties properties, PasswordEncoder passwordEncoder) {
        return User.builder()
                .id(properties.uuid())
                .username(properties.username())
                .password(passwordEncoder.encode(properties.password()))
                .authorities(List.of())
                .build();
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersByName.get(Objects.requireNonNull(username));
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public User loadUserById(UUID id) throws UsernameNotFoundException {
        User user = usersById.get(Objects.requireNonNull(id));
        if (user == null) {
            throw new UsernameNotFoundException(id.toString());
        }
        return user;
    }
}
