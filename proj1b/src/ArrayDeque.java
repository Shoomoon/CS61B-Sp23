import edu.princeton.cs.algs4.StdRandom;
import org.junit.runners.JUnit4;

import java.util.*;

public class ArrayDeque<T> implements Deque<T>{
    private int head;
    private int _size;
    private List<T> arr;
    private static final int INITIALSIZE = 15;
    public ArrayDeque() {
        _size = 0;
        head = 0;
        arr = new ArrayList<>(Collections.nCopies(INITIALSIZE, null));
    }
    private void reSize(boolean upScale) {
        if (upScale && _size == arr.size()) {
            arr.addAll(Collections.nCopies(_size, null));
            // if left part [0, head) < right part [head, _size),
            // shift elements [0, head) after the last element.
            // Otherwise, shift elements in [head, _size) to the end of the array [head + _size, 2 * _size)
            int begin = 0, end = head, newHead = head;
            if (head > _size - head) {
                begin = head;
                end = _size;
                newHead += _size;
            }
            for (int i = begin; i < end; i++) {
                arr.set(_size + i, arr.get(i));
                arr.set(i, null);
            }
            head = newHead;
        } else if (!upScale && _size >= INITIALSIZE && _size * 4 < arr.size()) {
            List<T> newArr = new ArrayList<>();
            for (int i = 0; i < _size; i++) {
                newArr.add(get(i));
            }
            arr = newArr;
            head = 0;
        }
    }
    /** return the actual index of i-th element considering circle,
     * eg: head = 3, size = 2, arrSize = 4,
     * then getIndex(0) = head = 3
     * getIndex(1) = (head + 1) % arrSize = 0
     * getIndex(-1) = 2
     */
    private int getIndex(int i) {
        return (head + i + arr.size()) % arr.size();
    }
    @Override
    public void addFirst(T x) {
        reSize(true);
        head = getIndex(-1);
        arr.set(head, x);
        _size += 1;
    }


    @Override
    public void addLast(T x) {
        reSize(true);
        arr.set(getIndex(_size), x);
        _size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> res = new ArrayList<>();
        for (int i = 0; i < _size; i++) {
            res.add(get(i));
        }
        return res;
    }

    @Override
    public boolean isEmpty() {
        return _size == 0;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        reSize(false);
        T firstVal = get(0);
        set(0, null);
        head = getIndex(1);
        _size -= 1;
        return firstVal;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        reSize(false);
        T lastVal = get(_size - 1);
        set(_size - 1, null);
        _size -= 1;
        return lastVal;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= _size) {
            return null;
        }
        return arr.get(getIndex(index));
    }
    public void set(int index, T x) {
        arr.set(getIndex(index), x);
    }
    public static void main(String[] args) {
        Deque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            int k = StdRandom.uniform(4);
            switch (k) {
                case 0 -> ad.addFirst(i);
                case 1 -> ad.addLast(i);
                case 2 -> ad.removeFirst();
                case 3 -> ad.removeLast();
                default -> {
                }
            }
        }

        System.out.println(ad.toList());
    }
}
