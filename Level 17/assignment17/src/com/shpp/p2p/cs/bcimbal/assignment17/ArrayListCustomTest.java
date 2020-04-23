package com.shpp.p2p.cs.bcimbal.assignment17;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListCustomTest {

    @Test
    void add() {
        ArrayList<Integer> b = new ArrayList<>();
        ArrayListCustom<Integer> a = new ArrayListCustom<>();
        a.add(23);
        a.add(45);
        b.add(23);
        b.add(45);
        System.out.println("");
    }
}