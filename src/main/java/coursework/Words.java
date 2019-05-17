package coursework;

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
        GoogleSearcher googleSearcher = new GoogleSearcher();
        if (getMostFrequent().isEmpty()) {
            return Collections.emptyList();
        }
        return googleSearcher.findMostPopularResults(getMostFrequent().get());
    }

    public void writeIntoExcel(String file) throws IOException {
        Recommendations recommendations = new Recommendations(
                getThreeMostFrequent(),
                getAdditional(),
                getRecommended());
        ExcelIO.writeIntoExcel(file, recommendations);
    }
}

