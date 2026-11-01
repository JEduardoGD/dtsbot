package mx.pm.sat.ape1.dtsbot.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import mx.pm.sat.ape1.dtsbot.util.exception.TextUtilException;

@Slf4j
public abstract class TextUtil {
    public static List<String> loadTextFile(File file)  throws TextUtilException {
        Path path = file.toPath();
        List<String> allLines = new ArrayList<>();
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
            allLines.addAll(lines);
        } catch (IOException e) {
            log.error("Error loading {}", file.getPath());
            log.error(e.getMessage());
            throw new TextUtilException(e);
        }
        return allLines;
    }
}
