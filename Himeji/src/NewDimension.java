///////////////////////////////////////////////////////////////////////////////////

//////////This file was only for testing. It serves no purpose/////////////////////

///////////////////////////////////////////////////////////////////////////////////
import java.io.File;

import com.mojang.nbt.CompoundTag;

public class NewDimension extends Dimension 
{
	String[][] stringRenderGrid;
	
	public NewDimension(File dir)
	{
		super(dir);
	}
	
	public void createRenderGrid()
	{
		File[] regions;
		stringRenderGrid = new String[blockWidth][blockHeight];
		
		regions = directory.listFiles();
		
		for (File regionFile : regions)
		{
			if (Himeji.SHOW_ALL_EVENTS)
				System.out.println("Reading " + regionFile.getName());
			
			NewRegion region = new NewRegion(regionFile);
			
			for (int chunkX = 0; chunkX < REGION_SIZE; chunkX++)
			{
				for (int chunkZ = 0; chunkZ < REGION_SIZE; chunkZ++)
				{
					if (!region.hasChunk(chunkX, chunkZ))
						continue;
					try
					{
						CompoundTag chunkTag = region.getChunk(chunkX, chunkZ);
						NewChunk chunk = new NewChunk(chunkTag);
						
						int gridX = (chunk.getX() + chunkXOffset * REGION_SIZE) * Chunk.CHUNK_SIZE;
						int gridZ = (chunk.getZ() + chunkZOffset * REGION_SIZE) * Chunk.CHUNK_SIZE;
						
						//chunkRenderFlags[gridX / 16][gridZ / 16] = true;
						int[][] chunkMap = chunk.getTopColors();
						
						for (int blockX = 0; blockX < Chunk.CHUNK_SIZE; blockX++)
						{
							for (int blockZ = 0; blockZ < Chunk.CHUNK_SIZE; blockZ++)
							{
								int xPos = gridX + blockX;
								int zPos = gridZ + blockZ;
								//stringRenderGrid[xPos][zPos] = chunkMap[blockX][blockZ];
							}
						}
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	public int getBlockColorAt(int x, int z)
	{
		return Block.getBlockColor(getStringBlockAt(x, z));
	}
	
	public String getStringBlockAt(int x, int z)
	{
		if (stringRenderGrid == null || x >= stringRenderGrid.length || z >= stringRenderGrid[0].length)
			return null;
		return stringRenderGrid[x][z];
	}
}
