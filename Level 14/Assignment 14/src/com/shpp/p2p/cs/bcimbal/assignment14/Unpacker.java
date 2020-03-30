package com.shpp.p2p.cs.bcimbal.assignment14;

import java.io.FileInputStream;
import java.io.IOException;

public class Unpacker {

    private byte[] charTable;
    private byte[] data;

    Unpacker(String fileName) {
        try {
            FileInputStream stream = new FileInputStream(fileName);
            byte[] bufferInfo = new byte[12];
            stream.read(bufferInfo);

            int charTableSize = getCharTableSize(bufferInfo);
            charTable = new byte[charTableSize];
            stream.read(charTable);

            data = new byte[stream.available()];
            stream.read(data);


        } catch (IOException e) {

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
