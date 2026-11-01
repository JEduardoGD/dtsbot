package mx.pm.sat.ape1.dtsbot.exception;

import java.io.IOException;

import mx.pm.sat.ape1.dtsbot.util.exception.TextUtilException;

public class DtsBotException extends Throwable {

    private static final long serialVersionUID = -6276873269626928324L;

    public DtsBotException(IOException e) {
        super(e);
    }

    public DtsBotException(TextUtilException e) {
        super(e);
    }

}
