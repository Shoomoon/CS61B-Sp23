package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final NGramMap myMap;
    private final WordNet myWordNet;

    public HyponymsHandler(NGramMap ngm, WordNet wordNet) {
        this.myMap = ngm;
        this.myWordNet = wordNet;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> hyponyms = myWordNet.sortedHyponyms(q.words());
        List<Integer> totalCountsOfHyponyms = new ArrayList<>();
        for (String hyponym: hyponyms) {
            TimeSeries ts = myMap.countHistory(hyponym, q.startYear(), q.endYear());
            totalCountsOfHyponyms.add(countOccurrence(ts));
        }
        if (q.k() > 0) {
            PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return totalCountsOfHyponyms.get(o1) - totalCountsOfHyponyms.get(o2);
                }
            });
            for (int i = 0; i < hyponyms.size(); i++) {
                if (totalCountsOfHyponyms.get(i) != 0) {
                    pq.add(i);
                    if (pq.size() > q.k()) {
                        pq.poll();
                    }
                }
            }
            List<String> res = new ArrayList<>();
            while (!pq.isEmpty()) {
                int index = pq.poll();
                res.add(hyponyms.get(index));
            }
            Collections.sort(res);
            return res.toString();
        }
        return hyponyms.toString();
    }
    private int countOccurrence(TimeSeries ts) {
        double totalCount = 0;
        for (double value: ts.values()) {
            totalCount += value;
        }
        return (int)totalCount;
    }
}
