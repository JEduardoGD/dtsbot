package mx.pm.sat.ape1.dtsbot.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import mx.pm.sat.ape1.dtsbot.dto.Incident;
import mx.pm.sat.ape1.dtsbot.service.CreatePropertiesIncsService;
import mx.pm.sat.ape1.dtsbot.service.exception.CreatePropertiesIncsServiceException;
import mx.pm.sat.ape1.dtsbot.util.OnFlyVarsUtil;

@Service
public class CreatePropertiesIncsServiceImpl implements CreatePropertiesIncsService {
    //private static final String DTSBOT_PY_LOCATION = "dtsbot_py";
    //private static final String DOT_TXT = ".txt";
    //private static final String DOT_DOCX = ".docx";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void createIncsPropertiesFile(Path parentPath, Incident incident) throws CreatePropertiesIncsServiceException {

        int numeroIncs = OnFlyVarsUtil.extractNumFromIncs(incident);

        List<String> lines = new ArrayList<>();

        lines.add(String.format("número: %s", numeroIncs));
        lines.add(String.format("IDENTIFICACION_DEL_PROBLEMA: %s", incident.originalText()));
        lines.add(String.format("FECHA_ANALISIS: %s", LocalDate.now().format(formatter)));

        try {
            Path path = Paths.get(OnFlyVarsUtil.createIncPropertiesFileRoute(parentPath, incident));
            Files.createDirectories(path.getParent());
            Files.write(path, lines);
        } catch (IOException e) {
            throw new CreatePropertiesIncsServiceException(e);
        }

        /*
        String outputFile = incident.incs() + File.separator + incident.incs() + DOT_DOCX;
        String templateFile = DTSBOT_PY_LOCATION + File.separator + "examples" + File.separator + "NNNNNNN_DTS.docx";
        modifyWordFile(properties, templateFile, outputFile);
        */
    }

    /*

    private void modifyWordFile(Properties properties, String templateFile, String outputFile) {

        try (FileInputStream fis = new FileInputStream(templateFile); XWPFDocument document = new XWPFDocument(fis)) {

            // Iterate through all paragraphs in the document
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                List<XWPFRun> runs = paragraph.getRuns();
                if (runs != null) {
                    for (XWPFRun run : runs) {
                        for (int runNumber = 0; runNumber < run.getNumberOfTexts(); runNumber++) {
                            String text = run.getText(runNumber);

                            for (Object keyObj : properties.keySet()) {
                                String key = null;
                                if (keyObj != null && keyObj instanceof String) {
                                    key = (String) keyObj;
                                }
                                if (key == null) {
                                    continue;
                                }
                                // Check if the target text exists in this run
                                if (text != null && text.contains(String.format("[%s]", key))) {
                                    String value = properties.getProperty(key);
                                    // Replace the text
                                    text = text.replace(String.format("[%s]", key), value);
                                    run.setText(text, runNumber); // 0 specifies the position in the run

                                    // Optional: Update styling of the modified text
                                    // run.setBold(true);
                                    // run.setColor("FF0000"); // Red hex code
                                }
                            }
                        }
                    }
                }
            }


            Path parentPath = Paths.get(outputFile).getParent();
            Files.createDirectories(parentPath);

            // Save the modifications to a new file
            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                document.write(fos);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
