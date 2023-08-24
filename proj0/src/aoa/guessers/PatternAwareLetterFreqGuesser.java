package aoa.guessers;

import aoa.utils.FileUtils;

import javax.swing.text.html.parser.Entity;
import java.util.*;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
        List<String> wordsPool = new ArrayList<>();
        for (String word: words) {
            if (match(pattern, word)) {
                wordsPool.add(word);
            }
        }
        Map<Character, Integer> charsFreq = getFrequencyMap(wordsPool);
        for (char c: guesses) {
            charsFreq.remove(c);
        }
        if (charsFreq.isEmpty()) {
            System.out.print("No letters left!");
        }
        char mostCommonLetter = '?';
        int count = 0;
        for (char c: charsFreq.keySet()) {
            int currentFreq = charsFreq.get(c);
            if (currentFreq < count) {
                continue;
            }
            if (currentFreq > count || mostCommonLetter > c) {
                mostCommonLetter = c;
                count = currentFreq;
            }
        }
        return mostCommonLetter;
    }
    private boolean match(String pattern, String target) {
        if (pattern.length() != target.length()) {
            return false;
        }
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) != '-' && pattern.charAt(i) != target.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    public Map<Character, Integer> getFrequencyMap(List<String> wordsPool) {
        // TODO: Fill in this method.
        Map<Character, Integer> res = new HashMap<>();
        for (String word: wordsPool) {
            for (char c: word.toCharArray()) {
                res.put(c, res.getOrDefault(c, 0) + 1);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}