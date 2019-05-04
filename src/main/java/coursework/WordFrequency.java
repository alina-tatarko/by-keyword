package coursework;

public class WordFrequency {
    private final String word;
    private final double frequency;

    public WordFrequency(String word, double frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public double getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return "WordFrequency{" +
                "word='" + word + '\'' +
                ", frequency=" + frequency +
                '}';
    }
}
