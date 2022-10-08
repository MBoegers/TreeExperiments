package io.github.mboegers.list.oo;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class SingleLinkedList<T> implements List<T> {

    private final SingleLinkedList<T> next;
    private final T value;

    private SingleLinkedList(SingleLinkedList<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    @SafeVarargs
    public static <T> List<T> of(T... values) {
        if (values.length == 0) {
            return new SingleLinkedList<>(null, null);
        }

        SingleLinkedList<T> current = null;
        for (int i = values.length; i >= 1; i--) {
            current = new SingleLinkedList<>(current, values[i - 1]);
        }
        return current;
    }

    @Override
    public T head() {
        if (isNull(value)) throw new IndexOutOfBoundsException("List is empty");
        else return this.value;
    }

    @Override
    public List<T> tail() {
        return nonNull(next) ? this.next : SingleLinkedList.of();
    }

    @Override
    public boolean contains(T value) {
        return this.value != null && (this.value.equals(value) || tail().contains(value));
    }

    @Override
    public String toString() {
        return "[%s] -> %s".formatted(value, next != null ? next.toString() : "NIL");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SingleLinkedList<?> other
                && Objects.equals(this.value, other.value)
                && Objects.equals(this.next, other.next);
    }
}
