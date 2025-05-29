package com.github.dmkyr20.climb2n.integration.notion;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

record NotionPage(
        NotionPageParent parent,
        Map<String, NotionPageProperty> properties) {

    record NotionPageParent(@JsonProperty("database_id") String databaseId) { }
}
