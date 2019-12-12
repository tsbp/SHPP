/* Filename: Assignment1Part4.java
 *
 * In this program Karel builds chessboard
 *
 */
package com.shpp.p2p.cs.bcimbal.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part4 extends KarelTheRobot {

    /* Precondition: Karel is standing in start position
     * Result: Karel builds chessboard
     */
    public void run() throws Exception {
        /* put beeper as a condition of task */
        putBeeper();
        /* fill line in current direction (to east) by alternating empties and beepers */
        fillLine();
        /* turn left (beginning position for next steps) */
        turnLeft();
        // fill verticals by auxiliary line
        while (frontIsClear()) {
            fillLine();
            moveBack();
            goToNextColumn();
        }
    }

    /* Precondition: facing to wall with auxiliary beepers
     * Result: facing to opposite wall
     */
    private void goToNextColumn() throws Exception {
        turnRight();
        if (frontIsClear()) {
            move();
            turnRight();
        }
    }

    /* Precondition: facing to wall
     * Result: facing to opposite wall
     */
    private void moveBack() throws Exception {
        turnAround();
        while (frontIsClear()) {
            move();
        }
    }

    /* Precondition: facing any
     * Result: fill line in current direction
     * by alternating empties and beepers, facing to wall
     */
    private void fillLine() throws Exception {
        /*moving to wall an alternating empties and beepers*/
        while (frontIsClear()) {
            if (noBeepersPresent()) {
                if (frontIsClear()) {
                    move();
                    putBeeper();
                }
            } else {
                if (frontIsClear()) {
                    move();
                }
            }
        }
    }

    /* Precondition: facing any
     * Result: facing opposite
     */
    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }

    /* Precondition: facing any
     * Result: turned right
     */
    private void turnRight() throws Exception {
        turnAround();
        turnLeft();
    }
}
