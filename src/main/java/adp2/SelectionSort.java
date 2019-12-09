package adp2;

import algs4.StdIn;
import algs4.StdOut;

public class SelectionSort {
    public static void sort(Comparable[] array) {
        int N = array.length;

        for (int i = 0; i < N; i++) {
            int min = i;

            for (int j = i+1; j < N; j++) {
                if (less(array[j] , array[min])) {
                    min = j;
                }
            }
            exch(array, i, min);
            assert isSorted(array, 0, i);
        }
        assert isSorted(array);
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    /**
     *  exchange a[i] and a[j] in place
     */
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
            System.out.println(a[i]);
        }
    }


    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        for (String string : a) {
            System.out.println(string);
        }
        SelectionSort.sort(a);
        show(a);
    }




}
