package com.shpp.p2p.cs.bcimbal.assignment3;

/*
 * Program that ask the user for the number of minutes spent in the last seven days for the exercise
 * and reports the result of cardiovascular health and blood pressure
 */

import com.shpp.cs.a.console.TextProgram;
public class Assignment3Part1 extends TextProgram {

    private static final String [] DAY_OF_WEEK = {"Monday", "Tuesday", "Wednesday ", "Thursday", "Friday", "Saturday", "Sunday"};

    /* Messages to user*/
    private static final String STR_HOW_MANY            = "How many minutes did you do on ";
    private static final String STR_GREAT_JOB           = "Great job!";
    private static final String STR_DONE_ENOUGH_CARDIO  = " You've done enough exercise for cardiovascular health.";
    private static final String STR_DONE_ENOUGH_BLOOD   = " You've done enough exercise to keep a low blood pressure.";
    private static final String STR_RESULT_CARDIO       = "Cardiovascular health:";
    private static final String STR_RESULT_BLOOD        = "Blood pressure:";
    private static final String STR_YOU_NEED            = "You needed to train hard for at least ";
    private static final String STR_DAYS_A_WEKK         = " more day(s) a week!";

    /* train at least day(s) a week for cardiovascular and blood pressure, respectively*/
    private static final int CARDIO_DAYS_MIN = 5;
    private static final int BLOOD_DAYS_MIN = 3;

    /* train at least minutes a day for cardiovascular and blood pressure, respectively*/
    private static final int CARDIO_MINUTES_MIN = 30;
    private static final int BLOOD_MINUTES_MIN = 40;

    int [] result;
    /*******************************************************************************************************************
     * main method
     */
    public void run() {
        while(!exitRequested()) {
            result = getTimesForSevenDays();
            println();
            printResult(result, STR_RESULT_CARDIO, STR_DONE_ENOUGH_CARDIO, CARDIO_DAYS_MIN, CARDIO_MINUTES_MIN);
            printResult(result, STR_RESULT_BLOOD, STR_DONE_ENOUGH_BLOOD, BLOOD_DAYS_MIN, BLOOD_MINUTES_MIN);
        }
    }

    /*******************************************************************************************************************
     * Ask user about trainings
     *
     * @return array of integer per day for whole week
     */
    private int [] getTimesForSevenDays() {
        int [] result = new int [7];
        for (int i = 0; i < 7; i++) {
            print(STR_HOW_MANY + DAY_OF_WEEK[i] + "? ");
            String s = readLine();
            if(isValueInteger(s))  result[i] = Integer.parseInt(s);
            else {
                i--;
            }
        }
        return result;
    }

    /*******************************************************************************************************************
     * Print result message about trainings
     * @param result int array of users values for each day of week
     * @param sResult user message of type of current training
     * @param sDoneEnough user message of result of current training
     * @param daysMin  minimal days setting
     * @param minutesMin   minimal minutes for good result
     */
    private void printResult(int [] result, String sResult, String sDoneEnough, int daysMin, int minutesMin) {
        int cntr = 0;
        for (int i = 0; i < 7; i++) {
            if(result[i] >= minutesMin) cntr++;
        }
        println(sResult);
        print("  ");
        if (cntr >= daysMin) println(STR_GREAT_JOB + sDoneEnough);
        else println(STR_YOU_NEED +  (daysMin - cntr) + STR_DAYS_A_WEKK);
    }

    /* Message if content of string is not integer*/
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
        return request.equals("x");
    }
}

