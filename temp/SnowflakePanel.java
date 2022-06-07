import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Ben Stacey
 * This draws the snowflake from the segments provided
 */
public class SnowflakePanel extends JPanel{
	private JLabel label;
	private JTextField input;
	private JButton push;
	private ArrayList<Segment> segments;
	private DrawPanel drawPanel;

	private double www;
	private double hhh;
	private int inputNum;

	/** constructor*/
	public SnowflakePanel(double wx, double hx, int ix){
		www = wx;
		hhh = hx;
		inputNum = ix;

		if (www > hhh) www = hhh;
		else hhh = www;

		label = new JLabel("Snowflake order: ");
		input = new JTextField(2);
		push = new JButton("Go!");
		push.addActionListener(new ButtonListener());

		drawPanel = new DrawPanel();

		add(label);
		add(input);
		add(push);
		add(drawPanel);

		setBackground(Color.white);
		setPreferredSize(new Dimension((int)Math.round(www), (int)Math.round(hhh)));
	}

	/** adds segments to arraylist.*/
	public void addAll(Segment[] arr, ArrayList<Segment> list){
		for (Segment s: arr){
			list.add(s);
		}
	}

	public int getInputNum(){
		return inputNum;
	}

	/** when a button is clicked it triggers the snowflake to be made */
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if (input.getText() == null){
				return;
			}else{
				int x = 0;
				try{
					x = Integer.parseInt(input.getText());
					inputNum = x;
				}catch(Exception e){
					System.out.println("Wrong number");
					return;
				}

				drawPanel = new DrawPanel();
			}
		}
	}

	/** draws the snowflake*/
	private class DrawPanel extends JPanel {
		/** constructor */
		public DrawPanel() {
			setPreferredSize(new Dimension((int)Math.round(www), (int)Math.round(hhh)));
			setBackground(Color.white);

			segments = new ArrayList<Segment>();

			double ax = (1d/5d) * www;
			double ay = (4d/5d) * hhh;
			double bx = (4d/5d) * www;
			double by = (4d/5d) * hhh;

			double length = bx - ax;
			double h = -length * Math.sqrt(3) / 2;

			double cx = (1d/2d) * www;
			double cy = (4d/5d) * hhh + h;

			Segment s1 = new Segment(ax, ay, bx, by);
			Segment s2 = new Segment(bx, by, cx, cy);
			Segment s3 = new Segment(cx, cy, ax, ay);

			segments.add(s1);
			segments.add(s2);
			segments.add(s3);

			if (inputNum == 0){
			}else if (inputNum > 0) {
				inputNum--;
				for (int i = 0; i < inputNum; i++) {
					ArrayList<Segment> nextIteration = new ArrayList<Segment>();

					for (Segment s : segments) {
						Segment[] children = s.create();
						addAll(children, nextIteration);
					}
					segments = nextIteration;
				}
			}
		}

		/** paint component method that applies the graphics to the panel */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.black);

			double temp = 1d/5d * hhh;
			int x = (int)temp;
			g.translate(0, -x);

			for (Segment s : segments) {
				s.drawLine(g);
			}

			repaint();
		}
	}
}