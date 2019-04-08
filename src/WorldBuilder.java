import java.util.Arrays;
import java.util.Collections;

public class WorldBuilder {
	public final static int WORLDHEIGHT = 100;
	public final static int BIOMEWIDTH = 250;
	private static BlockEnum[][] genSnow(int width, int startHeight, int endHeight) {
		final int SNOWLAYERHEIGHT = 12;
		BlockEnum[][] world = new BlockEnum[WORLDHEIGHT][width];
		for(int i=0; i<width; i++) {
			int dirtHeight = startHeight;
			for(int j=0;j<dirtHeight; j++) {
				world[j][i] = BlockEnum.DIRT;
			}
		}
		double wave1PhaseShift =  Math.random()* 2-1;
		double wave2PhaseShift =  Math.random()-.5;
		double wave3PhaseShift = Math.random();
		double direction = Math.random();
		int offset=0;

		for(int i=0; i<width; i++) {
			double height = (Math.sin(i/30d+ wave1PhaseShift)*10d );
			if(direction>.5) {
				height = -height;
			}
			double height2 = /*height + (Math.sin(i/Math.random()+.5)*1)*/ height + (Math.sin(i/20 + wave2PhaseShift)*2);
			double height3 = height + (Math.sin(i/10d + wave3PhaseShift)*1);

			if(i==0) {
				offset = (int)height3;
			}
			height3-=offset;
			if(height3 >0) {
				for(int j=0; j<height3|| j<2; j++) {
					if(height3-j>2) {
						world[j+startHeight][i] = BlockEnum.DIRT;	
					}
					else {
						world[j+startHeight][i] = BlockEnum.SNOW;	

					}
				}
			}
			else {
				for(int j=0; j>height3|| j>-2; j--) {
					if(j-height3>2) {
						world[j+startHeight][i] = null;	
					}
					else {
						world[j+startHeight][i] = BlockEnum.SNOW;	

					}
				}
			}

		}

		return world;
	}
	private static BlockEnum[][] genDesert(int width, int startHeight, int endHeight) {
		int offset=0;

		final int SNOWLAYERHEIGHT = 12;
		BlockEnum[][] world = new BlockEnum[WORLDHEIGHT][width];
		for(int i=0; i<width; i++) {
			int dirtHeight = startHeight+(int)(3d*Math.random()-1);
			for(int j=0;j<startHeight; j++) {
				world[j][i] = BlockEnum.SAND;
			}
		}

		double wave1PhaseShift =  Math.random()* 2-1;
		double wave2PhaseShift =  Math.random()-.5;
		//double wave3PhaseShift = Math.random();
		double direction = Math.random();

		for(int i=0; i<width; i++) {
			double  height = (Math.sin(i/30d)*4 + wave1PhaseShift) + 4;
			if(direction>.5) {
				height = -height;
			}
			double height2 = /*height + (Math.sin(i/Math.random()+.5)*1)*/ height + (Math.sin(i/20f + wave2PhaseShift)*2);
			//double height3 = height + (Math.sin(i/2 + wave3PhaseShift)*1);

			if(i==0) {
				offset = (int)height2;
			}
			height2-=offset;
			if(height2>0) {
				for(int j=0; j<height2; j++) {
					world[j+startHeight][i] = BlockEnum.SAND;
				}
			}
			else {
				for(int j=0; j>height2; j--) {
					world[j+startHeight][i] = null;
				}
			}

		}
		return world;
	}
	private static BlockEnum[][] genRainforest(int width, int startHeight, int endHeight) {
		int offset=0;

		final int SNOWLAYERHEIGHT = 12;
		BlockEnum[][] world = new BlockEnum[WORLDHEIGHT][width];
		for(int i=0; i<width; i++) {
			int dirtHeight = startHeight+(int)(3d*Math.random()-1);
			for(int j=0;j<startHeight; j++) {
				world[j][i] = BlockEnum.DIRT;
			}
		}

		double wave1PhaseShift =  Math.random()* 2-1;
		double wave2PhaseShift =  Math.random()-.5;
		double wave3PhaseShift = Math.random();
		double direction = Math.random();
		for(int i=0; i<width; i++) {
			double  height = (Math.sin(i/30f+ wave1PhaseShift)*4 ) + 4;
			if(direction>.5) {
				height = -height;
			}
			double height2 = /*height + (Math.sin(i/Math.random()+.5)*1)*/ height + (Math.sin(i/20f + wave2PhaseShift)*2);
			//double height3 = height + (Math.sin(i/2 + wave3PhaseShift)*1);

			if(i==0) {
				offset = (int)height2;
			}
			height2-=offset;
			if(height2>0) {
				for(int j=0; j<height2 || j<2; j++) {
					if(height2-j>2) {
						world[j+startHeight][i] = BlockEnum.DIRT;	
					}
					else {
						world[j+startHeight][i] = BlockEnum.GRASS;	

					}
				}
			}
			else {
				for(int j=0; j>height2 || j>-2; j--) {
					if(j-height2>2) {
						world[j+startHeight][i] = null;	
					}
					else {
						world[j+startHeight][i] = BlockEnum.GRASS;	

					}
				}
			}

		}

		return world;
	}
	private static BlockEnum[][] genGrass(int width, int startHeight, int endHeight) {
		final int SNOWLAYERHEIGHT = 12;
		int offset=0;

		BlockEnum[][] world = new BlockEnum[WORLDHEIGHT][width];
		for(int i=0; i<width; i++) {
			int dirtHeight = startHeight+(int)(3d*Math.random()-1);
			for(int j=0;j<startHeight; j++) {
				world[j][i] = BlockEnum.DIRT;
			}
		}

		double wave1PhaseShift =  Math.random()* 2-1;
		double wave2PhaseShift =  Math.random()-.5;
		double direction = Math.random();

		//double wave3PhaseShift = Math.random();
		for(int i=0; i<width; i++) {
			double  height = (Math.sin(i/30d + wave1PhaseShift)*4 ) + 4;
			if(direction>.5) {
				height = -height;
			}
			double height2 = /*height + (Math.sin(i/Math.random()+.5)*1)*/ height + (Math.sin(i/20d + wave2PhaseShift)*2);
			//double height3 = height + (Math.sin(i/2 + wave3PhaseShift)*1);

			if(i==0) {
				offset = (int)height2;
			}
			height2-=offset;
			if(height2>0) {
				for(int j=0; j<height2 || j<1; j++) {
					if(height2-j>1) {
						world[j+startHeight][i] = BlockEnum.DIRT;	
					}
					else {
						world[j+startHeight][i] = BlockEnum.GRASS;	

					}
				}
			}
			else {
				for(int j=0; j>height2 || j>-1; j--) {
					if(j-height2>1) {
						world[j+startHeight][i] = null;	
					}
					else {
						world[j+startHeight][i] = BlockEnum.GRASS;	

					}
				}
			}
		}
		return world;
	}
	private static BlockEnum[][] genOcean(int width, int startHeight, int endHeight) {
		final int SNOWLAYERHEIGHT = 12;
		BlockEnum[][] world = new BlockEnum[WORLDHEIGHT][width];
		for(int i=0; i<width; i++) {
			int dirtHeight = startHeight+(int)(3d*Math.random()-1);
			for(int j=0;j<startHeight; j++) {
				world[j][i] = BlockEnum.SAND;
			}
		}
		for(int i=0; i<width; i++) {
			double height = - Math.pow((Math.sin(i*Math.PI/width)) * (startHeight-2), .5) ;
			for(int j=0; j>height; j--) {
				world[j+startHeight][i] = BlockEnum.SNOW;	
			}
		}
		return world;
	}
	
	public static BlockEnum[][] genWorld() {
		BlockEnum[][] world;
		BlockEnum[][] snow;
		BlockEnum[][] desert;
		BlockEnum[][] grass;
		BlockEnum[][] rainforest;
		BlockEnum[][] ocean;
		world = new BlockEnum[WORLDHEIGHT][BIOMEWIDTH*5];
		Integer[] biomeOrder = {0,1,2,3,4};
		Collections.shuffle(Arrays.asList(biomeOrder));
		int startHeight =30;

		for(int i=0; i<biomeOrder.length; i++) {
			BlockEnum[][] activeBiome = null;
			if(biomeOrder[i]==0) {
				snow = genSnow(BIOMEWIDTH,startHeight,50);
				activeBiome=snow;
			}
			else if(biomeOrder[i]==1) {
				desert = genDesert(BIOMEWIDTH,startHeight,50);

				activeBiome=desert;
			}
			else if(biomeOrder[i]==2) {
				grass = genGrass(BIOMEWIDTH,startHeight,50);

				activeBiome=grass;
			}

			else if(biomeOrder[i]==3) {
				rainforest = genRainforest(BIOMEWIDTH,startHeight,50);

				activeBiome=rainforest;
			}
			else if(biomeOrder[i]==4) {
				ocean = genOcean(BIOMEWIDTH,startHeight,50);
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
			startHeight =0;
			while(world[startHeight][col+activeBiome[0].length-1]!=null) {
				startHeight++;
			}
			startHeight--;
		}
		//print(world);
		return world;
	}
	
	public static BlockEnum[][] flipWorld(BlockEnum[][] world) {
		BlockEnum[][] newWorld = new BlockEnum[world.length][world[0].length];
		for (int r = 0; r < newWorld.length; r++) {
			for (int c = 0; c < newWorld[0].length; c++) {
				newWorld[r][c] = world[world.length-1-r][c];
			}
		}
		return newWorld;
	}
	public static void print(BlockEnum[][] world) {
		for(int i = world.length - 1; i >= 0; i--) {
			for(int j=0; j<world[0].length; j++) {
				if (world[i][j] != null) {
					System.out.print(world[i][j]);
				}
				else {
					System.out.print("N");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}