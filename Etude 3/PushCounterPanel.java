
public class PushCounterPanel extends JPanel{
	private int count;
	private JButton push;
	private JLabel label;

	public PushCounterPanel(){
		count = 0;
		push = new JButton("Push me");
		push.addActionListener(new ButtonListener());

		label = new JLabel("Pushes: " + count);

		add(push);
		add(label);

		setBackground(Color.cyan);
		setPreferredSize(new Dimension(300, 40));
	}

	private class ButtonListener implements addActionListener{
		public void actionPerformed(ActionEvent event){
			count++;
			label.setText("Pushes " + count);
		}
	}
}
