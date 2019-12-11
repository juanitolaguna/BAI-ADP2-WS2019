package adp3;

import algs4.BST;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TreeTest {

    private static Tree<String, Integer> tree;

    @BeforeAll
    static void before() {
        tree = new Tree<>();
        tree.put("S", 5);
        tree.put("E", 5);
        tree.put("X", 0);
        tree.put("A", 10);
        tree.put("R", 8);
        tree.put("C", 11);
        tree.put("H", 3);
        tree.put("M", 7);
        tree.put("P", 4);
        tree.put("L", 5);
    }

    @Test
    void oneNode() {
        BST<String, Integer> bst = new BST<>();
        bst.put("A", 10);
        Tree<String, Integer> bstree = new Tree<>(bst);
        assertTrue(bstree.isOrdered("A", "A"));
        assertTrue(bstree.isOrdered("A", "Z"));
    }

    @Test
    void fromBST() {
        BST<String, Integer> bst = new BST<>();
        bst.put("S", 5);
        bst.put("E", 5);
        bst.put("X", 0);
        bst.put("A", 10);
        bst.put("R", 8);
        bst.put("C", 11);
        bst.put("H", 3);
        bst.put("M", 7);
        bst.put("P", 4);
        bst.put("L", 5);
        Tree<String, Integer> bstree = new Tree<>(bst);
        assertTrue(bstree.isOrdered("A", "X"));
    }

    @Test
    void string() {
        String s = "[0 S-5[1 E-5[2 A-10[NULL 3 C-11[NULL NULL]] 2 R-8[3 H-3[NULL 4 M-7[5 L-5[NULL NULL] 5 P-4[NULL NULL]]] NULL]] 1 X-0[NULL NULL]]]";
        assertEquals(s, tree.toString());
    }

    @Test
    void isOrdered() {
        assertTrue(tree.isOrdered("A", "X"));
    }

    @Test
    void isOrderedWrongKeys() {
        assertFalse(tree.isOrdered("A", "B"));
    }

    @Test
    void zChangeKey() {
        tree.changeKey("H", "S");
        assertFalse(tree.isOrdered("A", "X"));
    }
}
