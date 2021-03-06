/*
 *  Compilation:  javac QuickBars.java
 *  Execution:    java QuickBars m n
 *  Dependencies: StdDraw.java
 *
 *  Sort n random real numbers between 0 and 1 using quicksort with
 *  cutoff to insertion sort and median-of-3 partitioning.
 *
 *  Visualize the results by ploting bars with heights proportional
 *  to the values.
 *
 *  % java QuickBars 1000 75
 *
 *  Comments
 *  --------
 *   - if image is too large, it may not display properly but you can
 *     still save it to a file
 *
 */

package adp2;

import algs4.StdDraw;
import algs4.StdRandom;

import java.util.HashMap;
import java.util.Map;

public class QuickBars {
    private static final int VERTICAL = 70;
    private static final int CUTOFF = 8;
    private static int rows;
    private static int row = 0;

    // partition the subarray a[lo .. hi] by returning an index j
    // so that a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
    private static int partition(double[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        double v = a[lo];
        while (true) {

            // find item on lo to swap
            while (less(a[++i], v))
                if (i == hi) break;

            // find item on hi to swap
            while (less(v, a[--j]))
                if (j == lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
        }

        // put v = a[j] into position
        exch(a, lo, j);

        // with a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }

    public static void sort(double[] a, Median median) {
        show(a, 0, 0, -1, a.length - 1);
        sort(a, 0, a.length - 1, median);
        show(a, 0, 0, -1, a.length - 1);
    }

    // quicksort the subarray from a[lo] to a[hi]
    private static void sort(double[] a, int lo, int hi, Median median) {
        // cutoff to insertion sort
        int n = hi - lo + 1;
        if (n <= CUTOFF) {
            insertionSort(a, lo, hi);
            // show(a, lo, -1, -1, hi);
            return;
        }

        int m = median.equals(Median.THREE) ? median3(a, lo, lo + n / 2, hi) : median5(a, lo, lo + n / 4, lo + n / 2, hi - n / 4, hi);
        exch(a, m, lo);

        int j = partition(a, lo, hi);
        show(a, lo, j, j, hi);
        sort(a, lo, j - 1, median);
        sort(a, j + 1, hi, median);
    }

    // sort from a[lo] to a[hi] using insertion sort
    private static void insertionSort(double[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1]); j--)
                exch(a, j, j - 1);
    }


    // return the index of the median element among a[i], a[j], and a[k]
    private static int median3(double[] a, int i, int j, int k) {
        return (less(a[i], a[j]) ?
                (less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i) :
                (less(a[k], a[j]) ? j : less(a[k], a[i]) ? k : i));
    }

    // return the index of the median element among a[i], a[j], a[k], a[l] and a[m]
    private static int median5(double[] a, int i, int j, int k, int l, int m) {
        Map<Comparable, Integer> map = new HashMap<>();
        map.put(a[i], i);
        map.put(a[j], j);
        map.put(a[k], k);
        map.put(a[l], l);
        map.put(a[m], m);

        Comparable[] median = new Comparable[5];
        median[0] = a[i];
        median[1] = a[j];
        median[2] = a[k];
        median[3] = a[l];
        median[4] = a[m];

        MergeSort.sortTD(median);

        return map.get(median[2]);
    }

    private static boolean less(double v, double w) {
        return v < w;
    }

    private static void exch(double[] a, int i, int j) {
        double t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    // draw one row of trace
    private static void show(double[] array, int lo, int lt, int gt, int hi) {
        double y = rows - row - 1;
        for (int k = 0; k < array.length; k++) {
            if (k < lo) StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            else if (k > hi) StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            else if (k >= lt && k <= gt) StdDraw.setPenColor(StdDraw.BOOK_RED);
            else StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(k, y + array[k] * 0.25, 0.25, array[k] * 0.25);
        }
        row++;
    }

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        Median median = Median.valueOf(args[2]);
        double[] a = new double[n];
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            a[i] = (1 + StdRandom.uniform(m)) / (double) m;
            System.out.println(a[i]);
            b[i] = a[i];
        }

        StdDraw.enableDoubleBuffering();

        // precompute the number of rows
        rows = 0;
        sort(b, median);
        rows = row;
        row = 0;
        StdDraw.clear();

        System.out.println(rows);

        StdDraw.setCanvasSize(800, rows * VERTICAL);
        StdDraw.show();
        StdDraw.square(0.5, 0.5, 0.5);
        StdDraw.setXscale(-1, n);
        StdDraw.setYscale(-0.5, rows);
        StdDraw.show();
        sort(a, median);
        StdDraw.show();
    }

    enum Median {
        THREE, FIVE
    }
}