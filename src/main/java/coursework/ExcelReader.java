package coursework;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    public List<WordFrequency> read(String frequenciesFile) throws IOException {
        File excelFile = new File(frequenciesFile);
        FileInputStream fis = new FileInputStream(excelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<WordFrequency> wordFrequencies = new ArrayList<WordFrequency>();
        for (Row row : sheet) {
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                String word = cellIterator.next().getStringCellValue();
                double frequency = cellIterator.next().getNumericCellValue();
                WordFrequency wordFrequency = new WordFrequency(word, frequency);
                wordFrequencies.add(wordFrequency);
            }
        }
        workbook.close();
        fis.close();
        return wordFrequencies;
    }
}
