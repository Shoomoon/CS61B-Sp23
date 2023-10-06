package ngordnet.main;

import java.util.*;

public class SynsetNode {
    private final int id;
    private final List<String> synonyms;
    private final String definition;

    public SynsetNode(String item) {
        String[] s = item.split(", ?");
        if (s.length != 3) {
            throw new IllegalArgumentException("Invalid synsets!");
        }
        id = Integer.parseInt(s[0]);
        synonyms = Arrays.stream(s[1].trim().split(" +")).toList();
        definition = s[2];
    }
    public int getId() {
        return id;
    }
    public List<String> getSynonyms() {
        return synonyms;
    }
}
