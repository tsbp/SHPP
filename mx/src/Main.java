import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends WindowProgram {
    /**
     * Width and height of application window in pixels
     */
    private static double CELL_SIZE = 10;
    private static double CELLS_COUNT = 40;

    public static final int APPLICATION_WIDTH = (int) (CELL_SIZE * CELLS_COUNT);
    public static final int APPLICATION_HEIGHT = (int) (CELL_SIZE * CELLS_COUNT);

    ArrayList<AnimObject> arr = new ArrayList<>();


    public void run() {
        add(new GLabel("123"), 100, 100);
        addMouseListeners();
        boolean alt = true;
        for (int k = 0; k < CELLS_COUNT; k++) {
            int r = 0;
            if (alt) r = 1;
            for (int i = r; i < CELLS_COUNT; i += 2) {
//                add(new GRect(0, 0, CELL_SIZE, CELL_SIZE), i * CELL_SIZE, k * CELL_SIZE);
                AnimObject obj = new AnimObject(CELL_SIZE);
                arr.add(obj);
                add(obj.getObject(), i * CELL_SIZE, k * CELL_SIZE);
                //obj.startAnimation();
            }
            if (alt) alt = false;
            else alt = true;
        }

    }


    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        super.mouseMoved(mouseEvent);
        try {
            GCompound c = (GCompound) getElementAt(mouseEvent.getX(), mouseEvent.getY());
            for (AnimObject g : arr) {
                double dist = getDistance(g, mouseEvent.getX(), mouseEvent.getY());
                if(dist < 100) g.startAnimation();
                else g.stopAnimation();
//                    if(g.getObject() == c) g.startAnimation();
//                    else                    g.stopAnimation();
            }


            //(AnimObject) obj.startAnimation();
        } catch (Exception ignored) {
        }
    }

    double getDistance (AnimObject obj, double mX, double mY) {
        double x = obj.getObject().getX();
        double y = obj.getObject().getY();

        double cat1 = Math.abs(mX - x);
        double cat2 = Math.abs(mY - y);
        return Math.sqrt(cat1 * cat1 + cat2 * cat2);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        super.mouseClicked(mouseEvent);
//        AnimObject obj = new AnimObject("123");
//        GCompound a = obj.getObject();
//        add(a, mouseEvent.getX(), mouseEvent.getY());
//        arr.add(obj);
//        obj.startAnimation();

    }
}
