package com.shpp.p2p.cs.bcimbal.assignment2;

/* TODO: Replace these file comments with a description of what your program
 * does.
 */
import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part5 extends WindowProgram {


    /* The default width and height of the window. These constants will tell Java to
     * create a window whose size is *approximately* given by these dimensions. You should
     * not directly use these constants in your program; instead, use getWidth() and
     * getHeight(), which return the *exact* width and height of the window.
     */
    public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 500;

    /* The number of rows and columns in the grid, respectively. */
    private static final int NUM_ROWS = 7;
    private static final int NUM_COLS = 8;

    /* The width and height of each box. */
    private static final double BOX_SIZE = 50;

    /* The horizontal and vertical spacing between the boxes. */
    private static final double BOX_SPACING = 10;


    public void run() {

        double x = (getWidth()  - (NUM_COLS * BOX_SIZE + (NUM_COLS - 1) * BOX_SPACING)) / 2;
        double y = (getHeight() - (NUM_ROWS * BOX_SIZE + (NUM_ROWS - 1) * BOX_SPACING)) / 2;

        for(int i = 0; i < NUM_ROWS; i++){
            for (int j = 0; j < NUM_COLS; j++) {
                GRect r = new GRect(
                        x + j * (BOX_SIZE + BOX_SPACING),
                        y + i * (BOX_SIZE + BOX_SPACING),
                        BOX_SIZE, BOX_SIZE);
                r.setFilled(true);
                r.setFillColor(Color.BLACK);
                add(r);
            }
        }

    }

}