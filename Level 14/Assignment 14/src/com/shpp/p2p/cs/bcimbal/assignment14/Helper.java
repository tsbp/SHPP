package com.shpp.p2p.cs.bcimbal.assignment14;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

/* Class assists  main classes of archiving/unarchiving*/
public class Helper {
    /* default file directory */
    public static final String DIR = "assets/";
    /* Files extensions */
    public static final String EXTENSION_ARCH = ".par";    // zipped
    public static final String EXTENSION_UNARCH = ".uar";  // unzipped

    /* Buffer size for partial read/write */
    public static final int IO_BUFFER_SIZE = 100;
    /* Bytes number char table size occupies */
    public static final int HEADER_TABLE_SIZE_IN_BYTES = 4;
    /* Bytes number unzipped data size occupies */
    public static final int HEADER_DATA_SIZE_IN_BYTES = 8;

    /*******************************************************************************************************************
     * Get bits count to code "number"
     *
     * @param number input number
     * @return int bits count
     */
    public static int getBitsCount ( int number){
        double res = (Math.log(number) / Math.log(2));
        if(Math.pow(2, res) == number) return (int)res;
        return (int) (res) + 1;
    }

    /*******************************************************************************************************************
     * Write bufeered data in given stream
     *
     * @param stream current write stream
     * @param writeBuffer data buffer
     * @param ioBufferSize data buffer size
     */
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

    /*******************************************************************************************************************
     * Get file information by given parameters
     * @param info String info to show
     * @param fileName file name
     * @return int file size
     */
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
