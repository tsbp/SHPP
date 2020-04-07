package com.shpp.p2p.cs.bcimbal.assignment15;

/* Class represents structure to hold symbols data */
public class SymbolCode {
    private byte symbol;
    private int code;
    private int bitsCount;

    /*******************************************************************************************************************
     * Constructor
     * @param symbol byte input symbol
     * @param code symbol code
     * @param bitsCount symbol code bits number
     */
    SymbolCode(byte symbol, int code, int bitsCount) {
        this.symbol = symbol;
        this.code = code;
        this.bitsCount = bitsCount;
    }

    /*******************************************************************************************************************
     * get Code
     * @return code
     */
    public int getCode() {
        return code;
    }

    /*******************************************************************************************************************
     * get Symbol
     * @return symbol
     */
    public byte getSymbol() {
        return symbol;
    }

    /*******************************************************************************************************************
     * get Bits Count
     * @return Bits Count
     */
    public int getBitsCount() {
        return bitsCount;
    }
}
