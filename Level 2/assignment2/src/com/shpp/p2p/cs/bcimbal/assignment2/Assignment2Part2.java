package com.shpp.p2p.cs.bcimbal.assignment2;

import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;
import java.awt.*;


public class Assignment2Part2 extends WindowProgram {

    public static final int APPLICATION_WIDTH = 1000;
    public static final int APPLICATION_HEIGHT = 300;

    public static final int CIRCLE_RADIUS = 25;
    public static final int DIAM = CIRCLE_RADIUS * 2;

    int centr [][] = {
            {0, 0},
            {0, 0},
            {0, 0},
            {0, 0}
    };

    //==================================================================================================================
    //
    // =================================================================================================================
    public void run() {
        fillArrayOfCenters();
        addBubbles();
        addEmptiness();
    }

    //==================================================================================================================
    //
    // =================================================================================================================
    private void addEmptiness() {
        GRect rect = new GRect(
                centr[0][0] + CIRCLE_RADIUS, centr[0][1] + CIRCLE_RADIUS,
                   getWidth() - DIAM,                      getHeight() - DIAM
        );
        rect.setColor(Color.WHITE);
        rect.setFillColor(Color.WHITE);
        rect.setFilled(true);
        add(rect);
    }

    //==================================================================================================================
    //
    // =================================================================================================================
    private void addBubbles() {
        for (int i = 0; i < 4; i++) {
            GOval ov = new GOval(centr[i][0], centr[i][1], DIAM, DIAM);
            ov.setFilled(true);
            ov.setFillColor(Color.BLACK);
            add(ov);
        }
    }

    //==================================================================================================================
    //
    // =================================================================================================================
    private void fillArrayOfCenters() {
        centr[0][0] = 0;                  centr[0][1] = 0;                              //
        centr[1][0] = 0;                  centr[1][1] = getHeight() - DIAM;             //
        centr[2][0] = getWidth() - DIAM;  centr[2][1] = getHeight() - DIAM;             //
        centr[3][0] = getWidth() - DIAM;  centr[3][1] = 0;                              //
    }
}