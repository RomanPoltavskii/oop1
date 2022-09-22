package ru.vsu.cs.course2.g9.oop.poltavskii_r_a.oop_task1;

import java.io.Serializable;

import java.util.*;
import java.util.function.*;

public class Collectionss {
    public Collectionss() {
    }

//readOnlyCollection
    public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> c) {
        if (c.getClass() == UnmodifiableCollection.class) {
            return (Collection<T>) c;
        }
        return new UnmodifiableCollection<>(c);
    }

    static class UnmodifiableCollection<E> implements Collection<E>, Serializable {
        @java.io.Serial
        private static final long serialVersionUID = 1820017752578914078L;

        final Collection<? extends E> c;

        UnmodifiableCollection(Collection<? extends E> c) {
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

        public <T> T[] toArray(IntFunction<T[]> f) {
            return c.toArray(f);
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

                @Override
                public void forEachRemaining(Consumer<? super E> action) {
                    i.forEachRemaining(action);
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
    //singletonCollection
    public static <T> Set<T> singleton(T o) {
        return new SingletonSet<>(o);
    }

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


    private static class SingletonSet<E>
            extends AbstractSet<E>
            implements Serializable
    {
        @java.io.Serial
        private static final long serialVersionUID = 3193687207550431679L;
        private final E element;

        SingletonSet(E e) {element = e;}

        public Iterator<E> iterator() {
            return singletonIterator(element);
        }

        public int size() {return 1;}

        public boolean contains(Object o) {return eq(o, element);}

        // Override default methods for Collection
        @Override
        public void forEach(Consumer<? super E> action) {
            action.accept(element);
        }
        @Override
        public Spliterator<E> spliterator() {
            return singletonSpliterator(element);
        }
        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            throw new UnsupportedOperationException();
        }
        @Override
        public int hashCode() {
            return Objects.hashCode(element);
        }
    }

    public static <T> List<T> singletonList(T o) {
        return new SingletonList<>(o);
    }

    private static class SingletonList<E>
            extends AbstractList<E>
            implements RandomAccess, Serializable {

        @java.io.Serial
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

    /**
     * Returns an immutable map, mapping only the specified key to the
     * specified value.  The returned map is serializable.
     *
     * @param <K> the class of the map keys
     * @param <V> the class of the map values
     * @param key the sole key to be stored in the returned map.
     * @param value the value to which the returned map maps {@code key}.
     * @return an immutable map containing only the specified key-value
     *         mapping.
     * @since 1.3
     */
    public static <K,V> Map<K,V> singletonMap(K key, V value) {
        return new SingletonMap<>(key, value);
    }

    /**
     * @serial include
     */
    private static class SingletonMap<K,V>
            extends AbstractMap<K,V>
            implements Serializable {
        @java.io.Serial
        private static final long serialVersionUID = -6979724477215052911L;


        private final K k;
        private final V v;

        SingletonMap(K key, V value) {
            k = key;
            v = value;
        }

        public int size(){
            return 1;
        }
        public boolean isEmpty(){
            return false;
        }
        public boolean containsKey(Object key) {
            return eq(key, k);
        }
        public boolean containsValue(Object value){
            return eq(value, v);
        }
        public V get(Object key){
            return (eq(key, k) ? v : null);
        }

        private transient Set<K> keySet;
        private transient Set<Map.Entry<K,V>> entrySet;
        private transient Collection<V> values;

        public Set<K> keySet() {
            if (keySet==null)
                keySet = singleton(k);
            return keySet;
        }

        public Set<Map.Entry<K,V>> entrySet() {
            if (entrySet==null)
                entrySet = java.util.Collections.<Map.Entry<K,V>>singleton(
                        new SimpleImmutableEntry<>(k, v));
            return entrySet;
        }

        public Collection<V> values() {
            if (values==null)
                values = singleton(v);
            return values;
        }

        // Override default methods in Map
        @Override
        public V getOrDefault(Object key, V defaultValue) {
            return eq(key, k) ? v : defaultValue;
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> action) {
            action.accept(k, v);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V putIfAbsent(K key, V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object key, Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K key, V oldValue, V newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V replace(K key, V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsent(K key,
                                 Function<? super K, ? extends V> mappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfPresent(K key,
                                  BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V compute(K key,
                         BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V merge(K key, V value,
                       BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(k) ^ Objects.hashCode(v);
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

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("rawtypes")
    public static final Set EMPTY_SET = new EmptySet<>();

    @SuppressWarnings("unchecked")
    public static<T> Set<T> emptySet() {
        return (Set<T>) EMPTY_SET;
    }

    private static class EmptySet<E>
            extends AbstractSet<E>
            implements Serializable
    {
        @java.io.Serial
        private static final long serialVersionUID = 1582296315990362920L;

        public Iterator<E> iterator() {
            return emptyIterator();
        }

        public int size() {
            return 0;
        }
        public boolean isEmpty() {
            return true;
        }
        public void clear() {

        }

        public boolean contains(Object obj) {
            return false;}
        public boolean containsAll(Collection<?> c) {
            return c.isEmpty();
        }

        public Object[] toArray() {
            return new Object[0];
        }

        public <T> T[] toArray(T[] a) {
            if (a.length > 0)
                a[0] = null;
            return a;
        }

        // Override default methods in Collection
        @Override
        public void forEach(Consumer<? super E> action) {
            Objects.requireNonNull(action);
        }
        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            Objects.requireNonNull(filter);
            return false;
        }
        @Override
        public Spliterator<E> spliterator() {
            return Spliterators.emptySpliterator();
        }

        // Preserves singleton property
        @java.io.Serial
        private Object readResolve() {
            return EMPTY_SET;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

    @SuppressWarnings("rawtypes")
    public static final List EMPTY_LIST = new EmptyList<>();

    @SuppressWarnings("unchecked")
    public static <T> List<T> emptyList() {
        return (List<T>) EMPTY_LIST;
    }

    /**
     * @serial include
     */
    private static class EmptyList<E>
            extends AbstractList<E>
            implements RandomAccess, Serializable {
        @java.io.Serial
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

        // Override default methods in Collection
        @Override
        public void forEach(Consumer<? super E> action) {
            Objects.requireNonNull(action);
        }

        @Override
        public Spliterator<E> spliterator() { return Spliterators.emptySpliterator(); }

        // Preserves singleton property
        @java.io.Serial
        private Object readResolve() {
            return EMPTY_LIST;
        }
    }

    @SuppressWarnings("rawtypes")
    public static final Map EMPTY_MAP = new EmptyMap<>();


    @SuppressWarnings("unchecked")
    public static <K,V> Map<K,V> emptyMap() {
        return (Map<K,V>) EMPTY_MAP;
    }

    private static class EmptyMap<K,V>
            extends AbstractMap<K,V>
            implements Serializable
    {
        @java.io.Serial
        private static final long serialVersionUID = 6428348081105594320L;

        public int size()                          {return 0;}
        public boolean isEmpty()                   {return true;}
        public void clear()                        {}
        public boolean containsKey(Object key)     {return false;}
        public boolean containsValue(Object value) {return false;}
        public V get(Object key)                   {return null;}
        public Set<K> keySet()                     {return emptySet();}
        public Collection<V> values()              {return emptySet();}
        public Set<Map.Entry<K,V>> entrySet()      {return emptySet();}

        public boolean equals(Object o) {
            return (o instanceof Map) && ((Map<?,?>)o).isEmpty();
        }

        public int hashCode()                      {return 0;}

        // Override default methods in Map
        @Override
        public V getOrDefault(Object k, V defaultValue) {
            return defaultValue;
        }

        @Override
        public void forEach(BiConsumer<? super K, ? super V> action) {
            Objects.requireNonNull(action);
        }

        @Override
        public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
            Objects.requireNonNull(function);
        }

        @Override
        public V putIfAbsent(K key, V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object key, Object value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean replace(K key, V oldValue, V newValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V replace(K key, V value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfAbsent(K key,
                                 Function<? super K, ? extends V> mappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V computeIfPresent(K key,
                                  BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V compute(K key,
                         BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        @Override
        public V merge(K key, V value,
                       BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
            throw new UnsupportedOperationException();
        }

        // Preserves singleton property
        @java.io.Serial
        private Object readResolve() {
            return EMPTY_MAP;
        }
    }
}
