package org.example.mealservice.util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class InputSanitizer {

    public static String nullIfBlank(String input) {
        return (input == null || input.trim().isEmpty()) ? null : input.trim();
    }
}
