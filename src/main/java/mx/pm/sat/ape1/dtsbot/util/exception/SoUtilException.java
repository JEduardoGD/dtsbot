package mx.pm.sat.ape1.dtsbot.util.exception;

import java.io.IOException;

public class SoUtilException extends UtilException {

    private static final long serialVersionUID = -6417286704921760886L;

    public SoUtilException(IOException e) {
        super(e);
    }

    public SoUtilException(InterruptedException e) {
        super(e);
    }

}
