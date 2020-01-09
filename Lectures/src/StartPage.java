import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

public class StartPage extends WindowProgram {


    private static final String IMG_FILE = "images/img.png";

    public static final int CURSOR_SIZE = 30;

    private static final int BTN_START_OFFSET = 200;
    private static final int BTN_SET_OFFSET = 270;
    private static final int BTN_EXIT_OFFSET = 350;

    private static final int[] btnTopOffset = {
            BTN_START_OFFSET,
            BTN_SET_OFFSET,
            BTN_EXIT_OFFSET
    };
    private static final String[] btnTitle = {"START", "SET", "EXIT"};
    private static final Color[] btnColor = {Color.RED, Color.GREEN, Color.BLUE};

    MenuButton[] button = new MenuButton[3];
    Cursor curs;
    Countdown cd;
    boolean game = false;

    enum Mode {game, exit, settings, none}

    Mode mode = Mode.none;
    GImage img;

    public void run() {

        createStartMenu();
        addMouseListeners();
        addKeyListeners();

        while (true) {
            switch (mode) {
                case game:
                    menuSetVisible(false);
                    countdown();
                    mode = Mode.none;
                    break;

                case exit:
                    this.exit();
                    break;
            }
            pause(50);
        }
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
                    (getWidth()) / 2,
                    btnTopOffset[i],
                    btnColor[i],
                    btnTitle[i]);
            add(button[i]);
            button[i].animate(getWidth(), btnTopOffset[i]);
        }

       // if(cd != null) {
            cd = new Countdown();
            add(cd);
        //}

    }

    /*******************************************************************************************************************
     *
     */
    private void countdown() {
//        cd.animate(getWidth(), getHeight(), "5");
//        cd.animate(getWidth(), getHeight(), "4");
        cd.animate(getWidth(), getHeight(), "3");
        cd.animate(getWidth(), getHeight(), "2");
        cd.animate(getWidth(), getHeight(), "1");
    }

    /*******************************************************************************************************************
     *
     */
    private void menuSetVisible(boolean b) {
        img.setVisible(b);
        for (int i = 0; i < button.length; i++) {
            pause(200);
            button[i].setVisible(b);
        }
    }

    /*******************************************************************************************************************
     *
     */
//    public void mouseMoved(MouseEvent mouseEvent) {
//        curs.moveTo(mouseEvent.getX(), mouseEvent.getY());
//    }

    /*******************************************************************************************************************
     *
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        super.keyPressed(keyEvent);
        // println(keyEvent.toString());
        if (keyEvent.getKeyChar() == 27) {
            menuSetVisible(true);
        }
    }

    private GObject selectedObject = null;

    /*******************************************************************************************************************
     *
     * @param mouseEvent
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        selectedObject = getElementAt(x, y);

        if (selectedObject != null) {
//            println(selectedObject.toString());
            if (selectedObject == button[1]) {
                println("set button");
            } else if (selectedObject == button[2]) {
                println("exit button");
                mode = Mode.exit;
            } else if (selectedObject == button[0]) {
                println("start button");
                mode = Mode.game;

            }
            println(mode.toString());
        }
    }

    private void scaleX(GScalable scale) {
        scale.scale(2, 1);
    }
}

/**********************************************************************************************************************/
class Countdown extends GCompound {
    private GLabel label;

    public Countdown() {
        label = new GLabel("");
        label.setColor(Color.BLUE);
        label.setFont(new Font("Courier New", 3, 36));
        label.setVisible(false);
        add(label);

    }

    public void scaleA(int sc, double x, double y) {
        label.setFont(new Font("Courier New", 3, sc));
        label.setLocation((x - label.getWidth()) / 2, (y /*- label.getAscent()*/) / 2);
    }

    public void animate(double x, double y, String str) {

        label.setLabel(str);
        label.setVisible(true);
        int i = 200;
        label.setFont(new Font("Courier New", 3, i));
        while (i > 0) {
            scaleA(i, x, y);
            i -= 5;
            pause(20);
        }
        label.setVisible(false);
    }


}

/**********************************************************************************************************************/
class Cursor extends GCompound {

    public static final int CURSOR_SIZE = 30;
    GOval curs;

    public Cursor() {
        curs = new GOval(0, 0, CURSOR_SIZE, CURSOR_SIZE);
        add(curs);
    }

    public void moveTo(double x, double y) {
        curs.setLocation(x - CURSOR_SIZE / 2, y - CURSOR_SIZE / 2);
    }
}

/**********************************************************************************************************************/
class MenuButton extends GCompound {

    private static final int MARGIN_SIDE = 200;

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
