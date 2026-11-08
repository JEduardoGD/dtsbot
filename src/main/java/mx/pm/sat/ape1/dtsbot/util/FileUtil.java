package mx.pm.sat.ape1.dtsbot.util;

import java.util.Arrays;
import java.util.UUID;

public abstract class FileUtil {
    public static String toSafeFilename(String input, String fallbackExtension) {
        if (input == null || input.isBlank()) {
            return UUID.randomUUID().toString() + fallbackExtension;
        }

        // Replace any character not in [a-zA-Z0-9._-] with an underscore
        String sanitized = input.replaceAll("[^a-zA-Z0-9._-]+", "_");

        // Remove leading/trailing dots or spaces (prevents hidden files or weird shell
        // behavior)
        int start = 0;
        int end = sanitized.length();
        while (start < end && isBoundaryCharacter(sanitized.charAt(start))) {
            start++;
        }
        while (end > start && isBoundaryCharacter(sanitized.charAt(end - 1))) {
            end--;
        }
        sanitized = sanitized.substring(start, end);

        // Prevent empty strings after stripping
        if (sanitized.isBlank()) {
            sanitized = UUID.randomUUID().toString();
        }

        // Truncate to a reasonable maximum length (e.g., 255 bytes/characters for most
        // filesystems)
        int maxLength = 200;
        if (sanitized.length() > maxLength) {
            sanitized = sanitized.substring(0, maxLength);
        }

        return sanitized;
    }

    private static boolean isBoundaryCharacter(char character) {
        return character == '.' || character == '_' || character == '-';
    }

    public static String removeExtension(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }
        if (!input.contains(".")) {
            return input;
        }
        String[] source = input.split("\\.");
        String[] target = Arrays.copyOf(source, source.length - 1);
        return String.join(".", target);
    }
}
