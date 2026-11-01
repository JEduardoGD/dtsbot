package mx.pm.sat.ape1.dtsbot.service;

import java.io.File;

import mx.pm.sat.ape1.dtsbot.service.exception.ReportServiceException;

public interface ReportService {

    void appendToReport(File file, String content) throws ReportServiceException;

}
