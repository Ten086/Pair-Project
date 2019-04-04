import java.util.Arrays;
import java.util.Collections;

public class WorldBuilder {
	final static int WORLDHEIGHT = 6;
	final static int BASEDIRTHEIGHT = 3;
	private static BlockEnum[][] genSnow(int width, int startHeight, int endHeight) {
		final int SNOWLAYERHEIGHT = 12;
		BlockEnum[][] world = new BlockEnum[WORLDHEIGHT][width];
		for(int i=0; i<width; i++) {
			int dirtHeight = BASEDIRTHEIGHT+(int)(3d*Math.random()-1);
			for(int j=0;j<dirtHeight; j++) {
				world[j][i] = BlockEnum.DIRT;
			}
		}
		
		for(int i=0; i<width; i++) {
			int j=0;
			while(world[j][i] != null) {
				j++;
			}
			world[j][i] = BlockEnum.SNOW;
		}
		return world;
	}
	private static BlockEnum[][] genDesert(int width, int startHeight, int endHeight) {
		final int SNOWLAYERHEIGHT = 12;
		BlockEnum[][] world = new BlockEnum[WORLDHEIGHT][width];
		for(int i=0; i<width; i++) {
			int dirtHeight = BASEDIRTHEIGHT+(int)(3d*Math.random()-1);
			for(int j=0;j<BASEDIRTHEIGHT; j++) {
				world[j][i] = BlockEnum.SAND;
			}
		}
		
		for(int i=0; i<width; i++) {
			int j=0;
			while(world[j][i] != null) {
				j++;
			}
			world[j][i] = BlockEnum.SAND;
		}
		return world;
	}
	private static BlockEnum[][] genRainforest(int width, int startHeight, int endHeight) {
		final int SNOWLAYERHEIGHT = 12;
		BlockEnum[][] world = new BlockEnum[WORLDHEIGHT][width];
		for(int i=0; i<width; i++) {
			int dirtHeight = BASEDIRTHEIGHT+(int)(3d*Math.random()-1);
			for(int j=0;j<BASEDIRTHEIGHT; j++) {
				world[j][i] = BlockEnum.DIRT;
			}
		}
		
		for(int i=0; i<width; i++) {
			int j=0;
			while(world[j][i] != null) {
				j++;
			}
			world[j][i] = BlockEnum.GRASS;
			world[j+1][i] = BlockEnum.GRASS;
		}
		return world;
	}
	private static BlockEnum[][] genGrass(int width, int startHeight, int endHeight) {
		final int SNOWLAYERHEIGHT = 12;
		BlockEnum[][] world = new BlockEnum[WORLDHEIGHT][width];
		for(int i=0; i<width; i++) {
			int dirtHeight = BASEDIRTHEIGHT+(int)(3d*Math.random()-1);
			for(int j=0;j<BASEDIRTHEIGHT; j++) {
				world[j][i] = BlockEnum.DIRT;
			}
		}
		
		for(int i=0; i<width; i++) {
			int j=0;
			while(world[j][i] != null) {
				j++;
			}
			world[j][i] = BlockEnum.SNOW;
		}
		return world;
	}
	private static BlockEnum[][] genOcean(int width, int startHeight, int endHeight) {
		final int SNOWLAYERHEIGHT = 12;
		BlockEnum[][] world = new BlockEnum[WORLDHEIGHT][width];
		for(int i=0; i<width; i++) {
			int dirtHeight = BASEDIRTHEIGHT+(int)(3d*Math.random()-1);
			for(int j=0;j<BASEDIRTHEIGHT; j++) {
				world[j][i] = BlockEnum.DIRT;
			}
		}
		
		for(int i=0; i<width; i++) {
			int j=0;
			while(world[j][i] != null) {
				j++;
			}
			world[j][i] = BlockEnum.SNOW;
		}
		return world;
	}
	public static BlockEnum[][] genWorld() {
		BlockEnum[][] world;
		BlockEnum[][] snow = genSnow(50,50,50);
		BlockEnum[][] desert = genDesert(50,50,50);
		BlockEnum[][] grass = genGrass(50,50,50);
		BlockEnum[][] rainforest = genRainforest(50,50,50);
		BlockEnum[][] ocean = genOcean(50,50,50);
		world = new BlockEnum[WORLDHEIGHT][snow[0].length+desert[0].length + grass[0].length + rainforest[0].length + ocean[0].length];
		Integer[] biomeOrder = {0,1,2,3,4};
		Collections.shuffle(Arrays.asList(biomeOrder));

		for(int i=0; i<biomeOrder.length; i++) {
			BlockEnum[][] activeBiome = null;
			if(biomeOrder[i]==0) {
				activeBiome=snow;
			}
			else if(biomeOrder[i]==1) {
				activeBiome=desert;
			}
			else if(biomeOrder[i]==2) {
				activeBiome=grass;
			}
			
			else if(biomeOrder[i]==3) {
				activeBiome=rainforest;
			}
			else if(biomeOrder[i]==4) {
				activeBiome = ocean;
			}
			int col =0;
			while(world[0][col]!=null) {
				col++;
			}
			for(int j=0; j<activeBiome.length; j++) {
				for(int k=0; k<activeBiome[0].length; k++) {
					world[j][col+k] = activeBiome[j][k];
				}
			}
		}
		/*for(int i=0; i<world.length; i++) {
			for(int j=0; j<world[0].length; j++) {
				System.out.print(world[i][j]);
			}
			System.out.println();
		}
		System.out.println();*/
		return world;
	}
}