package io.github.mboegers.trees.bitree.jfp.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static io.github.mboegers.trees.bitree.jfp.model.TreeOperations.*;
import static org.assertj.core.api.Assertions.assertThat;

class SubBiTreeTest {

    @Nested
    class Creation {

        @Nested
        class Leaf {
            @Test
            void allArgsConstructor() {
                var newTree = new SubBiTree(new EmptyBiTree(), 3, new EmptyBiTree());
                var expected = new SubBiTree(new EmptyBiTree(), 3, new EmptyBiTree());

                assertThat(newTree).isEqualTo(expected);
            }

            @Test
            void withLeftTreeConstructor() {
                var newTree = new SubBiTree(new EmptyBiTree(), 3);
                var expected = new SubBiTree(new EmptyBiTree(), 3, new EmptyBiTree());

                assertThat(newTree).isEqualTo(expected);
            }

            @Test
            void withRightTreeConstructor() {
                var newTree = new SubBiTree(3, new EmptyBiTree());
                var expected = new SubBiTree(new EmptyBiTree(), 3, new EmptyBiTree());

                assertThat(newTree).isEqualTo(expected);
            }
        }

        @Nested
        class FullBiTree {
            @Test
            void allArgsConstructor() {
                var newTree = new SubBiTree(new SubBiTree(2), 3, new SubBiTree(5));
                var expected = new SubBiTree(new SubBiTree(new EmptyBiTree(), 2, new EmptyBiTree()), 3, new SubBiTree(new EmptyBiTree(), 5, new EmptyBiTree()));

                assertThat(newTree).isEqualTo(expected);
            }

            @Test
            void withLeftTreeConstructor() {
                var newTree = new SubBiTree(new SubBiTree(2), 3);
                var expected = new SubBiTree(new SubBiTree(2), 3, new EmptyBiTree());

                assertThat(newTree).isEqualTo(expected);
            }

            @Test
            void withRightTreeConstructor() {
                var newTree = new SubBiTree(3, new SubBiTree(4));
                var expected = new SubBiTree(new EmptyBiTree(), 3, new SubBiTree(4));

                assertThat(newTree).isEqualTo(expected);
            }
        }

    }

    @Nested
    class Delete {
        @Test
        void removeInnerNode() {
            /* from https://de.wikipedia.org/wiki/Bin%C3%A4rbaum#L%C3%B6schen second example
             *  Remove 10 => replace with 11, move 14 up
             *                  100                           100
             *                  / \                           / \
             *                 /   \                         /   \
             *                10   110                      11   110
             *               / \                           / \
             *              /   \                         /   \
             *             5    15         ---->         5     15
             *            / \   / \                     / \   / \
             *           1   7 11 20                   1   7 14 20
             *                  \
             *                  14
             */


            var containingTree = new SubBiTree(new SubBiTree(new SubBiTree(new SubBiTree(1), 5, new SubBiTree(7)), 10, new SubBiTree(new SubBiTree(11, new SubBiTree(14)), 15, new SubBiTree(20))), 100, new SubBiTree(110));
            var expected = new SubBiTree(new SubBiTree(new SubBiTree(1), 5, new SubBiTree(7, new SubBiTree(new SubBiTree(11, new SubBiTree(14)), 15, new SubBiTree(20)))), 100, new SubBiTree(110));

            var actual = delete(containingTree, 10);

            assertThat(actual).isEqualTo(expected);
        }

        @Nested
        class NoChild {
            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void hasNoChildContaining(Integer value) {
                var containingTree = new SubBiTree(new EmptyBiTree(), value, new EmptyBiTree());
                var expected = new EmptyBiTree();

                var actual = delete(containingTree, value);

                assertThat(actual).isEqualTo(expected);
            }

            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void hasNoChildNotContaining(Integer value) {
                var containingTree = new SubBiTree(new EmptyBiTree(), 13, new EmptyBiTree());
                var expected = new SubBiTree(new EmptyBiTree(), 13, new EmptyBiTree());

                var actual = delete(containingTree, value);

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class HasLeftChild {
            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void containingValueAndHasLeftChild(Integer value) {
                var containingTree = new SubBiTree(new SubBiTree(12), value);
                var expected = new SubBiTree(12);

                var actual = delete(containingTree, value);

                assertThat(actual).isEqualTo(expected);
            }


            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void notContainingValueAndHasLeftChild(Integer value) {
                var containingTree = new SubBiTree(new SubBiTree(12), 13);
                var expected = new SubBiTree(new SubBiTree(12), 13);

                var actual = delete(containingTree, value);

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class HasRightChild {
            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void containingValueAndHasRightChild(Integer value) {
                var containingTree = new SubBiTree(new EmptyBiTree(), value, new SubBiTree(17));
                var expected = new SubBiTree(17);

                var actual = delete(containingTree, value);

                assertThat(actual).isEqualTo(expected);
            }

            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void notContainingValueAndHasRightChild(Integer value) {
                var containingTree = new SubBiTree(new EmptyBiTree(), 13, new SubBiTree(17));
                var expected = new SubBiTree(new EmptyBiTree(), 13, new SubBiTree(17));

                var actual = delete(containingTree, value);

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class ValueInLeaf {
            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8})
            void leftChildContainsValue(Integer value) {
                var tree = new SubBiTree(new SubBiTree(value), 100, new SubBiTree(110));
                var expected = new SubBiTree(new EmptyBiTree(), 100, new SubBiTree(110));

                var actual = delete(tree, value);

                assertThat(actual).isEqualTo(expected);
            }

            @ParameterizedTest
            @ValueSource(ints = {-34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void rightChildContainsValue(Integer value) {
                var tree = new SubBiTree(new SubBiTree(-90), -100, new SubBiTree(value));
                var expected = new SubBiTree(new SubBiTree(-90), -100, new EmptyBiTree());

                var actual = delete(tree, value);

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Nested
    class Insert {

        @Test
        void insertLeft() {
            var flatTree = new SubBiTree(7);
            var expectedTree = new SubBiTree(new SubBiTree(3), 7, new EmptyBiTree());

            var leftHeavyTree = insert(flatTree, 3);

            assertThat(leftHeavyTree).isEqualTo(expectedTree);
        }

        @Test
        void insertRight() {
            var flatTree = new SubBiTree(7);
            var expectedTree = new SubBiTree(new EmptyBiTree(), 7, new SubBiTree(10));

            var leftHeavyTree = insert(flatTree, 10);

            assertThat(leftHeavyTree).isEqualTo(expectedTree);
        }

        @Test
        void insertBothSmallFirst() {
            var flatTree = new SubBiTree(6);
            var expectedTree = new SubBiTree(new SubBiTree(2), 6, new SubBiTree(11));

            var smallFirst = insert(insert(flatTree, 2), 11);

            assertThat(smallFirst).isEqualTo(expectedTree);
        }

        @Test
        void insertBothBigFirst() {
            var flatTree = new SubBiTree(6);
            var expectedTree = new SubBiTree(new SubBiTree(2), 6, new SubBiTree(11));

            var bigFirst = insert(insert(flatTree, 11), 2);

            assertThat(bigFirst).isEqualTo(expectedTree);
        }

        @Test
        void insertGiant() {
            /*
             *  Remove 10 => replace with 11, move 14 up
             *                  100
             *                  / \
             *                 /   \
             *                10   110
             *               / \
             *              /   \
             *             5    15
             *            / \   / \
             *           1   7 11 20
             *                  \
             *                  14
             */
            var expected = new SubBiTree(new SubBiTree(new SubBiTree(new SubBiTree(1), 5, new SubBiTree(7)), 10, new SubBiTree(new SubBiTree(11, new SubBiTree(14)), 15, new SubBiTree(20))), 100, new SubBiTree(110));

            var insertedTree = insert(insert(insert(insert(insert(insert(insert(insert(insert(insert(new EmptyBiTree(),
                    100), 110), 10), 5), 15), 1), 11), 7), 20), 14);

            assertThat(insertedTree).isEqualTo(expected);
        }

        @Test
        void insertDuplicate() {
            var baseTree = new SubBiTree(5);
            var expected = new SubBiTree(new SubBiTree(2), 5);

            var actual = insert(insert(baseTree, 2), 2);

            assertThat(actual).isEqualTo(expected);
        }

        @Nested
        class SubBiTreeInsert {
            @Test
            void insertFullSubTree() {
                var baseTree = new SubBiTree(new SubBiTree(4), 5, new SubBiTree(6));
                var subTree = new SubBiTree(new SubBiTree(7), 8, new SubBiTree(9));
                var expected = new SubBiTree(new SubBiTree(4), 5, new SubBiTree(6, new SubBiTree(new SubBiTree(7), 8, new SubBiTree(9))));

                var actual = insertTreeRecursive(baseTree, subTree);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void insertEmptySubTree() {
                var baseTree = new SubBiTree(new SubBiTree(4), 5, new SubBiTree(6));
                var subTree = new EmptyBiTree();
                var expected = new SubBiTree(new SubBiTree(4), 5, new SubBiTree(6));

                var actual = insertTreeRecursive(baseTree, subTree);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void insertIntoEmptySubTree() {
                var baseTree = new EmptyBiTree();
                var subTree = new SubBiTree(new SubBiTree(7), 8, new SubBiTree(9));
                var expected = new SubBiTree(new SubBiTree(7), 8, new SubBiTree(9));

                var actual = insertTreeRecursive(baseTree, subTree);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void insertSimpleSubTree() {
                var baseTree = new SubBiTree(new SubBiTree(4), 5, new SubBiTree(6));
                var subTree = new SubBiTree(8);
                var expected = new SubBiTree(new SubBiTree(4), 5, new SubBiTree(6, new SubBiTree(8)));

                var actual = insertTreeRecursive(baseTree, subTree);

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void insertIntoSimpleSubTree() {
                var baseTree = new SubBiTree(5);
                var subTree = new SubBiTree(new SubBiTree(7), 8, new SubBiTree(9));
                var expected = new SubBiTree(5, new SubBiTree(new SubBiTree(7), 8, new SubBiTree(9)));

                var actual = insertTreeRecursive(baseTree, subTree);

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Nested
    class Contains {

        @Nested
        class FirstLvl {
            @ParameterizedTest
            @NullSource
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, -8, Integer.MAX_VALUE})
            void doesNotContain(Integer notA5) {
                var treeContaining5 = new SubBiTree(5);

                assertThat(TreeOperations.contains(treeContaining5, notA5)).isFalse();
            }

            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, -8, Integer.MAX_VALUE})
            void contains(Integer value) {
                var valueContainTree = new SubBiTree(value);

                assertThat(TreeOperations.contains(valueContainTree, value)).isTrue();
            }
        }

        @Nested
        class SecondLvl {
            @ParameterizedTest
            @NullSource
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, -8, Integer.MAX_VALUE})
            void doesNotContain(Integer notA5) {
                var treeContaining5 = new SubBiTree(new SubBiTree(5), 8, new SubBiTree(10));

                assertThat(contains(treeContaining5, notA5)).isFalse();
            }

            @Test
            void containsRoot() {
                var valueContainTree = new SubBiTree(new SubBiTree(3), 5, new SubBiTree(8));

                assertThat(contains(valueContainTree, 5)).isTrue();
            }

            @Test
            void containsLeft() {
                var valueContainTree = new SubBiTree(new SubBiTree(5), 7, new SubBiTree(8));

                assertThat(contains(valueContainTree, 5)).isTrue();
            }

            @Test
            void containsRight() {
                var valueContainTree = new SubBiTree(new SubBiTree(3), 4, new SubBiTree(5));

                assertThat(contains(valueContainTree, 5)).isTrue();
            }
        }
    }
}
