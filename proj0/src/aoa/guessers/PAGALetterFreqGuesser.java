package aoa.guessers;

import aoa.utils.FileUtils;
import edu.princeton.cs.algs4.In;

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
        Map<Character, List<Integer>> charsIndexPattern = LFGHelper.getCharsIndexMap(pattern);
        Set<Character> noShowChars = new HashSet<>(guesses);
//        Set<Character> showAllChars = new HashSet<>();
        for (int i = 0; i < pattern.length(); i++) {
            noShowChars.remove(pattern.charAt(i));
//            showAllChars.add(pattern.charAt(i));
        }
        noShowChars.remove('-');
//        showAllChars.remove('-');
        for (String word: words) {
            if (pattern.length() == word.length() && match(word, noShowChars, charsIndexPattern)) {
                wordsPool.add(word);
            }
        }
//        for (String word: words) {
//            if (match(pattern, word, noShowChars, showAllChars)) {
//                wordsPool.add(word);
//            }
//        }
        return LFGHelper.getSimpleGuess(wordsPool, guesses);
    }

    private boolean match(String target, Set<Character> noShowChars, Map<Character, List<Integer
            >>charsIndexPattern) {
        Map<Character, List<Integer>> charsIndexTarget = LFGHelper.getCharsIndexMap(target);
        if (charsIndexTarget.size() < charsIndexPattern.size()) {
            return false;
        }
        for (char c: noShowChars) {
            if (charsIndexTarget.containsKey(c)) {
                return false;
            }
        }
        for (char cp: charsIndexPattern.keySet()) {
            if (!charsIndexTarget.containsKey(cp) || !charsIndexPattern.get(cp).equals(charsIndexTarget.get(cp))) {
                return false;
            }
        }
        return true;
    }
//    private boolean match(String pattern, String target, Set<Character> noShowChars, Set<Character> showAllChars) {
//        // any char c in showAllChars, then all char c in target must be shown
//        // any char c in noShowChars, must not be in target
//        if (pattern.length() != target.length()) {
//            return false;
//        }
//        for (int i = 0; i < pattern.length(); i++) {
//            char cp = pattern.charAt(i);
//            char ct = target.charAt(i);
//            if (noShowChars.contains(ct) ||
//                    cp != '-' && cp != ct ||
//                    cp == '-' & showAllChars.contains(ct)) {
//                return false;
//            }
//        }
//        return true;
//    }

    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
