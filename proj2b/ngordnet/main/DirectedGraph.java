package ngordnet.main;

import java.util.*;

public class DirectedGraph<T> {
    private Map<Integer, T> nodes;
    private Map<Integer, Set<Integer>> edges;
    public DirectedGraph() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }
    public void addDirectedEdge(int parentId, int childId) {
        if (!edges.containsKey(parentId)) {
            edges.put(parentId, new HashSet<>());
        }
        edges.get(parentId).add(childId);
    }
    public void addNode(int id, T node) {
        if (nodes.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Redundant node id: %d", id));
        }
        nodes.put(id, node);
    }
    public T getNode(int id) {
        return nodes.get(id);
    }
    public List<Integer> getChildrenIds(int parentId) {
        return new ArrayList<>(edges.getOrDefault(parentId, new HashSet<>()));
    }
    public List<T> traversal(Collection<Integer> rootIds) {
        List<T> nodesTraversal = new ArrayList<>();
        Set<Integer> visitedNodesId = new HashSet<>();
        for (int rootId: rootIds) {
            nodesTraversal.add(getNode(rootId));
            visitedNodesId.add(rootId);
            traversalHelper(nodesTraversal, visitedNodesId, rootId);
        }
        return nodesTraversal;
    }
    public List<T> traversal(int rootId) {
        return traversal(Set.of(rootId));
    }
    private void traversalHelper(List<T> traversalOrder, Set<Integer> visited, int curNodeId) {
        for (int childId: getChildrenIds(curNodeId)) {
            if (!visited.contains(childId)) {
                traversalOrder.add(getNode(childId));
                visited.add(childId);
                traversalHelper(traversalOrder, visited, childId);
            }
        }
    }
}
