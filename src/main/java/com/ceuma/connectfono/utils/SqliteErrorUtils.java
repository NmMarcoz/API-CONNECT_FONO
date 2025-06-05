package com.ceuma.connectfono.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SqliteErrorUtils {
    public static String extractUniqueConstraintField(String message) {
        if (message == null) return null;
        if (message.contains(")")) {
            message = message.substring(0, message.indexOf(")"));
        }
        String marker = "UNIQUE constraint failed: ";
        int idx = message.indexOf(marker);
        if (idx == -1) return null;
        String after = message.substring(idx + marker.length());
        String[] parts = after.split(",");
        if (parts.length > 0) {
            String field = parts[0].trim();
            int dotIdx = field.indexOf(".");
            if (dotIdx != -1) {
                return field.substring(dotIdx + 1);
            }

            return field;
        }
        return null;
    }

    public static String extractConstraintNullViolation(String errorMessage) {
        log.info("MENSAGEM DE ERRO: {}", errorMessage);
        String delimiter = ":";
        return errorMessage.substring(errorMessage.lastIndexOf(".") + 1);

    }
}
