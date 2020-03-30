package com.shpp.p2p.cs.bcimbal.assignment14;

public class Helper {
    public static final int IO_BUFFER_SIZE = 10;
    public static int getBitsCount ( int x){
        return (int) (Math.log(x) / Math.log(2)) + 1;
    }
}
