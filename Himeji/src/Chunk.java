//Program Name:   Chunk.java
//Date:           3/14/2020
//Programmer:     Shawn Carter
//Description:    This class represents a chunk stored in a region file.

import com.mojang.nbt.CompoundTag;

public abstract class Chunk 
{
	public static final int CHUNK_SIZE = 16;
	public static final int CHUNK_HEIGHT = 256;
	
	protected CompoundTag levelTag;
	protected int worldHeight;
	
	public Chunk(CompoundTag baseTag) 
	{
		levelTag = baseTag.getCompound("Level");
	}
	
	/**
	 * Creates a three-dimensional int array of the ARGB values for the top-most blocks of 
	 * each x, z and their y-value.
	 * @param startY Y value to start searching at
	 * @return int array of the colors for the top-most blocks of each x, z column
	 */
	public abstract int[][][] getTopColors(int startY);

	/**
	 * Creates a three-dimensional int array of the ARGB values for the top-most blocks of 
	 * each x, z and their y-value.
	 * @return int array of the colors for the top-most blocks of each x, z column
	 */
	public abstract int[][][] getTopColors();
	
	/**
	 * Gets the chunk's x position for the current Chunk, relative to the entire world.
	 * @return chunk x position
	 */
	public int getX() 
	{
		return levelTag.getInt("xPos");
	}
	
	/**
	 * Gets the chunk's z position for the current Chunk, relative to the entire world.
	 * @return chunk z position
	 */
	public int getZ() 
	{
		return levelTag.getInt("zPos");
	}
	
	public CompoundTag getTag()
	{
		return levelTag;
	}
	
	public abstract int getTopBlockY(int x, int z, int startY);
	
	public abstract int getTopBlockYIgnoreWater(int x, int z, int startY);
}
