import com.google.common.truth.Truth;
import jh61b.utils.Reflection;
import net.sf.saxon.functions.ConstantFunction;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.notification.Failure;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

     @Test
     @DisplayName("LinkedListDeque has no fields besides nodes and primitives")
     void noNonTrivialFields() {
         Class<?> nodeClass = NodeChecker.getNodeClass(LinkedListDeque.class, true);
         List<Field> badFields = Reflection.getFields(LinkedListDeque.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(nodeClass) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not nodes or primitives").that(badFields).isEmpty();
     }

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque<String> lld1 = new LinkedListDeque<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque<Integer> lld1 = new LinkedListDeque<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque.
     Deque<Integer> dqInt;
     Deque<String> dqString;
     @BeforeEach
     public void initialize() {
         dqInt = new LinkedListDeque<>();
         dqInt.addLast(0);
         dqInt.addLast(1);
         dqInt.addLast(2);
         dqString = new LinkedListDeque<>();
         dqString.addFirst("0");
         dqString.addFirst("1");
         dqString.addFirst("2");
     }
     @Test
     void addFirst() {
         dqInt.addFirst(3);
         assertThat(dqInt.toList()).isEqualTo(List.of(3, 0, 1, 2));
         dqString.addFirst("3");
         assertThat(dqString.toList()).isEqualTo(List.of("3", "2", "1", "0"));
     }

     @Test
     void addLast() {
         dqInt.addLast(3);
         assertThat(dqInt.toList()).isEqualTo(List.of(0, 1, 2, 3));
         dqString.addLast("3");
         assertThat(dqString.toList()).isEqualTo(List.of("2", "1", "0", "3"));
     }
     @Test
     void toList() {
         assertThat(dqInt.toList()).isEqualTo(List.of(0, 1, 2));
         assertThat(dqString.toList()).isEqualTo(List.of("2", "1", "0"));
     }

    @Test
    void isEmpty() {
         assertThat(dqInt.isEmpty()).isFalse();
         dqInt.removeFirst();
         dqInt.removeFirst();
         dqInt.removeFirst();
         assertThat(dqInt.isEmpty()).isTrue();
    }

    @Test
    void size() {
         assertThat(dqInt.size()).isEqualTo(3);
        assertThat(dqString.size()).isEqualTo(3);
         dqString.removeFirst();
         assertThat(dqString.size()).isEqualTo(2);
    }

    @Test
    void removeFirst() {
        assertThat(dqInt.get(0)).isEqualTo(0);
         assertThat(dqInt.get(1)).isEqualTo(1);
        assertThat(dqInt.removeFirst()).isEqualTo(0);
        assertThat(dqInt.get(0)).isEqualTo(1);

        assertThat(dqString.get(0)).isEqualTo("2");
         dqString.removeFirst();
         assertThat(dqString.get(0)).isEqualTo("1");
         dqInt.removeFirst();
         dqInt.removeFirst();
         assertThat(dqInt.removeFirst()).isNull();
    }

    @Test
    void removeLast() {
        assertThat(dqInt.get(dqInt.size() - 1)).isEqualTo(2);
        dqInt.removeLast();
        assertThat(dqInt.get(dqInt.size() - 1)).isEqualTo(1);
        assertThat(dqString.get(dqString.size() - 1)).isEqualTo("0");
        dqString.removeLast();
        assertThat(dqString.get(dqString.size() - 1)).isEqualTo("1");
        dqString.removeLast();
        dqString.removeLast();
        assertThat(dqString.removeFirst()).isNull();
    }

    @Test
    void get() {
         assertThat(dqInt.get(dqInt.size() - 1)).isEqualTo(2);
         assertThat(dqString.get(dqString.size() - 1)).isEqualTo("0");
         assertThat(dqInt.get(dqInt.size())).isNull();
         assertThat(dqInt.get(-1)).isNull();
    }

    @Test
    void getRecursive() {
         for (int i = 0; i < dqInt.size(); i++) {
             assertThat(dqInt.get(i)).isEqualTo(dqInt.getRecursive(i));
         }
        for (int i = 0; i < dqString.size(); i++) {
            assertThat(dqString.get(i)).isEqualTo(dqString.getRecursive(i));
        }
        assertThat(dqInt.getRecursive(dqInt.size())).isNull();
        assertThat(dqInt.getRecursive(-1)).isNull();
    }
}