package com.shpp.p2p.cs.bcimbal.assignment15;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class Assignment15Part1 extends Compressor{



    /******************************************************************************************************************/
    public static void main(String[] args) {

        FreqList symbolFreqs = new FreqList("assets/test.txt");
        CTree tree = new CTree(symbolFreqs.getFreqList());
        SymbolCode [] codes = tree.getCodes();

        String a = "abcccdddddd";
        Compressor compressor = new Compressor();
        LinkedList<Byte> compressed = compressor.compress(a.getBytes(), codes);

        //----------------------------------------------------------------
        byte [] fr = symbolFreqs.getFreqListAsByteArray();

        FreqList restored = new FreqList(fr);
        CTree treeRestored = new CTree(restored.getFreqList());
        SymbolCode [] codesRestored = treeRestored.getCodes();
        ArrayList<Byte> decompressed = compressor.decompress(a.length(), compressed, treeRestored);
        System.out.println("1234");
    }




}


