import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Screen extends JPanel implements KeyListener {

	private JFrame frame;
	private int initialX = 600;
	private int initialY = 300;
	private int xPos = 500 + 0;
	private int yPos = 500 + 0;
	private BufferedImage charImage;
	private BlockEnum[][] world;
	private final int blockSize = 10;

	public Screen() {

		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("Pictures/guy.png"));
		} catch (Exception e) {
			tempImage = null;
			System.out.println("NULL CHAR IMAGE");
		}
		charImage = tempImage;

		System.out.println("before gen");
		world = WorldBuilder.genWorld();
		System.out.println("after gen");
		initializeSwing();
	}

	private void initializeSwing() {
		frame = new JFrame("Bad RPG");
		frame.setContentPane(this);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setUndecorated(true);
		frame.setVisible(true);
		frame.addKeyListener(this);
	}

	public static void main(String[] args) {
		Screen screen = new Screen();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(0, 144, 255));
		drawWorld(g, world);
		//System.out.println("after draw");
		drawChar(g);
	}

	private void drawWorld(Graphics g, BlockEnum[][] world) {
		for (int r = 0; r < world.length; r++) {
			for (int c = 0; c < world[0].length; c++) {
				drawBlock(g, world[r][c], (c * blockSize) + xPos, yPos - (r * blockSize));
			}
		}
	}

	private void drawChar(Graphics g) {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("Pictures/guy.png"));
		} catch (Exception e) {
			tempImage = null;
			System.out.println("NULL BLOCK IMAGE");
		}
		g.drawImage(tempImage, initialX, initialY, this);
	}
	private void drawBlock(Graphics g, BlockEnum b, int x, int y) {
		if (b != null) {
			Graphics2D tempG = (Graphics2D) b.getImage().getGraphics();
			tempG.scale(0.1, 0.1);
			g.drawImage(b.getImage(), x, y, this);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyCode());
		int increment = blockSize;
		//right
		if (e.getKeyCode() == 39) {
			xPos -= increment;
		}
		//left
		else if (e.getKeyCode() == 37) {
			xPos += increment;
		}
		//up 
		if (e.getKeyCode() == 38) {
			yPos += increment;
		}
		//down
		else if (e.getKeyCode() == 40) {
			yPos -= increment;
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
