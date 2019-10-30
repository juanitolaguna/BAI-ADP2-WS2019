package adp2;

/**
 * Merge
 */
public class Merge {

  /**
   * Top Down
   * 
   * @param queue
   * @return
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

  public static Queue sortBU(Queue<Comparable> queue) {
    Queue<Queue> q = new Queue();

    queue.forEach(e -> {
      Queue<Comparable> tmp = new Queue<>();
      tmp.enqueue(e);
      // System.out.println("Tmp: " + tmp);
      q.enqueue(tmp);
      System.out.println("q: " + q);
    });

    while (q.size() > 1)
      q.enqueue(merge(q.dequeue(), q.dequeue()));

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

  public static Queue<Comparable> merge1(Queue<Comparable> a, Queue<Comparable> b) {
    Queue<Comparable> c = new Queue<>();
    while (!a.isEmpty() || !b.isEmpty()) {
      if (a.isEmpty())
        c.enqueue(b.dequeue());
      else if (b.isEmpty())
        c.enqueue(a.dequeue());
      else if (a.peek().compareTo(b.peek()) < 0)
        c.enqueue(a.dequeue());
      else
        c.enqueue(b.dequeue());
    }
    return c;
  }

  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  public static void main(String[] args) {
    Queue<Comparable> q1 = new Queue<>();
    Queue<Comparable> q2 = new Queue<>();

    q1.enqueue(1);
    q1.enqueue(2);
    q1.enqueue(3);
    q1.enqueue(5);
    q1.enqueue(19);
    q1.enqueue(4);
    q1.enqueue(17);
    q1.enqueue(80);
    q1.enqueue(11);

    // q2.enqueue(5);
    // q2.enqueue(6);
    // q2.enqueue(7);
    
    System.out.println(sortBU(q1));
  }
}