package pt.ipp.estg.algorithms.Sort;

import java.util.Arrays;
import java.util.Comparator;

public class MergeSort<T> {
    public static <T> void sort(T[] array, Comparator<T> comparator) {
        if (array.length > 1) {
            int mid = array.length / 2;
            T[] left = Arrays.copyOfRange(array, 0, mid);
            T[] right = Arrays.copyOfRange(array, mid, array.length);

            sort(left, comparator);
            sort(right, comparator);
            merge(array, left, right, comparator);
        }
    }

    private static <T> void merge(T[] array, T[] left, T[] right, Comparator<T> comparator) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) < 0) {
                array[k] = left[i];
                i++;
            } else {
                array[k] = right[j];
                j++;
            }

            k++;
        }

        while (i < left.length) {
            array[k] = left[i];
            i++;
            k++;
        }

        while (j < right.length) {
            array[k] = right[j];
            j++;
            k++;
        }
    }
}
