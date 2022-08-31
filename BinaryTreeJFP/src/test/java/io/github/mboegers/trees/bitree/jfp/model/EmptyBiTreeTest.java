package io.github.mboegers.trees.bitree.jfp.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
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
        @NullSource
        @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
        void empty(Integer value) {
            var emptyTree = new EmptyBiTree();

            var resultingTree = TreeOperations.delete(emptyTree, value);

            assertThat(resultingTree).isEqualTo(emptyTree);
        }
    }

    @Nested
    class Insert {
        @Test
        void insertRoot() {
            var emptyTree = new EmptyBiTree();
            var expectedTree = new SubBiTree(new EmptyBiTree(), 3, new EmptyBiTree());

            var containingTree = TreeOperations.delete(emptyTree, 3);

            assertThat(containingTree).isEqualTo(expectedTree);
        }
    }

    @Nested
    class Contains {
        @ParameterizedTest
        @NullSource
        @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
        void empty(Integer value) {
            var emptyTree = new EmptyBiTree();

            assertThat(TreeOperations.contains(emptyTree, value)).isFalse();
        }
    }
}