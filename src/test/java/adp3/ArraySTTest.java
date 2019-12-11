package adp3;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.assertEquals;


class ArraySTTest {

    @Test
    void get() {
        ArrayST<Integer, Integer> a = new ArrayST<>();
        a.put(1, 1);
        a.put(2, -1);
        assertEquals((Integer) 1, a.get(1));
        assertEquals((Integer) (-1), a.get(2));
    }

    @Test
    void contains() {
        ArrayST<Integer, Integer> a = new ArrayST<>();
        a.put(1, 1);
        assertTrue(a.contains(1));
        a.delete(1);
        assertFalse(a.contains(1));
    }

    @Test
    void isEmpty() {
        ArrayST<Integer, Integer> a = new ArrayST<>();
        assertTrue(a.isEmpty());
        a.put(1, 1);
        assertFalse(a.isEmpty());
        a.delete(1);
        assertTrue(a.isEmpty());
    }

    @Test
    void size() {
        ArrayST<Integer, Integer> a = new ArrayST<>();
        assertEquals(0, a.size());
        a.put(1, 1);
        assertEquals(1, a.size());
        a.delete(1);
        assertEquals(0, a.size());
    }

    @Test
    void keys() {
        ArrayST<Integer, Integer> a = new ArrayST<>();
        a.put(1, 1);
        a.put(2, 2);
        a.put(3, 3);
        a.put(4, 4);

        Iterator<Integer> forwards = a.keys().iterator();
        for (int i = 1; i < 5; i++) {
            if (!forwards.hasNext()) continue;
            assertEquals(i, (int) forwards.next());
        }

        a.get(2);
        a.get(3);
        a.get(4);

        Iterator<Integer> backwards = a.keys().iterator();
        for (int i = 1; i < 5; i++) {
            if (!backwards.hasNext()) continue;
            assertEquals(5 - i, (int) backwards.next());
        }
    }
}
