package io.github.mboegers.trees.bitree.jfp.model;

import java.util.Objects;

/**
 * Represents a node containing at least a value and may contain left and right children
 *
 * @param left  child of this node
 * @param value stored in this node
 * @param right child of this node
 */
record SubBiTree(BiTree left, int value, BiTree right) implements BiTree {

    SubBiTree {
        Objects.requireNonNull(left, "null children are not allowed use EmptyTree");
        Objects.requireNonNull(right, "null children are not allowed use EmptyTree");
    }

    /**
     * Create a subtree containing no children and storing the value
     *
     * @param value nodes value
     */
    public SubBiTree(int value) {
        this(new EmptyBiTree(), value, new EmptyBiTree());
    }

    /**
     * Create a subtree with the given vale and a left child
     *
     * @param left  child to refer
     * @param value to store in node
     */
    public SubBiTree(BiTree left, int value) {
        this(left, value, new EmptyBiTree());
    }

    /**
     * Create a subtree with given value and a right child
     *
     * @param value to store in node
     * @param right child to refer
     */
    public SubBiTree(int value, BiTree right) {
        this(new EmptyBiTree(), value, right);
    }

    @Override
    public String toString() {
        return "S[l=%s, v=%s, r=%s]".formatted(left, value, right);
    }
}
