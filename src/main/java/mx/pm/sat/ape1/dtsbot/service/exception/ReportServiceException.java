package mx.pm.sat.ape1.dtsbot.service.exception;

import java.io.IOException;

public class ReportServiceException extends ServiceException {

    private static final long serialVersionUID = 5382119598726812928L;

    public ReportServiceException(IOException e) {
        super(e);
    }

}
