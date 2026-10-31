package mx.pm.sat.ape1.dtsbot.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.pm.sat.ape1.dtsbot.service.LoadInsumosService;

@Service
public class LoadInsumosServiceImpl implements LoadInsumosService {

    @Value("${insumos.loation}")
    private String insumosLocation;

    private static final String XLS = ".xls";
    private static final String XLSX = ".xlsx";

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
    
    public void loadExcelFile(String filePath) {
        try (FileInputStream fis = new FileInputStream("sample.xlsx");
                XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            
        } catch (IOException e) {
        }
    }
}
