/* Filename: Assignment1Part2.java
 *
 * In this program Karel builds columns
 *
 */
package com.shpp.p2p.cs.bcimbal.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part2 extends KarelTheRobot {

    /* Precondition: Karel is standing in start position
     * Result: Karel build all columns
     */
    public void run() throws Exception {

        while (frontIsClear()) {
            /* while not end of the world build columns*/
            buildColumn();
            goToNextColumn();
        }
        /* build last column */
        buildColumn();
    }

    /* Precondition: Karel is standing under roof facing up
     * Result: Karel on the floor facing east
     */
    private void goToNextColumn() throws Exception {
        turnAround();
        /* making steps till meet floor*/
        while (frontIsClear()) {
            move();
        }
        /* turn to direction of next column */
        turnLeft();
        /* make four steps to the next column */
        for (int i = 0; i < 4; i++) {
            if (frontIsClear()) {
                move();
            }
        }
    }

    /* Precondition: Karel is standing on floor facing east
     * Result: Karel is standing under roof facing up
     */
    private void buildColumn() throws Exception {
        /*turn left to facing up*/
        turnLeft();
        /* build (restore) column */
        while (frontIsClear()) {
            if (noBeepersPresent()) {
                putBeeper();
            }
            move();
        }
        /*put beeper (if needed) at last cell*/
        if (noBeepersPresent()) {
            putBeeper();
        }
    }

    /* Precondition: facing any
     * Result: facing opposite
     */
    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }


}