package aoa.choosers;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import aoa.utils.FileUtils;
import edu.princeton.cs.algs4.StdRandom;

public class EvilChooser implements Chooser {
    private String pattern;
    private List<String> wordPool;

    public EvilChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in this constructor.
        if (wordLength <= 0) {
            throw new IllegalArgumentException("WordLength must be at least 1!");
        }
        wordPool = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if (wordPool.isEmpty()) {
            throw new IllegalStateException("No words found of wordLength!");
        }
        pattern = "-".repeat(wordLength);
    }

    @Override
    public int makeGuess(char letter) {
        // TODO: Fill in this method.
        TreeMap<String, List<String>> category = new TreeMap<>();
        for (String word: wordPool) {
            String nextPattern = getNextPattern(word, letter);
            if (!category.containsKey(nextPattern)) {
                category.put(nextPattern, new ArrayList<>());
            }
            category.get(nextPattern).add(word);
        }
        int maxSize = 0;
        for (String nextPattern: category.keySet()) {
            if (category.get(nextPattern).size() > maxSize) {
                pattern = nextPattern;
                wordPool = category.get(nextPattern);
                maxSize = wordPool.size();
            }
        }
        int count = 0;
        for (char c: pattern.toCharArray()) {
            if (c == letter) {
                count += 1;
            }
        }
        return count;
    }

    private String getNextPattern(String word, char letter) {
        StringBuilder nextPattern = new StringBuilder(pattern);
        for (int i = 0; i < nextPattern.length(); i++) {
            if (word.charAt(i) == letter) {
                nextPattern.setCharAt(i, letter);
            }
        }
        return nextPattern.toString();
    }

    @Override
    public String getPattern() {
        // TODO: Fill in this method.
        return pattern;
    }

    @Override
    public String getWord() {
        // TODO: Fill in this method.
        return wordPool.get(StdRandom.uniform(wordPool.size()));
    }
}
