package ru.vsu.cs.course2.g9.oop.poltavskii_r_a.oop_task1;

import java.util.Collection;

public interface Collecti<E> extends Iterable<E> {
    int size();
    boolean isEmpty();
    boolean contains(Object o);
    Object[] toArray();
    <T> T[] toArray(T[] a);
    boolean containsAll(Collecti<?> c);
}
