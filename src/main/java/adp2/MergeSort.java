package adp2;

import algs4.Queue;

import java.util.Arrays;
import java.util.Random;

/**
 * Merge
 */
public class MergeSort {

    public static int arrayCounter = 0;

    /* ======================= Queues ===============================*/

    /**
     * Top down Queue Merge Sort
     *
     * @param queue
     * @return queue
     */
    public static Queue<Comparable> sortTD(Queue<Comparable> queue) {
        int size = queue.size();
        if (size < 2)
            return queue;

        Queue<Comparable> q1 = new Queue<>();
        Queue<Comparable> q2 = new Queue<>();

        while (q1.size() < size / 2) {
            q1.enqueue(queue.dequeue());
        }

        while (!queue.isEmpty()) {
            q2.enqueue(queue.dequeue());
        }
        return merge(sortTD(q1), sortTD(q2));
    }


    /**
     * Bottom Up Queue merge sort
     *
     * @param queue
     * @return queue
     */
    public static Queue sortBU(Queue<Comparable> queue) {
        Queue<Queue<Comparable>> q = new Queue<>();

        // Erzeugen Sie N-Warteschlangen für N-Elemente
        queue.forEach(e -> {
            Queue<Comparable> tmp = new Queue<>();
            tmp.enqueue(e);
            q.enqueue(tmp);
            System.out.println("q: " + q);
        });

        //Mischen Sie jeweils die ersten beiden Warteschlangen
        // und legen das Ergebnis wieder in der Warteschlange ab.
        while (q.size() > 1) {
            q.enqueue(merge(q.dequeue(), q.dequeue()));
        }

        return q;

    }

    /**
     * Queue merge.
     *
     * @param q1
     * @param q2
     * @return
     */
    public static Queue<Comparable> merge(Queue<Comparable> q1, Queue<Comparable> q2) {
        Queue<Comparable> merged = new Queue<>();
        while (!q1.isEmpty() && !q2.isEmpty()) {
            if (less(q1.peek(), q2.peek())) {
                merged.enqueue(q1.dequeue());
            } else {
                merged.enqueue(q2.dequeue());
            }
        }
        while (!q1.isEmpty()) {
            merged.enqueue(q1.dequeue());
        }
        while (!q2.isEmpty()) {
            merged.enqueue(q2.dequeue());
        }
        return merged;
    }


    /* ======================= Arrays ==========================*/

  /* ================ Arrays Top Down ===============*/


  private static void sortTD(Comparable[] a, int lo, int hi,int depth){
    if (lo >= hi) return;
    int mid = lo + (hi-lo)/2;
    Comparable[] aux = new Comparable[a.length];
    sortTD(a,lo,mid,depth+1); // sortiert linke Hälfte
    sortTD(a,mid+1,hi,depth+1); // sortiert rechte Hälfte
    merge(a, aux, lo,mid,hi); // Mischt die Ergebnisse
  }

  public static void sortTD(Comparable[] a){
    Comparable[] aux = new Comparable[a.length];
    sortTD(a,0,a.length-1,0);
  }



  /**
     * Merge aLeft & aRight into array
     *
     * @param aLeft
     * @param aRight
     * @param array
     */
    public static void mergeTDAry(Comparable[] aLeft, Comparable[] aRight, Comparable[] array) {
        int i = 0, j = 0;
        while (i + j < array.length) {
            if (j == aRight.length || (i < aLeft.length && less(aLeft[i], aRight[j]))) {
                // copy ith element of aLeft and increment i
                arrayCounter++;
                array[i + j] = aLeft[i++];
            } else {
                // copy jth element of a2 and increment j
                arrayCounter++;
                array[i + j] = aRight[j++];
            }

        }
    }


    /* ================ Arrays Bottom Up ===============*/

    public static void sortBU(Comparable[] array) {
        int size = array.length;
        Comparable[] aux = new Comparable[size];

        for (int sz = 1; sz < size; sz *= 2) {
            for (int lo = 0; lo < size - sz; lo += sz + sz) {

                int mid = lo + sz - 1;
                int hi = Math.min(lo + sz + sz - 1, size - 1);
                merge(array, aux, lo, mid, hi);
            }
        }
    }

    private static void merge(Comparable[] array, Comparable[] aux, int lo, int mid, int hi) {
        //copy array
        for (int k = lo; k <= hi; k++) {
            aux[k] = array[k];
            arrayCounter += 2;
        }

//    merge back to array
        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                array[k] = aux[j++];
                arrayCounter += 2;
            } else if (j > hi) {
                array[k] = aux[i++];
                arrayCounter += 2;
            } else if (less(aux[j], aux[i])) {
                array[k] = aux[j++];
                arrayCounter += 4;
            } else {
                array[k] = aux[i++];
                arrayCounter += 4;
            }
        }
    }


    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void resetCounter() {
        arrayCounter = 0;
    }

    public static void main(String[] args) {
//        Queue<Comparable> q1 = new Queue<>();
//
//        q1.enqueue(1);
//        q1.enqueue(2);
//        q1.enqueue(3);
//        q1.enqueue(5);
//        q1.enqueue(19);
//        q1.enqueue(4);
//        q1.enqueue(17);
//        q1.enqueue(80);
//        q1.enqueue(11);
//
//        System.out.println(sortBU(q1));

      resetCounter();
        Random rand = new Random();

        for (int i = 2; i < 12; i++) {
            int size = (int) Math.pow(2, i);
            Comparable[] testArray = new Comparable[size];
            for (int j = 0; j < testArray.length; j++) {
                testArray[j] = rand.nextInt(testArray.length);
            }

//            Arrays.asList(testArray).forEach(e -> System.out.print(e + " "));
            System.out.println();
            sortTD(testArray);
            double ratio = arrayCounter / (6 * size * Math.log(size) / Math.log(2)) ;
            System.out.println("N: " + size + " Zugriffe: " + arrayCounter + " Theo: " + (6 * size * Math.log(size) / Math.log(2)) + " Ratio: " + ratio);
            resetCounter();
        }


//    Comparable[] array = {1,2,3,5,19,4,17,80,11};
//    sortBU(array);
//    for (int i = 0; i < array.length ; i++) {
//      System.out.println(array[i]);
//    }
//    System.out.println("Array zugriffe: " + arrayCounter);
//    resetCounter();
//
//    Comparable[] array2 = {1,2,3,5,19,4,17,80,11};
//    sortTD(array2);
//    for (int i = 0; i < array2.length ; i++) {
//      System.out.println(array2[i]);
//    }
//    System.out.println("Array zugriffe: " + arrayCounter);
//    resetCounter();


    }
}