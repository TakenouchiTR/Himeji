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
	public static final int BIOME_LIST = 5;
	
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
	private static HashMap<Integer, int[]> unknownBiomes;
	private static List<ListChangeListener> listeners = new ArrayList<>();
	
	/**
	 * Loads the data files
	 */
	public static void loadFiles()
	{
		final String DATA_FOLDER = Himeji.DATA_FOLDER;
		missingIds = new HashSet<>();
		unknownBiomes = new HashMap<>();
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
	
	/**
	 * Saves the color information to file
	 */
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
	
	/**
	 * Save the biome color information to file
	 */
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
	
	/**
	 * Saves the specified flags list to file
	 * @param listID List constant
	 */
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
	
	/**
	 * Loads a list of Strings from a file
	 * @param file File to load
	 * @return HashSet of Strings
	 */
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
	
	/**
	 * Adds a biome ID with a specified name to the list
	 * @param id Biome ID
	 * @param name Biome name
	 */
 	public static void addBiome(int id, String name)
 	{
 		biomes.put(id, name);
 		grassColors.put(id, 0xFF000000);
 		foliageColors.put(id, 0xFF000000);
 		waterColors.put(id, 0xFF000000);
 		
 		for (ListChangeListener lcl : listeners)
 		{
 			lcl.OnItemAddition(id + "", BIOME_LIST);
 		}
 	}
 	
 	/**
 	 * Adds a block ID to a specified flags list
 	 * @param id Block ID
 	 * @param listID List constant
 	 */
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
	
	/**
	 * Checks if a biome ID exists
	 * @param id Biome ID
	 * @return Whether the ID exists
	 */
	public static boolean biomeExists(int id)
	{
		return biomes.containsKey(id);
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
		return (SessionProperties.isUseDefaultColor()) ? 
				!invisible.contains(id) : !invisible.contains(id) && colors.containsKey(id);
	}

	/**
	 * Checks if a namespaced ID exists
	 * @param id Namespaced ID
	 * @return Whether the ID exists
	 */
	public static boolean idExists(String id)
	{
		return colors.containsKey(id);
	}

	/**
	 * Removes a block from a specified flags list
	 * @param id Block ID
	 * @param listID List constant
	 */
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
	
	/**
	 * Adds a missing block ID to the list of missing IDs
	 * @param id Missing block ID
	 */
	public static void addMissingId(String id)
	{
		missingIds.add(id);
	}
	
	/**
	 * Adds an unknown biome ID and coordinate to the list of unknown IDs
	 * @param id Unknown biome ID
	 * @param pos int array containing the x, y, z coords of the biome
	 */
	public static void addUnknownBiome(int id, int[] pos)
	{
		unknownBiomes.put(id, pos);
	}
	
	/**
	 * Removes a block from the list of IDs
	 * @param id block ID
	 */
	public static void removeBlock(String id)
	{
		if (colors.remove(id) != null) 
		{
			for (ListChangeListener lcl : listeners)
				lcl.OnItemRemoval(id, COLORS_LIST);
		}
	}
	
	/**
	 * Removes a biome ID from the list of biomes
	 * @param id Biome ID
	 */
	public static void removeBiome(int id)
	{
		if (biomes.remove(id) != null)
		{
			for (ListChangeListener lcl : listeners)
				lcl.OnItemRemoval(id + "", BIOME_LIST);
		}
	}
	
	/**
	 * Gets the MissingIDs HashSet
	 * @return
	 */
	public static HashSet<String> getMissingIds()
	{
		return missingIds;
	}
	
	/**
	 * Gets the UnknownBiomes HashSet
	 * @return
	 */
	public static HashMap<Integer, int[]> getUnknownBiomes()
	{
		return unknownBiomes;
	}
	
	/**
	 * Gets the Colors HashMap
	 * @return
	 */
	public static HashMap<String, Integer> getColors()
	{
		return colors;
	}
	
	/**
	 * Adds a ListChangeListener to the list of listeners
	 * @param lcl
	 */
	public static void addListChangeListener(ListChangeListener lcl)
	{
		listeners.add(lcl);
	}

	/**
	 * Gets the color associated with a biome color from a HashMap of colors
	 * @param biomeColors HashMap to pull from
	 * @param biomeID Biome ID
	 * @return ARGB color integer
	 */
	public static int getBiomeColor(HashMap<Integer, Integer> biomeColors, int biomeID)
	{
		return (biomeColors.containsKey(biomeID)) ? 
				biomeColors.get(biomeID) : biomeColors.get(SessionProperties.getDefaultBiome());
	}

	/**
	 * Gets a biome ID from its name
	 * @param name Biome name
	 * @return Biome ID
	 */
	public static int getBiomeID(String name)
	{
		for (int i : biomes.keySet())
			if (biomes.get(i).equals(name))
				return i;
		
		return -1;
	}
	
	/**
	 * Gets the block color of a block ID. Returns the default color if the block ID isn't added
	 * @param namespaceID Block ID
	 * @return ARG color integer
	 */
	public static int getBlockColor(String namespaceID)
	{
		if (colors.containsKey(namespaceID))
			return colors.get(namespaceID);
		else
		{
			if (namespaceID != null)
				missingIds.add(namespaceID);
			
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
		
		if (!SessionProperties.isRenderBiomes())
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
			
			float intensity = SessionProperties.getBiomeIntensity();
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
	 * Gets the namespaced ID from a legacy block's ID and metadata values
	 * @param id Legacy block ID
	 * @param meta Legacy block metadata
	 * @return Namespaced ID
	 */
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
	 * Gets all block IDs from a HashSet and returns it as a List
	 * @param listID List Constant
	 * @return List of Strings
	 */
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
	
	/**
	 * Gets the Biome HashMap
	 * @return
	 */
	public static HashMap<Integer, String> getBiomes()
	{
		return biomes;
	}
	
	/**
	 * Gets the GrassColors HashMap
	 * @return
	 */
	public static HashMap<Integer, Integer> getGrassColors()
	{
		return grassColors;
	}
	
	/**
	 * Gets the FoliageColors HashMap
	 * @return
	 */
	public static HashMap<Integer, Integer> getFoliageColors()
	{
		return foliageColors;
	}
	
	/**
	 * Gets the WaterColors HashMap
	 * @return
	 */
	public static HashMap<Integer, Integer> getWaterColors()
	{
		return waterColors;
	}
	
	/**
	 * Sets the foliage color for a biome
	 * @param biome Biome ID
	 * @param color ARGB color integer
	 */
	public static void setFoliageColor(int biome, int color)
	{
		foliageColors.put(biome, color);
	}
	
	/**
	 * Sets the grass color for a biome
	 * @param biome Biome ID
	 * @param color ARGB color integer
	 */
	public static void setGrassColor(int biome, int color)
	{
		grassColors.put(biome, color);
	}
	
	/**
	 * Sets the water color for a biome
	 * @param biome Biome ID
	 * @param color ARGB color integer
	 */
	public static void setWaterColor(int biome, int color)
	{
		waterColors.put(biome, color);
	}
	
}