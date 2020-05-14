package com.shpp.p2p.cs.bcimbal.original.assignment16;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

/***********************************************************************************************************************
 *  The class to perform tests of SStack class methods
 */
class SStackTest {

    /* Tested collection */
    SStack<String> expected;
    /* Reference collection */
    Stack<String> actual;

    private static int COLLECTION_ITEMS_COUNT = 3000;

    /*******************************************************************************************************************
     *  Fill both collections before executing each test
     */
    @BeforeEach
    void setup() {
        expected =  new SStack<>();
        actual = new Stack<>();

        for(int i = 0; i < COLLECTION_ITEMS_COUNT; i++) {
            String str = "test_" + i;
            actual.push(str);
            expected.push(str);
        }
    }

    /*******************************************************************************************************************
     * pop() and empty() methods test
     */
    @Test
    void pop_empty() {
        for(int i = 0; i < COLLECTION_ITEMS_COUNT; i++) {
            actual.pop();
            expected.pop();
        }
        Assert.assertEquals (actual.empty(), expected.empty());
        Assert.assertEquals (expected, actual);
    }

    /*******************************************************************************************************************
     * peek() method test
     */
    @Test
    void peek() {
        String actIndex = actual.peek();
        String expIndex = expected.peek();
        Assert.assertEquals (expIndex, actIndex);
    }

    /*******************************************************************************************************************
     * push() method test
     */
    @Test
    void push() {
        Assert.assertEquals (expected, actual);
    }

    /*******************************************************************************************************************
     * search() method test
     */
    @Test
    void search() {
        int actIndex = actual.search("test_19");
        int expIndex = expected.search("test_19");

        Assert.assertEquals (expIndex, actIndex);
    }
}