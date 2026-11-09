package mx.pm.sat.ape1.dtsbot.service.exception;

import java.io.IOException;

import mx.pm.sat.ape1.dtsbot.util.exception.TextUtilException;

public class DtsCallServiceException extends ServiceException {

    private static final long serialVersionUID = -8988537036830241911L;

    public DtsCallServiceException(IOException e) {
        super(e);
    }

    public DtsCallServiceException(TextUtilException e) {
        super(e);
    }

}
