import com.mojang.nbt.ByteArrayTag;
import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.IntArrayTag;
import com.mojang.nbt.ListTag;
import com.mojang.nbt.Tag;

public class NewChunk extends Chunk
{
	public static final int CHUNK_SIZE = 16;
	
	private CompoundTag levelTag;
	private int worldHeight;
	private String[][][] blocks;
	private int[][] biome;
	private Section[] sections;
	
	public NewChunk(CompoundTag baseTag) 
	{
		super();
		
		levelTag = baseTag.getCompound("Level");
		
		ListTag<? extends Tag> sectionTags = levelTag.getList("Sections");
		
		if (sectionTags.size() == 0)
			return;
		
		worldHeight = 256;//((int)((CompoundTag)(sectionTags.get(sectionTags.size() - 1))).getByte("Y") + 1) 
				//* CHUNK_SIZE;
		
		if (worldHeight <= 0)
			return;
		
		sections = new Section[sectionTags.size() - 1];
		blocks = new String[CHUNK_SIZE][worldHeight][CHUNK_SIZE];
		biome = new int[CHUNK_SIZE][CHUNK_SIZE];
		
		for(int x = 0; x < CHUNK_SIZE; x++) 
		{
			for(int z = 0; z < CHUNK_SIZE; z++) 
			{
				for(int y = 0; y < worldHeight; y++) 
				{
					blocks[x][y][z] = "minecraft:air";
				}
				biome[x][z] = 0;
			}
		}
		
		//boolean calcLight = levelTag.getBoolean("LightPopulated"); 
		
		for(int i = 0; i < sections.length; i++) 
		{
			sections[i] = new Section((CompoundTag)sectionTags.get(i));
			String[][][] sectionBlocks = sections[i].getBlocks();
			int sectionY = (sections[i].getSectionTag().getByte("Y")) * 16;
			
			if (sectionBlocks == null)
				continue;
			
			for(int x = 0; x < CHUNK_SIZE; x++) 
			{
				for(int y = 0; y < CHUNK_SIZE; y++) 
				{
					for(int z = 0; z < CHUNK_SIZE; z++) 
					{
						blocks[x][y + sectionY][z] = sectionBlocks[x][y][z];
					}
				}
			}
		}
		
		/*
		Tag biomeTag = levelTag.get("Biomes");
		
		if (biomeTag instanceof IntArrayTag)
		{
			int[] biomeData = levelTag.getIntArray("Biomes");
			for(int x = 0; x < CHUNK_SIZE; x++) 
			{
				for(int z = 0; z < CHUNK_SIZE; z++) 
				{
					biome[x][z] = biomeData[getIndex(x, z)];
				}
			}
		}
		else if (biomeTag instanceof ByteArrayTag)
		{
			byte[] biomeData = levelTag.getByteArray("Biomes");
			for(int x = 0; x < CHUNK_SIZE; x++) 
			{
				for(int z = 0; z < CHUNK_SIZE; z++) 
				{
					biome[x][z] = getPositiveByte(biomeData[getIndex(x, z)]);
				}
			}
		}
		*/
	}

	public int getX() 
	{
		return levelTag.getInt("xPos");
	}
	
	public int getZ() 
	{
		return levelTag.getInt("zPos");
	}
	
	public String[][] getTopStringBlocks()
	{
		String[][] result = new String[CHUNK_SIZE][CHUNK_SIZE];
		
		for (int x = 0; x < CHUNK_SIZE; x++)
		{
			for (int z = 0; z < CHUNK_SIZE; z++)
			{
				result[x][z] = getStringBlock(x, z);
			}
		}
		
		return result;
	}
	
	@Override
	public int[][][] getTopColors()
	{
		int[][][] result = new int[CHUNK_SIZE][CHUNK_SIZE][2];
		
		if (blocks == null)
			return null;
		
		for (int x = 0; x < CHUNK_SIZE; x++)
		{
			for (int z = 0; z < CHUNK_SIZE; z++)
			{
				int y = getTopBlockY(x, z);
				int color = Block.getBlockColor(blocks[x][y][z]);
				result[x][z][0] = color;
				result[x][z][1] = y;
			}
		}
		
		return result;
	}
	
	public String getStringBlock(int x, int z) 
	{
		String blockName;
		int[] idMeta;
		// starting at the top of the map and going down
		for(int y = worldHeight - 1; y > 0; y--) 
		{
			blockName = blocks[x][y][z];
			idMeta = Block.getIdMeta(blockName);
			if (idMeta != null)
			{
				if (Block.isBlockVisible(idMeta[0]))
				{
					return blockName;
				}
			}
			else
			{
				return "minecraft:air";
			}
			
		}
		return "minecraft:air";
	}
	
	@Override
	public int getTopBlockY(int x, int z)
	{
		for(int y = worldHeight - 1; y > 0; y--) 
		{
			String blockName = blocks[x][y][z];
			int[] idMeta = Block.getIdMeta(blockName);
			if (idMeta != null)
			{
				if (Block.isBlockVisible(idMeta[0]))
				{
					return y;
				}
			}
			else
			{
				return 0;
			}
			
		}
		return 0;
	}
}
