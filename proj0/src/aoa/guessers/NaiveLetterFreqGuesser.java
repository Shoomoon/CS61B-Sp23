package aoa.guessers;

import aoa.utils.FileUtils;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        // TODO: Fill in this method.
        Map<Character, Integer> res = new HashMap<>();
        for (String word: words) {
            for (char c: word.toCharArray()) {
                res.put(c, res.getOrDefault(c, 0) + 1);
            }
        }
        return res;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        // TODO: Fill in this method.
        Map<Character, Integer> charsFreq = getFrequencyMap();
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

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
