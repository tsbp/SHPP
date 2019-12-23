package com.shpp.p2p.cs.bcimbal.assignment3;

/*
 *  This program calculates the value of "base" raised to the degree of the "exponent"
 */

import com.shpp.cs.a.console.TextProgram;
public class Assignment3Part3 extends TextProgram {

    /* Massages to user*/
    private static final String STR_ASK_BASE     = "Enter base (to fractional number use point): ";
    private static final String STR_ASK_EXPONENT = "Enter exponent (integer value): ";

    /* Message to user if content of string is not valid*/
    private static final String STR_INCORRECT           = "Incorrect value. Please try again...";
    private enum VALType{_double, _int, _unknown};


    /*******************************************************************************************************************
     * main method
     */
    public void run() {
        double base;
        int exponent;
        while (!exitRequested()) {
            base =          readValue(VALType._double);
            exponent = (int)readValue(VALType._int);
            println("result = " + raiseToPower(base, exponent));
        }
    }

    /*******************************************************************************************************************
     * Method to read certain value type
     *
     * @param type expected type ov value
     * @return double (if expected int print error message)
     */
    private double readValue(VALType type) {
        while(true){
            switch(type)
            {
                case _double:
                    print(STR_ASK_BASE);
                    break;
                case _int:
                    print(STR_ASK_EXPONENT);
                    break;
            }
            String string  = readLine();
            double tempVal = 0;
            try{
                switch(type)
                {
                    case _double:
                        tempVal = Double.parseDouble(string);
                        break;
                    case _int:
                        tempVal = Integer.parseInt(string);
                        break;

                }
                return tempVal;
            }
            catch (Exception e){
                println(STR_INCORRECT);
            }
        }
    }

    /*******************************************************************************************************************
     * Сalculates the value "base" parameter raised to the degree of the "exponent" parameter
     *
     * @param base double value
     * @param exponent integer value
     * @return true
     */
    private double raiseToPower(double base, int exponent){

        double result = 1;
        int e = exponent;
        if (exponent < 0)      base = 1 / base;
        exponent = Math.abs(exponent);
        for(int i = 0; i < exponent; i++)  result *= base;
        return result;
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

}

