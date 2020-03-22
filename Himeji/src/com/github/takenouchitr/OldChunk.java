package com.github.takenouchitr;
//Program Name:   OldChunk.java
//Date:           3/14/2020
//Programmer:     Shawn Carter
//Description:    This class represents a chunk stored in a region file.

import com.mojang.nbt.ByteArrayTag;
import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.IntArrayTag;
import com.mojang.nbt.ListTag;
import com.mojang.nbt.Tag;

public class OldChunk extends Chunk 
{
	private int[][][] blockLight;
	private int[][][] sunLight;
	private int[][][] blocks; 
	private int[][][] metadata;
	private int[][] biome;
	
	/**
	 * Creates a Chunk using the old format for saving chunk data.
	 * @param baseTag CompoundTag containing the chunk's DataVersion and Level tags.
	 */
	public OldChunk(CompoundTag baseTag) 
	{
		super(baseTag);
		
		ListTag<? extends Tag> sections = levelTag.getList("Sections");
		
		blockLight = new int[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
		sunLight = new int[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
		blocks = new int[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
		metadata = new int[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
		biome = new int[CHUNK_SIZE][CHUNK_SIZE];
		
		for(int x = 0; x < CHUNK_SIZE; x++) 
		{
			for(int z = 0; z < CHUNK_SIZE; z++) 
			{
				for(int y = 0; y < CHUNK_HEIGHT; y++) 
				{
					blockLight[x][y][z] = 0;
					sunLight[x][y][z] = 15;
					blocks[x][y][z] = 0;
					metadata[x][y][z] = 0;
				}
				biome[x][z] = 0;
			}
		}
		
		boolean calcLight = levelTag.getBoolean("LightPopulated"); 
		
		for(int i = 0; i < sections.size(); i++) 
		{
			CompoundTag section = (CompoundTag)sections.get(i);
			
			int sectionY = (int)(section.getByte("Y")) * 16;
			if (sectionY < 0)
				continue;
			
			byte[] blockData = section.getByteArray("Blocks");
			byte[] blockLightData = section.getByteArray("BlockLight");
			byte[] skyLightData = section.getByteArray("SkyLight");
			byte[] metadataData = section.getByteArray("Data");
			
			if (blockData.length == 0)
				continue;
			
			for(int x = 0; x < CHUNK_SIZE; x++) 
			{
				for(int y = 0; y < CHUNK_SIZE; y++) 
				{
					for(int z = 0; z < CHUNK_SIZE; z++) 
					{
						try 
						{
							blocks[x][y + sectionY][z] = getPositiveByte(blockData[getIndex(x, y, z)]);
							metadata[x][y + sectionY][z] = getHalfIndexValue(metadataData, x, y, z);
							if(calcLight) 
							{
								blockLight[x][y + sectionY][z] = getHalfIndexValue(blockLightData, x, y, z);
								sunLight[x][y + sectionY][z] = getHalfIndexValue(skyLightData, x, y, z);
							}
						} 
						catch(Exception e) 
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		Tag biomeTag = levelTag.get("Biomes");
		
		if (biomeTag instanceof IntArrayTag)
		{
			int[] biomeData = levelTag.getIntArray("Biomes");
			for(int x = 0; x < CHUNK_SIZE; x++) 
			{
				for(int z = 0; z < CHUNK_SIZE; z++) 
				{
					try
					{
					biome[x][z] = biomeData[getIndex(x, z)];
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		else if (biomeTag instanceof ByteArrayTag)
		{
			byte[] biomeData = levelTag.getByteArray("Biomes");
			for(int x = 0; x < CHUNK_SIZE; x++) 
			{
				for(int z = 0; z < CHUNK_SIZE; z++) 
				{
					biome[x][z] = getPositiveByte(biomeData[getIndex(x, z)]);
				}
			}
		}
	}
	
	/**
	 * Returns the value of a byte as a positive integer.
	 * @param b byte to convert
	 * @return  integer of the positive form of the byte
	 */
	private int getPositiveByte(byte b) 
	{
		return (int)(b & 0x0f) + (int)((b & 0xf0) >> 4) * 16;
	}
	
	private int getHalfIndexValue(byte[] data, int x, int y, int z) 
	{
		int r = getIndex(x, y, z);
		int i = r / 2;
		int h = r % 2;
		byte b = data[i];
		
		if(h == 0) 
		{
		 	return (int)(b & 0x0f);
		} 
		else 
		{
			return (int)((b & 0xf0) >> 4);
		}
	}
	
	/**
	 * Gets the index for an array for an item stored at a chunk's x, y, z coordinate.
	 * @param x x coordinate, relative to the chunk
	 * @param y y coordinate
	 * @param z z coordinate, relative to the chunk
	 * @return  index of an array for the given coordinates
	 */
	private int getIndex(int x, int y, int z) 
	{
		int result;
		
		result = ((y * 16 + z) * 16 + x);
		
		return result;
	}
	
	/**
	 * Gets the index for an array for an item stored at a chunk's x, z coordinate.
	 * @param x x coordinate, relative to the chunk
	 * @param z z coordinate, relative to the chunk
	 * @return  index of an array for the given coordinates
	 */
	private int getIndex(int x, int z) 
	{
		int result;
		
		result = (z * 16 + x);
		
		return result;
	}
	
	/**
	 * Returns the top-most visible block at (x,z).
	 * @return a newly instanced Block object
	 */
	public Block getBlock(int x, int z) 
	{
		// starting at the top of the map and going down
		for(int y = CHUNK_HEIGHT - 1; y > 0; y--) 
		{
			// if the block reached is visible
			if(Block.isBlockVisible(blocks[x][y][z])) 
			{
				return new Block(blocks[x][y][z], metadata[x][y][z]);
			}
		}
		return new Block(0, 0);
	}
	
	/**
	 * Creates a two-dimensional Block array of the top-most blocks of each x, z column.
	 * @return Block array of the top-most blocks of each x, z column
	 */
	public Block[][] getTopBlocks()
	{
		Block[][] result = new Block[CHUNK_SIZE][CHUNK_SIZE];
		
		for (int x = 0; x < CHUNK_SIZE; x++)
		{
			for (int z = 0; z < CHUNK_SIZE; z++)
			{
				result[x][z] = getBlock(x, z);
			}
		}
		
		return result;
	}
	
	/**
	 * Creates a two-dimensional int array of the ARGB values for the top-most blocks of 
	 * each x, z column.
	 * @return int array of the colors for the top-most blocks of each x, z column
	 */
	@Override
	public int[][][] getTopColors(int startY)
	{
		int[][][] result = new int[CHUNK_SIZE][CHUNK_SIZE][2];
		
		for (int x = 0; x < CHUNK_SIZE; x++)
		{
			for (int z = 0; z < CHUNK_SIZE; z++)
			{
				int y = getTopBlockY(x, z, startY);
				int dy = y;
				if (Himeji.renderUnderWater())
					dy = getTopBlockYIgnoreWater(x, z, startY);
				
				int color = Block.getBlockColor(blocks[x][dy][z], metadata[x][dy][z], biome[x][z]);
				
				//Adds a blue effect to blocks under water
				if (y != dy)
				{
					int waterColor = Block.getBlockColor(8, 0, biome[x][z]);
					int a = 0;
					int r = 0;
					int g = 0;
					int b = 0;
					
					a = 0xFF000000;
					
					r += ((color & 0x00FF0000) >>> 16);
					r += ((waterColor & 0x00FF0000) >>> 16) * 3;
					g += ((color & 0x0000FF00) >>> 8);
					g += ((waterColor & 0x0000FF00) >>> 8) * 3;
					b += (color & 0x000000FF);
					b += (waterColor & 0x000000FF) * 3;
					
					r /= 4;
					g /= 4;
					b /= 4;
					
					r <<= 16;
					g <<= 8;
					
					color = a | r | g | b;
				}
				
				result[x][z][0] = color;
				result[x][z][1] = dy;
			}
		}
		
		return result;
	}

	/**
	 * Creates a three-dimensional int array of the ARGB values for the top-most blocks of 
	 * each x, z and their y-value.
	 * @return int array of the colors for the top-most blocks of each x, z column
	 */
	@Override
	public int[][][] getTopColors()
	{
		return getTopColors(CHUNK_HEIGHT - 1);
	}
	
	/**
	 * Gets the top block of a given x, z column that is not listed as invisible.
	 * @param x       the Chunk's x coord, relative to the chunk
	 * @param z       the Chunk's z coord, relative to the chunk
	 * @param startY  the y coord to start searching at
	 * @return        a color stored as an ARGB integer
	 */
	@Override
	public int getTopBlockY(int x, int z, int startY)
	{
		for(int y = startY; y > 0; y--) 
			if(Block.isBlockVisible(blocks[x][y][z])) 
				return y;
		
		return 0;
	}
	
	/**
	 * Gets the top block of a given x, z column that is not listed as invisible or is 
	 *   considered water.
	 * @param x       the Chunk's x coord, relative to the chunk
	 * @param z       the Chunk's z coord, relative to the chunk
	 * @param startY  the y coord to start searching at
	 * @return        a color stored as an ARGB integer
	 */
	@Override
	public int getTopBlockYIgnoreWater(int x, int z, int startY)
	{
		for(int y = startY; y > 0; y--) 
		{
			int block = blocks[x][y][z];
			if(Block.isBlockVisible(block) && block != 8 && block != 9) 
				return y;
		}
		
		return 0;
	}
}
