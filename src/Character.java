import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Character {
	
	private BufferedImage image;
	public Character(String name) {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("Pictures/" + name + ".png"));
		} catch (Exception e) {
			tempImage = null;
			System.out.println("NULL CHARACTER IMAGE");
		}
		image = tempImage;
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
