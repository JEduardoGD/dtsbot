package mx.pm.sat.ape1.dtsbot.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.pm.sat.ape1.dtsbot.dto.Incident;
import mx.pm.sat.ape1.dtsbot.service.LoadInsumosService;

@Service
@Slf4j
public class LoadInsumosServiceImpl implements LoadInsumosService {

    @Value("${insumos.loation}")
    private String insumosLocation;

    private static final String XLS = ".xls";
    private static final String XLSX = ".xlsx";
    private static final String FILE_ERROR_NOT_EXISTS = "file does not exists: {}";
    private static final String FILE_ERROR_CANT_READ = "file can't read: {}";
    
    private static final int SHEET_NUM_DATA = 0;
    private static final int START_ROW_NUM_DATA = 1;
    private static final int APLICATIVE_COL_NUM = 0;
    private static final int ORIGINAL_TEXT_COL_NUM = 1;

    @Override
    public List<String> loadInsumos() {
        List<String> fileList = Stream.of(new File(insumosLocation).listFiles()).filter(file -> !file.isDirectory())
                .map(File::getName).collect(Collectors.toList());
        if (fileList != null) {
            return fileList.stream().filter(f -> f.toLowerCase().endsWith(XLS) || f.toLowerCase().endsWith(XLSX))
                    .toList();
        }
        return null;
    }

    @Override
    public List<Incident> loadExcelInsumoFile(String excelFile) {
        List<Incident> incidentList = new ArrayList<>();

        Path path = Paths.get(insumosLocation + File.separator + excelFile);
        File file = path.toFile();
        if (!file.exists()) {
            log.error(FILE_ERROR_NOT_EXISTS, excelFile);
        }
        if (!file.canRead()) {
            log.error(FILE_ERROR_CANT_READ, excelFile);
        }

        try (java.io.InputStream is = new FileInputStream(path.toFile());
                org.apache.poi.ss.usermodel.Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(SHEET_NUM_DATA);
            for (int rowCount = START_ROW_NUM_DATA; rowCount <= sheet.getLastRowNum(); rowCount++) {
                Row row = sheet.getRow(rowCount);
                Cell cell;

                cell = row.getCell(APLICATIVE_COL_NUM);
                String aplicative = cell.getStringCellValue();

                String reqs = null;

                //String incs = null;
                String incs = cell.getRowIndex() + "";
                
                cell = row.getCell(ORIGINAL_TEXT_COL_NUM);
                String originalText = cell.getStringCellValue();

                incidentList.add(new Incident(excelFile, aplicative, reqs, incs, originalText));
            }
        } catch (IOException e) {

        }

        return incidentList;
    }
}
