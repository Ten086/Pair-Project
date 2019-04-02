import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public enum BlockEnum {
	DIRT ("dirt"),
	/*GRASS ("Pictures/grass.png"),
	SAND ("Pictures/sand.png"),
	SNOW ("Pictures/snow.png"),
	TREETRUNK ("Pictures/treetrunk.png"),
	TREELEAF ("Pictures/treeleaf.png"),
	BRICK ("Pictures/brick.png"),
	ROOF1 ("Pictures/roof1.png"),
	ROOF2 ("Pictures/roof2.png"),
	ROCK ("Pictures/rock.png"),
	WATER ("Pictures/water.png")*/;
	
	private static final String pathStart = "Pictures/";
	private static final String pathEnd = ".png";
	
	private final BufferedImage image;
	private final char type = 'X';
	
	private BlockEnum(String name) {
		BufferedImage tempImage = null;
		try {
			tempImage = ImageIO.read(new File(fileName));
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
		return "
	}
	
}
