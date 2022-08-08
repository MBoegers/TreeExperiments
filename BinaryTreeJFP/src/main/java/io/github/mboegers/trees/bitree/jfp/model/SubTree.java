package io.github.mboegers.trees.bitree.jfp.model;

record SubTree(Tree left, Integer value, Tree right) implements Tree {
    public SubTree(Integer value) {
        this(new EmptyTree(), value, new EmptyTree());
    }

    public SubTree(Tree left, Integer value) {
        this(left, value, new EmptyTree());
    }

    public SubTree(Integer value, Tree right) {
        this(new EmptyTree(), value, right);
    }

    @Override
    public String toString() {
        return "S[l=%s, v=%s, r=%s]".formatted(left, value, right);
    }
}
