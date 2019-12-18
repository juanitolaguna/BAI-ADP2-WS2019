/******************************************************************************
 *  Compilation:  javac LinearProbingHashTable.java
 *  Execution:    java LinearProbingHashTable < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://adp4.cs.princeton.edu/34hash/tinyST.txt
 *  
 *  Symbol-table implementation with linear-probing hash table.
 *
 ******************************************************************************/

package adp4;

import algs4.Queue;
import algs4.StdIn;
import algs4.StdOut;

import java.util.Arrays;

/**
 *  The {@code LinearProbingHashTable} class represents a symbol table of generic
 *  key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  This implementation uses a linear probing hash table. It requires that
 *  the key type overrides the {@code equals()} and {@code hashCode()} methods.
 *  The expected time per <em>put</em>, <em>contains</em>, or <em>remove</em>
 *  operation is constant, subject to the uniform hashing assumption.
 *  The <em>size</em>, and <em>is-empty</em> operations take constant time.
 *  Construction takes constant time.
 *  <p>
 *  For additional documentation, see <a href="https://adp4.cs.princeton.edu/34hash">Section 3.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.

 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class LinearProbingHashTable<Key, Value> {
    private static final int INIT_CAPACITY = 20;

    private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private Key[] keys;      // the keys
    private Value[] vals;    // the values
    private boolean resize;
    private int invalidKeys = 0;


    /**
     * Initializes an empty symbol table.
     */
    public LinearProbingHashTable() {
        this(INIT_CAPACITY, false);
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     *
     * @param capacity the initial capacity
     */
    public LinearProbingHashTable(int capacity, boolean resize) {
        this.resize = resize;
        m = capacity;
        n = 0;
        keys = (Key[])   new Object[m];
        vals = (Value[]) new Object[m];
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
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key};
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and M-1
    private int hash(Key key) {
        int hashC = (13 * key.hashCode()) % m;
        //System.out.println(hashC);

        return hashC;

    }

    // resizes the hash table to the given capacity by re-hashing all of the keys
    private void resize(int capacity) {
        LinearProbingHashTable<Key, Value> temp = new LinearProbingHashTable<Key, Value>(capacity, true);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null && vals[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m    = temp.m;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {
            delete(key);
            return;
        }

 //        double table size if 50% full
        if (n - invalidKeys >= m/2 && resize) {
            System.out.println(invalidKeys);
            resize(2*m);
            invalidKeys = 0;
        }

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        n++;
        System.out.println(this);
    }

    /**
     * Returns the value associated with the specified key.
     * @param key the key
     * @return the value associated with {@code key};
     *         {@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }

    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).    
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        // delete key and associated value
        //keys[i] = null;
        vals[i] = null;

        // TODO
        // rehash all keys in same cluster
//        i = (i + 1) % m;
//        while (keys[i] != null) {
//            // delete keys[i] an vals[i] and reinsert
//            Key   keyToRehash = keys[i];
//            Value valToRehash = vals[i];
//            keys[i] = null;
//            vals[i] = null;
//            n--;
//            put(keyToRehash, valToRehash);
//            i = (i + 1) % m;
//        }

        n--;


        // halves size of array if it's 12.5% full or less
        if (n - invalidKeys > 0 && n - invalidKeys <= m/8 && resize){
            resize(m/2);
        }

        assert check();
    }

    /**
     * Returns all keys in this symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in this symbol table
     */
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++)
            if (keys[i] != null) queue.enqueue(keys[i]);
        return queue;
    }

    // integrity check - don't check after each put() because
    // integrity not maintained during a delete()
    private boolean check() {

        // check that hash table is at most 50% full
        if (m < 2*n) {
            System.err.println("Hash table size m = " + m + "; array size n = " + n);
            return false;
        }

        // check that each key in table can be found by get()
        for (int i = 0; i < m; i++) {
            if (keys[i] == null) continue;
            else if (get(keys[i]) != vals[i]) {
                System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {

        String string = "[";
        for (int i = 0; i < keys.length - 1; i++) {
            //string += " (k: " + keys[i] + ", v: " + vals[i] + ")";
            string += vals[i] + "|" + keys[i] + ", ";
        }

        string += "]";


         return string;

    }

    /**
     * Unit tests the {@code LinearProbingHashTable} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) { 
//        LinearProbingHashTable<Character, Integer> st = new LinearProbingHashTable<>();
//
//        Character[] charList = new Character[]{'V', 'E', 'R', 'Y', 'I', 'M', 'P', 'O', 'R', 'T', 'A', 'N', 'T', 'P', 'E', 'R', 'S', 'O', 'N'};
//        for (int i = 0; i < charList.length; i++) {
//            st.put(charList[i], i);
//
//        }
//        System.out.println(st);



        Character[] charList2 = new Character[]{'V', 'E', 'R', 'Y', 'I', 'M', 'P', 'O', 'R', 'T', 'A', 'N', 'T', 'P', 'E', 'R', 'S', 'O', 'N'};
        LinearProbingHashTable<Character, Integer> st2 = new LinearProbingHashTable<>(charList2.length, true);
        for (int i = 0; i < charList2.length; i++) {
            st2.put(charList2[i], i);
        }

        st2.delete('V');


//        for (int i = 0; i < charList2.length; i++) {
//            st2.delete(charList2[i]);
//        }



        System.out.println("after delete: ");
        System.out.println(st2);


    }
}

/******************************************************************************
 *  Copyright 2002-2018, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of adp4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://adp4.cs.princeton.edu
 *
 *
 *  adp4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  adp4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with adp4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
