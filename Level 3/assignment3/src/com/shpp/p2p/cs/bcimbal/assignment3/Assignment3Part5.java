package com.shpp.p2p.cs.bcimbal.assignment3;

/*
 * Casino game
 */

import acm.util.RandomGenerator;
import com.shpp.cs.a.console.TextProgram;
public class Assignment3Part5 extends TextProgram {

    /* Messages to user*/
    private static final String STR_EARNED          = "This game, you earned Є";
    private static final String STR_TOTAL           = "Your total is Є";
    private static final String STR_IT_TOOK         = "It took ";
    private static final String STR_GAMES_TO_EARN   = " games to earn more than Є";

    /*******************************************************************************************************************
     * main method
     */
    public void run() {
        while(!exitRequested())
            playGame();
    }

    /*******************************************************************************************************************
     * One game
     */
    private void playGame() {
        RandomGenerator rgen = RandomGenerator.getInstance();
        int bank = 1, wallet = 0, gamesCntr = 0;
        while (wallet < 20) {
            if(rgen.nextBoolean()) {
                gamesCntr++;
                wallet += bank;                 // pick up bank
                println(STR_EARNED + bank);
                println(STR_TOTAL + wallet);
                bank = 1;                       // add 1 euro to bank
            }
            else {
                bank += bank;                    // bank doubling
            }
        }
        println(STR_IT_TOOK + gamesCntr + STR_GAMES_TO_EARN + wallet);
    }

    /* String to аsk user for further action */
    private static final String STR_EXIT = "To exit enter x. To continue press enter. ";
    /*******************************************************************************************************************
     * Method to check exit condition ("x" string)
     *
     * @return true if exit condition present
     */
    private boolean exitRequested() {
        print(STR_EXIT);
        return readLine().equals("x");
    }
}

