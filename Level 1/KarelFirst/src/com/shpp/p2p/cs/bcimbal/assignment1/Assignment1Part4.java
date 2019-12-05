package com.shpp.p2p.cs.bcimbal.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part4 extends KarelTheRobot {

    //==================================================================================================================
    //
    //==================================================================================================================
    public void run() throws Exception {
        //------------------------------------------------
        putBeeper();
        fillLine();
        turnLeft();
        //------------------------------------------------

        while(frontIsClear()) {
            fillLine();
            turnAround();
            moveBack();
            goToNextColumn();
        }
    }

    //==================================================================================================================
    // To turn right Karel turns left triple
    //==================================================================================================================
    private void goToNextColumn() throws Exception{
        turnRight();
        if(frontIsClear()){
            move();
            turnRight();
        }
    }

    //==================================================================================================================
    // To turn right Karel turns left triple
    //==================================================================================================================
    private void moveBack() throws  Exception{
        while (frontIsClear()) move();
    }

    //==================================================================================================================
    // fiil line in current dirrection
    // by alternating beepres and empties
    //==================================================================================================================
    private void fillLine() throws Exception{
        // fill column
        while (frontIsClear()) {              // while we not meet wall repeat
            if (noBeepersPresent()) {         //     +  if beeper absent
                if (frontIsClear()) {         //     +
                    move();                   //     +          if no wall ahead make step and put beeper
                    putBeeper();              //     +
                }                             //     +
            } else {                          //     +  if beeper present
                if (frontIsClear()) move();   //     +          if no wall ahead make step
            }
        }
    }

    //==================================================================================================================
    // To turn around Karel turns left twice
    //==================================================================================================================
    private void turnAround() throws Exception{
        turnLeft();
        turnLeft();
    }

    //==================================================================================================================
    // To turn right Karel turns left triple
    //==================================================================================================================
    private void turnRight() throws Exception{
        turnAround();
        turnLeft();
    }
}
