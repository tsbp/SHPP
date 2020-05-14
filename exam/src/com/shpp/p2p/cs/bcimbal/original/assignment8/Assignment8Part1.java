package com.shpp.p2p.cs.bcimbal.original.assignment8;

import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Assignment8Part1 extends WindowProgram {

    public static final int WIDTH_RECT_COUNT = 40;
    public static final int HEIGHT_RECT_COUNT = 20;
    public static final double RECT_SIZE = 20;

    public static final int APPLICATION_WIDTH = (int) RECT_SIZE * WIDTH_RECT_COUNT + 100;
    public static final int APPLICATION_HEIGHT = (int) RECT_SIZE * HEIGHT_RECT_COUNT + 100;

    /* List of jumping rects objects*/
    ArrayList<JumpingRect> jumpingRects = new ArrayList<>();

    /*******************************************************************************************************************
     * run
     */
    public void run() {

        createMesh();
        addMouseListeners();
        performJumpingForRectangles();

    }

    /*******************************************************************************************************************
     *
     */
    private void performJumpingForRectangles() {
        while(true) {
            for(JumpingRect jr : jumpingRects) {
                jr.jumpNextStep();
            }
            pause(30);
        }
    }

    /*******************************************************************************************************************
     * Create visual mesh in which rects are jumping
     */
    private void createMesh() {
        for (int i = 0; i < HEIGHT_RECT_COUNT; i++) {
            for (int j = 0; j < WIDTH_RECT_COUNT; j++) {
                /* mesh cell create*/
                GRect r = new GRect(j * RECT_SIZE, i * RECT_SIZE, RECT_SIZE, RECT_SIZE);
                r.setColor(Color.LIGHT_GRAY);
                /* if last row? place jumping ball objects*/
                if (i == HEIGHT_RECT_COUNT - 1) {
                    JumpingRect jRect = new JumpingRect(RECT_SIZE, RECT_SIZE);
                    r = jRect.getJRect();
                    r.setLocation(j * RECT_SIZE, i * RECT_SIZE);
                    jumpingRects.add(jRect);
                }
                add(r);
            }
        }
    }

    /*******************************************************************************************************************
     *
     */
    public void mouseClicked(MouseEvent me) {
        /* find object clicked rect belongs to*/
        for(JumpingRect jr : jumpingRects) {
            /* if found make jump */
            if(jr.getJRect() == getElementAt(me.getX(), me.getY())){
                jr.makeJump((HEIGHT_RECT_COUNT - 1) * RECT_SIZE);
            }
        }

    }
}
