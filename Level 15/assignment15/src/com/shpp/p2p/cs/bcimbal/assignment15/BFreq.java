package com.shpp.p2p.cs.bcimbal.assignment15;

public class BFreq {
    private int frequency;
    private byte byteValue;
    /*private BFreq parent;*/
    private BFreq left;
    private BFreq right;

    BFreq (int freq, byte byteValue, /*BFreq parent,*/ BFreq left, BFreq right) {
        this.frequency = freq;
        this.byteValue = byteValue;
//        this.parent = parent;
        this.left = left;
        this.right = right;
    }
    public int getFreq() {
        return frequency;
    }
    public byte getByteValue() {
        return byteValue;
    }
//    public BFreq getParent() {
//        return this.parent;
//    }
   /* public void setParent(BFreq parent) {
        this.parent = parent;
    }*/
    public BFreq getLeft() {
        return this.left;
    }
    public BFreq getRight() {
        return this.right;
    }
}
