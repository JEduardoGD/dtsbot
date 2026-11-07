package mx.pm.sat.ape1.dtsbot.util.exception;

import java.io.IOException;

import mx.pm.sat.ape1.dtsbot.exception.DtsBotException;

public abstract class UtilException extends RuntimeException {

    private static final long serialVersionUID = -6183667102560721330L;

    public UtilException(Exception e) {
        super(e);
    }

    public UtilException(String mgs) {
        super(mgs);
    }
}
