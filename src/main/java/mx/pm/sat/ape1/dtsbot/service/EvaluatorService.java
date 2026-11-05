package mx.pm.sat.ape1.dtsbot.service;

import java.time.LocalDateTime;

import mx.pm.sat.ape1.dtsbot.dto.Incident;
import mx.pm.sat.ape1.dtsbot.dto.IncidentProcessResponse;
import mx.pm.sat.ape1.dtsbot.service.exception.EvaluatorServiceException;

public interface EvaluatorService {

    void process(Incident incident, IncidentProcessResponse incidentProcessResponse, LocalDateTime localDateTime)
            throws EvaluatorServiceException;

}
