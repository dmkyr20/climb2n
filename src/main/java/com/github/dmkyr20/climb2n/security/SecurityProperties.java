package com.github.dmkyr20.climb2n.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("climb2n.security")
record SecurityProperties(
        JwtProperties jwt,
        List<UserProperties> users) {

}
