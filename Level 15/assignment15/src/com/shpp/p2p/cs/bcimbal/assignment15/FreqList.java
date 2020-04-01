package com.shpp.p2p.cs.bcimbal.assignment15;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class FreqList {

    private static final int IO_BUFFER_SIZE = 10;
    int totalBytes = 0;

//    int [] freqs = new int[256];

    LinkedList <Integer> freqs = new LinkedList<>(Collections.nCopies(256, 0));

    /******************************************************************************************************************/
    FreqList(String fileName) {
        HashMap<Character, Integer>freqs = getCharTable(fileName);
//        NavigableMap<Character, Integer>freqs1 = new TreeMap<>(freqs);
//
//        ArrayList<Integer> mapVals = new ArrayList<>(freqs1.values());
//        Collections.sort(mapVals);

        System.out.println("1213");
    }

    /******************************************************************************************************************/

    /******************************************************************************************************************/
    private HashMap<Character, Integer>  getCharTable(String fileName) {

        HashMap<Character, Integer> tmp = new HashMap<>();
        try {
            FileInputStream stream = new FileInputStream(fileName);
            int bytesToRead = stream.available();
            if (bytesToRead > IO_BUFFER_SIZE) bytesToRead = IO_BUFFER_SIZE;
            totalBytes += bytesToRead;
            while (bytesToRead > 0) {
                byte[] buffer = new byte[bytesToRead];
                stream.read(buffer);
                for (byte bt : buffer) {
                    char ch = (char) bt;
                    if(tmp.containsKey(ch)) {
                        int a = tmp.get(ch) + 1;
                        tmp.put(ch, a);
                    }
                    else {
                        tmp.put(ch, 1);
                    }
                }
                bytesToRead = stream.available();
                if (bytesToRead > IO_BUFFER_SIZE) bytesToRead = IO_BUFFER_SIZE;
                totalBytes += bytesToRead;
            }
        } catch (IOException e) {
            System.out.println("UPS");
            e.printStackTrace();
        }
        return tmp;
    }
}
