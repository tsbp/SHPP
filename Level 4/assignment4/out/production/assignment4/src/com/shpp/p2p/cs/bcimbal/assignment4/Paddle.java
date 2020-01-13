package com.shpp.p2p.cs.bcimbal.assignment4;

import acm.graphics.GCompound;
import acm.graphics.GOval;

import java.awt.*;

public class Paddle extends GCompound {

    private static int PADDLE_THICKNESS = 6;

    /**
     *
     * @param w  paddle width
     * @param h paddle height
     */
    public Paddle(double w, double h) {

        /*board*/
        for (int i = PADDLE_THICKNESS; i > 0; i--) {
            GOval o = new GOval(w, h);
            o.setColor(Color.BLUE);
            o.setFillColor(Color.RED);
            o.setFilled(true);
            add(o, 0, i);
        }

        /* grip */
        for (int i = 0; i < PADDLE_THICKNESS; i++) {
            GOval o = new GOval(w / 5, w / 5);
            o.setColor(Color.LIGHT_GRAY);
            o.setFillColor(Color.WHITE);
            o.setFilled(true);
            add(o, (w - w / 5) / 2, i + h);
        }
    }

}
