package com.github.dmkyr20.climb2n.service;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TrainingSessionCompleted extends ApplicationEvent {

    private final TrainingSession session;

    TrainingSessionCompleted(Object source, TrainingSession session) {
        super(source);
        this.session = session;
    }
}
