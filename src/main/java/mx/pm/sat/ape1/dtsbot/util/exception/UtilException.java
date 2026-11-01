package mx.pm.sat.ape1.dtsbot.util.exception;

import java.io.IOException;

import mx.pm.sat.ape1.dtsbot.exception.DtsBotException;

public class UtilException extends DtsBotException {

    private static final long serialVersionUID = -6183667102560721330L;

    public UtilException(IOException e) {
        super(e);
    }

}
