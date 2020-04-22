package com.shpp.p2p.cs.bcimbal.assignment17;

import java.util.*;

public class HHashMap<K, V> extends AbstractMap<K, V>  {

    Set<EEntry<K,V>> entries;
    int size = 0;

    @Override
    public Set<Entry<K, V>> entrySet() {
        return  null;
    }

    HHashMap() {
         entries = new EEntrySet<>();

    }

    @Override
    public V put(K key, V value) {
        EEntry e = new EEntry(key, value);
        entries.add(e);
        size++;
        return value;
    }

}
class EEntrySet <K,V> extends AbstractSet<EEntry<K, V>> {

    EEntry [] entriesList;
    int size = 0;

    EEntrySet () {
        entriesList = new EEntry[10];
    }

    @Override
    public boolean add(EEntry<K, V> kveEntry) {
        entriesList[size] = (kveEntry);
        size++;
        return true;
    }

    @Override
    public Iterator<EEntry<K, V>> iterator() {
        Iterator it = Arrays.asList(entriesList).iterator();
        return it;
    }

    @Override
    public int size() {
        return this.size;
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

