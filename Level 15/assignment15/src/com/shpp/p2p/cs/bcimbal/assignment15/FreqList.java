package com.shpp.p2p.cs.bcimbal.assignment15;

import org.w3c.dom.Node;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class FreqList {

    private static final int IO_BUFFER_SIZE = 3;

    LinkedList<BFreq> freqList;
    /******************************************************************************************************************/
    FreqList(String fileName) {
        freqList = readFreqsFromFile(fileName);
        //System.out.println(freqs);
    }

   public LinkedList<BFreq> getFreqList() {
        return freqList;
   }

    /******************************************************************************************************************/
    private LinkedList<BFreq> readFreqsFromFile(String fileName) {

        ArrayList<Integer> tmp = new ArrayList<>(Collections.nCopies((int)Math.pow(2, Byte.SIZE), 0));

        try (FileInputStream fStream = new FileInputStream(fileName);
             BufferedInputStream stream = new BufferedInputStream(fStream, IO_BUFFER_SIZE)) {
            byte[] buffer = new byte[IO_BUFFER_SIZE];
            int count = 0;
            while ((count = stream.read(buffer, 0, IO_BUFFER_SIZE)) > 0) {
                for (int i = 0; i < count; i++) {
                    int a = buffer[i] & 0xff;
                    tmp.set(a, tmp.get(a) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("UPS");
            e.printStackTrace();
        }

        LinkedList<BFreq> freq= new LinkedList<>();
        for(int i = 0; i < 256; i++) {
            if(tmp.get(i) > 0) {
                freq.add(new BFreq(tmp.get(i), (byte) i, /*null,*/ null, null));
            }
        }
        Collections.sort(freq, Comparator.comparing(BFreq::getFreq));
        return freq;
    }
}


