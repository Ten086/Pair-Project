import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
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

	private JFrame frame;
	private Timer timer;
	private BlockEnum[][] world;
	private static final int SCREENWIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	private static final int SCREENHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	private static final int BLOCKSWIDE = 100;
	public static final int BLOCKSIZE = SCREENWIDTH / BLOCKSWIDE;
	public static final int MOVEINCREMENT = (BLOCKSIZE / 4) * 3;
	private final int charX = BLOCKSIZE * (BLOCKSWIDE / 2);
	private final int charY = SCREENHEIGHT / 2 - BLOCKSIZE;
	private int xPos = -43000;
	private int yPos = BLOCKSIZE * 80;
	private Character character;
	private Rectangle charRect = new Rectangle(charX, charY, BLOCKSIZE, BLOCKSIZE * 2);
	private int xVel = 0;
	private int yVel = 0;
	private Rectangle belowRect;
	ArrayList<Integer> keysPressed = new ArrayList<Integer>();

	public Screen() {
		character = new Character("character");
		world = WorldBuilder.genWorld();
		System.out.println(BLOCKSIZE);
		initializeSwing();
		timer = new Timer(10, this);
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

	private int rowToY(int blockRow) {
		return yPos - (blockRow * BLOCKSIZE);
	}

	private int colToX(int blockCol) {
		return xPos + (blockCol * BLOCKSIZE);
	}
	private Rectangle makeRectangle(int blockRow, int blockCol) {
		return new Rectangle(colToX(blockCol), rowToY(blockRow), BLOCKSIZE, BLOCKSIZE);
	}

	private int[] belowRowCol() {
		int col = xToCol(charX);
		int row = yToRow(world.length - 1);
		while (world[row][col] == null || world[row][col] == BlockEnum.WATER) {
			row--;
		}
		int[] coords = {row, col};
		return coords;
	}

	private int belowRow() {
		return belowRowCol()[0];
	}

	private int belowCol() {
		return belowRowCol()[1];
	}

	private int belowY() {
		return rowToY(belowRow());
	}

	private int belowX() {
		return colToX(belowCol());
	}

	private int distanceBelow() {
		return ((charY + (BLOCKSIZE * 2) - belowY()));
	}

	private boolean collidesBelow() {
		belowRect = makeRectangle(belowRow(), belowCol());
		return belowRect.intersects(charRect);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(0, 144, 255));
		drawWorld(g, world);
		//System.out.println("after draw");
		drawChar(g);
		g.setColor(Color.BLACK);
		g.drawRect(charRect.x, charRect.y, charRect.width, charRect.height);
		g.setColor(Color.RED);
		g.drawLine(charX, charY + (BLOCKSIZE * 2), belowX(), belowY());
		g.drawRect(belowX(), belowY(), BLOCKSIZE, BLOCKSIZE);
		/*if (belowRect != null) {
			g.drawRect(belowRect.x, belowRect.y, belowRect.width, belowRect.height);
		}*/
		/*if (leftRect != null) {
			g.drawRect(leftRect.x, leftRect.y, leftRect.width, leftRect.height);
		}
		if (rightRect != null) {
			g.drawRect(rightRect.x, rightRect.y, rightRect.width, rightRect.height);
		}*/
	}

	private void drawWorld(Graphics g, BlockEnum[][] world) {
		for (int r = 0; r < world.length; r++) {
			for (int c = 0; c < world[0].length; c++) {
				int blockX = xPos + c * BLOCKSIZE;
				int blockY = yPos - r * BLOCKSIZE;
				if (blockX > -BLOCKSIZE && blockX < frame.getWidth() + BLOCKSIZE
						&& blockY > -BLOCKSIZE && blockY < frame.getHeight() + BLOCKSIZE) {
					drawBlock(g, world[r][c], blockX, blockY);
				}
			}
		}
	}

	private int xToCol(int xPixel) {
		return Math.round((float)(xPixel - xPos) / (float)(BLOCKSIZE));
	}

	private int yToRow(int yPixel) {
		return Math.round((float)(yPos - yPixel) / (float)(BLOCKSIZE));
	}


	private void drawChar(Graphics g) {
		g.drawImage(character.getImage(), charX, charY, BLOCKSIZE, BLOCKSIZE * 2, this);
	}

	private void drawBlock(Graphics g, BlockEnum b, int x, int y) {
		if (b != null) {
			g.drawImage(b.getImage(), x, y, BLOCKSIZE, BLOCKSIZE, this);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (!keysPressed.contains(keyCode)) {
			keysPressed.add(keyCode);
			System.out.println("adding " + keyCode);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keysPressed.remove(new Integer(keyCode));
		System.out.println("removing " + keyCode);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void actionPerformed(ActionEvent e) {

		//right
		if (keysPressed.contains(39)) {
			int worldWidth = WorldBuilder.BIOMEWIDTH * 5 * BLOCKSIZE;
			int rightXPos = xPos + worldWidth;
			int charRightXPos = charX + BLOCKSIZE;
			if (rightXPos - charRightXPos > MOVEINCREMENT) {
				xVel = -MOVEINCREMENT;
			}
			else {
				xPos = charRightXPos - worldWidth;
				xVel = 0;
			}
		}
		//left
		else if (keysPressed.contains(37)) {
			if (charX - xPos > MOVEINCREMENT) {
				xVel = MOVEINCREMENT;
			}
			else {
				xPos = charX;
				xVel = 0;
			}
		}
		else {
			xVel = 0;
		}

		//up 
		if (keysPressed.contains(38) && collidesBelow()) {
			yVel = 3 * MOVEINCREMENT;
		}
		//change position of world
		//yPos positive makes character go up relative to world
		//xPos positive makes character go left relative to world

		xPos += xVel;
		yPos += yVel;
		if (!collidesBelow()) {
			yVel -= (int)((float)MOVEINCREMENT / 3f);
		}
		else {
			yVel = 0;
			yPos += distanceBelow() - 1;
		}
		//System.out.println("y velocity: " + yVel);
		repaint();
	}
}