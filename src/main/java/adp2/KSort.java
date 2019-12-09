package adp2;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

// Data structure for Min Heap
class KSort {
  // Function to sort a K-Sorted Array
  public static void sort(List<Integer> list, int k) {
    // create an empty min heap and insert first k+1 elements in the heap
    // head tail
    PriorityQueue<Integer> pq = new PriorityQueue<>(list.subList(0, k + 1)); // [1,4,5]
    // System.out.println(pq);

    int index = 0;

    // do for remaining elements of the array
    // i = 4 -> 3 < 10
    for (int i = k + 1; i < list.size(); i++) {
      // pop top element from min-heap and assign it to
      // next available array index
      list.set(index++, pq.poll());
      System.out.println("1: " + pq);

      // System.out.println("List after poll: " + list.get(0));

      // push next array element into min-heap
      pq.add(list.get(i));
      System.out.println("2: " + pq);
      // System.out.println("I: " + i + " pq: " + pq + " List: " + list); // [2,4,5]
    }

    // pop all remaining elements from the min heap and assign it to
    // next available array index
    while (!pq.isEmpty()) {
      list.set(index++, pq.poll());
    }
  }

  public static void main(String[] args) {

    // pq add
    // [1,4,5] -> [1,5,4] ->[1]
    // [1,2,3] -> 2 ->[2,1]
    // [1,2,3] -> 3 ->[3,2,1] -> 321

    // pq(123);

    List<Integer> list = Arrays.asList(1, 4, 5, 2, 3, 7, 8, 6, 10, 9);
    int k = 2;

    sort(list, k);
    System.out.println(list);

    // PriorityQueue<Integer> prioQueue = new PriorityQueue<>(Arrays.asList(4, 5, 3,
    // 7));
    // System.out.println(prioQueue.poll());
    // System.out.println(prioQueue.poll());
    // System.out.println(prioQueue.poll());
    // System.out.println(prioQueue.poll());
  }
}