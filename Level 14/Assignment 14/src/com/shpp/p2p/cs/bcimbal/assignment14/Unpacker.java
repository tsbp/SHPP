package com.shpp.p2p.cs.bcimbal.assignment14;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class Unpacker {

    private byte[] charTable;
    private byte[] data;

    Unpacker(String fileIn, String fileOut) {
        try {
            FileInputStream stream = new FileInputStream(fileIn);
            byte[] bufferInfo = new byte[12];
            stream.read(bufferInfo);

            int charTableSize = getCharTableSize(bufferInfo);
            charTable = new byte[charTableSize];
            stream.read(charTable);

            data = unpack(fileOut, stream, charTable).getBytes();

        } catch (IOException e) {

        }
    }

    private  String unpack (String file, FileInputStream stream, byte[] charTable){
        int bitsCount = Helper.getBitsCount(charTable.length);
        StringBuilder out = new StringBuilder("");
        //----------------------------------------------------------------------------------------
        int totalBytes = Helper.IO_BUFFER_SIZE;
        try {
            int bytesToRead = stream.available();
            if (bytesToRead > Helper.IO_BUFFER_SIZE) bytesToRead = Helper.IO_BUFFER_SIZE;

            LinkedList<Boolean> charSeparator = new LinkedList<>();
            while (bytesToRead > 0) {
                byte[] buffer = new byte[bytesToRead];
                stream.read(buffer);
                collectData(charSeparator, buffer, charTable, bitsCount, out);
                bytesToRead = stream.available();
                if (bytesToRead > Helper.IO_BUFFER_SIZE) bytesToRead = Helper.IO_BUFFER_SIZE;
                totalBytes += bytesToRead;
            }
            stream.close();
        }
        catch (IOException e) {

        }

        System.out.println("Before unpacking: " + totalBytes + ". After: " + out.length());
        return out.toString();
    }

    private void collectData(LinkedList<Boolean> charSeparator, byte[] buffer, byte[] charTable, int bitsCount, StringBuilder out) {

            for (byte b : buffer) {
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
    }

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
