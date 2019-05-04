package coursework;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GoogleParser {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";

    public List<String> parse(String mostFrequent) throws IOException {
        List<String> phrases = doParse(mostFrequent);
        return phrases.stream()
                .map(phrase -> phrase.replace(", ", " "))
                .map(phrase -> phrase.split(" "))
                .flatMap(Stream::of)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<String> doParse(String mostFrequent) throws IOException {
        List<String> parsedWords = new ArrayList<>();
        Document doc = Jsoup.connect("https://www.google.com/search?q=" + mostFrequent).userAgent(USER_AGENT).get();
        for (Element result : doc.select("h3.LC20lb")) {
            String title = result.text();
            parsedWords.add(title);
        }
        for (Element result : doc.select("span.st")) {
            String description = result.text();
            parsedWords.add(description);
        }
        for (Element result : doc.select("p.nVcaUb")) {
            String hints = result.text();
            parsedWords.add(hints);
        }
        return parsedWords;
    }
}
