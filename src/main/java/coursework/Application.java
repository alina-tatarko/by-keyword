package coursework;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Application {
    public static void main(String[] args) throws IOException {
        ExcelReader reader = new ExcelReader();
        List<WordFrequency> wordFrequencies = reader.read("D:\\Java\\bykeyword\\keywords.xlsx");
        Words words = new Words(wordFrequencies);
        Optional<String> mostFrequent = words.getMostFrequent();
        // words.getAdditional().forEach(System.out::println);
        // words.getRecommended().forEach(System.out::println);
        words.writeIntoExcel("D:\\Java\\OOP\\Список слов для текста.xlsx");
    }
}
