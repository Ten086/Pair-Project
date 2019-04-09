import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Collectable {

	private int row;
	private int col;
	private BufferedImage image;
	private String type;
	private Rectangle rectangle;

	public static Collectable[] getAllCollectables() {
		String[] names = {"stick", "seashell"};
		Collectable[] collectables = new Collectable[names.length];
		for (int i = 0; i < names.length; i++) {
			collectables[i] = new Collectable(names[i]);
		}
		return collectables;
	}
	
	public static BufferedImage[] getImages() {
		String[] names = {"stick", "seashell"};
		BufferedImage[] images = new BufferedImage[names.length];
		BufferedImage tempImage = null;
		for (int i = 0; i < names.length; i++) {
			try {
				tempImage = ImageIO.read(new File("Pictures/" + names[i] + ".png"));
				images[i] = tempImage;
			} catch (Exception e) {
				tempImage = null;
				System.out.println("NULL COLLECTABLE IMAGE");
			}
		}
		return images;
	}
	
	public Collectable(String imageName) {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("Pictures/" + imageName + ".png"));
		} catch (Exception e) {
			tempImage = null;
			System.out.println("NULL COLLECTABLE IMAGE");
		}
		image = tempImage;
		type = imageName;
	}
	
	public Collectable(String imageName, int row, int col) {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File("Pictures/" + imageName + ".png"));
		} catch (Exception e) {
			tempImage = null;
			System.out.println("NULL COLLECTABLE IMAGE");
		}
		image = tempImage;
		type = imageName;
		this.row = row;
		this.col = col;
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public String getType() {
		return type;
	}

	public Rectangle getRect() {
		return rectangle;
	}

	public void setRect(Rectangle r) {
		rectangle = r;
	}

	public String toString() {
		return type + " at " + row + " " + col;
	}
}