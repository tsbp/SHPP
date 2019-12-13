package com.shpp.p2p.cs.bcimbal.assignment2;

/*
 * program draws optical illusion
 */

import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;


public class Assignment2Part2 extends WindowProgram {

    /* The default width and height of the window. These constants will tell Java to
     * create a window whose size is *approximately* given by these dimensions. */
    public static final int APPLICATION_WIDTH = 300;
    public static final int APPLICATION_HEIGHT = 300;

    /* specify yor radius of circle
     * note: diameter (2 * radius) must not be lager the APPLICATION_WIDTH and APPLICATION_HEIGHT */
    public static final int CIRCLE_RADIUS = 70;
    public static final int DIAM = CIRCLE_RADIUS * 2; // do not change

    /* Array of anchor points specify the upper-left corner of the
     * bounding box containing circle    {x, y}     */

    /*******************************************************************************************************************
     * Draws four black circles in corners of canvas
     * and white rectangle over them.
     * Edges of rectangle are in the centers of circles
     */
    public void run() {

        int [][] anchorPoint = getArrayOfAnchorPoints();
        /* draw four filled black circles*/
        for (int i = 0; i < 4; i++) {
            addBubble(anchorPoint[i][0], anchorPoint[i][1]);
        }
        addEmptiness(
                anchorPoint[0][0] + CIRCLE_RADIUS,                      // x coordinate
                anchorPoint[0][1] + CIRCLE_RADIUS,                      // y coordinate
                getWidth() - DIAM,                                  // width
                getHeight() - DIAM                                  // height
        );
    }

    /*******************************************************************************************************************
     * Draws a rectangle filled white with specified parameters
     *
     * @param aX      The x coordinate of the upper-left corner of rectangle.
     * @param aY      The y coordinate of the upper-left corner of rectangle.
     * @param aWidth  Width of rectangle.
     * @param aHeight Height of rectangle.
     */
    private void addEmptiness(int aX, int aY, int aWidth, int aHeight) {
        GRect rect = new GRect(aX, aY, aWidth, aHeight);
        rect.setColor(Color.WHITE);
        rect.setFillColor(Color.WHITE);
        rect.setFilled(true);
        add(rect);
    }

    /*******************************************************************************************************************
     * Draws a circle with diameter = DIAM filled black with specified parameters.
     *
     * @param aX      The x coordinate of the upper-left corner of circle.
     * @param aY      The y coordinate of the upper-left corner of circle.
     */
    private void addBubble(int aX, int aY) {
        GOval ov = new GOval(aX, aY, DIAM, DIAM);
        ov.setFilled(true);
        ov.setFillColor(Color.BLACK);
        add(ov);
    }

    /*******************************************************************************************************************
     * Fill array of anchor points according to the diameter of circle
     *
     * @return  int array of anchor points {x, y} which are specify the upper-left corner
     * of the bounding box containing circle (for four circles)
     */
    private int [][] getArrayOfAnchorPoints() {
        int [][] aPoint = new int[4][2];
        /*      x                                           y               */
        aPoint[0][0] = 0;                        aPoint[0][1] = 0;
        aPoint[1][0] = 0;                        aPoint[1][1] = getHeight() - DIAM;
        aPoint[2][0] = getWidth() - DIAM;        aPoint[2][1] = getHeight() - DIAM;
        aPoint[3][0] = getWidth() - DIAM;        aPoint[3][1] = 0;
        return aPoint;
    }
}