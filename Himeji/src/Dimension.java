//Himeji Name:   Dimension.java
//Date:           3/13/2020
//Himejimer:     Shawn Carter
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
	protected Block[][] renderGrid;
	protected int colorGrid[][];
	protected File directory;
	
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
		renderGrid = new Block[blockWidth][blockHeight];
		colorGrid = new int[blockWidth][blockHeight];
		chunkRenderFlags = new boolean[chunkWidth][chunkHeight];
		regionRenderFlags = new boolean[chunkWidth / 32][chunkHeight / 32];
		
		regions = directory.listFiles();
		
		for (File regionFile : regions)
		{
			if (Himeji.SHOW_ALL_EVENTS)
				System.out.println("Reading " + regionFile.getName());
			
			Region region = new Region(regionFile);
			
			for (int chunkX = 0; chunkX < REGION_SIZE; chunkX++)
			{
				for (int chunkZ = 0; chunkZ < REGION_SIZE; chunkZ++)
				{
					if (!region.hasChunk(chunkX, chunkZ))
						continue;
					try
					{
						CompoundTag chunkTag = region.getChunk(chunkX, chunkZ);
						int chunkVersion = chunkTag.getInt("DataVersion");
						Chunk chunk;
						
						if (chunkVersion >= 1519)
							chunk = new NewChunk(chunkTag);
						else
							chunk = new Chunk(chunkTag);
						
						int gridX = (chunk.getX() + chunkXOffset * REGION_SIZE) * Chunk.CHUNK_SIZE;
						int gridZ = (chunk.getZ() + chunkZOffset * REGION_SIZE) * Chunk.CHUNK_SIZE;
						
						chunkRenderFlags[gridX / 16][gridZ / 16] = true;
						int[][] chunkMap = chunk.getTopColors();
						
						for (int blockX = 0; blockX < Chunk.CHUNK_SIZE; blockX++)
						{
							for (int blockZ = 0; blockZ < Chunk.CHUNK_SIZE; blockZ++)
							{
								int xPos = gridX + blockX;
								int zPos = gridZ + blockZ;
								colorGrid[xPos][zPos] = chunkMap[blockX][blockZ];
							}
						}
					}
					catch (Exception e) 
					{
						//e.printStackTrace();
					}
				}
			}
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
	
	public Block getBlockAt(int x, int z)
	{
		if (renderGrid == null || x >= renderGrid.length || z >= renderGrid.length)
			return null;
		return renderGrid[x][z];
	}
	
	public int getBlockColorAt(int x, int z)
	{
		return colorGrid[x][z];
		
		/*Block block = getBlockAt(x, z);
		if (block == null)
			return 0;
		return Block.getBlockColor(block.getBlockID(), block.getMetaData());*/
	}

	public boolean getChunkRenderFlag(int x, int z)
	{
		return true;
		//return chunkRenderFlags[x][z];
	}
	
	public int getChunkXOffset()
	{
		return chunkXOffset;
	}
	
	public int getChunkZOffset()
	{
		return chunkZOffset;
	}
}
