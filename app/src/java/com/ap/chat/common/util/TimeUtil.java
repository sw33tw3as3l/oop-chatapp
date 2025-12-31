package com.ap.chat.common.util;

import java.time.Instant;

public class TimeUtil {
    public static String isoFromMillis(long millis) {
        return Instant.ofEpochMilli(millis).toString();
    }
}
