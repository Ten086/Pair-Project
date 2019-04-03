import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public enum BlockEnum {
	DIRT ("dirt"),
	GRASS ("grass"),
	SAND ("sand"),
	SNOW ("snow"),
	/*TREETRUNK ("treetrunk"),
	TREELEAF ("treeleaf"),
	BRICK ("brick"),
	ROOF1 ("roof1"),
	ROOF2 ("roof2"),
	ROCK ("rock"),
	WATER ("water")*/;
	
	private static final String pathStart = "Pictures/";
	private static final String pathEnd = ".png";
	
	private final BufferedImage image;
	private char type = 'X';
	
	private BlockEnum(String name) {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File(pathStart + name + pathEnd));
		} catch (Exception e) {
			tempImage = null;
			System.out.println("NULL BLOCK IMAGE");
		}
		image = tempImage;
		type = name.charAt(0);
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public String toString() {
		return "" + type;
	}
	
}
