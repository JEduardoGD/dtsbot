package mx.pm.sat.ape1.dtsbot;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.pm.sat.ape1.dtsbot.dto.Incident;
import mx.pm.sat.ape1.dtsbot.service.LoadInsumosService;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class DtsbotApplication implements CommandLineRunner {

    // private final DtsCallService dtsCallService;
    private final LoadInsumosService loadInsumosService;

	public static void main(String[] args) {
		SpringApplication.run(DtsbotApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

        List<String> insumosList = loadInsumosService.loadInsumos();
        if (insumosList != null) {
            for (String excelFile : insumosList) {
                List<Incident> incidentList = loadInsumosService.loadExcelInsumoFile(excelFile);
                incidentList.stream().forEach(i -> log.info(i.toString()));
            }
        }

        // List<Message> listContext = dtsCallService.loadContext();

        // Incident textResponse = dtsCallService.proceessCall(listContext, message);

        // return ResponseEntity.ok(new ResponseDTO<>(200, "SUCCESS", textResponse));

        
        
    }

}
