import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDequeTest {
    Deque<Integer> arr;
    Deque<Integer> myarr;
    final int FIXSIZE = 20;
    @BeforeEach
    void setUp() {
        arr = new ArrayDeque<>();
        arr.addLast(0);
        arr.addLast(1);
        arr.addLast(2);
        myarr = new FixSizeArrayDeque<>(FIXSIZE);
        myarr.addLast(0);
        myarr.addLast(1);
        myarr.addLast(2);
    }

    @Test
    void randomTest() {
        for (int i = 0; i < FIXSIZE; i++) {
            int k = StdRandom.uniform(4);
            switch (k) {
                case 0 -> {
                    arr.addFirst(i);
                    myarr.addFirst(i);
                }
                case 1 -> {
                    arr.addLast(i);
                    myarr.addLast(i);
                }
                case 2 -> assertThat(arr.removeFirst()).isEqualTo(myarr.removeFirst());
                case 3 -> assertThat(arr.removeLast()).isEqualTo(myarr.removeLast());
                default -> {}
            }
            assertThat(arr.toList()).isEqualTo(myarr.toList());
        }
    }
    @Test
    void addFirst() {
        arr.addFirst(3);
        assert(arr.get(0)).equals(3);
        arr.addFirst(4);
        assert(arr.get(0)).equals(4);
    }

    @Test
    void addLast() {
        arr.addLast(3);
        assert(arr.get(arr.size() - 1)).equals(3);
    }

    @Test
    void toList() {
        assert(arr.toList()).equals(List.of(0,1,2));
        arr.addFirst(3);
        assert(arr.toList()).equals(List.of(3,0,1,2));
    }

    @Test
    void isEmpty() {
        assertThat(arr.isEmpty()).isFalse();
        arr.removeFirst();
        arr.removeFirst();
        arr.removeFirst();
        assertThat(arr.isEmpty()).isTrue();
    }

    @Test
    void size() {
        assertThat(arr.size()).isEqualTo(3);
        arr.removeFirst();
        arr.removeFirst();
        arr.removeFirst();
        arr.removeFirst();
        assertThat(arr.size()).isEqualTo(0);
        assertThat(arr.isEmpty()).isTrue();
    }

    @Test
    void removeFirst() {
        assertThat(arr.removeFirst()).isEqualTo(0);
        assertThat(arr.removeFirst()).isEqualTo(1);
        assertThat(arr.removeFirst()).isEqualTo(2);
        assertThat(arr.removeFirst()).isEqualTo(null);
        assertThat(arr.removeFirst()).isEqualTo(null);
    }

    @Test
    void removeLast() {
        assertThat(arr.removeLast()).isEqualTo(2);
        assertThat(arr.removeLast()).isEqualTo(1);
        assertThat(arr.removeLast()).isEqualTo(0);
        assertThat(arr.removeLast()).isEqualTo(null);
        assertThat(arr.removeLast()).isEqualTo(null);
    }

    @Test
    void get() {
        assertThat(arr.get(0)).isEqualTo(0);
        assertThat(arr.get(1)).isEqualTo(1);
        assertThat(arr.get(2)).isEqualTo(2);
        arr.addFirst(3);
        assertThat(arr.get(0)).isEqualTo(3);
        assertThat(arr.get(1)).isEqualTo(0);
        assertThat(arr.get(2)).isEqualTo(1);
    }
}
