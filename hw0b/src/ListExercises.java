
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        int total = 0;
        for (int i: L) {
            total += i;
        }
        return total;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> res = new ArrayList<>();
        for (int i: L) {
            if (i % 2 == 0) {
                res.add(i);
            }
        }
        return res;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> commonNums = new ArrayList<>();
        Set<Integer> setL1 = new HashSet<>(L1);
        for (int i: L2) {
            if (setL1.contains(i)) {
                commonNums.add(i);
            }
        }
        return commonNums;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int count = 0;
        for (String word: words) {
            for (char ch: word.toCharArray()) {
                if (ch == c) {
                    count += 1;
                }
            }
        }
        return count;
    }
}
