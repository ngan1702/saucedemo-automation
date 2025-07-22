package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelUtils {
    public static List<String[]> readTestData(String fileName, String sheetName) {
        List<String[]> data = new ArrayList<>();
        try {
            ClassLoader classLoader = ExcelUtils.class.getClassLoader();
            InputStream is = classLoader.getResourceAsStream("data/" + fileName);
            if (is == null) throw new RuntimeException("Test data file not found");

            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // Skip header

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String[] rowData = new String[row.getLastCellNum()];
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);
                    rowData[i] = (cell == null) ? "" : cell.toString();
                }
                data.add(rowData);
            }

            workbook.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void writeTestResult(String filePath, String sheetName, String testCaseId, String result) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            for (Row row : sheet) {
                Cell idCell = row.getCell(0);
                if (idCell != null && idCell.getStringCellValue().equals(testCaseId)) {
                    Cell resultCell = row.createCell(row.getLastCellNum());
                    resultCell.setCellValue(result);
                    break;
                }
            }

            fis.close();
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            workbook.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
