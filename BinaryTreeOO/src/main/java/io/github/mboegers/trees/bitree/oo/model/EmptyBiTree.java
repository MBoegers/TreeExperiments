package io.github.mboegers.trees.bitree.oo.model;

/**
 * Instances of this class represent an empty node with no children
 */
final class EmptyBiTree implements BiTree {
    /**
     * {@inheritDoc}
     */
    @Override
    public BiTree insert(int value) {
        return new SubBiTree(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(int value) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BiTree delete(int value) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof EmptyBiTree;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "NIL";
    }
}
