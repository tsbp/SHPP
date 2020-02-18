import acm.graphics.*;
import acm.util.RandomGenerator;

import java.util.Timer;
import java.util.TimerTask;

import java.awt.*;
import java.util.Timer;

public class AnimObject extends GObject {
    //GLabel label;
//    GRect rect;

    boolean isAnimated = false;
    GCompound object = new GCompound();

    Timer timer = new Timer();
    double size, defSize;


    public AnimObject(double size) {
        GRect rect = new GRect(0, 0, size, size);
        Color col = Color.BLACK;//RandomGenerator.getInstance().nextColor();
        rect.setColor(Color.WHITE);
        rect.setFilled(true);
        rect.setFillColor(col);
        object.add(rect);
        this.size = size;
        defSize = size;


        object.add(rect);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isAnimated) animate();
            }
        }, 0, 30);
    }

    public GCompound getObject() {
        return object;
    }

    double step = -1;

    private void animate() {
        GRect r = (GRect) object.getElement(0);
        //r.setFillColor(RandomGenerator.getInstance().nextColor());
        this.size += step;
        r.setSize(this.size, this.size);
        r.move(-step/2,  -step/2);
        if(this.size < 5 || this.size >= this.defSize ){
            step = -step;
            r.setFillColor(RandomGenerator.getInstance().nextColor());
        }
    }

    public void startAnimation() {
                    isAnimated = true;


    }

    public void stopAnimation() {
            isAnimated = false;
//        GRect r = (GRect)object.getElement(0);
//        r.setSize(defSize,defSize);
//        r.setLocation((int)(r.getX() / defSize) * defSize, (int)(r.getY() / defSize) * defSize);

    }

    @Override
    public void paint(Graphics graphics) {

    }

    @Override
    public GRectangle getBounds() {
        return null;
    }
}

