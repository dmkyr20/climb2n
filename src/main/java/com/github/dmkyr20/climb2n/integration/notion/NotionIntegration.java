package com.github.dmkyr20.climb2n.integration.notion;

import com.github.dmkyr20.climb2n.integration.Integration;
import com.github.dmkyr20.climb2n.security.User;
import com.github.dmkyr20.climb2n.service.DifficultyLevel;
import com.github.dmkyr20.climb2n.service.TrainingSession;
import com.github.dmkyr20.climb2n.service.TrainingSessionCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;

@Slf4j
@Component
class NotionIntegration implements Integration {

    private final NotionClientFactory clientFactory;

    @Autowired
    NotionIntegration(NotionClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void process(TrainingSessionCompletedEvent event) {
        User user = event.getUser();
        NotionClient client = clientFactory.create(user);
        NotionMapping mapping = NotionMapping.from(user);

        NotionDatabase database = client.retrieveDatabase(mapping.databaseId());

        NotionPage page = new NotionPage(
                new NotionPage.NotionPageParent(mapping.databaseId()),
                new HashMap<>());

        TrainingSession session = event.getSession();
        computeDifficultyRecords(page, session, mapping);
        computeDate(page, session, mapping, ZoneId.of(user.getKey("zone-id")));
        computeDuration(page, session, mapping);
        computeTitle(page, session, mapping);

        client.createPage(page);

        log.info(database.toString());
    }

    private void computeDifficultyRecords(NotionPage page, TrainingSession session, NotionMapping mapping) {
        Arrays.stream(DifficultyLevel.values()).forEach(level -> computeDifficultyRecord(level, page, session, mapping));
    }

    private void computeDifficultyRecord(
            DifficultyLevel level,
            NotionPage page,
            TrainingSession session,
            NotionMapping mapping) {
        page.properties().put(
                mapping.getDifficultyKey(level),
                NotionPageProperty.number(session.records().get(level)));
    }

    private void computeDate(NotionPage page, TrainingSession session, NotionMapping mapping, ZoneId zoneId) {
        page.properties().put(mapping.keyDate(), NotionPageProperty.date(session.startTime(), zoneId));
    }

    private void computeDuration(NotionPage page, TrainingSession session, NotionMapping mapping) {
        page.properties().put(mapping.keyDuration(), NotionPageProperty.number(session.duration().toMinutes()));
    }

    private void computeTitle(NotionPage page, TrainingSession session, NotionMapping mapping) {
        page.properties().put(mapping.keyName(), NotionPageProperty.title(session.name()));
    }
}
