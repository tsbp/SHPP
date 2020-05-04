package com.shpp.p2p.cs.bcimbal.assignment17;

import java.util.*;


/**
 * The class represent bad attempt of HashMap implementation
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class HHashMap<K, V> extends AbstractMap<K, V> {

    /* initial capacity constant */
    private static final int INITIAL_CAPACITY = 11;
    /* objects storage array (hashcode as index)*/
    private EEntry[] hashTable;
    /* map size */
    private int size = 0;

    /*******************************************************************************************************************
     * The constructor initializes objects storage array
     */
    public HHashMap() {
        hashTable = new EEntry[INITIAL_CAPACITY];
    }

    /*******************************************************************************************************************
     * Method to get a set of current map entries keys
     * @return a set of entries keys
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
     * Method to get a collection of current map entries values
     * @return a collection of entries values
     */
    @Override
    public Collection<V> values() {
        V[] valsArr = (V[]) new Object[size];

        int index = 0;
        for (Entry e : entrySet()) {
            if (e != null) {
                valsArr[index++] = (V) e.getValue();
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
     * Method to get a set of current map entries (key-value pairs)
     * @return a set of entries
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        EEntry<K, V>[] e = new EEntry[size];
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

    /*******************************************************************************************************************
     * Get current map size
     * @return current map size
     */
    @Override
    public int size() {
        return size;
    }

    /*******************************************************************************************************************
     * Add key-value pair to map
     * @param key entry key
     * @param value entry value
     * @return value
     */
    @Override
    public V put(K key, V value) {
        EEntry<K, V> e = new EEntry(key, value, null);
        addToEntryToTable(e, hashTable);

        size++;
        if (size >= hashTable.length) {
            hashTable = hashTableResize();
        }
        return value;
    }

    /*******************************************************************************************************************
     * Remove key-value pair from current map
     * @param key the key of key-value pair to remove
     * @return the value of key-value pair to remove
     */
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

    /*******************************************************************************************************************
     * Method to add entry to hash table
     * @param e entry to add
     * @param hTable destination hash table
     */
    private void addToEntryToTable(EEntry<K, V> e, EEntry<K, V>[] hTable) {

        int index = getHash(e.getKey(), hTable.length);

        EEntry tmp = hTable[index];
        if (tmp != null) {
            while (tmp.next != null) {
                tmp = tmp.next;
            }
            tmp.next = e;

        } else {

            hTable[index] = e;
        }
    }

    /*******************************************************************************************************************
     * Grow up hash table size
     * @return resized hash table
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
     * Generate hashcode of key as remainder of dividing by size
     * @param key input key
     * @param size input size
     * @return hashcode integer value
     */
    private int getHash(K key, int size) {
        return Math.abs(key.hashCode() % size);/*(key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16)*/
    }

    /*******************************************************************************************************************
     * Check if map contains key
     * @param key input key
     * @return true ii contains, otherwise - false
     */
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
        return true;
    }

    /*******************************************************************************************************************
     * The value getter by key
     * @param key input key
     * @return requested by key value
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
        return (V) currentEntry.value;
    }

    /*******************************************************************************************************************
     * The class of map entry stores value linked to key (key-value pair)
     * @param <K> the type of keys
     * @param <V> the type of values
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
     * Iterator
     * @param <T> iterator type
     */
    static class MyIterator<T> implements Iterator<T> {
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





