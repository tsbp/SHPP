package com.shpp.p2p.cs.bcimbal.original.assignment3;

/*
 * Program draws picture of pyramid of bricks with specified parameters
 */

import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.*;

import java.awt.*;

public class Assignment3Part4 extends WindowProgram {

    /* The default width and height of the window. These constants will tell Java to
     * create a window whose size is *approximately* given by these dimensions. */
    public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 350;

    private static final int BRICK_HEIGHT = 20;
    private static final int BRICK_WIDTH = 10;
    private static final int BRICKS_IN_BASE = 15;

    /*******************************************************************************************************************
     * main method
     */
    public void run() {
        biuldPiramid();
    }

    /*******************************************************************************************************************
     * Method to build pyramid of bricks
     *
     * builds from bottom to top by substrate one brick per line
     */
    private void biuldPiramid() {
        double x = (getWidth() - (BRICKS_IN_BASE * BRICK_WIDTH)) / 2;
        double y = getHeight() - BRICK_HEIGHT;
        for (int i = BRICKS_IN_BASE; i > 0; i--)
            for (int j = 0; j < i; j++) {
                GOval r = new GOval(
                        x + j * BRICK_WIDTH + (BRICKS_IN_BASE - i) * BRICK_WIDTH / 2,
                        y- (BRICKS_IN_BASE - i) * BRICK_HEIGHT,
                        BRICK_WIDTH, BRICK_HEIGHT);
                r.setColor(Color.RED);
                add(r);
            }
    }


}

