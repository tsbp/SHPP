package com.shpp.p2p.cs.bcimbal.assignment16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Assignment16Part1 {

    public static void main(String[] args) {



        Integer[] nums = {10, -5, 4, 8, -2, -10, 1, 4, 2};
        ArrayList<Integer> numList = new ArrayList<>(Arrays.asList(nums));

        Iterator<Integer> numListIter = numList.iterator();
        while (numListIter.hasNext()) {
            int n = numListIter.next();
            if (n < 0) {
                numListIter.remove();
            }
        }

        System.out.println(numList);


        ArrayList<String> sa = new ArrayList<>();
        VArrayList<String> va = new VArrayList<>();
        for(int i = 0; i < 10; i++) {
            String str = "test_" + i;
            va.add(str);
            sa.add(str);
        }
        va.add("overflow");
        System.out.println(va.size());

        for(String str : sa) {
            System.out.println(str);
        }
//        for(String str : va) {
//            System.out.println(str);
//        }
    }
}
