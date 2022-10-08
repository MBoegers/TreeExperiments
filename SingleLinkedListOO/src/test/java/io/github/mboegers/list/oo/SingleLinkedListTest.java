package io.github.mboegers.list.oo;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SingleLinkedListTest {

    @Nested
    class Tail {
        @Test
        void empty() {
            var list = SingleLinkedList.of();

            var tail = list.tail();

            Assertions.assertThat(tail).isEqualTo(SingleLinkedList.of());
        }

        @Test
        void values() {
            var list = SingleLinkedList.of(1, 2, 3);

            var head = list.tail();

            Assertions.assertThat(head).isEqualTo(SingleLinkedList.of(2, 3));
        }

        @Test
        void idempotent() {
            var list = SingleLinkedList.of(1, 2, 3);

            var head1 = list.head();
            var head2 = list.head();

            Assertions.assertThat(head1).isNotNull().isEqualTo(head2);
        }

    }

    @Nested
    class Head {
        @Test
        void empty() {
            var list = SingleLinkedList.of();

            Assertions.assertThatThrownBy(list::head).isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        void values() {
            Integer firstElm = 1;
            var list = SingleLinkedList.of(firstElm, 2, 3);

            var head = list.head();

            Assertions.assertThat(head).isEqualTo(firstElm);
        }

        @Test
        void idempotent() {
            var list = SingleLinkedList.of(1, 2, 3);

            var head1 = list.tail();
            var head2 = list.tail();

            Assertions.assertThat(head1).isNotNull().isEqualTo(head2);
        }
    }

    @Nested
    class Contains {
        @ParameterizedTest
        @ValueSource(ints = {1, 3, 5, 8374, 44})
        void empty(int value) {
            List<Integer> list = SingleLinkedList.of();

            var contains = list.contains(value);

            Assertions.assertThat(contains).isFalse();
        }

        @Test
        void values_first() {
            Integer first = 1;
            var list = SingleLinkedList.of(first, 2, 3);

            var contains = list.contains(first);

            Assertions.assertThat(contains).isTrue();
        }

        @Test
        void values_middle() {
            Integer middle = 2;
            var list = SingleLinkedList.of(1, middle, 3);

            var contains = list.contains(middle);

            Assertions.assertThat(contains).isTrue();
        }

        @Test
        void values_last() {
            Integer last = 3;
            var list = SingleLinkedList.of(1, 2, last);

            var contains = list.contains(last);

            Assertions.assertThat(contains).isTrue();
        }

        @Test
        void idempotent() {
            var list = SingleLinkedList.of(1, 2, 3);

            var containsFirst = list.contains(2);
            var containsSecond = list.contains(2);

            Assertions.assertThat(containsFirst).isNotNull().isEqualTo(containsSecond);
        }
    }
}