package pt.pauloortolan.lists;

import java.util.List;

public record PartitionedLazyList(List<String> items, int partitionSize) implements LazyList<List<String>> {

    public static PartitionedLazyList of(List<String> items, int partitionSize) {
        return new PartitionedLazyList(items, partitionSize);
    }

    @Override
    public List<String> head() {
        return items.subList(0, Math.min(items.size(), partitionSize));
    }

    @Override
    public LazyList<List<String>> tail() {
        return (partitionSize > items.size()) ?
                new PartitionedLazyList(List.of(), partitionSize) :
                new PartitionedLazyList(items.subList(partitionSize, items.size()), partitionSize);
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
