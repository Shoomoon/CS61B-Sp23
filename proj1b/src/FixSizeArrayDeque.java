import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FixSizeArrayDeque<T> implements Deque<T>{
    private List<T> arr;
    private int head;
    private int _size;

    public FixSizeArrayDeque (int size) {
        arr = new ArrayList<>(Collections.nCopies(size, null));
        head = 0;
        _size = 0;
    }
    @Override
    public void addFirst(T x) {
        int newHead = getIndex(-1);
        arr.set(newHead, x);
        head = newHead;
        _size += 1;
    }
    private int getIndex(int i) {
        return (head + i + arr.size()) % arr.size();
    }
    @Override
    public void addLast(T x) {
        int tail = getIndex(_size);
        arr.set(tail, x);
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
        T res = get(0);
        arr.set(head, null);
        head = getIndex(1);
        _size -= 1;
        return res;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T res = get(_size - 1);
        arr.set(getIndex(_size - 1), null);
        _size -= 1;
        return res;
    }

    @Override
    public T get(int index) {
        return arr.get(getIndex(index));
    }
}
