package io.github.mboegers.trees.bitree.jfp.model;

final class TreeOperations {

    private TreeOperations() {
    }

    static Tree delete(Tree tree, Integer value) {
        return switch (tree) {
            case EmptyTree e -> e;
            case SubTree(Tree l, Integer v, Tree r) when contains(l, value) -> delete(l, value);
            case SubTree(Tree l, Integer v, Tree r) when contains(r, value) -> delete(r, value);
            case SubTree(EmptyTree l, Integer v, EmptyTree r) when v.equals(value) -> new EmptyTree();
            case SubTree(SubTree l, Integer v, EmptyTree r) when v.equals(value) -> l;
            case SubTree(EmptyTree l, Integer v, SubTree r) when v.equals(value) -> r;
            case SubTree(SubTree l, Integer v, SubTree r) when v.equals(value)-> insertSubTree(r, l);
            case SubTree subTree -> subTree;
        };
    }

    static Tree insertSubTree(Tree into, Tree subTree) {
        return switch (into) {
            case EmptyTree e -> subTree;
            case SubTree(Tree l, Integer v, SubTree r) -> new SubTree(l, v, insertSubTree(r, subTree));
            case SubTree(Tree l, Integer v, EmptyTree r) -> new SubTree(l,v,subTree);
        };
    }

    static Tree insert(Tree tree, Integer value) {
        return switch (tree) {
            case EmptyTree e -> new SubTree(value);
            case SubTree(Tree l, Integer v, Tree r) when v.equals(value) -> tree;
            case SubTree(Tree l, Integer v, Tree r) when v > value -> new SubTree(insert(l, value), v, r);
            case SubTree(Tree l, Integer v, Tree r) -> new SubTree(l, v, insert(r, value));
        };
    }

    static boolean contains(Tree tree, Integer value) {
        return switch (tree) {
            case EmptyTree e -> false;
            case SubTree(Tree l, Integer v, Tree r) when v.equals(value) -> true;
            case SubTree(Tree l, Integer v, Tree r) -> contains(l,value) || contains(r,value);
        };
    }
}
