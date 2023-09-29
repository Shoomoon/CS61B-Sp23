package ngordnet.main;

import edu.princeton.cs.algs4.In;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {
    private DirectedGraph wordNet;
    public WordNet(File synsets, File hyponyms) {
        In synsetsIn = new In(synsets);
        while (synsetsIn.hasNextLine()) {
            try {
                String item = synsetsIn.readLine();
                SynsetNode node = new SynsetNode(item);
                wordNet.addNode(node);
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }

        In hyponymsIn = new In(hyponyms);
        while (hyponymsIn.hasNextLine()) {
            String[] ids = hyponymsIn.readLine().split(",");
            List<Integer> idNums = new ArrayList<>();
            for (String id: ids) {
                idNums.add(Integer.parseInt(id));
            }
            int curRootNodeId = idNums.get(0);
            for (int i = 1; i < idNums.size(); i++) {
                wordNet.addEdge(curRootNodeId, idNums.get(i));
            }
        }

    }
}
