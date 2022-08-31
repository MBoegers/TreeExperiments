package io.github.mboegers.trees.bitree.jfp.model;

/**
 * This class makes us of Pattern matching for java to perform basic operations on binary trees.
 * The currently used java version is 19 with enabled preview.
 */
final class TreeOperations {

    private TreeOperations() {
    }

    /**
     * Deletes a value from a given tree
     *
     * @param biTree target of deletion
     * @param value  value to delete
     * @return Tree not containing the given value
     */
    static BiTree delete(BiTree biTree, int value) {
        return switch (biTree) {
            case EmptyBiTree e, null -> e;
            case SubBiTree(BiTree l,int v,BiTree r)
                    when contains(l, value) -> delete(l, value);
            case SubBiTree(BiTree l,int v,BiTree r)
                    when contains(r, value) -> delete(r, value);
            case SubBiTree(EmptyBiTree l,int v,EmptyBiTree r)
                    when(v == value) -> new EmptyBiTree();
            case SubBiTree(SubBiTree l,int v,EmptyBiTree r)
                    when(v == value) -> l;
            case SubBiTree(EmptyBiTree l,int v,SubBiTree r)
                    when(v == value) -> r;
            case SubBiTree(SubBiTree(BiTree tl,int vl,EmptyBiTree e),int v,SubBiTree r)
                    when(v == value) -> new SubBiTree(tl, v, r);
            case SubBiTree(SubBiTree(BiTree tl,int vl,SubBiTree tr),int v,SubBiTree r)
                    when(v == value) -> new SubBiTree(tl, v, insertTreeRecursive(tr, r));
            case SubBiTree subTree -> subTree;
        };
    }

    /**
     * Little helper which inserts a tree into another tree depply
     *
     * @param into      target of insertion
     * @param subBiTree tree to insert
     * @return Tree containing the inserted tree
     */
    static BiTree insertTreeRecursive(BiTree into, BiTree subBiTree) {
        return switch (into) {
            case EmptyBiTree e, null -> subBiTree;
            case SubBiTree(BiTree r,int v,BiTree l) -> insertTreeRecursive(l, subBiTree);
        };
    }

    /**
     * Insert a value into a tree
     *
     * @param biTree target of insertion
     * @param value  value to insert
     * @return Tree containing the value
     */
    static BiTree insert(BiTree biTree, int value) {
        return switch (biTree) {
            case EmptyBiTree e, null -> new SubBiTree(value);
            case SubBiTree(BiTree l,int v,BiTree r)when(v == value) -> biTree;
            case SubBiTree(BiTree l,int v,BiTree r)when(v > value) -> new SubBiTree(insert(l, value), v, r);
            case SubBiTree(BiTree l,int v,BiTree r) -> new SubBiTree(l, v, insert(r, value));
        };
    }

    /**
     * Checks whether a given tree contains a value
     *
     * @param biTree target to check
     * @param value  value to find
     * @return true iff value is contained in subtree
     */
    static boolean contains(BiTree biTree, int value) {
        return switch (biTree) {
            case EmptyBiTree e, null -> false;
            case SubBiTree(BiTree l,int v,BiTree r)when(v == value) -> true;
            case SubBiTree(BiTree l,int v,BiTree r) -> contains(l, value) || contains(r, value);
        };
    }
}
