package com.github.dmkyr20.climb2n.service;

import com.github.dmkyr20.climb2n.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TrainingSessionService {

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    TrainingSessionService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public TrainingSession start(String name) {
        return TrainingSession.initBuilder()
                .startTime(Instant.now())
                .name(name)
                .build();
    }

    public void stop(TrainingSession session, User user) {
        eventPublisher.publishEvent(
                new TrainingSessionCompletedEvent(this, user, session.withEndTime(Instant.now())));
    }
}
