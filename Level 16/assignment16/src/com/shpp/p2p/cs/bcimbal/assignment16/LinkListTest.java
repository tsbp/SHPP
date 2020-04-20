package com.shpp.p2p.cs.bcimbal.assignment16;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/* The class to perform tests of LinkList class methods*/

class LinkListTest {

    /* Tested collection */
    LinkList<String> expected;
    /* Reference collection */
    LinkedList<String> actual;

    private static int COLLECTION_ITEMS_COUNT = 3000;

    /* Fill both collections before executing each test*/
    @BeforeEach
    void setup() {
        expected =  new LinkList<>();
        actual = new LinkedList<>();

        for(int i = 0; i < 3000; i++) {
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
    void testAddInner() {
        String str = "inserted";
        expected.add(5, str);
        actual.add(5, str);
        Assert.assertEquals (expected, actual);
    }

    @Test
    void get() {
        LinkedList<String> exp = new LinkedList<>();
        for (String s : expected) {
            exp.add(s);
        }
        Assert.assertEquals (exp, actual);
    }

    @Test
    void addFirstLast() {
        String str = "last";
        expected.addLast(str);
        actual.addLast(str);

        str = "first";
        expected.addFirst(str);
        actual.addFirst(str);

        Assert.assertEquals (expected, actual);
    }

    @Test
    void listIterator() {
        List <String> expected2 = new LinkedList<>();
        for(String str : expected) {
            expected2.add(str);
        }
        Assert.assertEquals (expected2, actual);
    }

    @Test
    void size() {
        Assert.assertEquals (expected.size(), actual.size());
    }

    @Test
    void set() {
        String str = "replaced";
        expected.set(9, str);
        actual.set(9, str);
        Assert.assertEquals (expected, actual);
    }

    @Test
    void clear() {
        expected.clear();
        actual.clear();
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
}