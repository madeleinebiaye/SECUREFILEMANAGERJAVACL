package com.esiea.sfm.infrastructure.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppLogger {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void info(String message) {
        log("INFO", message);
    }

    public static void error(String message) {
        log("ERROR", message);
    }

    private static void log(String level, String message) {
        System.out.println(
                "[" + LocalDateTime.now().format(FORMATTER) + "] [" + level + "] " + message
        );
    }
}
