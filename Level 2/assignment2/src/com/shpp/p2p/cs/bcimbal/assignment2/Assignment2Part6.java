package com.shpp.p2p.cs.bcimbal.assignment2;

/*
 *  Program draws matrix of black boxes with spacing between them
 */
import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;
import java.awt.*;

public class Assignment2Part6 extends WindowProgram {


    /* The default width and height of the window. These constants will tell Java to
     * create a window whose size is *approximately* given by these dimensions.   */
    public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 500;

    /* The number of rows and columns in the grid, respectively. */
    private static final int NUM_ROWS = 30;
    private static final int NUM_COLS = 50;

    /* The width and height of each box. */
    private static final double BOX_SIZE = 10;

    /* The horizontal and vertical spacing between the boxes. */
    private static final double BOX_SPACING = 3;


    public void run() {

        /* define start point coordinates x, y from which we start draw */
        int x = (int) (getWidth()  - (NUM_COLS * BOX_SIZE + (NUM_COLS - 1) * BOX_SPACING)) / 2;
        int y = (int) (getHeight() - (NUM_ROWS * BOX_SIZE + (NUM_ROWS - 1) * BOX_SPACING)) / 2;

        /* draw NUM_ROWS rows with NUM_COLS squares with BOX_SPACING spacing between them */
        for (int i = 0; i < NUM_ROWS; i++) {
            /* draw row of squares */
            for (int j = 0; j < NUM_COLS; j++) {
                GRect r = new GRect(
                        x + j * (BOX_SIZE + BOX_SPACING),      // x coordinate, make shift j times on (BOX_SIZE + BOX_SPACING) value
                        y + i * (BOX_SIZE + BOX_SPACING),     // y coordinate, make shift i times on (BOX_SIZE + BOX_SPACING) value
                        BOX_SIZE, BOX_SIZE);                      // square with BOX_SIZE * BOX_SIZE dimension
                r.setFillColor(Color.BLACK);
                r.setFilled(true);
                add(r);
            }
        }
        /*TODO
             to reviewer: decomposition is not needed, because matrix is drawn in few steps
        */

    }

}