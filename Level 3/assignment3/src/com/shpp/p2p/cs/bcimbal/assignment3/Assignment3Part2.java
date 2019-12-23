package com.shpp.p2p.cs.bcimbal.assignment3;

/*
 * The Collatz conjecture
 * https://en.wikipedia.org/wiki/Collatz_conjecture
 */

import com.shpp.cs.a.console.TextProgram;

public class Assignment3Part2 extends TextProgram {

    /* Messages to user*/
    private static final String STR_YOUR_NUMBER = "Input your integer number: ";
    private static final String STR_EVEN = " is even, so multiply by 3 and add 1, result ";
    private static final String STR_ODD = " is odd, so divide by 2, result ";

    /*******************************************************************************************************************
     * main method
     */
    public void run() {
        while (!exitRequested()) {
            makeIterations( getIntegerValue());
        }
    }

    /*******************************************************************************************************************
     * Method to check if string contains integer value
     *
     * @param number  a value to make iteration with
     */
    private void makeIterations(int number) {
        while (number != 1) {
            if (number % 2 == 0) {
                print(number + STR_ODD);
                number /= 2;
            } else {
                print(number + STR_EVEN);
                number = (number * 3 + 1);
            }
            println("" + number);

        }
    }

    /*******************************************************************************************************************
     * User input of integer value
     *
     * @return integer value from input
     */
    private int getIntegerValue() {
        String inputString;
        print(STR_YOUR_NUMBER);
        do {
            inputString = readLine();
        } while (!isValueInteger(inputString));
        return Integer.parseInt(inputString);
    }

    /* String to аsk user for further action */
    private static final String STR_EXIT = "To exit enter x. To continue press enter. ";
    /*******************************************************************************************************************
     * Method to check exit condition
     *
     * @return true if exit condition present
     */
    private boolean exitRequested() {
        print(STR_EXIT);
        String request = readLine();
        if (request.equals("x")) return true;
        return false;
    }


    /* Message to user if content of string is not integer*/
    private static final String STR_INCORRECT           = "Incorrect value. Please try again...";
    /*******************************************************************************************************************
     * Method to check if string contains integer value
     *
     * @param inputString tested string
     * @return true if value of string is integer, else false
     */
    private boolean isValueInteger(String inputString) {
        if (inputString.length() == 0) return false;
        for (int j = 0; j < inputString.length(); j++) {
            /* Test if the current character is not a digit. If so, return false. */
            char ch = inputString.charAt(j);
            if (!Character.isDigit(ch)) {
                println(STR_INCORRECT);
                return false;
            }
        }
        return true;
    }


}

