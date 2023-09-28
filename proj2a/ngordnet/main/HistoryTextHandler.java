package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.ArrayList;
import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private final NGramMap myMap;
    public HistoryTextHandler(NGramMap map) {
        myMap = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> res = new ArrayList<>();
        for (String word: q.words()) {
            TimeSeries ts = myMap.weightHistory(word, q.startYear(), q.endYear());
            res.add(word + ": " + ts.toString());
        }
        return String.join("\n", res);
    }
}
