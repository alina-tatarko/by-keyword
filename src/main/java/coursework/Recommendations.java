package coursework;

import java.util.List;

public class Recommendations {
    private final List<String> threeMostFrequent;
    private final List<String> additional;
    private final List<String> recommended;

    public Recommendations(List<String> threeMostFrequent, List<String> additional, List<String> recommended) {
        this.threeMostFrequent = threeMostFrequent;
        this.additional = additional;
        this.recommended = recommended;
    }

    public List<String> getThreeMostFrequent() {
        return threeMostFrequent;
    }

    public List<String> getAdditional() {
        return additional;
    }

    public List<String> getRecommended() {
        return recommended;
    }
}
