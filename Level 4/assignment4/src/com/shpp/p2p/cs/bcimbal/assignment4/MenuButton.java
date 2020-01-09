package com.shpp.p2p.cs.bcimbal.assignment4;

import acm.graphics.GCompound;
import acm.graphics.GFillable;
import acm.graphics.GLabel;
import acm.graphics.GRect;

import java.awt.*;

public class MenuButton extends GCompound {
    private static final int MARGIN_SIDE = 100;

    private GRect rect;

    /**
     * @param canvasWidth
     * @param x
     * @param y
     * @param color
     * @param title
     */
    public MenuButton(double canvasWidth, double x, double y, Color color, String title) {
        rect = new GRect(x, y);
        rect.setColor(color);
        rect.setSize(0, 50);
        add(rect);
        fill(color, rect);

        GLabel label = new GLabel(title);
        label.setColor(Color.WHITE);
        label.setFont(new Font("Courier New", 3, 36));
//         /* calculate position of text*/
        double _x = (canvasWidth - label.getWidth()) / 2;
        double _y = (y + rect.getHeight() / 2 + (label.getDescent()));
        add(label, _x, _y);
    }

    /**
     * @param canvasWidth
     * @param y
     */
    public void animate(double canvasWidth, double y) {

        while (rect.getWidth() < canvasWidth - MARGIN_SIDE * 2) {
            rect.setSize(rect.getWidth() + 20, rect.getHeight());
            rect.setLocation((canvasWidth - rect.getWidth()) / 2, y);
            pause(20);
        }
    }

    /**
     * @param color
     * @param obj
     */
    private void fill(Color color, GFillable obj) {
        obj.setFillColor(color);
        obj.setFilled(true);

    }
}
