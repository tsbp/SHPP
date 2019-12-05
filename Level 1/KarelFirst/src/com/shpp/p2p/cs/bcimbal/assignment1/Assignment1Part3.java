package com.shpp.p2p.cs.bcimbal.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part3 extends KarelTheRobot {

    //==================================================================================================================
    // To solve this problem Karel fills line (height no matter) side by side by beepers
    // it helps him to find center of line once at the cell where he plans put beeper it already exists
    // At this cell he puts another beeper (to mark this cell).
    // And walks all over the line to pick beepers one by one.
    // As a result he has one beeper in the one (or one of two) center cell
    //==================================================================================================================
    public void run() throws Exception {

        while(noBeepersPresent()) {// if no beppers in current cell, else we found (one of) center cell
            putBeeper();
            goToOppositSide();    // go to opposite side to
        }
        putBeeper();
        pickAllSingleBeeppers();
    }

    //==================================================================================================================
    //
    //==================================================================================================================
    private void goToOppositSide() throws Exception{
        if(frontIsClear()) {
            move();
            while (frontIsClear() && noBeepersPresent()) move();
            if (beepersPresent()) {
                turnAround();
                move();
            } else {
                turnAround();
            }
        }
    }

    //==================================================================================================================
    // Karel picks all single beeppers
    //==================================================================================================================
    private void pickAllSingleBeeppers() throws Exception{
        while(frontIsClear()) move();                                   // go to the wall on current dirrection
        turnAround();
        while(frontIsClear()){                                          // while walking to the wall pick single beeppers
            pickBeeper();
            move();
        }
        pickBeeper();                                                   // pick last beepper
    }

    //==================================================================================================================
    //
    //==================================================================================================================
    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }
    //==================================================================================================================

}
