package com.shpp.p2p.cs.bcimbal.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part3 extends KarelTheRobot {

    //==================================================================================================================
    // To solve this problem Karel fills line (height no matter) side by side by beepers
    // it helps him to find center of line once at the cell where he plans put beeper it already exists
    // At this cell he puts another beeper (to mark this cell).
    // And walks all over the line to pick beepers one by one.
    // As a result he has one beeper in the one (or one of two) center cell
    //------------------------------------------------------------------------------------------------------------------
    // to simplify program make (un)comments a,b,c,d
    //==================================================================================================================
    public void run() throws Exception {

        while(noBeepersPresent()) {                             // if no beppers in current cell, else we found (one of) center cell
            putBeeper();
            if(frontIsClear()){
                goToOppositSide();                                  // go to opposite side to
            }
        }
        // at this step all line filled by beepers and we are in the one of center cell of line
//        putBeeper();                                            // put second beeper                    (a. comment this line)
//        pickAllSingleBeeppers();                                // pick all single beeppers             (b. comemnt this line)
    }

    //==================================================================================================================
    // Karel goes to opposite side of world (in curren row) and puts beeper in empty cel
    //==================================================================================================================
    private void goToOppositSide() throws Exception{

        move();                                                 // make one step to execute next stages (staying on beeper)

        while (frontIsClear() && noBeepersPresent()) move();    // move till wall or beeper
        if (beepersPresent()) {                                 // if beeper in current cell make one step back (turn around and move)
            pickBeeper();                                     //                                       (c. uncomment this line)
            turnAround();
            move();
        } else {
            turnAround();                                       // // if wall ahead current cell turn around and move
        }
    }

    //==================================================================================================================
    // Karel picks all single beepers                                                                    (d. comment method)
    //==================================================================================================================
    private void pickAllSingleBeeppers() throws Exception{
        while(frontIsClear()) move();                                   // go to the wall on current direction
        turnAround();
        while(frontIsClear()){                                          // while walking to the wall pick single beepers
            pickBeeper();
            move();
        }
        pickBeeper();                                                   // pick last beeper
    }

    //==================================================================================================================
    // To turn around Karel turns left twice
    //==================================================================================================================
    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }
    //==================================================================================================================

}
