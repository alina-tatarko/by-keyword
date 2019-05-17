package coursework;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Application {
    public static void main(String[] args) throws IOException {
        GoogleSearcher googleSearcher = new GoogleSearcher();
        List<String> bakeryMachine = googleSearcher.findMostPopularResults("bakeryMachine");
        System.out.println(bakeryMachine);


        List<WordFrequency> wordFrequencies = ExcelIO.read("D:\\Java\\bykeyword\\keywords.xlsx");
        Words words = new Words(wordFrequencies);
        Optional<String> mostFrequent = words.getMostFrequent();
        // words.getAdditional().forEach(System.out::println);
        // words.getRecommended().forEach(System.out::println);
        words.writeIntoExcel("D:\\Java\\OOP\\Список слов для текста.xlsx");
    }
}
