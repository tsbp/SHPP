package com.shpp.p2p.cs.bcimbal.assignment16;


import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

/* The class to perform tests of LinkList class methods*/

class LinkListTest {

    /* Tested collection */
    LinkList<String> expected;
    /* Reference collection */
    LinkedList<String> actual;

    private static int COLLECTION_ITEMS_COUNT = 13;

    /* Fill both collections before executing each test*/
    @BeforeEach
    void setup() {
        expected =  new LinkList<>();
        actual = new LinkedList<>();

        for(int i = 0; i < COLLECTION_ITEMS_COUNT; i++) {
            String str = "test_" + i;
            expected.add(str);
            actual.add(str);
        }
    }

    /*******************************************************************************************************************
     * add() method test
     */
    @Test
    void add() {
        Assert.assertEquals (expected, actual);
    }

    /*******************************************************************************************************************
     * add(index, value) method test
     */
    @Test
    void testAddInner() {
        String str = "inserted";
        expected.add(5, str);
        actual.add(5, str);
        Assert.assertEquals (expected, actual);
    }

    /*******************************************************************************************************************
     * get() method test
     */
    @Test
    void get() {
        actual.clear();
        for (int i = 0; i < expected.size(); i++) {
            actual.add(expected.get(i));
        }
        Assert.assertEquals (expected, actual);
    }

    /*******************************************************************************************************************
     * addFirst() and addLast() methods test
     */
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

    /*******************************************************************************************************************
     * listIterator() method test
     */
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

    /*******************************************************************************************************************
     * set() method test
     */
    @Test
    void set() {
        String str = "replaced";
        expected.set(9, str);
        actual.set(9, str);
        Assert.assertEquals (expected, actual);
    }

    /*******************************************************************************************************************
     * clear() method test
     */
    @Test
    void clear() {
        expected.clear();
        actual.clear();
        Assert.assertEquals (expected, actual);
    }

    /*******************************************************************************************************************
     * remove() method test
     */
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