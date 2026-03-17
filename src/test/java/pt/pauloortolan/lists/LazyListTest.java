package pt.pauloortolan.lists;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LazyListTest {

    @Nested
    class SimpleLazyListTest {

        @Test
        void head_returnsFirstElement() {
            LazyList<String> list = SimpleLazyList.of(List.of("a", "b", "c"));
            assertEquals("a", list.head());
        }

        @Test
        void tail_returnsListWithoutFirstElement() {
            LazyList<String> list = SimpleLazyList.of(List.of("a", "b", "c"));
            LazyList<String> tail = list.tail();
            assertEquals("b", tail.head());
        }

        @Test
        void tail_ofSingleElementList_isEmpty() {
            LazyList<String> list = SimpleLazyList.of(List.of("a"));
            assertTrue(list.tail().isEmpty());
        }

        @Test
        void isEmpty_returnsFalse_whenListHasElements() {
            LazyList<String> list = SimpleLazyList.of(List.of("a"));
            assertFalse(list.isEmpty());
        }

        @Test
        void isEmpty_returnsTrue_whenListIsEmpty() {
            LazyList<String> list = SimpleLazyList.of(List.of());
            assertTrue(list.isEmpty());
        }

        @Test
        void traversal_consumesAllElements() {
            LazyList<String> list = SimpleLazyList.of(List.of("a", "b", "c"));
            int count = 0;
            while (!list.isEmpty()) {
                list = list.tail();
                count++;
            }
            assertEquals(3, count);
        }
    }

    @Nested
    class PartitionedLazyListTest {

        @Test
        void head_returnsFirstPartition() {
            LazyList<List<String>> list = PartitionedLazyList.of(List.of("a", "b", "c", "d"), 2);
            assertEquals(List.of("a", "b"), list.head());
        }

        @Test
        void tail_returnsListStartingAtNextPartition() {
            LazyList<List<String>> list = PartitionedLazyList.of(List.of("a", "b", "c", "d"), 2);
            assertEquals(List.of("c", "d"), list.tail().head());
        }

        @Test
        void tail_ofLastPartition_isEmpty() {
            LazyList<List<String>> list = PartitionedLazyList.of(List.of("a", "b"), 2);
            assertTrue(list.tail().isEmpty());
        }

        @Test
        void isEmpty_returnsFalse_whenItemsExist() {
            LazyList<List<String>> list = PartitionedLazyList.of(List.of("a"), 1);
            assertFalse(list.isEmpty());
        }

        @Test
        void isEmpty_returnsTrue_whenNoItems() {
            LazyList<List<String>> list = PartitionedLazyList.of(List.of(), 2);
            assertTrue(list.isEmpty());
        }

        @Test
        void traversal_yieldsCorrectNumberOfPartitions() {
            LazyList<List<String>> list = PartitionedLazyList.of(List.of("a", "b", "c", "d", "e", "f"), 2);
            int count = 0;
            while (!list.isEmpty()) {
                list = list.tail();
                count++;
            }
            assertEquals(3, count);
        }
    }
}
