package ngordnet.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

class WordNetTest {
    WordNet wordNet11;
    WordNet wordNet16;
    WordNet wordNet;

    @BeforeEach
    void setUp() {
        String synsets11 = "./data/wordnet/synsets11.txt";
        String hyponyms11 = "./data/wordnet/hyponyms11.txt";
        wordNet11 = new WordNet(synsets11, hyponyms11);


        String synsets16 = "./data/wordnet/synsets16.txt";
        String hyponyms16 = "./data/wordnet/hyponyms16.txt";
        wordNet16 = new WordNet(synsets16, hyponyms16);

        String synsets = "./data/wordnet/synsets.txt";
        String hyponyms = "./data/wordnet/hyponyms.txt";
        wordNet = new WordNet(synsets, hyponyms);
    }

    @Test
    void hyponyms() {
        assertThat(wordNet11.sortedHyponyms("antihistamine")).isEqualTo(List.of("actifed", "antihistamine"));
        assertThat(wordNet11.sortedHyponyms("descent")).isEqualTo(List.of("descent", "jump", "parachuting"));
        assertThat(wordNet11.sortedHyponyms("action")).isEqualTo(List.of("action", "change", "demotion"));

        assertThat(wordNet16.sortedHyponyms("change")).isEqualTo(List.of("alteration", "change", "demotion", "increase", "jump", "leap", "modification", "saltation", "transition", "variation"));
        assertThat(wordNet16.sortedHyponyms("transition")).isEqualTo(List.of("flashback", "jump", "leap", "saltation", "transition"));

        assertThat(wordNet16.sortedHyponyms("activation")).isEmpty();

        assertThat(wordNet11.sortedHyponyms(List.of("increase", "augmentation"))).isEqualTo(List.of("augmentation"));
        assertThat(wordNet11.sortedHyponyms(List.of("increase", "increase"))).isEqualTo(List.of("augmentation", "increase", "jump", "leap"));
        assertThat(wordNet11.sortedHyponyms(List.of("increase", "actifed"))).isEmpty();

        assertThat(wordNet.sortedHyponyms(List.of("video", "recording"))).isEqualTo(List.of("video", "video_recording", "videocassette", "videotape"));
        assertThat(wordNet.sortedHyponyms(List.of("pastry", "tart"))).isEqualTo(List.of("apple_tart", "lobster_tart", "quiche", "quiche_Lorraine", "tart", "tartlet"));
    }
}