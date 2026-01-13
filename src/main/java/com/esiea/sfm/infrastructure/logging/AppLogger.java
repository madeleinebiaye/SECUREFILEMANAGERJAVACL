package com.esiea.sfm.infrastructure.logging;

import java.time.LocalDateTime;

public class AppLogger {

    public static void info(String message) {
        log("INFO", message);
    }

    public static void error(String message) {
        log("ERROR", message);
    }

    private static void log(String level, String message) {
        System.out.println(
                "[" + LocalDateTime.now() + "] [" + level + "] " + message
        );
    }
}
