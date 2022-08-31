package io.github.mboegers.trees.bitree.oo.model;

import java.util.function.Consumer;

/**
 * Represents a Binary Tree.
 * Instances can be empty, e.q. no value and no children or subtrees containing at least a value and may contain
 * EmptyBiTrees e.q. no value and no children
 * SubBiTree e.q. containing at least a value and may contain a left and right child
 */
sealed interface BiTree permits EmptyBiTree, SubBiTree {

    /**
     * Insert a given value in the tree
     *
     * @param value to insert
     * @return new instance containing the value
     */
    BiTree insert(int value);

    /**
     * Check whether this tree, or its children, contains the value
     *
     * @param value to check the exitence
     * @return tree if this tree or on if the children contain this value
     */
    boolean contains(int value);

    /**
     * Delete value from this tree
     *
     * @param value to delete
     * @return new Instance not containing the value
     */
    BiTree delete(int value);

    /**
     * Little helper to traverse the tree inorder
     *
     * @param callback visitor callback
     */
    default void inOrder(Consumer<Integer> callback) {
        // nop
    }
}
