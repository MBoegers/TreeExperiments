package io.github.mboegers.list.oo;

public interface List<T> {
    /**
     * Evaluates the beginning of the list.
     * <ul>
     *     <li><code>[1,2,3].head() := 1</code></li>
     *     <li><code>[].head() := error</code></li>
     * </ul>
     *
     * @return first element of the list
     * @throws IndexOutOfBoundsException if list is empty
     */
    T head();

    /**
     * Provides the tail of the list, assuming the current List the head.
     * <ul>
     *     <li><code>[1,2,3].tail() := [2,3]</code></li>
     *     <li><code>[].tail() := []</code></li>
     * </ul>
     *
     * @return list without the first element
     */
    List<T> tail();

    /**
     * Evaluates if a list contains a give value
     * <ul>
     *     <li><code>[1,2,3].contains(2) := true</code></li>
     *     <li><code>[1,2,3].contains(4) := false</code></li>
     *     <li><code>[].contains(x) := false</code></li>
     * </ul>
     *
     * @param value to determine
     * @return true iff the value is in the list
     */
    boolean contains(T value);
}
