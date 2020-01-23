package com.shpp.p2p.cs.ipetrechenko.assignment5;

import com.shpp.cs.a.console.TextProgram;

/**
 * Aim: a program that reads numeric strings turning them into a number
 */
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

    /**
     * Given two string representations of nonnegative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    private String addNumericStrings(String n1, String n2) {

        if (n2.length() > n1.length()) {
            String n3 = n2;
            n2 = n1;
            n1 = n3;
        }
        String res = "";
        //remembers the number in the mind
        int carry = 0;

        for (int i = 0; i < n1.length(); i++) {
            String first = n1.substring(n1.length() - 1 - i, n1.length() - i);
            int firstValue = Integer.parseInt(first);

            int secondValue = 0;

            if (i < n2.length()) {
                String second = n2.substring(n2.length() - 1 - i, n2.length() - i);
                secondValue = Integer.parseInt(second);
            }

            int total = firstValue + secondValue + carry;

            if (total >= 10) {
                carry = 1;
                total -= 10;
            } else {
                carry = 0;
            }

            res = total + res;
        }

        if (carry == 1) {
            res = "1" + res;
        }
        return res;
    }
}
