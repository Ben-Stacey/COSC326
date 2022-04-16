import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Ben Stacey
 * this draws the snowflake on the panel
 */
public class SnowflakePanel extends JPanel{
	private JLabel label;
	private JTextField input;
	private JButton push;
	private ArrayList<Segment> segments;
	private DrawPanel drawPanel = new DrawPanel();

	/** constructor */
	public SnowflakePanel(){
		label = new JLabel("Snowflake order: ");
		input = new JTextField(2);
		push = new JButton("Start");
		push.addActionListener(new ButtonListener());

		add(label);
		add(input);
		add(push);
		add(drawPanel);

		setBackground(Color.white);
		setPreferredSize(new Dimension(800, 900));
	}

	/** adds the segments to the arraylist */
	public void snowFlakeConstruct(Segment[] arr, ArrayList<Segment> list){
		for (Segment s: arr){
			list.add(s);
		}
	}

	/** inner class for handling the drawing panel. The drawing panel is where the snowflake is drawn*/
	private class DrawPanel extends JPanel {
		/**constuctor*/
		public DrawPanel() {
			setPreferredSize(new Dimension(800, 800));
			setBackground(Color.white);

			segments = new ArrayList<Segment>();

			double ax = 100;
			double ay = 700;
			double bx = 700;
			double by = 700;

			double length = bx - ax;
			double h = -length * Math.sqrt(3) / 2;

			double cx = 400;
			double cy = 700 + h;

			Segment s1 = new Segment(ax, ay, bx, by);
			Segment s2 = new Segment(bx, by, cx, cy);
			Segment s3 = new Segment(cx, cy, ax, ay);

			segments.add(s1);
			segments.add(s2);
			segments.add(s3);
		}

		/** paint component method that applies the graphics to the panel*/
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.black);
			g.translate(0, -100);

			for (Segment s : segments) {
				s.drawLine(g);
			}

			repaint();
		}
	}

	/** Inner class that deals with the button click*/
	private class ButtonListener implements ActionListener{
		/** when a button is clicked the actionPerformed method handles it*/
		public void actionPerformed(ActionEvent event){
			if (input.getText() == null){
				return;
			}else{
				int x = 0;
				try{
					x = Integer.parseInt(input.getText());
				}catch(Exception e){
					System.out.println("Wrong input");
					return;
				}

				DrawPanel draw = new DrawPanel();

				for (int i = 0; i < x; i++){
					ArrayList<Segment> nextIteration = new ArrayList<Segment>();

					for (Segment s : segments){
						Segment[] children = s.create();
						snowFlakeConstruct(children, nextIteration);
					}
					segments = nextIteration;
				}
			}
		}
	}
}