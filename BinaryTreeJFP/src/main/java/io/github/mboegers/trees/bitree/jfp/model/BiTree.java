package io.github.mboegers.trees.bitree.jfp.model;


/**
 * Represent a Binary Tree with its possible characteristics:
 * empty e.q. no value and not children {@linkplain EmptyBiTree}
 * inner node or leaf e.q. containing at least a value and may contain children {@linkplain SubBiTree}
 */
sealed interface BiTree permits EmptyBiTree, SubBiTree {
}
