package com.shpp.tsbp.cs;


public class Assignment1Part2 extends Assignment1Part1 {

    public void run() throws Exception {

        turnLeft();
        moveUP();
        turnAround();

        while(frontIsClear())
        {
            fillCollumn();
            moveToNextColumn();
        }
    }
    //==================================================================================================================
    private void moveUP() throws Exception{
        while(frontIsClear()) move();
    }
    //==================================================================================================================
    private void moveToNextColumn() throws Exception{
        turnAround();
        moveUP();
        turnRight();
        
        if(frontIsClear()) {
            move();
            while (leftIsClear()) move();
            turnRight();
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
}
