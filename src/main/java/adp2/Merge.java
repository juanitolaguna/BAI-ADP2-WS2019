package adp2;

import java.util.Arrays;

/**
 * Merge
 */
public class Merge {

  /**
   * Top down Merge Sort
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
   * Bottom Up merge sort
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


  /**
   * Merge aLeft & aRight into array
   * @param aLeft
   * @param aRight
   * @param array
   */
  public static void mergeTDAry(Comparable[] aLeft, Comparable[] aRight, Comparable[] array) {
    int i = 0, j = 0;
    while (i + j < array.length) {
      if (j == aRight.length || (i < aLeft.length && less(aLeft[i], aRight[j])))
        // copy ith element of aLeft and increment i
        array[i+j] = aLeft[i++];
      else
        // copy jth element of a2 and increment j
        array[i+j] = aRight[j++];
    }
  }

//  /** Merge-sort contents of array S. */
//  public static <K> void mergeSort(K[] S) {
//    int n = S.length;
//    if (n < 2) return;                        // array is trivially sorted
//    // divide
//    int mid = n/2;
//    K[] S1 = Arrays.copyOfRange(S, 0, mid);   // copy of first half
//    K[] S2 = Arrays.copyOfRange(S, mid, n);   // copy of second half
//    // conquer (with recursion)
//    mergeSort(S1);                      // sort copy of first half
//    mergeSort(S2);                      // sort copy of second half
//    // merge results
//    mergeTDAry(S1, S2, S);               // merge sorted halves back into original
//  }






  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  public static void main(String[] args) {
    Queue<Comparable> q1 = new Queue<>();

    q1.enqueue(1);
    q1.enqueue(2);
    q1.enqueue(3);
    q1.enqueue(5);
    q1.enqueue(19);
    q1.enqueue(4);
    q1.enqueue(17);
    q1.enqueue(80);
    q1.enqueue(11);

    System.out.println(sortBU(q1));
  }
}