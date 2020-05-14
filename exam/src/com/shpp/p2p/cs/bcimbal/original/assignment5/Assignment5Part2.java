package com.shpp.p2p.cs.bcimbal.original.assignment5;

import com.shpp.cs.a.console.TextProgram;


public class Assignment5Part2 extends TextProgram {
    public void run() {
        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            String n1 = readLine("Enter first number:  ");
            String n2 = readLine("Enter second number: ");
            println(n1 + " + " + n2 + " = " + addNumericStrings(n1, n2));
            println();
        }
    }

    /*******************************************************************************************************************
     * Given two string representations of nonnegative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    private String addNumericStrings(String n1, String n2) {

        /* if strings length not equal make their length same */
        if      (n1.length() > n2.length()) n2 = increase(n2, n1.length() - n2.length());
        else if (n2.length() > n1.length()) n1 = increase(n1, n2.length() - n1.length());


        String out = "";
        int overflow = 0; // overflow control

        for (int i = n1.length() - 1; i >= 0; i--) {
            int a = overflow + (n1.charAt(i) + n2.charAt(i) - 2 * '0');
            if (a >= 10) overflow = 1; // sum of two numbers more than 10, add one in next iteration
            else overflow = 0;
            out = a % 10 + out;
        }
        if (overflow == 1) out = "1" + out;
        return out;
    }

    /*******************************************************************************************************************
     * add to string "0"
     *
     * @param str input string
     * @param increaseValue adds number
     * @return increased string
     */
    private String increase(String str, int increaseValue) {
        for (int i = 0; i < increaseValue; i++)
            str = "0" + str;
        return str;
    }


}
