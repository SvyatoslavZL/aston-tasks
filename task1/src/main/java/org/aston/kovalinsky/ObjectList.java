package org.aston.kovalinsky;

import java.util.Arrays;
import java.util.Objects;

/**
 * <p>The custom {@code ArrayList} with limited functionality - resizable-array
 * implementation of the {@code Listable} interface ({@code List} analog).
 * Implements all listable operations and permits all elements, including null.
 * <p><strong>The class is not synchronized.</strong>
 *
 * <p>Each {@code ObjectList} instance as in {@code ArrayList}, has <i>size</i>
 * and <i>capacity</i>. The size is the number of elements in the list.
 * The capacity is the size of the array used to store the elements in the list.
 * It is always at least as large as the list size. Capacity is automatically increased
 * when items are added to the {@code ObjectList} or the size is equal to the capacity.
 *
 * <p>In addition {@code ObjectList} can be sorted using {@code Sorting} class.
 *
 * @param <E> the type of elements in this list
 * @author Svyatoslav Kovalinsky
 * @see Listable
 * @see Sorting
 */

public class ObjectList<E> implements Listable<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int CAPACITY_MULTIPLIER = 2;

    private int size = 0;
    private Object[] listData = new Object[DEFAULT_CAPACITY];


    /**
     * Appends the specified element to the end of this list.
     * After adding an element, if the size and length of the list array are the same,
     * the capacity of the sheet is increased
     *
     * @param elementToAdd element to be appended to this list
     */
    @Override
    public void add(E elementToAdd) {
        listData[size] = elementToAdd;
        size++;
        if (size == listData.length) {
            increaseCapacity();
        }
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * If the index is occupied by another element, it shifts it and all
     * subsequent elements to the right and then inserts the element.
     *
     * @param index        index at which the specified element is to be inserted
     * @param elementToAdd element to be inserted
     * @throws IndexOutOfBoundsException the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    @Override
    public void add(int index, E elementToAdd) {
        Objects.checkIndex(index, size + 1);
        size++;
        if (size == listData.length) {
            increaseCapacity();
        }
        for (int i = size; i > index; i--) {
            listData[i] = listData[i - 1];
        }
        listData[index] = elementToAdd;
    }

    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index        index of the element to replace
     * @param elementToSet element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E elementToSet) {
        Objects.checkIndex(index, size);
        E oldElement = (E) listData[index];
        listData[index] = elementToSet;
        return oldElement;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        Objects.checkIndex(index, size);
        return (E) listData[index];
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts all subsequent elements to the left by subtracting one from their indices.
     *
     * @param index the index of the element to be removed
     * @throws IndexOutOfBoundsException the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    @Override
    public void remove(int index) {
        Objects.checkIndex(index, size);
        for (int i = index; i < size - 1; i++) {
            listData[i] = listData[i + 1];
        }
        listData[--size] = null;
    }

    /**
     * Removes all of the elements from this list.
     * The list will be empty after this call returns.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            listData[i] = null;
        }
        size = 0;
    }

    /**
     * @return {@code true} if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return an array containing all of the elements in this list in
     * proper sequence from first to last element
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(listData, size);
    }

    /**
     * Returns a string representation of this list. The string
     * representation consists of the list elements in proper sequence,
     * enclosed in square brackets ({@code "[]"}).
     * Adjacent elements are separated by the characters {@code ", "}
     * (comma and space).
     *
     * @return a string representation of this list
     */
    @SuppressWarnings("unchecked")
    @Override
    public String toString() {
        if (size == 0) return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            E element = (E) listData[i];
            sb.append(element == this ? "(this Collection)" : element);
            if (i == size - 1) {
                sb.append(']');
            } else {
                sb.append(',').append(' ');
            }
        }
        return sb.toString();
    }

    private void increaseCapacity() {
        Object[] newListData = new Object[listData.length * CAPACITY_MULTIPLIER];
        System.arraycopy(listData, 0, newListData, 0, listData.length);
        listData = newListData;
    }
}






