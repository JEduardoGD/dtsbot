package mx.pm.sat.ape1.dtsbot.service.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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
    
    @Value("${env.so}")
    private String so;
    
    @Value("${dtstemplate.location}")
    private String dtsTemplateLocationStr;

    @Override
    public void process(Incident incident, IncidentProcessResponse incidentProcessResponse, LocalDateTime localDateTime)
            throws EvaluatorServiceException {
        String incidentPropertiesfileBasename = null;
        log.info("Procesando {}", incident.incs());
        String safeIincidentPropertiesfileBasename = null;
        try {
            incidentPropertiesfileBasename = OnFlyVarsUtil.getFileBaseName(incident);
            safeIincidentPropertiesfileBasename = OnFlyVarsUtil.getSafeFilename(incidentPropertiesfileBasename, EvaluatorStaticValues.DOT_TXT);
        } catch (OnFlyVarsUtilException e) {
            throw new EvaluatorServiceException(e);
        }

        String path = EvaluatorStaticValues.PARENT_PATH + File.separator
                + localDateTime.format(EvaluatorStaticValues.CUSTOM_FORMATTER) + EvaluatorStaticValues.UNDERSCORE
                + FileUtil.toSafeFilename(safeIincidentPropertiesfileBasename, EvaluatorStaticValues.DOT_TXT);

        String suspiciousFilePath = path + File.separator + EvaluatorStaticValues.SUSPICIOUS_PATH + File.separator
                + (localDateTime.format(EvaluatorStaticValues.CUSTOM_FORMATTER) + EvaluatorStaticValues.UNDERSCORE
                        + EvaluatorStaticValues.SUSPICIOUS_PATH + EvaluatorStaticValues.UNDERSCORE + safeIincidentPropertiesfileBasename
                        + EvaluatorStaticValues.DOT_TXT);

        String confirmedFilePath = path + File.separator + EvaluatorStaticValues.CONFIRMED_PATH + File.separator
                + (localDateTime.format(EvaluatorStaticValues.CUSTOM_FORMATTER) + EvaluatorStaticValues.UNDERSCORE
                        + EvaluatorStaticValues.CONFIRMED_PATH + EvaluatorStaticValues.UNDERSCORE + safeIincidentPropertiesfileBasename
                        + EvaluatorStaticValues.DOT_TXT);

        String unconfirmedFilePath = path + File.separator + EvaluatorStaticValues.UNCONFIRMED_PATH + File.separator
                + (localDateTime.format(EvaluatorStaticValues.CUSTOM_FORMATTER) + EvaluatorStaticValues.UNDERSCORE
                        + EvaluatorStaticValues.UNCONFIRMED_PATH + EvaluatorStaticValues.UNDERSCORE + safeIincidentPropertiesfileBasename
                        + EvaluatorStaticValues.DOT_TXT);

        File unconfirmedFile = Paths.get(unconfirmedFilePath).toFile();
        File suspiciousFile = Paths.get(suspiciousFilePath).toFile();
        File confirmedFile = Paths.get(confirmedFilePath).toFile();
        
        Path parentPath = null;
        //Path propertiesPath = null;
        //Path absolutePath;
        Path dtsTemplateAbsoluteLocation;

        if (incidentProcessResponse.likely().compareTo(EvaluatorStaticValues.LOW_LIMIT) < 0) {
            try {
                reportService.appendToReport(unconfirmedFile, incident.incs());
            } catch (ReportServiceException e) {
                log.error(e.getMessage());
                throw new EvaluatorServiceException(e);
            }
            parentPath = Paths.get(unconfirmedFilePath).getParent();
            //propertiesPath = Paths.get(OnFlyVarsUtil.createIncPropertiesFileRoute(parentPath, incident));
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
            //propertiesPath = Paths.get(OnFlyVarsUtil.createIncPropertiesFileRoute(parentPath, incident));
        }

        if (incidentProcessResponse.likely().compareTo(EvaluatorStaticValues.HIGH_LMIT) > 0) {
            try {
                reportService.appendToReport(confirmedFile, incident.incs());
            } catch (ReportServiceException e) {
                log.error(e.getMessage());
                throw new EvaluatorServiceException(e);
            }
            parentPath = Paths.get(confirmedFilePath).getParent();
            //propertiesPath = Paths.get(OnFlyVarsUtil.createIncPropertiesFileRoute(parentPath, incident));
        }
        
        /*
        if (propertiesPath == null) {
            throw new EvaluatorServiceException("propertiesPath is null");
        }
        absolutePath = propertiesPath.toAbsolutePath();
        */
        
        //Path parentAbsolutePath = parentPath.toAbsolutePath();
        
        String incsPropertiesAbsolutePathStr = OnFlyVarsUtil.createIncPropertiesFileRoute(parentPath, incident);
        Path incsPropertiesAbsolutePath = Paths.get(incsPropertiesAbsolutePathStr).toAbsolutePath();
        
        dtsTemplateAbsoluteLocation = Paths.get(dtsTemplateLocationStr).toAbsolutePath();

        if (incidentProcessResponse.likely().compareTo(EvaluatorStaticValues.LOW_LIMIT) >= 0) {
            try {
                createPropertiesIncsService.createIncsPropertiesFile(parentPath, incident, incidentProcessResponse);
            } catch (CreatePropertiesIncsServiceException e) {
                throw new EvaluatorServiceException(e);
            }
            List<String> pythonCommand = createRunPythonCommands(incsPropertiesAbsolutePath, dtsTemplateAbsoluteLocation);
            try {
                if (SoUtil.runningSoInstructions(pythonCommand/* , "dtsbot_py" */) != 0) {
                    log.error("An error occurs on running so call");
                    throw new EvaluatorServiceException("An error occurs on running so call");
                }
            } catch (SoUtilException e) {
                throw new EvaluatorServiceException(e);
            }
        }
    }

    private List<String> createRunPythonCommands(Path incsPropertiesAbsolutePath, Path dtsTemplateAbsoluteLocation) {
        //.\run_dtsbot_exe.bat "C:\Users\Eduardo\Documents\dtsbot_py\INCS00003799504\INCS00003799504.txt" "C:\Users\Eduardo\Documents\dtsbot_py\examples\NNNNNNN_DTS.docx" "C:\Users\Eduardo\Documents\dtsbot_py\INCS00003799504"
        List<String> commandsList = new ArrayList<>();
        // commandsList.add(".venv/bin/python");
        if (so.equals(EvaluatorStaticValues.WINDOWS)) {
            commandsList.add(".\\run_dtsbot_exe.bat");
            commandsList.add(String.format("\"%s\"", incsPropertiesAbsolutePath));
            commandsList.add(String.format("\"%s\"", dtsTemplateAbsoluteLocation));
            commandsList.add(String.format("\"%s\"", incsPropertiesAbsolutePath.getParent().toString()));
        }
        if (so.equals(EvaluatorStaticValues.LINUX)) {
            commandsList.add("./run_dtsbot_py.sh");
            commandsList.add(String.format("%s", incsPropertiesAbsolutePath));
            commandsList.add(String.format("%s", dtsTemplateAbsoluteLocation));
            commandsList.add(String.format("%s", incsPropertiesAbsolutePath.getParent().toString()));
        }
        return commandsList;
    }
}
