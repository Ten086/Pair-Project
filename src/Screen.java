import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Screen extends JPanel implements KeyListener, ActionListener {

	private boolean gameRunning = true;
	private Timer timer;
	private JFrame frame;
	private BlockEnum[][] world;
	public static final int BLOCKSIZE = 30;
	private int charX = 600;
	private int charY = 300;
	private int xPos = 500;
	private int yPos = 1800;
	private Character character;
	private Rectangle charRect = new Rectangle(charX, charY, BLOCKSIZE, BLOCKSIZE * 2);
	private Rectangle collisionRect;
	ArrayList<Integer> keysPressed = new ArrayList<Integer>();
	private int jumpSpeed;
	private int weight = 5;
	public static final int MOVEINCREMENT = 10;

	public Screen() {
		character = new Character("character");

		//System.out.println("before gen");
		world = WorldBuilder.genWorld();
		
		//System.out.println("after gen");
		initializeSwing();

		touchBelow();

		timer = new Timer(5, this);
		timer.setInitialDelay(100);
		timer.start();
	}

	public static void main(String[] args) {
		Screen screen = new Screen();
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

	private int[] getBlockBelow() {
		int row = -1;
		int col = -1;
		for (int c = 0; c < world[0].length; c++) {
			int leftPixel = c * BLOCKSIZE + xPos;
			int rightPixel = leftPixel + BLOCKSIZE;
			if (leftPixel <= charX && rightPixel > charX) {
				col = c;
			}
		}
		//System.out.println("col: " + col);
		for (int r = 0; r < world.length; r++) {
			if (world[r][col] != null) {
				row = r;
			}
		}
		//System.out.println("row: " + row);
		return new int[] {row, col};
	}

	private boolean touchBelow() {
		int blockRow = getBlockBelow()[0];
		int blockCol = getBlockBelow()[1];
		collisionRect = new Rectangle(xPos + (blockCol * BLOCKSIZE), yPos - (blockRow * BLOCKSIZE), BLOCKSIZE, BLOCKSIZE);
		return charRect.intersects(collisionRect);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(0, 144, 255));
		drawWorld(g, world);
		//System.out.println("after draw");
		drawChar(g);
		g.setColor(Color.BLACK);
		g.drawRect(charRect.x, charRect.y, charRect.width, charRect.height);
		g.drawRect(collisionRect.x, collisionRect.y, collisionRect.width, collisionRect.height);
	}
	private void drawWorld(Graphics g, BlockEnum[][] world) {
		for (int r = 0; r < world.length;r++) {
			for (int c = 0; c < world[0].length; c++) {
				drawBlock(g, world[r][c], (xPos + c * BLOCKSIZE), yPos - (r * BLOCKSIZE));
			}
		}
	}

	private void drawChar(Graphics g) {
		g.drawImage(character.getImage(), charX, charY, BLOCKSIZE, BLOCKSIZE * 2, this);
	}
	
	private void drawBlock(Graphics g, BlockEnum b, int x, int y) {
		if (b != null) {
			Graphics2D tempG = (Graphics2D) b.getImage().getGraphics();
			tempG.scale(0.1, 0.1);
			g.drawImage(b.getImage(), x, y, BLOCKSIZE, BLOCKSIZE, this);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		boolean alreadyHas = false;
		for (int i = 0; i < keysPressed.size(); i++) {
			if (keysPressed.get(i) == keyCode) {
				alreadyHas = true;
			}
		}
		if (!alreadyHas) {
			keysPressed.add(keyCode);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		for (int i = 0; i < keysPressed.size(); i++) {
			if (keysPressed.get(i) == keyCode) {
				keysPressed.remove(i);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent e) {
		if (!touchBelow()) {
			//System.out.println("no touch");
			yPos -= 2;
			repaint();
		}

		for (int keyCode : keysPressed) {
			if (keyCode == 39) {
				xPos -= MOVEINCREMENT;
			}
			//left
			else if (keyCode == 37) {
				xPos += MOVEINCREMENT;
			}

			//up 
			if (keyCode == 38 && touchBelow()) {
				int maxSpeed = 30;
				int weight = 5;
				int jumpSpeed = maxSpeed;
				while (jumpSpeed > 0) {
					yPos += jumpSpeed;
					jumpSpeed -= weight;
				}
			}
		}
		repaint();
	}
}