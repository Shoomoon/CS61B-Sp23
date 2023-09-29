package ngordnet.main;

import java.util.*;

public class DirectedGraph {
    private Map<Integer, SynsetNode> nodes;
    private Map<String, Set<Integer>> wordToIdMap;
    private Map<Integer, Set<Integer>> edges;
    public DirectedGraph() {
        nodes = new HashMap<>();
        wordToIdMap = new HashMap<>();
        edges = new HashMap<>();
    }
    public void addEdge(int parentId, int childId) {
        if (!edges.containsKey(parentId)) {
            edges.put(parentId, new HashSet<>());
        }
        edges.get(parentId).add(childId);
    }
    public void addNode(SynsetNode node) {
        int id = node.getId();
        if (nodes.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Redundant node id: %d", id));
        }
        nodes.put(id, node);
        for (String synonym: node.getSynonyms()) {
            if (!wordToIdMap.containsKey(synonym)) {
                wordToIdMap.put(synonym, new HashSet<>());
            }
            wordToIdMap.get(synonym).add(id);
        }
    }
    public List<Integer> getChildrenIds(int parentId) {
        return new ArrayList<>(edges.getOrDefault(parentId, new HashSet<>()));
    }
    public List<Integer> getNodeIdsByWord(String word) {
        return new ArrayList<>(wordToIdMap.getOrDefault(word, new HashSet<>()));
    }
}
