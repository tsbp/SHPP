package com.shpp.p2p.cs.bcimbal.assignment14;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Packer {
    /**
     *
     * @param
     */
    Packer(String fileIn, String fileOut) {

        String outString = "";
        try {
            outString = new String(Files.readAllBytes(Paths.get(fileIn)));
        } catch (Exception ignored) {

        }

        HashMap<Character, Integer> charTableAsMap = getCharTable(fileIn);
        byte [] charTableAsArray = convertToArray(charTableAsMap);

        byte[] packed = pack(outString.getBytes(), charTableAsMap);
        byte[] fileInfo = getFileInfo(charTableAsArray.length, outString.length());
        writeFile(fileOut, fileInfo, charTableAsArray, packed);
    }
    /**
     *
     * @param fileInfo
     * @param tableArray
     * @param packed
     */
    private static void writeFile(String file, byte[] fileInfo, byte[] tableArray, byte[] packed) {
        try {
            FileOutputStream f1 = new FileOutputStream(file);
            f1.write(fileInfo);
            f1.write(tableArray);
            f1.write(packed);
            f1.close();

        } catch (IOException е) {
            System.out.println("UPS");
        }
    }

    /*******************************************************************************************************************

     */
    private static byte[] getFileInfo ( int tableSize, int packedSize){
        byte[] out = new byte[12];

        for (int i = 0; i < 4; i++) {
            out[i] = getByte(i, tableSize);
        }

        for (int i = 0; i < 8; i++) {
            out[4 + i] = getByte(i, packedSize);
        }
        return out;
    }

    /*******************************************************************************************************************
     *
     * @param number
     * @param value
     * @return
     */
    private static byte getByte ( int number, long value){
        return (byte) ((value >> 8 * number) & 0xff);
    }

    /**
     *
     * @param fileName
     * @return
     */

    private static HashMap<Character, Integer> getCharTable(String fileName) {
        int totalUnicumChars = 0;
        HashMap<Character, Integer> tmp = new HashMap<>();
        try {
            FileInputStream stream = new FileInputStream(fileName);
            int bytesToRead = Helper.IO_BUFFER_SIZE;
            while(bytesToRead > 0) {
                byte[] buffer = new byte[bytesToRead];
                stream.read(buffer);
                for(byte ch: buffer) {
                    if (!tmp.containsKey((char)ch)) {
                        tmp.put((char)ch, totalUnicumChars);
                        totalUnicumChars++;
                    }
                }
                bytesToRead = stream.available();
                if(/*bytesToRead > 0 && */bytesToRead > Helper.IO_BUFFER_SIZE) bytesToRead = Helper.IO_BUFFER_SIZE;
            }
        } catch (IOException e) {

        }
        return tmp;
    }

    /******************************************************************************************************************/
    private static byte[] convertToArray(HashMap<Character, Integer> charTable) {
        byte[] out = new byte[charTable.size()];
        for (Character ch : charTable.keySet()) {
            out[charTable.get(ch)] = (byte) (ch & 0xff);
        }
        return out;
    }



    private static byte[] pack ( byte[] input, HashMap<Character, Integer > charTable){
        int bitsCount = Helper.getBitsCount(charTable.size());
        ArrayList<Byte> out = new ArrayList<>();
        LinkedList<Boolean> byteSeparator = new LinkedList<>();
        for (byte currentByte : input) {
            byte a = (byte) (charTable.get((char) currentByte) & 0xff);
            for (int n = 0; n < bitsCount; n++) {
                if ((a & (1 << n)) != 0) {
                    byteSeparator.add(true);
                } else {
                    byteSeparator.add(false);
                }
                //-------------------------------------------------------
                if (byteSeparator.size() >= 8) {
                    byte cByte = 0;
                    for (int k = 0; k < 8; k++) {
                        if (byteSeparator.getFirst()) {
                            cByte = (byte) (cByte | (1 << k));
                        }
                        byteSeparator.removeFirst();
                    }
                    out.add(cByte);
                }
            }
        }
        /* not full byte  */
        byte cByte = 0;
        int k = 0;
        while (byteSeparator.size() > 0) {
            if (byteSeparator.getFirst()) {
                cByte = (byte) (cByte | (1 << k));
            }
            k++;
            byteSeparator.removeFirst();
        }
        out.add(cByte);


        byte[] bOut = new byte[out.size()];
        for (int i = 0; i < bOut.length; i++) {
            bOut[i] = out.get(i);
        }
        System.out.println("Before packing: " + input.length + ". After: " + bOut.length);
        return bOut;
    }
}
