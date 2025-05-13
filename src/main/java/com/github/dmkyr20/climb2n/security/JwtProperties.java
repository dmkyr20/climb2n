package com.github.dmkyr20.climb2n.security;

import java.time.Duration;

record JwtProperties(
        boolean enableSigning,
        Duration timeToLive) {

    boolean isSigningEnabled() {
        return enableSigning;
    }

    boolean isTimeToLiveEnabled() {
        return timeToLive != null;
    }
}
