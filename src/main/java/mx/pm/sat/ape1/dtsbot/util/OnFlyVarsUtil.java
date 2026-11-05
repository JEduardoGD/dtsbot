package mx.pm.sat.ape1.dtsbot.util;

import java.io.File;
import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;

import mx.pm.sat.ape1.dtsbot.dto.Incident;
import mx.pm.sat.ape1.dtsbot.util.exception.OnFlyVarsUtilException;

public abstract class OnFlyVarsUtil {
    public static String getFileBaseName(Incident incident) throws OnFlyVarsUtilException {
        if (incident == null) {
            throw new OnFlyVarsUtilException("incidente can't be null");
        }
        return FilenameUtils.getBaseName(incident.filename());
    }

    public static String getSafeFilename(String sanitizedFileName, String fallbackExtension)
            throws OnFlyVarsUtilException {
        if (sanitizedFileName == null || fallbackExtension == null) {
            throw new OnFlyVarsUtilException("sanitizedFileName or fallbackExtension can't be null");
        }
        return FileUtil.toSafeFilename(sanitizedFileName, fallbackExtension);
    }

    public static Integer extractNumFromIncs(Incident incident) {
        if (incident == null || incident.incs() == null) {
            return null;
        }
        String[] arr = incident.incs().split("INCS0+");
        Integer numeroIncs = null;
        if (arr != null && arr.length == 2) {
            numeroIncs = Integer.valueOf(arr[1]);
        }
        return numeroIncs;
    }

    public static String createIncPropertiesFileRoute(Path parentPath, Incident incident) {
        return parentPath + File.separator + incident.incs() + File.separator + incident.incs()
                + EvaluatorStaticValues.DOT_TXT;
    }
}
