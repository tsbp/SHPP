package com.shpp.p2p.cs.bcimbal.assignment4;

import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;
import javafx.scene.Cursor;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Assignment4Part1 extends WindowProgram {

    /** Width and height of application window in pixels */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /** Dimensions of game board (usually the same) */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;

    /** Dimensions of the paddle */
    private static final int PADDLE_WIDTH = 200;
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


    private static final String IMG_FILE = "images/img.png";


    private static final int BTN_START_OFFSET = 300;
    private static final int BTN_SET_OFFSET = 370;
    private static final int BTN_EXIT_OFFSET = 450;

    private static final int[] btnTopOffset = {
            BTN_START_OFFSET,
            BTN_SET_OFFSET,
            BTN_EXIT_OFFSET
    };
    private static final String[] btnTitle = {"START", "SET", "EXIT"};
    private static final Color[] btnColor = {Color.RED, Color.GREEN, Color.BLUE};

    MenuButton[] button = new MenuButton[3];
    enum Mode {game, exit, settings, wait, none}

    Mode mode = Mode.none;
    GImage img;
    int gameScore = 0;

    GOval ball;
    Paddle paddle;

    public void run() {

        createStartMenu();
        addMouseListeners();
        addKeyListeners();

        while (true) {
            switch (mode) {
                case game:
                    removeAll();
                    createGameTable();
                    countdown();
                    playGame();
                    removeAll();
                    createStartMenu();
                    mode = Mode.none;
                    break;

                case exit:
                    exit();
                    break;
            }
            pause(50);
        }
    }

    /*******************************************************************************************************************/
    private void playGame() {
        while(mode == Mode.game) {
            int attempts = NTURNS;
            while(attempts > 0 && mode == Mode.game)
            {
                ballMove();
                checkBoom(ball.getX(), ball.getY());
                if(gameScore >= (NBRICKS_PER_ROW * NBRICK_ROWS)) mode = Mode.none; //you win
                if(isBallOnFloor())
                {
                    attempts--;
                    sccoreLabelSetText(attempts + " attempts left");
                    if(attempts > 0) waitForContinue();
                }
                pause(1);
            }
            if(gameScore < (NBRICKS_PER_ROW * NBRICK_ROWS)) sccoreLabelSetText("You loose!");
            else sccoreLabelSetText("You win!");
            pause(2000);
            mode = Mode.none;
        }
    }

    /*******************************************************************************************************************/
    private boolean isBallOnFloor() {
        return ball.getY() + 2 * BALL_RADIUS >= getHeight();
    }

    /*******************************************************************************************************************/
    private void waitForContinue() {
        /* wait for left mouse move to continue */
        mode = Mode.wait;
        pause(1000);
        double tmpX = 0;
        sccoreLabelSetText("RUN");
        ball.setLocation((getWidth() - BALL_RADIUS * 2) / 2, (getHeight() - BALL_RADIUS * 2) / 2);
        while(mode == Mode.wait)                    {
            if(me != null && me.getX() < tmpX) mode = Mode.game;
            tmpX = me.getX();
            pause(10);
        }
    }

    /*******************************************************************************************************************/
    private void sccoreLabelSetText(String str) {
        scoreLbl.setLabel(str);
        scoreLbl.setLocation((getWidth() - scoreLbl.getWidth()) / 2.0, scoreLbl.getAscent());
    }

    /*******************************************************************************************************************/
    private void createGameTable() {
        gameScore = 0;
        scoreLabelCreate();
        ball = new GOval(BALL_RADIUS * 2, BALL_RADIUS * 2);
        ball.setFillColor(Color.WHITE);
        ball.setFilled(true);
        paddle = new Paddle(PADDLE_WIDTH, PADDLE_HEIGHT);//new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
        add(ball, 100, 200);
        add(paddle, 0, getHeight() - PADDLE_Y_OFFSET);
        buildBricks();
        sccoreLabelSetText(gameScore + "");
    }

    /*******************************************************************************************************************/
    GLabel scoreLbl;
    private void scoreLabelCreate() {
            scoreLbl = new GLabel(gameScore + "");
            scoreLbl.setColor(Color.RED);
            scoreLbl.setFont(new Font("Courier New", 3, 36));
            scoreLbl.setVisible(true);
            add(scoreLbl, (getWidth() - scoreLbl.getWidth()) / 2.0, scoreLbl.getAscent());
    }

    /*******************************************************************************************************************/
    private void checkBoom(double x, double y) {
        for(int i = 0; i < 2; i++)
            for(int j = 0; j < 2; j++) {
                GObject obj = getElementAt(x + i * BALL_RADIUS*2, y + j * BALL_RADIUS*2);
                if (obj != null && obj != scoreLbl) {
                    forwardY *= -1;
                    if (obj == paddle) return;
                    gameScore++;
                    sccoreLabelSetText("" + gameScore);
                    remove(obj);
                    return;
                }
            }

        double tmpCoord = ball.getX();
        if (tmpCoord >= (getWidth() - BALL_RADIUS * 2) || tmpCoord <= 0) {
            forwardX *= -1;
        }
        tmpCoord = ball.getY();
        if (tmpCoord >= (getHeight() - BALL_RADIUS * 2) || tmpCoord <= 0) {
            forwardY *= -1;
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
    MouseEvent me = null;
    public void mouseMoved(MouseEvent mouseEvent) {
        me = mouseEvent;
        try {
            switch (mode){
                case game:
                    double positionX = mouseEvent.getX() - PADDLE_WIDTH / 2;
                    double positionY = getHeight() - PADDLE_Y_OFFSET;
                    if (positionX < 0) positionX = 0;
                    else if (positionX > getWidth() - PADDLE_WIDTH)
                        positionX = getWidth() - PADDLE_WIDTH;
                    paddle.setLocation(positionX, positionY);
                    //me = mouseEvent;
                    break;

                case wait:
                   // me = mouseEvent;
                    break;
            }
        }
        catch (Exception e) {}
    }

    /*******************************************************************************************************************/
    int forwardX = 1;
    int forwardY = 1;

    private void ballMove() {

        double STEP = 0.5;
        double y1 = getYcoord(ball.getX());
        double y2 = getYcoord(ball.getX() + forwardX * STEP);

        ball.move(forwardX * STEP, forwardY * forwardX * (y1 - y2));

//        double tmpCoord = ball.getX();
//        if (tmpCoord >= (getWidth() - BALL_RADIUS * 2) || tmpCoord <= 0) {
//            forwardX *= -1;
//        }
//        tmpCoord = ball.getY();
//        if (tmpCoord >= (getHeight() - BALL_RADIUS * 2) || tmpCoord <= 0) {
//            forwardY *= -1;
//        }
    }


    /*******************************************************************************************************************/
    private static final double NAKLON = 2.2d;
    private double getYcoord(double x) {
        return NAKLON * x;
    }


    /*******************************************************************************************************************
     *
     */
    private void createStartMenu() {
        setBackground(Color.lightGray);

        img = new GImage(IMG_FILE);
        double x = (getWidth() - img.getWidth()) / 2;
        //img.setSize(getWidth(), getHeight());
        img.setLocation(x, 5);
        add(img);

        for (int i = 0; i < button.length; i++) {
            button[i] = new MenuButton(
                    getWidth(),
                    (getWidth()) / 2.0,
                    btnTopOffset[i],
                    btnColor[i],
                    btnTitle[i]);
            add(button[i]);
            button[i].animate(getWidth(), btnTopOffset[i]);
        }
    }

    /*******************************************************************************************************************
     *
     */
    private static final String COUNTDOWN_STRING = "Ш++";
    private void countdown() {
        Countdown cd = new Countdown();
        add(cd);
        for(int i = 0; i < COUNTDOWN_STRING.length(); i++) {
            Character ch = COUNTDOWN_STRING.charAt(i);
            cd.animate(getWidth(), getHeight(), ch.toString());
        }
        remove(cd);
    }

    /*******************************************************************************************************************
     *
     */
    private void menuSetVisible(boolean b) {
        img.setVisible(b);
        for (MenuButton menuButton : button) {
            pause(200);
            menuButton.setVisible(b);
        }
    }

    /*******************************************************************************************************************
     *
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        super.keyPressed(keyEvent);
        if (keyEvent.getKeyChar() == 27) {
            menuSetVisible(true);
            mode = Mode.none;
        }
    }

    /*******************************************************************************************************************
     *
     * @param mouseEvent
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        GObject selectedObject = getElementAt(x, y);

        if (selectedObject != null) {
            if (selectedObject == button[1]) {
                mode = Mode.settings;
            }
            else if (selectedObject == button[2]) {
                mode = Mode.exit;
            }
            else if (selectedObject == button[0]) {
                mode = Mode.game;
            }
            println(mode.toString());
        }
    }

}
