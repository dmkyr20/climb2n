package com.github.dmkyr20.climb2n.integration.notion;

import lombok.Builder;
import lombok.With;

@With
@Builder
record NotionDatabaseProperty(
        String id,
        String name,
        String type) {

}
