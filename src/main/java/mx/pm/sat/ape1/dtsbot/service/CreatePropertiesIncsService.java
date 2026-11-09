package mx.pm.sat.ape1.dtsbot.service;

import java.nio.file.Path;

import mx.pm.sat.ape1.dtsbot.dto.Incident;
import mx.pm.sat.ape1.dtsbot.dto.IncidentProcessResponse;
import mx.pm.sat.ape1.dtsbot.service.exception.CreatePropertiesIncsServiceException;

public interface CreatePropertiesIncsService {

    void createIncsPropertiesFile(Path parentPath, Incident incident, IncidentProcessResponse incidentProcessResponse) throws CreatePropertiesIncsServiceException;


}
