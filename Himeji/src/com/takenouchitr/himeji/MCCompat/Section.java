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

import com.mojang.nbt.*;

public class Section 
{
	public static final int SECTION_HEIGHT = 16;
	private static final String EMPTY_BLOCK = "minecraft:air";
	private CompoundTag sectionTag;
	private String[] paletteNames;
	private boolean isEmpty;
	
	public Section(CompoundTag sectionTag)
	{
		this.sectionTag = sectionTag;
		int paletteSize;
		isEmpty = false;
		
		ListTag<? extends Tag> palette = sectionTag.getList("Palette");
		
		paletteSize = palette.size();
		paletteNames = new String[paletteSize];
		
		for (int i = 0; i < paletteSize; i++)
		{
			CompoundTag curTag = (CompoundTag)(palette.get(i));
			paletteNames[i] = curTag.getString("Name");
		}	
	}
	
	/**
	 * Creates a three-dimensional String array of each of the block IDs within the section.
	 * @return String array of block IDs
	 */
	public String[][][] getBlocks()
	{
		final int CHUNK_SIZE = Chunk.CHUNK_SIZE;
		final int LONG_BIT_SIZE = 64;
		int longIndex = 0;        //Which long in the array to read
		byte bitIndex = 0;        //The current bit in the long
		byte blockBitLength = 4;  //How many bits are needed for each block
		long[] blockStates = sectionTag.getLongArray("BlockStates");
		
		isEmpty = false;
		
		if (blockStates.length == 0)
			return null;
		
		String[][][] result = new String[CHUNK_SIZE][SECTION_HEIGHT][CHUNK_SIZE];
		
		//Determines how many bits are needed to represent each block.
		int bits = 16;
		while(bits < paletteNames.length)
		{
			bits *= 2;
			blockBitLength++;
		}
		
		long mask;
		for (int y = 0; y < SECTION_HEIGHT; y++)
		{
			for (int z = 0; z < CHUNK_SIZE; z++)
			{
				for (int x = 0; x < CHUNK_SIZE; x++)
				{
					long paletteID = 0L;
					
					for (int i = 0; i < blockBitLength; i++)
					{
						mask = 1 << bitIndex;       //Creates a mask to copy the bit at bitIndex
						if (bitIndex > 30)
							mask = 2147483648L << bitIndex - 31;
						
						mask &= blockStates[longIndex];
					
						if (bitIndex > i)                //Realigns the bit with where it needs to be
							mask >>>= bitIndex - i;
						else
							mask <<= i - bitIndex;
						
						paletteID |= mask;
						
						bitIndex++;
						if (bitIndex >= LONG_BIT_SIZE)
						{
							longIndex++;
							bitIndex = 0;
						}
					}
					
					result[x][y][z] = paletteNames[(int)paletteID];
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Creates a three-dimensional String array of each of the block IDs within the section.
	 * Ignores the "extra" unused portion of long's if a block's palette number doesn't
	 * perfectly fit in the long's bits
	 * 
	 * Ie: if 5 bits are used to represent a palette, then there will be 4 bits at the end
	 * of every long that will be unused. 
	 * (64 bits in long; 5 * 12 = 60 bits to represent blocks; 64 - 60 = 4 bits unused)
	 * @return String array of block IDs
	 */
	public String[][][] getTruncatedBlocks()
	{
		//Checks if a palette only contains one block.
		//  If true, then returns a 3D array of only that ID.
		if (paletteNames.length == 1)
		{
			//Mark section as empty if the only block is air
			if (paletteNames[0] == EMPTY_BLOCK)
				isEmpty = true;
			return makeFullSection(paletteNames[0]);
		}
		
		final int CHUNK_SIZE = Chunk.CHUNK_SIZE;
		final int LONG_BIT_SIZE = 64;
		int longIndex = 0;        //Which long in the array to read
		byte bitIndex = 0;        //The current bit in the long
		byte blockBitLength = 4;  //How many bits are needed for each block
		long[] blockStates = sectionTag.getLongArray("BlockStates");
		
		if (blockStates.length == 0)
			return null;
		
		String[][][] result = new String[CHUNK_SIZE][SECTION_HEIGHT][CHUNK_SIZE];
		
		//Determines how many bits are needed to represent each block.
		int bits = 16;
		while(bits < paletteNames.length)
		{
			bits *= 2;
			blockBitLength++;
		}
		
		long mask;
		for (int y = 0; y < SECTION_HEIGHT; y++)
		{
			for (int z = 0; z < CHUNK_SIZE; z++)
			{
				for (int x = 0; x < CHUNK_SIZE; x++)
				{
					long paletteID = 0L;
					
					for (int i = 0; i < blockBitLength; i++)
					{
						mask = 1 << bitIndex;       //Creates a mask to copy the bit at bitIndex
						if (bitIndex > 30)
							mask = 2147483648L << bitIndex - 31;
						
						mask &= blockStates[longIndex];
					
						if (bitIndex > i)                //Realigns the bit with where it needs to be
							mask >>>= bitIndex - i;
						else
							mask <<= i - bitIndex;
						
						paletteID |= mask;
						
						bitIndex++;
						if (bitIndex >= LONG_BIT_SIZE)
						{
							longIndex++;
							bitIndex = 0;
						}
					}
					
					result[x][y][z] = paletteNames[(int)paletteID];
					
					if (LONG_BIT_SIZE - bitIndex < blockBitLength)
					{
						longIndex++;
						bitIndex = 0;
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Returns a 3D array of strings, all of a single namespace ID
	 * @param namespaceID Namespace ID to use
	 * @return 3D array of block IDs
	 */
	private static String[][][] makeFullSection(String namespaceID)
	{
		final int CHUNK_SIZE = Chunk.CHUNK_SIZE;
		String[][][] result = new String[CHUNK_SIZE][SECTION_HEIGHT][CHUNK_SIZE];
		
		for (String[][] arr1 : result)
			for (String[] arr2 : arr1)
				Arrays.fill(arr2, namespaceID);
		
		return result;
	}
	
	/**
	 * Gets the block light data, if it exists
	 * @return byte array containing the data, null if the data is not stored
	 */
	public byte[] getBlockLight()
	{
		if (sectionTag.contains("BlockLight"))
			return sectionTag.getByteArray("BlockLight");
		return null;
	}
	
	/**
	 * Gets the sky light data, if it exists
	 * @return byte array containing the data, null if the data is not stored
	 */
	public byte[] getSkyLight()
	{
		if (sectionTag.contains("SkyLight"))
			return sectionTag.getByteArray("SkyLight");
		return null;
	}
	
	/**
	 * Gets the section's NBT.
	 * @return the section's NBT
	 */
	public CompoundTag getSectionTag()
	{
		return sectionTag;
	}

	/**
	 * Gets whether the section is only air blocks 
	 * @return True iff the section only contains air blocks
	 */
	public boolean isEmpty()
	{
		return isEmpty;
	}
}
