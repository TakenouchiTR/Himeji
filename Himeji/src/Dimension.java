//Program Name:   Dimension.java
//Date:           3/13/2020
//Programmer:     Shawn Carter
//Description:    This class represents a dimension of a Minecraft world.

import java.io.*;
import com.mojang.nbt.*;

public class Dimension 
{
	public static final int REGION_SIZE = 32;
	
	protected int blockHeight;
	protected int blockWidth;
	protected int chunkHeight;
	protected int chunkWidth;
	protected int chunkXOffset;
	protected int chunkZOffset;
	protected boolean[][] regionRenderFlags;
	protected boolean[][] chunkRenderFlags;
	protected FileIntMatrix colorGrid;
	private byte[][] yVals;
	protected File directory;
	private MapImage image;
	
	/**
	 * Creates a Dimension with a size determined by the files in the region
	 * directory.
	 * @param dimDir Folder that contains the dimension's data
	 */
	public Dimension(File dimDir)
	{
		int[] size;
		
		directory = dimDir;
		size = calcDimSize(dimDir);
		setChunkHeight(size[0]);
		setChunkWidth(size[1]);
		chunkXOffset = size[2];
		chunkZOffset = size[3];
	}
	
	/**
	 * Calculates how large the dimension is from the file names in its
	 * directory.
	 * @param dimension Folder that contains the dimension's data
	 * @return          Array containing both the height and width in chunks, 
	 *                  and the chunk x and z offset
	 */
	public static int[] calcDimSize(File dimension)
	{
		int[] result = {0, 0, 0, 0};
		int maxHeight = 0;
		int maxWidth = 0;
		int minHeight = -1;
		int minWidth = -1;
		
		File[] chunks = dimension.listFiles();

		if (Himeji.SHOW_ALL_EVENTS)
			System.out.println("Getting chunk files...");
		for (File c : chunks)
		{
			String loc = c.getName();

			if (Himeji.SHOW_ALL_EVENTS)
				System.out.printf("Checking file %s\n", loc);
			
			String[] locSplit = loc.split("[.]");
			
			int fileWidth = Integer.parseInt(locSplit[1]);
			int fileHeight = Integer.parseInt(locSplit[2]);
			
			if (fileHeight < minHeight)
				minHeight = fileHeight;
			else if (fileHeight > maxHeight)
				maxHeight = fileHeight;
			
			if (fileWidth < minWidth)
				minWidth = fileWidth;
			else if (fileWidth > maxWidth)
				maxWidth = fileWidth;
		}
		
		result[1] = (maxWidth - minWidth + 1) * 32;
		result[0] = (maxHeight - minHeight + 1) * 32;
		result[2] = Math.abs(minWidth);
		result[3] = Math.abs(minHeight);
		
		if (Himeji.SHOW_ALL_EVENTS)
			System.out.printf("Height: %1$d\nWidth : %2$d\n", 
					result[0], result[1]);
		
		return result;
	}
	
	/**
	 * Validates whether a dimension exists. A dimension exists if it has been 
	 * visited in-game, causing specific files and folders to generate.
	 * @param dimension Folder that contains the dimension's data
	 * @return          Returns true if the dimension folder contains a valid dimension
	 */
	public static boolean validateGen(File dir)
	{
		boolean result = false;
		
		File[] dimFolders;
		
		if (dir.isDirectory())
		{
			dimFolders = dir.listFiles(new FilenameFilter()
				{
					@Override
					public boolean accept(File current, String name)
					{
						return new File(current, name).isDirectory();
					}
				});
			
			boolean hasRegion = false;
			boolean hasPoi = false;
			
			for (File f : dimFolders)
			{
				switch(f.getName())
				{
					case "region":
						hasRegion = true;
						break;
					case "poi":
						hasPoi = true;
						break;
				}
			}
			
			if (hasRegion && hasPoi)
				result = true;
		}
		
		return result;
	}
	
	public void createRenderGrid()
	{
		File[] regions;
		
		image = new MapImage(blockWidth, blockHeight, 
				new File("C:\\Users\\TOTak\\AppData\\Roaming\\.minecraft\\saves\\ping.png"));
		
		/*try
		{
			File gridLoc = File.createTempFile("gen", ".txt");
			colorGrid = new FileIntMatrix(gridLoc, blockWidth, blockHeight);
		}
		catch (Exception e)
		{
			
		}*/
		chunkRenderFlags = new boolean[chunkWidth][chunkHeight];
		yVals = new byte[blockWidth][blockHeight];
		
		regions = directory.listFiles();
		
		for (File regionFile : regions)
		{
			Region region;
			Region upRegion;
			Region rightRegion;
			Chunk chunk;
			Chunk upChunk;
			Chunk rightChunk;
			File upFile;
			File rightFile;
			String regionName;
			String[] nameSplit;
			int regionX;
			int regionZ;
			int curYVal;
			int upYVal;
			int rightYVal;
			
			regionName = regionFile.getName();
			nameSplit = regionName.split("[.]");
			regionX = Integer.parseInt(nameSplit[1]);
			regionZ = Integer.parseInt(nameSplit[2]);
			
			
			if (Himeji.SHOW_ALL_EVENTS)
				System.out.println("Reading " + regionName);
			
			region = new Region(regionFile);
			upFile = new File(String.format(regionFile.getParent() + 
					"r.%1$d.%2$d.mca", regionX, regionZ - 1));
			rightFile = new File(String.format(regionFile.getParent() + 
					"r.%1$d.%2$d.mca", regionX + 1, regionZ));
			
			if (upFile.exists())
				upRegion = new Region(upFile);
			else
				upRegion = null;
			if (rightFile.exists())
				rightRegion = new Region(rightFile);
			else
				rightRegion = null;
			
			for (int chunkX = 0; chunkX < REGION_SIZE; chunkX++)
			{
				for (int chunkZ = 0; chunkZ < REGION_SIZE; chunkZ++)
				{
					if (!region.hasChunk(chunkX, chunkZ))
						continue;
					
					try
					{
						CompoundTag chunkTag = region.getChunk(chunkX, chunkZ);
						if (chunkTag == null)
							continue;
						
						upChunk = null;
						rightChunk = null;
						
						if (chunkZ == 0)
						{
							if (upRegion != null)
							{
								if (upRegion.hasChunk(chunkX, 31))
								{
									CompoundTag upTag = upRegion.getChunk(chunkX, REGION_SIZE - 1);
									if (upTag != null)
									{
										if (upTag.getInt("DataVersion") > 1519)
											upChunk = new NewChunk(upTag);
										else
											upChunk = new Chunk(upTag);
									}
								}
							}
						}
						else
						{
							if (region.hasChunk(chunkX, chunkZ - 1))
							{
								CompoundTag upTag = region.getChunk(chunkX, chunkZ - 1);
								if (upTag != null)
								{
									if (upTag.getInt("DataVersion") > 1519)
										upChunk = new NewChunk(upTag);
									else
										upChunk = new Chunk(upTag);
								}
							}
						}
						
						if (chunkX == REGION_SIZE - 1)
						{
							if (rightRegion != null)
							{
								if (rightRegion.hasChunk(0, chunkZ))
								{
									CompoundTag rightTag = rightRegion.getChunk(0, chunkZ);
									if (rightTag != null)
									{
										if (rightTag.getInt("DataVersion") > 1519)
											rightChunk = new NewChunk(rightTag);
										else
											rightChunk = new Chunk(rightTag);
									}
								}
							}
						}
						else
						{
							if (region.hasChunk(chunkX + 1, chunkZ))
							{
								CompoundTag rightTag = region.getChunk(chunkX + 1, chunkZ);
								if (rightTag != null)
								{
									if (rightTag.getInt("DataVersion") > 1519)
										rightChunk = new NewChunk(rightTag);
									else
										rightChunk = new Chunk(rightTag);
								}
							}
						}
						
						int chunkVersion = chunkTag.getInt("DataVersion");
						
						if (chunkVersion >= 1519)
							chunk = new NewChunk(chunkTag);
						else
							chunk = new Chunk(chunkTag);
						
						int gridX = (chunk.getX() + chunkXOffset * REGION_SIZE) * Chunk.CHUNK_SIZE;
						int gridZ = (chunk.getZ() + chunkZOffset * REGION_SIZE) * Chunk.CHUNK_SIZE;
						
						chunkRenderFlags[gridX / 16][gridZ / 16] = true;
						int[][][] chunkMap = chunk.getTopColors();
						
						if (chunkMap == null)
							continue;
						
						int xPos;
						int zPos;
						int curColor;
						
						for (int blockX = 0; blockX < Chunk.CHUNK_SIZE; blockX++)
						{
							xPos = gridX + blockX;
							for (int blockZ = 0; blockZ < Chunk.CHUNK_SIZE; blockZ++)
							{
								zPos = gridZ + blockZ;
								
								curColor = chunkMap[blockX][blockZ][0];
								curYVal = chunkMap[blockX][blockZ][1];
								
								if (blockZ == 0)
								{
									if (upChunk == null)
										upYVal = curYVal;
									else
										upYVal = upChunk.getTopBlockYIgnoreWater(blockX, Chunk.CHUNK_SIZE - 1);
								}
								else
								{
									upYVal = chunkMap[blockX][blockZ - 1][1];
								}
								
								if (blockX == Chunk.CHUNK_SIZE - 1)
								{
									if (rightChunk == null)
										rightYVal = curYVal;
									else
										rightYVal = rightChunk.getTopBlockYIgnoreWater(0, blockZ);
								}
								else
								{
									rightYVal = chunkMap[blockX + 1][blockZ][1];
								}
								
								curColor = applyShading(curColor, curYVal, upYVal, rightYVal);
								
								image.setPixel(xPos, zPos, curColor);
								
								//yVals[xPos][zPos] = (byte)(chunkMap[blockX][blockZ][1] - 128);
								//colorGrid.set(xPos, zPos, curColor);
							}
						}
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		System.out.println("Applying shading");
		//applyShading();
	}
	
	public int applyShading(int color, int yVal, int upYVal, int rightYVal)
	{
		int a, r, g, b;
		
		if (yVal < upYVal || yVal < rightYVal)
		{
			a = (color & 0xFF000000);
			r = (int) (((color & 0x00FF0000) >>> 16) * .85f);
			g = (int) (((color & 0x0000FF00) >>> 8) * .85f);
			b = (int) ((color & 0x000000FF) * .85f);
			
			if (r < 0)
				r = 0;
			if (g < 0)
				g = 0;
			if (b < 0)
				b = 0;
			
			color = a | (r << 16) | (g << 8) | b;
		}
		else if (yVal > upYVal || yVal > rightYVal)
		{
			a = (color & 0xFF000000);
			r = (int) (((color & 0x00FF0000) >>> 16) * 1.15f);
			g = (int) (((color & 0x0000FF00) >>> 8) * 1.15f);
			b = (int) ((color & 0x000000FF) * 1.15f);
			
			if (r > 255)
				r = 255;
			if (g > 255)
				g = 255;
			if (b > 255)
				b = 255;
			
			color = a | (r << 16) | (g << 8) | b;
		}
		
		return color;
	}
	
	public void applyShading()
	{
		int width = colorGrid.getRows() - 1;
		int height = colorGrid.getCols();
		int aboveY;
		int rightY;
		int curY = 0;
		int a, r, g, b, curColor;
		
		for (int z = 1; z < height; z++)
		{
			for (int x = 1; x < width; x++)
			{
				if (!getChunkRenderFlag(x / 16, z / 16))
				{
					x += 15;
					continue;
				}
				try
				{
					
					rightY = yVals[x + 1][z];
					aboveY = yVals[x][z - 1];
					curY = yVals[x][z];
					
					if (curY < aboveY || curY < rightY)
					{
						curColor = colorGrid.get(x, z);
						
						a = (curColor & 0xFF000000);
						r = (int) (((curColor & 0x00FF0000) >>> 16) * .85f);
						g = (int) (((curColor & 0x0000FF00) >>> 8) * .85f);
						b = (int) ((curColor & 0x000000FF) * .85f);
						
						if (r < 0)
							r = 0;
						if (g < 0)
							g = 0;
						if (b < 0)
							b = 0;
						
						curColor = a | (r << 16) | (g << 8) | b;
						
						colorGrid.set(x, z, curColor);
					}
					else if (curY > aboveY || curY > rightY)
					{
						curColor = colorGrid.get(x, z);
						
						a = (curColor & 0xFF000000);
						r = (int) (((curColor & 0x00FF0000) >>> 16) * 1.15f);
						g = (int) (((curColor & 0x0000FF00) >>> 8) * 1.15f);
						b = (int) ((curColor & 0x000000FF) * 1.15f);
						
						if (r > 255)
							r = 255;
						if (g > 255)
							g = 255;
						if (b > 255)
							b = 255;
						
						curColor = a | (r << 16) | (g << 8) | b;
						
						colorGrid.set(x, z, curColor);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			
			if (z % 100 == 0)
				System.out.println("Applied to " + z + "/" + height);
		}
	}
	
	/**
	 * Returns the Dimension's width in chunks.
	 * @return Width of dimension in chunks.
	 */
	public int getChunkWidth() 
	{
		return chunkWidth;
	}
	
	/**
	 * Sets the width of a dimension in chunks. Sets the width in blocks
	 * as well.
	 * @param chunkWidth Width of dimension in chunks
	 */
	public void setChunkWidth(int chunkWidth) 
	{
		this.chunkWidth = chunkWidth;
		blockWidth = chunkWidth * 16;
	}
	
	/**
	 * Returns the Dimension's height in chunks.
	 * @return Height of dimension in chunks.
	 */
	public int getChunkHeight() 
	{
		return chunkHeight;
	}
	
	/**
	 * Sets the height of a dimension in chunks. Sets the height in blocks
	 * as well.
	 * @param chunkWidth Width of dimension in chunks
	 */
	public void setChunkHeight(int chunkHeight) 
	{
		this.chunkHeight = chunkHeight;
		blockHeight = chunkHeight * 16;
	}
	
	/**
	 * Returns the Dimension's width in blocks.
	 * @return Width of dimension in blocks.
	 */
	public int getBlockWidth() 
	{
		return blockWidth;
	}
	
	/**
	 * Returns the Dimension's height in blocks.
	 * @return Height of dimension in blocks.
	 */
	public int getBlockHeight() 
	{
		return blockHeight;
	}
	
	public int getBlockColorAt(int x, int z)
	{
		try
		{
			return colorGrid.get(x, z);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	public boolean getChunkRenderFlag(int x, int z)
	{
		return chunkRenderFlags[x][z];
	}
	
	public int getChunkXOffset()
	{
		return chunkXOffset;
	}
	
	public int getYCoord(int color)
	{
		return color>>>24;
	}
	
	public int getChunkZOffset()
	{
		return chunkZOffset;
	}

	public void closeFileMatrix()
	{
		try
		{
			colorGrid.close();
		}
		catch (Exception e)
		{
			
		}
	}

	public void render()
	{
		image.renderImage();
	}
}