import deque.ArrayDeque;
import deque.Deque;
import deque.LinkedListDeque;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class DequeTest {
    Deque<Integer> ad;
    Deque<Integer> lld;
    @BeforeEach
    public void init() {
        ad = new ArrayDeque<>();
        lld = new LinkedListDeque<>();
        ad.addFirst(0);
        ad.addLast(1);
        lld.addFirst(0);
        lld.addLast(1);
    }
    @Test
    public void iteratorTest() {
        List<Integer> adList = ad.toList();
        Iterator<Integer> adIt = ad.iterator();
        for (int i = 0; i < ad.size(); i++) {
            assertThat(adIt.hasNext()).isTrue();
            assertThat(adList.get(i)).isEqualTo(adIt.next());
        }
        assertThat(adIt.hasNext()).isFalse();

        List<Integer> lldList = ad.toList();
        Iterator<Integer> lldIt = ad.iterator();
        for (int i = 0; i < ad.size(); i++) {
            assertThat(lldIt.hasNext()).isTrue();
            assertThat(lldList.get(i)).isEqualTo(lldIt.next());
        }
        assertThat(lldIt.hasNext()).isFalse();
    }

    @Test
    public void equalsTest() {
        assertThat(ad.equals(ad)).isTrue();
        assertThat(ad.equals(null)).isFalse();
        assertThat(lld.equals(lld)).isTrue();
        assertThat(lld.equals(null)).isFalse();
        assertThat(ad.equals(lld)).isTrue();
        assertThat(lld.equals(ad)).isTrue();
        Deque<Integer> ad1 = new ArrayDeque<>();
        ad1.addLast(0);
        ad1.addLast(1);
        Deque<Integer> lld1 = new ArrayDeque<>();
        lld1.addLast(0);
        lld1.addLast(1);
        assertThat(ad.equals(ad1)).isTrue();
        assertThat(lld.equals(lld1)).isTrue();
    }
}
