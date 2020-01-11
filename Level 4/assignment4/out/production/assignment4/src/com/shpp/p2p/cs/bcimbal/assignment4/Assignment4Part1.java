package com.shpp.p2p.cs.bcimbal.assignment4;

/* My attempt to create popular game Breakout
* https://en.wikipedia.org/wiki/Breakout_(video_game)
* */

import acm.graphics.*;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Assignment4Part1 extends WindowProgram {

    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 800;
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * Dimensions of game board (usually the same)
     */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;

    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

    /**
     * Width of a brick
     */
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;

    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;

    /**
     * Number of turns
     */
    private static final int NTURNS = 3;

    /* start menu settings */
    private static final String IMG_FILE = "assets/img.png";
    private static final int MENU_TOP_OFFSET = 350;
    private static final int BUTTON_SPACING = 20;

    private static final String BUTTON1_TITLE = "PLAY";
    private static final String BUTTON2_TITLE = "CONTROLS";
    private static final String BUTTON3_TITLE = "EXIT";
    private static final String[] btnTitle = {BUTTON1_TITLE, BUTTON2_TITLE, BUTTON3_TITLE};
    private static final Color[] btnColor = {Color.RED, Color.GREEN, Color.BLUE};

    /* array of wall colors */
    private static final Color[] brickColor = {
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.CYAN
    };

    /* program stages */
    private enum Mode {game, exit, rules, pause, none}
    private static Mode stage = Mode.none;

    /* Global variables */
    private static GLabel scoreLbl;
    private static GOval ball;
    private static Paddle paddle;

    private double directionX = 1;              // vx
    private double directionY = 3;              // vy
    private boolean isGamePaused = false;         // game pause control variable
    private int sticking = 0;                   // ball to paddle sticking control

    private MouseEvent me = null;

    public void run() {

        createStartMenu();
        addMouseListeners();
        addKeyListeners();

        while (stage != Mode.exit) {             // my waitForClick();
            switch (stage) {
                case game:                       // game play
                    createGameTable();
                    countdown("Ш++");
                    while (stage == Mode.game)
                        playGame();
                    createStartMenu();
                    stage = Mode.none;
                    break;

                case rules:   // show some text information for user
                    removeAll();
                    add(new Rules(), 20, 100);
                    while (stage == Mode.rules) pause(10);
                    createStartMenu();
                    break;
            }
            pause(50);
        }
        exit();
    }

    /*******************************************************************************************************************
     *
     */
    private void playGame() {
        int attempts = NTURNS;
        int gameScore = 0;
        directionX = getRandomTilt();

        scoreLabelSetText(gameScore + "");
        isGamePaused = false;
        while (attempts > 0 && stage == Mode.game) {

            /* if game paused, than wait*/
            while (isGamePaused) pause(10);

            ball.move(directionX, directionY);
            /* add game score if brick boomed */
            if (isBrickBoom(ball.getX(), ball.getY())) {
                gameScore++;
                if (gameScore >= (NBRICKS_PER_ROW * NBRICK_ROWS)) stage = Mode.none; //you win
                scoreLabelSetText("" + gameScore);
            }
            /* bad situation, ball missed, attempt failed */
            if (isBallOnFloor(ball.getY())) {
                attempts--;
                scoreLabelSetText(attempts + " attempts left");
                directionX = getRandomTilt();
                if (attempts > 0) waitForContinue();
            }

            pause(7);
        }

        /* game result */
        if (gameScore < (NBRICKS_PER_ROW * NBRICK_ROWS)) {
            scoreLabelSetText("Looser!");
            pause(HEIGHT + 2000);
        } else countdown("Winner!");
        stage = Mode.none;
    }

    /******************************************************************************************************************/
    private double getRandomTilt() {
        RandomGenerator rgen = RandomGenerator.getInstance();
        double vx = rgen.nextDouble(1.0, 3.0);
        if (rgen.nextBoolean(0.5))
            vx = -vx;
        return vx;
    }

    /******************************************************************************************************************/
    private boolean isBallOnFloor(double y) {
        return y + 2 * BALL_RADIUS >= getHeight();
    }

    /*******************************************************************************************************************
     * Pause current stage and wait for left mouse move to continue
     */
    private void waitForContinue() {
        /*  */
        stage = Mode.pause;
        pause(1000);
        double tmpX = 0;
        scoreLabelSetText("RUN");
        moveToOriginalPosition(ball);
        /* wait for left mouse movement */
        while (stage == Mode.pause) {
            if (me != null) {
                if (me.getX() < tmpX) stage = Mode.game;
                tmpX = me.getX();
            }
            pause(10);
        }
    }

    /******************************************************************************************************************/
    private void moveToOriginalPosition(GObject obj) {
        obj.setLocation((getWidth() - BALL_RADIUS * 2d) / 2, (getHeight() - BALL_RADIUS * 2d) / 2);
    }

    /******************************************************************************************************************/
    private void createGameTable() {
        removeAll();
        scoreLabelCreate();
        ball = new GOval(BALL_RADIUS * 2, BALL_RADIUS * 2);
        ball.setFillColor(Color.WHITE);
        ball.setFilled(true);
        paddle = new Paddle(PADDLE_WIDTH, PADDLE_HEIGHT); //new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
        moveToOriginalPosition(ball);
        add(ball);
        add(paddle, 0, getHeight() - PADDLE_Y_OFFSET);
        buildBricks();
    }

    /******************************************************************************************************************/
    private void scoreLabelCreate() {
        scoreLbl = new GLabel("");
        scoreLbl.setColor(Color.YELLOW);
        scoreLbl.setFont(new Font("Courier New", Font.BOLD, 30));
        scoreLbl.setVisible(true);
        add(scoreLbl, (getWidth() - scoreLbl.getWidth()) / 2.0, scoreLbl.getAscent());
    }

    /*******************************************************************************************************************
     * Score label set text
     *
     * @param str String text set to label
     */
    private void scoreLabelSetText(String str) {
        scoreLbl.setLabel(str);
        scoreLbl.setLocation((getWidth() - scoreLbl.getWidth()) / 2.0, scoreLbl.getAscent());
    }

    /*******************************************************************************************************************
     *  Build a wall of bricks
     */
    private void buildBricks() {

        double x = (getWidth() - (NBRICKS_PER_ROW * BRICK_WIDTH + (NBRICKS_PER_ROW - 1) * BRICK_SEP)) / 2d;

        /* draw NUM_ROWS rows with NUM_COLS squares with BOX_SPACING spacing between them */
        for (int i = 0; i < NBRICK_ROWS; i++) {
            /* draw row of squares */
            for (int j = 0; j < NBRICKS_PER_ROW; j++) {
                GRoundRect r = new GRoundRect(
                        x + j * (BRICK_WIDTH + BRICK_SEP),
                        BRICK_Y_OFFSET + i * (BRICK_HEIGHT + BRICK_SEP),
                        BRICK_WIDTH, BRICK_HEIGHT);
                r.setColor(brickColor[i / 2]);
                r.setFillColor(brickColor[i / 2]);
                r.setFilled(true);
                add(r);
            }
        }
    }

    /*******************************************************************************************************************
     * Method to change direction of movement if some object detected in current position (x, y)
     *
     * @param x current x coordinate of ball
     * @param y current y coordinate of ball
     * @return true if brick detected (removed)
     */
    private boolean isBrickBoom(double x, double y) {

        /* check for paddle or brick boom in four vertexes of ball bounding box*/
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++) {

                GObject obj = getElementAt(x + i * BALL_RADIUS * 2, y + j * BALL_RADIUS * 2);

                if (obj != null && obj != scoreLbl) {
                    directionY = -directionY;

                    if (obj == paddle) {
                        sticking++;                  // direction changed
                        if (sticking > 3) {          // 3 directions changes in paddle - sticking detected
                            ball.setLocation(        // set ball location behind paddle
                                    ball.getX(),
                                    (getHeight() - PADDLE_Y_OFFSET - BALL_RADIUS * 2));
                            directionX = getRandomTilt();  // get random trajectory
                            sticking = 0;
                        }
                        return false;
                    }
                    remove(obj);
                    return true;
                }
            }

        /* check wall boom */
        if (x >= (getWidth() - BALL_RADIUS * 2) || x <= 0) {
            directionX = -directionX;
        }

        /* check floor or roof boom*/
        if (y >= (getHeight() - BALL_RADIUS * 2) || y <= 0) {
            directionY = -directionY;
        }
        return false;
    }

    /******************************************************************************************************************/
    private void createStartMenu() {
        removeAll();
        setBackground(Color.BLACK);

        /* place logo */
        GImage img = new GImage(IMG_FILE);
        double x = (getWidth() - img.getWidth()) / 2;
        img.setLocation(x, 70);
        add(img);

        /* add menu buttons*/
        MenuButton[] button = new MenuButton[3];
        for (int i = 0; i < button.length; i++) {
            button[i] = new MenuButton(
                    getWidth(),
                    MENU_TOP_OFFSET + i * (MenuButton.BUTTON_HEIGHT + BUTTON_SPACING),
                    btnColor[i],
                    btnTitle[i]);
            add(button[i]);
            button[i].animate(getWidth(), MENU_TOP_OFFSET +
                    i * (MenuButton.BUTTON_HEIGHT + BUTTON_SPACING));
        }
    }

    /*******************************************************************************************************************
     * show string animation
     *
     * @param str string will be animated
     */
    private void countdown(String str) {
        Countdown cd = new Countdown();
        add(cd);
        for (int i = 0; i < str.length(); i++) {
            cd.animate(getWidth(), getHeight(), str.charAt(i));
        }
        remove(cd);
    }

    /******************************************************************************************************************/
    public void mouseMoved(MouseEvent mouseEvent) {
        me = mouseEvent;
        try {
            switch (stage) {
                case game:
                case pause:
                    /* move paddle to cursor position */
                    double positionX = mouseEvent.getX() - PADDLE_WIDTH / 2d;
                    double positionY = getHeight() - PADDLE_Y_OFFSET;
                    if (positionX < 0) positionX = 0;
                    else if (positionX > getWidth() - PADDLE_WIDTH)
                        positionX = getWidth() - PADDLE_WIDTH;
                    paddle.setLocation(positionX, positionY);
                    break;
            }
        } catch (Exception e) {
            println(":-(");
        }
    }

    /******************************************************************************************************************/
    public void keyPressed(KeyEvent keyEvent) {
        super.keyPressed(keyEvent);
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:          // ESC
                stage = Mode.none;
                break;
            case KeyEvent.VK_SPACE:          // SPACE
                isGamePaused = !isGamePaused;
                if (isGamePaused) scoreLabelSetText("Sleeping");
                else scoreLabelSetText("");
                break;
        }
    }

    /*******************************************************************************************************************
     *
     * @param mouseEvent  mouse Event
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        GObject selectedObject = getElementAt(x, y);
        try {
            String str = ((GLabel) ((GCompound) selectedObject).getElement(1)).getLabel();
            switch (str) {
                case BUTTON1_TITLE:
                    stage = Mode.game;
                    break;
                case BUTTON2_TITLE:
                    stage = Mode.rules;
                    break;
                case BUTTON3_TITLE:
                    stage = Mode.exit;
                    break;
            }
            println(stage.toString());
        } catch (Exception e) {
            println(":-(");
        }
    }

}
