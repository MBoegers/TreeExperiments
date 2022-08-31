package io.github.mboegers.trees.bitree.oo.model;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * A Tree containing at least a value.
 * Instances of this class represent nodes or leaf within a binary tree.
 *
 * @param left  the left child of this node
 * @param value the value stored in this node
 * @param right the right child of this node
 */
record SubBiTree(BiTree left, int value, BiTree right) implements BiTree {
    SubBiTree {
        Objects.requireNonNull(left, "null is not allowed as child, use EmptyTree instead");
        Objects.requireNonNull(right, "null is not allowed as child, use EmptyTree instead");
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

    /**
     * {@inheritDoc}
     */
    @Override
    public BiTree insert(int value) {
        if (value < this.value)
            return new SubBiTree(left.insert(value), this.value, right);
        else if (value > this.value)
            return new SubBiTree(left, this.value, right.insert(value));
        else
            return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(int value) {
        return this.value == value
                || left.contains(value)
                || right.contains(value);
    }

    /**
     * {@inheritDoc}
     */
    BiTree insertInRightTree(BiTree t) {
        if (right instanceof SubBiTree r) // insert somewhere right
            return new SubBiTree(left, value, r.insertInRightTree(t));
        else // insert here
            return new SubBiTree(value, t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BiTree delete(int v) {
        if (value == v) { // this node contains the value to delete
            if (left instanceof EmptyBiTree && right instanceof EmptyBiTree)
                // A1 - no children => delete node
                return new EmptyBiTree();
            else if (left instanceof EmptyBiTree && right instanceof SubBiTree)
                // A2.R - one child on the right => replace with right
                return right;
            else if (left instanceof SubBiTree && right instanceof EmptyBiTree)
                // A2.L - one child on the left => replace with left
                return left;
            else if (left instanceof SubBiTree l && right instanceof SubBiTree)
                // B: has children on both sides => replace node to delete with left and insert right three at the right
                return l.insertInRightTree(right);
            else
                throw new IllegalStateException("This should not be possible");
        } else if (left.contains(v)) // the left subtree contains the node to delete => dive deeper left
            return new SubBiTree(left.delete(v), value, right);
        else if (right.contains(v)) // the right subtree contains the node to delete => dive deeper right
            return new SubBiTree(left, value, right.delete(v));
        else // value is not in tree so abort operation
            return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void inOrder(Consumer<Integer> callback) {
        left.inOrder(callback);
        callback.accept(value);
        right.inOrder(callback);
    }

    @Override
    public String toString() {
        return "S[l=%s, v=%s, r=%s]".formatted(left, value, right);
    }
}
