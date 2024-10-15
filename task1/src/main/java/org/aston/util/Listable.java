package org.aston.util;

/**
 * <p>The custom {@code List} with limited functionality.
 * An ordered list that allows duplicate elements and multiple null elements.
 * With this interface, user has to control over where in the list each item is inserted.
 * The user can access elements by their integer index (position in the list).
 *
 * <p>The {@code Listable} interface as {@code List} interface provides two methods to
 * insert: {@code add, set}, one method to get element: {@code get} and one method to
 * remove element: {@code remove}.
 *
 * <p>In addition the {@code Listable} interface contains methods for clearing
 * the list: {@code clear}, converting the list to an array: {@code toArray}, and checking
 * for emptiness and size: {@code isEmpty, size}.
 *
 * @param <E> the type of elements in this list
 * @author Svyatoslav Kovalinsky
 * @see ObjectList
 * @see Sorting
 */

public interface Listable<E> {

    /**
     * <p>Appends the specified element to the end of this list.
     * Null elements can be added.
     *
     * @param element element to be appended to this list
     * @throws IllegalArgumentException if some property of this element
     *                                  prevents it from being added to this list
     */
    void add(E element);

    /**
     * Inserts the specified element at the specified position in this list.
     * If the index is occupied by another element, it shifts it and all
     * subsequent elements to the right.
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IllegalArgumentException  if some property of the specified
     *                                   element prevents it from being added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index > size()})
     */
    void add(int index, E element);

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IllegalArgumentException  if some property of the specified
     *                                   element prevents it from being added to this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    E set(int index, E element);

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    E get(int index);

    /**
     * Removes the element at the specified position in this list.
     * Shifts all subsequent elements to the left by subtracting one from their indices.
     *
     * @param index the index of the element to be removed
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    void remove(int index);

    /**
     * Removes all of the elements from this list.
     * The list will be empty after this call returns.
     */
    void clear();

    /**
     * @return {@code true} if the list contains no elements
     */
    boolean isEmpty();

    /**
     * @return the number of elements in this list
     */
    int size();

    /**
     * @return an array containing all of the elements in this list in proper
     * sequence from first to last element
     */
    Object[] toArray();
}
