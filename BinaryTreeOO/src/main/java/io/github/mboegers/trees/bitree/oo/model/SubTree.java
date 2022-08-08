package io.github.mboegers.trees.bitree.oo.model;

import java.util.function.Consumer;

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
    public Tree insert(Integer value) {
        if (value < this.value)
            return new SubTree(left.insert(value), this.value, right);
        else if (value > this.value)
            return new SubTree(left, this.value, right.insert(value));
        else
            return this;
    }

    @Override
    public boolean contains(Integer value) {
        return value != null &&
                (this.value.equals(value)
                        || left.contains(value)
                        || right.contains(value));
    }

    Tree insertInRightTree(Tree t) {
        if (right instanceof SubTree r) // insert somewhere right
            return new SubTree(left, value, r.insertInRightTree(t));
        else // insert here
            return new SubTree(value, t);
    }

    @Override
    public Tree delete(Integer v) {
        if (value.equals(v)) { // this node contains the value to delete
            if (left instanceof EmptyTree && right instanceof EmptyTree)
                // A1 - no children => delete node
                return new EmptyTree();
            else if (left instanceof EmptyTree && right instanceof SubTree)
                // A2.R - one child on the right => replace with right
                return right;
            else if (left instanceof SubTree && right instanceof EmptyTree)
                // A2.L - one child on the left => replace with left
                return left;
            else if (left instanceof SubTree l && right instanceof SubTree)
                // B: has children on both sides => replace node to delete with left and insert right three at the right
                return l.insertInRightTree(right);
            else
                throw new IllegalStateException("This should not be possible");
        } else if (left.contains(v)) // the left subtree contains the node to delete => dive deeper left
            return new SubTree(left.delete(v), value, right);
        else if (right.contains(v)) // the right subtree contains the node to delete => dive deeper right
            return new SubTree(left, value, right.delete(v));
        else // value is not in tree so abort operation
            return this;
    }

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
