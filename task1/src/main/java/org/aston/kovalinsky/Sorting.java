package org.aston.kovalinsky;

import java.util.Comparator;

/**
 * <p>This class consists exclusively of static sorting methods that operate on
 * {@code ObjectList}. It contains quicksort algorithm that operate on {@code ObjectList}
 * and modify it.
 *
 * @author Svyatoslav Kovalinsky
 * @see Listable
 */

public class Sorting {

    /**
     * Suppresses default constructor, ensuring non-instantiability.
     */
    private Sorting() {
    }

    /**
     * Sorts the specified list into ascending order, according to the natural ordering of its elements.
     * All elements in the list must implement the {@code Comparable} interface.
     * <p>The method sorts the elements of the list by modifying it.
     * <p>Sorting uses a quick sorting algorithm
     *
     * @param <T>  the class of the objects in the list
     * @param list the list to be sorted.
     * @throws NullPointerException if the specified list is null.
     * @see Comparable
     */
    public static <T extends Comparable<? super T>> void sort(ObjectList<T> list) {
        quickSort(list, 0, list.size() - 1, Comparator.naturalOrder());
    }

    /**
     * Sorts the specified list using {@code Comparator}, according to the order induced by the
     * specified comparator.
     * <p>The method sorts the elements of the list by modifying it.
     * <p>Sorting uses a quick sorting algorithm
     *
     * @param <T>  the class of the objects in the list
     * @param list the list to be sorted.
     * @throws NullPointerException if the specified list or comparator is null.
     * @see Comparator
     */
    public static <T> void sort(ObjectList<T> list, Comparator<T> comparator) {
        quickSort(list, 0, list.size() - 1, comparator);
    }

    private static <T> void quickSort(ObjectList<T> list, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivot = partition(list, low, high, comparator);
            quickSort(list, low, pivot - 1, comparator);
            quickSort(list, pivot + 1, high, comparator);
        }
    }

    private static <T> int partition(ObjectList<T> list, int low, int high, Comparator<T> comparator) {
        int middle = low + (high - low) / 2;
        T pivot = list.get(middle);

        swap(list, middle, high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) < 0) {
                i++;
                swap(list, i, j);
            }
        }

        swap(list, i + 1, high);
        return i + 1;
    }

    private static <T> void swap(ObjectList<T> list, int firstNumber, int secondNumber) {
        T temp = list.get(firstNumber);
        list.set(firstNumber, list.get(secondNumber));
        list.set(secondNumber, temp);
    }
}
