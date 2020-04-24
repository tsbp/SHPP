package com.shpp.p2p.cs.bcimbal.assignment17;

import org.junit.Assert;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HHashMapTest {


    @org.junit.jupiter.api.Test
    void entrySet() {
    }

    @org.junit.jupiter.api.Test
    void size() {
    }

    @org.junit.jupiter.api.Test
    void put() {
        HashMap<Integer, String> actual = new HashMap<>();
        HHashMap<Integer, String> expected = new HHashMap<>();
        expected.put(1, "first"); size();
        expected.put(2, "second");
        expected.put(3, "third");
        actual.put(1, "first"); size();
        actual.put(2, "second");
        actual.put(3, "third");

        String[] tmp = new String[expected.size()];
        Set<Integer>s = expected.keySet();
        Collection<String> c = expected.values();
        s.toString();
        for(int i : expected.keySet()) {
            tmp[i-1] = expected.get(i);
        }
        System.out.println("breakpoint");
        Assert.assertEquals(expected, actual);
    }
}