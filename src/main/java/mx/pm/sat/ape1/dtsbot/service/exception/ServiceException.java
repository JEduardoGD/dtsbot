package mx.pm.sat.ape1.dtsbot.service.exception;

import java.io.IOException;

import mx.pm.sat.ape1.dtsbot.exception.DtsBotException;
import mx.pm.sat.ape1.dtsbot.util.exception.OnFlyVarsUtilException;
import mx.pm.sat.ape1.dtsbot.util.exception.SoUtilException;
import mx.pm.sat.ape1.dtsbot.util.exception.TextUtilException;

public class ServiceException extends DtsBotException {

    private static final long serialVersionUID = -1209377649257078536L;

    public ServiceException(IOException e) {
        super(e);
    }

    public ServiceException(TextUtilException e) {
        super(e);
    }

    public ServiceException(OnFlyVarsUtilException e) {
        super(e);
    }

    public ServiceException(ReportServiceException e) {
        super(e);
    }

    public ServiceException(CreatePropertiesIncsServiceException e) {
        super(e);
    }

    public ServiceException(SoUtilException e) {
        super(e);
    }

    public ServiceException(String string) {
        super(string);
    }

}
