package com.shpp.p2p.cs.bcimbal.assignment17;

import java.util.*;

public class HHashMap<K, V> extends AbstractMap<K, V>  {

    private static final int INITIAL_CAPACITY = 10;
    private Set<Entry<K,V>> entrySet;
    private EEntry[]/*Set<Entry<K,V>>*/ hashTable;
    private int size = 0;
    private EEntry first;
    EEntry current;



    HHashMap() {
        hashTable = new EEntry[INITIAL_CAPACITY];
    }


    /*******************************************************************************************************************
     *
     * @return
     */
    @Override
    public Set<K> keySet() {
        EEntry<K, V> [] arr =  (EEntry[]) hashTable;
        K [] keysArr =  (K[]) new Object[size];

        int index = 0;
        for(EEntry e: hashTable) {
            if(e != null) {
                keysArr[index++] = (K) e.getKey();
                if (index >= size) break;
            }
        }

        return new AbstractSet<K>() {
            K [] keysA = keysArr;
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
        EEntry<K, V> [] arr =  (EEntry[]) hashTable;
        V [] valsArr =  (V[]) new Object[size];

        int index = 0;
        for(EEntry e: hashTable) {
            if(e != null) {
                valsArr[index++] = (V) e.getValue();
                if (index >= size) break;
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
                return hashTable.length;
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
        EEntry<K, V> e = new EEntry<K,V>(key, value, null);
        int kh = key.hashCode();
        int vh = value.hashCode();
        if(size == 0) {
            first = e;
        }
        int index = getHash(key);

        if(!containsKey(key)) {
            hashTable[index] = (e);
        }
        size++;
        return value;
    }

    /*******************************************************************************************************************
     */
    private int getHash( K key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }


    @Override
    public boolean containsKey(Object key) {
        int index = getHash((K)key);
        EEntry currentEntry = hashTable[index];
        if (currentEntry == null){
            return  false;
        }
        while(currentEntry.getKey() != key) {
            if(currentEntry.next == null) {
                return false;
            }
            currentEntry = currentEntry.next;
        }
        /*return super.containsKey(key);*/
        return true;
    }

    @Override
    public V get(Object key) {
        return super.get(key);
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
        EEntry (K key, V value, EEntry next){
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
        T [] array;

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





