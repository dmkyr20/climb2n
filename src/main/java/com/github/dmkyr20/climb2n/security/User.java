package com.github.dmkyr20.climb2n.security;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class User implements UserDetails {

    private final UUID id;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;
    private final Map<String, String> keys;

    public String getKey(String key) {
        return keys == null ? null : keys.get(key);
    }
}
