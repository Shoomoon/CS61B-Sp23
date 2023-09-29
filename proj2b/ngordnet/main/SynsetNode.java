package ngordnet.main;

import java.util.*;

public class SynsetNode {
    private final int id;
    private final List<String> synonyms;
    private final String definition;
    private final List<SynsetNode> hyponymsNodes;

    public SynsetNode(String item) {
        String[] s = item.split(", ?");
        if (s.length != 3) {
            throw new IllegalArgumentException("Invalid synsets!");
        }
        id = Integer.parseInt(s[0]);
        synonyms = Arrays.stream(s[1].trim().split(" +")).toList();
        definition = s[2];
        hyponymsNodes = new ArrayList<>();
    }
    public void addHyponym(SynsetNode hyponym) {
        hyponymsNodes.add(hyponym);
    }
    public int getId() {
        return id;
    }
    public List<String> getSynonyms() {
        return synonyms;
    }
    public List<SynsetNode> getHyponymsNodes() {
        return hyponymsNodes;
    }
    public List<String> getAllDirectHyponyms() {
        Set<String> res = new HashSet<>();
        getHyponymsHelper(this, res);
        List<String> allHyponyms = new ArrayList<>(res);
        Collections.sort(allHyponyms);
        return allHyponyms;
    }
    private void getHyponymsHelper(SynsetNode node, Set<String> ans) {
        ans.addAll(node.getSynonyms());
        for (SynsetNode hyponym: node.getHyponymsNodes()) {
            getHyponymsHelper(hyponym, ans);
        }
    }
}
