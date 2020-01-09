package com.shpp.p2p.cs.bcimbal.assignment4;

import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Ball extends WindowProgram {
    /** Width and height of application window in pixels */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /** Dimensions of game board (usually the same) */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;

    /** Dimensions of the paddle */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /** Offset of the paddle up from the bottom */
    private static final int PADDLE_Y_OFFSET = 30;

    /** Number of bricks per row */
    private static final int NBRICKS_PER_ROW = 10;

    /** Number of rows of bricks */
    private static final int NBRICK_ROWS = 10;

    /** Separation between bricks */
    private static final int BRICK_SEP = 4;

    /** Width of a brick */
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /** Height of a brick */
    private static final int BRICK_HEIGHT = 8;

    /** Radius of the ball in pixels */
    private static final int BALL_RADIUS = 10;

    /** Offset of the top brick row from the top */
    private static final int BRICK_Y_OFFSET = 70;

    /** Number of turns */
    private static final int NTURNS = 3;

    GOval ball;
    GRect paddle;


    public void run() {
        ball = new GOval(BALL_RADIUS * 2, BALL_RADIUS * 2);
        paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
        add(ball, 100, 200);
        add(paddle, 0, getHeight() - PADDLE_Y_OFFSET);
        buildBricks();
        addMouseListeners();
        while(true) {
            ballMove();
            checkBoom(ball.getX(), ball.getY());
            pause(7);
        }

    }

    /*******************************************************************************************************************/
    private void checkBoom(double x, double y) {
        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++) {
                GObject obj = getElementAt(x + i * BALL_RADIUS*2, y + j * BALL_RADIUS*2);
                if (obj != null) {
                    forwardY *= -1;
                    if (obj == paddle) return;
                    remove(obj);
                }
            }
    }



    /*******************************************************************************************************************/
    GRect wall;
    private void buildBricks() {

        double x = (getWidth()  - (NBRICKS_PER_ROW * BRICK_WIDTH + (NBRICKS_PER_ROW - 1) * BRICK_SEP)) / 2;
        double y = BRICK_Y_OFFSET;//(int) (getHeight() - (NBRICK_ROWS * BRICK_HEIGHT + (NBRICK_ROWS - 1) * BRICK_SEP)) / 2;

        /* draw NUM_ROWS rows with NUM_COLS squares with BOX_SPACING spacing between them */
        for (int i = 0; i < NBRICK_ROWS; i++) {
            /* draw row of squares */
            for (int j = 0; j < NBRICKS_PER_ROW; j++) {
                GRect r = new GRect(
                        x + j * (BRICK_WIDTH + BRICK_SEP),      // x coordinate, make shift j times on (BOX_SIZE + BOX_SPACING) value
                        y + i * (BRICK_HEIGHT + BRICK_SEP),     // y coordinate, make shift i times on (BOX_SIZE + BOX_SPACING) value
                        BRICK_WIDTH, BRICK_HEIGHT);                      // square with BOX_SIZE * BOX_SIZE dimension
                r.setColor(Color.white);
                r.setFillColor(new Color(255,10,i * 25));
                r.setFilled(true);
                add(r);
            }
        }
    }

    /*******************************************************************************************************************/
    public void mouseMoved(MouseEvent mouseEvent) {
        double positionX = mouseEvent.getX() - PADDLE_WIDTH / 2;
        double positionY = getHeight() - PADDLE_Y_OFFSET;
        if(positionX < 0) positionX = 0;
        else if(positionX > getWidth() - PADDLE_WIDTH)
                positionX = getWidth() - PADDLE_WIDTH;
       // println("" + positionX);
        paddle.setLocation(positionX, positionY);
    }

    /*******************************************************************************************************************/
    //private static final double BALL_SIZE = 30.0;
    private double STEP = 2.5;
    double x = 0;
    int forwardX = 1;
    int forwardY = 1;

    private void ballMove() {
        double y1 = getYcoord(x);
        double y2 = getYcoord(x - forwardX);
        ball.move(forwardX * STEP, forwardY * forwardX * (y1 - y2));

        double tmpCoord = ball.getX();
        if (tmpCoord >= (getWidth() - BALL_RADIUS*2)) forwardX = -1;
        else if (tmpCoord <= 0) forwardX = 1;

        tmpCoord = ball.getY();
        if (tmpCoord >= getHeight() - BALL_RADIUS*2) forwardY = -1;
        else if (tmpCoord <= 0) forwardY = 1;

        if (forwardX == 1) x += STEP;
        else x -= STEP;
    }


    /*******************************************************************************************************************/
    private double getYcoord(double x) {
        return 5 * x;
    }

}
