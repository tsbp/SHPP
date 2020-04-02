package com.shpp.p2p.cs.bcimbal.assignment15;


import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Assignment15Part1 {


    /******************************************************************************************************************/
    public static void main(String[] args) {

        FreqList fl = new FreqList("assets/test.txt");
        BFreq root = createTree(fl.getFreqList());
        System.out.println("1213");

    }

    /*******************************************************************************************************************
     *
     * @param freqs
     * @return
     */
    private static BFreq createTree (LinkedList<BFreq> freqs) {
        while (freqs.size() > 1) {
            BFreq leafr = freqs.poll();
            BFreq leafl = freqs.poll();
            BFreq parent = new BFreq(leafl.getFreq() + leafr.getFreq(),
                    (byte) 0, leafl, leafr);
            freqs.addFirst(parent);
            Collections.sort(freqs, Comparator.comparing(BFreq::getFreq));
        }
        return freqs.poll();
    }

}