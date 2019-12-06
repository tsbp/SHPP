package com.shpp.p2p.cs.bcimbal.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part2 extends KarelTheRobot {

    //==================================================================================================================
    //  Karel fills empty cells in columns from floor to roof
    //==================================================================================================================
    public void run() throws Exception {

        while (frontIsClear()) {                        // if not end of the world or wall
            buildColumn();                              // build column
            goToNextColumn();                           // go to the next column
        }
        // now we are at the end of the world (wall)
        buildColumn();                                  // build column before wall
    }

    //==================================================================================================================
    // go to next column
    //==================================================================================================================
    private void goToNextColumn() throws Exception {
        //---- go down ------------------
        turnAround();
        while (frontIsClear()) move();                  // making steps till meet floor
        //-------------------------------
        turnLeft();                                     // turn to direction of next column

        // make fOUR steps to the nex column
        for (int i = 0; i < 4; i++) {
            if (frontIsClear()) move();                 // while not the end of world (or wall) make step
        }
    }

    //==================================================================================================================
    // Filling empty cells in column from floor to roof
    //==================================================================================================================
    private void buildColumn() throws Exception {

        turnLeft();                                     // turn left to facing up

        while (frontIsClear()) {                        // repeat while not roof
            if (noBeepersPresent()) putBeeper();        //  +   if no beeper put beeper
            move();                                     //  +   make step
        }
        if (noBeepersPresent()) putBeeper();            // put beeper (if needed) at last cell

    }

    //==================================================================================================================
    // To turn around Karel turns left twice
    //==================================================================================================================
    private void turnAround() throws Exception {
        turnLeft();                                    // 1
        turnLeft();                                    // 2
    }

    //==================================================================================================================
    // To turn right Karel turns left triple
    //==================================================================================================================
    private void turnRight() throws Exception {
        turnAround();                                  // 2
        turnLeft();                                    // 3
    }

}