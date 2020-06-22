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

package com.github.takenouchitr;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import com.mojang.nbt.Tag;

public class NewChunk extends Chunk
{
	private String[][][] blocks;
	private int[][][] biome;
	private int biomeHeight;
	private Section[] sections;
	
	/**
	 * Creates a Chunk using the new format for saving chunk data.
	 * @param baseTag CompoundTag containing the chunk's DataVersion and Level tags.
	 */
	public NewChunk(CompoundTag baseTag) 
	{
		super(baseTag);
		
		ListTag<? extends Tag> sectionTags = levelTag.getList("Sections");
		
		
		if (sectionTags.size() != 0)
			sections = new Section[sectionTags.size() - 1];
		
		blocks = new String[CHUNK_SIZE][CHUNK_HEIGHT][CHUNK_SIZE];
		
		//Fills all blocks with the default block namespace ID
		for(int x = 0; x < CHUNK_SIZE; x++) 
		{
			for(int z = 0; z < CHUNK_SIZE; z++) 
			{
				for(int y = 0; y < CHUNK_HEIGHT; y++) 
				{
					blocks[x][y][z] = "minecraft:air";
				}
			}
		}
		
		//Stops if there is no block data stored in the tag
		if (sections == null)
			return;
		
		//Loads data from each of the sections
		for(int i = 0; i < sections.length; i++) 
		{
			sections[i] = new Section((CompoundTag)sectionTags.get(i));
			String[][][] sectionBlocks = sections[i].getBlocks();
			int sectionY = (sections[i].getSectionTag().getByte("Y")) * 16;
			
			//Skips a section if there are no blocks stored
			if (sectionBlocks == null)
				continue;
			
			//Loads each of the blocks in the section
			for(int x = 0; x < CHUNK_SIZE; x++) 
			{
				for(int y = 0; y < CHUNK_SIZE; y++) 
				{
					for(int z = 0; z < CHUNK_SIZE; z++) 
					{
						blocks[x][y + sectionY][z] = sectionBlocks[x][y][z];
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
	 * Gets the top block of a given x, z column that is not listed as invisible.
	 * @param x       the Chunk's x coord, relative to the chunk
	 * @param z       the Chunk's z coord, relative to the chunk
	 * @param startY  the y coord to start searching at
	 * @return        a color stored as an ARGB integer
	 */
	@Override
	public int[][][] getTopColors(int startY, int endY)
	{
		int[][][] result = new int[CHUNK_SIZE][CHUNK_SIZE][2];
		
		if (blocks == null || sections == null)
			return null;
		
		for (int x = 0; x < CHUNK_SIZE; x++)
		{
			for (int z = 0; z < CHUNK_SIZE; z++)
			{
				int y = getTopBlockY(x, z, startY, endY);
				int dy = y;
				if (Himeji.getProperty(Property.RENDER_UNDER_WATER).equals("true"))
					dy = getTopBlockYIgnoreWater(x, z, startY, endY);
				
				int biomeWidth = CHUNK_SIZE / biome.length;
				int color = Block.getBlockColor(blocks[x][dy][z], 
						biome[x / biomeWidth][dy / biomeHeight][z / biomeWidth]);
				
				//Adds a blue effect to blocks under water
				if (y != dy)
				{
					int waterColor = Block.getBlockColor("minecraft:water", 
							biome[x / biomeWidth][y / biomeHeight][z / biomeWidth]);
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
	 * Gets the top block of a given x, z column that is not listed as invisible or is 
	 *   considered water.
	 * @param x       the Chunk's x coord, relative to the chunk
	 * @param z       the Chunk's z coord, relative to the chunk
	 * @param startY  the y coord to start searching at
	 * @return        a color stored as an ARGB integer
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
		{
			String blockName = blocks[x][y][z];
			
			if (Block.isBlockVisible(blockName))
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
	@Override
	public int getTopBlockYIgnoreWater(int x, int z, int startY, int endY)
	{
		for(int y = startY; y >= endY; y--) 
		{
			try
			{
				String blockName = blocks[x][y][z];
				
				if (Block.isBlockVisible(blockName) && 
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
