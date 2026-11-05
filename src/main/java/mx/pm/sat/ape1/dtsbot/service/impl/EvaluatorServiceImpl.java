package mx.pm.sat.ape1.dtsbot.service.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.pm.sat.ape1.dtsbot.dto.Incident;
import mx.pm.sat.ape1.dtsbot.dto.IncidentProcessResponse;
import mx.pm.sat.ape1.dtsbot.service.CreatePropertiesIncsService;
import mx.pm.sat.ape1.dtsbot.service.EvaluatorService;
import mx.pm.sat.ape1.dtsbot.service.ReportService;
import mx.pm.sat.ape1.dtsbot.service.exception.CreatePropertiesIncsServiceException;
import mx.pm.sat.ape1.dtsbot.service.exception.EvaluatorServiceException;
import mx.pm.sat.ape1.dtsbot.service.exception.ReportServiceException;
import mx.pm.sat.ape1.dtsbot.util.EvaluatorStaticValues;
import mx.pm.sat.ape1.dtsbot.util.FileUtil;
import mx.pm.sat.ape1.dtsbot.util.OnFlyVarsUtil;
import mx.pm.sat.ape1.dtsbot.util.SoUtil;
import mx.pm.sat.ape1.dtsbot.util.exception.OnFlyVarsUtilException;
import mx.pm.sat.ape1.dtsbot.util.exception.SoUtilException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EvaluatorServiceImpl implements EvaluatorService {

    private final ReportService reportService;
    private final CreatePropertiesIncsService createPropertiesIncsService;

    @Override
    public void process(Incident incident, IncidentProcessResponse incidentProcessResponse, LocalDateTime localDateTime)
            throws EvaluatorServiceException {
        String fileBaseName = null;
        String safeFilename = null;
        try {
            fileBaseName = OnFlyVarsUtil.getFileBaseName(incident);
            safeFilename = OnFlyVarsUtil.getSafeFilename(fileBaseName, EvaluatorStaticValues.DOT_TXT);
        } catch (OnFlyVarsUtilException e) {
            throw new EvaluatorServiceException(e);
        }

        String path = EvaluatorStaticValues.PARENT_PATH + File.separator
                + localDateTime.format(EvaluatorStaticValues.CUSTOM_FORMATTER) + EvaluatorStaticValues.UNDERSCORE
                + FileUtil.toSafeFilename(safeFilename, EvaluatorStaticValues.DOT_TXT);

        String suspiciousFilePath = path + File.separator + EvaluatorStaticValues.SUSPICIOUS_PATH + File.separator
                + (localDateTime.format(EvaluatorStaticValues.CUSTOM_FORMATTER) + EvaluatorStaticValues.UNDERSCORE
                        + EvaluatorStaticValues.SUSPICIOUS_PATH + EvaluatorStaticValues.UNDERSCORE + safeFilename
                        + EvaluatorStaticValues.DOT_TXT);

        String confirmedFilePath = path + File.separator + EvaluatorStaticValues.CONFIRMED_PATH + File.separator
                + (localDateTime.format(EvaluatorStaticValues.CUSTOM_FORMATTER) + EvaluatorStaticValues.UNDERSCORE
                        + EvaluatorStaticValues.CONFIRMED_PATH + EvaluatorStaticValues.UNDERSCORE + safeFilename
                        + EvaluatorStaticValues.DOT_TXT);

        String unconfirmedFilePath = path + File.separator + EvaluatorStaticValues.UNCONFIRMED_PATH + File.separator
                + (localDateTime.format(EvaluatorStaticValues.CUSTOM_FORMATTER) + EvaluatorStaticValues.UNDERSCORE
                        + EvaluatorStaticValues.UNCONFIRMED_PATH + EvaluatorStaticValues.UNDERSCORE + safeFilename
                        + EvaluatorStaticValues.DOT_TXT);

        File unconfirmedFile = Paths.get(unconfirmedFilePath).toFile();
        File suspiciousFile = Paths.get(suspiciousFilePath).toFile();
        File confirmedFile = Paths.get(confirmedFilePath).toFile();
        
        Path parentPath = null;
        Path propertiesPath = null;

        if (incidentProcessResponse.likely().compareTo(EvaluatorStaticValues.LOW_LIMIT) < 0) {
            try {
                reportService.appendToReport(unconfirmedFile, incident.incs());
            } catch (ReportServiceException e) {
                log.error(e.getMessage());
                throw new EvaluatorServiceException(e);
            }
            parentPath = Paths.get(unconfirmedFilePath).getParent();
            propertiesPath = Paths.get(OnFlyVarsUtil.createIncPropertiesFileRoute(parentPath, incident));
        }

        if (incidentProcessResponse.likely().compareTo(EvaluatorStaticValues.LOW_LIMIT) >= 0
                && incidentProcessResponse.likely().compareTo(EvaluatorStaticValues.HIGH_LMIT) < 0) {
            try {
                reportService.appendToReport(suspiciousFile, incident.incs());
            } catch (ReportServiceException e) {
                log.error(e.getMessage());
                throw new EvaluatorServiceException(e);
            }
            parentPath = Paths.get(suspiciousFilePath).getParent();
            propertiesPath = Paths.get(OnFlyVarsUtil.createIncPropertiesFileRoute(parentPath, incident));
        }

        if (incidentProcessResponse.likely().compareTo(EvaluatorStaticValues.HIGH_LMIT) > 0) {
            try {
                reportService.appendToReport(confirmedFile, incident.incs());
            } catch (ReportServiceException e) {
                log.error(e.getMessage());
                throw new EvaluatorServiceException(e);
            }
            parentPath = Paths.get(confirmedFilePath).getParent();
            propertiesPath = Paths.get(OnFlyVarsUtil.createIncPropertiesFileRoute(parentPath, incident));
        }

        if (incidentProcessResponse.likely().compareTo(EvaluatorStaticValues.LOW_LIMIT) >= 0) {
            try {
                createPropertiesIncsService.createIncsPropertiesFile(parentPath, incident);
            } catch (CreatePropertiesIncsServiceException e) {
                throw new EvaluatorServiceException(e);
            }
            Integer numeroIncs = OnFlyVarsUtil.extractNumFromIncs(incident);
            List<String> pythonCommand = createRunPythonCommands(propertiesPath, numeroIncs);
            try {
                if (SoUtil.runningSoInstructions(pythonCommand/* , "dtsbot_py" */) != 0) {
                    log.error("Error running so call");
                }
            } catch (SoUtilException e) {
                throw new EvaluatorServiceException(e);
            }
        }
    }

    private List<String> createRunPythonCommands(Path propertiesPath, Integer numeroIncs) {
        List<String> commandsList = new ArrayList<>();
        // commandsList.add(".venv/bin/python");
        commandsList.add("./dtsbot_py/run_dtsbot.sh");
        commandsList.add(String.format("%s", propertiesPath));
        commandsList.add("--output-dir");
        commandsList.add(String.format("%s", propertiesPath.getParent().toString()));
        return commandsList;
    }
}
