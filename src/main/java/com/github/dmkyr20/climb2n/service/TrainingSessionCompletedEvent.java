package com.github.dmkyr20.climb2n.service;

import com.github.dmkyr20.climb2n.security.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TrainingSessionCompletedEvent extends ApplicationEvent {

    private final User user;
    private final TrainingSession session;

    TrainingSessionCompletedEvent(
            Object source,
            User user,
            TrainingSession session) {
        super(source);
        this.user = user;
        this.session = session;
    }
}
