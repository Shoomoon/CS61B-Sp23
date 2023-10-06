package ngordnet.main;

import edu.princeton.cs.algs4.In;
import org.checkerframework.checker.units.qual.A;
import org.reflections.vfs.Vfs;

import java.io.File;
import java.util.*;

public class WordNet {
    private final DirectedGraph<SynsetNode> wordNet;
    private final Map<String, Set<Integer>> wordToNodeIdMap;
    public WordNet(String synsets, String hyponyms) {
        this(new File(synsets), new File(hyponyms));
    }
    public WordNet(File synsets, String hyponyms) {
        this(synsets, new File(hyponyms));
    }

    public WordNet(String synsets, File hyponyms) {
        this(new File(synsets), hyponyms);
    }
    public WordNet(File synsets, File hyponyms) {
        wordNet = new DirectedGraph<>();
        wordToNodeIdMap = new HashMap<>();

        In synsetsIn = new In(synsets);
        while (synsetsIn.hasNextLine()) {
            try {
                String item = synsetsIn.readLine();
                SynsetNode node = new SynsetNode(item);
                wordNet.addNode(node.getId(), node);
                for (String synonym: node.getSynonyms()) {
                    if (!wordToNodeIdMap.containsKey(synonym)) {
                        wordToNodeIdMap.put(synonym, new HashSet<>());
                    }
                    wordToNodeIdMap.get(synonym).add(node.getId());
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }

        In hyponymsIn = new In(hyponyms);
        while (hyponymsIn.hasNextLine()) {
            try {
                String[] ids = hyponymsIn.readLine().split(",");
                List<Integer> idNums = new ArrayList<>();
                for (String id: ids) {
                    idNums.add(Integer.parseInt(id));
                }
                int curRootNodeId = idNums.get(0);
                for (int i = 1; i < idNums.size(); i++) {
                    wordNet.addDirectedEdge(curRootNodeId, idNums.get(i));
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }
    public List<String> hyponyms(String word) {
        Set<String> allHyponyms = new HashSet<>();
        Set<Integer> rootsId = wordToNodeIdMap.getOrDefault(word, new HashSet<>());
        for (SynsetNode node: wordNet.traversal(rootsId)) {
            allHyponyms.addAll(node.getSynonyms());
        }
        List<String> res = new ArrayList<>(allHyponyms);
        Collections.sort(res);
        return res;
    }
}
