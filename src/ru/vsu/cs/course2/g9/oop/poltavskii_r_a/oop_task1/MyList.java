package ru.vsu.cs.course2.g9.oop.poltavskii_r_a.oop_task1;

import java.util.ListIterator;

public interface MyList<E> extends Collecti<E> {
    E get(int index);
    int indexOf(Object o);
    int lastIndexOf(Object o);
    ListIterator<E> listIterator();
    ListIterator<E> listIterator(int index);
    MyList<E> subList(int fromIndex, int toIndex);
}
