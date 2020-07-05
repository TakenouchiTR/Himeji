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

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.takenouchitr.himeji.Himeji;
import com.takenouchitr.himeji.ListChangeListener;
import com.takenouchitr.himeji.SessionProperties;

public class Block 
{
	public static final int MISSING_COLOR = 0xFFFF00FF;
	public static final int COLORS_LIST = 0;
	public static final int INVISIBLE_LIST = 1;
	public static final int GRASS_LIST = 2;
	public static final int FOLIAGE_LIST = 3;
	public static final int WATER_LIST = 4;
	
	private static String[][] legacyIds;
	private static HashMap<String, Integer> colors;
	private static HashMap<Integer, String> biomes;
	private static HashMap<Integer, Integer> grassColors;
	private static HashMap<Integer, Integer> foliageColors;
	private static HashMap<Integer, Integer> waterColors;
	private static HashSet<String> foliage;
	private static HashSet<String> grass;
	private static HashSet<String> water;
	private static HashSet<String> invisible;
	private static HashSet<String> missingIds;
	private static HashSet<Integer> unknownBiomes;
	private static List<ListChangeListener> listeners = new ArrayList<>();
	
	private int blockID;
	private int metadata;
	
	public static void loadFiles()
	{
		final String DATA_FOLDER = Himeji.DATA_FOLDER;
		missingIds = new HashSet<>();
		unknownBiomes = new HashSet<>();
		colors = new HashMap<>();
		
		try
		{
			File file = new File(DATA_FOLDER + Himeji.BLOCK_COLORS_FILE);
			List<String> items = Files.readAllLines(file.toPath());
			
			for (String s : items)
			{
				String[] split = s.split("[,]");
				colors.put(split[0], Integer.parseInt(split[1]));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		foliage = loadStringList(new File(DATA_FOLDER + Himeji.FOLIAGE_FILE));
		grass = loadStringList(new File(DATA_FOLDER + Himeji.GRASS_FILE));
		water = loadStringList(new File(DATA_FOLDER + Himeji.WATER_FILE));
		invisible = loadStringList(new File(DATA_FOLDER + Himeji.INVISIBLE_FILE));
			
		biomes = new HashMap<>();
		foliageColors = new HashMap<>();
		grassColors = new HashMap<>();
		waterColors = new HashMap<>();
		
		try
		{
			File file = new File(DATA_FOLDER + Himeji.BIOMES_FILE);
			List<String> lines = Files.readAllLines(file.toPath());
			
			for (String s : lines)
			{
				String[] split = s.split("[,]");
				int id = Integer.parseInt(split[1]);
				
				biomes.put(id, split[0]);
				grassColors.put(id, Integer.parseInt(split[2]));
				foliageColors.put(id, Integer.parseInt(split[3]));
				waterColors.put(id, Integer.parseInt(split[4]));
			}
		}
		catch (Exception e)
		{
			
		}
		
		try
		{
			File file = new File(DATA_FOLDER + Himeji.LEGACY_ID_FILE);
			List<String> items = Files.readAllLines(file.toPath());
			
			legacyIds = new String[items.size()][];
			
			int index = 0;
			for (String s : items)
			{
				String[] split = s.split("[,]");
				legacyIds[index++] = split;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void saveColorFile()
	{
		File file = new File(Himeji.DATA_FOLDER + Himeji.BLOCK_COLORS_FILE);
		try
		{
			FileWriter writer = new FileWriter(file);
			
			for (String s : colors.keySet())
				writer.write(s + ',' + colors.get(s) + '\n');
			
			writer.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void saveBiomeColorFiles()
	{
		File biomeFile = new File(Himeji.DATA_FOLDER + Himeji.BIOMES_FILE);
		try
		{
			FileWriter writer = new FileWriter(biomeFile);
			
			for (Integer i : biomes.keySet())
			{
				writer.write(String.format("%1$s,%2$d,%3$d,%4$d,%5$d\n", biomes.get(i), i,
						grassColors.get(i), foliageColors.get(i), waterColors.get(i)));
			}
			
			writer.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void saveFlagFile(int listID)
	{
		File file;
		HashSet<String> list;
		switch (listID)
		{
			case INVISIBLE_LIST:
				file = new File(Himeji.DATA_FOLDER + Himeji.INVISIBLE_FILE);
				list = invisible;
				break;
			case GRASS_LIST:
				file = new File(Himeji.DATA_FOLDER + Himeji.GRASS_FILE);
				list = grass;
				break;
			case FOLIAGE_LIST:
				file = new File(Himeji.DATA_FOLDER + Himeji.FOLIAGE_FILE);
				list = foliage;
				break;
			case WATER_LIST:
				file = new File(Himeji.DATA_FOLDER + Himeji.WATER_FILE);
				list = water;
				break;
			default: 
				return;
		}
		try
		{
			FileWriter writer = new FileWriter(file);
			
			for (String s : list)
				writer.write(s + "\n");
			
			writer.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static HashSet<String> loadStringList(File file)
	{
		HashSet<String> result = new HashSet<>();
		
		try
		{
			List<String> items = Files.readAllLines(file.toPath());
			
			for (String s : items)
				result.add(s);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
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
	
	private static HashMap<String, int[]> loadDictionary(File file)
	{
		try
		{
			List<String> items = Files.readAllLines(file.toPath());
			
			int size = items.size();
			
			HashMap<String, int[]> dict = new HashMap<String, int[]>();
			
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
		String namespaceId = getNamespacedId(id, meta);
		
		return foliage.contains(namespaceId);
	}
	
	/**
	 * Checks whether a block is changes colors depending on the biome using the 
	 *   foliage colors.
	 * @param id   ID of the block
	 * @param meta Metadata of the block
	 * @return     true iff the block/metadata combination is in biomeFoliage 
	 */
	public static boolean hasFoliageColor(String id)
	{
		return foliage.contains(id);
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
		String namespaceId = getNamespacedId(id, meta);
		
		return water.contains(namespaceId);
	}
	
	/**
	 * Checks whether a block is changes colors depending on the biome using the
	 *   grass colors.
	 * @param id   ID of the block
	 * @param meta Metadata of the block
	 * @return     true iff the block/metadata combination is in biomeFoliage 
	 */
	public static boolean hasWaterColor(String id)
	{
		return water.contains(id);
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
		String namespaceId = getNamespacedId(id, meta);
		
		return grass.contains(namespaceId);
	}
	
	/**
	 * Checks whether a block is changes colors depending on the biome using the
	 *   grass colors.
	 * @param id   ID of the block
	 * @param meta Metadata of the block
	 * @return     true iff the block/metadata combination is in biomeFoliage 
	 */
	public static boolean hasGrassColor(String id)
	{
		return grass.contains(id);
	}
	
	public static String getNamespacedId(int id, int meta)
	{
		String namespaceID = null;
		try
		{
			if (legacyIds[id] != null && legacyIds[id].length > meta)
				namespaceID = legacyIds[id][meta];
			else
				namespaceID = legacyIds[id][0];
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return namespaceID;
	}
	
	/**
	 * Checks if a block with a certain pre-flattening ID is false on the blockVisibility array.
	 * Invisible blocks will be skipped when determining the top block of a map.
	 * @param id Pre-flattening block id
	 * @return   true if the block should be rendered, false if the block should be skipped
	 */
	public static boolean isBlockVisible(int id, int meta)
	{
		return !invisible.contains(getNamespacedId(id, meta));
	}
	
	/**
	 * Checks if a block with a certain post-flattening ID is false on the blockVisibility array.
	 * Invisible blocks will be skipped when determining the top block of a map.
	 * @param id Post-flattening block id
	 * @return   true if the block should be rendered, false if the block should be skipped
	 */
	public static boolean isBlockVisible(String id)
	{
		return !invisible.contains(id);
	}

	/**
	 * Gets the biome-specific color for foliage.
	 * @param biome biome the foliage is located in
	 * @return      int ARGB value for a color
	 */
	public static int getFoliageColor(int biome)
	{
		return getBiomeColor(foliageColors, biome);
	}
	
	/**
	 * Gets the biome-specific color for grasses.
	 * @param biome biome the grass is located in
	 * @return      int ARGB value for a color
	 */
	public static int getGrassColor(int biome)
	{
		return getBiomeColor(grassColors, biome);
	}
	
	/**
	 * Gets the biome-specific color for water.
	 * @param biome biome the water is located in
	 * @return      int ARGB value for a color
	 */
	public static int getWaterColor(int biome)
	{
		return getBiomeColor(waterColors, biome);
	}
	
	/**
	 * Gets the color of a block to be rendered using the pre-flattening block ID and metadata.
	 * @param id   Pre-flattening block ID
	 * @param meta Pre-flattening block metadata
	 * @return     int representation of an ARGB value
	 */
	public static int getBlockColor(int id, int meta, int biome)
	{
		if (id >= legacyIds.length || legacyIds[id] == null ||
				legacyIds[id].length == 0)
			return 0xFF000000;
		
		String namespaceId = getNamespacedId(id, meta);
		
		return getBlockColor(namespaceId, biome);
	}
	
	public static boolean idExists(String id)
	{
		return colors.containsKey(id);
	}
	
	public static int getBiomeColor(HashMap<Integer, Integer> biomeColors, int biomeID)
	{
		if (!biomeColors.containsKey(biomeID))
		{
			unknownBiomes.add(biomeID);
			return biomeColors.get(Biome.FOREST.id);
		}
		
		return biomeColors.get(biomeID);
	}
	
	public static void setGrassColor(int biome, int color)
	{
		grassColors.put(biome, color);
	}
	
	public static void setFoliageColor(int biome, int color)
	{
		foliageColors.put(biome, color);
	}
	
	public static void setWaterColor(int biome, int color)
	{
		waterColors.put(biome, color);
	}
	
	public static int getBlockColor(String namespaceId)
	{
		if (colors.containsKey(namespaceId))
			return colors.get(namespaceId);
		else
		{
			missingIds.add(namespaceId);
			return MISSING_COLOR;
		}
		
	}
	
	/**
	 * Gets the color of a block to be rendered using the post-flattening block ID.
	 * @param id   Post-flattening block ID
	 * @return     int representation of an ARGB value
	 */
	public static int getBlockColor(String id, int biome)
	{
		int color;
		int secondColor = 0xFF000000;
		
		boolean blend = false;
		
		color = getBlockColor(id);
		
		if (!SessionProperties.renderBiomes)
			return color;
		
		if (hasFoliageColor(id))
		{
			secondColor = getFoliageColor(biome);
			blend = true;
		}
		else if (hasGrassColor(id))
		{
			secondColor = getGrassColor(biome);
			blend = true;
		}
		else if (hasWaterColor(id))
		{
			secondColor = getWaterColor(biome);
			blend = true;
		}
		
		//Blends the block color with the biome color
		if (blend)
		{
			int a = 0;
			int r = 0;
			int g = 0;
			int b = 0;
			
			float intensity = SessionProperties.biomeIntensity;
			float inverseIntensity = 1 - intensity;
			
			a = 0xFF000000;
			
			r += (int)(((color & 0x00FF0000) >>> 16) * inverseIntensity);
			r += (int)(((secondColor & 0x00FF0000) >>> 16) * intensity);
			
			g += (int)(((color & 0x0000FF00) >>> 8) * inverseIntensity);
			g += (int)(((secondColor & 0x0000FF00) >>> 8) * intensity);
			
			b += (int)(((color & 0x000000FF)) * inverseIntensity);
			b += (int)((secondColor & 0x000000FF) * intensity);
			
			r <<= 16;
			g <<= 8;
					
			color = a | r | g | b;
		}
		
		return color;
	}
	
	public static List<String> getList(int listID)
	{
		HashSet<String> list = null;
		switch(listID)
		{
			case INVISIBLE_LIST:
				list = invisible;
				break;
			case GRASS_LIST:
				list = grass;
				break;
			case FOLIAGE_LIST:
				list = foliage;
				break;
			case WATER_LIST:
				list = water;
				break;
			default:
				return null;
		}
		
		List<String> result = new ArrayList<>();
		for (String s : list)
			result.add(s);
		
		return result;
	}
	
	public static HashMap<Integer, String> getBiomes()
	{
		return biomes;
	}
	
	public static HashMap<Integer, Integer> getGrassColors()
	{
		return grassColors;
	}
	
	public static HashMap<Integer, Integer> getFoliageColors()
	{
		return foliageColors;
	}
	
	public static HashMap<Integer, Integer> getWaterColors()
	{
		return waterColors;
	}
	
 	public static void addIdToList(String id, int listID)
	{
		HashSet<String> list = null;
		
		switch(listID)
		{
			case INVISIBLE_LIST:
				list = invisible;
				break;
			case GRASS_LIST:
				list = grass;
				break;
			case FOLIAGE_LIST:
				list = foliage;
				break;
			case WATER_LIST:
				list = water;
				break;
			default:
				return;
		}
		
		if (list.contains(id))
			return;
		
		list.add(id);
		for (ListChangeListener lcl : listeners)
			lcl.OnItemAddition(id, listID);
	}
	
	public static void removeIdFromList(String id, int listID)
	{
		HashSet<String> list = null;
		
		switch(listID)
		{
			case INVISIBLE_LIST:
				list = invisible;
				break;
			case GRASS_LIST:
				list = grass;
				break;
			case FOLIAGE_LIST:
				list = foliage;
				break;
			case WATER_LIST:
				list = water;
				break;
			default:
				return;
		}
		
		if (!list.contains(id))
			return;
		
		list.remove(id);
		for (ListChangeListener lcl : listeners)
			lcl.OnItemRemoval(id, listID);
	}
	
	/**
	 * Gets the program's internal ID and metadata for a block's namespace ID. 
	 * @param namespaceID a block's Minecraft namespace ID
	 * @return            int array containing the ID and metadata for a block
	 *
	public static int[] getIdMeta(String namespaceID)
	{
		return blockDict.get(namespaceID);
	}
	*/
	public static void setBlockColor(String id, int color)
	{
		if (!colors.containsKey(id))
		{
			for (ListChangeListener lcl : listeners)
				lcl.OnItemAddition(id, COLORS_LIST);
		}
		
		colors.put(id, color);
		if (missingIds.contains(id))
			missingIds.remove(id);
	}
	
	public static void addMissingId(String id)
	{
		missingIds.add(id);
	}
	
	public static HashSet<String> getMissingIds()
	{
		return missingIds;
	}
	
	public static HashSet<Integer> getUnknownBiomes()
	{
		return unknownBiomes;
	}
	
	public static HashMap<String, Integer> getColors()
	{
		return colors;
	}
	
	public static void addListChangeListener(ListChangeListener lcl)
	{
		listeners.add(lcl);
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