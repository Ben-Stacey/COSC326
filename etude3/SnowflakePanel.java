import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class SnowflakePanel extends JPanel{
	private JLabel inputLabel;
	private JTextField order;
	private DrawingPanel drawPanel;
	private JPanel controlPanel;
	private JButton go;
	private ArrayList<String> segment = new ArrayList<String>();

	public SnowflakePanel(){
		ArrayList<String> segment = new ArrayList<String>();
		controlPanel = new JPanel();

		inputLabel = new JLabel("Enter Order Number: ");
		order = new JTextField(5);

		DrawingPanel drawingPanel = new DrawingPanel();
		controlPanel.setPreferredSize(new Dimension(200, 400));

		JButton go = new JButton("GO!");
		ButtonListener listener = new ButtonListener();
		go.addActionListener(listener);
		controlPanel.add(go);

		controlPanel.add(inputLabel);
		controlPanel.add(order);

		add(controlPanel);
		add(drawingPanel);
	}

	private class DrawingPanel extends JPanel{
		public DrawingPanel(){
			setBackground(Color.white);
			setPreferredSize(new Dimension(400, 400));
		}

		public void paintComponenet(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.black);
			for(String s: segment){
				s.display(g);
			}
		}
	}

	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == "GO!"){
				segment.add(order.getText());
			}
		} 
	}
}