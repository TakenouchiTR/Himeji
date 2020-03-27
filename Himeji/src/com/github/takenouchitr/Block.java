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

import java.io.File;
import java.nio.file.Files;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Block 
{
	private static int maxBlockId;
	
	private static boolean[] blockVisibility; 
	private static int[][] biomeFoliage;
	private static int[][] biomeGrass;
	private static int[][] biomeWater;
	private static int[][] blockColors;
	private static int[] foliageColors;
	private static int[] grassColors;
	private static int[] waterColors;
	private static Dictionary<String, int[]> blockDict;
	
	private int blockID;
	private int metadata;
	
	public static void loadFiles()
	{
		final String DATA_FOLDER = Himeji.DATA_FOLDER;
		
		File input = new File(DATA_FOLDER + Himeji.BLOCK_COLORS_FILE);
		blockColors = load2DIntArray(input);
		maxBlockId = blockColors.length - 1;
		
		input = new File(DATA_FOLDER + Himeji.FOLIAGE_FILE);
		biomeFoliage = load2DIntArray(input);
		
		input = new File(DATA_FOLDER + Himeji.GRASS_FILE);
		biomeGrass = load2DIntArray(input);
		
		input = new File(DATA_FOLDER + Himeji.WATER_FILE);
		biomeWater = load2DIntArray(input);
		
		input = new File(DATA_FOLDER + Himeji.FOLIAGE_COLORS_FILE);
		foliageColors = loadIntArray(input);
		
		input = new File(DATA_FOLDER + Himeji.GRASS_COLORS_FILE);
		grassColors = loadIntArray(input);
		
		input = new File(DATA_FOLDER + Himeji.WATER_COLORS_FILE);
		waterColors = loadIntArray(input);
		
		input = new File(DATA_FOLDER + Himeji.INVISIBLE_FILE);
		blockVisibility = loadBooleanArray(input);
		
		input = new File(DATA_FOLDER + Himeji.NAMESPACED_ID_FILE);
		blockDict = loadDictionary(input);
	}
	
	private static int[][] load2DIntArray(File file)
	{
		try
		{
			List<String> items = Files.readAllLines(file.toPath());
			
			int size = items.size();
			int[][] array = new int[size][];
			
			for (int i = 0; i < size; i++)
			{
				String line = items.get(i);
				if (line.isEmpty())
					continue;
				
				String[] split = line.split(",");
				array[i] = new int[split.length];
				
				for (int j = 0; j < split.length; j++)
					array[i][j] = Integer.parseInt(split[j]);
			}
			return array;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private static int[] loadIntArray(File file)
	{
		try
		{
			List<String> items = Files.readAllLines(file.toPath());
			
			int size = items.size();
			int[] array = new int[size];
			
			for (int i = 0; i < size; i++)
				array[i] = Integer.parseInt(items.get(i));
			
			return array;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private static boolean[] loadBooleanArray(File file)
	{
		try
		{
			List<String> items = Files.readAllLines(file.toPath());
			
			int size = items.size();
			boolean[] array = new boolean[size];
			
			for (int i = 0; i < array.length; i++)
				array[i] = Boolean.parseBoolean(items.get(i));
			
			return array;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private static Dictionary<String, int[]> loadDictionary(File file)
	{
		try
		{
			List<String> items = Files.readAllLines(file.toPath());
			
			int size = items.size();
			
			Dictionary<String, int[]> dict = new Hashtable<String, int[]>();
			
			for (int i = 0; i < size; i++)
			{
				String line = items.get(i);
				String[] split = line.split(",");
				int[] values = {Integer.parseInt(split[1]), 
						Integer.parseInt(split[2])};
				
				dict.put(split[0], values);
			}
			
			return dict;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Checks whether a block is changes colors depending on the biome using the 
	 *   foliage colors.
	 * @param id   ID of the block
	 * @param meta Metadata of the block
	 * @return     true iff the block/metadata combination is in biomeFoliage 
	 */
	public static boolean hasFoliageColor(int id, int meta)
	{
		for (int i = 0; i < biomeFoliage.length; i++)
			if (id == biomeFoliage[i][0] && meta == biomeFoliage[i][1])
				return true;
		
		return false;
	}

	/**
	 * Checks whether a block is changes colors depending on the biome using the
	 *   grass colors.
	 * @param id   ID of the block
	 * @param meta Metadata of the block
	 * @return     true iff the block/metadata combination is in biomeFoliage 
	 */
	public static boolean hasWaterColor(int id, int meta)
	{
		for (int i = 0; i < biomeWater.length; i++)
			if (id == biomeWater[i][0] && meta == biomeWater[i][1])
				return true;
		
		return false;
	}
	
	/**
	 * Checks whether a block is changes colors depending on the biome using the
	 *   grass colors.
	 * @param id   ID of the block
	 * @param meta Metadata of the block
	 * @return     true iff the block/metadata combination is in biomeFoliage 
	 */
	public static boolean hasGrassColor(int id, int meta)
	{
		for (int i = 0; i < biomeGrass.length; i++)
			if (id == biomeGrass[i][0] && meta == biomeGrass[i][1])
				return true;
		
		return false;
	}
	
	/**
	 * Checks if a block with a certain pre-flattening ID is false on the blockVisibility array.
	 * Invisible blocks will be skipped when determining the top block of a map.
	 * @param id Pre-flattening block id
	 * @return   true if the block should be rendered, false if the block should be skipped
	 */
	public static boolean isBlockVisible(int id)
	{
		if (id < maxBlockId)
			return blockVisibility[id];
		return false;
	}
	
	/**
	 * Checks if a block with a certain post-flattening ID is false on the blockVisibility array.
	 * Invisible blocks will be skipped when determining the top block of a map.
	 * @param id Post-flattening block id
	 * @return   true if the block should be rendered, false if the block should be skipped
	 */
	public static boolean isBlockVisible(String id)
	{
		//Gets the pre-flattening ID of the block
		int[] idMeta = getIdMeta(id);
		
		if (idMeta[0] <= maxBlockId)
			return blockVisibility[idMeta[0]];
		return false;
	}

	/**
	 * Gets the biome-specific color for foliage.
	 * @param biome biome the foliage is located in
	 * @return      int ARGB value for a color
	 */
	public static int getFoliageColor(int biome)
	{
		return foliageColors[biome];
	}
	
	/**
	 * Gets the biome-specific color for grasses.
	 * @param biome biome the grass is located in
	 * @return      int ARGB value for a color
	 */
	public static int getGrassColor(int biome)
	{
		return grassColors[biome];
	}
	
	/**
	 * Gets the biome-specific color for water.
	 * @param biome biome the water is located in
	 * @return      int ARGB value for a color
	 */
	public static int getWaterColor(int biome)
	{
		return waterColors[biome];
	}
	
	/**
	 * Gets the color of a block to be rendered using the pre-flattening block ID and metadata.
	 * @param id   Pre-flattening block ID
	 * @param meta Pre-flattening block metadata
	 * @return     int representation of an ARGB value
	 */
	public static int getBlockColor(int id, int meta, int biome)
	{
		if (id >= blockColors.length || blockColors[id].length == 0)
			return 0;
		
		int color;
		int secondColor = 0;
		boolean blend = false;
		
		color = blockColors[id][Math.min(meta, blockColors[id].length - 1)];
		
		if (Himeji.getProperty(Property.RENDER_BIOME_COLORS).equals("false"))
			return color;
		
		if (hasFoliageColor(id, meta))
		{
			secondColor = getFoliageColor(biome);
			blend = true;
		}
		else if (hasGrassColor(id, meta))
		{
			secondColor = getGrassColor(biome);
			blend = true;
		}
		else if (id == 8 || id == 9 || id == 208)
		{
			secondColor = getWaterColor(biome);
			blend = true;
		}
		
		if (blend)
		{
			int a = 0;
			int r = 0;
			int g = 0;
			int b = 0;
			
			a = 0xFF000000;
			r += (color & 0x00FF0000) >>> 16;
			r += ((secondColor & 0x00FF0000) >>> 16) * 2;
			g += (color & 0x0000FF00) >>> 8;
			g += ((secondColor & 0x0000FF00) >>> 8) * 2;
			b += (color & 0x000000FF);
			b += (secondColor & 0x000000FF) * 2;
			
			r /= 3;
			g /= 3;
			b /= 3;
			
			r <<= 16;
			g <<= 8;
					
			color = a | r | g | b;
		}
		
		return color;
	}
	
	/**
	 * Gets the color of a block to be rendered using the post-flattening block ID.
	 * @param id   Post-flattening block ID
	 * @return     int representation of an ARGB value
	 */
	public static int getBlockColor(String id, int biome)
	{
		if (id == null)
			return 0;
		int[] idMeta = blockDict.get(id);
		return getBlockColor(idMeta[0], idMeta[1], biome);
	}
	
	/**
	 * Gets the program's internal ID and metadata for a block's namespace ID. 
	 * @param namespaceID a block's Minecraft namespace ID
	 * @return            int array containing the ID and metadata for a block
	 */
	public static int[] getIdMeta(String namespaceID)
	{
		return blockDict.get(namespaceID);
	}
	
	/**
	 * Creates a block with a specified blockID and metadata
	 * @param blockID  the program's internal block id
	 * @param metadata the metadata for the block
	 */
	public Block(int blockID, int metadata) 
	{
		this.blockID = blockID;
		this.metadata = metadata;
	}
	
	/**
	 * Gets the block's blockID
	 * @return the block's blockID
	 */
 	public int getBlockID() 
	{
		return blockID;
	}
	
 	/**
 	 * Gets the block's metadata
 	 * @return the block's metadata
 	 */
	public int getMetaData() 
	{
		return metadata;
	}
}