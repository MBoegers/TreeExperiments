package io.github.mboegers.trees.bitree.jfp.model;

final class EmptyTree implements Tree {
    @Override
    public boolean equals(Object obj) {
        return obj instanceof EmptyTree;
    }

    @Override
    public String toString() {
        return "NIL";
    }
}
