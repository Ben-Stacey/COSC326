import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class SnowflakePanel extends JPanel{
	private JLabel label;
	private JTextField input;
	private JButton push;
	private ArrayList<Segment> segments;
	private DrawPanel drawPanel = new DrawPanel();

	public SnowflakePanel(){
		label = new JLabel("Enter Order Number: ");
		input = new JTextField(2);
		push = new JButton("GO!");

		push.addActionListener(new ButtonListener());

		add(push);
		add(label);
		add(input);
		add(drawPanel);

		setBackground(Color.white);
		setPreferredSize(new Dimension(800, 900));
	}

	public void addAll(Segment[] arr, ArrayList<Segment> list){
		for(Segment s: arr){
			list.add(s);
		}
	}

	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(input.getText() == null){
				return;
			}else{
				int x = 0;
				try{
					x = Integer.parseInt(input.getText());
				}catch(Exception e){
					System.out.println("Incorrect input");
					return;
				}

				DrawPanel draw = new DrawPanel();

				for(int i = 0; i < x; i++){
					ArrayList<Segment> nextInteraction = new ArrayList<Segment>();

					for(Segment s : segments){
						Segment[] children = s.create();
						addAll(children, nextInteraction);
					}
					segments = nextInteraction;
				}
			}
		} 
	}

	private class DrawPanel extends JPanel{
		public DrawPanel() {
			setPreferredSize(new Dimension(800, 900));
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

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.black);
			g.translate(0, -100);

			for(Segment s : segments){
				s.draw(g);
			}

			repaint();
		}
	}
}