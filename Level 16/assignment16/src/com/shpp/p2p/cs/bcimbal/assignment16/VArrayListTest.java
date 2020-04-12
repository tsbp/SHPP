package com.shpp.p2p.cs.bcimbal.assignment16;

import org.junit.Assert;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VArrayListTest {

    @org.junit.jupiter.api.Test
    void add() {
        ArrayList<String> expected = new ArrayList<>();
        ArrayList<String> actual = new ArrayList<>();
        int index = 0;
        for(int i = 0; i < 10; i++) {
            expected.add("test_" + i);
            actual.add("test_" + i);
        }
        //actual.add("test_" + 25);
        Assert.assertEquals(expected, actual);
    }
}