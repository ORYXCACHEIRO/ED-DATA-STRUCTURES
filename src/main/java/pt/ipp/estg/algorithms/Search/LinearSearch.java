package pt.ipp.estg.algorithms.Search;

public class LinearSearch<T> {
    public static <T extends Comparable<? super T>> int search(T[] array, T target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }

        return -1;
    }
}
