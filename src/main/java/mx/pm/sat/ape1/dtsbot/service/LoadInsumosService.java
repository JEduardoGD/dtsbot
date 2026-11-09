package mx.pm.sat.ape1.dtsbot.service;

import java.util.List;

import mx.pm.sat.ape1.dtsbot.dto.Incident;

public interface LoadInsumosService {

    List<String> loadInsumos();

    List<Incident> loadExcelInsumoFile(String excelFile);

}
