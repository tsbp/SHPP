package com.shpp.p2p.cs.bcimbal.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part4 extends KarelTheRobot {

    //==================================================================================================================
    //
    //==================================================================================================================
    public void run() throws Exception {
        //------------------------------------------------
        putBeeper();
        while(frontIsClear()){
           fillLine();
        }
        //------------------------------------------------
        turnLeft();
        while(frontIsClear()) {
            fillLine();
            turnAround();
            while (frontIsClear()) move();
            turnRight();
            if(frontIsClear()){
                move();
                turnRight();
            }
        }
    }

    //==================================================================================================================
    //
    //==================================================================================================================
    private void fillLine() throws Exception{
        // fill column
        while (frontIsClear()) {
            if (noBeepersPresent()) {
                if (frontIsClear()) {
                    move();
                    putBeeper();
                }
            } else {
                if (frontIsClear()) move();
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
