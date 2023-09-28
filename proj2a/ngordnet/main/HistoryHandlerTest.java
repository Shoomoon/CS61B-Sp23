package ngordnet.main;

import edu.princeton.cs.algs4.In;
import ngordnet.browser.NgordnetQuery;
import ngordnet.ngrams.NGramMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HistoryHandlerTest {
    HistoryHandler hh;

    @BeforeEach
    void setUp() {
        NGramMap ngm = new NGramMap("./data/ngrams/top_14377_words.csv",
                "./data/ngrams/total_counts.csv");
        hh = new HistoryHandler(ngm);
    }

    @Test
    void handle() {
        List<String> words = List.of("cat", "dog");
        In in = new In("data/wordnet/responseOfCatNDog.txt");
        String actualResponse = in.readString();
        int startYear = 1900;
        int endYear = 1950;
        assertThat(hh.handle(new NgordnetQuery(words, startYear, endYear, 5))).isEqualTo(actualResponse);
    }
}