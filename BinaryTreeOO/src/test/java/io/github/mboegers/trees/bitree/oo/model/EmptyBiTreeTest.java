package io.github.mboegers.trees.bitree.oo.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class EmptyBiTreeTest {

    @Test
    void create() {
        BiTree t = new EmptyBiTree();

        assertThat(t).isInstanceOf(EmptyBiTree.class);
    }

    @Nested
    class Delete {
        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
        void empty(Integer value) {
            var emptyTree = new EmptyBiTree();

            var resultingTree = emptyTree.delete(value);

            assertThat(resultingTree).isEqualTo(emptyTree);
        }
    }

    @Nested
    class Insert {
        @Test
        void insertRoot() {
            var emptyTree = new EmptyBiTree();
            var expectedTree = new SubBiTree(new EmptyBiTree(), 3, new EmptyBiTree());

            var containingTree = emptyTree.insert(3);

            assertThat(containingTree).isEqualTo(expectedTree);
        }
    }

    @Nested
    class Contains {
        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
        void empty(Integer value) {
            var emptyTree = new EmptyBiTree();

            assertThat(emptyTree.contains(value)).isFalse();
        }
    }
}