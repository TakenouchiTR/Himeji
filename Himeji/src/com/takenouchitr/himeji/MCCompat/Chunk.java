/*
   Copyright (C) 2020  Shawn Carter
   Contact: shawn.jf.carter@gmail.com
   
   This file is part of Himeji Map Viewer (HMV).

    HMV is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    HMV is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with HMV.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.takenouchitr.himeji.MCCompat;

import java.util.Arrays;
import java.util.HashMap;

import com.mojang.nbt.ByteArrayTag;
import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.IntArrayTag;
import com.mojang.nbt.ListTag;
import com.mojang.nbt.Tag;
import com.takenouchitr.himeji.SessionProperties;

public class Chunk 
{
	public static final int CHUNK_SIZE = 16;
	public static final int CHUNK_HEIGHT = 256;
	
	private int highestBlock;
	private int biomeHeight;
	private int version;
	private String[][][] blocks;
	private int[][][] biome;
	private int[][][] blockLight;
	private int[][][] skyLight;
	private CompoundTag levelTag;
	
	/**
	 * Creates a chunk and sets the NBT for the chunk's info. The rest of the 
	 *   actions are dependent on whether the chunk is in the new or old format.
	 * @param baseTag CompoundTag containing the chunk's DataVersion and Level tags.
	 */
	public Chunk(CompoundTag baseTag) 
	{
		blocks = new String[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
		blockLight = new int[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
		skyLight = new int[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
		
		levelTag = baseTag.getCompound("Level");
		if (!baseTag.contains("DataVersion"))
		{
			readMcrChunk();
			return;
		}
		
		version = baseTag.getInt("DataVersion");
		
		if (version < 1519)
			readPreFlatChunk();
		else
			readPostFlatChunk();
	}
	
	/**
	 * Reads a chunk using the McRegion format
	 */
	private void readMcrChunk()
	{
		biome = new int[CHUNK_SIZE][1][CHUNK_HEIGHT];
		biomeHeight = CHUNK_HEIGHT;
		highestBlock = 127;
		
		byte[] blockData = levelTag.getByteArray("Blocks");
		byte[] metadataData = levelTag.getByteArray("Data");
		
		byte[] blockLightData = null;
		byte[] skyLightData = null;
		
		if (SessionProperties.isRenderLight())
		{
			skyLightData = levelTag.getByteArray("SkyLight");
			blockLightData = levelTag.getByteArray("BlockLight");
		}
		int index = 0;
		
		for (int x = 0; x < CHUNK_SIZE; x++) 
		{
			for (int z = 0; z < CHUNK_SIZE; z++) 
			{
				for (int y = 0; y < 128; y++) 
				{
					int block = getPositiveByte(blockData[index]);
					int meta = getHalfIndexValue(metadataData, index);
					blocks[x][y][z] = Block.getNamespacedId(block, meta);
					
					if (SessionProperties.isRenderLight())
					{
						skyLight[x][y][z] = getHalfIndexValue(skyLightData, index);
						blockLight[x][y][z] = getHalfIndexValue(blockLightData, index);
					}
					
					index++;
				}
				biome[x][0][z] = Biome.FOREST.id;
			}
		}
	}
	
	/**
	 * Reads a chunk using the Anvil format, pre-flattening (<1.13)
	 */
	private void readPreFlatChunk()
	{
		biome = new int[CHUNK_SIZE][1][CHUNK_HEIGHT];
		biomeHeight = CHUNK_HEIGHT;
		
		ListTag<? extends Tag> sections = levelTag.getList("Sections");
		
		//Loads up the blocks stored in each of the sections
		for(int i = 0; i < sections.size(); i++) 
		{
			CompoundTag section = (CompoundTag)sections.get(i);
			
			int sectionY = (int)(section.getByte("Y")) * 16;
			if (sectionY < 0)
				continue;
			
			highestBlock = highestBlock > sectionY + 15 ? highestBlock : sectionY + 15;
			
			byte[] blockData = section.getByteArray("Blocks");
			byte[] metadataData = section.getByteArray("Data");
			byte[] blockLightData = null;
			byte[] skyLightData = null;
			
			if (SessionProperties.isRenderLight())
			{
				blockLightData = section.getByteArray("BlockLight");
				skyLightData = section.getByteArray("SkyLight");
			}
			
			//Skips the section if there is no block data
			if (blockData.length == 0)
				continue;
			
			for(int x = 0; x < CHUNK_SIZE; x++) 
			{
				for(int y = 0; y < CHUNK_SIZE; y++) 
				{
					for(int z = 0; z < CHUNK_SIZE; z++) 
					{
						int block = getPositiveByte(blockData[getIndex(x, y, z)]);
						int meta = getHalfIndexValue(metadataData, x, y, z);
						blocks[x][y + sectionY][z] = Block.getNamespacedId(block, meta);
						
						if (SessionProperties.isRenderLight())
						{
							blockLight[x][y + sectionY][z] = getHalfIndexValue(blockLightData, x, y, z);
							skyLight[x][y + sectionY][z] = getHalfIndexValue(skyLightData, x, y, z);
						}
					}
				}
			}
		}
		
		Tag biomeTag = levelTag.get("Biomes");
		
		//Loads the new, IntArray biome tag
		if (biomeTag instanceof IntArrayTag)
		{
			int[] biomeData = levelTag.getIntArray("Biomes");
			
			//Defaults the biome to Forest if the data is missing for some reason.
			//It happened in one file, and I cannot figure out why.
			if (biomeData.length == 0)
			{
				biomeData = new int[256];
				Arrays.fill(biomeData, Biome.FOREST.id);
				return;
			}
			
			for(int x = 0; x < CHUNK_SIZE; x++) 
			{
				for(int z = 0; z < CHUNK_SIZE; z++) 
				{ 
					biome[x][0][z] = biomeData[getIndex(x, z)];
				}
			}
		}
		//Loads the old, ByteArray biome tag
		else if (biomeTag instanceof ByteArrayTag)
		{
			byte[] biomeData = levelTag.getByteArray("Biomes");
			
			for(int x = 0; x < CHUNK_SIZE; x++) 
			{
				for(int z = 0; z < CHUNK_SIZE; z++) 
				{
					biome[x][0][z] = getPositiveByte(biomeData[getIndex(x, z)]);
				}
			}
		}
	}
	
	/**
	 * Reads a chunk using the Anvil format, post-flattening
	 */
	private void readPostFlatChunk()
	{
		Section[] sections = null;
		
		ListTag<? extends Tag> sectionTags = levelTag.getList("Sections");
		
		if (sectionTags.size() != 0)
			sections = new Section[sectionTags.size()];
		
		blocks = new String[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
		
		//Stops if there is no block data stored in the tag
		if (sections == null)
			return;
		
		//Loads data from each of the sections
		for (int i = 0; i < sections.length; i++) 
		{
			sections[i] = new Section((CompoundTag)sectionTags.get(i));
			String[][][] sectionBlocks;
			
			if (version >= 2529)
				sectionBlocks = sections[i].getTruncatedBlocks();
			else
				sectionBlocks = sections[i].getBlocks();
			
			int sectionY = (sections[i].getSectionTag().getByte("Y")) * 16;
			
			//Skips a section if there are no blocks stored
			if (sectionBlocks == null)
				continue;
			
			byte[] blockLightData = null;
			byte[] skyLightData = null;
			
			if (SessionProperties.isRenderLight())
			{
				blockLightData = sections[i].getBlockLight();
				skyLightData = sections[i].getSkyLight();
			}
			
			highestBlock = highestBlock > sectionY + 15 ? highestBlock : sectionY + 15;
			
			//Loads each of the blocks in the section
			for(int x = 0; x < CHUNK_SIZE; x++) 
			{
				for(int y = 0; y < CHUNK_SIZE; y++) 
				{
					for(int z = 0; z < CHUNK_SIZE; z++) 
					{
						blocks[x][y + sectionY][z] = sectionBlocks[x][y][z];
						
						if (blockLightData != null)
							blockLight[x][y + sectionY][z] = getHalfIndexValue(blockLightData, x, y, z);
						if (skyLightData != null)
							skyLight[x][y + sectionY][z] = getHalfIndexValue(skyLightData, x, y, z);
					}
				}
			}
		}
		
		int dataLoc = 0;
		int[] biomeData = levelTag.getIntArray("Biomes");
		
		//Loads new, 3D-Biomes (1.15+)
		if (biomeData.length == 1024)
		{
			biome = new int[CHUNK_SIZE / 4][CHUNK_HEIGHT / 4][CHUNK_SIZE / 4];
			biomeHeight = 4;
			
			for (int y = 0; y < CHUNK_HEIGHT / 4; y++)
			{
				for(int z = 0; z < CHUNK_SIZE / 4; z++)
				{
					for(int x = 0; x < CHUNK_SIZE / 4; x++) 
					{	
						biome[x][y][z] = biomeData[dataLoc];
						dataLoc++;
					}
				}
			}
			
		}
		//Loads old, 2D-Biomes (pre 1.15)
		else
		{
			biome = new int[CHUNK_SIZE][1][CHUNK_SIZE];
			biomeHeight = CHUNK_HEIGHT;
			
			for(int z = 0; z < CHUNK_SIZE; z++) 
			{
				for(int x = 0; x < CHUNK_SIZE; x++) 
				{
					biome[x][0][z] = biomeData[dataLoc];
					dataLoc++;
				}
			}
		}
	}
	
	/**
	 * reads a chunk using the Anvil format, post palette expansion
	 */
	@SuppressWarnings("unused")
	private void readPostExpansionChunk()
	{
		
	}
	
	/*
	 * Returns the value of a byte as a positive integer.
	 * @param b byte to convert
	 * @return  integer of the positive form of the byte
	 */
	private int getPositiveByte(byte b) 
	{
		return (int)(b & 0xFF);
	}
	
	/**
	 * Gets the half of the value stored at the index of a byte array. The index 
	 * is half of what the index is for a given block, rounded down. Even number
	 * indices will give the last four bits, while odd will give the first four. 
	 * @param data byte array containing data for all blocks in a section
	 * @param x    x coordinate for the block
	 * @param y    y coordinate for the block
	 * @param z    z coordinate for the block
	 * @return     half the value stored at the index
	 */
	private int getHalfIndexValue(byte[] data, int x, int y, int z) 
	{
		int result = 0;
		int index = getIndex(x, y, z);
		int halfIndex = index / 2;
		boolean isEven = (index % 2) == 0;
		byte b = data[halfIndex];
		
		if(isEven)
		 	result = (int)(b & 0x0f);
		else
			result = (int)((b & 0xf0) >> 4);
		
		return result;
	}
	
	/**
	 * Gets the half of the value stored at the index of a byte array. The index 
	 * is half of what the index is for a given block, rounded down. Even number
	 * indices will give the last four bits, while odd will give the first four. 
	 * @param data byte array containing data for all blocks in a section
	 * @param index Index of the array to return.
	 * @return     half the value stored at the index
	 */
	private int getHalfIndexValue(byte[] data, int index) 
	{
		int result = 0;
		int halfIndex = index / 2;
		boolean isEven = (index % 2) == 0;
		byte b = data[halfIndex];
		
		if(isEven)
		 	result = (int)(b & 0x0f);
		else
			result = (int)((b & 0xf0) >> 4);
		
		return result;
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
	
	/*
	 * Gets the top block of a given x, z column that is not listed as invisible.
	 * @param x       the Chunk's x coord, relative to the chunk
	 * @param z       the Chunk's z coord, relative to the chunk
	 * @param startY  the y coord to start searching at
	 * @return        a color stored as an ARGB integer
	 */
	public int[][][] getTopColors(int startY, int endY)
	{
		if (blocks == null || biome == null)
			return null;
		
		//[x][z][0] = color
		//[x][z][1] = y coord
		//[x][z][2] = block light
		//[x][z][3] = sky light
		int[][][] result = new int[CHUNK_SIZE][CHUNK_SIZE][4];
		
		startY = (startY < highestBlock) ? startY : highestBlock;
		HashMap<Integer, int[]> unknownBiomes = Block.getUnknownBiomes();
		
		for (int x = 0; x < CHUNK_SIZE; x++)
		{
			for (int z = 0; z < CHUNK_SIZE; z++)
			{
				int y = getTopBlockY(x, z, startY, endY);
				int dy = y;
				if (SessionProperties.isRenderUnderWater())
					dy = getTopBlockYIgnoreWater(x, z, y, endY);
				
				int biomeWidth = CHUNK_SIZE / biome.length;
				int biomeID = biome[x / biomeWidth][dy / biomeHeight][z / biomeWidth];
				
				int color = Block.getBlockColor(blocks[x][dy][z], biomeID);
				
				
				if (!Block.biomeExists(biomeID) && !unknownBiomes.containsKey(biomeID))
				{
					Block.addUnknownBiome(biomeID, new int[] {x, y, z});
				}
				
				int light = 15;
				int sLight = 15;
				
				if (y < CHUNK_HEIGHT - 1 && SessionProperties.isRenderLight())
				{
					light = blockLight[x][y + 1][z];
					sLight = skyLight[x][y + 1][z];
				}
				
				//Adds a blue effect to blocks under water
				if (y != dy)
				{
					int waterColor = Block.getBlockColor(blocks[x][y][z], 
							biome[x / biomeWidth][y / biomeHeight][z / biomeWidth]);
					int a = 0;
					int r = 0;
					int g = 0;
					int b = 0;
					
					float intensity = 1 - SessionProperties.getWaterTransparency();
					float inverseIntensity = 1 - intensity;
					
					a = 0xFF000000;
					
					r += (int)(((color & 0x00FF0000) >>> 16) * inverseIntensity);
					r += (int)(((waterColor & 0x00FF0000) >>> 16) * intensity);
					
					g += (int)(((color & 0x0000FF00) >>> 8) * inverseIntensity);
					g += (int)(((waterColor & 0x0000FF00) >>> 8) * intensity);
					
					b += (int)(((color & 0x000000FF)) * inverseIntensity);
					b += (int)((waterColor & 0x000000FF) * intensity);
					
					r <<= 16;
					g <<= 8;
					
					color = a | r | g | b;
				}
				
				result[x][z][0] = color;
				result[x][z][1] = dy;
				result[x][z][2] = light;
				result[x][z][3] = sLight;
			}
		}
		
		return result;
	}
	
	/**
	 * Gets the top block of a given x, z column that is not listed as invisible or is 
	 *   considered water.
	 * @param x       the Chunk's x coord, relative to the chunk
	 * @param z       the Chunk's z coord, relative to the chunk
	 * @param startY  the y coord to start searching at
	 * @return        a color stored as an ARGB integer
	 */
	public int[][][] getTopColors()
	{
		return getTopColors(CHUNK_HEIGHT - 1, 0);
	}
	
	/**
	 * Gets the top block of a given x, z column that is not listed as invisible.
	 * @param x       the Chunk's x coord, relative to the chunk
	 * @param z       the Chunk's z coord, relative to the chunk
	 * @param startY  the y coord to start searching at
	 * @return        a color stored as an ARGB integer
	 */
	public int getTopBlockY(int x, int z, int startY, int endY)
	{
		for (int y = startY; y >= endY; y--) 
		{
			String blockName = blocks[x][y][z];
			
			if (blockName != null && Block.isBlockVisible(blockName))
			{
				return y;
			}
		}
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
	public int getTopBlockYIgnoreWater(int x, int z, int startY, int endY)
	{
		for (int y = startY; y >= endY; y--) 
		{
			try
			{
				String blockName = blocks[x][y][z];
				
				if (blockName != null && 
					Block.isBlockVisible(blockName) && 
					!Block.hasWaterColor(blockName))
				{
					return y;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		return 0;
	}

}
