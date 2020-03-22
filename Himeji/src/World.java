//Himeji Name:   World.java
//Date:           3/13/2020
//Himejimer:     Shawn Carter
//Description:    This class holds information for a Minecraft world.

import java.io.*;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.NbtIo;

public class World 
{
	private File rootDir;
	private Dimension overworld;
	private Dimension nether;
	private Dimension theEnd;
	private CompoundTag dataTag;
	
	/**
	 * Creates a World from the specified directory.
	 * @param dir Root folder that contains all of the world files.
	 */
	public World(File dir) 
	{
		rootDir = dir;
		
		File levelDat = new File(dir.getPath() + "\\level.dat");
		
		File owFolder = new File(rootDir.getPath() + "\\region");
		File netherFolder = new File(rootDir.getPath() + "\\DIM-1\\region");
		File endFolder = new File(rootDir.getPath() + "\\DIM1\\region");
		
		File netherCheck = new File(rootDir.getPath() + "\\DIM-1");
		File endCheck = new File(rootDir.getPath() + "\\DIM1");
		
		//Loads the Level.dat datatag
		try
		{
			CompoundTag root;
			root = NbtIo.readCompressed(new FileInputStream(levelDat));
			dataTag = root.getCompound("Data"); 
		}
		catch (Exception e)
		{
			System.out.println("Level.dat does not exist or is corrupted.");
			e.printStackTrace();
		}
		
		overworld = new Dimension(owFolder);
		
		if (Dimension.validateGen(netherCheck))
			nether = new Dimension(netherFolder);
		else if (Himeji.SHOW_ALL_EVENTS)
			System.out.println("Nether has not been generated.");
		
		if (Dimension.validateGen(endCheck))
			theEnd = new Dimension(endFolder);
		else if (Himeji.SHOW_ALL_EVENTS)
			System.out.println("End has not been generated.");
	}
	
	/**
	 * Checks a folder to validate that it is a Minecraft world.
	 * Immediately returns false if the File path doesn't lead to
	 * a folder.
	 * @param dir Root directory of the world folder
	 * @return    true if the folder is successfully validated
	 */
	public static boolean validateDir(File dir)
	{
		boolean result = false;
		File[] worldFolders;
		
		if (dir.isDirectory())
		{
			worldFolders = dir.listFiles(new FilenameFilter()
				{
					@Override
					public boolean accept(File current, String name)
					{
						return new File(current, name).isDirectory();
					}
				});
			
			boolean hasRegion = false;
			boolean hasPlayerdata = false;
			
			for (File f : worldFolders)
			{
				switch(f.getName())
				{
					case "region":
						hasRegion = true;
						break;
					case "playerdata":
						hasPlayerdata = true;
						break;
				}
			}
			
			if (hasRegion && hasPlayerdata)
				result = true;
		}
		
		return result;
	}

	/**
	 * Creates the BufferedImage for all selected dimensions.
	 */
	public void createRenderGrids()
	{
		overworld.createRenderGrid();
		overworld.drawBlocksToBuffer(Himeji.getStartY());
		//overworld.drawEntitiesToBuffer(255);
	}

	/**
	 * Saves the image for a dimension to file.
	 */
	public void renderWorld()
	{
		overworld.render();
		//MapImage.renderDim(overworld);
	}
}
