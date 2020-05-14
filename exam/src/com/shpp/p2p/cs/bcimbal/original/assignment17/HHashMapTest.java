package com.shpp.p2p.cs.bcimbal.original.assignment17;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class HHashMapTest {

    private static  final int ITERATIONS_COUNT = 13;
    HashMap<String, String> actual;
    HHashMap<String, String> expected;

    @BeforeEach
    void setup() {
        actual = new HashMap<>();
        expected = new HHashMap<>();
        for(int i = 0; i < ITERATIONS_COUNT; i++){
            String key = "key_" + i;
            String value = "value_" + i;
            actual.put(key, value);
            expected.put(key, value);
        }
    }

    @Test
    void keySet() {
        Set sActual = actual.keySet();
        Set sExpected = expected.keySet();

        Assert.assertTrue(sActual.containsAll(sExpected));
    }

    @Test
    void values() {
        Collection cActual = actual.values();
        Collection cExpected = expected.values();
        Assert.assertTrue(cActual.containsAll(cExpected));
    }

    @Test
    void entrySet() {
        Set sActual = actual.entrySet();
        Set sExpected = expected.entrySet();

        Assert.assertTrue(sActual.containsAll(sExpected));
    }

    @Test
    void size() {
        Assert.assertTrue(expected.size() == actual.size());
    }

    @Test
    void put() {
        entrySet();
    }

    @Test
    void remove() {
        Object [] keyArray = actual.keySet().toArray();
        for(int i = 0; i < ITERATIONS_COUNT; i += 2) {
            expected.remove(keyArray[i]);
            Assert.assertFalse(expected.containsKey(keyArray[i]));
        }
    }

    @Test
    void containsKey() {
        for (String key : actual.keySet()) {
            Assert.assertTrue(expected.containsKey(key));
        }
    }

    @Test
    void get() {
        for (String key : actual.keySet()) {
            Assert.assertEquals(expected.get(key), actual.get(key));
        }
    }
}