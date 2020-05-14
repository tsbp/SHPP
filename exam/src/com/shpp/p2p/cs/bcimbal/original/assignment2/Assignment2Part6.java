package com.shpp.p2p.cs.bcimbal.original.assignment2;

/* This program draws picture similar to caterpillar */

import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part6 extends WindowProgram {

    /* The default width and height of the window. These constants will tell Java to
     * create a window whose size is *approximately* given by these dimensions.
     */
    public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 500;

    /* Shift on x and y direction between segments of caterpillar, respectively. */
    private static final int SHIFT_X = 30;
    private static final int SHIFT_Y = 35;

    /* Position offset of picture in canvas from left top corner. */
    private static final int POSITION_OFFSET_X = 150;
    private static final int POSITION_OFFSET_Y = 300;

    /* Size and count caterpillar segments, respectively. */
    private static final int SEGMENT_SIZE = 100;
    private static final int SEGMENT_COUNT = 8;

    /* Additionally, caterpillar eye size */
    private static final int EYE_SIZE = 30;

    /*******************************************************************************************************************
     *  main method
     */
    public void run() {
        int alt = 0;                // used for alternate segment position
        int x = 0, y = 0;           // define current coordinates of segment

        /*draw SEGMENT_COUNT segments*/
        for (int i = 0; i < SEGMENT_COUNT; i++) {
            /* calculate position of segment */
            x = POSITION_OFFSET_X + i * SHIFT_X;
            y = POSITION_OFFSET_Y;
            /* if alt = 0 lower position , else upper position */
            if (alt == 1) {
                y -= SHIFT_Y;    // up shift segment
            }
            segmentAdd(x, y, SEGMENT_SIZE, Color.GREEN, Color.YELLOW);                 // place current segment
            alt ^= 1;                                                                  // make alternating
        }

        /* place eyes, right and left */
        segmentAdd(x + SEGMENT_SIZE / 2 - 2 * EYE_SIZE, y, EYE_SIZE, Color.BLUE, Color.RED);
        segmentAdd(x + SEGMENT_SIZE / 2 + EYE_SIZE, y, EYE_SIZE, Color.BLUE, Color.RED);
    }

    /*******************************************************************************************************************
     * Draws a circle(segment of caterpillar). The parameters should specify the upper-left corner of the
     * bounding box containing segment? its size ad color.
     *
     * @param aX The x coordinate of the upper-left corner of segment.
     * @param aY The y coordinate of the upper-left corner of segment.
     * @param aSize Size of segment (circle).
     * @param aColor Border color of segment.
     * @param aFillColor Fill color of segment.
     ******************************************************************************************************************/
    private void segmentAdd(int aX, int aY, int aSize, Color aColor, Color aFillColor) {
        GOval seg = new GOval(aX, aY, aSize, aSize);
        seg.setColor(aColor);
        seg.setFillColor(aFillColor);
        seg.setFilled(true);
        add(seg);
    }

}