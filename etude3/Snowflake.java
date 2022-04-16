import javax.swing.JFrame;

/**
 * Ben Stacey
 *
 * main class for making the snowflake
 */
public class Snowflake{
	public static void main(String[]args){
		JFrame frame = new JFrame("Snowflake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SnowflakePanel panel = new SnowflakePanel();
		frame.getContentPane().add(panel);

		frame.pack();
		frame.setVisible(true);
	}
}