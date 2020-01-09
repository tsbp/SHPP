package com.shpp.p2p.cs.bcimbal.assignment4;

import acm.graphics.GCompound;
import acm.graphics.GLabel;

import java.awt.*;

public class Countdown extends GCompound {
    private GLabel label;

    public Countdown() {
        label = new GLabel("");
        label.setColor(Color.BLUE);
        label.setFont(new Font("Courier New", 3, 36));
        label.setVisible(false);
        add(label);
    }

    /*******************************************************************************************************************
     *
     * @param sc
     * @param x
     * @param y
     */
    public void scaleA(int sc, double x, double y) {
        label.setFont(new Font("Courier New", 3, sc));
        label.setLocation((x - label.getWidth()) / 2, (y /*- label.getAscent()*/) / 2);
    }

    /*******************************************************************************************************************
     *
     * @param x
     * @param y
     * @param str
     */
    public void animate(double x, double y, String str) {

        label.setLabel(str);
        label.setVisible(true);
        int i = 200;
        label.setFont(new Font("Courier New", 3, i));
        while (i > 0) {
            scaleA(i, x, y);
            i -= 5;
            pause(10);
        }
        label.setVisible(false);
    }

}
