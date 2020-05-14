/* Filename: Assignment1Part1.java
 *
 * In this program Karel finds beeper and returns to start position
 *
 */
package com.shpp.p2p.cs.bcimbal.original.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part1 extends KarelTheRobot {

    /* Precondition: Karel is standing in start position
     * Result: Karel walked clockwise around the room along walls,
     * found beeper and returned to start position(marked by another beeper)
     */
    public void run() throws Exception {
        /* find beeper */
        goToBeeper();
        /* pick beeper (beeper always present in certain position)*/
        pickBeeper();
        /* returning to start position(marked by another beeper)*/
        goToOrigin();
    }

    /* Precondition: Karel is standing in start position
     * Result: Karel standing behind beeper facing east
     */
    private void goToBeeper() throws Exception {
        /*  put beeper to find original position */
        putBeeper();
        /*  move to prevent exit in next step */
        goToCornerOrDoor();

        /* cycle to find beeper */
        while (noBeepersPresent()) {
            goToCornerOrDoor();
            /* condition when Karel is facing to the door*/
            if (leftIsClear() && rightIsClear() && frontIsClear()) {
                /* do two steps to stand on beeper*/
                move();
                move();
            }
        }
    }

    /* Precondition: facing any
     * Result: Karel in the corner or near the door
     */
    private void goToCornerOrDoor() throws Exception {
        /* moves along wall to next corner
         * if frontIsBlocked it is corner
         * if leftIsClear it is door  */
        while (frontIsClear() && leftIsBlocked()) {
            move();
        }
        turnLeft();
    }

    /* Precondition: facing east
     * Result: staying in original position
     */
    private void goToOrigin() throws Exception {
        /* go out from door */
        turnAround();
        move();
        move();
        turnLeft();
        move();
        /* finding start position (marked by beeper) */
        while (noBeepersPresent()) {
            goToCornerOrDoor();
        }
        /* pick extra beeper */
        pickBeeper();
        /* turning to original position */
        turnAround();
    }

    /* Precondition: facing any
     * Result: facing opposite
     */
    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }


}