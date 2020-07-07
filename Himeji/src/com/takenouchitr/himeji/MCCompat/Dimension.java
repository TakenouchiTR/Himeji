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

import java.io.*;
import java.util.ArrayList;

import com.mojang.nbt.*;
import com.takenouchitr.himeji.Himeji;
import com.takenouchitr.himeji.MapImage;
import com.takenouchitr.himeji.MapWorker;
import com.takenouchitr.himeji.Property;
import com.takenouchitr.himeji.SessionProperties;

public class Dimension 
{
	public static final int REGION_SIZE = 32;
	public static final String[] EXTENSIONS = {".mca", ".mcr"};
	
	protected int blockHeight;
	protected int blockWidth;
	protected int chunkHeight;
	protected int chunkWidth;
	protected int chunkXOffset;
	protected int chunkZOffset;
	protected int totalChunks;
	protected File directory;
	protected MapWorker worker;
	private MapImage image;
	
	/**
	 * Creates a Dimension with a size determined by the files in the region
	 * directory.
	 * @param dimDir Folder that contains the dimension's data
	 */
	public Dimension(File dimDir)
	{
		int[] size;
		
		directory = dimDir;
		Himeji.log("Calculating dimension size...");
		size = calcDimSize(dimDir);
		Himeji.log("Setting bounds data");
		setChunkWidth(size[0]);
		setChunkHeight(size[1]);
		chunkXOffset = size[2];
		chunkZOffset = size[3];
	}
	
	public Dimension(File dimDir, int maxX, int minX, int maxZ, int minZ)
	{
		int[] size;
		
		directory = dimDir;
		size = calcDimSize(dimDir, maxX, minX, maxZ, minZ);
		setChunkWidth(size[0]);
		setChunkHeight(size[1]);
		chunkXOffset = size[2];
		chunkZOffset = size[3];
	}
	
	public Dimension(File dimDir, int[] size)
	{
		directory = dimDir;
		setChunkWidth(size[0]);
		setChunkHeight(size[1]);
		chunkXOffset = size[2];
		chunkZOffset = size[3];
	}
	
	/**
	 * Calculates how large the dimension is from the file names in its
	 * directory.
	 * @param dimension Folder that contains the dimension's data
	 * @return          Array containing both the height and width in chunks, 
	 *                  and the chunk x and z offset
	 */
	public static int[] calcDimSize(File dimension)
	{
		int[] result = {0, 0, 0, 0};
		int maxHeight = Integer.MIN_VALUE;
		int maxWidth = Integer.MIN_VALUE;
		int minHeight = Integer.MAX_VALUE;
		int minWidth = Integer.MAX_VALUE;
		
		Himeji.log("Getting Region files");
		File[] chunks = dimension.listFiles();
		Himeji.log(chunks.length + " files found");

		if (Himeji.SHOW_ALL_EVENTS)
			System.out.println("Getting chunk files...");
		
		int counter = 0;
		
		for (File c : chunks)
		{
			String loc = c.getName();
			counter++;

			if (Himeji.SHOW_ALL_EVENTS)
				System.out.printf("Checking file %s\n", loc);
			Himeji.log(String.format("Checking file %1$s (%2$d)", loc, counter));
			
			String[] locSplit = loc.split("[.]");
			
			int fileWidth = Integer.parseInt(locSplit[1]);
			int fileHeight = Integer.parseInt(locSplit[2]);
			
			if (fileHeight < minHeight)
				minHeight = fileHeight;
			if (fileHeight > maxHeight)
				maxHeight = fileHeight;
			
			if (fileWidth < minWidth)
				minWidth = fileWidth;
			if (fileWidth > maxWidth)
				maxWidth = fileWidth;
		}
		Himeji.log("Bounds files have been found");
		Himeji.log("Calculating chunk size");
		result[0] = (Math.abs(maxWidth - minWidth) + 1) * 32;
		result[1] = (Math.abs(maxHeight - minHeight) + 1) * 32;
		result[2] = -minWidth * 32;
		result[3] = -minHeight * 32;
		
		if (Himeji.SHOW_ALL_EVENTS)
			System.out.printf("Height: %1$d\nWidth : %2$d\n", 
					result[0], result[1]);
		
		return result;
	}
	
	public static int[] calcDimSize(File dimension, int maxX, int minX, int maxZ, int minZ)
	{
		int[] result = {Math.abs(maxX - minX) + 1, Math.abs(maxZ - minZ) + 1, -minX, -minZ};
		
		return result;
	}
	
	/**
	 * Validates whether a dimension exists. A dimension exists if it has been 
	 * visited in-game, causing specific files and folders to generate.
	 * @param dimension Folder that contains the dimension's data
	 * @return          Returns true if the dimension folder contains a valid dimension
	 */
	public static boolean validateGen(File dir)
	{
		boolean result = false;
		
		File[] dimFolders;
		
		if (dir.isDirectory())
		{
			dimFolders = dir.listFiles(new FilenameFilter()
				{
					@Override
					public boolean accept(File current, String name)
					{
						return new File(current, name).isDirectory();
					}
				});
			
			boolean hasRegion = false;
			boolean hasPoi = false;
			
			for (File f : dimFolders)
			{
				switch(f.getName())
				{
					case "region":
						hasRegion = true;
						break;
					case "poi":
						hasPoi = true;
						break;
				}
			}
			
			if (hasRegion && hasPoi)
				result = true;
		}
		
		return result;
	}
	
	/**
	 * Creates an image of the entire map between two set Y coord bounds
	 * @param startY Highest Y value
	 * @param endY Lowest Y Value
	 */
	public void drawBlocksToBuffer(int startY, int endY)
	{
		drawBlocksToBuffer(startY, endY, Integer.MAX_VALUE - 32, Integer.MIN_VALUE + 32,
				Integer.MAX_VALUE - 32, Integer.MIN_VALUE + 32);
	}
	
	/**
	 * Creates an image of the entire map between two set Y coord bounds
	 * @param startY Highest Y value
	 * @param endY Lowest Y Value
	 * @param maxX Farthest right X value
	 * @param minX Farthest left X value
	 * @param maxZ Farthest down Z value
	 * @param minZ Farthest up Z value
	 */
	public void drawBlocksToBuffer(int startY, int endY, int maxX, int minX, int maxZ, int minZ)
	{
		File[] regions;
		FileFilter mcaFilter = new FileFilter()
		{
			@Override
			public boolean accept(File pathname) 
			{
				return pathname.getName().endsWith(".mca") || 
						pathname.getName().endsWith(".mcr");
			}
		};
		
		regions = directory.listFiles(mcaFilter);
		int finishedRegions = 0;
		
		for (File regionFile : regions)
		{
			finishedRegions++;
			
			Region region;
			Region upRegion = null;
			Region rightRegion = null;
			Chunk chunk;
			Chunk upChunk = null;
			Chunk rightChunk = null;
			File upFile;
			File rightFile;
			String regionName;
			String[] nameSplit;
			int regionX;
			int regionZ;
			int curYVal;
			int light;
			int upYVal;
			int rightYVal;
			
			regionName = regionFile.getName();
			nameSplit = regionName.split("[.]");
			regionX = Integer.parseInt(nameSplit[1]);
			regionZ = Integer.parseInt(nameSplit[2]);
			
			if (regionX * 32 < minX - 32 || regionX * 32 >= maxX + 32)
				continue;
			if (regionZ * 32 < minZ - 32 || regionZ * 32 >= maxZ + 32)
				continue;
			
			if (Himeji.SHOW_ALL_EVENTS)
				System.out.println("Reading " + regionName);
			
			Himeji.displayMessage(String.format("Reading %1$s (%2$d/%3$d)",
					regionName, finishedRegions, regions.length));
			
			region = new Region(regionFile);
			
			for (String s : EXTENSIONS)
			{
				upFile = new File(String.format(regionFile.getParent() + 
						"\\r.%1$d.%2$d%3$s", regionX, regionZ - 1, s));
				
				if (upFile.exists())
				{
					upRegion = new Region(upFile);
					break;
				}
			}
			
			for (String s : EXTENSIONS)
			{
				rightFile = new File(String.format(regionFile.getParent() + 
						"\\r.%1$d.%2$d%3$s", regionX + 1, regionZ, s));
				
				if (rightFile.exists())
				{
					rightRegion = new Region(rightFile);
					break;
				}
			}
			
			for (int chunkX = 0; chunkX < REGION_SIZE; chunkX++)
			{
				int absChunkX = chunkX + regionX * 32; 
				if (absChunkX < minX || absChunkX > maxX)
					continue;
				
				upChunk = null;
				
				for (int chunkZ = 0; chunkZ < REGION_SIZE; chunkZ++)
				{
					int absChunkZ = chunkZ + regionZ * 32; 
					
					if (absChunkZ < minZ || absChunkZ > maxZ || 
							!region.hasChunk(chunkX, chunkZ))
					{
						upChunk = null;
						continue;
					}
					
					try
					{
						CompoundTag chunkTag = region.getChunk(chunkX, chunkZ);
						if (chunkTag == null)
						{
							upChunk = null;
							continue;
						}
						if (chunkZ == 0)
						{
							if (upRegion != null && upRegion.hasChunk(chunkX, 31))
							{
								CompoundTag upTag = upRegion.getChunk(chunkX, REGION_SIZE - 1);
								
								if (upTag != null)
									upChunk = new Chunk(upTag);
							}
						}
						else
						{
							if (upChunk == null && region.hasChunk(chunkX, chunkZ - 1))
							{
								CompoundTag upTag = region.getChunk(chunkX, chunkZ - 1);
								
								if (upTag != null)
									upChunk = new Chunk(upTag);
							}
						}
						
						if (chunkX == REGION_SIZE - 1)
						{
							if (rightRegion != null && rightRegion.hasChunk(0, chunkZ))
							{
								CompoundTag rightTag = rightRegion.getChunk(0, chunkZ);
								
								if (rightTag != null)
									rightChunk = new Chunk(rightTag);
							}
						}
						else
						{
							if (region.hasChunk(chunkX + 1, chunkZ))
							{
								CompoundTag rightTag = region.getChunk(chunkX + 1, chunkZ);
								
								if (rightTag != null)
									rightChunk = new Chunk(rightTag);
							}
						}
						chunk = new Chunk(chunkTag);
							
						int gridX = (chunk.getX() + chunkXOffset) * Chunk.CHUNK_SIZE;
						int gridZ = (chunk.getZ() + chunkZOffset) * Chunk.CHUNK_SIZE;
						
						int[][][] chunkMap = chunk.getTopColors(startY, endY);
						
						if (chunkMap == null)
							continue;
						
						int xPos;
						int zPos;
						int curColor;
						
						// TODO condense this
						
						for (int blockX = 0; blockX < Chunk.CHUNK_SIZE; blockX++)
						{
							xPos = gridX + blockX;
							for (int blockZ = 0; blockZ < Chunk.CHUNK_SIZE; blockZ++)
							{
								zPos = gridZ + blockZ;
								
								curColor = chunkMap[blockX][blockZ][0];
								curYVal = chunkMap[blockX][blockZ][1];
								light = chunkMap[blockX][blockZ][2];
								
								if (SessionProperties.renderShadows)
								{
									if (blockZ == 0)
									{
										if (upChunk == null)
											upYVal = curYVal;
										else
										{
											if (SessionProperties.renderUnderWater)
												upYVal = upChunk.getTopBlockYIgnoreWater(blockX, 
													Chunk.CHUNK_SIZE - 1, startY, endY);
											else
												upYVal = upChunk.getTopBlockY(blockX, 
														Chunk.CHUNK_SIZE - 1, startY, endY);
										}
									}
									else
									{
										upYVal = chunkMap[blockX][blockZ - 1][1];
									}
									
									if (blockX == Chunk.CHUNK_SIZE - 1)
									{
										if (rightChunk == null)
											rightYVal = curYVal;
										else
										{	
											if (SessionProperties.renderUnderWater)
												rightYVal = rightChunk.getTopBlockYIgnoreWater(0, blockZ, startY, endY);
											else
												rightYVal = rightChunk.getTopBlockY(0,  blockZ, startY, endY);
										}
									}
									else
									{
										rightYVal = chunkMap[blockX + 1][blockZ][1];
									}
									
									curColor = applyShading(curColor, light, curYVal, upYVal, rightYVal);
								}
								else
								{
									curColor = applyShading(curColor, light, curYVal, curYVal, curYVal);
								}
								
								try
								{
									image.setPixel(xPos, zPos, curColor);
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
							}
						}
						upChunk = chunk;
					}
					catch (Exception e) 
					{
						e.printStackTrace();
						upChunk = null;
					}
				}
			}
		}
	}
	
	/**
	 * Used as a test, please ignore.
	 * @param startY
	 */
	public void drawEntitiesToBuffer(int startY)
	{
		File[] regions = directory.listFiles();
		ArrayList<String> mobNames = new ArrayList<String>();
		ArrayList<Integer> mobCount = new ArrayList<Integer>();
		
		for (File regionFile : regions)
		{
			String regionName = regionFile.getName();
			Region region = new Region(regionFile);
			
			if (Himeji.SHOW_ALL_EVENTS)
				System.out.println("Reading " + regionName);
			
			int xPos;
			int zPos;
			
			for (int chunkX = 0; chunkX < REGION_SIZE; chunkX++)
			{
				for (int chunkZ = 0; chunkZ < REGION_SIZE; chunkZ++)
				{
					if (!region.hasChunk(chunkX, chunkZ))
						continue;
					
					try
					{
						CompoundTag chunkTag = region.getChunk(chunkX, chunkZ);
						if (chunkTag == null)
							continue;
						
						CompoundTag levelTag = chunkTag.getCompound("Level");
						if (levelTag == null)
							continue;
						
						ListTag<? extends Tag> entitiesList = levelTag.getList("Entities");
						if (entitiesList == null || entitiesList.size() == 0)
							continue;
						
						
						for (int i = 0; i < entitiesList.size(); i++)
						{
							CompoundTag entityTag = (CompoundTag)entitiesList.get(i);
							ListTag<? extends Tag> position = entityTag.getList("Pos");
							
							DoubleTag yTag = (DoubleTag)(position.get(1));
							if ((int)yTag.data > startY)
								continue;
									
							String id = entityTag.getString("id");
							DoubleTag xTag = (DoubleTag)(position.get(0));
							DoubleTag zTag = (DoubleTag)(position.get(2));
							
							boolean found = false;
							for (int j = 0; j < mobNames.size(); j++)
							{
								if (mobNames.get(j).equals(id))
								{
									mobCount.set(j, new Integer(mobCount.get(j) + 1));
									found = true;
									break;
								}
							}
							if (!found)
							{
								mobNames.add(id);
								mobCount.add(1);
							}
							
							id = id.toUpperCase();
							xPos = (int)(xTag.data);
							zPos = (int)(zTag.data);
							
							xPos += (chunkTag.getInt("xPos") + chunkXOffset * REGION_SIZE) * Chunk.CHUNK_SIZE;
							zPos += (chunkTag.getInt("zPos") + chunkZOffset * REGION_SIZE) * Chunk.CHUNK_SIZE;;
							
							Entity entity = Entity.valueOf(id.split(":")[1]);
							
							image.setPixel(xPos, zPos, entity.COLOR);
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		for (int i = 0; i < mobCount.size(); i++)
		{
			System.out.println(mobNames.get(i) + " - " + mobCount.get(i));
		}
	}
	
	/**
	 * Creates the MapImage to write pixels to.
	 */
	public void createRenderGrid()
	{
		// TODO Check if file is valid, if it exists, change method name
		image = new MapImage(blockWidth, blockHeight, 
				new File(Himeji.getProperty(Property.OUTPUT_PATH)));
	}
	
	/**
	 * Applies shading to a block color.
	 * @param color Color of the block
	 * @param light The light level to render the block at
	 * @param yVal The height of the block
	 * @param upYVal The height of the block above the target
	 * @param rightYVal The height of the block to the right of the target
	 * @return shaded color
	 */
	public int applyShading(int color, int light, int yVal, int upYVal, int rightYVal)
	{
		int a, r, g, b;
		boolean applyMult = false;
		float lightMult = 1;
		float heightMult = 1f;
		
		float brightness = SessionProperties.nightBrightness;
		float shadowIntensity = SessionProperties.shadowIntensity;
		float highlightIntensity = SessionProperties.highlightIntensity;
		
		if (light < 15)
		{
			applyMult = true;
			lightMult = (light / 15f * (1f - brightness)) + brightness;
		}
		
		if (yVal < upYVal || yVal < rightYVal)
		{
			applyMult = true;
			heightMult = 1 - shadowIntensity;
		}
		else if (yVal > upYVal || yVal > rightYVal)
		{
			applyMult = true;
			heightMult = 1 + highlightIntensity;
		}
		
		if (applyMult)
		{
			a = (color & 0xFF000000);
			r = (int) (((color & 0x00FF0000) >>> 16) * heightMult * lightMult);
			g = (int) (((color & 0x0000FF00) >>> 8) * heightMult * lightMult);
			b = (int) ((color & 0x000000FF) * heightMult * lightMult);
			
			if (r > 255)
				r = 255;
			else if (r < 0)
				r = 0;
			
			if (g > 255)
				g = 255;
			else if (g < 0)
				g = 0;
			
			if (b > 255)
				b = 255;
			else if (g < 0)
				g = 0;
			
			color = a | (r << 16) | (g << 8) | b;
		}
		
		return color;
	}
	
	/**
	 * Sets the width of a dimension in chunks. Sets the width in blocks
	 * as well.
	 * @param chunkWidth Width of dimension in chunks
	 */
	public void setChunkWidth(int chunkWidth) 
	{
		this.chunkWidth = chunkWidth;
		blockWidth = chunkWidth * 16;
	}
	
	/**
	 * Sets the height of a dimension in chunks. Sets the height in blocks
	 * as well.
	 * @param chunkWidth Width of dimension in chunks
	 */
	public void setChunkHeight(int chunkHeight) 
	{
		this.chunkHeight = chunkHeight;
		blockHeight = chunkHeight * 16;
	}
	
	/**
	 * Returns the Dimension's width in blocks.
	 * @return Width of dimension in blocks.
	 */
	public int getBlockWidth() 
	{
		return blockWidth;
	}
	
	/**
	 * Returns the Dimension's height in blocks.
	 * @return Height of dimension in blocks.
	 */
	public int getBlockHeight() 
	{
		return blockHeight;
	}
	
	/**
	 * Gets the Y coordinate stored in the first byte of a color integer
	 * @param color
	 * @return
	 */
	public int getYCoord(int color)
	{
		return color>>>24;
	}

	/**
	 * Saves the image as a file
	 */
	public void render()
	{
		image.renderImage();
	}
}