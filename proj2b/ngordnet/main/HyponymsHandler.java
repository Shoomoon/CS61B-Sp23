package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

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
        return hyponyms.toString();
    }
}
