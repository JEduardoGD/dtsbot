package mx.pm.sat.ape1.dtsbot.service.exception;

import java.io.IOException;

import mx.pm.sat.ape1.dtsbot.exception.DtsBotException;
import mx.pm.sat.ape1.dtsbot.util.exception.OnFlyVarsUtilException;
import mx.pm.sat.ape1.dtsbot.util.exception.SoUtilException;
import mx.pm.sat.ape1.dtsbot.util.exception.TextUtilException;

public class ServiceException extends RuntimeException {

    private static final long Trhow = -1209377649257078536L;


    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String mgs) {
        super(mgs);
    }
}
