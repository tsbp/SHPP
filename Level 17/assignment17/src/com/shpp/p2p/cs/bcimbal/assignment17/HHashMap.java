package com.shpp.p2p.cs.bcimbal.assignment17;


import java.util.*;

@SuppressWarnings({"rawtypes","unchecked"})
public class HHashMap<K, V> extends AbstractMap<K, V> {

    private static final int INITIAL_CAPACITY = 20000;
    //private Set<Entry<K, V>> entrySet;
    private EEntry[]/*Set<Entry<K,V>>*/ hashTable;
    private int size = 0;

    public HHashMap() {
        hashTable = new EEntry[INITIAL_CAPACITY];
    }


    /*******************************************************************************************************************
     *
     * @return a
     */
    @Override
    public Set<K> keySet() {
        K[] keysArr = (K[]) new Object[size];

        int index = 0;
        for (Entry e : entrySet()) {
            if (e != null) {
                keysArr[index++] = (K) e.getKey();
            }
        }


        return new AbstractSet<K>() {
            @Override
            public Iterator<K> iterator() {
                return new MyIterator(keysArr);
            }

            @Override
            public int size() {
                return size;
            }
        };
    }

    /*******************************************************************************************************************
     *
     * @return a
     */
    @Override
    public Collection<V> values() {
        V[] valsArr = (V[]) new Object[size];

        int index = 0;
        for (Entry e : entrySet()) {
            if (e != null) {
                valsArr[index++] = (V)e.getValue();
            }
        }
        return new AbstractCollection<V>() {
            @Override
            public Iterator<V> iterator() {
                return new MyIterator(valsArr);
            }

            @Override
            public int size() {
                return valsArr.length;
            }
        };
    }

    /*******************************************************************************************************************
     *
     * @return a
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        EEntry<K,V>[] e = new EEntry[size];
        int index = 0;
        for (EEntry eEntry : hashTable) {
            if (eEntry != null) {
                e[index++] = eEntry;
                EEntry tmp = eEntry;
                while (tmp.next != null) {
                    e[index++] = tmp.next;
                    tmp = tmp.next;
                }
            }
        }
        return new AbstractSet<Entry<K, V>>() {
            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new MyIterator<>(e);
            }

            @Override
            public int size() {
                return size;
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    /*******************************************************************************************************************
     *
     * @param key a
     * @param value a
     * @return a
     */
    @Override
    public V put(K key, V value) {
        EEntry<K, V> e = new EEntry(key, value, null);
        addToEntryToTable(e, hashTable);

        size++;
        if (size >= hashTable.length) {
            hashTable = hashTableResize();
        }
        //entrySet = entrySet();
        return value;
    }

    @Override
    public V remove(Object key) {

        int index = getHash((K) key, hashTable.length);
        V value = get(key);
        EEntry tmp = hashTable[index];

            if (tmp.getKey().equals(key)) {
                hashTable[index] = hashTable[index].next;
            } else {
                while (!tmp.next.getKey().equals(key)) {
                    tmp = tmp.next;
                }
                if (tmp.next.next == null) {
                    tmp.next = null;
                } else {
                    tmp.next = tmp.next.next;
                }
            }
            size--;

        return value;
    }

    private void addToEntryToTable(EEntry<K, V> e, EEntry<K,V>[] hTable) {

        int index = getHash(e.getKey(), hTable.length);

            EEntry tmp = hTable[index];
            if (tmp != null) {
                //e.next = tmp;
                while (tmp.next != null) {
                    tmp = tmp.next;
                }
                tmp.next = e;

            } else {

                hTable[index] = e;
            }

    }


    /*******************************************************************************************************************
     *
     */
    private EEntry<K, V>[] hashTableResize() {

            EEntry<K, V>[] newHashTable = new EEntry[hashTable.length * 2];
            for (Entry<K, V> e : entrySet()) {
                if (e != null) {
                    EEntry<K, V> tmp = (EEntry<K, V>) e;
                    while (tmp != null) {
                        addToEntryToTable((EEntry<K, V>) e, newHashTable);
                        tmp = tmp.next;
                    }
                }
            }
            return newHashTable;


    }

    /*******************************************************************************************************************
     */
    private int getHash(K key, int size) {
        return Math.abs(key.hashCode() % size);/*(key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16)*/
    }


    @Override
    public boolean containsKey(Object key) {


        int index = getHash((K) key, hashTable.length);
        EEntry currentEntry = hashTable[index];
        if (currentEntry == null) {
            return false;
        }

        while (!currentEntry.getKey().equals(key)/* != key*/) {
            if (currentEntry.next == null) {
                return false;
            }
            currentEntry = currentEntry.next;
        }
        /*return super.containsKey(key);*/
        return true;
    }

    /*******************************************************************************************************************
     *
     * @param key a
     * @return a
     */
    @Override
    public V get(Object key) {
        int index = getHash((K) key, hashTable.length);

        EEntry currentEntry = hashTable[index];
        if (currentEntry == null) {
            return null;
        }

        while (!currentEntry.getKey().equals(key)) {
            if (currentEntry.next == null) {
                return null;
            }
            currentEntry = currentEntry.next;
        }
        return (V) currentEntry.value/*super.get(key)*/;
    }

    //==================================




    /*******************************************************************************************************************
     *
     * @param <K>
     * @param <V>
     */
    static class EEntry<K, V> implements Map.Entry<K, V> {
        K key;
        V value;
        EEntry next;

        EEntry(K key, V value, EEntry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
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

    /*******************************************************************************************************************
     *
     * @param <T>
     */
    static class MyIterator<T> implements Iterator<T>{
        int index = 0;
        int size;
        T[] array;

        public MyIterator(T[] array) {
            this.array = array;
            this.size = array.length;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            return array[index++];
        }
    }
}





