import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Character implements KeyListener {

	private int xPos;
	private int yPos;
	private BufferedImage image;
	private Collectable[] inventory;
	
	public Character(int xStart, int yStart, String imageName) {
		xPos = xStart;
		yPos = yStart;
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("Pictures/" + imageName + ".png"));
		} catch (Exception e) {
			tempImage = null;
			System.out.println("NULL BLOCK IMAGE");
		}
		image = tempImage;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public int x() {
		return xPos;
	}
	
	public int y() {
		return yPos;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println(e.getKeyCode());
		int increment = Screen.BLOCKSIZE;
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
