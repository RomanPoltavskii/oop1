package ru.vsu.cs.course2.g9.oop.poltavskii_r_a.oop_task1;

import java.io.Serializable;

import java.util.*;
import java.util.function.*;

public class Collectionss {
    public Collectionss() {
    }

//readOnlyCollection
    public static <T> Collection<T> readOnlyCollection(Collection<? extends T> c) {

        return new ReadOnlyCollection<>(c);
    }

    static class ReadOnlyCollection<E> implements Collection<E>, Serializable {

        private static final long serialVersionUID = 1820017752578914078L;

        final Collection<? extends E> c;

        ReadOnlyCollection(Collection<? extends E> c) {
            if (c == null)
                throw new NullPointerException();
            this.c = c;
        }

        public int size() {
            return c.size();
        }

        public boolean isEmpty() {
            return c.isEmpty();
        }

        public boolean contains(Object o) {
            return c.contains(o);
        }

        public Object[] toArray() {
            return c.toArray();
        }

        public <T> T[] toArray(T[] a) {
            return c.toArray(a);
        }

        public String toString() {
            return c.toString();
        }

        public Iterator<E> iterator() {
            return new Iterator<E>() {
                private final Iterator<? extends E> i = c.iterator();

                public boolean hasNext() {
                    return i.hasNext();
                }

                public E next() {
                    return i.next();
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }

            };
        }

        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        public boolean containsAll(Collection<?> coll) {
            return c.containsAll(coll);
        }

        public boolean addAll(Collection<? extends E> coll) {
            throw new UnsupportedOperationException();
        }

        public boolean removeAll(Collection<?> coll) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(Collection<?> coll) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    public static <T> List<T> readOnlyList(List<? extends T> list) {
        return new Collectionss.ReadOnlyList<>(list);
    }

    static class ReadOnlyList<E> extends Collectionss.ReadOnlyCollection<E>
            implements List<E> {
        private static final long serialVersionUID = -283967356065247728L;

        final List<? extends E> list;

        ReadOnlyList(List<? extends E> list) {
            super(list);
            this.list = list;
        }

        public boolean equals(Object o) {
            return o == this || list.equals(o);
        }
        public int hashCode()           {
            return list.hashCode();
        }

        public E get(int index) {
            return list.get(index);
        }
        public E set(int index, E element) {

            throw new UnsupportedOperationException();
        }
        public void add(int index, E element) {
            throw new UnsupportedOperationException();
        }
        public E remove(int index) {
            throw new UnsupportedOperationException();
        }
        public int indexOf(Object o)            {
            return list.indexOf(o);
        }
        public int lastIndexOf(Object o)        {
            return list.lastIndexOf(o);
        }
        public boolean addAll(int index, Collection<? extends E> c) {
            throw new UnsupportedOperationException();
        }

        public void replaceAll(UnaryOperator<E> operator) {
            throw new UnsupportedOperationException();
        }

        public void sort(Comparator<? super E> c) {
            throw new UnsupportedOperationException();
        }

        public ListIterator<E> listIterator()   {
            return listIterator(0);
        }

        public ListIterator<E> listIterator(final int index) {
            return new ListIterator<E>() {
                private final ListIterator<? extends E> i
                        = list.listIterator(index);

                public boolean hasNext()     {
                    return i.hasNext();
                }
                public E next()              {
                    return i.next();
                }
                public boolean hasPrevious() {
                    return i.hasPrevious();
                }
                public E previous()          {
                    return i.previous();
                }
                public int nextIndex()       {
                    return i.nextIndex();
                }
                public int previousIndex()   {
                    return i.previousIndex();
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }
                public void set(E e) {
                    throw new UnsupportedOperationException();
                }
                public void add(E e) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void forEachRemaining(Consumer<? super E> action) {
                    i.forEachRemaining(action);
                }
            };
        }

        public List<E> subList(int fromIndex, int toIndex) {
            return new Collectionss.ReadOnlyList<>(list.subList(fromIndex, toIndex));
        }

        private Object readResolve() {
            return this;
        }
    }

    //singletonCollection


    static <E> Iterator<E> singletonIterator(final E e) {
        return new Iterator<E>() {
            private boolean hasNext = true;
            public boolean hasNext() {
                return hasNext;
            }
            public E next() {
                if (hasNext) {
                    hasNext = false;
                    return e;
                }
                throw new NoSuchElementException();
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
            @Override
            public void forEachRemaining(Consumer<? super E> action) {
                Objects.requireNonNull(action);
                if (hasNext) {
                    hasNext = false;
                    action.accept(e);
                }
            }
        };
    }
    static <T> Spliterator<T> singletonSpliterator(final T element) {
        return new Spliterator<T>() {
            long est = 1;

            @Override
            public Spliterator<T> trySplit() {
                return null;
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                if (est > 0) {
                    est--;
                    consumer.accept(element);
                    return true;
                }
                return false;
            }

            @Override
            public void forEachRemaining(Consumer<? super T> consumer) {
                tryAdvance(consumer);
            }

            @Override
            public long estimateSize() {
                return est;
            }

            @Override
            public int characteristics() {
                int value = (element != null) ? Spliterator.NONNULL : 0;

                return value | Spliterator.SIZED | Spliterator.SUBSIZED | Spliterator.IMMUTABLE |
                        Spliterator.DISTINCT | Spliterator.ORDERED;
            }
        };
    }
    public static <T> List<T> singletonList(T o) {
        return new SingletonList<>(o);
    }

    private static class SingletonList<E> extends AbstractList<E> implements RandomAccess, Serializable {

        private static final long serialVersionUID = 3093736618740652951L;

        private final E element;

        SingletonList(E obj) {
            element = obj;}

        public Iterator<E> iterator() {
            return singletonIterator(element);
        }

        public int size(){
            return 1;}

        public boolean contains(Object obj) {
            return eq(obj, element);}

        public E get(int index) {
            if (index != 0)
                throw new IndexOutOfBoundsException("Index: "+index+", Size: 1");
            return element;
        }

        // Override default methods for Collection
        @Override
        public void forEach(Consumer<? super E> action) {
            action.accept(element);
        }
        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            throw new UnsupportedOperationException();
        }
        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            throw new UnsupportedOperationException();
        }
        @Override
        public void sort(Comparator<? super E> c) {
        }
        @Override
        public Spliterator<E> spliterator() {
            return singletonSpliterator(element);
        }
        @Override
        public int hashCode() {
            return 31 + Objects.hashCode(element);
        }
    }

    static boolean eq(Object o1, Object o2) {
        return o1==null ? o2==null : o1.equals(o2);
    }

    //emptyCollections

    public static <T> Iterator<T> emptyIterator() {
        return (Iterator<T>) EmptyIterator.EMPTY_ITERATOR;
    }

    private static class EmptyIterator<E> implements Iterator<E> {
        static final EmptyIterator<Object> EMPTY_ITERATOR
                = new EmptyIterator<>();

        public boolean hasNext() { return false; }
        public E next() { throw new NoSuchElementException(); }
        public void remove() { throw new IllegalStateException(); }
        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
        }
    }

    public static <T> ListIterator<T> emptyListIterator() {
        return (ListIterator<T>) EmptyListIterator.EMPTY_ITERATOR;
    }

    private static class EmptyListIterator<E>
            extends EmptyIterator<E>
            implements ListIterator<E>
    {
        static final EmptyListIterator<Object> EMPTY_ITERATOR
                = new EmptyListIterator<>();

        public boolean hasPrevious() { return false; }
        public E previous() { throw new NoSuchElementException(); }
        public int nextIndex()     { return 0; }
        public int previousIndex() { return -1; }
        public void set(E e) { throw new IllegalStateException(); }
        public void add(E e) { throw new UnsupportedOperationException(); }
    }

    public static <T> Enumeration<T> emptyEnumeration() {
        return (Enumeration<T>) EmptyEnumeration.EMPTY_ENUMERATION;
    }

    private static class EmptyEnumeration<E> implements Enumeration<E> {
        static final EmptyEnumeration<Object> EMPTY_ENUMERATION
                = new EmptyEnumeration<>();

        public boolean hasMoreElements() { return false; }
        public E nextElement() { throw new NoSuchElementException(); }
        public Iterator<E> asIterator() { return emptyIterator(); }
    }

    public static final List EMPTY_LIST = new EmptyList<>();
    public static <T> List<T> emptyList() {
        return (List<T>) EMPTY_LIST;
    }

    private static class EmptyList<E>
            extends AbstractList<E>
            implements RandomAccess, Serializable {
        private static final long serialVersionUID = 8842843931221139166L;

        public Iterator<E> iterator() {
            return emptyIterator();
        }
        public ListIterator<E> listIterator() {
            return emptyListIterator();
        }

        public int size() {return 0;}
        public boolean isEmpty() {return true;}
        public void clear() {}

        public boolean contains(Object obj) {return false;}
        public boolean containsAll(Collection<?> c) { return c.isEmpty(); }

        public Object[] toArray() { return new Object[0]; }

        public <T> T[] toArray(T[] a) {
            if (a.length > 0)
                a[0] = null;
            return a;
        }

        public E get(int index) {
            throw new IndexOutOfBoundsException("Index: "+index);
        }

        public boolean equals(Object o) {
            return (o instanceof List) && ((List<?>)o).isEmpty();
        }

        public int hashCode() { return 1; }

        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            Objects.requireNonNull(filter);
            return false;
        }
        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            Objects.requireNonNull(operator);
        }
        @Override
        public void sort(Comparator<? super E> c) {
        }

        @Override
        public void forEach(Consumer<? super E> action) {
            Objects.requireNonNull(action);
        }

        @Override
        public Spliterator<E> spliterator() { return Spliterators.emptySpliterator(); }


        private Object readResolve() {
            return EMPTY_LIST;
        }
    }
}
