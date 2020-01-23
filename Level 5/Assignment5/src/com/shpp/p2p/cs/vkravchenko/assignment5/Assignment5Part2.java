// File: Assignment5Part2.java
package com.shpp.p2p.cs.vkravchenko.assignment5;

import com.shpp.cs.a.console.TextProgram;

/*
 * Assignment5Part2 -- Assembly algorithm
 */
public class Assignment5Part2 extends TextProgram {
    /* ascii, '0' has the int value 48 */
    private static final int NUMBER_FIRST = 48;
    /* ascii, '9' has the int value 57 */
    private static final int NUMBER_LAST = 57;

    /**
     * Runs the program, the user enters two string values
     * code -- sums two numbers manually ( a convenient way )
     */
    public void run() {
        sumNumbers();
    }

    /**
     * This method will adjust the order of execution of other methods
     * and output the result
     */
    private void sumNumbers() {
        while (true) {
            String firstNumber;
            firstNumber = getNumber(0); // 0 -- to enter the first label
            String firstPrint = firstNumber;
            String secondNumber;
            secondNumber = getNumber(1); // 1 -- to enter the second label
            String secondPrint = secondNumber;
            int differenceNumbers = firstNumber.length() - secondNumber.length();
            if (differenceNumbers > 0) {
                secondNumber = getLine(differenceNumbers, secondNumber);
            } else {
                firstNumber = getLine(differenceNumbers, firstNumber);
            }
            println(firstPrint + " + " + secondPrint + " = " + addNumericStrings(firstNumber, secondNumber));
        }
    }

    /**
     * A method finds number which is the sum of the accepted numbers
     *
     * @param firstNumber  -- the first value of type String
     * @param secondNumber -- the second value of type String
     * @return value of type String
     */
    private String addNumericStrings(String firstNumber, String secondNumber) {
        int numberFirst;
        int numberSecond;
        int oneTransfer = 0;
        String lineTotal = "";
        for (int i = firstNumber.length() - 1; i >= 0; i--) {
            numberFirst = Character.getNumericValue(firstNumber.charAt(i));
            numberSecond = Character.getNumericValue(secondNumber.charAt(i));
            int numberSum = numberFirst + numberSecond + oneTransfer;
            if (numberSum > 9) {
                numberSum = numberSum % 10;
                oneTransfer = 1;
            } else {
                oneTransfer = 0;
            }
            lineTotal = String.valueOf(numberSum).concat(lineTotal);
        }
        if (oneTransfer == 1) {
            lineTotal = String.valueOf(oneTransfer).concat(lineTotal);
        }
        return lineTotal;
    }

    /**
     * A method aligns the strings entered by the user (increases the length of the smaller line)
     *
     * @param differenceNumbers -- the difference between the lengths of the two lines
     * @param stringOfMethod    -- a smaller string
     * @return new length string
     */
    private String getLine(int differenceNumbers, String stringOfMethod) {
        String lineZeros = "";
        if (differenceNumbers < 0) {
            differenceNumbers = Math.abs(differenceNumbers);
        }
        for (int i = 0; i < differenceNumbers; i++) {
            lineZeros = lineZeros.concat("0");
        }
        return lineZeros.concat(stringOfMethod);
    }

    /**
     * A method only accepts a line composed of
     * characters -- '0','1', '2', '3', '4', '5', '6', '7', '8', '9'
     *
     * @param i -- numbers 0 or 1 ( first label, second label)
     * @return user entered line
     */
    private String getNumber(int i) {
        String[] arrayString = {"first", "second"};
        String lineMethod;
        int k = 0;
        do {
            if (k++ > 0) println("Repeat, please1!");
            println("Enter " + arrayString[i] + " number:");
            int j = 0;
            do {
                if (j++ > 0) println("You pressed only enter!");
                lineMethod = readLine();
            } while (lineMethod.equals(""));
        } while (checkWord(lineMethod));
        return lineMethod;
    }

    /**
     * A method that checks the character code
     * should match the number from 48 to 57
     *
     * @param lineMethod -- user entered line
     * @return boolean values (true or false)
     */
    private boolean checkWord(String lineMethod) {
        boolean statusMethod = false;
        for (int i = 0; i < lineMethod.length(); i++) {
            if ((int) lineMethod.charAt(i) < NUMBER_FIRST || (int) lineMethod.charAt(i) > NUMBER_LAST) {
                statusMethod = true;
                break;
            }
        }
        return statusMethod;
    }
}
