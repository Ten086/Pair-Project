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
	private int yPos = 1600;
	private Character character;
	private Rectangle charRect = new Rectangle(charX, charY, BLOCKSIZE, BLOCKSIZE * 2);
	private Rectangle belowRect;
	private Rectangle leftRect;
	private Rectangle rightRect;
	ArrayList<Integer> keysPressed = new ArrayList<Integer>();
	private int xVel = 0;
	private int yVel = 0;
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

	private int[] getBlockLeft() {
		int row = -1;
		int col = -1;
		for (int c = 0; c < world[0].length; c++) {
			int rightPixel = c * BLOCKSIZE + xPos + BLOCKSIZE;
			if (rightPixel < charX) {
				col = c;
			}
		}
		for (int r = 0; r < world.length; r++) {
			int topPixel = yPos - (r * BLOCKSIZE);
			if (topPixel > charY && topPixel < charY + (2 * BLOCKSIZE)) {
				row = r;
			}
		}
		return new int[] {row, col};
	}

	private int[] getBlockRight() {
		int row = -1;
		int col = -1;
		for (int c = world[0].length - 1; c >= 0; c--) {
			int leftPixel = c * BLOCKSIZE + xPos;
			if (leftPixel > charX) {
				col = c;
			}
		}
		for (int r = 0; r < world.length; r++) {
			int topPixel = yPos - (r * BLOCKSIZE);
			if (topPixel > charY && topPixel < charY + (2 * BLOCKSIZE)) {
				row = r;
			}
		}
		return new int[] {row, col};
	}
	private boolean touchLeft() {
		int blockRow = getBlockLeft()[0];
		int blockCol = getBlockLeft()[1];
		if (world[blockRow][blockCol] == null) {
			return false;
		}
		else {
			leftRect = new Rectangle(xPos + (blockCol * BLOCKSIZE), yPos - (blockRow * BLOCKSIZE), BLOCKSIZE, BLOCKSIZE);
			return charRect.intersects(leftRect);
		}
	}

	private boolean touchRight() {
		int blockRow = getBlockRight()[0];
		int blockCol = getBlockRight()[1];
		if (world[blockRow][blockCol] == null) {
			return false;
		}
		else {
			rightRect = new Rectangle(xPos + (blockCol * BLOCKSIZE), yPos - (blockRow * BLOCKSIZE), BLOCKSIZE, BLOCKSIZE);
			return charRect.intersects(rightRect);
		}
	}
	private boolean touchBelow() {
		int blockRow = getBlockBelow()[0];
		int blockCol = getBlockBelow()[1];
		belowRect = new Rectangle(xPos + (blockCol * BLOCKSIZE), yPos - (blockRow * BLOCKSIZE), BLOCKSIZE, BLOCKSIZE);
		return charRect.intersects(belowRect);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(0, 144, 255));
		drawWorld(g, world);
		//System.out.println("after draw");
		drawChar(g);
		g.setColor(Color.BLACK);
		g.drawRect(charRect.x, charRect.y, charRect.width, charRect.height);
		g.drawRect(belowRect.x, belowRect.y, belowRect.width, belowRect.height);
		if (leftRect != null) {
			g.drawRect(leftRect.x, leftRect.y, leftRect.width, leftRect.height);
		}
		if (rightRect != null) {
			g.drawRect(rightRect.x, rightRect.y, rightRect.width, rightRect.height);
		}
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
		if (keyCode == 39) {
			xVel = -MOVEINCREMENT;
		}
		//left
		else if (keyCode == 37) {
			xVel = MOVEINCREMENT;
		}

		//up 
		if (keyCode == 38 && touchBelow()) {
			yVel = 2 * MOVEINCREMENT;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == 39 || keyCode == 37) {
			xVel = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		xPos += xVel;
		yPos += yVel;
		if (!touchBelow()) {
			yVel--;
		}
		else {
			yVel = 0;
		}
		repaint();
	}
}