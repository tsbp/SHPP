package com.shpp.p2p.cs.bcimbal.assignment14;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/*******************************************************************************************************************
 * The class makes archiveing
 */

public class Assignment14Part1 {
    public static void main(String args[]) {
//        String outString = "";
//        String fileName = "assets/test.txt";
//        try {
//            outString = new String(Files.readAllBytes(Paths.get(fileName)));
//        } catch (Exception ignored) {
//
//        }
//
//        CompressorTable charTableObject = new CompressorTable(fileName);
//
//        byte[] packed = pack(outString.getBytes(), charTableObject.getTableMap());
//        byte[] fileInfo = getFileInfo(charTableObject.getTableArray().length, outString.length());
//        writeFile(fileInfo, charTableObject.getTableArray(), packed);

        Unpacker unp = new Unpacker("assets/arch.par");
        String unpacked = unpack(unp.getData(), unp.getCharTable());
        System.out.println(unpacked);
    }


    /**
     *
     * @param fileInfo
     * @param tableArray
     * @param packed
     */
    private static void writeFile(byte[] fileInfo, byte[] tableArray, byte[] packed) {
        try {
            FileOutputStream f1 = new FileOutputStream("assets/arch.par ");
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

        /******************************************************************************************************************/
        private static String unpack ( byte[] input, byte[] charTable){
            int bitsCount = getBitsCount(charTable.length);
            StringBuilder out = new StringBuilder("");

            LinkedList<Boolean> charSeparator = new LinkedList<>();
            for (byte b : input) {
                for (int n = 0; n < 8; n++) {
                    if ((b & (1 << n)) != 0) {
                        charSeparator.add(true);
                    } else {
                        charSeparator.add(false);
                    }
                    //-------------------------------------------------------
                    if (charSeparator.size() >= bitsCount) {
                        int code = 0;
                        for (int k = 0; k < bitsCount; k++) {
                            if (charSeparator.getFirst()) {
                                code = (char) (code | (1 << k));
                            }
                            charSeparator.removeFirst();
                        }
                        out.append((char) charTable[code]);
                    }
                }
            }
            System.out.println("Before unpacking: " + input.length + ". After: " + out.length());
            return out.toString();
        }

        /******************************************************************************************************************/
        private static byte[] pack ( byte[] input, HashMap<Character, Integer > charTable){
            int bitsCount = getBitsCount(charTable.size());
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


        /****************************************************************************************************************/
        private static int getBitsCount ( int x){
            return (int) (Math.log(x) / Math.log(2)) + 1;
        }


    }
