import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Screen extends JPanel implements KeyListener, ActionListener {

	private JFrame frame;
	private Timer timer;
	private BlockEnum[][] world;
	private BufferedImage[][] images;
	private ArrayList<Collectable> collectables = new ArrayList<Collectable>();
	private static final int SCREENWIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	private static final int SCREENHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	private static final int BLOCKSWIDE = 50;
	public static final int BLOCKSIZE = SCREENWIDTH / BLOCKSWIDE;
	public static final int MOVEINCREMENT = (BLOCKSIZE / 3) * 2;
	private final int charX = BLOCKSIZE * (BLOCKSWIDE / 2);
	private final int charY = SCREENHEIGHT / 2 - BLOCKSIZE;
	private final int xStart = charX - (int)((double)WorldBuilder.BIOMEWIDTH * (double)BLOCKSIZE * 3.5);
	private final int yStart = BLOCKSIZE * WorldBuilder.WORLDHEIGHT;
	private int xPos = xStart;
	private int yPos = yStart;
	private Character character;
	private Rectangle charRect = new Rectangle(charX, charY, BLOCKSIZE, BLOCKSIZE * 2);
	private int xVel = 0;
	private int yVel = 0;
	private Rectangle belowRect;
	ArrayList<Integer> keysPressed = new ArrayList<Integer>();
	private BufferedImage arrowImage;

	public Screen() {
		character = new Character("character");
		world = WorldBuilder.genWorld();
		images = getRotatedWorld(world);
		//System.out.println("blocksize: " + BLOCKSIZE);

		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("Pictures/arrow.png"));
			arrowImage = tempImage;
		} catch (Exception e) {
			tempImage = null;
			System.out.println("NULL ARROW IMAGE");
		}

		initializeSwing();
		initCollectables();
		yPos += distanceBelow();
		timer = new Timer(20, this);
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

	private void initCollectables() {
		int numCollectables = 100;
		int[] surfaceRows = new int[WorldBuilder.WORLDWIDTH];
		for (int c = 0; c < surfaceRows.length; c++) {
			for (int r = 0; r < WorldBuilder.WORLDHEIGHT; r++) {
				if (world[r][c] != null && world[r][c] != BlockEnum.WATER) {
					surfaceRows[c] = r;
				}
			}
		}
		for (int i = 0; i < numCollectables; i++) {
			int randomCol = (int)(Math.random() * WorldBuilder.WORLDWIDTH);
			int randomNum = (int)(Math.random() * 2);
			if (randomNum == 0) {
				collectables.add(new Collectable("stick", surfaceRows[randomCol], randomCol));
			}
			else {
				collectables.add(new Collectable("seashell", surfaceRows[randomCol], randomCol));

			}
		}
	}

	private BufferedImage[][] getRotatedWorld(BlockEnum[][] world) {
		BufferedImage[][] images = new BufferedImage[world.length][world[0].length];
		for (int r = 0; r < images.length; r++) {
			for (int c = 0; c < images[0].length; c++) {
				BlockEnum b = world[r][c];
				if (b != null) {
					if (b != BlockEnum.GRASS) {
						AffineTransform affine = new AffineTransform();
						BufferedImage image = b.getImage();
						int numRotations = (int)(Math.random() * 2);
						for (int i = 0; i < numRotations; i++) {
							//rotates 90 degrees around anchor point in center:
							affine.rotate(Math.PI / 2, image.getWidth() / 2, image.getHeight() / 2);
							//
							AffineTransformOp affineOp = new AffineTransformOp(affine, AffineTransformOp.TYPE_BILINEAR);
							image = affineOp.filter(image, null);
						}
						images[r][c] = image;
					}
					else {
						images[r][c] = b.getImage();
					}

				}
			}
		}
		return images;
	}
	
	private int rowToY(int blockRow) {
		return yPos - (blockRow * BLOCKSIZE);
	}

	private int colToX(int blockCol) {
		return xPos + (blockCol * BLOCKSIZE);
	}

	private int xToCol(int xPixel) {
		return Math.round((float)(xPixel - xPos) / (float)(BLOCKSIZE));
	}

	private int yToRow(int yPixel) {
		return Math.round((float)(yPos - yPixel) / (float)(BLOCKSIZE));
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
		this.setBackground(new Color(50, 176, 255));
		//drawWorld(g, world);
		drawWorld(g, images);
		//System.out.println("after draw");
		drawChar(g);
		g.setColor(Color.BLACK);
		g.drawRect(charRect.x, charRect.y, charRect.width, charRect.height);
		g.setColor(Color.RED);
		g.drawRect(belowX(), belowY(), BLOCKSIZE, BLOCKSIZE);
		drawCollectables(g);
		drawInventory(g);
	}

	private void drawWorld(Graphics g, BufferedImage[][] images) {
		for (int r = 0; r < world.length; r++) {
			for (int c = 0; c < world[0].length; c++) {
				int blockX = xPos + c * BLOCKSIZE;
				int blockY = yPos - r * BLOCKSIZE;
				if (blockX > -BLOCKSIZE && blockX < frame.getWidth() + BLOCKSIZE
						&& blockY > -BLOCKSIZE && blockY < frame.getHeight() + BLOCKSIZE) {
					drawBlock(g, images[r][c], blockX, blockY);
				}
			}
		}
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

	private void drawChar(Graphics g) {
		g.drawImage(character.getImage(), charX, charY, BLOCKSIZE, BLOCKSIZE * 2, this);
	}

	private void drawArrow(Graphics g, Collectable c) {
		g.drawImage(arrowImage, colToX(c.getCol()), rowToY(c.getRow()) - (int)((float)BLOCKSIZE * 3.5f), BLOCKSIZE, BLOCKSIZE, this);
	}

	private void drawBlock(Graphics g, BufferedImage b, int x, int y) {
		if (b != null) {
			g.drawImage(b, x, y, BLOCKSIZE, BLOCKSIZE, this);
		}
	}

	private void drawBlock(Graphics g, BlockEnum b, int x, int y) {
		if (b != null) {
			BufferedImage image = b.getImage();
			g.drawImage(image, x, y, BLOCKSIZE, BLOCKSIZE, this);
		}
	}

	private void drawStick(Graphics g, Collectable c) {
		g.drawImage(c.getImage(), colToX(c.getCol()), rowToY(c.getRow()) - (int)(BLOCKSIZE / 2), BLOCKSIZE, BLOCKSIZE / 2, this);
	}

	private void drawSeashell(Graphics g, Collectable c) {
		g.drawImage(c.getImage(), colToX(c.getCol()), rowToY(c.getRow()) - (int)(BLOCKSIZE / 2), BLOCKSIZE / 2, BLOCKSIZE / 2, this);
	}

	private void drawCollectables(Graphics g) {
		Iterator<Collectable> itr = collectables.iterator();
		while (itr.hasNext()) {
			Collectable c = (Collectable)itr.next();
			if (c != null) {
				int x = colToX(c.getCol());
				int y = rowToY(c.getRow());
				if (x > -BLOCKSIZE && x < frame.getWidth() + BLOCKSIZE
						&& y > -BLOCKSIZE && y < frame.getHeight() + BLOCKSIZE) {
					c.setRect(makeRectangle(c.getRow(), c.getCol()));
					if (c.getType().equals("stick")) {
						drawStick(g, c);
					}
					else if (c.getType().equals("seashell")) {
						drawSeashell(g, c);
					}
					if (charRect.intersects(c.getRect())) {
						drawArrow(g, c);
					}
				}
			}
		}
	}
	
	public void drawInventory(Graphics g) {
		g.setColor(Color.BLACK);
		Font f = new Font("Helvetica", Font.BOLD, 50);
		g.setFont(f);
		int stringWidth = g.getFontMetrics().stringWidth("INVENTORY");
		int textX = frame.getWidth() - stringWidth * 2;
		int textY = 100;
		g.drawString("INVENTORY", textX, textY);
		Collectable[] list = Collectable.getAllCollectables();
		for (int i = 0; i < list.length; i++) {
			g.drawImage(list[i].getImage(), textX, textY + (100 * (i+1)), BLOCKSIZE, BLOCKSIZE, this);
			g.drawString("" + character.numOf(list[i]), textX + (2 * BLOCKSIZE), textY + (100 * (i+1)));
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (!keysPressed.contains(keyCode)) {
			keysPressed.add(keyCode);
			//System.out.println("adding " + keyCode);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		keysPressed.remove(new Integer(keyCode));
		//System.out.println("removing " + keyCode);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		//right
		if (keysPressed.contains(39)) {
			int worldWidth = WorldBuilder.BIOMEWIDTH * 6 * BLOCKSIZE;
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
			yVel = (int)(2.5f * (float)MOVEINCREMENT);
		}
		//yPos positive makes character go up relative to world
		//xPos positive makes character go left relative to world
		xPos += xVel;
		yPos += yVel;
		if (!collidesBelow()) {
			yVel -= (int)((float)MOVEINCREMENT / 4f);
		}
		else {
			yVel = 0;
			yPos += distanceBelow() - 1;
		}
		//System.out.println("y velocity: " + yVel);
		
		//down
		if (keysPressed.contains(40)) {
			Iterator<Collectable> itr = collectables.iterator();
			while (itr.hasNext()) {
				Collectable c = (Collectable)itr.next();
				if (c != null && c.getRect() != null && charRect.intersects(c.getRect())) {
					character.addToInventory(c);
					itr.remove();
				}
			}
		}
		repaint();
	}
}