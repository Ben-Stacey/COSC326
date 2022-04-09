import 

public class Snowflake{
	public static void main(String[]args){
		JFrame frame = new JFrame("Push Counter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		PushCounterPanel.panel = new PushCounterPanel();
		frame.getCounterPane().add(panel);

		frame.pack();
		frame.setVisible(true);
	}
}
