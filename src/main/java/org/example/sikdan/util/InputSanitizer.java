package org.example.sikdan.util;

public final class InputSanitizer {

    private InputSanitizer() {
    }

    public static String nullIfBlank(String input) {
        return (input == null || input.trim().isEmpty()) ? null : input.trim();
    }
}
