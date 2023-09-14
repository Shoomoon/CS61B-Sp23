import deque.Deque;
import deque.MaxArrayDeque;
import org.junit.jupiter.api.*;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDequeTest {
    private MaxArrayDeque<Integer> ad;
    private Comparator<Integer> reverseOrder;
    @BeforeEach
    public void init() {
        Comparator<Integer> naturalOrder = (o1, o2) -> o1 - o2;
        reverseOrder = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        };
        ad = new MaxArrayDeque<>(naturalOrder);
        System.out.println(ad instanceof Deque);
    }
    @Test
    public void myCompareTest() {
        for (int i = 0; i < 10; i++) {
            ad.addLast(i);
        }
        assertThat(ad.max()).isEqualTo(9);
        ad.removeLast();
        assertThat(ad.max()).isEqualTo(8);
    }
    @Test
    public void customCompareTest() {
        for (int i = 0; i < 10; i++) {
            ad.addLast(i);
        }
        assertThat(ad.max(reverseOrder)).isEqualTo(0);
        ad.removeLast();
        assertThat(ad.max(reverseOrder)).isEqualTo(0);
        ad.removeFirst();
        assertThat(ad.max(reverseOrder)).isEqualTo(1);
    }
    @Test
    public void emptyDequeTest() {
        assertThat(ad.max()).isEqualTo(null);
        assertThat(ad.max(reverseOrder)).isEqualTo(null);
    }
}
