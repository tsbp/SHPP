package com.shpp.p2p.cs.bcimbal.assignment15;

public class CNode {
    private int frequency;
    private byte byteValue;
    private int bitCount;
    private int code;
    private CNode[] leafs = new CNode[2];

    private boolean visited = false;

    CNode(int freq, byte byteValue, CNode[] nodes) {
        this.frequency = freq;
        this.byteValue = byteValue;

        for(int i = 0; i < nodes.length; i++) {
                this.leafs[i] = nodes[i];
            }

    }
    public int getBitCount() {
        return bitCount;
    }
    public void setBitCount(int bitCount) {
        this.bitCount = bitCount;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public int getFreq() {
        return frequency;
    }
    public byte getByteValue() {
        return byteValue;
    }
    public CNode[] getLeafs() {
        return this.leafs;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    public boolean isVisited() {
        return visited;
    }
}
