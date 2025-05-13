package com.github.dmkyr20.climb2n.integration;

import com.github.dmkyr20.climb2n.service.TrainingSessionCompletedEvent;

public interface Integration {

    void process(TrainingSessionCompletedEvent event);
}
