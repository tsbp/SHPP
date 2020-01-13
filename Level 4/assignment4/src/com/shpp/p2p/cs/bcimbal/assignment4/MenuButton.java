package com.shpp.p2p.cs.bcimbal.assignment4;

import acm.graphics.GCompound;
import acm.graphics.GFillable;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.*;

public class MenuButton extends GCompound {

    /* Button settings */
    private static final int BUTTON_WIDTH = 250;
    public static final int BUTTON_HEIGHT = 40;
    private static final int BUTTON_SCALE_STEP = 20;

    /* button components */
    private GRect rect;
    private GLabel label;

    /**
     *  create menu button
     *
     * @param canvasWidth parameter to set button position at the center of canvas
     * @param y vertical coordinate of button
     * @param color button color
     * @param title button title
     */
    public MenuButton(double canvasWidth, double y, Color color, String title) {

        rect = new GRect((canvasWidth - BUTTON_WIDTH) / 2, y);//(x, y);
        rect.setColor(color);
        rect.setSize(0, BUTTON_HEIGHT);
        add(rect);
        fill(color, rect);

        label = new GLabel(title);
        label.setColor(Color.WHITE);
        label.setVisible(false);
        label.setFont(new Font("Courier New", Font.BOLD, 28));
        /* calculate position of text*/
        double _x = (canvasWidth - label.getWidth()) / 2;
        double _y = (y + rect.getHeight() / 2 + (label.getDescent()));
        add(label, _x, _y);
    }

    /*******************************************************************************************************************
     * Animate button creation
     *
     * @param canvasWidth parameter to set button position at the center of canvas
     * @param y vertical coordinate of button
     */
    public void animate(double canvasWidth, double y) {
        /*animate button by adding to horizontal size of it*/
        while (rect.getWidth() < BUTTON_WIDTH) {
            rect.setSize(rect.getWidth() + BUTTON_SCALE_STEP, rect.getHeight());
            rect.setLocation((canvasWidth - rect.getWidth()) / 2, y);
            pause(20);
        }
        label.setVisible(true);
    }

    /*******************************************************************************************************************
     * Fill GObject to color
     *
     * @param color fill color
     * @param obj  object to fill
     */
    private void fill(Color color, GFillable obj) {
        obj.setFillColor(color);
        obj.setFilled(true);

    }
}
