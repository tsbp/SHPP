package com.shpp.p2p.cs.bcimbal.assignment2;

/* Program draws picture similar to flag
 * with text label in right left corner
 */

import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part4 extends WindowProgram {

    /* The default width and height of the window. */
    public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 500;
    /* Dimensions of flag sector width and height, respectively*/
    public static final int SEGMENT_WIDTH = 100;
    public static final int SEGMENT_HEIGHT = 250;
    /* String flag of */
    public static final String FLAG_OFF_STRING = "Flag of luGANDONia";
    /* Art and Science of Java page 107*/
    private static int TEXT_OFFSET = 8;
    /* Three colors for flag*/
    private static Color color[] = {Color.CYAN, Color.BLUE, Color.RED};

    /*******************************************************************************************************************
     * Run
     */
    public void run() {

        /* Calculate canter of canvas x and y, respectively*/
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        /* Calculate start point to draw first segment x and y, respectively*/
        int startX = centerX - 3 * (SEGMENT_WIDTH / 2);
        int startY = centerY - (SEGMENT_HEIGHT / 2);

        /* Draw three segments of flag*/
        for (int i = 0; i < 3; i++) {
            addSegmentOfFlag(startX + i * SEGMENT_WIDTH, startY, SEGMENT_WIDTH, SEGMENT_HEIGHT, color[i]);
        }

        /*print label flag of*/
        GLabel label = new GLabel(FLAG_OFF_STRING);
        label.setFont(new Font("Courier New", 3, 36));
        /* calculate position of text*/
        int x = (getWidth() - (int) label.getWidth());
        int y = (getHeight() - TEXT_OFFSET);
        add(label, x, y);

    }

    /*******************************************************************************************************************
     * Draws a rectangle (segment of flag).
     *
     * @param aX      The x coordinate of the upper-left corner of segment.
     * @param aY      The y coordinate of the upper-left corner of segment.
     * @param aWidth  Width of segment.
     * @param aHeight Height of segment.
     * @param aColor  Color of segment.
     */
    private void addSegmentOfFlag(int aX, int aY, int aWidth, int aHeight, Color aColor) {
        GRect segment = new GRect(aX, aY, aWidth, aHeight);
        segment.setColor(aColor);
        segment.setFillColor(aColor);
        segment.setFilled(true);
        add(segment);
    }


}