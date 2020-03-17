///////////////////////////////////////////////////////////////////////////////////

//////////This file was only for testing. It serves no purpose/////////////////////

///////////////////////////////////////////////////////////////////////////////////
import java.io.File;

import com.mojang.nbt.CompoundTag;

public class NewRegion extends Region
{
	public static final int REGION_SIZE = 32;
	
	private int blockHeight;
	private int blockWidth;
	private int chunkHeight;
	private int chunkWidth;
	private int chunkXOffset;
	private int chunkZOffset;
	private boolean[][] regionRenderFlags;
	private boolean[][] chunkRenderFlags;
	private String[][] renderGrid;
	private File directory;
	
	public NewRegion(File dir)
	{
		super(dir);
	}
	
	public void createRenderGrid()
	{
		File[] regions;
		renderGrid = new String[blockWidth][blockHeight];
		chunkRenderFlags = new boolean[chunkWidth][chunkHeight];
		regionRenderFlags = new boolean[chunkWidth / 32][chunkHeight / 32];
		
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
						
						chunkRenderFlags[gridX / 16][gridZ / 16] = true;
						//String[][] chunkMap = chunk.getTopBlocks();
						
						for (int blockX = 0; blockX < Chunk.CHUNK_SIZE; blockX++)
						{
							for (int blockZ = 0; blockZ < Chunk.CHUNK_SIZE; blockZ++)
							{
								int xPos = gridX + blockX;
								int zPos = gridZ + blockZ;
								//renderGrid[xPos][zPos] = chunkMap[blockX][blockZ];
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
}
