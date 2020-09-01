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
import java.util.Arrays;
import java.util.HashMap;

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
	
	private int blockHeight;
	private int blockWidth;
	private int chunkHeight;
	private int chunkWidth;
	private int chunkXOffset;
	private int chunkZOffset;
	private int completedFiles;
	private int totalChunks;
	private File directory;
	private MapWorker worker;
	private MapImage image;
	private HashMap<String, Region> regionCache;
	private HashMap<String, Integer> cacheCount;
	
	private static Object outputKey = new Object();
	private static Object regionCacheKey = new Object();
	
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
		
		regionCache = new HashMap<>();
		cacheCount = new HashMap<>();
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
		
		regionCache = new HashMap<>();
		cacheCount = new HashMap<>();
	}
	
	public Dimension(File dimDir, int[] size)
	{
		directory = dimDir;
		setChunkWidth(size[0]);
		setChunkHeight(size[1]);
		chunkXOffset = size[2];
		chunkZOffset = size[3];
		
		regionCache = new HashMap<>();
		cacheCount = new HashMap<>();
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
	
	public void startRender(int startY, int endY, int threadCount)
	{
		completedFiles = 0;
		File[] regions;
		//Filter ONLY for .mcr and .mca files.
		//TODO: Figure out how to handle folders with both files.
		//      Prompt the user? Add default in settings? Always use .mca? 
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
		int totalThreads = threadCount > regions.length ? regions.length : threadCount;
		
		Thread[] threads = new Thread[threadCount];
		
		for (int i = 0; i < threadCount; i++)
		{
			int startInd = i;
			threads[i] = new Thread() 
			{
				@Override
				public void run()
				{
					render(startY, endY, Integer.MAX_VALUE - 32, Integer.MIN_VALUE + 32,
							Integer.MAX_VALUE - 32, Integer.MIN_VALUE + 32, startInd, totalThreads, regions);
				}
				
			};
			
			threads[i].start();
		}
		
		for (Thread t : threads)
		{
			
			try
			{
				t.join();
			} 
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void startRender(int startY, int endY, int maxX, int minX, int maxZ, int minZ, int threadCount)
	{
		completedFiles = 0;
		File[] regions;
		//Filter ONLY for .mcr and .mca files.
		//TODO: Figure out how to handle folders with both files.
		//      Prompt the user? Add default in settings? Always use .mca? 
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
		int totalThreads = threadCount > regions.length ? regions.length : threadCount;
		
		Thread[] threads = new Thread[threadCount];
		
		for (int i = 0; i < threadCount; i++)
		{
			int startInd = i;
			threads[i] = new Thread() 
			{
				@Override
				public void run()
				{
					render(startY, endY, maxX, minX, maxZ, minZ, startInd, totalThreads, regions);
				}
				
			};
			
			threads[i].start();
		}
		
		for (Thread t : threads)
		{
			
			try
			{
				t.join();
			} 
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void render(int startY, int endY, int maxX, int minX, int maxZ, int minZ, int startInd, 
			int threadCount, File[] regions)
	{
		int currentRegion = 0;
		
		//Adds one region's worth of chunks to the bounds to include the edge regions into the render
		int minBoundsX = minX - 32;
		int maxBoundsX = maxX + 32;
		int minBoundsZ = minZ - 32;
		int maxBoundsZ = maxZ + 32;
		
		Chunk[][] loadedChunks;
		boolean[][] loadAttempts;
		
		for (int i = startInd; i < regions.length; i += threadCount)
		{
			File regionFile = regions[i];
			currentRegion = i;
			
			Region region;
			Region upRegion = null;
			Region rightRegion = null;
			Chunk chunk;
			Chunk upChunk = null;
			Chunk rightChunk = null;
			File upFile = null;
			File rightFile = null;
			String regionName;
			String[] nameSplit;
			int regionX;
			int regionZ;
			int curYVal;
			int light;
			int skyLight;
			int upYVal;
			int rightYVal;
			
			regionName = regionFile.getName();
			nameSplit = regionName.split("[.]");
			regionX = Integer.parseInt(nameSplit[1]);
			regionZ = Integer.parseInt(nameSplit[2]);
			
			//Skips region files that are out of bounds
			if (regionX * 32 < minBoundsX || regionX * 32 >= maxBoundsX)
				continue;
			if (regionZ * 32 < minBoundsZ || regionZ * 32 >= maxBoundsZ)
				continue;
			
			loadedChunks = new Chunk[REGION_SIZE][REGION_SIZE];
			loadAttempts = new boolean[REGION_SIZE][REGION_SIZE];
			
			if (Himeji.SHOW_ALL_EVENTS)
				System.out.println("Reading " + regionName);
			
			region = loadRegion(regionFile);
			
			//Checks if the region file above the current file exists
			for (String s : EXTENSIONS)
			{
				upFile = new File(String.format(regionFile.getParent() + 
						"\\r.%1$d.%2$d%3$s", regionX, regionZ - 1, s));
				
				if (upFile.exists())
				{
					upRegion = loadRegion(upFile);
					break;
				}
			}
			
			//Checks if the region file to the right of the current file exists
			for (String s : EXTENSIONS)
			{
				rightFile = new File(String.format(regionFile.getParent() + 
						"\\r.%1$d.%2$d%3$s", regionX + 1, regionZ, s));
				
				if (rightFile.exists())
				{
					rightRegion = loadRegion(rightFile);
					break;
				}
			}
			
			//Loops through each chunk in the region
			for (int chunkX = 0; chunkX < REGION_SIZE; chunkX++)
			{
				int absChunkX = chunkX + regionX * 32; 
				
				//Skips the chunk if it is out of the X render bounds
				if (absChunkX < minX || absChunkX > maxX)
					continue;
				
				for (int chunkZ = 0; chunkZ < REGION_SIZE; chunkZ++)
				{
					int absChunkZ = chunkZ + regionZ * 32; 
					
					//Checks if the chunk is out of the Z render bounds, 
					//  or if the chunk doesn't exist
					if (absChunkZ < minZ || absChunkZ > maxZ || 
							!region.hasChunk(chunkX, chunkZ))
						continue;
					
					try
					{
						chunk = getCurrentChunk(chunkX, chunkZ, region, loadedChunks, loadAttempts);
						if (chunk == null)
							continue;
						
						//Loads the chunk above the current one (for shading)
						upChunk = getUpChunk(chunkX, chunkZ, region, upRegion, loadedChunks, loadAttempts);
						
						//Loads the chunk to the right of the current one (for shading)
						rightChunk = getRightChunk(chunkX, chunkZ, region, rightRegion, loadedChunks, loadAttempts);
							
						int gridX = (chunk.getX() + chunkXOffset) * Chunk.CHUNK_SIZE;
						int gridZ = (chunk.getZ() + chunkZOffset) * Chunk.CHUNK_SIZE;
						
						//Gets the grid of colors, light data, and the y value for the chunk.
						//[x][z][0] = color
						//[x][z][1] = y coord
						//[x][z][2] = block light
						//[x][z][3] = sky light
						int[][][] chunkMap = chunk.getTopColors(startY, endY);
						
						if (chunkMap == null)
							continue;
						
						int xPos;
						int zPos;
						int curColor;
						
						// TODO condense this
						
						for (int blockX = 0; blockX < Chunk.CHUNK_SIZE; blockX++)
						{
							//X pixel on the image
							xPos = gridX + blockX;
							
							for (int blockZ = 0; blockZ < Chunk.CHUNK_SIZE; blockZ++)
							{
								//Y pixel on the image
								zPos = gridZ + blockZ;
								
								curColor = chunkMap[blockX][blockZ][0];
								curYVal = chunkMap[blockX][blockZ][1];
								light = chunkMap[blockX][blockZ][2];
								skyLight = chunkMap[blockX][blockZ][3];
								
								upYVal = curYVal;
								rightYVal = curYVal;
								
								if (SessionProperties.isRenderShadows())
								{
									upYVal = getUpYVal(curYVal, blockX, blockZ, startY, endY, upChunk, chunkMap);
									
									rightYVal = getRightYVal(curYVal, blockX, blockZ, startY, endY, 
											rightChunk, chunkMap);
									
									curColor = applyShading(curColor, light, skyLight, 
											curYVal, upYVal, rightYVal);
								}
								
								curColor = applyShading(curColor, light, skyLight, 
										curYVal, upYVal, rightYVal);
								
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
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					
					//Unloads the chunk above the current one as it's no longer used
					if (chunkZ >= 1)
						loadedChunks[chunkX][chunkZ - 1] = null;
				}
				
				//Clears out the current column after it's finished
				loadedChunks[chunkX][REGION_SIZE - 1] = null;
				loadedChunks[chunkX][REGION_SIZE - 2] = null;
			}
			
			updateOutput(regions.length, regionFile.getName());
			unloadRegion(regionFile);
			unloadRegion(upFile);
			unloadRegion(rightFile);
			
			for (boolean[] arr : loadAttempts)
				Arrays.fill(arr, false);
		}
	}
	
	private void updateOutput(int total, String file)
	{
		synchronized(outputKey)
		{
			completedFiles++;
			Himeji.displayMessage(String.format("Finished %1$s (%2$d/%3$d)",
					file, completedFiles, total));
		}
	}
	
	private int getUpYVal(int curYVal, int blockX, int blockZ, int startY, int endY, Chunk upChunk, int[][][] chunkMap)
	{
		//Checks if the current block is at the top of the chunk
		if (blockZ == 0)
		{
			//Sets the elevation of the above block to the current block if the above
			//  chunk doesn't exist
			if (upChunk == null)
				return curYVal;
			else
			{
				if (SessionProperties.isRenderUnderWater())
					return upChunk.getTopBlockYIgnoreWater(blockX, 
						Chunk.CHUNK_SIZE - 1, startY, endY);
				else
					return upChunk.getTopBlockY(blockX, 
							Chunk.CHUNK_SIZE - 1, startY, endY);
			}
		}
		
		return chunkMap[blockX][blockZ - 1][1];
	}
	
	private int getRightYVal(int curYVal, int blockX, int blockZ, int startY, int endY, Chunk rightChunk, 
			int[][][] chunkMap)
	{
		if (blockX == Chunk.CHUNK_SIZE - 1)
		{
			if (rightChunk == null)
				return curYVal;
			else
			{	
				if (SessionProperties.isRenderUnderWater())
					return rightChunk.getTopBlockYIgnoreWater(0, blockZ, startY, endY);
				else
					return rightChunk.getTopBlockY(0,  blockZ, startY, endY);
			}
		}
		
		return chunkMap[blockX + 1][blockZ][1];
	}
	
	private Chunk getRightChunk(int chunkX, int chunkZ, Region region, Region rightRegion, 
			Chunk[][] loadedChunks, boolean[][] loadAttempts) throws IOException
	{
		//Checks if the current chunk is at the far right of the region.
		if (chunkX == REGION_SIZE - 1)
		{
			//Checks if the region to the right the current region exists, then whether
			//  it contains the needed chunk
			if (rightRegion != null && rightRegion.hasChunk(0, chunkZ))
			{
				CompoundTag rightTag = rightRegion.getChunk(0, chunkZ);
				
				//returns the chunk, if it exists
				if (rightTag != null)
					return new Chunk(rightTag);
			}
		}
		else
		{
			if (region.hasChunk(chunkX + 1, chunkZ))
			{
				loadAttempts[chunkX][chunkZ] = true;
				
				Chunk result = null;
				CompoundTag rightTag = region.getChunk(chunkX + 1, chunkZ);
				
				//Returns the chunk to the right, if it exists
				if (rightTag != null)
					result = new Chunk(rightTag);
				
				loadedChunks[chunkX][chunkZ] = result;
				return result;
			}
		}
		
		return null;
	}
	
	private Chunk getUpChunk(int chunkX, int chunkZ, Region region, Region upRegion, 
			Chunk[][] loadedChunks, boolean[][] loadAttempts) throws IOException
	{
		Chunk result = null;
		
		//Checks if the current chunk is at the very top of the region.
		if (chunkZ == 0)
		{
			//Checks if the region above the current region exists, then whether
			//  it contains the needed chunk
			if (upRegion != null && upRegion.hasChunk(chunkX, 31))
			{
				CompoundTag upTag = upRegion.getChunk(chunkX, REGION_SIZE - 1);
				
				//Returns the chunk, if it exists
				if (upTag != null)
					result = new Chunk(upTag);
			}
		}
		else
		{
			//If the chunk has already been loaded, returns that chunk.
			if (!loadAttempts[chunkX][chunkZ - 1])
				return loadedChunks[chunkX][chunkZ - 1];
			
			//Checks if the above chunk is set to a chunk already (from previous
			//  chunk). Loads it if is null and the chunk exists in the region
			if (region.hasChunk(chunkX, chunkZ - 1))
			{
				loadAttempts[chunkX][chunkZ - 1] = true;
				CompoundTag upTag = region.getChunk(chunkX, chunkZ - 1);
				
				//Returns the chunk above, if it exists
				if (upTag != null)
					result = new Chunk(upTag);
				
				loadedChunks[chunkX][chunkZ - 1] = result;
			}
		}
		
		return result;
	}

	private Chunk getCurrentChunk(int chunkX, int chunkZ, Region region, 
			Chunk[][] loadedChunks, boolean[][] loadAttempts) 
	{
		if (loadAttempts[chunkX][chunkZ])
			return loadedChunks[chunkX][chunkZ];
		
		loadAttempts[chunkX][chunkZ] = true;
		try
		{
			CompoundTag chunkTag = region.getChunk(chunkX, chunkZ);
			
			//Catches a corner case where the chunk "exists," but doesn't 
			//  return a tag when using .getChunk()
			if (chunkTag == null)
				return null;
			
			Chunk result = new Chunk(chunkTag);
			loadedChunks[chunkX][chunkZ] = result;
			return result;
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private Region loadRegion(File regionFile)
	{
		if (regionFile == null)
			return null;
		
		synchronized(regionCacheKey)
		{
			String key = regionFile.getName();
			
			if (regionCache.containsKey(key))
			{
				cacheCount.put(key, cacheCount.get(key) + 1);
				return regionCache.get(key);
			}
			
			Region result = new Region(regionFile);
			regionCache.put(key, result);
			cacheCount.put(key, 1);
			
			return result;
		}
	}
	
	private void unloadRegion(File regionFile)
	{
		if (regionFile == null)
			return;
		
		synchronized(regionCacheKey)
		{
			String key = regionFile.getName();
			
			if (!regionCache.containsKey(key))
				return;
			
			int remaining = cacheCount.get(key) - 1;
			
			if (remaining == 0)
			{
				Region region = regionCache.get(key);
				region.close();
				cacheCount.remove(key);
				regionCache.remove(key);
			}
			else
			{
				cacheCount.put(key, remaining);
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
	public void createMapImage()
	{
		// TODO Check if file is valid, if it exists
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
	public int applyShading(int color, int light, int skyLight, int yVal, int upYVal, int rightYVal)
	{
		int a, r, g, b;
		boolean applyMult = false;
		float lightMult = 1;
		float heightMult = 1f;
		
		float brightness = SessionProperties.getNightBrightness();
		float shadowIntensity = SessionProperties.getShadowIntensity();
		float highlightIntensity = SessionProperties.getHighlightIntensity();
		
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
			
			//Testing an overlay for showing mob spawn areas
			/*
			if (light < 8)
			{
				if (skyLight < 8)
				{
					r = (int)(r * 1.5f);
					g = (int)(g * .5f);
					b = (int)(b * .5f);
				}
				else
				{
					r = (int)(r * 1.5f);
					g = (int)(g * 1.5f);
					b = (int)(b * .5f);
				}
			}*/
			
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