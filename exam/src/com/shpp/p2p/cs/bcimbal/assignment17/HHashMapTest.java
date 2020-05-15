package com.shpp.p2p.cs.bcimbal.assignment17;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

class HHashMapTest {

    private static  final int ITERATIONS_COUNT = 13000;
    HashMap<String, String> actual;
    HHashMap<String, String> expected;

    @BeforeEach
    void setup() {
        actual = new HashMap<>();
        expected = new HHashMap<>();
        actual.put(null, "v_null");
        expected.put(null, "v_null");
        for(int i = 0; i < ITERATIONS_COUNT; i++){
            String key = "key_" + i;
            String value = "value_" + i;
            actual.put(key, value);
            expected.put(key, value);
        }
    }

    @Test
    void keySet() {
        Set<String> sActual = actual.keySet();
        Set<String> sExpected = expected.keySet();

        Assert.assertTrue(sActual.containsAll(sExpected));
    }

    @Test
    void values() {
        Collection<String> cActual = actual.values();
        Collection<String> cExpected = expected.values();
        Assert.assertTrue(cActual.containsAll(cExpected));
    }

    @Test
    void entrySet() {
        Set<Map.Entry<String, String>> sActual = actual.entrySet();
        Set<Map.Entry<String, String>> sExpected = expected.entrySet();

        Assert.assertTrue(sActual.containsAll(sExpected));
    }

    @Test
    void size() {
        Assert.assertEquals(expected.size(), actual.size());
    }

    @Test
    void put() {
        entrySet();
        // put existing key
        expected.put("key_0", "value changed");
        Assert.assertEquals(expected.get("key_0"), "value changed");
    }

    @Test
    void remove() {
        Assert.assertEquals(expected.remove("not present key"), null);
        Object [] keyArray = actual.keySet().toArray();
        for(int i = 0; i < ITERATIONS_COUNT; i += 2) {
            Assert.assertTrue(expected.containsKey(keyArray[i]));
            expected.remove(keyArray[i]);
            Assert.assertEquals(expected.get(keyArray[i]), null);
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