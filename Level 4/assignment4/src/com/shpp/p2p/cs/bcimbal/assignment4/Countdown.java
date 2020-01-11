package com.shpp.p2p.cs.bcimbal.assignment4;

import acm.graphics.GCompound;
import acm.graphics.GLabel;

import java.awt.*;

public class Countdown extends GCompound {
    private GLabel label;

    public Countdown() {
        /* prepare label*/
        label = new GLabel("");
        label.setColor(Color.BLUE);
        label.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, 36));
        label.setVisible(false);
        add(label);
    }

    /*******************************************************************************************************************
     * Increase character
     *
     * @param sc integer increase value
     * @param x  double current x coordinate
     * @param y  double current y coordinate
     */
    public void increase(int sc, double x, double y) {
        label.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, sc));
        label.setLocation((x - label.getWidth()) / 2, (y /*- label.getAscent()*/) / 2);
    }

    /*******************************************************************************************************************
     * Animation of character
     *
     * @param x double current x coordinate of character
     * @param y double current x coordinate of character
     * @param letter char character to animate
     */
    public void animate(double x, double y, char letter) {

        label.setLabel("" + letter);
        label.setVisible(true);
        int i = 200;
        label.setFont(new Font("Courier New", Font.BOLD + Font.ITALIC, i));
        while (i > 20) {
            increase(i, x, y);
            i -= 5;
            pause(10);
        }
        label.setVisible(false);
    }

}
