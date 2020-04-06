package com.shpp.p2p.cs.bcimbal.assignment15;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class FreqList extends CHelper{



    private LinkedList<CNode> freqList;
    private byte [] freqListAsByteArray;
    /******************************************************************************************************************/
    FreqList(String fileName) {
        freqList = readFreqsFromFile(fileName);
        freqListAsByteArray = createByteArray(freqList);
    }

    /*******************************************************************************************************************
     *
     * @param array
     */
    FreqList (byte [] array) {
        freqList = createList(array);
    }

    /*******************************************************************************************************************
     *
     * @return
     */
    public byte[] getFreqListAsByteArray() {
        return freqListAsByteArray;
    }

    /*******************************************************************************************************************
     *
     * @param freqList
     * @return
     */
    private byte[] createByteArray(LinkedList<CNode> freqList) {
        byte [] tmp = new byte[freqList.size() * 5];
        for(int i = 0; i < freqList.size(); i++) {
            CNode node = freqList.get(i);
            tmp[i * 5] = node.getByteValue();
            int frequency = node.getFreq();
            tmp[i * 5 + 1] = (byte)((frequency << 0) & 0xff);
            tmp[i * 5 + 2] = (byte)((frequency << 8) & 0xff);
            tmp[i * 5 + 3] = (byte)((frequency << 16) & 0xff);
            tmp[i * 5 + 4] = (byte)((frequency << 24) & 0xff);
        }
        return tmp;
    }



    /*******************************************************************************************************************
     *
     * @param array
     */
    private LinkedList<CNode> createList(byte[] array) {
        LinkedList<CNode> tmp = new LinkedList<>();
        for(int i = 0; i < array.length / 5; i++) {
            CNode [] children = {null, null};
            byte byteValue = array[i * 5];
            int freq = ((array[i * 5 + 1] & 0xff) << 0) |
                    ((array[i * 5 + 2] & 0xff) << 8) |
                    ((array[i * 5 + 3] & 0xff) << 16) |
                    ((array[i * 5 + 4] & 0xff) << 24);
            tmp.add(new CNode(freq, byteValue, children));
        }
        return tmp;
    }

    /*******************************************************************************************************************
     *
     * @return
     */
   public LinkedList<CNode> getFreqList() {
        return freqList;
   }

    /******************************************************************************************************************/
    private LinkedList<CNode> readFreqsFromFile(String fileName) {

        ArrayList<Integer> tmp = new ArrayList<>(Collections.nCopies((int)Math.pow(2, Byte.SIZE), 0));

        try (FileInputStream fStream = new FileInputStream(fileName);
             BufferedInputStream stream = new BufferedInputStream(fStream, IO_BUFFER_SIZE)) {
            byte[] buffer = new byte[IO_BUFFER_SIZE];
            int count = 0;
            while ((count = stream.read(buffer/*, 0, IO_BUFFER_SIZE*/)) > 0) {
                for (int i = 0; i < count; i++) {
                    int a = buffer[i] & 0xff;
                    tmp.set(a, tmp.get(a) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("UPS");
            e.printStackTrace();
        }

        LinkedList<CNode> freq= new LinkedList<>();
        for(int i = 0; i < 256; i++) {
            if(tmp.get(i) > 0) {
                CNode[] nodes = {null, null};
                freq.add(new CNode(tmp.get(i), (byte) i, /*null,*/ /*null,*/ nodes));
            }
        }
        Collections.sort(freq, Comparator.comparing(CNode::getFreq));
        return freq;
    }
}


