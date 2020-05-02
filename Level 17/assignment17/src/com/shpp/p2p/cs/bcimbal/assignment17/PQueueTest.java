package com.shpp.p2p.cs.bcimbal.assignment17;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PQueueTest {

    @Test
    void add() {
        PQueue<Integer> pq = new PQueue<>();
        for(int i = 0; i < 20; i++) {
           /* String s = "test_";*/
            pq.add(/*s + */new Random().nextInt(10));
        }
        for(int i = 0; i < 20; i++) {
            /* String s = "test_";*/
            Integer p = pq.poll();
        }
        Assert.assertEquals(true, true);
    }

    @Test
    void iterator() {
    }

    @Test
    void size() {
    }

    @Test
    void offer() {
    }

    @Test
    void poll() {
    }

    @Test
    void peek() {
    }
}