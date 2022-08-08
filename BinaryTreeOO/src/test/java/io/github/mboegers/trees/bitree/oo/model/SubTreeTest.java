package io.github.mboegers.trees.bitree.oo.model;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class SubTreeTest {

    @Nested
    class Creation {

        @Nested
        class Leaf {
            @Test
            void allArgsConstructor() {
                var newTree = new SubTree(new EmptyTree(), 3, new EmptyTree());
                var expected = new SubTree(new EmptyTree(), 3, new EmptyTree());

                assertThat(newTree).isEqualTo(expected);
            }

            @Test
            void withLeftTreeConstructor() {
                var newTree = new SubTree(new EmptyTree(), 3);
                var expected = new SubTree(new EmptyTree(), 3, new EmptyTree());

                assertThat(newTree).isEqualTo(expected);
            }

            @Test
            void withRightTreeConstructor() {
                var newTree = new SubTree(3, new EmptyTree());
                var expected = new SubTree(new EmptyTree(), 3, new EmptyTree());

                assertThat(newTree).isEqualTo(expected);
            }
        }

        @Nested
        class FullTree {
            @Test
            void allArgsConstructor() {
                var newTree = new SubTree(new SubTree(2), 3, new SubTree(5));
                var expected = new SubTree(new SubTree(new EmptyTree(), 2, new EmptyTree()), 3, new SubTree(new EmptyTree(), 5, new EmptyTree()));

                assertThat(newTree).isEqualTo(expected);
            }

            @Test
            void withLeftTreeConstructor() {
                var newTree = new SubTree(new SubTree(2), 3);
                var expected = new SubTree(new SubTree(2), 3, new EmptyTree());

                assertThat(newTree).isEqualTo(expected);
            }

            @Test
            void withRightTreeConstructor() {
                var newTree = new SubTree(3, new SubTree(4));
                var expected = new SubTree(new EmptyTree(), 3, new SubTree(4));

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


            var containingTree = new SubTree(new SubTree(new SubTree(new SubTree(1), 5, new SubTree(7)), 10, new SubTree(new SubTree(11, new SubTree(14)), 15, new SubTree(20))), 100, new SubTree(110));
            var expected = new SubTree(new SubTree(new SubTree(1), 5, new SubTree(7, new SubTree(new SubTree(11, new SubTree(14)), 15, new SubTree(20)))), 100, new SubTree(110));

            var actual = containingTree.delete(10);

            assertThat(actual).isEqualTo(expected);
        }

        @Nested
        class NoChild {
            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void hasNoChildContaining(Integer value) {
                var containingTree = new SubTree(new EmptyTree(), value, new EmptyTree());
                var expected = new EmptyTree();

                var actual = containingTree.delete(value);

                assertThat(actual).isEqualTo(expected);
            }

            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void hasNoChildNotContaining(Integer value) {
                var containingTree = new SubTree(new EmptyTree(), 13, new EmptyTree());
                var expected = new SubTree(new EmptyTree(), 13, new EmptyTree());

                var actual = containingTree.delete(value);

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class HasLeftChild {
            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void containingValueAndHasLeftChild(Integer value) {
                var containingTree = new SubTree(new SubTree(12), value);
                var expected = new SubTree(12);

                var actual = containingTree.delete(value);

                assertThat(actual).isEqualTo(expected);
            }


            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void notContainingValueAndHasLeftChild(Integer value) {
                var containingTree = new SubTree(new SubTree(12), 13);
                var expected = new SubTree(new SubTree(12), 13);

                var actual = containingTree.delete(value);

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class HasRightChild {
            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void containingValueAndHasRightChild(Integer value) {
                var containingTree = new SubTree(new EmptyTree(), value, new SubTree(17));
                var expected = new SubTree(17);

                var actual = containingTree.delete(value);

                assertThat(actual).isEqualTo(expected);
            }

            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void notContainingValueAndHasRightChild(Integer value) {
                var containingTree = new SubTree(new EmptyTree(), 13, new SubTree(17));
                var expected = new SubTree(new EmptyTree(), 13, new SubTree(17));

                var actual = containingTree.delete(value);

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class ValueInLeaf {
            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, 5, -8})
            void leftChildContainsValue(Integer value) {
                var tree = new SubTree(new SubTree(value), 100, new SubTree(110));
                var expected = new SubTree(new EmptyTree(), 100, new SubTree(110));

                var actual = tree.delete(value);

                assertThat(actual).isEqualTo(expected);
            }

            @ParameterizedTest
            @ValueSource(ints = {-34, -3, 33, 5, -8, Integer.MAX_VALUE})
            void rightChildContainsValue(Integer value) {
                var tree = new SubTree(new SubTree(-90), -100, new SubTree(value));
                var expected = new SubTree(new SubTree(-90), -100, new EmptyTree());

                var actual = tree.delete(value);

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Nested
    class Insert {

        @Test
        void insertLeft() {
            var flatTree = new SubTree(7);
            var expectedTree = new SubTree(new SubTree(3), 7, new EmptyTree());

            var leftHeavyTree = flatTree.insert(3);

            assertThat(leftHeavyTree).isEqualTo(expectedTree);
        }

        @Test
        void insertRight() {
            var flatTree = new SubTree(7);
            var expectedTree = new SubTree(new EmptyTree(), 7, new SubTree(10));

            var leftHeavyTree = flatTree.insert(10);

            assertThat(leftHeavyTree).isEqualTo(expectedTree);
        }

        @Test
        void insertBothSmallFirst() {
            var flatTree = new SubTree(6);
            var expectedTree = new SubTree(new SubTree(2), 6, new SubTree(11));

            var smallFirst = flatTree.insert(2).insert(11);

            assertThat(smallFirst).isEqualTo(expectedTree);
        }

        @Test
        void insertBothBigFirst() {
            var flatTree = new SubTree(6);
            var expectedTree = new SubTree(new SubTree(2), 6, new SubTree(11));

            var bigFirst = flatTree.insert(11).insert(2);

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
            var expected = new SubTree(new SubTree(new SubTree(new SubTree(1), 5, new SubTree(7)), 10, new SubTree(new SubTree(11, new SubTree(14)), 15, new SubTree(20))), 100, new SubTree(110));

            var insertedTree = new EmptyTree()
                    .insert(100) // 1st Lvl
                    .insert(110) // 2nd Lvl
                    .insert(10) // 2nd Lvl
                    .insert(5) // 3rd Lvl
                    .insert(15) // 3rd Lvl
                    .insert(1) // 4th Lvl
                    .insert(11) // 4th Lvl
                    .insert(7) // 4th Lvl
                    .insert(20) // 4th Lvl
                    .insert(14); // 5th Lvl

            assertThat(insertedTree).isEqualTo(expected);
        }

        @Test
        void insertDuplicate() {
            var baseTree = new SubTree(5);
            var expected = new SubTree(new SubTree(2), 5);

            var actual = baseTree.insert(2).insert(2);

            assertThat(actual).isEqualTo(expected);
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
                var treeContaining5 = new SubTree(5);

                assertThat(treeContaining5.contains(notA5)).isFalse();
            }

            @ParameterizedTest
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, -8, Integer.MAX_VALUE})
            void contains(Integer value) {
                var valueContainTree = new SubTree(value);

                assertThat(valueContainTree.contains(value)).isTrue();
            }
        }

        @Nested
        class SecondLvl {
            @ParameterizedTest
            @NullSource
            @ValueSource(ints = {Integer.MIN_VALUE, -34, -3, 33, -8, Integer.MAX_VALUE})
            void doesNotContain(Integer notA5) {
                var treeContaining5 = new SubTree(new SubTree(5), 8, new SubTree(10));

                assertThat(treeContaining5.contains(notA5)).isFalse();
            }

            @Test
            void containsRoot() {
                var valueContainTree = new SubTree(new SubTree(3), 5, new SubTree(8));

                assertThat(valueContainTree.contains(5)).isTrue();
            }

            @Test
            void containsLeft() {
                var valueContainTree = new SubTree(new SubTree(5), 7, new SubTree(8));

                assertThat(valueContainTree.contains(5)).isTrue();
            }

            @Test
            void containsRight() {
                var valueContainTree = new SubTree(new SubTree(3), 4, new SubTree(5));

                assertThat(valueContainTree.contains(5)).isTrue();
            }
        }
    }
}
