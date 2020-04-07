package com.shpp.p2p.cs.bcimbal.assignment14;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

/*******************************************************************************************************************
 * Class makes archiving of given input file to output file
 * with showing result of process
 */
public class Packer {

    private int totalBytes = 0;

    /*******************************************************************************************************************
     * Constructor makes archiving of given input file to output file
     * with showing result of process
     *
     * @param fileIn input file name
     * @param fileOut output file name
     */
    Packer(String fileIn, String fileOut) {

        double efficiency = Helper.getFileInfo("Input file size in bytes:", fileIn);
        long sysTime = System.currentTimeMillis();

        HashMap<Character, Integer> charTableAsMap = getCharTable(fileIn);
        packToFile(fileOut, fileIn, charTableAsMap);

        sysTime = System.currentTimeMillis() - sysTime;

        System.out.println("Efficiency: " + efficiency /
                (double)Helper.getFileInfo("Packing time: " + sysTime +
                "ms.\r\nOutput file size in bytes:", fileOut));
    }

    /*******************************************************************************************************************
     * Packaging data
     *
     * @param fileOut output file name
     * @param fileIn input file name
     * @param charTableAsMap char table map
     */
    private void packToFile(String fileOut, String fileIn, HashMap<Character, Integer> charTableAsMap) {
        int bitsCount = Helper.getBitsCount(convertToArray(charTableAsMap).length);
        try {

            FileInputStream streamRead = new FileInputStream(fileIn);
            FileOutputStream streamWrite = new FileOutputStream(fileOut);
            byte[] charTableAsArray = convertToArray(charTableAsMap);
            streamWrite.write(getFileInfo(charTableAsArray.length, totalBytes));
            streamWrite.write(charTableAsArray);

            //--------------------------------------------------------
            int bytesToRead = streamRead.available();
            if (bytesToRead > Helper.IO_BUFFER_SIZE) bytesToRead = Helper.IO_BUFFER_SIZE;

            LinkedList<Byte> writeBuffer = new LinkedList<>();
            LinkedList<Boolean> byteSeparator = new LinkedList<>();
            while (bytesToRead > 0) {
                byte[] readBuffer = new byte[bytesToRead];
                streamRead.read(readBuffer);
                collectData(byteSeparator, readBuffer, charTableAsMap, bitsCount, writeBuffer);
                if (writeBuffer.size() >= Helper.IO_BUFFER_SIZE) {
                    totalBytes -= bytesToRead;
                    System.out.print("Bytes left for processing: " + totalBytes + "\r");
                    Helper.writeBufferToFile(streamWrite, writeBuffer, Helper.IO_BUFFER_SIZE);

                }
                bytesToRead = streamRead.available();
                if (bytesToRead > Helper.IO_BUFFER_SIZE) bytesToRead = Helper.IO_BUFFER_SIZE;
            }
            streamRead.close();
            /* remains bits */
            if (byteSeparator.size() > 0) {
                byte[] tmp = {charTableAsArray[0]};
                collectData(byteSeparator, tmp, charTableAsMap, bitsCount, writeBuffer);
            }
            if (writeBuffer.size() > 0) Helper.writeBufferToFile(streamWrite, writeBuffer, writeBuffer.size());
            streamWrite.close();


        } catch (IOException e) {
            System.out.println("UPS");
            e.printStackTrace();
        }
    }

    /*******************************************************************************************************************
     * Collect data to write buffer
     *
     * @param byteSeparator list of bits to be decoded
     * @param buffer input data buffer
     * @param charTable char table map
     * @param bitsCount bits count to codding chars
     * @param writeBuffer data buffer
     */
    private void collectData(LinkedList<Boolean> byteSeparator, byte[] buffer,
                             HashMap<Character, Integer> charTable, int bitsCount,
                             LinkedList<Byte> writeBuffer) {
        for (byte currentByte : buffer) {
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
                    writeBuffer.add(cByte);
                }
            }
        }
    }

    /*******************************************************************************************************************
     * Prepare file header
     *
     * @param tableSize  char table size
     * @param packedSize data size
     * @return packed file header byte array
     */
    private static byte[] getFileInfo(int tableSize, int packedSize) {
        byte[] out = new byte[12];

        for (int i = 0; i < Helper.HEADER_TABLE_SIZE_IN_BYTES; i++) {
            out[i] = getByte(i, tableSize);
        }

        for (int i = 0; i < Helper.HEADER_DATA_SIZE_IN_BYTES; i++) {
            out[4 + i] = getByte(i, packedSize);
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
     * Read file partially to define char table
     * @param fileName file name
     * @return char table map
     */
    private HashMap<Character, Integer> getCharTable(String fileName) {
        int totalUnicumChars = 0;
        HashMap<Character, Integer> tmp = new HashMap<>();
        try {
            FileInputStream stream = new FileInputStream(fileName);
            int bytesToRead = stream.available();
            if (bytesToRead > Helper.IO_BUFFER_SIZE) bytesToRead = Helper.IO_BUFFER_SIZE;
            totalBytes += bytesToRead;
            while (bytesToRead > 0) {
                byte[] buffer = new byte[bytesToRead];
                stream.read(buffer);
                for (byte ch : buffer) {
                    if (!tmp.containsKey((char) ch)) {
                        tmp.put((char) ch, totalUnicumChars);
                        totalUnicumChars++;
                    }
                }
                bytesToRead = stream.available();
                if (bytesToRead > Helper.IO_BUFFER_SIZE) bytesToRead = Helper.IO_BUFFER_SIZE;
                totalBytes += bytesToRead;
            }
        } catch (IOException e) {
            System.out.println("UPS");
            e.printStackTrace();
        }
        return tmp;
    }

    /*******************************************************************************************************************
     * Convert char table map to char table array
     *
     * @param charTable char table map
     * @return byte char table array
     */
    private static byte[] convertToArray(HashMap<Character, Integer> charTable) {
        byte[] out = new byte[charTable.size()];
        for (Character ch : charTable.keySet()) {
            out[charTable.get(ch)] = (byte) (ch & 0xff);
        }
        return out;
    }


}
