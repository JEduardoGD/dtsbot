package mx.pm.sat.ape1.dtsbot.exception;

import java.io.IOException;

import mx.pm.sat.ape1.dtsbot.service.exception.CreatePropertiesIncsServiceException;
import mx.pm.sat.ape1.dtsbot.service.exception.ReportServiceException;
import mx.pm.sat.ape1.dtsbot.util.exception.OnFlyVarsUtilException;
import mx.pm.sat.ape1.dtsbot.util.exception.SoUtilException;
import mx.pm.sat.ape1.dtsbot.util.exception.TextUtilException;

public class DtsBotException extends Throwable {

    private static final long serialVersionUID = -6276873269626928324L;

    public DtsBotException(IOException e) {
        super(e);
    }

    public DtsBotException(TextUtilException e) {
        super(e);
    }

    public DtsBotException(String string) {
        super(string);
    }

    public DtsBotException(OnFlyVarsUtilException e) {
        super(e);
    }

    public DtsBotException(ReportServiceException e) {
        super(e);
    }

    public DtsBotException(CreatePropertiesIncsServiceException e) {
        super(e);
    }

    public DtsBotException(InterruptedException e) {
        super(e);
    }

    public DtsBotException(SoUtilException e) {
        super(e);
    }

}
