package com.github.dmkyr20.climb2n.security;

import java.time.Instant;
import java.util.Date;

final class DateConverter {

    private DateConverter() {
        throw new AssertionError("No instances allowed");
    }

    static Date convert(Instant instant) {
        return instant == null ? null : Date.from(instant);
    }

    static Instant convert(Date date) {
        return date == null ? null : date.toInstant();
    }
}
