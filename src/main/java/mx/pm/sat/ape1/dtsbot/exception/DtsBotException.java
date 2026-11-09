package mx.pm.sat.ape1.dtsbot.exception;

import java.io.IOException;

import mx.pm.sat.ape1.dtsbot.service.exception.CreatePropertiesIncsServiceException;
import mx.pm.sat.ape1.dtsbot.service.exception.ReportServiceException;
import mx.pm.sat.ape1.dtsbot.util.exception.OnFlyVarsUtilException;
import mx.pm.sat.ape1.dtsbot.util.exception.SoUtilException;
import mx.pm.sat.ape1.dtsbot.util.exception.TextUtilException;
import mx.pm.sat.ape1.dtsbot.util.exception.UtilException;

public class DtsBotException extends Throwable {

    private static final long serialVersionUID = -6276873269626928324L;

    public DtsBotException(UtilException e) {
        super(e);
    }

    public DtsBotException(String msg) {
        super(msg);
    }
}
