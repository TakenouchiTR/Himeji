//Date:           3/13/2020
//Me:             Shawn Carter

package com.github.takenouchitr;
import java.io.*;

public class World 
{
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

}
