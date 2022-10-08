package io.github.mboegers.list.jfp;

public sealed interface SingleLinkedList<T> {
    final class Empty<T> implements SingleLinkedList<T> {
        @Override
        public boolean equals(Object obj) {
            return obj instanceof Empty<?>;
        }

        @Override
        public String toString() {
            return "NIL";
        }
    }

    record Element<T>(T value, SingleLinkedList<T> next) implements SingleLinkedList<T> {

        @Override
        public String toString() {
            return "[%s] --> %s".formatted(value, next.toString());
        }
    }
}
