package io.github.mboegers.list.jfp;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SingleLinkedListOperationTest {

    @Nested
    class Tail {
        @Test
        void nullList() {
            SingleLinkedList<Integer> list = null;

            var tail = SingleLinkedListOperations.tail(list);

            assertThat(tail).isEqualTo(SingleLinkedListOperations.of());
        }

        @Test
        void empty() {
            var list = SingleLinkedListOperations.of();

            var tail = SingleLinkedListOperations.tail(list);

            assertThat(tail).isEqualTo(SingleLinkedListOperations.of());
        }

        @Test
        void values() {
            var list = SingleLinkedListOperations.of(1, 2, 3);

            var head = SingleLinkedListOperations.tail(list);

            assertThat(head).isEqualTo(SingleLinkedListOperations.of(2, 3));
        }

        @Test
        void idempotent() {
            var list = SingleLinkedListOperations.of(1, 2, 3);

            var head1 = SingleLinkedListOperations.head(list);
            var head2 = SingleLinkedListOperations.head(list);

            assertThat(head1).isNotNull().isEqualTo(head2);
        }

    }

    @Nested
    class Head {
        @Test
        void nullList() {
            SingleLinkedList<Integer> list = null;

            assertThatThrownBy(() -> SingleLinkedListOperations.head(list))
                    .isInstanceOf(NullPointerException.class);
        }

        @Test
        void empty() {
            var list = SingleLinkedListOperations.of();

            assertThatThrownBy(() -> SingleLinkedListOperations.head(list))
                    .isInstanceOf(IndexOutOfBoundsException.class);
        }

        @Test
        void values() {
            Integer firstElm = 1;
            var list = SingleLinkedListOperations.of(firstElm, 2, 3);

            var head = SingleLinkedListOperations.head(list);

            assertThat(head).isEqualTo(firstElm);
        }

        @Test
        void idempotent() {
            var list = SingleLinkedListOperations.of(1, 2, 3);

            var head1 = SingleLinkedListOperations.tail(list);
            var head2 = SingleLinkedListOperations.tail(list);

            assertThat(head1).isNotNull().isEqualTo(head2);
        }
    }

    @Nested
    class Contains {
        @ParameterizedTest
        @ValueSource(ints = {1, 3, 5, 8374, 44})
        void nullList(int value) {
            SingleLinkedList<Integer> list = null;

            var contains = SingleLinkedListOperations.contains(value, list);

            assertThat(contains).isFalse();
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 3, 5, 8374, 44})
        void empty(int value) {
            SingleLinkedList<Integer> list = SingleLinkedListOperations.of();

            var contains = SingleLinkedListOperations.contains(value, list);

            assertThat(contains).isFalse();
        }

        @Test
        void values_first() {
            Integer first = 1;
            var list = SingleLinkedListOperations.of(first, 2, 3);

            var contains = SingleLinkedListOperations.contains(first, list);

            assertThat(contains).isTrue();
        }

        @Test
        void values_middle() {
            Integer middle = 2;
            var list = SingleLinkedListOperations.of(1, middle, 3);

            var contains = SingleLinkedListOperations.contains(middle, list);

            assertThat(contains).isTrue();
        }

        @Test
        void values_last() {
            Integer last = 3;
            var list = SingleLinkedListOperations.of(1, 2, last);

            var contains = SingleLinkedListOperations.contains(last, list);

            assertThat(contains).isTrue();
        }

        @Test
        void idempotent() {
            var list = SingleLinkedListOperations.of(1, 2, 3);

            var containsFirst = SingleLinkedListOperations.contains(2, list);
            var containsSecond = SingleLinkedListOperations.contains(2, list);

            assertThat(containsFirst).isNotNull().isEqualTo(containsSecond);
        }
    }
}
