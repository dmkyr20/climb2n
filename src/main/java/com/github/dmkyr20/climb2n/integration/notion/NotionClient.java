package com.github.dmkyr20.climb2n.integration.notion;

import org.springframework.web.client.RestClient;

class NotionClient {

    private final String apiToken;
    private final RestClient client;

    NotionClient(RestClient client, String apiToken) {
        this.client = client;
        this.apiToken = apiToken;
    }

    NotionDatabase retrieveDatabase(String databaseId) {
        return client.get()
                .uri("/databases/{db_id}", databaseId)
                .header("Authorization", "Bearer " + apiToken)
                .retrieve()
                .body(NotionDatabase.class);
    }

    NotionPage createPage(NotionPage page) {
        return client.post()
                .uri("/pages")
                .header("Authorization", "Bearer " + apiToken)
                .body(page)
                .retrieve()
                .body(NotionPage.class);
    }
}
