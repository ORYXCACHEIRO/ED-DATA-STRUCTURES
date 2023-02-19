package pt.ipp.estg.algorithms.Search;

public class BinarySearch<T> {
    public static <T extends Comparable<? super T>> int search(T[] array, T target) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = array[mid].compareTo(target);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }
}
