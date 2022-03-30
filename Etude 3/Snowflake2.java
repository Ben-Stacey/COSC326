import java.awt.*;
import javax.swing.*;

public class Snowflake2 extends JApplet{
	int level = 0;

	public void init(){
		setSize(640, 480);
		String levelStr = JOptionPane.showInputDialog("Enter the recursion depth: ");
		level = Integer.parseInt(levelStr);
	}

	public void paint(Graphics g){
		Point pointOne = new Point(120, 320);
		Point pointTwo = new Point(440, 320);	// reusing Points from example program * 2;
		Point pointThree = new Point(280, 40);

		drawSnowflake(g, level, pointOne, pointTwo, pointThree);
	}

	private void drawSnowflake(Graphics g, int lev, Point p1, Point p2, Point p3){
		drawSegment(g, lev, p1, p2);	// draw 3 lines to make a triangle
		drawSegment(g, lev, p2, p3);
		drawSegment(g, lev, p3, p1);
	}
	
	private void drawSegment(Graphics g, int lev, Point pOne, Point pTwo){
		if (lev == 0){
			g.drawLine(pOne.x, pOne.y, pTwo.x, pTwo.y);
			}
		if (lev >= 1){
			Point distance = new Point( (pTwo.x-pOne.x)/3, (pTwo.y-pOne.y)/3 );
            Point pA = new Point( pOne.x+distance.x, pOne.y+distance.y);
            Point pB = new Point( pTwo.x-distance.x, pTwo.y-distance.y);
			double sin60 = -0.866025403784438646763723170752936183471402626905190;
            Point pTip = new Point(
         	pA.x + (int)(distance.x* 0.5 + distance.y*sin60),
         	pA.y + (int)(distance.y* 0.5 - distance.x*sin60)
         );
		
			drawSegment(g, lev - 1, pOne, pA);	// if level 1 or higher,
			drawSegment(g, lev - 1, pA, pTip);	// recursively call self 4 times
			drawSegment(g, lev - 1, pTip, pB);	
			drawSegment(g, lev - 1, pB, pTwo);	
		}
	}
}
