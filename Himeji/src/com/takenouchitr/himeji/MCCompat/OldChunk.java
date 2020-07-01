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

import com.mojang.nbt.ByteArrayTag;
import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.IntArrayTag;
import com.mojang.nbt.ListTag;
import com.mojang.nbt.Tag;
import com.takenouchitr.himeji.Himeji;
import com.takenouchitr.himeji.Property;

public class OldChunk extends Chunk 
{
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
		
		blocks = new int[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
		metadata = new int[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
		biome = new int[CHUNK_SIZE][CHUNK_SIZE];
		
		boolean isFirstVersion = !levelTag.contains("Sections");
		
		if (!isFirstVersion)
		{
			ListTag<? extends Tag> sections = levelTag.getList("Sections");
			
			//Loads up the blocks stored in each of the sections
			for(int i = 0; i < sections.size(); i++) 
			{
				CompoundTag section = (CompoundTag)sections.get(i);
				
				int sectionY = (int)(section.getByte("Y")) * 16;
				if (sectionY < 0)
					continue;
				
				byte[] blockData = section.getByteArray("Blocks");
				byte[] metadataData = section.getByteArray("Data");
				
				//Skips the section if there is no block data
				if (blockData.length == 0)
					continue;
				
				for(int x = 0; x < CHUNK_SIZE; x++) 
				{
					for(int y = 0; y < CHUNK_SIZE; y++) 
					{
						for(int z = 0; z < CHUNK_SIZE; z++) 
						{
							blocks[x][y + sectionY][z] = getPositiveByte(blockData[getIndex(x, y, z)]);
							metadata[x][y + sectionY][z] = getHalfIndexValue(metadataData, x, y, z);
						}
					}
				}
			}
			
			Tag biomeTag = levelTag.get("Biomes");
			
			//Loads the new, IntArray biome tag
			if (biomeTag instanceof IntArrayTag)
			{
				int[] biomeData = levelTag.getIntArray("Biomes");
				
				for(int x = 0; x < CHUNK_SIZE; x++) 
				{
					for(int z = 0; z < CHUNK_SIZE; z++) 
					{
						biome[x][z] = biomeData[getIndex(x, z)];
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
						biome[x][z] = getPositiveByte(biomeData[getIndex(x, z)]);
					}
				}
			}
		}
		else
		{
			byte[] blockData = levelTag.getByteArray("Blocks");
			byte[] metadataData = levelTag.getByteArray("Data");
			int index = 0;
			
			for(int x = 0; x < CHUNK_SIZE; x++) 
			{
				for(int z = 0; z < CHUNK_SIZE; z++) 
				{
					for(int y = 0; y < 128; y++) 
					{
						blocks[x][y][z] = getPositiveByte(blockData[index]);
						metadata[x][y][z] = 0;//getHalfIndexValue(metadataData, index);
						index++;
					}
					biome[x][z] = Biome.FOREST.id;
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
	 * Creates a two-dimensional int array of the ARGB values for the top-most blocks of 
	 * each x, z column.
	 * @return int array of the colors for the top-most blocks of each x, z column
	 */
	@Override
	public int[][][] getTopColors(int startY, int endY)
	{
		int[][][] result = new int[CHUNK_SIZE][CHUNK_SIZE][2];
		
		for (int x = 0; x < CHUNK_SIZE; x++)
		{
			for (int z = 0; z < CHUNK_SIZE; z++)
			{
				int y = getTopBlockY(x, z, startY, endY);
				int dy = y;
				if (Himeji.getProperty(Property.RENDER_UNDER_WATER).equals("true"))
					dy = getTopBlockYIgnoreWater(x, z, startY, endY);
				
				int color = Block.getBlockColor(blocks[x][dy][z], metadata[x][dy][z], biome[x][z]);
				
				//Adds a blue effect to blocks under water
				if (y != dy)
				{
					int waterColor = Block.getBlockColor("minecraft:water", biome[x][z]);
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
		return getTopColors(CHUNK_HEIGHT - 1, 0);
	}
	
	/**
	 * Gets the top block of a given x, z column that is not listed as invisible.
	 * @param x       the Chunk's x coord, relative to the chunk
	 * @param z       the Chunk's z coord, relative to the chunk
	 * @param startY  the y coord to start searching at
	 * @return        a color stored as an ARGB integer
	 */
	@Override
	public int getTopBlockY(int x, int z, int startY, int endY)
	{
		for(int y = startY; y >= endY; y--) 
			if(Block.isBlockVisible(blocks[x][y][z], metadata[x][y][z])) 
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
	public int getTopBlockYIgnoreWater(int x, int z, int startY, int endY)
	{
		for(int y = startY; y >= endY; y--) 
		{
			int block = blocks[x][y][z];
			int meta = metadata[x][y][z];
			if(Block.isBlockVisible(block, meta) && !Block.hasWaterColor(block, 0)) 
				return y;
		}
		
		return 0;
	}
}
