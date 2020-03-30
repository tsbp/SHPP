package com.shpp.p2p.cs.bcimbal.assignment14;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class Unpacker {

    private byte[] charTable;
    private byte[] data;

    Unpacker(String fileiIn, String fileOut) {
        try {
            FileInputStream stream = new FileInputStream(fileiIn);
            byte[] bufferInfo = new byte[12];
            stream.read(bufferInfo);

            int charTableSize = getCharTableSize(bufferInfo);
            charTable = new byte[charTableSize];
            stream.read(charTable);

            data = new byte[stream.available()];
            stream.read(data);
            //unpack(fileOut, stream, charTable);

        } catch (IOException e) {

        }
    }

//    private static String unpack (String file, FileInputStream stream, /*byte[] input,*/ byte[] charTable){
//        int bitsCount = Helper.getBitsCount(charTable.length);
//        StringBuilder out = new StringBuilder("");
//        //----------------------------------------------------------------------------------------
//        try {
//            int bytesToRead = Helper.IO_BUFFER_SIZE;
//            while (bytesToRead > 0) {
//                byte[] buffer = new byte[bytesToRead];
//                stream.read(buffer);
//                for (byte ch : buffer) {
////                if (!tmp.containsKey((char)ch)) {
////                    tmp.put((char)ch, totalUnicumChars);
////                    totalUnicumChars++;
////                }
//                }
//                bytesToRead = stream.available();
//                if (bytesToRead > 0 && bytesToRead < Helper.IO_BUFFER_SIZE) bytesToRead = Helper.IO_BUFFER_SIZE;
//            }
//        }
//        catch (IOException e) {
//
//        }
//        //----------------------------------------------------------------------------------------
//        LinkedList<Boolean> charSeparator = new LinkedList<>();
//        for (byte b : input) {
//            for (int n = 0; n < 8; n++) {
//                if ((b & (1 << n)) != 0) {
//                    charSeparator.add(true);
//                } else {
//                    charSeparator.add(false);
//                }
//                //-------------------------------------------------------
//                if (charSeparator.size() >= bitsCount) {
//                    int code = 0;
//                    for (int k = 0; k < bitsCount; k++) {
//                        if (charSeparator.getFirst()) {
//                            code = (char) (code | (1 << k));
//                        }
//                        charSeparator.removeFirst();
//                    }
//                    out.append((char) charTable[code]);
//                }
//            }
//        }
//        System.out.println("Before unpacking: " + input.length + ". After: " + out.length());
//        return out.toString();
//    }

    public byte [] getCharTable() {
        return charTable;
    }

    public byte [] getData() {
        return data;
    }

    private int getCharTableSize(byte[] buffer) {
        int out = 0;
        for (int i = 0; i < 4; i++) {
            out = out | (buffer[i] << (i * 8));
        }
        return out;
    }


}
