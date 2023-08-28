package aoa.guessers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LFGHelper {
    public static char getSimpleGuess(List<String> wordsPool, List<Character> guesses) {
        Map<Character, Integer> charsFreq = getFrequencyMap(wordsPool);
        for (char c: guesses) {
            charsFreq.remove(c);
        }
        return getMostCommonChar(charsFreq);
    }
    public static  char getMostCommonChar(Map<Character, Integer> charsFreq) {
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
    public static Map<Character, List<Integer>> getCharsIndexMap(String word) {
        Map<Character, List<Integer>> charsIndex = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            char cp = word.charAt(i);
            if (!charsIndex.containsKey(cp)) {
                charsIndex.put(cp, new ArrayList<>());
            }
            charsIndex.get(cp).add(i);
        }
        charsIndex.remove('-');
        return charsIndex;
    }
    public static Map<Character, Integer> getFrequencyMap(List<String> wordsPool) {
        // TODO: Fill in this method.
        Map<Character, Integer> res = new HashMap<>();
        for (String word: wordsPool) {
            for (char c: word.toCharArray()) {
                res.put(c, res.getOrDefault(c, 0) + 1);
            }
        }
        return res;
    }
}
