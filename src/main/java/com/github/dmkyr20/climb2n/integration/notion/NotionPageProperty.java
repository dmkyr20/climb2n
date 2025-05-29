package com.github.dmkyr20.climb2n.integration.notion;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@JsonInclude(JsonInclude.Include.NON_NULL)
record NotionPageProperty(
        NotionText[] title,
        Double number,
        NotionDate date) {

    record NotionDate(String start) { }

    record NotionText(NotionContent text) { }

    record NotionContent(String content) { }

    static NotionPageProperty title(String title) {
        return new NotionPageProperty(new NotionText[] { new NotionText(new NotionContent(title)) }, null, null);
    }

    static NotionPageProperty number(long number) {
        return new NotionPageProperty(null, (double) number, null);
    }

    static NotionPageProperty number(Integer number) {
        return new NotionPageProperty(null, number == null ? null : (double) number, null);
    }

    static NotionPageProperty date(Instant instant, ZoneId zoneId) {
        return new NotionPageProperty(null, null, new NotionDate(LocalDate.ofInstant(instant, zoneId).toString()));
    }

}
