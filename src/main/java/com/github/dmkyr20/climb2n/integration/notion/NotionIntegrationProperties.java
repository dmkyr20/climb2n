package com.github.dmkyr20.climb2n.integration.notion;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("climb2n.integrations.notion")
record NotionIntegrationProperties(
        NotionClientProperties client) {

    record NotionClientProperties(
            String baseUrl,
            String version,
            Duration readTimeout,
            Duration connectionTimeout) { }

}
