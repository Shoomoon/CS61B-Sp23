package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        // TODO: Fill in this method.
        List<String> wordsPool = new ArrayList<>();
        Set<Character> noShowChars = new HashSet<>(guesses);
        Set<Character> showAllChars = new HashSet<>();
        for (int i = 0; i < pattern.length(); i++) {
            noShowChars.remove(pattern.charAt(i));
            showAllChars.add(pattern.charAt(i));
        }
        noShowChars.remove('-');
        showAllChars.remove('-');
        for (String word: words) {
            if (match(pattern, word, noShowChars, showAllChars)) {
                wordsPool.add(word);
            }
        }
        return getSimpleGuess(wordsPool, guesses);
    }
    private char getSimpleGuess(List<String> wordsPool, List<Character> guesses) {
        Map<Character, Integer> charsFreq = getFrequencyMap(wordsPool);
        for (char c: guesses) {
            charsFreq.remove(c);
        }
        return getMostCommonChar(charsFreq);
    }
    private char getMostCommonChar(Map<Character, Integer> charsFreq) {
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
    private boolean match(String pattern, String target, Set<Character> noShowChars, Set<Character> showAllChars) {
        // any char c in showAllChars, then all char c in target must be shown
        // any char c in noShowChars, must not be in target
        if (pattern.length() != target.length()) {
            return false;
        }
        for (int i = 0; i < pattern.length(); i++) {
            char cp = pattern.charAt(i);
            char ct = target.charAt(i);
            if (noShowChars.contains(ct) ||
                    cp != '-' && cp != ct ||
                    cp == '-' & showAllChars.contains(ct)) {
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
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
