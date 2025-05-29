package com.github.dmkyr20.climb2n.integration.notion;

import java.util.Map;
import java.util.Optional;

record NotionDatabase(
        String id,
        Map<String, NotionDatabaseProperty> properties) {

    Optional<NotionDatabaseProperty> getProperty(String name) {
        return properties == null
                ? Optional.empty()
                : Optional.ofNullable(properties.get(name));
    }

}
