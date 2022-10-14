package io.github.mboegers.list.jfp;

import java.util.Objects;

public class SingleLinkedListOperations {

    @SafeVarargs
    static <T> SingleLinkedList<T> of(T... values) {
        if (values.length == 0) return new SingleLinkedList.Empty<>();

        SingleLinkedList<T> current = new SingleLinkedList.Empty<>();
        for (int i = values.length - 1; i >= 0; i--) {
            current = new SingleLinkedList.Element<>(values[i], current);
        }
        return current;
    }

    /**
     * Evaluates the beginning of the list.
     * <ul>
     *     <li><code>head([1,2,3]) := 1</code></li>
     *     <li><code>head([]) := error</code></li>
     * </ul>
     *
     * @return first element of the list
     * @throws IndexOutOfBoundsException if list is empty
     */
    static <T> T head(SingleLinkedList<T> list) {
        return switch (list) {
            case SingleLinkedList.Empty<T> __ -> throw new IndexOutOfBoundsException("List is empty!");
            case SingleLinkedList.Element<T> e -> e.value();
        };
    }

    /**
     * Provides the tail of the list, assuming the current List the head.
     * <ul>
     *     <li><code>tail([1,2,3]) := [2,3]</code></li>
     *     <li><code>tail([]) := []</code></li>
     *     <li><code>tail(null) := []</code></li>
     * </ul>
     *
     * @return list without the first element
     */
    static <T> SingleLinkedList<T> tail(SingleLinkedList<T> list) {
        return switch (list) {
            // case null, SingleLinkedList.Empty<T> e -> e; // geht führt aber zu einem null als return!
            case null -> new SingleLinkedList.Empty<>();
            case SingleLinkedList.Empty<T> e -> e;
            case SingleLinkedList.Element<T>(T __,SingleLinkedList<T> next) -> next;
        };
    }

    /**
     * Evaluates if a list contains a give value
     * <ul>
     *     <li><code>contains([1,2,3],2) := true</code></li>
     *     <li><code>contains([1,2,3]4) := false</code></li>
     *     <li><code>contains([]) := false</code></li>
     * </ul>
     *
     * @param value to determine
     * @return true iff the value is in the list
     */
    static <T> boolean contains(T value, SingleLinkedList<T> list) {
        return switch (list) {
            case null, SingleLinkedList.Empty<T> __ -> false;
            case SingleLinkedList.Element<T>(T v,SingleLinkedList<T> __)when Objects.equals(value, v) -> true;
            case SingleLinkedList.Element<T>(T __,SingleLinkedList<T> next) -> contains(value, next);
        };
    }
}
