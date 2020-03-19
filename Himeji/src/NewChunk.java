import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import com.mojang.nbt.Tag;

public class NewChunk extends Chunk
{
	public static final int CHUNK_SIZE = 16;
	
	private CompoundTag levelTag;
	private int worldHeight;
	private String[][][] blocks;
	private int[][][] biome;
	private int biomeHeight;
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
		
		for(int x = 0; x < CHUNK_SIZE; x++) 
		{
			for(int z = 0; z < CHUNK_SIZE; z++) 
			{
				for(int y = 0; y < worldHeight; y++) 
				{
					blocks[x][y][z] = "minecraft:air";
				}
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
		
		int dataLoc = 0;
		int[] biomeData = levelTag.getIntArray("Biomes");
			
		if (biomeData.length == 1024)
		{
			biome = new int[CHUNK_SIZE / 4][256 / 4][CHUNK_SIZE / 4];
			biomeHeight = 4;
			
			for (int y = 0; y < 256 / 4; y++)
			{
				for(int z = 0; z < CHUNK_SIZE / 4; z++)
				{
					for(int x = 0; x < CHUNK_SIZE / 4; x++) 
					{	
						biome[x][y][z] = biomeData[dataLoc];
						dataLoc++;
					}
				}
			}
			
		}
		else
		{
			biome = new int[CHUNK_SIZE][1][CHUNK_SIZE];
			biomeHeight = 256;
			
			for(int z = 0; z < CHUNK_SIZE; z++) 
			{
				for(int x = 0; x < CHUNK_SIZE; x++) 
				{
					biome[x][0][z] = biomeData[dataLoc];
					dataLoc++;
				}
			}
		}
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
				int dy = getTopBlockYIgnoreWater(x, z);
				int biomeWidth = CHUNK_SIZE / biome.length;
				int color = Block.getBlockColor(blocks[x][dy][z], 
						biome[x / biomeWidth][dy / biomeHeight][z / biomeWidth]);
				
				//Adds a blue effect to blocks under water
				if (y != dy)
				{
					int waterColor = Block.getBlockColor(8, 0, 
							biome[x / biomeWidth][y / biomeHeight][z / biomeWidth]);
					int a = 0;
					int r = 0;
					int g = 0;
					int b = 0;
					
					a = 0xFF000000;
					
					r += ((color & 0x00FF0000) >>> 16);
					r += ((waterColor & 0x00FF0000) >>> 16) * 3;
					g += ((color & 0x0000FF00) >>> 8);
					g += ((waterColor & 0x0000FF00) >>> 8) * 3;
					b += (color & 0x000000FF);
					b += (waterColor & 0x000000FF) * 3;
					
					r /= 4;
					g /= 4;
					b /= 4;
					
					r <<= 16;
					g <<= 8;
					
					color = a | r | g | b;
				}
				
				result[x][z][0] = color;
				result[x][z][1] = dy;
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
	
	@Override
	public int getTopBlockYIgnoreWater(int x, int z)
	{
		for(int y = worldHeight - 1; y > 0; y--) 
		{
			String blockName = blocks[x][y][z];
			int[] idMeta = Block.getIdMeta(blockName);
			if (idMeta != null)
			{
				if (Block.isBlockVisible(idMeta[0]) && idMeta[0] != 8 && idMeta[0] != 9)
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
