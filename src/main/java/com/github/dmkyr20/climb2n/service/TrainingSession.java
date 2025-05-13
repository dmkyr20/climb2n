package com.github.dmkyr20.climb2n.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Singular;
import lombok.With;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Builder
public record TrainingSession(
        String name,
        @Singular
        Map<DifficultyLevel, Integer> records,
        Instant startTime,
        @With
        Instant endTime) {

    static TrainingSessionBuilder initBuilder() {
        return TrainingSession.builder()
                .record(DifficultyLevel.ONE, 0)
                .record(DifficultyLevel.TWO, 0)
                .record(DifficultyLevel.THREE, 0)
                .record(DifficultyLevel.FOUR, 0)
                .record(DifficultyLevel.FIVE, 0)
                .record(DifficultyLevel.SIX, 0)
                .record(DifficultyLevel.SEVEN, 0);
    }

    @JsonIgnore
    Duration duration() {
        if (startTime == null || endTime == null) {
            return null;
        }
        return Duration.between(startTime, endTime);
    }
}
