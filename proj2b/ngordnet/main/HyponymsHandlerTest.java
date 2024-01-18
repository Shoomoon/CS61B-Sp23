package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.ngrams.NGramMap;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class HyponymsHandlerTest {
    private static NGramMap myNgm;
    private static WordNet wordNet;
    private static HyponymsHandler handler;
    @BeforeAll
    public static void init() {
        // creates an NGramMap
        myNgm = new NGramMap("./data/ngrams/top_49887_words.csv",
                "./data/ngrams/total_counts.csv");
        wordNet = new WordNet("data/wordnet/synsets.txt", "data/wordnet/hyponyms.txt");
        handler = new HyponymsHandler(myNgm, wordNet);
    }
    @Test
    public void handleTest() {
        List<String> words = new ArrayList<>(List.of("cake", "food"));
        NgordnetQuery nq = new NgordnetQuery(words, 1950, 1990, 5);
        assertThat(handler.handle(nq).equals("[biscuit, cake, kiss, snap, wafer]"));
    }

}
