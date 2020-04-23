package com.shpp.p2p.cs.bcimbal.assignment17;

import java.util.*;

public class HHashMap<K, V> extends AbstractMap<K, V>  {

    private Set<Entry<K,V>> entries;
    private int size = 0;

    HHashMap() {
         entries = new EEntries();

    }

    @Override
    public Set<K> keySet() {
        Set<K> kSet = new AbstractSet<K>() {

            @Override
            public Iterator<K> iterator() {
                Iterator it = new MySetIter(entries);
                return it;
            }

            @Override
            public int size() {
                return size;
            }

            class MySetIter implements Iterator {
                Entry<K, V> []array;
                public MySetIter(Set<Entry<K, V>> entries) {
                    array = (Entry<K, V>[])new Object[entries.size()];
                }

                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public Object next() {
                    return null;
                }
            }
        };
        return kSet;
    }


    @Override
    public String toString() {
        return entries.toString();
//        "HHashMap{" +
//                "entries=" + entries.toString() +
//                ", size=" + size +
//                '}';
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return entries;
    }

    @Override
    public int size() {
        return entries.size();
    }

    public V put(K key, V value) {
        EEntry<K, V> e = new EEntry<K,V>(key, value);
        entries.add(e);
        size++;
        return value;
    }

    @Override
    public V get(Object key) {
        return super.get(key);
    }


    @Override
    public Collection<V> values() {
        return super.values();
    }





}

class EEntries<K, V>  extends AbstractSet<EEntry<K,V>> {

    EEntry[] entriesArr ;
    int size = 0;
    EEntries() {
        entriesArr = new EEntry[10];
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        Iterator it = new MyIter(entriesArr);/* Arrays.asList(entriesArr).iterator()*/;
        return it;
    }

    @Override
    public Object[] toArray() {
        Object [] tmp = new Object[size];
        System.arraycopy(entriesArr, 0, tmp, 0, size);
        return tmp;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T [] tmp = (T []) new Object[size];
        System.arraycopy(entriesArr, 0, tmp, 0, size);
        return tmp;
    }

    @Override
    public boolean add(EEntry e) {
        entriesArr[size++] = e;
        //size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends EEntry<K, V>> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public String toString() {
        String tmp = "{1=first, 2=second, 3=third}";
//         "EEntries{" +
//                "entriesArr=" + toArray() +
//                ", size=" + size +
//                '}';
        return tmp;
    }


    private class MyIter implements Iterator {
        int index = 0;
        int size;

        public MyIter(EEntry[] entriesArr) {
            this.size = entriesArr.length;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Object next() {
            return index++;
        }
    }
}

class EEntry<K, V> implements Map.Entry<K, V> {

    K key;
    V value;
    EEntry (K key, V value){
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}

