package mx.pm.sat.ape1.dtsbot.service.exception;

import mx.pm.sat.ape1.dtsbot.util.exception.OnFlyVarsUtilException;
import mx.pm.sat.ape1.dtsbot.util.exception.SoUtilException;

public class EvaluatorServiceException extends ServiceException {

    private static final long serialVersionUID = -9075696235101242417L;

    public EvaluatorServiceException(OnFlyVarsUtilException e) {
        super(e);
    }

    public EvaluatorServiceException(ReportServiceException e) {
        super(e);
    }

    public EvaluatorServiceException(CreatePropertiesIncsServiceException e) {
        super(e);
    }

    public EvaluatorServiceException(SoUtilException e) {
        super(e);
    }

}
