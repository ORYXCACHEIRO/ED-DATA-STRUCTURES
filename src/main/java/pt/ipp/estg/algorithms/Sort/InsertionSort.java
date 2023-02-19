package pt.ipp.estg.algorithms.Sort;

public class InsertionSort<T extends Comparable<T>> {
    public static <T extends Comparable<? super T>> void sort(T[] array) {
        for (int i = 1; i < array.length; i++) {
            T key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j].compareTo(key) > 0) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }
    }
}
