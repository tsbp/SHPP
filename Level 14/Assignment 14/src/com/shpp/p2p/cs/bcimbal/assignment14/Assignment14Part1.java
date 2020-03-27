package com.shpp.p2p.cs.bcimbal.assignment14;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/*******************************************************************************************************************
 * The class makes archiveing
 */

public  class Assignment14Part1{
    public static void main(String args[]) {
        String outString = "";
        String fileName = "assets/test.txt";
        try{
            outString = new String(Files.readAllBytes(Paths.get(fileName)));
        }
        catch (Exception e) {

        }
        //System.out.println(outString);
        HashMap<Character, Integer> charTable = getCharTable(outString);
        byte [] packed = pack(outString.getBytes(), charTable);
    }

    /******************************************************************************************************************/
    private static byte[] pack(byte[] input, HashMap<Character, Integer> charTable) {
        int bitsCount = getBitsCount(charTable.size(), 2);
//        for(int i = 0; i < input.length; i++) {
//            input[i] <<= 8 - bitsCount;
//        }
        ArrayList<Byte> tmp = new ArrayList<>();
        int curIndex = 0;

        while(curIndex < input.length) {
           tmp.add(input[curIndex]);
           int bitsCurr = 8 - bitsCount;
           int bitsNext = bitsCurr;
           while (bitsCurr < 8) {

           }
           curIndex++;
        }

        return null;
    }

    /****************************************************************************************************************/
    private static HashMap<Character, Integer> getCharTable (String inputString) {
        int totalUnicumChars = 0;
        HashMap<Character, Integer> tmp = new HashMap<>();
        for(int i = 0; i < inputString.length(); i++) {
            Character ch = inputString.charAt(i);
            if(!tmp.containsKey(ch)) {
                tmp.put(ch, totalUnicumChars);
                totalUnicumChars++;
            }

        }
        return  tmp;
    }

    private static int getBitsCount(int x, int base) {
        return (int) (Math.log(x) / Math.log(base)) + 1;
    }

}
