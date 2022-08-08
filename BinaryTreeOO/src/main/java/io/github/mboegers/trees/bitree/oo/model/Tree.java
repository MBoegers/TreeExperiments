package io.github.mboegers.trees.bitree.oo.model;

import java.util.function.Consumer;

sealed interface Tree permits EmptyTree, SubTree {

    Tree insert(Integer value);

    boolean contains(Integer value);

    Tree delete(Integer value);

    default void inOrder(Consumer<Integer> callback) {
        // nop
    }
}
