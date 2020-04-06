package com.shpp.p2p.cs.bcimbal.assignment15;


import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class Assignment15Part1 extends CHelper {


    static String fileIn = "assets/123.png";
    static String fileOut = "assets/123.png.par";

    static String fileInD = "assets/123.png.par";
    static String fileOutD = "assets/123D.png";

    /******************************************************************************************************************/
    public static void main(String[] args) {

        FreqList symbolFreqs = new FreqList(fileIn);
        CTree tree = new CTree(symbolFreqs.getFreqList());
        SymbolCode[] codes = tree.getCodes();


        int uncompressedSize = 0;
        try (FileInputStream fStreamIn = new FileInputStream(fileIn);
             BufferedInputStream inputStream = new BufferedInputStream(fStreamIn, IO_BUFFER_SIZE);
             FileOutputStream fStreamOut = new FileOutputStream(fileOut);
             BufferedOutputStream outputStream = new BufferedOutputStream(fStreamOut, IO_BUFFER_SIZE)) {
            uncompressedSize = inputStream.available();
            Compressor compressor = new Compressor();
            compressor.compress(inputStream, outputStream, codes);

        } catch (IOException e) {
            System.out.println("UPS");
            e.printStackTrace();
        }

        //----------------------------------------------------------------
        byte[] fr = symbolFreqs.getFreqListAsByteArray();
        FreqList restored = new FreqList(fr);
        CTree treeRestored = new CTree(restored.getFreqList());

        try (FileInputStream fStreamIn = new FileInputStream(fileInD);
             BufferedInputStream inputStream = new BufferedInputStream(fStreamIn, IO_BUFFER_SIZE);
             FileOutputStream fStreamOut = new FileOutputStream(fileOutD);
             BufferedOutputStream outputStream = new BufferedOutputStream(fStreamOut, IO_BUFFER_SIZE)) {
            Compressor compressor = new Compressor();
            compressor.decompress(inputStream, outputStream,
                    uncompressedSize, treeRestored);

        } catch (IOException e) {
            System.out.println("UPS");
            e.printStackTrace();
        }


        System.out.println("123");
    }


}


