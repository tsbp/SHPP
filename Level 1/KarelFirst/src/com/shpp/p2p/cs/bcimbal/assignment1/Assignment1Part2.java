package com.shpp.p2p.cs.bcimbal.assignment1;


import com.shpp.karel.KarelTheRobot;

public class Assignment1Part2 extends KarelTheRobot {

    public void run() throws Exception {
        goToNorth();
        orientationToSouth();

        while(frontIsClear())
        {
            fillCollumn();
            moveToNextColumn();
        }
    }
    //==================================================================================================================
    private void orientationToSouth()throws  Exception {
        while (notFacingSouth()) turnLeft();
    }
    //==================================================================================================================
    private void goToNorth() throws Exception {
        while(notFacingNorth()) turnLeft();
        while(frontIsClear())   move();
    }
    //==================================================================================================================
    private void moveToNextColumn() throws Exception{

        goToNorth();
        turnRight();
        
        if(frontIsClear()) {// if not last column
            move();
            while (leftIsClear()) move();
            orientationToSouth();
        }
    }
    //==================================================================================================================
    private void fillCollumn() throws Exception{
        while(frontIsClear())
        {
            if(noBeepersPresent()) putBeeper();
            move();
        }
        if(noBeepersPresent()) putBeeper();
    }
    //==================================================================================================================
    // To turn right Karel turns left triple
    //==================================================================================================================
    private void turnRight() throws Exception{
        turnLeft();
        turnLeft();
        turnLeft();
    }
}
