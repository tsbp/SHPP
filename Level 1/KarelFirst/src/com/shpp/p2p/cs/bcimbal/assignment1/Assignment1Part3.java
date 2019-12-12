/* Filename: Assignment1Part3.java
 *
 * In this program Karel finds center of easter line
 *
 */
package com.shpp.p2p.cs.bcimbal.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Part3 extends KarelTheRobot {

    /* Precondition: Karel is standing in start position
     * Result: Karel is standing on beeper in one of the center cells
     */
    public void run() throws Exception {
        /* Karel put beepers at the ends of line
         * and moves them to the center
         */
        while (noBeepersPresent()) {
            putBeeper();
            if (frontIsClear()) {
                goToOppositeSideAndMoveBeeperToCenter();
            }
        }
    }

    /* Precondition: Karel is standing on beeper facing opposite side
     * Result: Karel is standing on beeper facing opposite side
     */
    private void goToOppositeSideAndMoveBeeperToCenter() throws Exception {
        /* make one step to execute next stages (staying on beeper) */
        move();
        /* move till wall or beeper */
        while (frontIsClear() && noBeepersPresent()) {
            move();
        }
        /* pick beeper to move it to center */
        if (beepersPresent()) {
            /* if beeper in current cell pick beeper and make one step back */
            pickBeeper();
            turnAround();
            move();
        } else {
            /* if no beeper only turn around*/
            turnAround();
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
