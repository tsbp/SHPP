package com.shpp.p2p.cs.bcimbal.assignment14;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

/*******************************************************************************************************************
 * Constructor makes unzipping of given input file to output file
 * with showing result of process
 */
public class Unpacker {
    /*******************************************************************************************************************
     * Constructor makes unzipping of given input file to output file
     * with showing result of process
     *
     * @param fileIn input file name
     * @param fileOut output file name
     */
    Unpacker(String fileIn, String fileOut) {
        try {
            Helper.getFileInfo("Input file size in bytes:", fileIn);
            long sysTime = System.currentTimeMillis();

            FileInputStream streamRead = new FileInputStream(fileIn);
            byte[] bufferInfo = new byte[12];
            streamRead.read(bufferInfo);

            int charTableSize = getCharTableSize(bufferInfo);
            byte[] charTable = new byte[charTableSize];
            streamRead.read(charTable);

            unpack(fileOut, streamRead, charTable);

            sysTime = System.currentTimeMillis() - sysTime;
            Helper.getFileInfo("Unpacking time: " + sysTime +
                    "ms.\r\nOutput file size in bytes:", fileOut);

        } catch (IOException e) {

        }
    }

    /*******************************************************************************************************************
     * Unpacking file
     *
     * @param file output file name to sore dat
     * @param streamRead current stream of reading
     * @param charTable char table array to decode chars
     */
    private  void unpack (String file, FileInputStream streamRead, byte[] charTable){
        int bitsCount = Helper.getBitsCount(charTable.length);
        //----------------------------------------------------------------------------------------
        int totalBytes = Helper.IO_BUFFER_SIZE;
        try {
            FileOutputStream streamWrite = new FileOutputStream(file);
            LinkedList<Byte> writeBuffer = new LinkedList<>();

            int bytesToRead = streamRead.available();
            if (bytesToRead > Helper.IO_BUFFER_SIZE) bytesToRead = Helper.IO_BUFFER_SIZE;

            LinkedList<Boolean> charSeparator = new LinkedList<>();
            while (bytesToRead > 0) {
                byte[] readBuffer = new byte[bytesToRead];
                streamRead.read(readBuffer);
                collectData(charSeparator, readBuffer, charTable, bitsCount, writeBuffer);
                if (writeBuffer.size() >= Helper.IO_BUFFER_SIZE) {
                    Helper.writeBufferToFile(streamWrite, writeBuffer, Helper.IO_BUFFER_SIZE);
                }
                bytesToRead = streamRead.available();
                if (bytesToRead > Helper.IO_BUFFER_SIZE) {
                    bytesToRead = Helper.IO_BUFFER_SIZE;
                }
                totalBytes += bytesToRead;
            }
            streamRead.close();
            if (writeBuffer.size() > 0) {
                Helper.writeBufferToFile(streamWrite, writeBuffer, writeBuffer.size());
            }
            streamWrite.close();
        }
        catch (Exception e) {
            System.out.println("UPS");
        }
    }

     /*******************************************************************************************************************
     * Collect data to write buffer
     *
     * @param charSeparator list of bits to be decoded to char
     * @param buffer input data buffer
     * @param charTable char table array
     * @param bitsCount bits count to codding chars
     * @param writeBuffer buffer of data to be written
     */
    private void collectData(LinkedList<Boolean> charSeparator, byte[] buffer, byte[] charTable, int bitsCount,
                             LinkedList<Byte> writeBuffer) {

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
                        writeBuffer.add(charTable[code]);
                    }
                }
            }
    }

    /*******************************************************************************************************************
     * Get char table size from input buffer
     * @param buffer input byte buffer
     * @return int char table size
     */
    private int getCharTableSize(byte[] buffer) {
        int out = 0;
        for (int i = 0; i < Helper.HEADER_TABLE_SIZE_IN_BYTES; i++) {
            out = out | (buffer[i] << (i * 8));
        }
        return out;
    }


}
