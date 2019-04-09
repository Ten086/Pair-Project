import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Character {
	
	private BufferedImage image;
	private ArrayList<Collectable> inventory = new ArrayList<Collectable>();
	
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
	
	public void addToInventory(Collectable c) {
		inventory.add(c);
	}
	
	public int numOf(Collectable cToCheck) {
		int num = 0;
		for (Collectable c : inventory) {
			if (c.getType().equals(cToCheck.getType())) {
				num++;
			}
		}
		return num;
	}
}