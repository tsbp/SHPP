package com.shpp.p2p.cs.bcimbal.assignment16;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;


class SStackTest {

    SStack<String> expected;
    Stack<String> actual;

    @BeforeEach
    void setup() {
        expected =  new SStack<>();
        actual = new Stack<>();

        for(int i = 0; i < 20; i++) {
            String str = "test_" + i;
            actual.push(str);
            expected.push(str);
        }
    }

    @Test
    void pop_empty() {
        for(int i = 0; i < 20; i++) {
            actual.pop();
            expected.рор();
        }
        Assert.assertEquals (actual.empty(), expected.empty());
        Assert.assertEquals (expected, actual);
    }

    @Test
    void peek() {
        String actIndex = actual.peek();
        String expIndex = expected.peek();
        Assert.assertEquals (expIndex, actIndex);
    }

    @Test
    void push() {
        Assert.assertEquals (expected, actual);
    }

    @Test
    void search() {
        int actIndex = actual.search("test_19");
        int expIndex = expected.search("test_19");

        Assert.assertEquals (expIndex, actIndex);
    }
}