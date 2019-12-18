package adp3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayST<Key extends Comparable<Key>, Value> {

    private static final int SIZE = 10;

    private final Object[] keys;
    private final Object[] values;
    private int i;

    public ArrayST() {
        this.keys = new Object[SIZE];
        this.values = new Object[SIZE];
        this.i = 0;
    }

    public void put(Key key, Value value) {
        this.keys[i] = key;
        this.values[i++] = value;
    }

    public Value get(Key key) {
        for (int j = 0; j < SIZE; j++) {
            if (this.keys[j] == key) {
                return shiftDown(j);
            }
        }
        return null;
    }

    public Value delete(Key key) {
        for (int j = 0; j < SIZE; j++) {
            Key current = (Key) this.keys[j];
            if (current.equals(key)) {
                i--;
                return shiftUp(j);
            }
        }
        return null;
    }

    public boolean contains(Key key) {
        for (int j = 0; j < i; j++) {
            Key current = (Key) this.keys[j];
            if (current.equals(key)) return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return this.i == 0;
    }

    public int size() {
        return this.i;
    }

    public Iterable<Key> keys() {
        List<Key> asList = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            asList.add((Key) this.keys[j]);
        }

        return asList;
    }

    private Value shiftUp(int j) {
        Key key = (Key) this.keys[i];
        Value value = (Value) this.values[i];

        this.keys[i] = null;
        this.values[i] = null;
        for (int k = i - 1; k >= j; k--) {
            Key tempk = (Key) this.keys[k];
            Value tempv = (Value) this.values[k];

            this.keys[k] = key;
            this.values[k] = value;

            key = tempk;
            value = tempv;
        }

        return value;
    }

    private Value shiftDown(int j) {
        Key key = (Key) this.keys[0];
        Value value = (Value) this.values[0];
        for (int k = 1; k <= j; k++) {
            Key tempk = (Key) this.keys[k];
            Value tempv = (Value) this.values[k];

            this.keys[k] = key;
            this.values[k] = value;

            key = tempk;
            value = tempv;
        }
        this.keys[0] = key;
        this.values[0] = value;
        return value;
    }

    @Override
    public String toString() {
        String s = "[";
        for (int j = 0; j < keys.length; j++) {
            s += keys[j] + ", ";
        }
        s += "]";
        return s;
    }

    public static void main(String[] args) {
        ArrayST<Integer, Integer> st = new ArrayST<>();
        for (int i = 0; i < 10; i++) {
            st.put(i, i);
        }

        System.out.println(st);
        st.get(6);
        System.out.println(st);
        st.get(9);
        st.get(9);
        st.get(6);
        System.out.println(st);
    }


}
