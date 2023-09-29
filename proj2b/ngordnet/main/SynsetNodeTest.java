package ngordnet.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SynsetNodeTest {
    SynsetNode cat;
    SynsetNode dog;
    SynsetNode cow;
    SynsetNode sheep;

    @BeforeEach
    void setUp() {
        cat = new SynsetNode("0, cat, animal cat");
        dog = new SynsetNode("1,  dog, animal dog");
        cow = new SynsetNode("2, cow   bull, animal cow");
        sheep = new SynsetNode("3, sheep goat, animal sheep");
    }

    @Test
    void addHyponym() {
    }

    @Test
    void getId() {
        assertThat(cat.getId()).isEqualTo(0);
        assertThat(dog.getId()).isEqualTo(1);
        assertThat(cow.getId()).isEqualTo(2);
        assertThat(sheep.getId()).isEqualTo(3);
    }

    @Test
    void getSynonyms() {
        assertThat(cat.getSynonyms()).isEqualTo(List.of("cat"));
        assertThat(dog.getSynonyms()).isEqualTo(List.of("dog"));
        assertThat(cow.getSynonyms()).isEqualTo(List.of("cow", "bull"));
        assertThat(sheep.getSynonyms()).isEqualTo(List.of("sheep", "goat"));
    }

}