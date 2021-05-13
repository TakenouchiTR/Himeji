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

package com.takenouchitr.himeji;

import java.awt.Desktop;
import java.io.*;
import java.util.*;
import javax.swing.*;
import com.takenouchitr.himeji.MCCompat.*;
import com.takenouchitr.himeji.frames.MissingBiomesFrame;

public class MapWorker extends SwingWorker<Void, String>
{
	public static final int MEGABYTE = 1_048_576;
	public static final int GIGABYTE = 1_073_741_824;
	
	private boolean failed;
	
	@Override
	protected Void doInBackground() throws Exception 
	{
		long start = System.currentTimeMillis();
		
		failed = false;
		Himeji.log("Starting worker");
		
		File worldPath = new File(Himeji.getProperty(Property.WORLD_PATH));
		if (!World.validateDir(worldPath))
			return null;
		
		Dimension dim;
		File dimFile;
		
		// TODO Check for whether the dimension exists
		Himeji.log("Getting dimension");
		dimFile = getDimensionFile(worldPath);
		
		//Gets the Y bounds for the render
		int startY = Integer.parseInt(Himeji.getProperty(Property.START_Y));
		int endY = Integer.parseInt(Himeji.getProperty(Property.END_Y));
		
		if (!dimFile.exists())
		{
			Himeji.log("Dimension does not exist");
			
			JOptionPane.showMessageDialog(Himeji.getFrame(), "Dimension does not exist.\n" + 
				"Please change the selected dimension.", "Dimension not found", 
				JOptionPane.WARNING_MESSAGE);
			
			failed = true;
			return null;
		}
		
		publish("Getting image dimensions...");
		
		int threadCount = Integer.parseInt(Himeji.getProperty(Property.THREAD_COUNT));
		
		//If only a certain area is being rendered
		if (Himeji.getProperty(Property.USE_AREA).equals("true"))
		{
			int startX = Integer.parseInt(Himeji.getProperty(Property.START_X));
			int startZ = Integer.parseInt(Himeji.getProperty(Property.START_Z));
			int endX = Integer.parseInt(Himeji.getProperty(Property.END_X));
			int endZ = Integer.parseInt(Himeji.getProperty(Property.END_Z));
			
			long width = startX - endX;
			long height = startZ - endZ;
			
			if (!checkValidFileSize(width, height))
			{
				failed = true;
				return null;
			}
			
			dim = new Dimension(dimFile, startX, endX, startZ, endZ);
			
			Himeji.log("Creating MapImage");
			dim.createMapImage();
			Himeji.log("MapImage created");
			
			publish("Getting blocks...");
			dim.startRender(startY, endY, startX, endX, startZ, endZ, threadCount);
		}
		//If entire map is being rendered
		else
		{
			int[] dimBounds = Dimension.calcDimSize(dimFile);
			
			long width = dimBounds[0] * 16;
			long height = dimBounds[1] * 16;
			
			if (!checkValidFileSize(width, height))
			{
				failed = true;
				return null;
			}
			
			dim = new Dimension(dimFile, dimBounds);
			
			Himeji.log("Creating MapImage");
			dim.createMapImage();
			Himeji.log("MapImage created");
			
			publish("Getting blocks...");
			dim.startRender(startY, endY, threadCount);
		}
		
		publish("Rendering image...");
		dim.render();
		
		long end = System.currentTimeMillis();
		int seconds = (int)(end - start) / 1000;
		String elapsedTime = String.format("Time elapsed:  %1$02d:%2$02d;%3$03d", 
			seconds / 60, seconds % 60, (end - start) % 1000);
		
		publish(elapsedTime);
		
		return null;
	}

	@Override
	protected void process(List<String> chunks)
	{
		Himeji.displayMessage(chunks.get(chunks.size() - 1));
	}
	
	@Override
	protected void done()
	{
		checkMissingBlocks();
		
		checkUnknownBiomes();

		openResult();
		
		Himeji.setComponentsEnabled(true);
	}
	
	/**
	 * Gets the dimension folder for a given world path.
	 * @param worldPath base directory for a world
	 * @return File object for the dimension folder
	 */
	private File getDimensionFile(File worldPath) 
	{
		String dimensionName = Himeji.getProperty(Property.DIMENSION);
		
		switch(dimensionName)
		{
			case "Overworld":
				return new File(worldPath.getPath() + "\\region");
			case "Nether":
				return new File(worldPath.getPath() + "\\DIM-1\\region");
			case "End":
				return new File(worldPath.getPath() + "\\DIM1\\region");
			default:
				return new File("");
		}
	}
	
	/**
	 * Checks if a file size of a given width and height will result in an image that is too large
	 * to render.
	 * @param width  width of the image in pixels
	 * @param height height of the image in pixels
	 * @return true iff the expected file size is under one gigabyte 
	 */
	private boolean checkValidFileSize(long width, long height)
	{
		long estimatedSize = width * height * 4;
		long maxSize = Long.parseLong(Himeji.getProperty(Property.IMAGE_MEMORY));
		
		if (estimatedSize / MEGABYTE > maxSize)
		{
			float megs = Math.round(estimatedSize / (float)MEGABYTE);
			publish("Expected memory allocation too large: " + estimatedSize + " bytes");
			
			JOptionPane.showMessageDialog(Himeji.getFrame(), 
				String.format("Memory allocation for image expected to exceed %1$dMB (%2$.2f).\n"
				+ "Please restict the render area to reduce the size or change the\n"
				+ "limit in the settings.", maxSize, megs),
				"Memory warning", JOptionPane.WARNING_MESSAGE); 
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * Opens the rendered image or folder, if the user chooses to
	 */
	private void openResult()
	{
		String fileString = null;
		int result;
		
		if (!failed)
		{
			switch (Himeji.getProperty(Property.OPEN_IMAGE_SETTING.key))
			{
				case "ask_image":
					result = JOptionPane.showConfirmDialog(Himeji.getFrame(),
							"Would you like to open the image?", 
							"Open image?", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					
					if (result == JOptionPane.YES_OPTION)
						fileString = Himeji.getProperty(Property.OUTPUT_PATH.key);
					break;
					
				case "ask_folder":
					result = JOptionPane.showConfirmDialog(Himeji.getFrame(),
							"Would you like to open the image's folder?", 
							"Open folder?", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					
					if (result == JOptionPane.YES_OPTION)
						fileString = new File(Himeji.getProperty(Property.OUTPUT_PATH.key)).getParent();
					break;
					
				case "always_image":
					fileString = Himeji.getProperty(Property.OUTPUT_PATH.key);
					break;
					
				case "always_folder":
					fileString = new File(Himeji.getProperty(Property.OUTPUT_PATH.key)).getParent();
					break;
			}
			
			if (fileString != null)
			{
				try
				{
					File outputFile = new File(fileString);
					
					Desktop.getDesktop().open(outputFile);
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Checks if there were any missing block IDs found during the render.
	 */
	private void checkMissingBlocks()
	{
		int size = Block.getMissingIds().size();
		
		if (size > 0 && Himeji.getProperty(Property.SHOW_MISSING_BLOCK).equals("true"))
		{
			int result = JOptionPane.showConfirmDialog(Himeji.getFrame(),size + " missing block ID(s) found.\n" + 
				"Would you like to add them now?", "Missing IDs found", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
			
			if (result == JOptionPane.YES_OPTION)
			{
				List<String> ids = new ArrayList<String>();
				for (String s : Block.getMissingIds())
					ids.add(s);
				
				for (String s : ids)
				{
					Block.setBlockColor(s, 0xFF000000);
				}
				
				JOptionPane.showMessageDialog(Himeji.getFrame(), "Please see bottom of the list in " + 
					"\"Config>Block Colors\" for the new ids.\nThe addition(s) are black by default and " + 
					"are not saved automatically.\nPlease update the color and save them there.", 
					"Missing IDs added", JOptionPane.INFORMATION_MESSAGE);
			}
			
			Block.getMissingIds().clear();
		}
	}
	
	/**
	 * Checks if there were any unknown biome IDs found during the render.
	 */
	private void checkUnknownBiomes()
	{
		int size = Block.getUnknownBiomes().size();
		if (size > 0 && Himeji.getProperty(Property.SHOW_UNKNOWN_BIOME).equals("true"))
		{
			int result = JOptionPane.showConfirmDialog(Himeji.getFrame(),
					"Unknown biomes detected.\n"
					+ "All colors have been rendered using Forest colors by default.\n"
					+ "Would you like to see a list of IDs and the first coordinate they were found at?", 
					"Unknown biomes found", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			
			if (result == JOptionPane.YES_OPTION)
			{
				MissingBiomesFrame mbf = new MissingBiomesFrame();
				mbf.setLocationRelativeTo(Himeji.getFrame());
				mbf.setVisible(true);
			}
			Block.getUnknownBiomes().clear();
		}
	}
}