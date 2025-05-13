package com.github.dmkyr20.climb2n.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Component
class JwtTokenizer {

    private final JwtProperties properties;
    private final SecretKey signature;

    @Autowired
    JwtTokenizer(SecurityProperties properties) {
        this(properties.jwt() == null
                ? new JwtProperties(true, Duration.ofHours(5))
                : properties.jwt());
    }

    JwtTokenizer(JwtProperties properties) {
        this.properties = properties;
        this.signature = generateIfSigningEnabled(properties);
    }

    private static SecretKey generateIfSigningEnabled(JwtProperties properties) {
        return properties.isSigningEnabled()
                ? Jwts.SIG.HS256.key().build()
                : null;
    }

    String tokenizeUserId(UUID userId) {
        Instant now = Instant.now();

        JwtBuilder builder = Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(DateConverter.convert(now));

        if (properties.isTimeToLiveEnabled()) {
            builder.expiration(DateConverter.convert(
                    now.plus(properties.timeToLive())));
        }

        if (properties.isSigningEnabled()) {
            builder.signWith(signature);
        }
        return builder.compact();
    }

    UUID detokenizeUserId(String token) {
        JwtParserBuilder builder = Jwts.parser();

        if (properties.isTimeToLiveEnabled()) {
            builder.verifyWith(signature);
        }

        return UUID.fromString(
                builder.build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());
    }
}
