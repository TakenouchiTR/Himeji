//Program Name:   Chunk.java
//Date:           3/14/2020
//Programmer:     Shawn Carter
//Description:    This class represents a chunk stored in a region file.

import com.mojang.nbt.ByteArrayTag;
import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.IntArrayTag;
import com.mojang.nbt.ListTag;
import com.mojang.nbt.Tag;

public class Chunk 
{
	public static final int CHUNK_SIZE = 16;
	
	private CompoundTag levelTag;
	private int worldHeight;
	private int[][][] blockLight;
	private int[][][] sunLight;
	private int[][][] blocks; 
	private int[][][] metadata;
	private int[][] biome;
	
	public Chunk(CompoundTag baseTag) 
	{
		levelTag = baseTag.getCompound("Level");
		
		ListTag<? extends Tag> sections = levelTag.getList("Sections");
		
		worldHeight = 256;//((int)((CompoundTag)(sections.get(sections.size() - 1))).getByte("Y") + 1) 
				//* CHUNK_SIZE;
		
		blockLight = new int[CHUNK_SIZE][worldHeight][CHUNK_SIZE];
		sunLight = new int[CHUNK_SIZE][worldHeight][CHUNK_SIZE];
		blocks = new int[CHUNK_SIZE][worldHeight][CHUNK_SIZE];
		metadata = new int[CHUNK_SIZE][worldHeight][CHUNK_SIZE];
		biome = new int[CHUNK_SIZE][CHUNK_SIZE];
		
		for(int x = 0; x < CHUNK_SIZE; x++) 
		{
			for(int z = 0; z < CHUNK_SIZE; z++) 
			{
				for(int y = 0; y < worldHeight; y++) 
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
							//e.printStackTrace();
						}
					}
				}
			}
		}
		
		/*
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
		*/
	}
	
	public Chunk()
	{
		
	}
	
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
	
	private int getIndex(int x, int y, int z) 
	{
		int result;
		
		result = ((y * 16 + z) * 16 + x);
		
		return result;
	}
	
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
		for(int y = worldHeight - 1; y > 0; y--) 
		{
			// if the block reached is visible
			if(Block.isBlockVisible(blocks[x][y][z])) 
			{
				//Gets the light level of the block above it. If at worldHeight, 
				//  uses the block's sun light level
				int lightY = (y < worldHeight - 1 ? y + 1 : y);
				
				return new Block(blocks[x][y][z], metadata[x][y][z], y, sunLight[x][lightY][z], 
						blockLight[x][lightY][z], biome[x][z]);
			}
		}
		return new Block(0, 0, 0, 0, 0, 0);
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
	public int[][][] getTopColors()
	{
		int[][][] result = new int[CHUNK_SIZE][CHUNK_SIZE][2];
		
		for (int x = 0; x < CHUNK_SIZE; x++)
		{
			for (int z = 0; z < CHUNK_SIZE; z++)
			{
				Block block = getBlock(x, z);
				int y = getTopBlockY(x, z);
				int color = Block.getBlockColor(block.getBlockID(), block.getMetaData());
				
				result[x][z][0] = color;
				result[x][z][1] = y;
			}
		}
		
		return result;
	}

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
	
	public int getTopBlockY(int x, int z)
	{
		for(int y = worldHeight - 1; y > 0; y--) 
			if(Block.isBlockVisible(blocks[x][y][z])) 
				return y;
		
		return 0;
	}

	public int setAlphaToY(int color, int y)
	{
		int result;
		
		result = (color & 0x00FFFFFF);
		y <<= 24;
		result |= y;
		
		return result;
	}
}
