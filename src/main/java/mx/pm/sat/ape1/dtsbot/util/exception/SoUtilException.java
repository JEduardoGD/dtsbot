package mx.pm.sat.ape1.dtsbot.util.exception;

import com.openai.models.beta.threads.runs.Run;

import java.io.IOException;

public class SoUtilException extends UtilException {

    private static final long serialVersionUID = -6417286704921760886L;

    public SoUtilException(Exception e) {
        super(e);
    }
}
