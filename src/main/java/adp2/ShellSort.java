package adp2;

/**
 * ShellSort
 */
public class ShellSort {

  public static void sort(Comparable[] array) {
    int arrayLength = array.length;
    int h = 1;

    // 1, 4, 13, 40, 121, 364....
    while (h < arrayLength / 3) {
      h = 3 * h + 1;
    }

    while (h >= 1) {
      for (int i = h; i < arrayLength; i++) {

        for (int j = i; j >= h && less(array[j], array[j - h]); j = j - h) {
          System.out.println(String.format("Swap %d %d", j, j-h));
          exch(array, j, j - h);
        }

      }
      h = h / 3;
      System.out.println("update h: " + h);
    }

  }

  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  private static void exch(Object[] a, int i, int j) {
    Object swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  private static void show(Comparable[] array) {
    for (int i = 0; i < array.length; i++) {
      System.out.print(array[i] + " ");
    }
    System.out.println("\n");
  }

  public static void main(String[] args) {
    String[] unsorted = { "s", "o", "r", "t", "e", "x", "a", "m", "p", "l", "e" };
    show(unsorted);

    sort(unsorted);

    show(unsorted);
  }
}