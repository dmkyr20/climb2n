package com.github.dmkyr20.climb2n.security;

import java.util.Map;
import java.util.UUID;

public record UserProperties(
        UUID uuid,
        String username,
        String password,
        boolean passwordEncrypted,
        Map<String, String> keys) {
}
