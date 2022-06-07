import java.math.*;
import java.awt.*;
import javax.swing.*;

/**
 * Ben Stacey
 * TThis makes the lines for the snowflake
 */
public class Segment {
    private double ax, ay, bx, by;

    /** Constructor */
    public Segment(double a1, double a2, double b1, double b2){
        ax = a1;
        ay = a2;
        bx = b1;
        by = b2;
    }

    /** this method draws the line*/
    public Segment[] create(){
        Segment[] pieces = new Segment[4];

        double[] working = minus(bx, by, ax, ay);
        working = split(working[0], working [1], 3);

        double[] b1 = addition(ax, ay, working[0], working[1]);
        pieces[0] = new Segment(ax, ay, b1[0], b1[1]);

        double[] a1 = minus(bx, by, working[0], working[1]);
        pieces[3] = new Segment(a1[0], a1[1], bx, by);

        working = move(working[0], working[1], Math.PI / 3);
        double[] c1 = addition(b1[0], b1[1], working[0], working[1]);
        pieces[1] = new Segment(b1[0], b1[1], c1[0], c1[1]);
        pieces[2] = new Segment(c1[0], c1[1], a1[0], a1[1]);

        return pieces;
    }

    /** this method makes the segments of the snow flake*/
    public void draw(Graphics g){
        g.setColor(Color.black);
        g.drawLine((int)ax, (int)ay, (int)bx, (int)by);
    }

    /** called by the create method to do the task required*/
    public double[] addition(double x1, double x2, double y1, double y2){
        double x = x1 + y1;
        double y = x2 + y2;
        double[] result = {x, y};

        return result;
    }

    /** called by the create method to do the task required.*/
    public double[] minus(double x1, double x2, double y1, double y2){
        double x = x1 - y1;
        double y = x2 - y2;
        double[] result = {x, y};

        return result;
    }

    /** called by the create method to do the task required.*/
    public double[] split(double x1, double x2, double div){
        double x = x1 / div;
        double y = x2 / div;
        double[] result = {x, y};

        return result;
    }

    /** called by the create method to do the task required.*/
    public double[] move(double x, double y, double angle) {
        double temp = x;
        x = x * Math.cos(angle) - y * Math.sin(angle);
        y = temp * Math.sin(angle) + y * Math.cos(angle);
        double[] result = {x, y};

        return result;
    }
}
