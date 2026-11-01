package mx.pm.sat.ape1.dtsbot.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.pm.sat.ape1.dtsbot.dto.Incident;
import mx.pm.sat.ape1.dtsbot.dto.IncidentProcessResponse;
import mx.pm.sat.ape1.dtsbot.service.EvaluatorService;
import mx.pm.sat.ape1.dtsbot.service.ReportService;
import mx.pm.sat.ape1.dtsbot.service.exception.ReportServiceException;
import mx.pm.sat.ape1.dtsbot.util.FileUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class EvaluatorServiceImpl implements EvaluatorService {

    private final ReportService reportService;

    private static final BigDecimal LOW_LIMIT = BigDecimal.valueOf(0.5);
    private static final BigDecimal HIGH_LMIT = BigDecimal.valueOf(0.8);

    private static final String DOT_TXT = ".txt";
    private static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd_HHmm");
    private static final String UNDERSCORE = "_";
    private static final String PARENT_PATH = "results";
    private static final String CONFIRMED_PATH = "confirmed";
    private static final String SUSPICIOUS_PATH = "suspicious";
    private static final String UNCONFIRMED_PATH = "unconfirmed";

    @Override
    public void process(Incident incident, IncidentProcessResponse incidentProcessResponse,
            LocalDateTime localDateTime) {
        String fileBaseName = FilenameUtils.getBaseName(incident.filename());

        String sanitizedFileName = FileUtil.toSafeFilename(fileBaseName, DOT_TXT);

        String path = PARENT_PATH + File.separator + localDateTime.format(CUSTOM_FORMATTER) + UNDERSCORE
                + FileUtil.toSafeFilename(sanitizedFileName, DOT_TXT);

        String suspiciousPath = path + File.separator + SUSPICIOUS_PATH + File.separator
                + localDateTime.format(CUSTOM_FORMATTER) + UNDERSCORE + SUSPICIOUS_PATH + UNDERSCORE + sanitizedFileName
                + DOT_TXT;

        String confirmedPath = path + File.separator + CONFIRMED_PATH + File.separator
                + localDateTime.format(CUSTOM_FORMATTER) + UNDERSCORE + CONFIRMED_PATH + UNDERSCORE + sanitizedFileName
                + DOT_TXT;

        String unconfirmedPath = path + File.separator + UNCONFIRMED_PATH + File.separator
                + localDateTime.format(CUSTOM_FORMATTER) + UNDERSCORE + UNCONFIRMED_PATH + UNDERSCORE + sanitizedFileName
                + DOT_TXT;

        File unconfirmedFile = Paths.get(unconfirmedPath).toFile();
        File suspiciousFile = Paths.get(suspiciousPath).toFile();
        File confirmedFile = Paths.get(confirmedPath).toFile();

        if (incidentProcessResponse.likely().compareTo(LOW_LIMIT) < 0) {
            try {
                reportService.appendToReport(unconfirmedFile, incident.incs());
            } catch (ReportServiceException e) {
                log.error(e.getMessage());
            }
        }

        if (incidentProcessResponse.likely().compareTo(LOW_LIMIT) >= 0
                && incidentProcessResponse.likely().compareTo(HIGH_LMIT) < 0) {
            try {
                reportService.appendToReport(suspiciousFile, incident.incs());
            } catch (ReportServiceException e) {
                log.error(e.getMessage());
            }
        }

        if (incidentProcessResponse.likely().compareTo(HIGH_LMIT) > 0) {
            try {
                reportService.appendToReport(confirmedFile, incident.incs());
            } catch (ReportServiceException e) {
                log.error(e.getMessage());
            }
        }
    }
}
