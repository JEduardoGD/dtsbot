package mx.pm.sat.ape1.dtsbot.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.stereotype.Service;

import mx.pm.sat.ape1.dtsbot.service.ReportService;
import mx.pm.sat.ape1.dtsbot.service.exception.ReportServiceException;

@Service
public class ReportServiceImpl implements ReportService {
    @Override
    public void appendToReport(File file, String content) throws ReportServiceException {
        Path path = Paths.get(file.getPath());
        
        Path parentPath = path.getParent();
        try {
            Files.createDirectories(parentPath);
            Files.write(path,
                    (content + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new ReportServiceException(e);
        }
    }
}
