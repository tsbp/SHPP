package com.shpp.p2p.cs.bcimbal.assignment17;

import java.util.*;

public class HHashMap<K, V> extends AbstractMap<K, V> {

    private static final int INITIAL_CAPACITY = 10;
    private Set<Entry<K, V>> entrySet;
    /*private EEntry[]*//*Set<Entry<K,V>>*//* enties;*/
    private EEntry[]/*Set<Entry<K,V>>*/ hashTable;
    private int size = 0;
    //private EEntry first;
    EEntry current;


    HHashMap() {
        hashTable = new EEntry[INITIAL_CAPACITY];
    }


    /*******************************************************************************************************************
     *
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public Set<K> keySet() {
        //EEntry<K, V> [] arr =  (EEntry[]) hashTable;
        K[] keysArr = (K[]) new Object[size];

        int index = 0;
        for (EEntry e : hashTable) {
            if (e != null) {
                //EEntry<K, V> current = e;
                keysArr[index++] = (K)e.getKey();
            }
        }

        return new AbstractSet<K>() {
            K[] keysA = keysArr;

            @Override
            public Iterator<K> iterator() {
                return new MyIterator<K>(keysArr);
            }

            @Override
            public int size() {
                return size;
            }
        };
    }

    /*******************************************************************************************************************
     *
     * @return
     */
    @Override
    public Collection<V> values() {
        EEntry<K, V>[] arr = (EEntry[]) hashTable;
        V[] valsArr = (V[]) new Object[size];

        int index = 0;
        for (EEntry e : hashTable) {
            if (e != null) {
                EEntry<K, V> current = e;
                valsArr[index++] = current.getValue();
            }
        }
        return new AbstractCollection<V>() {
            @Override
            public Iterator<V> iterator() {
                return new MyIterator<V>(valsArr);
            }

            @Override
            public int size() {
                return valsArr.length;
            }
        };
    }

    /*******************************************************************************************************************
     *
     * @return
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        entrySet = new AbstractSet<Entry<K, V>>() {
            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new MyIterator<>(hashTable);

            }

            @Override
            public int size() {
                return size;
            }
        };
        return entrySet;
    }

    @Override
    public int size() {
        return size;
    }

    /*******************************************************************************************************************
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public V put(K key, V value) {
        EEntry<K, V> e = new EEntry<K, V>(key, value, null);

//        if (size == 0) {
//            first = e;
//        }
        addToEntryToTable(e, hashTable);

        size++;
        if (size >= hashTable.length) {
            hashTable = hashTableResize();
        }
        entrySet = entrySet();
        return value;
    }

    private void addToEntryToTable(EEntry<K,V> e, EEntry[] hashTable) {
        int index = getHash(e.getKey());

        EEntry<K, V> tmp = hashTable[index];
        if (tmp != null) {
            //e.next = tmp;
            while (tmp.next != null) {
                tmp = tmp.next;
            }
            tmp.next = e;
            hashTable[index] = tmp;
            //size++;
            index = 0;
            do{} while (hashTable[index] != null && index++ < hashTable.length);
            /*hashTable[index] = e;*/
        } /*else*/ {

            hashTable[index] = e;
        }
    }


    /*******************************************************************************************************************
     *
     */
    private  EEntry<K, V>[] hashTableResize() {
        EEntry<K, V>[] newHashTable = new EEntry[hashTable.length * 2];
        for(EEntry e : hashTable){
            addToEntryToTable(e, newHashTable);
        }
        return newHashTable;
    }

    /*******************************************************************************************************************
     */
    private int getHash(K key) {
        int h;
        return Math.abs(key.hashCode() % hashTable.length);/*(key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16)*/
    }


    @Override
    public boolean containsKey(Object key) {

        int index = getHash((K) key);
        if (!isIndexInOfBounds(index)) {
            return false;
        }
        EEntry currentEntry = hashTable[index];
        if (currentEntry == null) {
            return false;
        }

        while (currentEntry.getKey() != key) {
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
     * @param key
     * @return
     */
    @Override
    public V get(Object key) {
        int index = getHash((K) key);

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
     * check Index In Of Bounds of array
     * @param index input index
     * @return true if Index In Of Bounds
     */
    private boolean isIndexInOfBounds(int index) {
        if (index >= 0 && index < size) {
            return true;
        }
        //throw  new IndexOutOfBoundsException();
        return false;
    }

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
    static class MyIterator<T> implements Iterator {
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





