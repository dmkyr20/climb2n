package com.github.dmkyr20.climb2n.integration;

import com.github.dmkyr20.climb2n.service.TrainingSessionCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
class IntegrationsProcessor {

    private final List<Integration> integrations;

    @Autowired
    IntegrationsProcessor(List<Integration> integrations) {
        this.integrations = integrations;
    }

    @EventListener(TrainingSessionCompletedEvent.class)
    private void process(TrainingSessionCompletedEvent event) {
        integrations.parallelStream()
                .forEach(handleExceptions(integration -> integration.process(event)));
    }

    private Consumer<Integration> handleExceptions(Consumer<Integration> consumer) {
        return integration -> {
            try {
                consumer.accept(integration);
            } catch (Exception e) {
                log.error("Integration error", e);
            }
        };
    }
}
