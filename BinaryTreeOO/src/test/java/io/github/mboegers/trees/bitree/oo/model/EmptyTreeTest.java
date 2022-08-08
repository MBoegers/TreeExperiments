package io.github.mboegers.trees.bitree.oo.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class EmptyTreeTest {

    @Test
    void create() {
        Tree t = new EmptyTree();

        assertThat(t).isInstanceOf(EmptyTree.class);
    }

    @Nested
    class Delete {
        @ParameterizedTest
        @NullSource
        @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
        void empty(Integer value) {
            var emptyTree = new EmptyTree();

            var resultingTree = emptyTree.delete(value);

            assertThat(resultingTree).isEqualTo(emptyTree);
        }
    }

    @Nested
    class Insert {
        @Test
        void insertRoot() {
            var emptyTree = new EmptyTree();
            var expectedTree = new SubTree(new EmptyTree(), 3, new EmptyTree());

            var containingTree = emptyTree.insert(3);

            assertThat(containingTree).isEqualTo(expectedTree);
        }
    }

    @Nested
    class Contains {
        @ParameterizedTest
        @NullSource
        @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
        void empty(Integer value) {
            var emptyTree = new EmptyTree();

            assertThat(emptyTree.contains(value)).isFalse();
        }
    }
}