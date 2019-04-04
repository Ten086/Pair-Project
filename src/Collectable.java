import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Collectable {

	private int xPos;
	private int yPos;
	private BufferedImage image;

	public Collectable(int xPos, int yPos, String imageName) {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("Pictures/" + imageName + ".png"));
		} catch (Exception e) {
			tempImage = null;
			System.out.println("NULL COLLECTABLE IMAGE");
		}
		image = tempImage;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
}
