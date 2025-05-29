package com.github.dmkyr20.climb2n.integration.notion;

import com.github.dmkyr20.climb2n.security.User;
import com.github.dmkyr20.climb2n.service.DifficultyLevel;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.Objects;

@Builder(access = AccessLevel.PROTECTED)
record NotionMapping(
        String databaseId,
        String keyName,
        String keyLevel1,
        String keyLevel2,
        String keyLevel3,
        String keyLevel4,
        String keyLevel5,
        String keyLevel6,
        String keyLevel7,
        String keyDuration,
        String keyDate) {

    static NotionMapping from(User user) {
        return NotionMapping.builder()
                .databaseId(Objects.requireNonNull(user.getKey("notion.database-id")))
                .keyDuration(Objects.requireNonNull(user.getKey("notion.key.duration")))
                .keyName(Objects.requireNonNull(user.getKey("notion.key.name")))
                .keyDate(Objects.requireNonNull(user.getKey("notion.key.date")))
                .keyLevel1(Objects.requireNonNull(user.getKey("notion.key.level1")))
                .keyLevel2(Objects.requireNonNull(user.getKey("notion.key.level2")))
                .keyLevel3(Objects.requireNonNull(user.getKey("notion.key.level3")))
                .keyLevel4(Objects.requireNonNull(user.getKey("notion.key.level4")))
                .keyLevel5(Objects.requireNonNull(user.getKey("notion.key.level5")))
                .keyLevel6(Objects.requireNonNull(user.getKey("notion.key.level6")))
                .keyLevel7(Objects.requireNonNull(user.getKey("notion.key.level7")))
                .build();
    }

    String getDifficultyKey(DifficultyLevel level) {
        return switch (level) {
            case ONE -> keyLevel1;
            case TWO -> keyLevel2;
            case THREE -> keyLevel3;
            case FOUR -> keyLevel4;
            case FIVE -> keyLevel5;
            case SIX -> keyLevel6;
            case SEVEN -> keyLevel7;
        };
    }
}
