import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

public class Screen extends JPanel {

	private JFrame frame;
	
	public Screen() {
		initializeSwing();
	}
	
	private void initializeSwing() {
		frame = new JFrame("Bad RPG");
		frame.setContentPane(this);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//frame.setUndecorated(true);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		Screen screen = new Screen();
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(100, 100, 50, 50);
		
	}
	
	private void drawBlock(Graphics g, Block b) {
	}
	
}
