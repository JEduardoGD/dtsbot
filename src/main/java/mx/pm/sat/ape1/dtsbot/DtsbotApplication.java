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

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class DtsbotApplication implements CommandLineRunner {

    private final LoadInsumosService loadInsumosService;
    private final DtsCallService dtsCallService;
    private final EvaluatorService evaluatorService;

    public static void main(String[] args) {
        SpringApplication.run(DtsbotApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> insumosList = loadInsumosService.loadInsumos();
        if (insumosList != null) {
            for (String excelFile : insumosList) {
                LocalDateTime localDateTime = LocalDateTime.now();
                List<Incident> incidentList = loadInsumosService.loadExcelInsumoFile(excelFile);
                incidentList.stream().forEach(i -> log.info(i.toString()));

                List<Message> messagesContextList;
                try {
                    messagesContextList = dtsCallService.loadContext();
                } catch (DtsCallServiceException e) {
                    log.error(e.getMessage());
                    return;
                }

                for (Incident incident : incidentList) {
                    // IncidentProcessResponse incidentProcessResponse =
                    // dtsCallService.proceessCall(messagesContextList, incident);
                    IncidentProcessResponse incidentProcessResponse = new IncidentProcessResponse(
                            """
                                    El mensaje reporta lentitud extrema en el aplicativo CertiSat CRM, con tiempos superiores a 10 minutos para revocar y generar certificados, lo que corresponde directamente a la categoría "lentitud en certisat"
                                    """,
                            BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0.1, 0.99)));
                    evaluatorService.process(incident, incidentProcessResponse, localDateTime);
                }
            }
        }
    }
}
