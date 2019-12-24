import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;


public class StartPage extends WindowProgram {

    private static final int MARGIN = 200;

    private static final int BTN_START_OFFSET = 200;
    private static final int BTN_SET_OFFSET = 270;
    private static final int BTN_EXIT_OFFSET = 350;

    GRect rectStart = new GRect(0, BTN_START_OFFSET,5,50);
    GRect rectSet = new GRect(0, BTN_SET_OFFSET,5,50);
    GRect rectExit = new GRect(0, BTN_EXIT_OFFSET,5,50);


    public void run() {

        addButton(rectStart, Color.GREEN, BTN_START_OFFSET,  "START");
        addButton(rectSet, Color.RED, BTN_SET_OFFSET, "SET");
        addButton(rectExit, Color.BLUE, BTN_EXIT_OFFSET, "EXIT");
        addMouseListeners();
        //println("234");

    }
    private GObject selectedObject = null;

    public void mouseClicked(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        selectedObject = getElementAt(x, y);
        if (selectedObject != null) {
           if(selectedObject == rectStart)      {
                println("start button");
                fill(Color.GRAY, (GFillable) selectedObject);
                rectExit.setVisible(false);
                rectSet.setVisible(false);
            }
            if(selectedObject == rectSet) println("set button");
            if(selectedObject == rectExit) println("exit button");
        }
    }

    private void addButton(GRect rect, Color color, int topOffset, String title) {
        rect.setLocation(0,topOffset);
        rect.setColor(color);
        add(rect);
        fill(color, rect);
        while(rect.getWidth() < getWidth() - MARGIN * 2) {
            rect.setSize(rect.getWidth() + 20, rect.getHeight());
            rect.setLocation((getWidth()-rect.getWidth()) / 2, rect.getY());
            pause(20);
        }

        GLabel label = new GLabel(title);
        label.setColor(Color.WHITE);
        label.setFont(new Font("Courier New", 3, 36));
        /* calculate position of text*/
        double x = (getWidth() - (int) label.getWidth()) / 2;
        double y = (topOffset + rect.getHeight() / 2 + (label.getDescent()) );
        add(label, x, y);
    }

    private void scaleX( GScalable scale) {

        scale.scale(2,1);

    }
    private void fill(Color color, GFillable obj) {
        obj.setFillColor(color);
        obj.setFilled(true);

    }


}
