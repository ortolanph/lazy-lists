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
        return SimpleLazyList.of(items.stream().skip(1).toList());
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
