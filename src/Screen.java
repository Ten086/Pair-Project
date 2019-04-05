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
	private int initialX = 609;
	private int initialY = 400;
	private int xPos = 500 + 0;
	private int yPos = 500 + 0;
	private BufferedImage charImage;
	private BlockEnum[][] world;
	public static final int BLOCKSIZE = 20;

	public Screen() {

		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("Pictures/guy.png"));
		} catch (Exception e) {
			tempImage = null;
			System.out.println("NULL CHAR IMAGE");
		}
		charImage = tempImage;

		//System.out.println("before gen");
		world = WorldBuilder.genWorld();
		//System.out.println("after gen");
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
		drawChar(g);
		drawCollectable(g, new Collectable(700, 300, "stick"));
	}

	private void drawWorld(Graphics g, BlockEnum[][] world) {
		for (int r = 0; r < world.length;r++) {
			for (int c = 0; c < world[0].length; c++) {
				drawBlock(g, world[r][c], (c * BLOCKSIZE) + xPos, yPos - (r * BLOCKSIZE));
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
			g.drawImage(b.getImage(), x, y, this);
		}
	}
	
	private void drawCollectable(Graphics g, Collectable c) {
		if (c != null) {
			g.drawImage(c.getImage(), c.getX(), c.getY(), this);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
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