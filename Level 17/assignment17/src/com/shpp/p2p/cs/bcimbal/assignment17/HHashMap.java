package com.shpp.p2p.cs.bcimbal.assignment17;

import java.util.*;

/**
 * The class represent bad attempt of HashMap implementation
 * Never use this class
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
        Entry<K, V>[] allEntries = new Entry[size];
        int index = 0;
        for (EEntry eEntry : hashTable) {
            if (eEntry != null) {
                allEntries[index++] = eEntry;
                EEntry tmp = eEntry;
                while (tmp.next != null) {
                    allEntries[index++] = tmp.next;
                    tmp = tmp.next;
                }
            }
        }
        return new AbstractSet<Entry<K, V>>() {
            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new MyIterator<>(allEntries);
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
        if(entryAddedToTable(e, hashTable)) {
            size++;
        }
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
//    @Override
//    public V remove(Object key) {
//
//        int index = getHash((K) key, hashTable.length);
//        V value = get(key);
//        EEntry tmp = hashTable[index];
//
//        if (Objects.equals(tmp.getKey(), key)) { // first in chain
//            hashTable[index] = hashTable[index].next;
//            size--;
//            return value;
//        } else {
//            if(tmp == null) {
//                return null;
//            }
//            do  {
//                if(Objects.equals(tmp.getKey(), key)) {
//                    size--;
//                    return value;
//                }
//                tmp = tmp.next;
//            }
//            while (tmp != null);
//
//        }
//        return null;
//    }

    /*******************************************************************************************************************
     * Method to add entry to hash table
     * @param e entry to add
     * @param hTable destination hash table
     * @return
     */
    private boolean entryAddedToTable(EEntry<K, V> e, EEntry<K, V>[] hTable) {

        int index = getHash(e.key, hTable.length);

        EEntry tmp = hTable[index];
        if (tmp == null) {
            hTable[index] = e;
            return true;
        }
          else {
            while (true) {
                if (Objects.equals(e.key, tmp.key)) { // found same key
                    tmp.setValue(e.getValue());
                    return false;
                }

                if(tmp.next == null){
                    tmp.next = e;
                    return true;
                }
                tmp = tmp.next;
            }
        }
    }

    /*******************************************************************************************************************
     * Grow up hash table size
     * @return resized hash table
     */
    private EEntry<K, V>[] hashTableResize() {

        EEntry<K, V>[] newHashTable = new EEntry[hashTable.length * 2 - 1];
        for (Entry<K, V> e : entrySet()) {
            if (e != null) {
                ((EEntry<K, V>) e).next = null; //clear links
                entryAddedToTable((EEntry<K, V>) e, newHashTable);
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
        if(key == null) {
            return 0;
        }
        int hash = key.hashCode();
        return Math.abs((hash /*/ 3*/) % size);
    }

    /*******************************************************************************************************************
     * Check if map contains key
     * @param key input key
     * @return true ii contains, otherwise - false
     */
    @Override
    public boolean containsKey(Object key) {
        if(get(key) == null) {
            return false;
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
        while (!Objects.equals(currentEntry.getKey(), key)) {
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
    static class EEntry<K, V> implements Entry<K, V>{
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
            throw new NullPointerException("Ha-ha. Got you, equals!");
            /*return false;*/
        }

        @Override
        public int hashCode() {
            throw new NullPointerException("Ha-ha. Got you, hasCode!");
            /*return key.hashCode();*/
        }
    }

    /*******************************************************************************************************************
     * Iterator
     * @param <T> iterator type
     */
    static class MyIterator<T> implements Iterator {
        int index = 0;
        int size;
        T[] array;

        public MyIterator(T[] array) {
            this.array = array;
            this.size = array.length;
        }

//        @Override
//        public void remove() {
//
//        }

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





