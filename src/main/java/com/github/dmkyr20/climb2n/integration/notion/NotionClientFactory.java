package com.github.dmkyr20.climb2n.integration.notion;

import com.github.dmkyr20.climb2n.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
class NotionClientFactory {

    private final NotionIntegrationProperties properties;
    private final ClientHttpRequestFactory requestFactory;

    @Autowired
    NotionClientFactory(NotionIntegrationProperties properties) {
        this.properties = properties;
        SimpleClientHttpRequestFactory simpleRequestFactory = new SimpleClientHttpRequestFactory();
        simpleRequestFactory.setReadTimeout(properties.client().readTimeout());
        simpleRequestFactory.setConnectTimeout(properties.client().connectionTimeout());
        this.requestFactory = simpleRequestFactory;
    }

    NotionClient create(User user) {
        return new NotionClient(
                RestClient.builder()
                        .baseUrl(properties.client().baseUrl())
                        .defaultHeader("Notion-Version", properties.client().version())
                        .requestFactory(requestFactory)
                        .build(),
                user.getKey("notion.api-key"));
    }


}
