package com.shpp.p2p.cs.bcimbal.assignment17;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HHashMapTest {

    @Test
    void keySet() {
    }

    @Test
    void values() {
    }

    @Test
    void entrySet() {
    }

    @Test
    void size() {
    }

    @Test
    void put() {
        HashMap<String, String> actual = new HashMap<>();
        HHashMap<String, String> expected = new HHashMap<>();
        for(int i = 0; i < 10000; i++){
            String fStr = "test_" + i;
            expected.put("key_" + i, fStr);
            actual.put("key_" + i, fStr);
        }

        String[] tmp = new String[expected.size()];
        Set es = expected.entrySet();
        Set<String> s = expected.keySet();
        Collection<String> c = expected.values();
        int index = 0;
        for(String k : expected.keySet()) {
            if(k.equals("key_11999")){
                System.out.println("breakpoint");
            }
            if(!expected.containsKey(k)){
                System.out.println("breakpoint");
            }
            assertEquals(expected.containsKey(k), true);
        }

//        s.toString();
//        for(int i = 0; i < expected.keySet().size(); i++) {
//            if(expected.containsKey("kay_" + i)) {
//                String val = expected.get("key_" + i);
//                tmp[i] = val;
//            }
//        }
        System.out.println("breakpoint");
        //Assert.assertEquals(expected, actual);
    }

    @Test
    void containsKey() {
    }

    @Test
    void get() {
    }
}