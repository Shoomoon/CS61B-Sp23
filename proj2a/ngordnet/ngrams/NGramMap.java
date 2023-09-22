package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    // TODO: Add any necessary static/instance variables.
    private final Map<String, TimeSeries> wordsCountHistory;
    private final TimeSeries totalWordsCount;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: Fill in this constructor. See the "NGramMap Tips" section of the spec for help.
        wordsCountHistory = new HashMap<>();
        totalWordsCount = new TimeSeries();
        In wordsFileIn = new In(wordsFilename);
        while (wordsFileIn.hasNextLine()) {
            String word = wordsFileIn.readString();
            int year = wordsFileIn.readInt();
            double count = wordsFileIn.readDouble();
            int distinctSources = wordsFileIn.readInt();
            if (!wordsCountHistory.containsKey(word)) {
                wordsCountHistory.put(word, new TimeSeries());
            }
            wordsCountHistory.get(word).put(year, count);
        }
        In countsFileIn = new In(countsFilename);
        while (countsFileIn.hasNextLine()) {
            String[] line = countsFileIn.readLine().split(",");
            int year = Integer.parseInt(line[0]);
            double count = Double.parseDouble(line[1]);
            totalWordsCount.put(year, count);
        }

    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        return new TimeSeries(wordsCountHistory.getOrDefault(word, null), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return new TimeSeries(totalWordsCount, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries wordCountCut = countHistory(word, startYear, endYear);
        return wordCountCut.dividedBy(totalWordsCount);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries selectedWordsCountCut = new TimeSeries();
        for (String word: words) {
            TimeSeries curWordCountCut = countHistory(word, startYear, endYear);
            selectedWordsCountCut.roughPlus(curWordCountCut);
        }
        return selectedWordsCountCut.dividedBy(totalWordsCount);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
//        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
        TimeSeries selectedWordsCountCut = new TimeSeries();
        for (String word: words) {
            TimeSeries curWordCount = wordsCountHistory.getOrDefault(word, new TimeSeries());
            for (int year: curWordCount.keySet()) {
                selectedWordsCountCut.put(year, selectedWordsCountCut.getOrDefault(year, 0.0) + curWordCount.get(year));
            }
        }
        return selectedWordsCountCut;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
