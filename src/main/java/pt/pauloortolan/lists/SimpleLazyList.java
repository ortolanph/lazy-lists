package pt.pauloortolan.lists;

import java.util.List;

public record SimpleLazyList(List<String> items) implements LazyList<String> {

    public static SimpleLazyList of(List<String> items) {
        return new SimpleLazyList(items);
    }

    @Override
    public String head() {
        return items.getFirst();
    }

    @Override
    public LazyList<String> tail() {
        return SimpleLazyList.of(items.subList(1, items.size()));
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
