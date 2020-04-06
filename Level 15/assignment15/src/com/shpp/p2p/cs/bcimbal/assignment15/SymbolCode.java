package com.shpp.p2p.cs.bcimbal.assignment15;

public class SymbolCode {
    private byte symbol;
    private int code;
    private int bitsCount;

    SymbolCode(byte symbol, int code, int bitsCount) {
        this.symbol = symbol;
        this.code = code;
        this.bitsCount = bitsCount;
    }

    public int getCode() {
        return code;
    }

    public byte getSymbol() {
        return symbol;
    }

    public int getBitsCount() {
        return bitsCount;
    }
}
