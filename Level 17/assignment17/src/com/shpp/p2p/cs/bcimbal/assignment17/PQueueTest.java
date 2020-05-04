package com.shpp.p2p.cs.bcimbal.assignment17;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PQueueTest {


    @Test
    void add() {

        PriorityQueue<Integer> actual = new PriorityQueue<>();
        PQueue<Integer> expected = new PQueue<>();
        int[] tmp = new int[20];
        for(int i = 0; i < 20; i++) {
//            String s = "test_" + new Random().nextInt(20);
            int s = new Random().nextInt(20);
            expected.add(s );
            actual.add(s);
            tmp[i] = s;
        }

        Arrays.sort(tmp);

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