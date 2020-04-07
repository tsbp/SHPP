package com.shpp.p2p.cs.bcimbal.assignment15;

import java.io.*;
import java.util.LinkedList;

/* Class makes file compression/decompression by Huffman codding */
public class Compressor extends CHelper {

    /*******************************************************************************************************************
     * Constructor performs compressing/decompressing input file to output file
     * depending to mode
     *
     * @param fileIn input file
     * @param fileOut output file
     * @param mode compressor mode (compressing/decompressing)
     */
    Compressor(String fileIn, String fileOut, int mode) {

        double efficiency = getFileInfo("Input file (" + fileIn + ") size in bytes: ", fileIn);
        long sysTime = System.currentTimeMillis();

        perform(fileIn, fileOut, mode);

        switch (mode) {

            case COMPRESSOR_MODE_COMPRESS:
                System.out.println("Efficiency: " + efficiency /
                        (double) getFileInfo("time: " + (System.currentTimeMillis() - sysTime) +
                                " ms.\r\nOutput file (" + fileOut + ") size in bytes: ", fileOut));
                break;

            case COMPRESSOR_MODE_DECOMPRESS:
                System.out.println("Efficiency: " + 1 / (efficiency /
                        (double) getFileInfo("time: " + (System.currentTimeMillis() - sysTime) +
                                " ms.\r\nOutput file (" + fileOut + ") size in bytes: ", fileOut)));
                break;
        }

    }

    /*******************************************************************************************************************
     * Method performs compressing/decompressing input file to output file
     * depending to mode
     *
     * @param fileIn input file
     * @param fileOut output file
     * @param mode compressor mode (compressing/decompressing)
     */
    private void perform(String fileIn, String fileOut, int mode) {
        switch (mode) {

            case COMPRESSOR_MODE_COMPRESS:

                /* get tree and symbol codes*/
                FreqList symbolFreqs = new FreqList(fileIn);
                CTree tree = new CTree(symbolFreqs.getFreqList());
                SymbolCode[] codes = tree.getCodes();
                /* compress and write */
                try (FileInputStream fStreamIn = new FileInputStream(fileIn);
                     BufferedInputStream inputStream = new BufferedInputStream(fStreamIn, IO_BUFFER_SIZE);
                     FileOutputStream fStreamOut = new FileOutputStream(fileOut);
                     BufferedOutputStream outputStream = new BufferedOutputStream(fStreamOut, IO_BUFFER_SIZE)) {
                    /* write file header */
                    byte[] symFreqs = symbolFreqs.getFreqListAsByteArray();
                    byte[] fHeader = getFileInfo(symFreqs.length, tree.getRoot().getFreq());
                    outputStream.write(fHeader, 0, fHeader.length);
                    outputStream.write(symFreqs, 0, symFreqs.length);
                    /* write compressed data */
                    compress(inputStream, outputStream, codes);

                } catch (IOException e) {
                    System.out.println("UPS");
                    e.printStackTrace();
                }
                System.out.print("Packing ");
                break;


            case COMPRESSOR_MODE_DECOMPRESS:

                try (FileInputStream fStreamIn = new FileInputStream(fileIn);
                     BufferedInputStream inputStream = new BufferedInputStream(fStreamIn, IO_BUFFER_SIZE);
                     FileOutputStream fStreamOut = new FileOutputStream(fileOut);
                     BufferedOutputStream outputStream = new BufferedOutputStream(fStreamOut, IO_BUFFER_SIZE)) {
                    /* read header */
                    byte[] header = new byte[HEADER_TOLTAL_SIZE_IN_BYTES];
                    inputStream.read(header, 0, HEADER_TOLTAL_SIZE_IN_BYTES);
                    int symFreqsSize = getValue(header, HEADER_TABLE_SIZE_IN_BYTES, 0);
                    int dataSize = getValue(header, HEADER_DATA_SIZE_IN_BYTES, HEADER_TABLE_SIZE_IN_BYTES);
                    /* read symols frequencies */
                    byte[] symFreqs = new byte[symFreqsSize];
                    inputStream.read(symFreqs, 0, symFreqsSize);
                    /* restore tree */
                    FreqList restored = new FreqList(symFreqs);
                    CTree treeRestored = new CTree(restored.getFreqList());
                    /* write decompressed data */
                    decompress(inputStream, outputStream, dataSize, treeRestored);

                } catch (IOException e) {
                    System.out.println("UPS");
                    e.printStackTrace();
                }
                System.out.print("Unpacking ");
                break;
        }
    }


    /*******************************************************************************************************************
     * Method to compress from input to output stream
     *
     * @param streamIn BufferedInputStream input stream
     * @param streamOut BufferedOutputStream output stream
     * @param codes array of symbol codes
     * @throws IOException a
     */
    public void compress(BufferedInputStream streamIn,
                         BufferedOutputStream streamOut,
                         SymbolCode[] codes) throws IOException {

        byte[] inputBuffer = new byte[IO_BUFFER_SIZE];
        byte[] outputBuffer = new byte[IO_BUFFER_SIZE];
        int bytesProceeded = 0;
        int inBytesCount;
        int outBytesCount = 0;
        LinkedList<Boolean> byteSeparator = new LinkedList<>();
        while ((inBytesCount = streamIn.read(inputBuffer, 0, IO_BUFFER_SIZE)) > 0) {
            for (int currentByte = 0; currentByte < inBytesCount; currentByte++) {
                bytesProceeded++;
                int index = inputBuffer[currentByte] & 0xff;
                int bitsCount = codes[index].getBitsCount();
                if (bitsCount > 8)
                    System.out.print("");
                int currentCode = codes[index].getCode();

                for (int n = 0; n < bitsCount; n++) {
                    if ((currentCode & (1 << n)) != 0) {
                        byteSeparator.add(true);
                    } else {
                        byteSeparator.add(false);
                    }
                    /* send byte to out buffer */
                    if (byteSeparator.size() >= 8) {
                        outputBuffer[outBytesCount++] = getByte(byteSeparator, 8);
                        /* out buffer filled, send*/
                        if (outBytesCount >= IO_BUFFER_SIZE) {
                            System.out.print("Bytes proceeded: " + bytesProceeded + "\r");
                            streamOut.write(outputBuffer);
                            outBytesCount = 0;
                        }
                    }
                }
            }
        }
        /* remains bits */
        if (byteSeparator.size() > 0) {
            outputBuffer[outBytesCount++] = getByte(byteSeparator, byteSeparator.size());
            streamOut.write(outputBuffer, 0, outBytesCount);
        }
    }

    /*******************************************************************************************************************
     * Restore  full byte from bits list
     *
     * @param byteSeparator bits list
     * @param bitsCount bits number in symbol code
     * @return restored byte
     */
    private static byte getByte(LinkedList<Boolean> byteSeparator, int bitsCount) {
        byte cByte = 0;
        for (int k = 0; k < bitsCount; k++) {
            if (byteSeparator.getFirst()) {
                cByte = (byte) (cByte | (1 << k));
            }
            byteSeparator.removeFirst();
        }
        return cByte;
    }

    /*******************************************************************************************************************
     * Method to decompress from input to output stream
     *
     * @param streamIn input stream
     * @param streamOut output stream
     * @param dataLength decompressed data size
     * @param tree decompression tree
     * @throws IOException
     */
    public void decompress(BufferedInputStream streamIn,
                           BufferedOutputStream streamOut,
                           long dataLength, CTree tree) throws IOException {

        byte[] inputBuffer = new byte[IO_BUFFER_SIZE];
        byte[] outputBuffer = new byte[IO_BUFFER_SIZE];
        int bytesProceeded = 0;
        int inBytesCount;
        int outBytesCount = 0;
        int bytesDecompressed = 0;
        CNode curNode = tree.getRoot();
        while ((inBytesCount = streamIn.read(inputBuffer, 0, IO_BUFFER_SIZE)) > 0) {
            for (int currentByte = 0; currentByte < inBytesCount; currentByte++) {
                byte bTmp = inputBuffer[currentByte];
                bytesProceeded++;
                for (int i = 0; i < 8; i++) {
                    if (bytesDecompressed >= dataLength) break;
                    CNode[] leafs = curNode.getLeafs();

                    if (leafs[0] == null && leafs[1] == null) {
                        outputBuffer[outBytesCount++] = curNode.getByteValue();
                        bytesDecompressed++;
                        /* out buffer filled, send*/
                        if (outBytesCount >= IO_BUFFER_SIZE) {
                            streamOut.write(outputBuffer);
                            System.out.print("Bytes proceeded: " + bytesProceeded + "\r");
                            outBytesCount = 0;
                        }
                        curNode = tree.getRoot();
                        i--;
                    } else {
                        if ((bTmp & 1) > 0) {
                            curNode = leafs[1];
                        } else {
                            curNode = leafs[0];
                        }
                        bTmp >>= 1;
                    }
                }
            }
        }
        streamOut.write(outputBuffer, 0, outBytesCount);
    }
}
