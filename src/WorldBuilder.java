
public class WorldBuilder {
	final int WORLDHEIGHT = 50;
	final int BASEDIRTHEIGHT = 12;
	private Block[][] genSnow(int width, int startHeight, int endHeight) {
		Block[][] world = new Block[WORLDHEIGHT][width];
		for(int i=0; i<width; i++) {
			int dirtHeight = BASEDIRTHEIGHT+(int)(3d*Math.random()-1);
			for(int j=0;j<BASEDIRTHEIGHT; j++) {
				world[j][i] = new Block(Block.DIRT);
			}
		}
		return world;
	}
}
