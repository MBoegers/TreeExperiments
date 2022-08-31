package io.github.mboegers.trees.bitree.jfp.model;

/**
 * Represent a Tree containing nether a value nor children
 */
final class EmptyBiTree implements BiTree {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof EmptyBiTree;
    }

    @Override
    public String toString() {
        return "NIL";
    }
}
