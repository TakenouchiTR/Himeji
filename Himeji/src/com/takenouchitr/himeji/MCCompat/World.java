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
			
			for (File f : worldFolders)
			{
				switch(f.getName())
				{
					case "region":
						hasRegion = true;
						break;
				}
			}
			
			if (hasRegion)
				result = true;
		}
		
		return result;
	}

}
