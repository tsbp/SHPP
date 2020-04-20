package com.shpp.p2p.cs.bcimbal.assignment16;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

class QQueueTest {

    /* Tested collection */
    QQueue<String> expected;
    /* Reference collection */
    Queue<String> actual;

    private static int COLLECTION_ITEMS_COUNT = 5;

    /* Fill both collections before executing each test*/
    @BeforeEach
    void setup() {
        expected =  new QQueue<>();
        actual = new LinkedList<>();

        for(int i = 0; i < COLLECTION_ITEMS_COUNT; i++) {
            String str = "test_" + i;
            actual.add(str);
            expected.add(str);
        }
    }

    @Test
    void peek() {
        String exp = expected.peek();
        String act = actual.peek();
        Assert.assertEquals(expected, actual);
    }

    @Test
    void poll() {
        for(int i = 0; i < COLLECTION_ITEMS_COUNT / 2; i++) {
            expected.poll();
            actual.poll();
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    void offer() {
        expected =  new QQueue<>(COLLECTION_ITEMS_COUNT);
        for(int i = 0; i < COLLECTION_ITEMS_COUNT; i++) {
            String str = "test_" + i;
            expected.add(str);
        }
        expected.offer("offered");
        Assert.assertEquals(expected, actual);
    }

    @Test
    void remove() {
        for(int i = 0; i < COLLECTION_ITEMS_COUNT / 2; i++) {
            expected.remove();
            actual.remove();
        }
        Assert.assertEquals(expected, actual);
    }

    @Test
    void element() {
    }

}