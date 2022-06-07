import javax.swing.JFrame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Ben Stacey
 *
 * main class for making the snowflake
 */
public class Snowflake{
	static JFrame frame;
	static SnowflakePanel panel;

	public static void main(String[]args){
		panel = new SnowflakePanel(500, 500, 0);
		frame = new JFrame("Snowflake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);

		frame.getContentPane().addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				frame.getContentPane().remove(panel);

				double w = frame.getWidth();
				double h = frame.getHeight();
				int i = panel.getInputNum();

				panel = new SnowflakePanel(w, h, i);

				frame.getContentPane().add(panel);
				frame.validate();
			}
		});
	}
}