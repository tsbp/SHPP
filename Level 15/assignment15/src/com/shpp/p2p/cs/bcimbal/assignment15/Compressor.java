package com.shpp.p2p.cs.bcimbal.assignment15;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Compressor extends CHelper {
    Compressor() {

    }

    /**
     * @param streamIn
     * @return
     */

    public /*LinkedList<Byte>*/void compress(
            BufferedInputStream streamIn,
            BufferedOutputStream streamOut,
            SymbolCode[] codes) throws IOException {
        /*LinkedList<Byte> writeBuffer = new LinkedList<>();*/
        byte[] inputBuffer = new byte[IO_BUFFER_SIZE];
        byte[] outputBuffer = new byte[IO_BUFFER_SIZE];
        int inBytesCount;
        int outBytesCount = 0;
        LinkedList<Boolean> byteSeparator = new LinkedList<>();
        while ((inBytesCount = streamIn.read(inputBuffer, 0, IO_BUFFER_SIZE)) > 0) {
            for (int currentByte = 0; currentByte < inBytesCount; currentByte++) {
                int index = inputBuffer[currentByte] & 0xff;
                int bitsCount = codes[index].getBitsCount();
                if(bitsCount > 8)
                    System.out.print("");
                int currentCode = codes[index].getCode();

                for (int n = 0; n < bitsCount; n++) {
                    if ((currentCode & (1 << n)) != 0) {
                        byteSeparator.add(true);
                    } else {
                        byteSeparator.add(false);
                    }
                    //-------------------------------------------------------
                    if (byteSeparator.size() >= 8) {
                        /*writeBuffer.add(getByte(byteSeparator, 8));*/
                        outputBuffer[outBytesCount++] = getByte(byteSeparator, 8);//writeBuffer.getLast();
                        /* out buffer filled, send*/
                        if(outBytesCount >= IO_BUFFER_SIZE) {
                            streamOut.write(outputBuffer);
                            outBytesCount = 0;
                        }
                    }
                }
            }
        }
        /* remains bits */
        if (byteSeparator.size() > 0) {
            //writeBuffer.add(getByte(byteSeparator, byteSeparator.size()));
            outputBuffer[outBytesCount++] = getByte(byteSeparator, byteSeparator.size());//writeBuffer.getLast();
            streamOut.write(outputBuffer);
        }
        //return writeBuffer;
    }

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

    /**
     * @param dataLength
     * @param tree
     * @return
     */
    public void decompress(
            BufferedInputStream streamIn,
            BufferedOutputStream streamOut,
            long dataLength, CTree tree)throws IOException {

        byte[] inputBuffer = new byte[IO_BUFFER_SIZE];
        byte[] outputBuffer = new byte[IO_BUFFER_SIZE];
        int inBytesCount;
        int outBytesCount = 0;
        int bytesDecompressed = 0;
        CNode curNode = tree.getRoot();
        while ((inBytesCount = streamIn.read(inputBuffer, 0, IO_BUFFER_SIZE)) > 0) {
            for(int currentByte = 0; currentByte < inBytesCount; currentByte++)  {
                byte bTmp = inputBuffer[currentByte];;
                for (int i = 0; i < 8; i++) {
                    if (bytesDecompressed >= dataLength) break;
                    CNode[] leafs = curNode.getLeafs();

                    if (leafs[0] == null && leafs[1] == null) {
                        outputBuffer[outBytesCount++] = curNode.getByteValue();
                        bytesDecompressed++;
                        /* out buffer filled, send*/
                        if(outBytesCount >= IO_BUFFER_SIZE) {
                            streamOut.write(outputBuffer);
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
