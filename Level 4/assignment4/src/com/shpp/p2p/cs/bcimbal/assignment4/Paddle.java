package com.shpp.p2p.cs.bcimbal.assignment4;

import acm.graphics.GCompound;
import acm.graphics.GOval;

import java.awt.*;

public class Paddle extends GCompound {

    public Paddle(double w, double h) {

        /*board*/
        for (int i = 8; i > 0; i--) {
            GOval o = new GOval(w, h);
            o.setColor(Color.BLUE);
            o.setFillColor(Color.RED);
            o.setFilled(true);
            add(o, 0, i);
        }

        /* grip */
        for (int i = 0; i < 8; i++) {
            GOval o = new GOval(w / 5, w / 5);
            o.setColor(Color.black);
            o.setFillColor(Color.WHITE);
            o.setFilled(true);
            add(o, (w - w / 5) / 2, i + 10);
        }
    }

}
