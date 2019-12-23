package com.shpp.p2p.cs.bcimbal.assignment3;

/* Animation of caterpillar sinusoidal move*/

import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment3Part6 extends WindowProgram {

    /* The default width and height of the window. These constants will tell Java to
     * create a window whose size is *approximately* given by these dimensions.
     */
    public static final int APPLICATION_WIDTH = 1000;
    public static final int APPLICATION_HEIGHT = 500;

    /********** CATERPILLAR SETTINGS **********************/
    /* Size and count caterpillar segments, respectively. */
    private static final int SEGMENT_SIZE = 70;
    private static final int SEGMENT_COUNT = 20;
    /* Additionally, caterpillar eye size */
    private static final int EYE_SIZE = SEGMENT_SIZE / 3;

    /********** ANIMATION SETTINGS **********************/
    /* steps between segments in x axis */
    private static final int STEP_BETWEEN_SEGMENTS = 8;
    /* scale coefficients for sinusoid*/
    private static final int Y_SCALE = 70;
    private static final double X_AXIS_COMPRESS = 3; // if 0 then line
    /* sinusoid baseline top margin */
    private static final int TOP_MARGIN = 200;
    /* animation step in x axis */
    private static final int STEP_MOVE = 1;

    private static final double FRAME_RATE = 60;
    /* The amount of time to pause between frames */
    private static final double PAUSE_TIME = 1000.0 / FRAME_RATE;
    private static final double ANIM_DURATION = 5000.0;
    private static final double FRAMES_TOTAL = ANIM_DURATION / PAUSE_TIME;

    /* constant for degrees to radians conversions*/
    private static final double DEGREES_TO_RADIANS = 0.017453292519943295D;

    /*******************************************************************************************************************
     *  main method
     */
    public void run() {
        animate(myCaterpillar());
    }

    /*******************************************************************************************************************
     *  Sinusoidal movement
     *
     * @param  caterpillar array of GObjects
     */
    private void animate(GObject[] caterpillar) {

        int angle = 0;               // angle of segment on sinusoid
        int framesCounter = 0;

        /* move till right end of canvas or frames total */
        while (caterpillar[SEGMENT_COUNT + 1].getX() + EYE_SIZE < getWidth() &&
               framesCounter < FRAMES_TOTAL) {

            for (int i = 0; i < SEGMENT_COUNT + 2; i++) {
                angle = (int) caterpillar[i].getX() + STEP_MOVE;
                double ay = Math.sin(angle * DEGREES_TO_RADIANS * X_AXIS_COMPRESS);
                caterpillar[i].move(STEP_MOVE, TOP_MARGIN - (double) SEGMENT_SIZE / 2 - caterpillar[i].getY() - (int) (Y_SCALE * ay));
            }
            /* eyes movement */
            caterpillar[SEGMENT_COUNT].setLocation(caterpillar[SEGMENT_COUNT - 1].getX() + (double) SEGMENT_SIZE / 2 - 2 * EYE_SIZE, (int) caterpillar[SEGMENT_COUNT - 1].getY());
            caterpillar[SEGMENT_COUNT + 1].setLocation(caterpillar[SEGMENT_COUNT - 1].getX() + (double) SEGMENT_SIZE / 2 + EYE_SIZE, (int) caterpillar[SEGMENT_COUNT - 1].getY());
            pause(PAUSE_TIME);
            framesCounter++;
        }
    }

    /*******************************************************************************************************************
     *  Create caterpillar
     * @return array of GObject that stores segments of gusenitsa
     */
    private GObject[] myCaterpillar() {
        double x, y;
        GObject[] cp = new GOval[SEGMENT_COUNT + 2];                       // +2 eyes
        for (int i = 0; i < SEGMENT_COUNT; i++) {
            x = i * STEP_BETWEEN_SEGMENTS;
            y = TOP_MARGIN - SEGMENT_SIZE / 2 + (int) (Y_SCALE * Math.sin(x * DEGREES_TO_RADIANS * X_AXIS_COMPRESS));
            cp[i] = segmentAdd(x, y, SEGMENT_SIZE, Color.BLACK, Color.RED);
        }
        /*adding eyes */
        cp[SEGMENT_COUNT]     = segmentAdd(cp[SEGMENT_COUNT - 1].getX() + (double) SEGMENT_SIZE / 2 - 2 * EYE_SIZE, (int) cp[SEGMENT_COUNT - 1].getY(), EYE_SIZE, Color.BLUE, Color.YELLOW);
        cp[SEGMENT_COUNT + 1] = segmentAdd(cp[SEGMENT_COUNT - 1].getX() + (double) SEGMENT_SIZE / 2 +     EYE_SIZE, (int) cp[SEGMENT_COUNT - 1].getY(), EYE_SIZE, Color.BLUE, Color.YELLOW);

        return cp;
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
    private GObject segmentAdd(double aX, double aY, int aSize, Color aColor, Color aFillColor) {
        GOval seg = new GOval(aX, aY, aSize, aSize);
        seg.setColor(aColor);
        seg.setFillColor(aFillColor);
        seg.setFilled(true);
        add(seg);
        return seg;
    }

}