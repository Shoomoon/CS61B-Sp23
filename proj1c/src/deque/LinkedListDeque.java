package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class LinkedListDeque<T> implements Deque<T> {
    private final LinkedListNode<T> head;
    private int _size;

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator<>();
    }
    private class LinkedListDequeIterator<T> implements Iterator<T> {
        private LinkedListNode<T> curNode;
        public LinkedListDequeIterator() {
            curNode = (LinkedListNode<T>) head.getNextNode();
        }
        @Override
        public boolean hasNext() {
            return !curNode.isHead();
        }

        @Override
        public T next() {
            T res = curNode.getVal();
            curNode = curNode.getNextNode();
            return res;
        }
    }
    private static class LinkedListNode<T> {
        private final T _val;
        private LinkedListNode<T> _nextNode;
        private LinkedListNode<T> _prevNode;
        public LinkedListNode(T value, LinkedListNode<T> nextNode, LinkedListNode<T> prevNode) {
            this._val = value;
            this._nextNode = nextNode;
            this._prevNode = prevNode;
        }

        public T getVal() {
            return _val;
        }

        public LinkedListNode<T> getNextNode() {
            return _nextNode;
        }
        public LinkedListNode<T> getPrevNode() {
            return _prevNode;
        }
        public void setNextNode(LinkedListNode<T> newNextNode) {
            this._nextNode = newNextNode;
        }
        public void setPrevNode(LinkedListNode<T> newPrevNode) {
            this._prevNode = newPrevNode;
        }
        public void addNodeAfter(T x) {
            LinkedListNode<T> nextNode = this._nextNode;
            LinkedListNode<T> newNode = new LinkedListNode<>(x, null, null);
            this.setNextNode(newNode);
            nextNode.setPrevNode(newNode);
            newNode.setPrevNode(this);
            newNode.setNextNode(nextNode);
        }
        /** Remove next node and return the removed node.
         * If next Node is HeadNode, then do nothing and return HeadNode.
         * */
        public LinkedListNode<T> removeNextNode() {
            LinkedListNode<T> nextNode = this._nextNode;
            if (nextNode.isHead()) {
                System.out.println("No node can be removed!");
            } else {
                LinkedListNode<T> nextNextNode = nextNode.getNextNode();
                this._nextNode = nextNextNode;
                nextNextNode.setPrevNode(this);
            }
            return nextNode;
        }
        /** Remove previous node and return the removed node.
         * If previous Node is HeadNode, then do nothing and return HeadNode.
         * */
        public LinkedListNode<T> removePrevNode() {
            LinkedListNode<T> prevNode = this._prevNode;
            if (prevNode.isHead()) {
                System.out.println("No node can be removed!");
            } else {
                LinkedListNode<T> prevPrevNode = prevNode.getPrevNode();
                this._prevNode = prevPrevNode;
                prevPrevNode.setNextNode(this);
            }
            return prevNode;
        }
        public T getValRecursive(int index) {
            if (index == 0) {
                return _val;
            }
            return _nextNode.getValRecursive(index - 1);
        }
        public boolean isHead() {
            return _val == null;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();
    }
    public LinkedListDeque() {
        head = new LinkedListNode<>(null, null, null);
        head.setNextNode(head);
        head.setPrevNode(head);
        this._size = 0;
    }
    @Override
    public void addFirst(T x) {
        head.addNodeAfter(x);
        this._size += 1;
    }

    @Override
    public void addLast(T x) {
        LinkedListNode<T> lastNode = head.getPrevNode();
        lastNode.addNodeAfter(x);
        this._size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        LinkedListNode<T> cur = head.getNextNode();
        while (!cur.isHead()) {
            returnList.add(cur.getVal());
            cur = cur.getNextNode();
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return head.getNextNode().isHead();
    }

    @Override
    public int size() {
        return this._size;
    }

    @Override
    public T removeFirst() {
        LinkedListNode<T> removedNode = head.removeNextNode();
        this._size = Math.max(this._size - 1, 0);
        return removedNode.getVal();
    }

    @Override
    public T removeLast() {
        LinkedListNode<T> removedNode = head.removePrevNode();
        this._size = Math.max(this._size - 1, 0);
        return removedNode.getVal();
    }

    @Override
    public T get(int index) {
        if (index >= this._size || index < 0) {
//            throw new IllegalArgumentException("Index out of range!");
            return null;
        }
        LinkedListNode<T> curNode = head;
        while (index >= 0) {
            curNode = curNode.getNextNode();
            index -= 1;
        }
        return curNode.getVal();
    }

    @Override
    public T getRecursive(int index) {
        if (index >= this._size || index < 0) {
//            throw new IllegalArgumentException("Index out of range!");
            return null;
        }
        return head.getNextNode().getValRecursive(index);
    }
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Deque)) {
            return false;
        }
        if (this == other) {
            return true;
        }
        Deque<T> otherDeque = (Deque<T>) other;
        if (otherDeque.size() != this.size()) {
            return false;
        }
        Iterator<T> itThis = this.iterator();
        Iterator<T> itOther = otherDeque.iterator();
        while (itThis.hasNext()) {
            if (!itThis.next().equals(itOther.next())) {
                return false;
            }
        }
        return !itOther.hasNext();
    }
    public String toString() {
        List<String> res = new ArrayList<>();
        for (T value: toList()) {
            res.add(value.toString());
        }
        return "[" + String.join(", ", res) + "]";
    }
}
