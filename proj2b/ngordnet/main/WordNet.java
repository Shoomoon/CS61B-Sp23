package ngordnet.main;

import edu.princeton.cs.algs4.In;

import java.io.File;
import java.util.*;

public class WordNet {
    private final DirectedGraph<SynsetNode> wordsGraph;
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
        wordsGraph = new DirectedGraph<>();
        wordToNodeIdMap = new HashMap<>();

        In synsetsIn = new In(synsets);
        while (synsetsIn.hasNextLine()) {
            try {
                String item = synsetsIn.readLine();
                SynsetNode node = new SynsetNode(item);
                wordsGraph.addNode(node.getId(), node);
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
                    wordsGraph.addDirectedEdge(curRootNodeId, idNums.get(i));
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }
    public List<String> sortedHyponyms(String word) {
        return sortedHyponyms(List.of(word));
    }
    public List<String> sortedHyponyms(List<String> words) {
        if (words.isEmpty()) {
            return null;
        }
        Set<String> commonHyponyms = hyponyms(words.get(0));
        for (int i = 1; i < words.size(); i++) {
            Set<String> allHyponyms = hyponyms(words.get(i));
            commonHyponyms = commonElements(commonHyponyms, allHyponyms);
            if (commonHyponyms.isEmpty()) {
                break;
            }
        }
        List<String> res = new ArrayList<>(commonHyponyms);
        Collections.sort(res);
        return res;
    }
    private Set<String> commonElements(Set<String> s0, Set<String> s1) {
        if (s0.size() > s1.size()) {
            return commonElements(s1, s0);
        }
        Set<String> commonSet = new HashSet<>();
        for (String word: s0) {
            if (s1.contains(word)) {
                commonSet.add(word);
            }
        }
        return commonSet;
    }
    private Set<String> hyponyms(String word) {
        Set<String> allHyponyms = new HashSet<>();
        if (wordToNodeIdMap.containsKey(word)) {
            Set<Integer> rootNodesId = wordToNodeIdMap.get(word);
            for (SynsetNode node: wordsGraph.traversal(rootNodesId)) {
                allHyponyms.addAll(node.getSynonyms());
            }
        }
        return allHyponyms;
    }
}
