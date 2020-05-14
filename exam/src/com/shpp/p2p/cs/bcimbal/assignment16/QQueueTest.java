package com.shpp.p2p.cs.bcimbal.assignment16;


import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/* The class to perform tests of QQueue class methods*/
class QQueueTest {

    /* Tested collection */
    QQueue<String> expected;
    /* Reference collection */
    Queue<String> actual;

    private static int COLLECTION_ITEMS_COUNT = 5;

    /*******************************************************************************************************************
     *  Fill both collections before executing each test
     *  */
    @BeforeEach
    void setup() {
        expected = new QQueue<>();
        actual = new LinkedList<>();

        for (int i = 0; i < COLLECTION_ITEMS_COUNT; i++) {
            String str = "test_" + i;
            actual.add(str);
            expected.add(str);
        }
    }

    /*******************************************************************************************************************
     * peek() method test
     */
    @Test
    void peek() {
        String exp = expected.peek();
        String act = actual.peek();
        Assert.assertEquals(exp, act);

        expected.clear();
        actual.clear();

        exp = expected.peek();
        act = actual.peek();
        Assert.assertEquals(exp, act);

    }

    /*******************************************************************************************************************
     * poll() method test
     */
    @Test
    void poll() {
        for (int i = 0; i < COLLECTION_ITEMS_COUNT / 2; i++) {
            expected.poll();
            actual.poll();
        }
        Assert.assertEquals(expected, actual);
    }

    /*******************************************************************************************************************
     * poll() method test
     */
    @Test
    void offer() {
        expected = new QQueue<>(COLLECTION_ITEMS_COUNT);
        for (int i = 0; i < COLLECTION_ITEMS_COUNT; i++) {
            String str = "test_" + i;
            expected.add(str);
        }
        expected.offer("offered");
        Assert.assertEquals(expected, actual);
    }

    /*******************************************************************************************************************
     * remove() method test
     */
    @Test
    void remove() {
        for (int i = 0; i < COLLECTION_ITEMS_COUNT / 2; i++) {
            expected.remove();
            actual.remove();
        }
        Assert.assertEquals(expected, actual);
    }

    /*******************************************************************************************************************
     * element() method test
     */
    @Test
    void element() {
        expected = new QQueue<>();
        Exception ex = null;
        try {
            expected.element();
        } catch (Exception e) {
            ex = e;
        }
        Assert.assertEquals(ex, new NoSuchElementException());
    }

}