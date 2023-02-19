package pt.ipp.estg.algorithms.Sort;

public class MergeSort<T extends Comparable<T>> {
    public static <T extends Comparable<T>> void sort(T[] array) {
        if (array.length > 1) {
            int mid = array.length / 2;
            T[] left = (T[]) new Comparable[mid];
            T[] right = (T[]) new Comparable[array.length - mid];

            System.arraycopy(array, 0, left, 0, mid);

            if (array.length - mid >= 0) System.arraycopy(array, mid, right, 0, array.length - mid);

            sort(left);
            sort(right);
            merge(array, left, right);
        }
    }

    private static <T extends Comparable<? super T>> void merge(T[] array, T[] left, T[] right) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i].compareTo(right[j]) < 0) {
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
