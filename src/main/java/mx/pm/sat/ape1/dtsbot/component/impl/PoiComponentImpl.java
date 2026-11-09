package mx.pm.sat.ape1.dtsbot.component.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import mx.pm.sat.ape1.dtsbot.component.PoiComponent;

public class PoiComponentImpl implements PoiComponent {
    private void test(Cell cell) {
        switch (cell.getCellType()) {
        case STRING:
            String strVal = cell.getStringCellValue();
            // Attempt to parse string to numeric safely
            double valFromString = (strVal != null && !strVal.isEmpty()) ? Double.parseDouble(strVal) : 0.0;
            break;

        case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
                System.out.println("Date value: " + cell.getDateCellValue());
            } else {
                double valFromNumeric = cell.getNumericCellValue();
            }
            break;

        case FORMULA:
            // Handle formulas based on their evaluated result type
            switch (cell.getCachedFormulaResultType()) {
            case STRING:
                System.out.println(cell.getStringCellValue());
                break;
            case NUMERIC:
                System.out.println(cell.getNumericCellValue());
                break;
            }
            break;

        case BLANK:
        default:
            // Handle blank or boolean cells safely
            break;
        }

    }
}
