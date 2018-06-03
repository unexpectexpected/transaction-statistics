package com.unexpectexpected.n26.rest.service.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class DateTimeUtils {

    public static ZonedDateTime format(long timestamp) {

        ZonedDateTime utc = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.UTC);

        return utc;
    }

    public static long format(ZonedDateTime zonedDateTime) {

        long timestamp = zonedDateTime.toInstant().toEpochMilli();

        return timestamp;
    }

    public static boolean olderThan(long timestamp, long seconds) {

        ZonedDateTime now = Instant.now().atOffset(ZoneOffset.UTC).toZonedDateTime();

        return format(timestamp).isAfter(now.minusSeconds(seconds)) && format(timestamp).isBefore(now);
    }


}
