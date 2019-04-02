import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Screen extends JPanel implements KeyListener {

	private JFrame frame;
	private int xPos = 0;
	private int yPos = 0;
	private BufferedImage charImage;
	
	public Screen() {
		initializeSwing();
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("Pictures/guy.png"));
		} catch (Exception e) {
			tempImage = null;
			System.out.println("NULL CHAR IMAGE");
		}
		charImage = tempImage;
	}
	
	private void initializeSwing() {
		frame = new JFrame("Bad RPG");
		frame.setContentPane(this);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//frame.setUndecorated(true);
		frame.setVisible(true);
		frame.addKeyListener(this);
	}
	
	public static void main(String[] args) {
		Screen screen = new Screen();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		BlockEnum[][] worldTest = new BlockEnum[5][3];
		for (int r = 0; r < worldTest.length; r++) {
			for (int c = 0; c < worldTest[0].length; c++) {
				worldTest[r][c] = BlockEnum.DIRT;
			}
		}
		drawWorld(g, worldTest);
	}
	
	private void drawWorld(Graphics g, BlockEnum[][] world) {
		for (int r = 0; r < world.length; r++) {
			for (int c = 0; c < world[0].length; c++) {
				drawBlock(g, world[r][c], (c*50) + xPos, (r*50) + yPos);
			}
		}
	}
	
	private void drawChar() {
		g.drawImage()
	}
	private void drawBlock(Graphics g, BlockEnum b, int x, int y) {
		g.drawImage(b.getImage(), x, y, this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyCode());
		int increment = 5;
		//right
		if (e.getKeyCode() == 39) {
			xPos -= increment;
		}
		//up 
		else if (e.getKeyCode() == 38) {
			yPos += increment;
		}
		//left
		else if (e.getKeyCode() == 37) {
			xPos += increment;
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
