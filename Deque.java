package HTMLtags;

import java.util.Arrays;

public class Deque<T> {
    T[] data;
    int f, l, capacity;
    int sz = 0;

    public Deque() {
        this(1000);
    }

    public Deque(int capacity) {
        this.capacity = capacity;
        this.data = (T[]) new Object[capacity];
        f = (capacity / 2) - 1;
        l = capacity / 2;
    }

    public void addFirst(T t) throws IllegalStateException {
        if (sz == data.length) throw new IllegalStateException("Queue is full");
        if (data[f] != null) f = (f + capacity - 1) % capacity;
        data[f] = t;
        sz++;
    }

    public void addLast(T t) throws IllegalStateException {
        if (sz == data.length) throw new IllegalStateException("Queue is full");
        if (data[l] != null) l = (l + 1) % capacity;
        data[l] = t;
        sz++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        else if (data[f] == null) f++;
        T value = data[f];
        data[f] = null;
        sz--;
        if (f == l) {
            f = (capacity / 2) - 1;
            l = capacity / 2;
        } else {
            f = (f + 1) % capacity;
        }
        return value;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        else if (data[l] == null) l--;
        T value = data[l];
        data[l] = null;
        sz--;
        if (f == l) {
            f = (capacity / 2) - 1;
            l = capacity / 2;
        } else {
            l = (l + capacity - 1) % capacity;
        }
        return value;
    }

    public T first() {
        if (isEmpty()) return null;
        else if (data[f] == null) return data[f + 1];
        else return data[f];
    }

    public T last() {
        if (isEmpty()) return null;
        else if (data[l] == null) return data[l - 1];
        else return data[l];
    }

    public boolean isEmpty() {
        if (sz == 0) return true;
        else return false;
    }

    public int size() {
        return sz;
    }

    @Override
    public String toString() {
        return String.format(Arrays.toString(data) + "\nFirst: %d\nLast: %d", f, l);
    }
}
