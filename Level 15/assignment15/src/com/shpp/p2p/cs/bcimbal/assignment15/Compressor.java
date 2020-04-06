package com.shpp.p2p.cs.bcimbal.assignment15;

import java.util.ArrayList;
import java.util.LinkedList;

public class Compressor {
    Compressor () {

    }
    /**
     *
     * @param buffer
     * @return
     */
    public LinkedList<Byte> compress (byte [] buffer, SymbolCode [] codes) {
        LinkedList<Byte> writeBuffer = new LinkedList<>();
        LinkedList<Boolean> byteSeparator = new LinkedList<>();
        for (byte currentByte : buffer) {
            int currentCode = codes[currentByte].getCode();
            int bitsCount = codes[currentByte].getBitsCount();
            for (int n = 0; n < bitsCount; n++) {
                if ((currentCode & (1 << n)) != 0) {
                    byteSeparator.add(true);
                } else {
                    byteSeparator.add(false);
                }
                //-------------------------------------------------------
                if (byteSeparator.size() >= 8) {
                    writeBuffer.add(getByte(byteSeparator, 8));
                }
            }
        }
        if(byteSeparator.size() > 0) {
            writeBuffer.add(getByte(byteSeparator, byteSeparator.size()));
        }
        return writeBuffer;
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

    public ArrayList<Byte> decompress(int length, LinkedList<Byte> compressed, CTree treeRestored) {
        ArrayList<Byte> out = new ArrayList<>();
        CNode root = treeRestored.getRoot();
        CNode curNode = root;
        for(byte iByte : compressed) {
            byte bTmp = iByte;
            for(int i = 0; i < 8; i++) {
                if (out. size() >= length) break;
                CNode [] leafs = curNode.getLeafs();

                if(leafs[0] == null && leafs[1] == null) {
                    out.add(curNode.getByteValue());
                    curNode = root;
                    i--;
                }
                else {
                    if ((bTmp & 1) > 0) {
                        curNode = leafs[1];
                    } else {
                        curNode = leafs[0];
                    }
                    bTmp >>= 1;
                }


            }
        }
        return out;
    }
}
