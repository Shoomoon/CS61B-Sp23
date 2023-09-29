package ngordnet.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {
    DirectedGraph graph;

    @BeforeEach
    void setUp() {
        graph = new DirectedGraph();
        SynsetNode cat = new SynsetNode("0, cat, animal cat");
        SynsetNode dog = new SynsetNode("1,  dog goat, animal dog");
        SynsetNode cow = new SynsetNode("2, cow   bull, animal cow");
        SynsetNode sheep = new SynsetNode("3, sheep goat  cat, animal sheep");
        graph.addNode(cat);
        graph.addNode(dog);
        graph.addNode(cow);
        graph.addNode(sheep);
        graph.addEdge(0, 1);
        graph.addEdge(2, 3);

    }

    @Test
    void addEdge() {
        assertThat(graph.getChildrenIds(0)).isEqualTo(List.of(1));
        assertThat(graph.getChildrenIds(1)).isEmpty();
        assertThat(graph.getChildrenIds(2)).isEqualTo(List.of(3));
        assertThat(graph.getChildrenIds(3)).isEmpty();
    }

    @Test
    void addNode() {
        assertThat(graph.getNodeIdsByWord("cat")).isEqualTo(List.of(0, 3));
        assertThat(graph.getNodeIdsByWord("dog")).isEqualTo(List.of(1));
        assertThat(graph.getNodeIdsByWord("cow")).isEqualTo(List.of(2));
        assertThat(graph.getNodeIdsByWord("bull")).isEqualTo(List.of(2));
        assertThat(graph.getNodeIdsByWord("sheep")).isEqualTo(List.of(3));
        assertThat(graph.getNodeIdsByWord("goat")).isEqualTo(List.of(1, 3));
    }
}