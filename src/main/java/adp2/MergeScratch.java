//package adp2;
//
//public class MergeScratch {
//    /**
//     * [ 1 2 3 4 | 5 6 7 8 ]
//     */
//
//    public static void merge(Comparable[] array, Comparable[] aux, int lo, int mid, int hi) {
//        int i = lo;
//        int j = mid + 1;
//
//        // copy into aux array
//        for (int n = lo; n <= hi; n++) {
//            aux[n] = array[n];
//        }
//
//        for (int n = lo; n <= hi; n++) {
//            if (i > mid) {
//                array[n] = aux[j++];
//            } else if (j > hi) {
//                array[n] = aux[i++];
//            } else if (getLess(aux[j], aux[i])) {
//                array[n] = aux[j++];
//            } else {
//                array[n] = aux[i++];
//            }
//        }
//
//
//    }
//}
