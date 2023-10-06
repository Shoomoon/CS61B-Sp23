package ngordnet.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WordNetTest {
    WordNet wordNet11;
    WordNet wordNet16;

    @BeforeEach
    void setUp() {
        String synsets11 = "./data/wordnet/synsets11.txt";
        String hyponyms11 = "./data/wordnet/hyponyms11.txt";
        wordNet11 = new WordNet(synsets11, hyponyms11);


        String synsets16 = "./data/wordnet/synsets16.txt";
        String hyponyms16 = "./data/wordnet/hyponyms16.txt";
        wordNet16 = new WordNet(synsets16, hyponyms16);
    }

    @Test
    void hyponyms() {
        assertThat(wordNet11.hyponyms("antihistamine")).isEqualTo(List.of("actifed", "antihistamine"));
        assertThat(wordNet11.hyponyms("descent")).isEqualTo(List.of("descent", "jump", "parachuting"));
        assertThat(wordNet11.hyponyms("action")).isEqualTo(List.of("action", "change", "demotion"));

        assertThat(wordNet16.hyponyms("change")).isEqualTo(List.of("alteration", "change", "demotion", "increase", "jump", "leap", "modification", "saltation", "transition", "variation"));
        assertThat(wordNet16.hyponyms("transition")).isEqualTo(List.of("flashback", "jump", "leap", "saltation", "transition"));

        assertThat(wordNet16.hyponyms("activation")).isEmpty();

    }
}