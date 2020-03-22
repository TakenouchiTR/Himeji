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
	
	/**
	 * Creates a chunk and sets the NBT for the chunk's info. The rest of the 
	 *   actions are dependent on whether the chunk is in the new or old format.
	 * @param baseTag CompoundTag containing the chunk's DataVersion and Level tags.
	 */
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
	
	/**
	 * Gets the Chunk's Level NBT.
	 * @return the chunk's Level NBT
	 */
	public CompoundTag getTag()
	{
		return levelTag;
	}
	
	/**
	 * Gets the top block of a given x, z column that is not listed as invisible.
	 * @param x       the Chunk's x coord, relative to the chunk
	 * @param z       the Chunk's z coord, relative to the chunk
	 * @param startY  the y coord to start searching at
	 * @return        a color stored as an ARGB integer
	 */
	public abstract int getTopBlockY(int x, int z, int startY);
	
	/**
	 * Gets the top block of a given x, z column that is not listed as invisible or is 
	 *   considered water.
	 * @param x       the Chunk's x coord, relative to the chunk
	 * @param z       the Chunk's z coord, relative to the chunk
	 * @param startY  the y coord to start searching at
	 * @return        a color stored as an ARGB integer
	 */
	public abstract int getTopBlockYIgnoreWater(int x, int z, int startY);
}
