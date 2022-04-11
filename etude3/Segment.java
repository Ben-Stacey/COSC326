import java.awt.*;

public class Segment{
    protected double ax, ay, bx, by;

    public Segment(double a1, double a2, double b1, double b2){
        ax = a1;
        ay = a2;
        bx = b1;
        by = b2;
    }

    public Segment[] create() {
        Segment[] pieces = new Segment[4];

        double[] working = sub(bx, by, ax, ay);
        working = div(working[0], working[1], 3);

        double[] b1 = add(ax, ay, working[0], working[1]);
        pieces[0] = new Segment(ax, ay, b1[0], b1[0]);

        double[] a1 = sub(bx, by, working[0], working[1]);
        pieces[3] = new Segment (a1[0], a1[1], bx, by);

        working = rotate(working[0], working[1], Math.PI / 3);
        double[] c1 = add(b1[0], b1[1], working[0], working[2]);
        pieces[1] = new Segment(b1[0], b1[1], c1[0], c1[1]);
        pieces[2] = new Segment(c1[0], c1[1], a1[0], a1[1]);

        return pieces;
    }

    public void draw(Graphics g){
        g.setColor(Color.black);
        g.drawLine((int)ax, (int)ay, (int)bx, (int)by);
    }

    public double[] add(double x1, double x2, double y1, double y2){
        double x = x1 + y1;
        double y = x2 + y2;
        double[] results = {x, y};

        return results;
    }

    public double[] sub(double x1, double x2, double y1, double y2){
        double x = x1 - y1;
        double y = x2 - y2;
        double[] results = {x, y};

        return results;
    }

    public double[] div(double x1, double x2, double div){
        double x = x1 / div;
        double y = x2 / div;
        double[] results = {x, y};

        return results;
    }

    public double[] rotate(double x, double y, double angle) {
        double temp = x;
        x = x * Math.cos(angle) - y * Math.sin(angle);
        y = temp * Math.sin(angle) + y * Math.cos(angle);
        double[] results = {x, y};

        return results;
    }
}
