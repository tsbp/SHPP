package com.shpp.p2p.cs.bcimbal.assignment14;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class Helper {

    public static final String DIR = "assets/";

    public static final String EXTENSION_ARCH = ".par";
    public static final String EXTENSION_UNARCH = ".uar";

    public static final int IO_BUFFER_SIZE = 3333;
    public static final int HEADER_TABLE_SIZE_IN_BYTES = 4;
    public static final int HEADER_DATA_SIZE_IN_BYTES = 8;

    public static int getBitsCount ( int x){
        double res = (Math.log(x) / Math.log(2));
        double a = Math.pow(2, res);
        if(a == x) return (int)res;
        return (int) (res) + 1;
    }

    public static void writeBufferToFile(FileOutputStream stream, LinkedList<Byte> writeBuffer, int ioBufferSize) {
        byte[] bOut = new byte[ioBufferSize];
        try {
            for (int i = 0; i < bOut.length; i++) {

                bOut[i] = writeBuffer.getFirst();
                writeBuffer.removeFirst();
            }
            stream.write(bOut);
        } catch (IOException e) {
            System.out.println("UPS");
            e.printStackTrace();
        }
    }

    public static int getFileInfo(String info, String fileName) {
        int size = 0;
        try {
            FileInputStream stream = new FileInputStream(fileName);
            size = stream.available();
            System.out.println(info + size);
            stream.close();
        }
        catch (IOException e) {
            System.out.println("UPS");
            e.printStackTrace();
        }
        return size;
    }
}
