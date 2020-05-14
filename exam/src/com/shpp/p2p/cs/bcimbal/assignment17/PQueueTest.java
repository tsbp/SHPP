package com.shpp.p2p.cs.bcimbal.assignment17;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PQueueTest {

    private static  final int ITERATIONS_COUNT = 13000;
    PriorityQueue<Integer> actual;
    PQueue<Integer> expected;

    @BeforeEach
    void setup(){
        actual = new PriorityQueue<>();
        expected = new PQueue<>();
        for(int i = 0; i < ITERATIONS_COUNT; i++) {
//            String s = "test_" + new Random().nextInt(20);
            int s = new Random().nextInt(ITERATIONS_COUNT);
            expected.add(s );
            actual.add(s);
        }
    }

    @Test
    void add() {
        setup();
        Object [] eArray =  expected.toArray();
        for(int i = 0; i < ITERATIONS_COUNT; i++) {
            Assert.assertEquals(expected.poll(), actual.poll());
        }
    }

    @Test
    void size() {
        Assert.assertTrue(expected.size() == actual.size());
    }

    @Test
    void offer() {
        expected = new PQueue<>(1);
        Assert.assertTrue(expected.offer(23));
        Assert.assertFalse(expected.offer(23));
    }

    @Test
    void poll() {
        for(int i = 0; i < ITERATIONS_COUNT; i++) {
            Assert.assertEquals(expected.poll(), actual.poll());
        }
    }

    @Test
    void peek() {
        Assert.assertTrue(expected.peek() == actual.peek());
    }
}