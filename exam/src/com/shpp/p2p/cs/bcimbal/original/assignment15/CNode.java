package com.shpp.p2p.cs.bcimbal.original.assignment15;

/* Class represents structure to hold huffman tree node data */

public class CNode {
    /* frequency at which the symbol occurs */
    private int frequency;
    /* byte value of symbol */
    private byte byteValue;
    /* symbol code */
    private int code;
    /* number of bits symbol code occupies */
    private int bitCount;
    /* node leafs (2 pcs.) */
    private CNode[] leafs = new CNode[2];
    /* marker to perform  search algorithm*/
    private boolean visited = false;

    /*******************************************************************************************************************
     * Constructor
     * @param freq frequency at which the symbol occurs
     * @param byteValue byte value of symbol
     * @param leafs node leafs
     */
    CNode(int freq, byte byteValue, CNode[] leafs) {
        this.frequency = freq;
        this.byteValue = byteValue;

        System.arraycopy(leafs, 0, this.leafs, 0, leafs.length);
    }

    /*******************************************************************************************************************
     * External request for code bits count
     * @return code bits number
     */
    public int getBitCount() {
        return bitCount;
    }

    /*******************************************************************************************************************
     * External set code bits number
     * @param bitCount code bits number
     */
    public void setBitCount(int bitCount) {
        this.bitCount = bitCount;
    }

    /*******************************************************************************************************************
     * External request for symbol code
     * @return symbol code
     */
    public int getCode() {
        return code;
    }

    /*******************************************************************************************************************
     * External set  symbol code
     * @param code  symbol code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /*******************************************************************************************************************
     * External request for symbol frequency
     * @return symbol frequency
     */
    public int getFreq() {
        return frequency;
    }

    /*******************************************************************************************************************
     *  External request for symbol byte value
     * @return symbol byte value
     */
    public byte getByteValue() {
        return byteValue;
    }

    /*******************************************************************************************************************
     *  External request for node leafs
     * @return node leafs
     */
    public CNode[] getLeafs() {
        return this.leafs;
    }

    /*******************************************************************************************************************
     * External set search algorithm marker
     * @param visited boolean, true if operations have been executed
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /*******************************************************************************************************************
     * External request search algorithm marker state
     * @return boolean, true if operations have been executed
     */
    public boolean isVisited() {
        return visited;
    }
}
