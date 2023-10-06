package ngordnet.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

class DirectedGraphTest {
    DirectedGraph<SynsetNode> graph;

    @BeforeEach
    void setUp() {
        graph = new DirectedGraph<>();
        SynsetNode cat = new SynsetNode("0, cat, animal cat");
        SynsetNode dog = new SynsetNode("1,  dog goat, animal dog");
        SynsetNode cow = new SynsetNode("2, cow   bull, animal cow");
        SynsetNode sheep = new SynsetNode("3, sheep goat  cat, animal sheep");
        graph.addNode(0, cat);
        graph.addNode(1, dog);
        graph.addNode(2, cow);
        graph.addNode(3, sheep);
        graph.addDirectedEdge(0, 1);
        graph.addDirectedEdge(2, 3);

    }

    @Test
    void testGetChildrenIds() {
        assertThat(graph.getChildrenIds(0)).isEqualTo(List.of(1));
        assertThat(graph.getChildrenIds(1)).isEmpty();
        assertThat(graph.getChildrenIds(2)).isEqualTo(List.of(3));
        assertThat(graph.getChildrenIds(3)).isEmpty();
    }


    @Test
    void testTraversal() {
        assertThat(graph.traversal(0)).isEqualTo(List.of(graph.getNode(0), graph.getNode(1)));
        assertThat(graph.traversal(1)).isEqualTo(List.of(graph.getNode(1)));
        assertThat(graph.traversal(2)).isEqualTo(List.of(graph.getNode(2), graph.getNode(3)));
        assertThat(graph.traversal(3)).isEqualTo(List.of(graph.getNode(3)));

    }
}