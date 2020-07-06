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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import com.takenouchitr.himeji.MCCompat.Block;
import com.takenouchitr.himeji.MCCompat.Dimension;
import com.takenouchitr.himeji.MCCompat.World;
import com.takenouchitr.himeji.frames.MissingBiomesFrame;

public class MapWorker extends SwingWorker<Void, String>
{
	public static final int GIGABYTE = 1_073_741_824;
	
	@Override
	protected Void doInBackground() throws Exception 
	{
		Himeji.log("Starting worker");
		
		File file = new File(Himeji.getProperty(Property.WORLD_PATH));
		if (World.validateDir(file)) 
		{
			long start = System.currentTimeMillis();
			
			Dimension dim;
			File dimFile;
			
			// TODO Check for whether the dimension exists
			Himeji.log("Getting dimension");
			String dimensionName = Himeji.getProperty(Property.DIMENSION);
			switch(dimensionName)
			{
				case "Overworld":
					dimFile = new File(file.getPath() + "\\region");
					break;
				case "Nether":
					dimFile = new File(file.getPath() + "\\DIM-1\\region");
					break;
				case "End":
					dimFile = new File(file.getPath() + "\\DIM1\\region");
					break;
				default:
					dimFile = new File("");
			}
			
			int startY = Integer.parseInt(Himeji.getProperty(Property.START_Y));
			int endY = Integer.parseInt(Himeji.getProperty(Property.END_Y));
			
			if (!dimFile.exists())
			{
				Himeji.log("Dimension does not exist");
				
				JOptionPane.showMessageDialog(Himeji.frame, "Dimension does not exist.\n" + 
					"Please change the selected dimension.", "Dimension not found", 
					JOptionPane.WARNING_MESSAGE);
				
				return null;
			}
			
			publish("Getting area size...");
			if (Himeji.getProperty(Property.USE_AREA).equals("true"))
			{
				int startX = Integer.parseInt(Himeji.getProperty(Property.START_X));
				int startZ = Integer.parseInt(Himeji.getProperty(Property.START_Z));
				int endX = Integer.parseInt(Himeji.getProperty(Property.END_X));
				int endZ = Integer.parseInt(Himeji.getProperty(Property.END_Z));
				
				long width = startX - endX;
				long height = startZ - endZ;
				long size = width * height * 4;
				
				if (size > 1_073_741_824)
				{
					publish("Expected filesize too large: " + size + " bytes");
					
					JOptionPane.showMessageDialog(Himeji.frame, "Image file expected to exceed 1GB (" + 
						(size / GIGABYTE) + "GB).\nPlease restict the render area to reduce the size.",
						"Image size warning", JOptionPane.WARNING_MESSAGE); 
					return null;
				}
				
				dim = new Dimension(dimFile, startX, endX, startZ, endZ);
				
				dim.createRenderGrid();
				
				publish("Getting blocks...");
				dim.drawBlocksToBuffer(startY, endY, startX, endX, startZ, endZ);
			}
			else
			{
				Himeji.log("Creating dimension " + dimensionName + "...");
				int[] dimBounds = Dimension.calcDimSize(dimFile);
				
				long width = dimBounds[0] * 16;
				long height = dimBounds[1] * 16;
				long size = width * height * 4;
				
				if (size > 1_073_741_824)
				{
					publish("Expected filesize too large: " + size + " bytes");
					
					JOptionPane.showMessageDialog(Himeji.frame, "Image file expected to exceed 1GB (" + 
						(size / GIGABYTE) + "GB).\nPlease restict the render area to reduce the size.",
						"Image size warning", JOptionPane.WARNING_MESSAGE); 
					return null;
				}
				
				dim = new Dimension(dimFile, dimBounds);
				
				Himeji.log("Creating render grid");
				dim.createRenderGrid();
				Himeji.log("Render grid created");
				
				publish("Getting blocks...");
				dim.drawBlocksToBuffer(startY, endY);
			}
			
			publish("Rendering image...");
			dim.render();
			
			long end = System.currentTimeMillis();
			int seconds = (int)(end - start) / 1000;
			String elapsed = String.format("Time elapsed:  %1$02d:%2$02d;%3$03d", 
				seconds / 60, seconds % 60, (end - start) % 1000);
			
			publish(elapsed);
		}
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
		int size = Block.getMissingIds().size();
		if (size > 0 && Himeji.getProperty(Property.SHOW_MISSING_BLOCK).equals("true"))
		{
			int result = JOptionPane.showConfirmDialog(Himeji.frame,size + " missing block ID(s) found.\n" + 
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
				
				JOptionPane.showMessageDialog(Himeji.frame, "Please see bottom of the list in " + 
					"\"Config>Block Colors\" for the new ids.\nThe addition(s) are black by default and " + 
					"are not saved automatically.\nPlease update the color and save them there.", 
					"Missing IDs added", JOptionPane.INFORMATION_MESSAGE);
			}
			
			Block.getMissingIds().clear();
		}
		
		size = Block.getUnknownBiomes().size();
		if (size > 0 && Himeji.getProperty(Property.SHOW_UNKNOWN_BIOME).equals("true"))
		{
			int result = JOptionPane.showConfirmDialog(Himeji.frame,
					"Unknown biomes detected.\n"
					+ "All colors have been rendered using Forest colors by default.\n"
					+ "Would you like to see a list of IDs and the first coordinate they were found at?", 
					"Unknown biomes found", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			
			if (result == JOptionPane.YES_OPTION)
			{
				MissingBiomesFrame mbf = new MissingBiomesFrame();
				mbf.setLocationRelativeTo(Himeji.frame);
				mbf.setVisible(true);
			}
			Block.getUnknownBiomes().clear();
		}
		
		Himeji.setComponentsEnabled(true);
	}
}