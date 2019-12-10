package com.shpp.p2p.cs.bcimbal.assignment2;

/* TODO: Replace these file comments with a description of what your program
 * does.
 */
import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

public class Assignment2Part4 extends WindowProgram {


    /* The default width and height of the window. These constants will tell Java to
     * create a window whose size is *approximately* given by these dimensions. You should
     * not directly use these constants in your program; instead, use getWidth() and
     * getHeight(), which return the *exact* width and height of the window.
     */
    public static final int APPLICATION_WIDTH = 700;
    public static final int APPLICATION_HEIGHT = 500;

    public static final int SEGMENT_WIDTH = 100;
    public static final int SEGMENT_HEIGHT = 250;
    public static final String FLAG_OFF = "Flag of Azmoland";
    private static int OFFSET = 8; //to shift the baseline down a little, you need to add the  appropriate offset and not subtract it

    GRect segment[] = new GRect[3];
    Color color[] = {Color.BLACK, Color.YELLOW, Color.RED};



    public void run() {

        int centerX = getWidth()  / 2;
        int centerY = getHeight() / 2;

        int startX = centerX - 3 * (SEGMENT_WIDTH  / 2);
        int startY = centerY -     (SEGMENT_HEIGHT / 2);

        for(int i = 0; i < 3; i++){
            segment[i] = new GRect(startX + i * SEGMENT_WIDTH, startY, SEGMENT_WIDTH, SEGMENT_HEIGHT);
            segment[i].setColor(color[i]);
            segment[i].setFilled(true);
            segment[i].setFillColor(color[i]);
            add(segment[i]);
        }

        GLabel label = new GLabel(FLAG_OFF);
        label.setFont(new Font("Courier New", 3, 36)); //label.setFont("Courier New-36");
        double x = (getWidth() - label.getWidth());
        double y = (getHeight()) - OFFSET;// + label.getAscent());
        add(label, x, y);

    }

}