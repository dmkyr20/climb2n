package com.github.dmkyr20.climb2n.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("climb2n.security")
record SecurityProperties(JwtProperties jwt) {

}
