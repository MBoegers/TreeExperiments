package io.github.mboegers.trees.bitree.oo.model;

final class EmptyTree implements Tree {
    @Override
    public Tree insert(Integer value) {
        return new SubTree(value);
    }

    @Override
    public boolean contains(Integer value) {
        return false;
    }

    @Override
    public Tree delete(Integer value) {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EmptyTree;
    }

    @Override
    public String toString() {
        return "NIL";
    }
}
