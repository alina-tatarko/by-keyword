package coursework;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Words {
    private final List<WordFrequency> frequencies;

    Words(List<WordFrequency> frequencies) {
        this.frequencies = frequencies;
    }

    public List<String> getThreeMostFrequent() {
        return frequencies.stream()
                .sorted(Comparator.comparingDouble(WordFrequency::getFrequency).reversed())
                .limit(3)
                .map(WordFrequency::getWord)
                .collect(Collectors.toList());
    }

    public List<String> getRecommended() {
        return frequencies.stream()
                .map(WordFrequency::getWord)
                .map(word -> word.replace(", ", " "))
                .map(word -> word.split(" "))
                .flatMap(Stream::of)
                .distinct()
                .collect(Collectors.toList());
    }

    public Optional<String> getMostFrequent() {
        return frequencies.stream()
                .max(Comparator.comparingDouble(WordFrequency::getFrequency))
                .map(WordFrequency::getWord);
    }

    public List<String> getAdditional() throws IOException {
        GoogleParser googleParser = new GoogleParser();
        if (getMostFrequent().isEmpty()) {
            return Collections.emptyList();
        }
        return googleParser.parse(getMostFrequent().get());
    }

    public void writeIntoExcel(String file) throws IOException {
        XSSFWorkbook outWorkbook = new XSSFWorkbook();
        XSSFSheet mostFrSheet = outWorkbook.createSheet("Most frequent phrases");
        int rowCount1 = 0;
        for (String threeMostFr : getThreeMostFrequent()) {
            Row row = mostFrSheet.createRow(rowCount1++);
            Cell cell = row.createCell(0);
            cell.setCellValue(threeMostFr);
        }
        XSSFSheet recomSheet = outWorkbook.createSheet("Recommended words");
        int rowCount2 = 0;
        for (String recommended : getRecommended()) {
            Row row = recomSheet.createRow(rowCount2++);
            Cell cell = row.createCell(0);
            cell.setCellValue(recommended);
        }
        XSSFSheet addSheet = outWorkbook.createSheet("Additional words");
        int rowCount3 = 0;
        for (String additional : getAdditional()) {
            Row row = addSheet.createRow(rowCount3++);
            Cell cell = row.createCell(0);
            cell.setCellValue(additional);
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        outWorkbook.write(outputStream);
    }
}

