package com.shpp.p2p.cs.bcimbal.assignment16;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
/* The class to perform tests of ArrList class methods*/

class ArrListTest {

    /* Tested collection */
    List<String> expected;
    /* Reference collection */
    List<String> actual;

    private static int COLLECTION_ITEMS_COUNT = 3000;

    /* Fill both collections before executing each test*/
    @BeforeEach
    void setup() {
        expected =  new ArrList<>();
        actual = new ArrayList<>();

        for(int i = 0; i < COLLECTION_ITEMS_COUNT; i++) {
            String str = "test_" + i;
            expected.add(str);
            actual.add(str);
        }
    }


    @Test
    void add() {
        Assert.assertEquals (expected, actual);
    }


    @Test
    void set() {
        /* change values of items from 5 to 10 */
        for(int i = 5; i <= 10; i++) {
            String str = "replaced_" + i;
            expected.set(i, str);
            actual.set(i, str);
        }
        Assert.assertEquals (expected, actual);
    }

    @Test
    void remove() {
        /* remove item at tail */
        expected.remove(expected.size() - 1);
        actual.remove(actual.size() - 1);

        /* remove item at head */
        expected.remove(0);
        actual.remove(0);

        /* remove inner item */
        expected.remove(3);
        actual.remove(3);

        Assert.assertEquals (expected, actual);
    }

    @Test
    void iterator() {
        List <String> expected2 = new ArrayList<>();
        for(String str : expected) {
            expected2.add(str);
        }
        Assert.assertEquals (expected2, actual);
    }

    /* get() method test */
    @Test
    void get() {
        ArrList<String> exp = new ArrList<>();
        for(int i = 0; i < expected.size(); i++) {
            exp.add(expected.get(i));
        }
        Assert.assertEquals (exp, actual);
    }

    @Test
    void size() {
        Assert.assertEquals (expected.size(), actual.size());
    }
}