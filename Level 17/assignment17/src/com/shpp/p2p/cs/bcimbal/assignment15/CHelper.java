package com.shpp.p2p.cs.bcimbal.assignment15;

import java.io.FileInputStream;
import java.io.IOException;

/* Main function helper class */

public class CHelper {

    /* default file directory */
    public static final String DIR = "assets/";
    /* Files extensions */
    public static final String EXTENSION_ARCH = ".par";    // zipped
    public static final String EXTENSION_UNARCH = ".uar";  // unzipped

    /* Buffer size for partial read/write */
    public static final int IO_BUFFER_SIZE = 4096;
    /* Bytes number char table size occupies */
    public static final int HEADER_TABLE_SIZE_IN_BYTES = 4;
    /* Bytes number unzipped data size occupies */
    public static final int HEADER_DATA_SIZE_IN_BYTES = 8;

    /* file header size*/
    public static final int HEADER_TOLTAL_SIZE_IN_BYTES =
            HEADER_TABLE_SIZE_IN_BYTES + HEADER_DATA_SIZE_IN_BYTES;

    /* Compressor mode to compress*/
    public static final int COMPRESSOR_MODE_COMPRESS = 1;
    /* Compressor mode to decompress*/
    public static final int COMPRESSOR_MODE_DECOMPRESS = 2;


    /*******************************************************************************************************************
     * Get file information by given parameters
     * @param info String info to show
     * @param fileName file name
     * @return int file size
     */
    public int getFileInfo(String info, String fileName) {
        int size = 0;
        try (FileInputStream stream = new FileInputStream(fileName)) {
            size = stream.available();
            System.out.println(info + size);
        } catch (IOException e) {
            System.out.println("UPS");
            e.printStackTrace();
        }
        return size;
    }

    /*******************************************************************************************************************
     * Prepare file header
     *
     * @param tableSize  char table size
     * @param dataSize data size
     * @return packed file header byte array
     */
    public byte[] getFileInfo(int tableSize, int dataSize) {
        byte[] out = new byte[12];

        for (int i = 0; i < HEADER_TABLE_SIZE_IN_BYTES; i++) {
            out[i] = getByte(i, tableSize);
        }

        for (int i = 0; i < HEADER_DATA_SIZE_IN_BYTES; i++) {
            out[4 + i] = getByte(i, dataSize);
        }
        return out;
    }

    /*******************************************************************************************************************
     * Get certain byte from long value
     *
     * @param number byte number
     * @param value long value
     * @return byte value
     */
    private static byte getByte(int number, long value) {
        return (byte) ((value >> 8 * number) & 0xff);
    }

    /*******************************************************************************************************************
     * Get integer(long) value in given byte buffer
     * @param buffer current buffer
     * @param bytesCount number of bytes value consist of
     * @param offset offset in buffer
     * @return integer (long) value
     */
    public int getValue(byte[] buffer, int bytesCount, int offset) {
        int out = 0;
        for (int i = offset; i < bytesCount; i++) {
            out |= ((buffer[i] & 0xff) << (i - offset) * 8);
        }
        return out;
    }
}
