//package com.shpp.tsbp.cs;
package com.shpp.p2p.cs.bcimbal.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part1 extends KarelTheRobot {

    //==================================================================================================================
    // Karel always walks clockwise around the room along walls to find beeper and start positon(marked by another beeper)
    //==================================================================================================================
    public void run() throws Exception {
        goToBeeper();   // finding door and stops behind beeper
        pickBeeper();   // as written
        goToOrigin();   // finding beeper that marks original position
    }

    //==================================================================================================================
    // Karel marks original position (put beeper) and moves from corner to corner to find door
    //==================================================================================================================
    private void goToBeeper() throws Exception{
        //----- mark start -------------------------
        putBeeper();                                        // put beeper to find original position
        goToCornerOrDoor();                                 // move to prevent exit in next step

        //------- finding beeper -------------------
        while(noBeepersPresent())                           // finding beeper
        {
            goToCornerOrDoor();                             // moves along wall to next corner (on his way he may find door)
            if(leftIsClear() && rightIsClear() && frontIsClear())
            {
                move();    //  if it is door(he looking at door), Karel enters
                move();
            }
        }
        // we found door and staying on beeper
    }

    //==================================================================================================================
    // Karel moves along wall till find corner or door
    //==================================================================================================================
    private void goToCornerOrDoor() throws Exception{
        while(                              // moves (1 step) along wall to next corner (frontIsClear and leftIsBlocked),
                frontIsClear()              // if frontIsBlocked it is corner
                        && leftIsBlocked()) // if leftIsClear it is door,
            move();

        turnLeft();                                      // always turning left
    }

    //==================================================================================================================
    // Karel moves along wall till find corner or door
    //==================================================================================================================
    private void goToOrigin() throws Exception {
        //---- go out from door ------
        turnAround();
        move();
        move();
        turnLeft();
        move();
        //---- finding start position -----------
        while(noBeepersPresent())             // while we not find beeper (marks original position)
            goToCornerOrDoor();               // we walk along walls from corner to corner
        pickBeeper();                         // pick beeper that marks start
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