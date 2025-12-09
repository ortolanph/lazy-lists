package pt.pauloortolan.lists;

public interface LazyList<T> {

    T head();
    LazyList<T> tail();
    boolean isEmpty();

}
