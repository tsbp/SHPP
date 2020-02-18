package com.shpp.p2p.cs.bcimbal.assignment8;

import acm.graphics.GRect;
import acm.util.RandomGenerator;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class JumpingRect extends GRect {

    /* jumping rectangle */
    private GRect jRect;
    /* variable to perform jump*/
    private boolean makeJump = false;
    /* height to jump */
    private double jumpHeight;
    /* my run() to perform jumping*/
    private Timer myRun = new Timer();

    public JumpingRect(double width, double height) {
        super(width, height);
        jRect = new GRect(0, 0, width, height);
        jRect.setColor(Color.WHITE);
        jRect.setFilled(true);
        jRect.setFillColor(Color.BLACK);
        myRun.schedule(new TimerTask() {

            double direction = -1; // -1 moving up
            double step = 15;

            @Override
            public void run() {
                /* make one jump*/
                if (makeJump) {
                    double y = jRect.getY();
                    jRect.move(0, direction * step);
                    if (direction == -1) step -= (step / 30);
                    else step += step / 30;

                    /* touch top */
                    if (direction == -1 && y <= 0) {
                        direction = -direction;
                        jRect.setLocation(jRect.getX(), 0);
                    }

                    /* touch bottom*/
                    if (direction == 1 && y > jumpHeight) {
                        makeJump = false;
                        jRect.setLocation(jRect.getX(), jumpHeight);
                        direction = -direction;
                        step = 15;
                    }
                }
            }
        }, 0, 30);
    }

    /*******************************************************************************************************************
     *
     * @return GRect object
     */
    public GRect getJRect() {
        return jRect;
    }

    /*******************************************************************************************************************
     * Method to perform jump
     *
     * @param jumpHeight double the height of jump
     */
    public void makeJump(double jumpHeight) {
        makeJump = true;
        this.jumpHeight = jumpHeight;
    }


}
