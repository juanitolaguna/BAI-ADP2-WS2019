package adp4;

import algs4.Queue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BinarySearchMultiST<Key extends Comparable<Key>, Value> {
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] vals;
    private int n = 0;

    /**
     * Initializes an empty symbol table.
     */
    public BinarySearchMultiST() {
        this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     *
     * @param capacity the maximum capacity
     */
    public BinarySearchMultiST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }


    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    /**
     * Unit tests the {@code BinarySearchST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        BinarySearchMultiST<String, Integer> st = new BinarySearchMultiST<String, Integer>();
//        List<String> keys = new ArrayList<>(List.of("S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E", "E"));
        List<String> keys = new ArrayList<>(List.of("A", "E", "E", "E", "E", "E", "E", "G", "H", "H", "B"));
        //0    1    2    3    4    5    6    7    8    9    10

        Integer index = 0;
        for (String key : keys) {
            st.put(key, index++);
        }

        System.out.println(st);
        System.out.println("size: " + st.size());
        System.out.println("rank(A) => " + st.rank("A"));
        System.out.println("rank(B) => " + st.rank("B"));
        System.out.println("rank(E) => " + st.rank("E"));
        System.out.println("rank(G) => " + st.rank("G"));
        System.out.println("rank(H) => " + st.rank("H"));

//        [ A 0 ] [ B 10 ] [ E 6 ] [ E 5 ] [ E 4 ] [ E 3 ] [ E 2 ] [ E 1 ] [ G 7 ] [ H 9 ] [ H 8 ]

        String keysInST = "";
        for (String k : st.keys()) {
            keysInST += k + ":[" + st.get(k) + "]  ";
        }
        System.out.println("All keys in ST with getFirst: " + keysInST);
//        st.delete("E");
//        System.out.println("ST: " + st);

//
//        System.out.println("st.getAll(E)" + st.getAll("E"));
//        System.out.println("st.getAll(H)" + st.getAll("H"));
//        System.out.println("st.get(E)" + st.get("E"));

        st.delete("E");
        System.out.println(st);
        System.out.println("size: " + st.size());
        st.delete("H");
        System.out.println(st);
        System.out.println("size: " + st.size());


    }

    // resize the underlying arrays
    private void resize(int capacity) {
        assert capacity >= n;
        Key[] tempk = (Key[]) new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty;
     * {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     * {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key in this symbol table.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     * and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        List<Value> values = new ArrayList<>();
        for (Value value : getAll(key)) {
            values.add(value);
        }
        int size = values.size() - 1;
        int random = size > 0 ? getRandomNumberInRange(0, size) : 0;
        return values.get(random);
    }

    public Iterable<Value> getAll(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        if (isEmpty()) return new ArrayList<>();

        List<Value> values = new ArrayList<>();
        int i = rank(key);
        while ((i < n) && key.compareTo(keys[i]) == 0) {
            values.add(vals[i]);
            i++;
        }
        return values;
    }

    /**
     * Returns the number of keys in this symbol table strictly less than {@code key}.
     *
     * @param key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");

        System.out.println("Key: " + key);

        int lo = 0, hi = n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            //int mid = lo + (hi - lo) / 2;

            System.out.println(lo + " + (" + hi + " - " + lo + ") = " + (lo + (hi - lo)));
            System.out.println(lo + " + (" + hi + " - " + lo + ") / 2 = " + mid);
            System.out.println(hi + " / 2 = " + (hi / 2));
            int cmp = key.compareTo(keys[mid]);
            System.out.println(key + ".compareTo(" + keys[mid] + ") => " + cmp);
            if (cmp < 0) {
                hi = mid - 1;
                System.out.println("hi: " + hi);
            } else if (cmp > 0) {
                lo = mid + 1;
                System.out.println("lo: " + lo);
            }
            // If compare == 0 (key found -> skip)
            else {
                System.out.println("mid: " + mid);

                while (--mid > 0 && key.compareTo(keys[mid]) == 0) {
                }
                return mid + 1;
            }
        }
        return lo;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {
            delete(key);
            return;
        }

        int i = rank(key);
//        System.out.println("Key: " + key + " Rank: " + i);

        // key is already in table
//        if (i < n && keys[i].compareTo(key) == 0) {
//            vals[i] = val;
//            return;
//        }

        // insert new key-value pair
        if (n == keys.length) {
            resize(2 * keys.length);
        }

        for (int j = n; j > i; j--) {
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        keys[i] = key;
        vals[i] = val;
        n++;

        assert check();
    }

    /**
     * Removes the specified key and associated value from this symbol table
     * (if the key is in the symbol table).
     *
     * @param key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (isEmpty()) return;

        // compute rank
        int i = rank(key);


        int countSameKeys = 0;
        int k = i;

        while (k != n && keys[k] != null && key.compareTo(keys[k++]) == 0) {
            countSameKeys++;
        }
//        System.out.println("Same keys: " + countSameKeys);


        // key not in table
        if (i == n || keys[i].compareTo(key) != 0) {
            return;
        }

        // Alle keys ab i eine position nach hinten vershieben.
        for (int j = i; j < n - 1; j++) {
            keys[j] = keys[j + countSameKeys];
            vals[j] = vals[j + countSameKeys];
        }

        n = n - 1;
        keys[n] = null;  // to avoid loitering
        vals[n] = null;

        n = n - countSameKeys + 1;


        // resize if 1/4 full
        if (n > 0 && n == keys.length / 4) resize(keys.length / 2);

        assert check();
    }

    /**
     * Removes the smallest key and associated value from this symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
        delete(min());
    }


    /***************************************************************************
     *  Ordered symbol table methods.
     ***************************************************************************/

    /**
     * Removes the largest key and associated value from this symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
        delete(max());
    }

    /**
     * Returns the smallest key in this symbol table.
     *
     * @return the smallest key in this symbol table
     * @throws NoSuchElementException if this symbol table is empty
     */
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
        return keys[0];
    }

    /**
     * Returns the largest key in this symbol table.
     *
     * @return the largest key in this symbol table
     * @throws NoSuchElementException if this symbol table is empty
     */
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
        return keys[n - 1];
    }

    /**
     * Return the kth smallest key in this symbol table.
     *
     * @param k the order statistic
     * @return the {@code k}th smallest key in this symbol table
     * @throws IllegalArgumentException unless {@code k} is between 0 and
     *                                  <em>n</em>–1
     */
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        }
        return keys[k];
    }

    /**
     * Returns the largest key in this symbol table less than or equal to {@code key}.
     *
     * @param key the key
     * @return the largest key in this symbol table less than or equal to {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        int i = rank(key);
        if (i < n && key.compareTo(keys[i]) == 0) return keys[i];
        if (i == 0) return null;
        else return keys[i - 1];
    }

    /**
     * Returns the smallest key in this symbol table greater than or equal to {@code key}.
     *
     * @param key the key
     * @return the smallest key in this symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException   if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        int i = rank(key);
        if (i == n) return null;
        else return keys[i];
    }

    /**
     * Returns the number of keys in this symbol table in the specified range.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return the number of keys in this symbol table between {@code lo}
     * (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *                                  is {@code null}
     */
    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    /**
     * Returns all keys in this symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in this symbol table
     */
    public Iterable<Key> keys() {

        return keys(min(), max());
    }

    /**
     * Returns all keys in this symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return all keys in this symbol table between {@code lo}
     * (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *                                  is {@code null}
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new Queue<Key>();
        if (lo.compareTo(hi) > 0) return queue;
        Key currentKey, nextKey;
        int nextIndex;
        for (int i = rank(lo); i < rank(hi); i++) {

            while (i < n && keys[i].compareTo(keys[i + 1]) == 0) {
                i++;
            }
            queue.enqueue(keys[i]);
        }

        if (contains(hi)) queue.enqueue(keys[rank(hi)]);
        return queue;
    }

    /***************************************************************************
     *  Check internal invariants.
     ***************************************************************************/

    private boolean check() {
        return isSorted() && rankCheck();
    }

    // are the items in the array in ascending order?
    private boolean isSorted() {
        for (int i = 1; i < size(); i++)
            if (keys[i].compareTo(keys[i - 1]) < 0) return false;
        return true;
    }

    // check that rank(select(i)) = i
    private boolean rankCheck() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (int i = 0; i < size(); i++)
            if (keys[i].compareTo(select(rank(keys[i]))) != 0) return false;
        return true;
    }

    @Override
    public String toString() {
        String content = "";
        for (int i = 0; i < keys.length; i++) {

            //if (keys[i] != null) {
            content += "[ " + keys[i] + " " + vals[i] + " ] ";
            //}
        }
        return content;
    }
}
