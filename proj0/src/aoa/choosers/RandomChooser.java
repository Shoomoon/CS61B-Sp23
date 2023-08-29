package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.util.List;
import java.util.Random;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in/change this constructor.
        if (wordLength <= 0) {
            throw new IllegalArgumentException("WordLength must be at least 1!");
        }
        List<String> wordsDic = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if (wordsDic.isEmpty()) {
            throw new IllegalStateException("No words found of wordLength!");
        }
        chosenWord = wordsDic.get(StdRandom.uniform(wordsDic.size()));
        pattern = "-".repeat(wordLength);
    }

    @Override
    public int makeGuess(char letter) {
        // TODO: Fill in this method.
        int count = 0;
        StringBuilder nextPattern = new StringBuilder(pattern);
        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.charAt(i) == letter) {
                count += 1;
                nextPattern.setCharAt(i, letter);
            }
        }
        pattern = nextPattern.toString();
        return count;
    }

    @Override
    public String getPattern() {
        // TODO: Fill in this method.
        return pattern;
    }

    @Override
    public String getWord() {
        // TODO: Fill in this method.
        return chosenWord;
    }
}
