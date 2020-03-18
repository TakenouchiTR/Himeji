//Program Name:   Region.java
//Date:           3/14/2020
//Programmer:     Shawn Carter
//Description:    This class handles parsing region .mca files.

import java.io.*;
import com.mojang.nbt.*;
import net.minecraft.world.level.chunk.storage.RegionFile;

public class Region 
{
	RegionFile file;
	
	/**
	 * Creates a Region from a specified file
	 * @param file r.x.z.mca file to read region data from.
	 */
	public Region(File file) 
	{
		if(!file.exists()) 
		{
			System.out.println("File does not exist");
		}
		try 
		{
			this.file = new RegionFile(file);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the chunk data from a chunk in the region. Returns null if the 
	 * chunk has not been loaded.
	 * @param x             X coord of the chunk, relative to the region
	 * @param z             Z coord of the chunk, relative to the region
	 * @return
	 * @throws IOException
	 */
	public CompoundTag getChunk(int x, int z) throws IOException 
	{
		CompoundTag result = null;
		
		if(file.hasChunk(x, z)) 
		{
			DataInputStream dis = file.getChunkDataInputStream(x, z);
				if (dis != null)
					result = NbtIo.read(dis);
		}
		
		return result;
	}
	
	/**
	 * Checks if the region contains a generated chunk at a given coordinate.
	 * @param x X coord, relative to the chunk
	 * @param z Z coord, relative to the chunk
	 * @return  true if the chunk has been generated, otherwise false
	 */
	public boolean hasChunk(int x, int z) 
	{
		return file.hasChunk(x, z);
	}
}
