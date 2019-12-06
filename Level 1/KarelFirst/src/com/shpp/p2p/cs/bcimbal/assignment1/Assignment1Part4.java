package com.shpp.p2p.cs.bcimbal.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part4 extends KarelTheRobot {

    //==================================================================================================================
    // main method to build chess board
    // 1. Putting beeper (problem of task)
    // 2. Filling horizontal row by alternating empties and beepers
    // 3. According to cells in horizontal rows fills vertical columns
    //==================================================================================================================
    public void run() throws Exception {
        //------------------------------------------------
        putBeeper();                                            // put beeper in current position
        fillLine();                                             // fill line in current direction (to east) by alternating empties and beepers
        turnLeft();                                             // turn left to facing north (in current case)
        //------------------------------------------------

        while(frontIsClear()) {                                 // while we not meet wall (end of wal)
            fillLine();                                         // fills line in north direction
            moveBack();                                         // moves back in current line
            goToNextColumn();                                   // and jumps to next line (column)
        }
    }

    //==================================================================================================================
    // returns Karel to original position before next step
    //==================================================================================================================
    private void goToNextColumn() throws Exception{
        turnRight();                                            // turning right because we facing south
        if(frontIsClear()){                                     // if not wall (end of world)
            move();                                             // make step
            turnRight();                                        // turn right to facing north (start positon before filling line)
        }
    }

    //==================================================================================================================
    // returns Karel to opposite wall (end of world) in current line
    //==================================================================================================================
    private void moveBack() throws  Exception{
        turnAround();                                           // turning around
        while (frontIsClear()) move();                          // move to wall (end of world)
    }

    //==================================================================================================================
    // fill line in current dirrection
    // by alternating empties and beepres
    //==================================================================================================================
    private void fillLine() throws Exception{
        // fill column
        while (frontIsClear()) {                                // while we not meet wall repeat
            if (noBeepersPresent()) {                           //     +  if beeper absent
                if (frontIsClear()) {                           //     +
                    move();                                     //     +          if no wall ahead make step and put beeper
                    putBeeper();                                //     +
                }                                               //     +
            } else {                                            //     +  if beeper present
                if (frontIsClear()) move();                     //     +          if no wall ahead make step
            }
        }
    }

    //==================================================================================================================
    // To turn around Karel turns left twice
    //==================================================================================================================
    private void turnAround() throws Exception{
        turnLeft();                                             // 1
        turnLeft();                                             // 2
    }

    //==================================================================================================================
    // To turn right Karel turns left triple
    //==================================================================================================================
    private void turnRight() throws Exception{
        turnAround();                                           // 2
        turnLeft();                                             // 3
    }
}
