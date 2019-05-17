package coursework;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelIO {

    public static List<WordFrequency> read(String frequenciesFile) throws IOException {
        File excelFile = new File(frequenciesFile);
        try (FileInputStream fis = new FileInputStream(excelFile);
             XSSFWorkbook workbook = new XSSFWorkbook(new BufferedInputStream(fis))) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            List<WordFrequency> wordFrequencies = new ArrayList<>();
            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    String word = cellIterator.next().getStringCellValue();
                    double frequency = cellIterator.next().getNumericCellValue();
                    WordFrequency wordFrequency = new WordFrequency(word, frequency);
                    wordFrequencies.add(wordFrequency);
                }
            }
            return wordFrequencies;
        }
    }

    public static void writeIntoExcel(String file, Recommendations recommendations) throws IOException {
        try (XSSFWorkbook outWorkbook = new XSSFWorkbook()) {
            XSSFSheet mostFrSheet = outWorkbook.createSheet("Most frequent phrases");
            int rowCount1 = 0;
            for (String threeMostFr : recommendations.getThreeMostFrequent()) {
                Row row = mostFrSheet.createRow(rowCount1++);
                Cell cell = row.createCell(0);
                cell.setCellValue(threeMostFr);
            }
            XSSFSheet recomSheet = outWorkbook.createSheet("Recommended words");
            int rowCount2 = 0;
            for (String recommended : recommendations.getRecommended()) {
                Row row = recomSheet.createRow(rowCount2++);
                Cell cell = row.createCell(0);
                cell.setCellValue(recommended);
            }
            XSSFSheet addSheet = outWorkbook.createSheet("Additional words");
            int rowCount3 = 0;
            for (String additional : recommendations.getAdditional()) {
                Row row = addSheet.createRow(rowCount3++);
                Cell cell = row.createCell(0);
                cell.setCellValue(additional);
            }
            try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
                outWorkbook.write(outputStream);
            }
        }
    }
}
