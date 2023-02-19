package pt.ipp.estg.algorithms.Search;

public class BinarySearchRecursive<T> {
    public static <T extends Comparable<? super T>> int search(T[] array, T target, int low, int high) {
        if (low <= high) {
            int mid = (low + high) / 2;
            int cmp = array[mid].compareTo(target);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                return search(array, target, mid + 1, high);
            } else {
                return search(array, target, low, mid - 1);
            }
        }

        return -1;
    }
}
