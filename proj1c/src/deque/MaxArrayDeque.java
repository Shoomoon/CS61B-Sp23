package deque;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private final Comparator<T> myComparator;
    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.myComparator = c;
    }
    public T max() {
        return this.max(myComparator);
    }
    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T res = this.get(0);
        for (int i = 1; i < this.size(); i++) {
            if (c.compare(res, this.get(i)) < 0) {
                res = this.get(i);
            }
        }
        return res;
    }
}
