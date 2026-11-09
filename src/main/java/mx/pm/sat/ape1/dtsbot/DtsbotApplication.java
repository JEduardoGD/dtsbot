package mx.pm.sat.ape1.dtsbot;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.ai.chat.messages.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.pm.sat.ape1.dtsbot.dto.Incident;
import mx.pm.sat.ape1.dtsbot.dto.IncidentProcessResponse;
import mx.pm.sat.ape1.dtsbot.service.DtsCallService;
import mx.pm.sat.ape1.dtsbot.service.EvaluatorService;
import mx.pm.sat.ape1.dtsbot.service.LoadInsumosService;
import mx.pm.sat.ape1.dtsbot.service.exception.DtsCallServiceException;
import mx.pm.sat.ape1.dtsbot.service.exception.EvaluatorServiceException;
import mx.pm.sat.ape1.dtsbot.util.EnvironmentService;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class DtsbotApplication implements CommandLineRunner {

    private final LoadInsumosService loadInsumosService;
    private final DtsCallService dtsCallService;
    private final EvaluatorService evaluatorService;
    private final EnvironmentService environmentService;

    private static final String DEFAULT_REASONING = "RAZONAMIENTO INTELIGENCIA MOCK: El mensaje reporta lentitud extrema en el aplicativo CertiSat CRM, con tiempos superiores a 10 minutos para revocar y generar certificados, lo que corresponde directamente a la categoría \"lentitud en certisat\"";

    public static void main(String[] args) {
        SpringApplication.run(DtsbotApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> insumosList = loadInsumosService.loadInsumos();
        if (insumosList != null) {
            for (String excelFile : insumosList) {
                LocalDateTime localDateTime = LocalDateTime.now();
                log.debug("loading insumos...");
                List<Incident> incidentList = loadInsumosService.loadExcelInsumoFile(excelFile);
                if(incidentList == null){
                    throw new Exception("insumos is null");
                }
                log.info("fond {} insumos", incidentList.size());

                List<Message> messagesContextList;
                try {
                    messagesContextList = dtsCallService.loadContext();
                } catch (DtsCallServiceException e) {
                    log.error(e.getMessage());
                    return;
                }
                
                if(messagesContextList == null) {
                    log.error("messagesContextList is null");
                    return;
                }
                
                if(messagesContextList.isEmpty()) {
                    log.warn("messagesContextList is empty");
                    return;
                }
                
                log.info("Message context size: {}", messagesContextList.size());

                short i = 1;
                for (Incident incident : incidentList) {
                    log.info("Processing: {} / {}: {}", i++, incidentList.size(), incident.originalText());
                    IncidentProcessResponse incidentProcessResponse;
                    if (!environmentService.isUseFakeIa()) {
                        incidentProcessResponse = dtsCallService.proceessCall(messagesContextList, incident);
                    } else {
                        incidentProcessResponse = new IncidentProcessResponse(DEFAULT_REASONING,
                                BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0.1, 0.99)));
                    }
                    try {
                        evaluatorService.process(incident, incidentProcessResponse, localDateTime);
                    } catch (EvaluatorServiceException e) {
                        // TODO Auto-generated catch block
                        throw new Exception(e);
                    }
                }
            }
        }
    }
}
