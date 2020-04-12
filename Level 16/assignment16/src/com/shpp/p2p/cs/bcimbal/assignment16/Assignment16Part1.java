package com.shpp.p2p.cs.bcimbal.assignment16;

import java.util.*;

public class Assignment16Part1 {

    public static void main(String[] args) {

        VLinkedList<String> va = new VLinkedList<>();
        for(int i = 0; i < 10; i++) {
            String str = "test_" + i;
            va.add(str);
        }
        va.add("overflow");
//        System.out.println(va.size());
//
        for(String str : va) {
            System.out.println(str);
        }
    }
}
