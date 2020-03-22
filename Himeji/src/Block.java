//Program Name:   Block.java
//Date:           3/14/2020
//Programmer:     Shawn Carter
//Description:    This class represents a Block in Minecraft.

import java.util.Dictionary;
import java.util.Hashtable;

public class Block 
{
	public static final int MAX_BLOCK_ID = 297;
	
	private static boolean[] blockVisibility; 
	private static int[][] biomeFoliage;
	private static int[][] biomeGrass;
	private static int[][] biomeWater;
	private static int[][] blockColors;
	private static int[] foliageColors;
	private static int[] grassColors;
	private static int[] waterColors;
	private static Dictionary<String, int[]> blockDict;
	
	private int blockID;
	private int metadata;
	
	/**
	 * Sets the default values for which blocks will be rendered
	 */
	public static void setBlockVisibility()
	{
		blockVisibility = new boolean[MAX_BLOCK_ID];
		
		//Fills the visibility array to true by default
		for (int i = 0; i < blockVisibility.length; i++)
			blockVisibility[i] = true;
		
		//Sets invisible blocks to false
		blockVisibility[0] = false;   //Air
		blockVisibility[6] = false;   //Saplings
		blockVisibility[31] = false;  //Grasses
		blockVisibility[32] = false;  //Dead Bush
		blockVisibility[36] = false;  //Moving piston head
		blockVisibility[38] = false;  //Flowers
		blockVisibility[39] = false;  //Brown mushroom
		blockVisibility[40] = false;  //Red mushroom
		blockVisibility[50] = false;  //Torch
		blockVisibility[63] = false;  //Standing sign
		blockVisibility[68] = false;  //Wall sign
		blockVisibility[69] = false;  //Lever
		blockVisibility[75] = false;  //Redstone Torch (off)
		blockVisibility[76] = false;  //Redstone Torch (on)
		blockVisibility[77] = false;  //Stone button
		blockVisibility[106] = false; //Vines
		blockVisibility[131] = false; //Tripwire Hook
		blockVisibility[132] = false; //Tripwire
		blockVisibility[143] = false; //Wood button
		blockVisibility[144] = false; //Heads
		blockVisibility[166] = false; //Barrier
		blockVisibility[175] = false; //Double plant
		blockVisibility[176] = false; //Standing banner
		blockVisibility[177] = false; //Wall banner
		blockVisibility[198] = false; //End rod
		blockVisibility[255] = false; //Structure Block
	}
	
	/**
	 * Sets the default values for biomeFoliage 
	 */
	public static void setBiomeFoliage() 
	{
		biomeFoliage = new int[7][2];
		
		biomeFoliage[0][0] = 18;   //Leaves
		biomeFoliage[0][1] = 0;
		biomeFoliage[1][0] = 18;
		biomeFoliage[1][1] = 1;
		biomeFoliage[2][0] = 18;
		biomeFoliage[2][1] = 2;
		biomeFoliage[3][0] = 18;
		biomeFoliage[3][1] = 3;
		biomeFoliage[4][0] = 161;
		biomeFoliage[4][1] = 0;
		biomeFoliage[5][0] = 161;
		biomeFoliage[5][1] = 1;
		biomeFoliage[6][0] = 106;  //Vines
		biomeFoliage[6][1] = 0;
	}
	
	/**
	 * Sets the default values for biomeGrass.
	 */
	public static void setBiomeGrass()
	{
		biomeGrass = new int[4][2];
		
		biomeGrass[0][0] = 2;    //Grass Block
		biomeGrass[0][1] = 0;
		biomeGrass[1][0] = 31;   //Grass
		biomeGrass[1][1] = 0;
		biomeGrass[2][0] = 31;   //Fern
		biomeGrass[2][1] = 1;
		biomeGrass[3][0] = 175;  //Tall Grass
		biomeGrass[3][1] = 3;
	}
	
	/**
	 * Sets the default values for biomeWater.
	 */
	public static void setBiomeWater()
	{
		biomeWater = new int[3][2];
		
		biomeWater[0][0] = 8;
		biomeWater[1][0] = 9;
		biomeWater[2][0] = 280;
	}
	
	/**
	 * Sets the default values for biomeColors.
	 */
	public static void setBiomeColors()
	{
		int length = 173;
		foliageColors = new int[length];
		grassColors = new int[length];
		waterColors = new int[length];
		
		foliageColors[Biome.OCEAN.id] = 0xFF5AB027;
		grassColors[Biome.OCEAN.id] = 0xFF73C252;
		waterColors[Biome.OCEAN.id] = 0xFF1F8FBE;
		foliageColors[Biome.PLAINS.id] = 0xFF1ABF00;
		grassColors[Biome.PLAINS.id] = 0xFF47CD33;
		waterColors[Biome.PLAINS.id] = 0xFF36599C;
		foliageColors[Biome.DESERT.id] = 0xFFAEA42A;
		grassColors[Biome.DESERT.id] = 0xFFBFB755;
		waterColors[Biome.DESERT.id] = 0xFF36599C;
		foliageColors[Biome.MOUNTAINS.id] = 0xFF659B78;
		grassColors[Biome.MOUNTAINS.id] = 0xFF73C05F;
		waterColors[Biome.MOUNTAINS.id] = 0xFF36599C;
		foliageColors[Biome.FOREST.id] = 0xFF67AE2C;
		grassColors[Biome.FOREST.id] = 0xFF78C152;
		waterColors[Biome.FOREST.id] = 0xFF36599C;
		foliageColors[Biome.TAIGA.id] = 0xFF6B9793;
		grassColors[Biome.TAIGA.id] = 0xFF80B497;
		waterColors[Biome.TAIGA.id] = 0xFF36599C;
		foliageColors[Biome.SWAMP.id] = 0xFF222620;
		grassColors[Biome.SWAMP.id] = 0xFF222620;
		waterColors[Biome.SWAMP.id] = 0xFF77804F;
		foliageColors[Biome.RIVER.id] = 0xFF5AB027;
		grassColors[Biome.RIVER.id] = 0xFF73C252;
		waterColors[Biome.RIVER.id] = 0xFF36599C;
		foliageColors[Biome.NETHER_WASTES.id] = 0xFFAEA42A;
		grassColors[Biome.NETHER_WASTES.id] = 0xFFBFB755;
		waterColors[Biome.NETHER_WASTES.id] = 0xFF36599C;
		foliageColors[Biome.THE_END.id] = 0xFF8DAA22;
		grassColors[Biome.THE_END.id] = 0xFFA2BD4E;
		waterColors[Biome.THE_END.id] = 0xFF36599C;
		foliageColors[Biome.FROZEN_OCEAN.id] = 0xFF659B78;
		grassColors[Biome.FROZEN_OCEAN.id] = 0xFF73C05F;
		waterColors[Biome.FROZEN_OCEAN.id] = 0xFF182B80;
		foliageColors[Biome.FROZEN_RIVER.id] = 0xFF659B78;
		grassColors[Biome.FROZEN_RIVER.id] = 0xFF73C05F;
		waterColors[Biome.FROZEN_RIVER.id] = 0xFF36599C;
		foliageColors[Biome.SNOWY_TUNDRA.id] = 0xFF659B78;
		grassColors[Biome.SNOWY_TUNDRA.id] = 0xFF73C05F;
		waterColors[Biome.SNOWY_TUNDRA.id] = 0xFF36599C;
		foliageColors[Biome.SNOWY_MOUNTAINS.id] = 0xFF659B78;
		grassColors[Biome.SNOWY_MOUNTAINS.id] = 0xFF73C05F;
		waterColors[Biome.SNOWY_MOUNTAINS.id] = 0xFF36599C;
		foliageColors[Biome.MUSHROOM_FIELDS.id] = 0xFF2EBB06;
		grassColors[Biome.MUSHROOM_FIELDS.id] = 0xFF4CCC35;
		waterColors[Biome.MUSHROOM_FIELDS.id] = 0xFF36599C;
		foliageColors[Biome.MUSHROOM_FIELD_SHORE .id] = 0xFF2EBB06;
		grassColors[Biome.MUSHROOM_FIELD_SHORE .id] = 0xFF4CCC35;
		waterColors[Biome.MUSHROOM_FIELD_SHORE .id] = 0xFF36599C;
		foliageColors[Biome.BEACH.id] = 0xFF5AB027;
		grassColors[Biome.BEACH.id] = 0xFF73C252;
		waterColors[Biome.BEACH.id] = 0xFF36599C;
		foliageColors[Biome.DESERT_HILLS.id] = 0xFFAEA42A;
		grassColors[Biome.DESERT_HILLS.id] = 0xFFBFB755;
		waterColors[Biome.DESERT_HILLS.id] = 0xFF36599C;
		foliageColors[Biome.WOODED_HILLS.id] = 0xFF67AE2C;
		grassColors[Biome.WOODED_HILLS.id] = 0xFF78C152;
		waterColors[Biome.WOODED_HILLS.id] = 0xFF36599C;
		foliageColors[Biome.TAIGA_HILLS.id] = 0xFF6B9793;
		grassColors[Biome.TAIGA_HILLS.id] = 0xFF80B497;
		waterColors[Biome.TAIGA_HILLS.id] = 0xFF36599C;
		foliageColors[Biome.MOUNTAIN_EDGE.id] = 0xFF659B78;
		grassColors[Biome.MOUNTAIN_EDGE.id] = 0xFF73C05F;
		waterColors[Biome.MOUNTAIN_EDGE.id] = 0xFF36599C;
		foliageColors[Biome.JUNGLE.id] = 0xFF1ABF00;
		grassColors[Biome.JUNGLE.id] = 0xFF44B70C;
		waterColors[Biome.JUNGLE.id] = 0xFF36599C;
		foliageColors[Biome.JUNGLE_HILLS.id] = 0xFF1ABF00;
		grassColors[Biome.JUNGLE_HILLS.id] = 0xFF44B70C;
		waterColors[Biome.JUNGLE_HILLS.id] = 0xFF36599C;
		foliageColors[Biome.JUNGLE_EDGE.id] = 0xFF1ABF00;
		grassColors[Biome.JUNGLE_EDGE.id] = 0xFF44B70C;
		waterColors[Biome.JUNGLE_EDGE.id] = 0xFF36599C;
		foliageColors[Biome.DEEP_OCEAN.id] = 0xFF5AB027;
		grassColors[Biome.DEEP_OCEAN.id] = 0xFF73C252;
		waterColors[Biome.DEEP_OCEAN.id] = 0xFF1F8FBE;
		foliageColors[Biome.STONE_SHORE.id] = 0xFF659B78;
		grassColors[Biome.STONE_SHORE.id] = 0xFF73C05F;
		waterColors[Biome.STONE_SHORE.id] = 0xFF36599C;
		foliageColors[Biome.SNOWY_BEACH.id] = 0xFF659B78;
		grassColors[Biome.SNOWY_BEACH.id] = 0xFF73C05F;
		waterColors[Biome.SNOWY_BEACH.id] = 0xFF36599C;
		foliageColors[Biome.BIRCH_FOREST.id] = 0xFF70AF19;
		grassColors[Biome.BIRCH_FOREST.id] = 0xFF89C146;
		waterColors[Biome.BIRCH_FOREST.id] = 0xFF36599C;
		foliageColors[Biome.BIRCH_FOREST_HILLS.id] = 0xFF70AF19;
		grassColors[Biome.BIRCH_FOREST_HILLS.id] = 0xFF89C146;
		waterColors[Biome.BIRCH_FOREST_HILLS.id] = 0xFF36599C;
		foliageColors[Biome.DARK_FOREST.id] = 0xFF28340A;
		grassColors[Biome.DARK_FOREST.id] = 0xFF28340A;
		waterColors[Biome.DARK_FOREST.id] = 0xFF36599C;
		foliageColors[Biome.SNOWY_TAIGA.id] = 0xFF6B9793;
		grassColors[Biome.SNOWY_TAIGA.id] = 0xFF80B497;
		waterColors[Biome.SNOWY_TAIGA.id] = 0xFF36599C;
		foliageColors[Biome.SNOWY_TAIGA_HILLS.id] = 0xFF6B9793;
		grassColors[Biome.SNOWY_TAIGA_HILLS.id] = 0xFF80B497;
		waterColors[Biome.SNOWY_TAIGA_HILLS.id] = 0xFF36599C;
		foliageColors[Biome.GIANT_TREE_TAIGA.id] = 0xFF6B9793;
		grassColors[Biome.GIANT_TREE_TAIGA.id] = 0xFF80B497;
		waterColors[Biome.GIANT_TREE_TAIGA.id] = 0xFF36599C;
		foliageColors[Biome.GIANT_TREE_TAIGA_HILLS.id] = 0xFF6B9793;
		grassColors[Biome.GIANT_TREE_TAIGA_HILLS.id] = 0xFF80B497;
		waterColors[Biome.GIANT_TREE_TAIGA_HILLS.id] = 0xFF36599C;
		foliageColors[Biome.WOODED_MOUNTAINS.id] = 0xFF659B78;
		grassColors[Biome.WOODED_MOUNTAINS.id] = 0xFF73C05F;
		waterColors[Biome.WOODED_MOUNTAINS.id] = 0xFF36599C;
		foliageColors[Biome.SAVANNA.id] = 0xFFAEA42A;
		grassColors[Biome.SAVANNA.id] = 0xFFBFB755;
		waterColors[Biome.SAVANNA.id] = 0xFF36599C;
		foliageColors[Biome.SAVANNA_PLATEAU.id] = 0xFFAEA42A;
		grassColors[Biome.SAVANNA_PLATEAU.id] = 0xFFBFB755;
		waterColors[Biome.SAVANNA_PLATEAU.id] = 0xFF36599C;
		foliageColors[Biome.BADLANDS.id] = 0xFF9E814D;
		grassColors[Biome.BADLANDS.id] = 0xFF90814D;
		waterColors[Biome.BADLANDS.id] = 0xFF36599C;
		foliageColors[Biome.WOODED_BADLANDS_PLATEAU.id] = 0xFF9E814D;
		grassColors[Biome.WOODED_BADLANDS_PLATEAU.id] = 0xFF90814D;
		waterColors[Biome.WOODED_BADLANDS_PLATEAU.id] = 0xFF36599C;
		foliageColors[Biome.BADLANDS_PLATEAU.id] = 0xFF9E814D;
		grassColors[Biome.BADLANDS_PLATEAU.id] = 0xFF90814D;
		waterColors[Biome.BADLANDS_PLATEAU.id] = 0xFF36599C;
		foliageColors[Biome.SMALL_END_ISLANDS.id] = 0xFF8DAA22;
		grassColors[Biome.SMALL_END_ISLANDS.id] = 0xFFA2BD4E;
		waterColors[Biome.SMALL_END_ISLANDS.id] = 0xFF36599C;
		foliageColors[Biome.END_MIDLANDS.id] = 0xFF8DAA22;
		grassColors[Biome.END_MIDLANDS.id] = 0xFFA2BD4E;
		waterColors[Biome.END_MIDLANDS.id] = 0xFF36599C;
		foliageColors[Biome.END_HIGHLANDS.id] = 0xFF8DAA22;
		grassColors[Biome.END_HIGHLANDS.id] = 0xFFA2BD4E;
		waterColors[Biome.END_HIGHLANDS.id] = 0xFF36599C;
		foliageColors[Biome.END_BARRENS.id] = 0xFF8DAA22;
		grassColors[Biome.END_BARRENS.id] = 0xFFA2BD4E;
		waterColors[Biome.END_BARRENS.id] = 0xFF36599C;
		foliageColors[Biome.WARM_OCEAN.id] = 0xFF4EB513;
		grassColors[Biome.WARM_OCEAN.id] = 0xFF6BC63E;
		waterColors[Biome.WARM_OCEAN.id] = 0xFF1F8FBE;
		foliageColors[Biome.LUKEWARM_OCEAN.id] = 0xFF5AB027;
		grassColors[Biome.LUKEWARM_OCEAN.id] = 0xFF73C252;
		waterColors[Biome.LUKEWARM_OCEAN.id] = 0xFF1F8FBE;
		foliageColors[Biome.COLD_OCEAN.id] = 0xFF6B9793;
		grassColors[Biome.COLD_OCEAN.id] = 0xFF80B497;
		waterColors[Biome.COLD_OCEAN.id] = 0xFF182B80;
		foliageColors[Biome.DEEP_WARM_OCEAN.id] = 0xFF4EB513;
		grassColors[Biome.DEEP_WARM_OCEAN.id] = 0xFF6BC63E;
		waterColors[Biome.DEEP_WARM_OCEAN.id] = 0xFF1F8FBE;
		foliageColors[Biome.DEEP_LUKEWARM_OCEAN.id] = 0xFF5AB027;
		grassColors[Biome.DEEP_LUKEWARM_OCEAN.id] = 0xFF73C252;
		waterColors[Biome.DEEP_LUKEWARM_OCEAN.id] = 0xFF1F8FBE;
		foliageColors[Biome.DEEP_COLD_OCEAN.id] = 0xFF6B9793;
		grassColors[Biome.DEEP_COLD_OCEAN.id] = 0xFF80B497;
		waterColors[Biome.DEEP_COLD_OCEAN.id] = 0xFF182B80;
		foliageColors[Biome.DEEP_FROZON_OCEAN.id] = 0xFF6B9793;
		grassColors[Biome.DEEP_FROZON_OCEAN.id] = 0xFF80B497;
		waterColors[Biome.DEEP_FROZON_OCEAN.id] = 0xFF182B80;
		foliageColors[Biome.THE_VOID.id] = 0xFF9CA825;
		grassColors[Biome.THE_VOID.id] = 0xFFAFBA50;
		waterColors[Biome.THE_VOID.id] = 0xFF36599C;
		foliageColors[Biome.SUNFLOWER_PLAINS.id] = 0xFF1ABF00;
		grassColors[Biome.SUNFLOWER_PLAINS.id] = 0xFF47CD33;
		waterColors[Biome.SUNFLOWER_PLAINS.id] = 0xFF36599C;
		foliageColors[Biome.DESERT_LAKES.id] = 0xFFAEA42A;
		grassColors[Biome.DESERT_LAKES.id] = 0xFFBFB755;
		waterColors[Biome.DESERT_LAKES.id] = 0xFF36599C;
		foliageColors[Biome.GRAVELLY_MOUNTAINS.id] = 0xFF659B78;
		grassColors[Biome.GRAVELLY_MOUNTAINS.id] = 0xFF73C05F;
		waterColors[Biome.GRAVELLY_MOUNTAINS.id] = 0xFF36599C;
		foliageColors[Biome.FLOWER_FOREST.id] = 0xFF67AE2C;
		grassColors[Biome.FLOWER_FOREST.id] = 0xFF78C152;
		waterColors[Biome.FLOWER_FOREST.id] = 0xFF36599C;
		foliageColors[Biome.TAIGA_MOUNTAINS.id] = 0xFF6B9793;
		grassColors[Biome.TAIGA_MOUNTAINS.id] = 0xFF80B497;
		waterColors[Biome.TAIGA_MOUNTAINS.id] = 0xFF36599C;
		foliageColors[Biome.SWAMP_HILLS.id] = 0xFF222620;
		grassColors[Biome.SWAMP_HILLS.id] = 0xFF222620;
		waterColors[Biome.SWAMP_HILLS.id] = 0xFF77804F;
		foliageColors[Biome.ICE_SPIKES.id] = 0xFF6B9793;
		grassColors[Biome.ICE_SPIKES.id] = 0xFF80B497;
		waterColors[Biome.ICE_SPIKES.id] = 0xFF36599C;
		foliageColors[Biome.MODIFIED_JUNGLE.id] = 0xFF1ABF00;
		grassColors[Biome.MODIFIED_JUNGLE.id] = 0xFF44B70C;
		waterColors[Biome.MODIFIED_JUNGLE.id] = 0xFF36599C;
		foliageColors[Biome.MODIFIED_JUNGLE_EDGE.id] = 0xFF1ABF00;
		grassColors[Biome.MODIFIED_JUNGLE_EDGE.id] = 0xFF44B70C;
		waterColors[Biome.MODIFIED_JUNGLE_EDGE.id] = 0xFF36599C;
		foliageColors[Biome.TALL_BIRCH_FOREST.id] = 0xFF70AF19;
		grassColors[Biome.TALL_BIRCH_FOREST.id] = 0xFF89C146;
		waterColors[Biome.TALL_BIRCH_FOREST.id] = 0xFF36599C;
		foliageColors[Biome.TALL_BIRCH_HILLS.id] = 0xFF70AF19;
		grassColors[Biome.TALL_BIRCH_HILLS.id] = 0xFF89C146;
		waterColors[Biome.TALL_BIRCH_HILLS.id] = 0xFF36599C;
		foliageColors[Biome.DARK_FOREST_HILLS.id] = 0xFF28340A;
		grassColors[Biome.DARK_FOREST_HILLS.id] = 0xFF28340A;
		waterColors[Biome.DARK_FOREST_HILLS.id] = 0xFF36599C;
		foliageColors[Biome.SNOWY_TAIGA_MOUNTAINS.id] = 0xFF6B9793;
		grassColors[Biome.SNOWY_TAIGA_MOUNTAINS.id] = 0xFF80B497;
		waterColors[Biome.SNOWY_TAIGA_MOUNTAINS.id] = 0xFF36599C;
		foliageColors[Biome.GIANT_SPRUCE_TAIGA.id] = 0xFF6B9793;
		grassColors[Biome.GIANT_SPRUCE_TAIGA.id] = 0xFF80B497;
		waterColors[Biome.GIANT_SPRUCE_TAIGA.id] = 0xFF36599C;
		foliageColors[Biome.GIANT_SPRUCE_TAIGA_HILLS.id] = 0xFF6B9793;
		grassColors[Biome.GIANT_SPRUCE_TAIGA_HILLS.id] = 0xFF80B497;
		waterColors[Biome.GIANT_SPRUCE_TAIGA_HILLS.id] = 0xFF36599C;
		foliageColors[Biome.MODIFIED_GRAVELLY_MOUNTAINS.id] = 0xFF659B78;
		grassColors[Biome.MODIFIED_GRAVELLY_MOUNTAINS.id] = 0xFF73C05F;
		waterColors[Biome.MODIFIED_GRAVELLY_MOUNTAINS.id] = 0xFF36599C;
		foliageColors[Biome.SHATTERED_SAVANNA.id] = 0xFFAEA42A;
		grassColors[Biome.SHATTERED_SAVANNA.id] = 0xFFBFB755;
		waterColors[Biome.SHATTERED_SAVANNA.id] = 0xFF36599C;
		foliageColors[Biome.SHATTERED_SAVANNA_PLATEAU.id] = 0xFFAEA42A;
		grassColors[Biome.SHATTERED_SAVANNA_PLATEAU.id] = 0xFFBFB755;
		waterColors[Biome.SHATTERED_SAVANNA_PLATEAU.id] = 0xFF36599C;
		foliageColors[Biome.ERODED_BADLANDS.id] = 0xFF9E814D;
		grassColors[Biome.ERODED_BADLANDS.id] = 0xFF90814D;
		waterColors[Biome.ERODED_BADLANDS.id] = 0xFF36599C;
		foliageColors[Biome.MODIFIED_WOODED_BADLANDS_PLATEAU.id] = 0xFF9E814D;
		grassColors[Biome.MODIFIED_WOODED_BADLANDS_PLATEAU.id] = 0xFF90814D;
		waterColors[Biome.MODIFIED_WOODED_BADLANDS_PLATEAU.id] = 0xFF36599C;
		foliageColors[Biome.BAMBOO_JUNGLE.id] = 0xFF1ABF00;
		grassColors[Biome.BAMBOO_JUNGLE.id] = 0xFF44B70C;
		waterColors[Biome.BAMBOO_JUNGLE.id] = 0xFF36599C;
		foliageColors[Biome.BAMBOO_JUNGLE_HILLS.id] = 0xFF1ABF00;
		grassColors[Biome.BAMBOO_JUNGLE_HILLS.id] = 0xFF44B70C;
		waterColors[Biome.BAMBOO_JUNGLE_HILLS.id] = 0xFF36599C;
		foliageColors[Biome.SOUL_SAND_VALLEY.id] = 0xFFAEA42A;
		grassColors[Biome.SOUL_SAND_VALLEY.id] = 0xFFBFB755;
		waterColors[Biome.SOUL_SAND_VALLEY.id] = 0xFF36599C;
		foliageColors[Biome.CRIMSON_FOREST.id] = 0xFFAEA42A;
		grassColors[Biome.CRIMSON_FOREST.id] = 0xFFBFB755;
		waterColors[Biome.CRIMSON_FOREST.id] = 0xFF36599C;
		foliageColors[Biome.WARPED_FOREST.id] = 0xFFAEA42A;
		grassColors[Biome.WARPED_FOREST.id] = 0xFFBFB755;
		waterColors[Biome.WARPED_FOREST.id] = 0xFF36599C;
	}
	
	/**
	 * Sets the default colors for all blocks using the pre-flattening format.
	 */
	public static void setBlockColors()
	{
		blockColors = new int[MAX_BLOCK_ID][];
		
		blockColors[0] = new int[] {-16777216};      //Air
		blockColors[1] = new int[] {-7368817,        //Stone
									-6329512,        //Granite
									-3434366,        //P. Granite
									-4737095,        //Andesite
									-2894890,        //P. Andesite
									-10066330,       //Diorite
									-6645094,        //P. Diorite 
									-5197648};       //Smooth Stone
		blockColors[2] = new int[] {-13256686};      //Grass Block
		blockColors[3] = new int[] {-8825542,        //Dirt
									-10994648,       //Coarse Dirt
									-11912164};      //Podzol
		blockColors[4] = new int[] {-8947849};       //Cobblestone
		blockColors[5] = new int[] {-6323123,        //Oak Planks
									-8956107,        //Spruce Planks
									-8798333,        //Birch Planks
									-4815517,        //Jungle Planks
									-4693450,        //Acacia Planks
									-12640976};      //Dark Oak Planks
		blockColors[6] = new int[] {-11940827,       //Oak Sapling -------------------------
									-11940827,       //Spruce Sapling
									-11940827,       //Birch Sapling
									-11940827,       //Jungle Sapling
									-11940827,       //Acacia Sapling
									-11940827};      //Dark Oak Sapling
		blockColors[7] = new int[] {-13421773};      //Bedrock
		blockColors[8] = new int[] {-13810027};      //Flowing Water
		blockColors[9] = new int[] {-13810027};      //Still Water
		blockColors[10] = new int[] {-4309497};      //Flowing Lava
		blockColors[11] = new int[] {-4309497};      //Still Lava
		blockColors[12] = new int[] {-1342,          //Sand
									 -5285084};      //Red Sand
		blockColors[13] = new int[] {-6647924};      //Gravel
		blockColors[14] = new int[] {-201141};       //Gold Ore
		blockColors[15] = new int[] {-2576493};      //Iron Ore
		blockColors[16] = new int[] {-12632257};     //Coal Ore
		blockColors[17] = new int[] {-11715291,      //Oak Log
									 -14411766,      //Spruce Log
									 -5525845,       //Birch Log
									 -13293298};     //Jungle Log
		blockColors[18] = new int[] {-10248388,      //Oak Leaves -----------------------------
									 -10248388,      //Spruce Leaves
									 -10248388,      //Birch Leaves
									 -10248388};     //Jungle Leaves
		blockColors[19] = new int[] {-3026352,       //Sponge
									 -6909126};      //Wet Sponge
		blockColors[20] = new int[] {-4131330};      //Glass
		blockColors[21] = new int[] {-14919738};     //Lapis Ore
		blockColors[22] = new int[] {-14919738};     //Lapis Block
		blockColors[23] = new int[] {-9868951};      //Dispenser
		blockColors[24] = new int[] {-3028581,       //Sandstone
									 -3028581,       //Chiseled Sandstone
									 -3028581,       //Cut (old Smooth) Sandstone
									 -3028581};      //Smooth Sandstone
		blockColors[25] = new int[] {-6592949};      //Note Block
		blockColors[26] = new int[] {-6806752,       //Red Bed
				 					 -820454,        //Orange Bed
				 					 -3319610,       //Magenta Bed
				 					 -13524016,      //Light Blue Bed
				 					 -404678,        //Yellow Bed
				 					 -10047721,      //Lime Bed
				 					 -1144921,       //Pink Bed
				 					 -12040120,      //Gray Bed
				 					 -15369079,      //Light Gray Bed
				 					 -8902231,       //Cyan Bed
				 					 -13223524,      //Purple Bed
				 					 -10076382,      //Blue Bed
				 					 -11640035,      //Brown Bed
				 					 -11508196,      //Green Bed
				 					 -1,             //White Bed
				 					 -15921907};     //Black Bed
		blockColors[27] = new int[] {-2246612};      //Powered Rails
		blockColors[28] = new int[] {-10203350};     //Detector Rail
		blockColors[29] = new int[] {-9325465};      //Sticky Piston
		blockColors[30] = new int[] {-1};            //Cobweb
		blockColors[31] = new int[] {-13085135,      //Grass
									 -13402095};     //Fern
		blockColors[32] = new int[] {-7052248};      //Dead Bush
		blockColors[33] = new int[] {-11776948};     //Piston
		blockColors[34] = new int[] {-7572422};      //Piston Head
		blockColors[35] = new int[] {-1,             //White Wool
									 -820454,        //Orange Wool
									 -3319610,       //Magenta Wool
									 -13524016,      //Light Blue Wool
									 -404678,        //Yellow Wool
									 -10047721,      //Lime Wool
									 -1144921,       //Pink Wool
									 -12040120,      //Gray Wool
									 -15369079,      //Light Gray Wool
									 -8902231,       //Cyan Wool
									 -13223524,      //Purple Wool
									 -10076382,      //Blue Wool
									 -11640035,      //Brown Wool
									 -11508196,      //Green Wool
									 -6806752,       //Red Wool
									 -15921907};     //Black Wool
		blockColors[36] = new int[] {};              //???---------------------
		blockColors[37] = new int[] {-919294};       //Dandelion
		blockColors[38] = new int[] {-4586229,       //Poppy
				 					 -14045189,      //Blue Orchid
				 					 -4692611,       //Allium
				 					 -2564378,       //Azure Bluet
				 					 -2806265,       //Red Tulip
				 					 -2198753,       //Orange Tulip
				 					 -789517,        //White Tulip
				 					 -797453,        //Pink Tulip
				 					 -526345,        //Oxeye Daisy
				 					 0xFF392C25,     //Wither Rose
				 					 0xFFEFEFF1,     //Lily of the Valley
				 					 0xFF577AEA};    //Cornflower
		blockColors[39] = new int[] {-7246507};      //Brown Mushroom
		blockColors[40] = new int[] {-3924698};      //Red Mushroom
		blockColors[41] = new int[] {-131495};       //Gold Block
		blockColors[42] = new int[] {-1447447};      //Iron Block
		blockColors[43] = new int[] {-5197648,       //Double Smooth Stone Slab
									 -3028581,       //Double Sandstone Slab
									 -6323123,       //Double Oak Plank Slab (depreciated)
									 -8947849,       //Double Cobblestone Slab
									 -5086386,       //Double Brick Slab
									 -9539986,       //Double Stone Brick Slab
									 -12706268,      //Double Nether Brick Slab
									 -1644826};      //Double Nether Quartz Slab    
		blockColors[44] = new int[] {-5197648,       //Smooth Stone Slab
									 -3028581,       //Sandstone Slab
				 					 -6323123,       //Oak Plank Slab (depreciated)
				 					 -8947849,       //Cobblestone Slab
				 					 -5086386,       //Brick Slab
				 					 -9539986,       //Stone Brick Slab
				 					 -12706268,      //Nether Brick Slab
				 					 -1644826,       //Nether Quartz Slab
				 					 -7368817,       //Stone Slab
				 					 -6329512,       //Granite Slab
									 -3434366,       //P. Granite Slab
									 -4737095,       //Andesite Slab
									 -2894890,       //P. Andesite Slab
									 -10066330,      //Diorite Slab
									 -6645094,       //P. Diorite Slab    
									 -3028581,       //Cut Sandstone Slab
									 -3028581,       //Smooth Sandstone Slab
									 -5416929,       //Cut Red Sandstone Slab
									 -5416929,       //Smooth Red Sandstone Slab
									 -10968957,      //Prismarine Slab
									 -8869470,       //Prismarine Brick Slab
									 -12232866,      //Dark Prismarine Slab
									 -1972056,       //End Stone Brick Slab
									 -13018567,      //Mossy Cobblestone Slab
									 -10917066,      //Mossy Stone Brick Slab
									 -12706268,      //Nether Brick Slab
									 -12058607,      //Red Nether Brick Slab
									 -1644826};      //Smooth Quartz Slab
		blockColors[45] = new int[] {-5086386};      //Brick Block
		blockColors[46] = new int[] {-2407398};      //TNT
		blockColors[47] = new int[] {-6323123};      //Bookshelf
		blockColors[48] = new int[] {-13018567};     //Mossy Cobblestone
		blockColors[49] = new int[] {-14804959};     //Obsidian
		blockColors[50] = new int[] {-10240};        //Torch
		blockColors[51] = new int[] {-4433644};      //Fire
		blockColors[52] = new int[] {-15128782};     //Monster Spawner
		blockColors[53] = new int[] {-6323123};      //Oak Wood Stairs
		blockColors[54] = new int[] {-5539539};      //Chest
		blockColors[55] = new int[] {-2752512};      //Redstone Wire
		blockColors[56] = new int[] {-10621707};     //Diamond Ore
		blockColors[57] = new int[] {-10621707};     //Diamond Block
		blockColors[58] = new int[] {-5279162};      //Crafting Table
		blockColors[59] = new int[] {-7166970};      //Wheat Crop
		blockColors[60] = new int[] {-12244715};     //Farmland
		blockColors[61] = new int[] {-9868951,       //Furnace
									 0xFF48474A,     //Blast Furnace
									 0xFF767676};    //Smoker
		blockColors[62] = new int[] {-9868951};      //Burning Furnace
		blockColors[63] = new int[] {-6323123,       //Oak Sign
				  					 -8956107,       //Spruce Sign
				  					 -8798333,       //Birch Sign
				  					 -4815517,       //Jungle Sign
				  					 -4693450,       //Acacia Sign
				  					 -12640976};     //Dark Oak Sign
		blockColors[64] = new int[] {-6323130};      //Oak Door
		blockColors[65] = new int[] {-6323130};      //Ladder
		blockColors[66] = new int[] {-5987164};      //Rail
		blockColors[67] = new int[] {-8947849};      //Cobblestone Stairs
		blockColors[68] = new int[] {-6323123,       //Oak Wall Sign
									 -8956107,       //Spruce Wall Sign
									 -8798333,       //Birch Wall Sign
									 -4815517,       //Jungle Wall Sign
									 -4693450,       //Acacia Wall Sign
									 -12640976};     //Dark Oak Wall Sign
		blockColors[69] = new int[] {-9874381};      //Lever
		blockColors[70] = new int[] {-7368817};      //Stone Pressure Plate
		blockColors[71] = new int[] {-3289651};      //Iron Door
		blockColors[72] = new int[] {-6323123,       //Oak Pressure Plate
									 -8956107,       //Spruce Pressure Plate
									 -8798333,       //Birch Pressure Plate
									 -4815517,       //Jungle Pressure Plate
									 -4693450,       //Acacia Pressure Plate
									 -12640976};     //Dark Oak Pressure Plate
		blockColors[73] = new int[] {-5635068};      //Redstone Ore
		blockColors[74] = new int[] {-65536};        //Glowing Redstone Ore
		blockColors[75] = new int[] {-11141120};     //Unlit Redstone Torch
		blockColors[76] = new int[] {-196608};       //Redstone Torch
		blockColors[77] = new int[] {-7368817};      //Stone Button
		blockColors[78] = new int[] {-1};            //Snow Layer
		blockColors[79] = new int[] {-15488513};     //Ice
		blockColors[80] = new int[] {-1};            //Snow Block
		blockColors[81] = new int[] {-15696867};     //Cactus
		blockColors[82] = new int[] {-6183595};      //Clay Block
		blockColors[83] = new int[] {-5579916};      //Sugarcane
		blockColors[84] = new int[] {-7053500};      //Jukebox
		blockColors[85] = new int[] {-6323123};      //Oak Fence
		blockColors[86] = new int[] {-1863651,       //Carved Pumpkin
									 -1863651};      //Pumpkin
		blockColors[87] = new int[] {-8174267};      //Netherrack
		blockColors[88] = new int[] {-8822445};      //Soul Sand
		blockColors[89] = new int[] {-17314};        //Glowstone
		blockColors[90] = new int[] {-9168686};      //Nether Portal
		blockColors[91] = new int[] {-1863651};      //Jack o'Lantern
		blockColors[92] = new int[] {-1381654};      //Cake
		blockColors[93] = new int[] {-5723992};      //Unpowered repeater
		blockColors[94] = new int[] {-5723992};      //Powered Repeater
		blockColors[95] = new int[] {-1,             //White Stained Glass /////////Using wool colors
				 					 -820454,        //Orange Stained Glass
				 					 -3319610,       //Magenta Stained Glass
				 					 -13524016,      //Light Blue Stained Glass
				 					 -404678,        //Yellow Stained Glass
				 					 -10047721,      //Lime Stained Glass
				 					 -1144921,       //Pink Stained Glass
				 					 -12040120,      //Gray Stained Glass
				 					 -15369079,      //Light Gray Stained Glass
				 					 -8902231,       //Cyan Stained Glass
				 					 -13223524,      //Purple Stained Glass
				 					 -10076382,      //Blue Stained Glass
				 					 -11640035,      //Brown Stained Glass
				 					 -11508196,      //Green Stained Glass
				 					 -6806752,       //Red Stained Glass
				 					 -15921907};     //Black Stained Glass
		blockColors[96] = new int[] {-7246536,       //Oak Trapdoor
									 -8758730,       //Spruce Trapdoor
									 -2700660,       //Birch Trapdoor
									 -5078434,       //Jungle Trapdoor
									 -5151168,       //Acacia Trapdoor
									 -12640976};     //Dark Oak Trapdoor
		blockColors[97] = new int[] {-7368817,       //Stone Monster Egg
									 -8947849,       //Cobblestone Monster Egg
									 -9539986,       //Stone Brick Monster Egg
									 -10917066,      //Mossy Stone Brick Monster Egg
									 -10658467,      //Cracked Stone Brick Monster Egg
									 -7434610};      //Chiseled Stone Brick Monster Egg
		blockColors[98] = new int[] {-9539986,       //Stone Brick
				 					 -10917066,      //Mossy Stone Brick
				 					 -10658467,      //Cracked Stone Brick
				 					 -7434610};      //Chiseled Stone Brick
		blockColors[99] = new int[] {-7706543};      //Brown Mushroom Block
		blockColors[100] = new int[] {-5365730};     //Red Mushroom Block
		blockColors[101] = new int[] {-10724260};    //Iron Bars
		blockColors[102] = new int[] {-4131330};     //Glass Pane
		blockColors[103] = new int[] {-6710493};     //Melon Block
		blockColors[104] = new int[] {-9918402       //Melon Stem
									  -9918402};     //Attached Melon Stem
		blockColors[105] = new int[] {-9918402       //Pumpkin Stem
									  -9918402};     //Attached Pumpkin Stem
		blockColors[106] = new int[] {-15188984};    //Vines
		blockColors[107] = new int[] {-6323123};     //Oak Fence Gate
		blockColors[108] = new int[] {-5086386};     //Brick Stairs
		blockColors[109] = new int[] {-9539986};     //Stone Brick Stairs
		blockColors[110] = new int[] {-9801854};     //Mycelium
		blockColors[111] = new int[] {-15966444};    //Lillypad
		blockColors[112] = new int[] {-12706268};    //Nether Brick
		blockColors[113] = new int[] {-12706268};    //Nether Brick Fence
		blockColors[114] = new int[] {-12706268};    //Nether Brick Stairs
		blockColors[115] = new int[] {-6216407};     //Nether Wart
		blockColors[116] = new int[] {-9888741};     //Enchantment Table
		blockColors[117] = new int[] {-4278943};     //Brewing Stand
		blockColors[118] = new int[] {-13027015};    //Cauldron
		blockColors[119] = new int[] {-15724528};    //End Portal
		blockColors[120] = new int[] {-3224678};     //End Portal Frame
		blockColors[121] = new int[] {-4804224};     //End Stone
		blockColors[122] = new int[] {-13893326};    //Dragon Egg
		blockColors[123] = new int[] {-4169151};     //Unlit Redstone Lamp
		blockColors[124] = new int[] {-3036720};     //Lit Redstone Lamp
		blockColors[125] = new int[] {-6323123,      //Double Oak Slab (Depreciated)
									  -8956107,      //Double Spruce Slab (Depreciated)
									  -8798333,      //Double Birch Slab (Depreciated)
									  -4815517,		 //Double Jungle Slab (Depreciated)
									  -4693450,      //Double Acacia Slab (Depreciated)
									  -12640976};    //Double Dark Oak Slab (Depreciated)
		blockColors[126] = new int[] {-6323123,      //Oak Slab
									  -8956107,      //Spruce Slab
									  -8798333,      //Birch Slab
									  -4815517,      //Jungle Slab
									  -4693450,      //Acacia Slab
									  -12640976};    //Dark Oak Slab
		blockColors[127] = new int[] {-4229585};     //Cocoa 
		blockColors[128] = new int[] {-3028581};     //Sandstone stairs
		blockColors[129] = new int[] {-15282340};    //Emerald Ore
		blockColors[130] = new int[] {-13812159};    //Ender Chest
		blockColors[131] = new int[] {-8553091};     //Tripwire Hook
		blockColors[132] = new int[] {-8947849};     //Tripwire
		blockColors[133] = new int[] {-15282340};    //Emerald Block
		blockColors[134] = new int[] {-8956107};     //Spruce Stairs
		blockColors[135] = new int[] {-8798333};     //Birch Stairs
		blockColors[136] = new int[] {-4815517};     //Jungle Stairs
		blockColors[137] = new int[] {-4688567};     //Command Block
		blockColors[138] = new int[] {-9444388};     //Beacon
		blockColors[139] = new int[] {-8947849,      //Cobblestone Wall
									  -13018567,     //Mossy Cobblestone Wall
									  -6329512,      //Granite Wall
									  -4737095,      //Andesite Wall
									  -10066330,     //Diorite Wall
									  -5086386,      //Brick Wall
									  -1972056,      //End Stone Brick Wall
									  -10917066,     //Mossy Stone Brick Wall
									  -12706268,     //Nether Brick Wall
									  -10968957,     //Prismarine Wall
									  -12058607,     //Red Nether Brick Wall
									  -3028581,      //Sandstone Wall
									  -5416929,      //Red Sandstone Wall
									  -9539986};     //Stone Brick Wall
		blockColors[140] = new int[] {-8633034};     //Flower Pot
		blockColors[141] = new int[] {-16202752};    //Carrot Crop
		blockColors[142] = new int[] {-16734180};    //Potato Crop
		blockColors[143] = new int[] {-6323123,      //Oak Button
				 					  -8956107,      //Spruce Button
				 					  -8798333,      //Birch Button
				 					  -4815517,      //Jungle Button
				 					  -4693450,      //Acacia Button
				 					  -12640976};    //Dark Oak Button
		blockColors[144] = new int[] {-11908534,     //Skeleton Skull
									  -11908534,     //Skeleton Wall Skull
									  0xFF202020,    //Wither Skeleton Skull
									  0xFF202020,    //Wither Skeleton Wall Skull
									  0xFF59BC4B,    //Creeper Head
									  0xFF59BC4B,    //Creeper Wall Head
									  0xFF141414,    //Dragon Head
									  0xFF141414,    //Dragon Wall Head
									  0xFF271706,    //Player Head
									  0xFF271706,    //Player Wall Head
									  0xFF477331,    //Zombie Head
									  0xFF477331};   //Zombie Wall Head
		blockColors[145] = new int[] {-12763843,     //Anvil
									  -12763843,     //Chipped Anvil
									  -12763843};    //Damaged Anvil
		blockColors[146] = new int[] {-5539539};     //Trapped Chest
		blockColors[147] = new int[] {-131495};      //Light Weighted Pressure Plate
		blockColors[148] = new int[] {-1447447};     //Heavy Weighted Pressure Plate
		blockColors[149] = new int[] {-3489095};     //Redstone Comparator Inactive
		blockColors[150] = new int[] {-3489095};     //Redstone Comparator Active (Depreciated)
		blockColors[151] = new int[] {-3492196};     //Daylight Sensor
		blockColors[152] = new int[] {-5628407};     //Redstone Block
		blockColors[153] = new int[] {-1712427};     //Nether Quartz Ore
		blockColors[154] = new int[] {-10132123};    //Hopper
		blockColors[155] = new int[] {-1644826,      //Quartz Block
									  -1644826,      //Chiseled Quartz Block
									  -1644826,      //Pillar Quartz Block
									  -1644826};     //Smooth Quartz     
		blockColors[156] = new int[] {-1644826};     //Quartz Stairs
		blockColors[157] = new int[] {-6881280};     //Activator Rail
		blockColors[158] = new int[] {-9868951};     //Dropper
		blockColors[159] = new int[] {-1,            //White Hardened Clay /////////Using wool colors
				 					 -820454,        //Orange Hardened Clay
				 					 -3319610,       //Magenta Hardened Clay
				 					 -13524016,      //Light Blue Hardened Clay
				 					 -404678,        //Yellow Hardened Clay
				 					 -10047721,      //Lime Hardened Clay
				 					 -1144921,       //Pink Hardened Clay
				 					 -12040120,      //Gray Hardened Clay
				 					 -15369079,      //Light Hardened Clay
				 					 -8902231,       //Cyan Hardened Clay
				 					 -13223524,      //Purple Hardened Clay
				 					 -10076382,      //Blue Hardened Clay
				 					 -11640035,      //Brown Hardened Clay
				 					 -11508196,      //Green Hardened Clay
				 					 -6806752,       //Red Hardened Clay
				 					 -15921907};     //Black Hardened Clay
		blockColors[160] = new int[] {-1,            //White Stained Glass Pane /////////Using wool colors
				 					  -820454,       //Orange Stained Glass Pane
				 					  -3319610,      //Magenta Stained Glass Pane
				 					  -13524016,     //Light Blue Stained Glass Pane
				 					  -404678,       //Yellow Stained Glass Pane
				 					  -10047721,     //Lime Stained Glass Pane
				 					  -1144921,      //Pink Stained Glass Pane
				 					  -12040120,     //Gray Stained Glass Pane
				 					  -15369079,     //Light Stained Glass Pane
				 					  -8902231,      //Cyan Stained Glass Pane
				 					  -13223524,     //Purple Stained Glass Pane
				 					  -10076382,     //Blue Stained Glass Pane
				 					  -11640035,     //Brown Stained Glass Pane
				 					  -11508196,     //Green Stained Glass Pane
				 					  -6806752,      //Red Stained Glass Pane
				 					  -15921907};    //Black Stained Glass Pane
		blockColors[161] = new int[] {-10248388,     //Acacia Leaves /////////////////////////////////Uses Oak color
									  -10248388};    //Dark Oak Leaves
		blockColors[162] = new int[] {-11317690,     //Acacia Log
									  -15002356};    //Dark Oak Log
		blockColors[163] = new int[] {-4693450};     //Acacia Stairs
		blockColors[164] = new int[] {-12640976};    //Dark Oak Stairs
		blockColors[165] = new int[] {-8800897};     //Slime Block
		blockColors[166] = new int[] {0};            //Barrier
		blockColors[167] = new int[] {-3289651};     //Iron Trap Door
		blockColors[168] = new int[] {-10968957,     //Prismarine Block
									  -8869470,      //Prismarine Brick
									  -12232866};    //Dark Prismarine
		blockColors[169] = new int[] {-3546662};     //Sea Lantern
		blockColors[170] = new int[] {-3298287};     //Hay Bale
		blockColors[171] = new int[] {-1,            //White Carpet
				 					  -820454,       //Orange Carpet
				 					  -3319610,      //Magenta Carpet
				 					  -13524016,     //Light Blue Carpet
				 					  -404678,       //Yellow Carpet
				 					  -10047721,     //Lime Carpet
				 					  -1144921,      //Pink Carpet
				 					  -12040120,     //Gray Carpet
				 					  -15369079,     //Light Gray Carpet
				 					  -8902231,      //Cyan Carpet
				 					  -13223524,     //Purple Carpet
				 					  -10076382,     //Blue Carpet
				 					  -11640035,     //Brown Carpet
				 					  -11508196,     //Green Carpet
				 					  -6806752,      //Red Carpet
				 					  -15921907};    //Black Carpet
		blockColors[172] = new int[] {-6988990};     //Hardened Clay
		blockColors[173] = new int[] {-15395563};    //Coal Block
		blockColors[174] = new int[] {-5979915,      //Packed Ice
									  0xFF76A8F9};   //Blue Ice
		blockColors[175] = new int[] {-2062559,      //Sunflower
									  -4547138,      //Lilac
									  -11964609,     //Double Tallgrass
									  -13678808,     //Large Fern
									  -588017,       //Rose Bush
									  -1720841};     //Peony
		blockColors[176] = new int[] {-1,            //White Banner
				 					  -820454,       //Orange Banner
				 					  -3319610,      //Magenta Banner
				 					  -13524016,     //Light Blue Banner
				 					  -404678,       //Yellow Banner
				 					  -10047721,     //Lime Banner
				 					  -1144921,      //Pink Banner
				 					  -12040120,     //Gray Banner
				 					  -15369079,     //Light Gray Banner
				 					  -8902231,      //Cyan Banner
				 					  -13223524,     //Purple Banner
				 					  -10076382,     //Blue Banner
				 					  -11640035,     //Brown Banner
				 					  -11508196,     //Green Banner
				 					  -6806752,      //Red Banner
				 					  -15921907};    //Black Banner
		blockColors[177] = new int[] {-1,            //White Wall Banner
				 					  -820454,       //Orange Wall Banner
				 					  -3319610,      //Magenta Wall Banner
				 					  -13524016,     //Light Blue Wall Banner
				 					  -404678,       //Yellow Wall Banner
				 					  -10047721,     //Lime Wall Banner
				 					  -1144921,      //Pink Wall Banner
				 					  -12040120,     //Gray Wall Banner
				 					  -15369079,     //Light Gray Wall Banner
				 					  -8902231,      //Cyan Wall Banner
				 					  -13223524,     //Purple Wall Banner
				 					  -10076382,     //Blue Wall Banner
				 					  -11640035,     //Brown Wall Banner
				 					  -11508196,     //Green Wall Banner
				 					  -6806752,      //Red Wall Banner
				 					  -15921907};    //Black Wall Banner
		blockColors[178] = new int[] {-7825490};     //Inverted Daylight Sensor (Depreciated)
		blockColors[179] = new int[] {-5416929,      //Red Sandstone      
									  -5416929,      //Chiseled Red Sandstone
									  -5416929,      //Cut Red Sandstone
									  -5416929};     //Smooth Red Sandstone
		blockColors[180] = new int[] {-5416929};     //Red Sandstone Stairs
		blockColors[181] = new int[] {-5416929};     //Double Red Sandstone Slab
		blockColors[182] = new int[] {-5416929};     //Red Sandstone Slab
		blockColors[183] = new int[] {-8956107};     //Spruce Fence Gate
		blockColors[184] = new int[] {-8798333};     //Birch Fence Gate
		blockColors[185] = new int[] {-4815517};     //Jungle Fence Gate
		blockColors[186] = new int[] {-4693450};     //Acacia Fence Gate
		blockColors[187] = new int[] {-12640976};    //Dark Oak Fence Gate
		blockColors[188] = new int[] {-8956107};     //Spruce Fence
		blockColors[189] = new int[] {-8798333};     //Birch Fence
		blockColors[190] = new int[] {-4815517};     //Jungle Fence
		blockColors[191] = new int[] {-4693450};     //Acacia Fence
		blockColors[192] = new int[] {-12243948};    //Dark Oak Fence
		blockColors[193] = new int[] {-8758730};     //Spruce Door
		blockColors[194] = new int[] {-2700660};     //Birch Door
		blockColors[195] = new int[] {-5078434};     //Jungle Door
		blockColors[196] = new int[] {-5151168};     //Acacia Door
		blockColors[197] = new int[] {-12640976};    //Dark Oak Door
		blockColors[198] = new int[] {-859191};      //End Rod
		blockColors[199] = new int[] {-10997672};    //Chorus Plant
		blockColors[200] = new int[] {-2766891};     //Chorus Flower
		blockColors[201] = new int[] {-5538901};     //Purpur Block
		blockColors[202] = new int[] {-5538901};     //Purpur Pillar
		blockColors[203] = new int[] {-5538901};     //Purpur Stairs
		blockColors[204] = new int[] {-5538901};     //Purpur Double Slab (Depreciated)
		blockColors[205] = new int[] {-5538901};     //Purpur SLab
		blockColors[206] = new int[] {-1972056};     //End Stone Brick
		blockColors[207] = new int[] {-8811171};     //Beetroot Crop
		blockColors[208] = new int[] {-5532327};     //Grass Path
		blockColors[209] = new int[] {-15264735};    //End Gateway
		blockColors[210] = new int[] {-9809977};     //Repeating Command Block
		blockColors[211] = new int[] {-4410729};     //Chain Command Block
		blockColors[212] = new int[] {-9000964};     //Frosted Ice
		blockColors[213] = new int[] {-1678320};     //Magma Block
		blockColors[214] = new int[] {-9109499};     //Nether Wart Block
		blockColors[215] = new int[] {-12058607};    //Red Nether Brick
		blockColors[216] = new int[] {-2367290};     //Bone Block
		blockColors[217] = new int[] {-15770268};    //Structure Void
		blockColors[218] = new int[] {-8487298};     //Observer
		blockColors[219] = new int[] {-2171941};     //White Shulker Box
		blockColors[220] = new int[] {-3115462};     //Orange Shulker Box
		blockColors[221] = new int[] {-4561726};     //Magenta Shulker Box
		blockColors[222] = new int[] {-10121525};    //Light Blue Shulker Box
		blockColors[223] = new int[] {-4081859};     //Yellow Shulker Box
		blockColors[224] = new int[] {-12077253};    //Lime Shulker Box
		blockColors[225] = new int[] {-3109727};     //Pink Shulker Box
		blockColors[226] = new int[] {-11382190};    //Gray Shulker Box
		blockColors[227] = new int[] {-6052957};     //Light Gray Shulker Box
		blockColors[228] = new int[] {-12285788};    //Cyan Shulker Box
		blockColors[229] = new int[] {0xFF721FA6};   //Purple Shulker Box
		blockColors[230] = new int[] {-10128951};    //Blue Shulker Box
		blockColors[231] = new int[] {-7507875};     //Brown Shulker Box
		blockColors[232] = new int[] {-9469356};     //Green Shulker Box
		blockColors[233] = new int[] {-4040619};     //Red Shulker Box
		blockColors[234] = new int[] {-13092808};    //Black Shulker Box
		blockColors[235] = new int[] {-1};           //White Glazed Terracotta
		blockColors[236] = new int[] {-425955};      //Orange Glazed Terracotta
		blockColors[237] = new int[] {-3450686};     //Magenta Glazed Terracotta
		blockColors[238] = new int[] {-12930086};    //Light Blue Terracotta
		blockColors[239] = new int[] {-805339};      //Yellow Glazed Terracotta
		blockColors[240] = new int[] {-8007903};     //Lime Glazed Terracotta
		blockColors[241] = new int[] {-739893};      //Pink Glazed Terracotta
		blockColors[242] = new int[] {-10785679};    //Gray Glazed Terracotta
		blockColors[243] = new int[] {-4079167};     //Light Gray Glazed Terracotta
		blockColors[244] = new int[] {-15504775};    //Cyan Glazed Terracotta
		blockColors[245] = new int[] {-10871411};    //Purple Glazed Terracotta
		blockColors[246] = new int[] {-12827578};    //Blue Glazed Terracotta
		blockColors[247] = new int[] {-5538992};     //Brown Glazed Terracotta
		blockColors[248] = new int[] {-12233183};    //Green Glazed Terracotta
		blockColors[249] = new int[] {-3257532};     //Red Glazed Terracotta
		blockColors[250] = new int[] {-13487566};    //Black Glazed Terracotta
		blockColors[251] = new int[] {-1,            //White Concrete
									  -2006527,      //Orange Concrete
									  -5623394,      //Magenta Concrete
									  -14448443,     //Light Blue Concrete
									  -872169,       //Yellow Concrete
									  -10639336,     //Lime Concrete
									  -2726513,      //Pink Concrete
									  -13027015,     //Gray Concrete
									  -8553101,      //Light Gray Concrete
									  -15370360,     //Cyan Concrete
									  -10149731,     //Purple Concrete
									  -13750128,     //Blue Concrete
									  -10536161,     //Brown Concrete
									  -11903963,     //Green Concrete
									  -7396830,      //Red Concrete
									  -16250872};    //Black Concrete
		blockColors[251] = new int[] {-1,            //White Concrete Powder
									  -2328040,      //Orange Concrete Powder
									  -3714370,      //Magenta Concrete Powder
									  -11880748,     //Light Blue Concrete Powder
									  -1194448,      //Yellow Concrete Powder
									  -8339671,      //Lime Concrete Powder
									  -1530180,      //Pink Concrete Powder
									  -11053225,     //Gray Concrete Powder
									  -6776686,      //Light Gray Concrete Powder
									  -14446182,     //Cyan Concrete Powder
									  -8572245,      //Purple Concrete Powder
									  -12697188,     //Blue Concrete Powder
									  -7511490,      //Brown Concrete Powder
									  -10323665,     //Green Concrete Powder
									  -4834505,      //Red Concrete Powder
									  -15132391};    //Black Concrete Powder
		blockColors[253] = new int[] {};
		blockColors[254] = new int[] {};
		blockColors[255] = new int[] {0};            //Structure Block
		blockColors[256] = new int[] {-11715291,     //Oak Wood
				 					  -14411766,     //Spruce Wood
				 					  -5525845,      //Birch Wood
				 					  -13293298,     //Jungle Wood
				 					  -11317690,     //Acacia Wood
									  -15002356};    //Dark Oak Wood    
		blockColors[257] = new int[] {-4482723,      //Stripped Oak Log
									  -8430284,      //Stripped Spruce Log
									  -3690120,      //Stripped Birch Log
									  -4814046,      //Stripped Jungle Log
									  -5219528,      //Stripped Acacia Log
									  -10138322};    //Stripped Dark Oak Log
		blockColors[258] = new int[] {-4482723,      //Stripped Oak Log
				  					  -8430284,      //Stripped Spruce Wood
				  					  -3690120,      //Stripped Birch Wood
				  					  -4814046,      //Stripped Jungle Wood
				  					  -5219528,      //Stripped Acacia Wood
				  					  -10138322};    //Stripped Dark Oak Wood
		blockColors[259] = new int[] {-6329512,      //Granite Stairs
									  -3434366,      //P. Granite Stairs
									  -4737095,      //Andesite Stairs
									  -2894890,      //P. Andesite Stairs
									  -10066330,     //Diorite Stairs
									  -6645094,      //P. Diorite Stairs
									  -10968957,     //Prismarine Stairs
									  -8869470,      //Prismarine Brick Stairs
									  -12232866,     //Dark Prismarine Stairs
									  -1972056,      //End Stone Brick Stairs
									  -13018567,     //Mossy Cobblestone Stairs
									  -10917066,     //Mossy Stone Brick Stairs
									  -12058607,     //Red Nether Brick Stairs
									  -1644826,      //Smooth Quartz Stairs
									  -3028581,      //Smooth Sandstone Stairs
									  -5416929,      //Red Sandstone Stairs
									  -7368817};     //Stone Stairs
		blockColors[260] = new int[] {0xFF489B1B,    //Seagrass
									  0xFF489B1B};   //Tall Seagrass
		blockColors[261] = new int[] {0xFF74A435,    //Bamboo
				  					  0xFF489B1B};   //Bamboo Sapling
		blockColors[262] = new int[] {0xFF58AC2D,    //Kelp
				  					  0xFF58AC2D};   //Kelp Plant
		blockColors[263] = new int[] {0xFFCAAC79};   //Mushroom Stem
		blockColors[264] = new int[] {-9868951};     //Observer
		blockColors[265] = new int[] {0xFFAB7E4A};   //Scaffold
		blockColors[266] = new int[] {0xFF565C1B};   //Sea Pickle
		blockColors[267] = new int[] {0xFF956595};   //Shulker Box
		blockColors[268] = new int[] {0xFF936C3C};   //Barrel
		blockColors[269] = new int[] {0xFFF9E447};   //Bell
		blockColors[270] = new int[] {0xFFDF7DB6,    //Brain Coral
									  0xFFDF7DB6,    //Brain Coral Block
									  0xFFDF7DB6,    //Brain Coral Fan
									  0xFFDF7DB6};   //Brain Coral Wall Fan
		blockColors[271] = new int[] {0xFFCA2DBB,    //Bubble Coral
				  					  0xFFCA2DBB,    //Bubble Coral Block
				  					  0xFFCA2DBB,    //Bubble Coral Fan
				  					  0xFFCA2DBB};   //Bubble Coral Wall Fan
		blockColors[272] = new int[] {0xFFA61C2B,    //Fire Coral
									  0xFFA61C2B,    //Fire Coral Block
									  0xFFA61C2B,    //Fire Coral Fan
									  0xFFA61C2B};   //Fire Coral Wall Fan
		blockColors[273] = new int[] {0xFFD9CB41,    //Horn Coral
				  					  0xFFD9CB41,    //Horn Coral Block
				  					  0xFFD9CB41,    //Horn Coral Fan
				  					  0xFFD9CB41};   //Horn Coral Wall Fan
		blockColors[274] = new int[] {0xFF335DDD,    //Tube Coral
				  					  0xFF335DDD,    //Tube Coral Block
				  					  0xFF335DDD,    //Tube Coral Fan
				  					  0xFF335DDD};   //Tube Coral Wall Fan
		blockColors[275] = new int[] {0xFF8D8581,    //Dead Brain Coral
									  0xFF8D8581,    //Dead Brain Coral Block
									  0xFF8D8581,    //Dead Brain Coral Fan
									  0xFF8D8581};   //Dead Brain Coral Wall Fan
		blockColors[276] = new int[] {0xFF8D8581,    //Dead Bubble Coral
									  0xFF8D8581,    //Dead Bubble Coral Block
									  0xFF8D8581,    //Dead Bubble Coral Fan
									  0xFF8D8581};   //Dead Bubble Coral Wall Fan
		blockColors[277] = new int[] {0xFF8D8581,    //Dead Fire Coral
									  0xFF8D8581,    //Dead Fire Coral Block
									  0xFF8D8581,    //Dead Fire Coral Fan
									  0xFF8D8581};   //Dead Fire Coral Wall Fan
		blockColors[278] = new int[] {0xFF8D8581,    //Dead Horn Coral
									  0xFF8D8581,    //Dead Horn Coral Block
									  0xFF8D8581,    //Dead Horn Coral Fan
									  0xFF8D8581};   //Dead Horn Coral Wall Fan
		blockColors[279] = new int[] {0xFF8D8581,    //Dead Tube Coral
									  0xFF8D8581,    //Dead Tube Coral Block
									  0xFF8D8581,    //Dead Tube Coral Fan
									  0xFF8D8581};   //Dead Tube Coral Wall Fan
		blockColors[280] = new int[] {-13810027};    //Bubble Stream
		blockColors[281] = new int[] {0xFFECE4CC};   //Turtle Egg
		blockColors[282] = new int[] {0xFFDA9B52};   //Campfire
		blockColors[283] = new int[] {0xFF472A0E};   //Cartography Table
		blockColors[284] = new int[] {0xFF996739};   //Composter
		blockColors[285] = new int[] {0xFFA2967F};   //Conduit
		blockColors[286] = new int[] {0xFF243118};   //Dried Kelp Block
		blockColors[288] = new int[] {0xFF868686};   //Grindstone
		blockColors[289] = new int[] {0xFFD4BFD4};   //Jigsaw Block
		blockColors[290] = new int[] {0xFF444D63};   //Lantern
		blockColors[291] = new int[] {0xFFBD9455};   //Lecturn
		blockColors[292] = new int[] {0xFF7E6E61};   //Loom
		blockColors[293] = new int[] {0xFF2A2B33};   //Smithing Table
		blockColors[294] = new int[] {0xFF7C7C7C};   //Stone Cutter
		blockColors[295] = new int[] {0xFF29653F};   //Sweet Berry Bush
		blockColors[296] = new int[] {0xFF7A4232,    //Potted Plants (I'm being lazy)
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232,
									  0xFF7A4232};
		
	}
	
	/**
	 * Creates the dictionary that links the new String block id's to the old int id's.
	 */
	public static void setBlockDict()
	{
		blockDict = new Hashtable<String, int[]>();
		
		blockDict.put("minecraft:acacia_button", new int[] {143, 4} );
		blockDict.put("minecraft:acacia_door", new int[] {196, 0} );
		blockDict.put("minecraft:acacia_fence_gate", new int[] {187, 0} );
		blockDict.put("minecraft:acacia_fence", new int[] {192, 0} );
		blockDict.put("minecraft:acacia_leaves", new int[] {161, 0} );
		blockDict.put("minecraft:acacia_log", new int[] {162, 0} );
		blockDict.put("minecraft:acacia_planks", new int[] {5, 4} );
		blockDict.put("minecraft:acacia_pressure_plate", new int[] {72, 4} );
		blockDict.put("minecraft:acacia_sapling", new int[] {6, 4} );
		blockDict.put("minecraft:acacia_sign", new int[] {63, 4} );
		blockDict.put("minecraft:acacia_slab", new int[] {126, 4} );
		blockDict.put("minecraft:acacia_stairs", new int[] {163, 0} );
		blockDict.put("minecraft:acacia_trapdoor", new int[] {96, 4} );
		blockDict.put("minecraft:acacia_wall_sign", new int[] {68, 4} );
		blockDict.put("minecraft:acacia_wood", new int[] {256, 4} );
		blockDict.put("minecraft:activator_rail", new int[] {157, 0} );
		blockDict.put("minecraft:air", new int[] {0, 0} );
		blockDict.put("minecraft:allium", new int[] {38, 2} );
		blockDict.put("minecraft:andesite", new int[] {1, 3} );
		blockDict.put("minecraft:andesite_slab", new int[] {44, 11} );
		blockDict.put("minecraft:andesite_stairs", new int[] {259, 2} );
		blockDict.put("minecraft:andesite_wall", new int[] {139, 3} );
		blockDict.put("minecraft:anvil", new int[] {145, 0} );
		blockDict.put("minecraft:attached_melon_stem", new int[] {104, 1} );
		blockDict.put("minecraft:attached_pumpkin_stem", new int[] {105, 1} );
		blockDict.put("minecraft:azure_bluet", new int[] {38, 3} );
		blockDict.put("minecraft:bamboo", new int[] {261, 0} );
		blockDict.put("minecraft:bamboo_sapling", new int[] {261, 1} );
		blockDict.put("minecraft:barrel", new int[] {268, 0} );
		blockDict.put("minecraft:barrier", new int[] {166, 0} );
		blockDict.put("minecraft:beacon", new int[] {138, 0} );
		blockDict.put("minecraft:bedrock", new int[] {7, 0} );
		blockDict.put("minecraft:beetroots", new int[] {207, 0} );
		blockDict.put("minecraft:bell", new int[] {269, 0} );
		blockDict.put("minecraft:birch_button", new int[] {143, 2} );
		blockDict.put("minecraft:birch_door", new int[] {194, 0} );
		blockDict.put("minecraft:birch_fence_gate", new int[] {184, 0} );
		blockDict.put("minecraft:birch_fence", new int[] {189, 0} );
		blockDict.put("minecraft:birch_leaves", new int[] {18, 2} );
		blockDict.put("minecraft:birch_log", new int[] {17, 2} );
		blockDict.put("minecraft:birch_planks", new int[] {5, 2} );
		blockDict.put("minecraft:birch_pressure_plate", new int[] {72, 2} );
		blockDict.put("minecraft:birch_sapling", new int[] {6, 2} );
		blockDict.put("minecraft:birch_sign", new int[] {63, 2} );
		blockDict.put("minecraft:birch_slab", new int[] {126, 2} );
		blockDict.put("minecraft:birch_stairs", new int[] {135, 0} );
		blockDict.put("minecraft:birch_trapdoor", new int[] {96, 2} );
		blockDict.put("minecraft:birch_wall_sign", new int[] {68, 2} );
		blockDict.put("minecraft:birch_wood", new int[] {256, 2} );
		blockDict.put("minecraft:black_banner", new int[] {176, 15} );
		blockDict.put("minecraft:black_bed", new int[] {26, 15} );
		blockDict.put("minecraft:black_carpet", new int[] {171, 15} );
		blockDict.put("minecraft:black_concrete_powder", new int[] {252, 15} );
		blockDict.put("minecraft:black_concrete", new int[] {251, 15} );
		blockDict.put("minecraft:black_glazed_terracotta", new int[] {250, 0} );
		blockDict.put("minecraft:black_shulker_box", new int[] {234, 0} );
		blockDict.put("minecraft:black_stained_glass", new int[] {95, 15} );
		blockDict.put("minecraft:black_stained_glass_pane", new int[] {160, 15} );
		blockDict.put("minecraft:black_terracotta", new int[] {159, 15} );
		blockDict.put("minecraft:black_wall_banner", new int[] {177, 15} );
		blockDict.put("minecraft:black_wool", new int[] {35, 15} );
		blockDict.put("minecraft:blast_furnace", new int[] {61, 1} );
		blockDict.put("minecraft:blue_banner", new int[] {176, 11} );
		blockDict.put("minecraft:blue_bed", new int[] {26, 11} );
		blockDict.put("minecraft:blue_carpet", new int[] {171, 11} );
		blockDict.put("minecraft:blue_concrete_powder", new int[] {252, 11} );
		blockDict.put("minecraft:blue_concrete", new int[] {251, 11} );
		blockDict.put("minecraft:blue_glazed_terracotta", new int[] {246, 0} );
		blockDict.put("minecraft:blue_ice", new int[] {174, 1} );
		blockDict.put("minecraft:blue_orchid", new int[] {38, 1} );
		blockDict.put("minecraft:blue_shulker_box", new int[] {230, 0} );
		blockDict.put("minecraft:blue_stained_glass", new int[] {95, 11} );
		blockDict.put("minecraft:blue_stained_glass_pane", new int[] {160, 11} );
		blockDict.put("minecraft:blue_terracotta", new int[] {159, 11} );
		blockDict.put("minecraft:blue_wall_banner", new int[] {177, 11} );
		blockDict.put("minecraft:blue_wool", new int[] {35, 11} );
		blockDict.put("minecraft:bone_block", new int[] {216, 0} );
		blockDict.put("minecraft:bookshelf", new int[] {47, 0} );
		blockDict.put("minecraft:brain_coral", new int[] {270, 0} );
		blockDict.put("minecraft:brain_coral_block", new int[] {270, 1} );
		blockDict.put("minecraft:brain_coral_fan", new int[] {270, 2} );
		blockDict.put("minecraft:brain_coral_wall_fan", new int[] {270, 3} );
		blockDict.put("minecraft:brewing_stand", new int[] {117, 0} );
		blockDict.put("minecraft:brick_slab", new int[] {44, 4} );
		blockDict.put("minecraft:brick_stairs", new int[] {108, 0} );
		blockDict.put("minecraft:brick_wall", new int[] {139, 5} );
		blockDict.put("minecraft:bricks", new int[] {45, 0} );
		blockDict.put("minecraft:brown_banner", new int[] {176, 12} );
		blockDict.put("minecraft:brown_bed", new int[] {26, 12} );
		blockDict.put("minecraft:brown_carpet", new int[] {171, 12} );
		blockDict.put("minecraft:brown_concrete_powder", new int[] {252, 12} );
		blockDict.put("minecraft:brown_concrete", new int[] {251, 12} );
		blockDict.put("minecraft:brown_glazed_terracotta", new int[] {247, 0} );
		blockDict.put("minecraft:brown_mushroom_block", new int[] {99, 0} );
		blockDict.put("minecraft:brown_mushroom", new int[] {39, 0} );
		blockDict.put("minecraft:brown_shulker_box", new int[] {231, 0} );
		blockDict.put("minecraft:brown_stained_glass", new int[] {95, 12} );
		blockDict.put("minecraft:brown_stained_glass_pane", new int[] {160, 12} );
		blockDict.put("minecraft:brown_terracotta", new int[] {159, 12} );
		blockDict.put("minecraft:brown_wall_banner", new int[] {177, 12} );
		blockDict.put("minecraft:brown_wool", new int[] {35, 12} );
		blockDict.put("minecraft:bubble_column", new int[] {280, 0} );
		blockDict.put("minecraft:bubble_coral", new int[] {271, 0} );
		blockDict.put("minecraft:bubble_coral_block", new int[] {271, 1} );
		blockDict.put("minecraft:bubble_coral_fan", new int[] {271, 2} );
		blockDict.put("minecraft:bubble_coral_wall_fan", new int[] {271, 3} );
		blockDict.put("minecraft:cactus", new int[] {81, 0} );
		blockDict.put("minecraft:cake", new int[] {92, 0} );
		blockDict.put("minecraft:campfire", new int[] {282, 0} );
		blockDict.put("minecraft:carrots", new int[] {141, 0} );
		blockDict.put("minecraft:cartography_table", new int[] {283, 0} );
		blockDict.put("minecraft:carved_pumpkin", new int[] {86, 0} );
		blockDict.put("minecraft:cauldron", new int[] {118, 0} );
		blockDict.put("minecraft:cave_air", new int[] {0, 0} );
		blockDict.put("minecraft:chain_command_block", new int[] {211, 0} );
		blockDict.put("minecraft:chest", new int[] {54, 0} );
		blockDict.put("minecraft:chipped_anvil", new int[] {145, 1} );
		blockDict.put("minecraft:chiseled_quartz_block", new int[] {155, 1} );
		blockDict.put("minecraft:chiseled_red_sandstone", new int[] {179, 1} );
		blockDict.put("minecraft:chiseled_sandstone", new int[] {24, 1} );
		blockDict.put("minecraft:chiseled_stone_bricks", new int[] {98, 3} );
		blockDict.put("minecraft:chorus_flower", new int[] {200, 0} );
		blockDict.put("minecraft:chorus_plant", new int[] {199, 0} );
		blockDict.put("minecraft:clay", new int[] {82, 0} );
		blockDict.put("minecraft:coal_block", new int[] {173, 0} );
		blockDict.put("minecraft:coal_ore", new int[] {16, 0} );
		blockDict.put("minecraft:coarse_dirt", new int[] {3, 1} );
		blockDict.put("minecraft:cobblestone", new int[] {4, 0} );
		blockDict.put("minecraft:cobblestone_slab", new int[] {44, 3} );
		blockDict.put("minecraft:cobblestone_stairs", new int[] {67, 0} );
		blockDict.put("minecraft:cobblestone_wall", new int[] {139, 0} );
		blockDict.put("minecraft:cobweb", new int[] {30, 0} );
		blockDict.put("minecraft:cocoa", new int[] {127, 0} );
		blockDict.put("minecraft:command_block", new int[] {137, 0} );
		blockDict.put("minecraft:comparator", new int[] {149, 0} );
		blockDict.put("minecraft:composter", new int[] {284, 0} );
		blockDict.put("minecraft:conduit", new int[] {285, 0} );
		blockDict.put("minecraft:cornflower", new int[] {38, 11} );
		blockDict.put("minecraft:cracked_stone_bricks", new int[] {98, 2} );
		blockDict.put("minecraft:crafting_table", new int[] {58, 0} );
		blockDict.put("minecraft:creeper_head", new int[] {144, 4} );
		blockDict.put("minecraft:creeper_wall_head", new int[] {144, 5} );
		blockDict.put("minecraft:cut_red_sandstone", new int[] {179, 2} );
		blockDict.put("minecraft:cut_red_sandstone_slab", new int[] {44, 17} );
		blockDict.put("minecraft:cut_sandstone", new int[] {24, 2} );
		blockDict.put("minecraft:cut_sandstone_slab", new int[] {44, 17} );
		blockDict.put("minecraft:cyan_banner", new int[] {176, 9} );
		blockDict.put("minecraft:cyan_bed", new int[] {26, 9} );
		blockDict.put("minecraft:cyan_carpet", new int[] {171, 9} );
		blockDict.put("minecraft:cyan_concrete_powder", new int[] {252, 9} );
		blockDict.put("minecraft:cyan_concrete", new int[] {251, 9} );
		blockDict.put("minecraft:cyan_glazed_terracotta", new int[] {244, 0} );
		blockDict.put("minecraft:cyan_shulker_box", new int[] {228, 0} );
		blockDict.put("minecraft:cyan_stained_glass", new int[] {95, 9} );
		blockDict.put("minecraft:cyan_stained_glass_pane", new int[] {160, 9} );
		blockDict.put("minecraft:cyan_terracotta", new int[] {159, 9} );
		blockDict.put("minecraft:cyan_wall_banner", new int[] {177, 9} );
		blockDict.put("minecraft:cyan_wool", new int[] {35, 9} );
		blockDict.put("minecraft:damaged_anvil", new int[] {145, 2} );
		blockDict.put("minecraft:dandelion", new int[] {37, 0} );
		blockDict.put("minecraft:dark_oak_button", new int[] {143, 5} );
		blockDict.put("minecraft:dark_oak_door", new int[] {197, 0} );
		blockDict.put("minecraft:dark_oak_fence_gate", new int[] {186, 0} );
		blockDict.put("minecraft:dark_oak_fence", new int[] {191, 0} );
		blockDict.put("minecraft:dark_oak_leaves", new int[] {161, 1} );
		blockDict.put("minecraft:dark_oak_log", new int[] {162, 1} );
		blockDict.put("minecraft:dark_oak_planks", new int[] {5, 5} );
		blockDict.put("minecraft:dark_oak_pressure_plate", new int[] {72, 5} );
		blockDict.put("minecraft:dark_oak_sapling", new int[] {6, 5} );
		blockDict.put("minecraft:dark_oak_sign", new int[] {63, 5} );
		blockDict.put("minecraft:dark_oak_slab", new int[] {126, 5} );
		blockDict.put("minecraft:dark_oak_stairs", new int[] {164, 0} );
		blockDict.put("minecraft:dark_oak_trapdoor", new int[] {96, 5} );
		blockDict.put("minecraft:dark_oak_wall_sign", new int[] {68, 5} );
		blockDict.put("minecraft:dark_oak_wood", new int[] {256, 5} );
		blockDict.put("minecraft:dark_prismarine", new int[] {168, 2} );
		blockDict.put("minecraft:dark_prismarine_slab", new int[] {44, 21} );
		blockDict.put("minecraft:dark_prismarine_stairs", new int[] {259, 8} );
		blockDict.put("minecraft:daylight_detector", new int[] {151, 0} );
		blockDict.put("minecraft:dead_brain_coral", new int[] {275, 0} );
		blockDict.put("minecraft:dead_brain_coral_block", new int[] {275, 1} );
		blockDict.put("minecraft:dead_brain_coral_fan", new int[] {275, 2} );
		blockDict.put("minecraft:dead_brain_coral_wall_fan", new int[] {275, 3} );
		blockDict.put("minecraft:dead_bubble_coral", new int[] {276, 0} );
		blockDict.put("minecraft:dead_bubble_coral_block", new int[] {276, 1} );
		blockDict.put("minecraft:dead_bubble_coral_fan", new int[] {276, 2} );
		blockDict.put("minecraft:dead_bubble_coral_wall_fan", new int[] {276, 3} );
		blockDict.put("minecraft:dead_bush", new int[] {32, 0} );
		blockDict.put("minecraft:dead_fire_coral", new int[] {277, 0} );
		blockDict.put("minecraft:dead_fire_coral_block", new int[] {277, 1} );
		blockDict.put("minecraft:dead_fire_coral_fan", new int[] {277, 2} );
		blockDict.put("minecraft:dead_fire_coral_wall_fan", new int[] {277, 3} );
		blockDict.put("minecraft:dead_horn_coral", new int[] {278, 0} );
		blockDict.put("minecraft:dead_horn_coral_block", new int[] {278, 1} );
		blockDict.put("minecraft:dead_horn_coral_fan", new int[] {278, 2} );
		blockDict.put("minecraft:dead_horn_coral_wall_fan", new int[] {278, 3} );
		blockDict.put("minecraft:dead_tube_coral", new int[] {279, 0} );
		blockDict.put("minecraft:dead_tube_coral_block", new int[] {279, 1} );
		blockDict.put("minecraft:dead_tube_coral_fan", new int[] {279, 2} );
		blockDict.put("minecraft:dead_tube_coral_wall_fan", new int[] {279, 3} );
		blockDict.put("minecraft:detector_rail", new int[] {28, 0} );
		blockDict.put("minecraft:diamond_block", new int[] {57, 0} );
		blockDict.put("minecraft:diamond_ore", new int[] {56, 0} );
		blockDict.put("minecraft:diorite", new int[] {1, 5} );
		blockDict.put("minecraft:diorite_slab", new int[] {44, 13} );
		blockDict.put("minecraft:diorite_stairs", new int[] {259, 4} );
		blockDict.put("minecraft:diorite_wall", new int[] {139, 4} );
		blockDict.put("minecraft:dirt", new int[] {3, 0} );
		blockDict.put("minecraft:dispenser", new int[] {23, 0} );
		blockDict.put("minecraft:dragon_egg", new int[] {122, 0} );
		blockDict.put("minecraft:dragon_head", new int[] {144, 6} );
		blockDict.put("minecraft:dragon_wall_head", new int[] {144, 7} );
		blockDict.put("minecraft:dried_kelp_block", new int[] {286, 0} );
		blockDict.put("minecraft:dropper", new int[] {158, 0} );
		blockDict.put("minecraft:emerald_block", new int[] {133, 0} );
		blockDict.put("minecraft:emerald_ore", new int[] {129, 0} );
		blockDict.put("minecraft:enchanting_table", new int[] {116, 0} );
		blockDict.put("minecraft:end_gateway", new int[] {209, 0} );
		blockDict.put("minecraft:end_portal_frame", new int[] {120, 0} );
		blockDict.put("minecraft:end_portal", new int[] {119, 0} );
		blockDict.put("minecraft:end_rod", new int[] {198, 0} );
		blockDict.put("minecraft:end_stone", new int[] {121, 0} );
		blockDict.put("minecraft:end_stone_brick_slab", new int[] {44, 22} );
		blockDict.put("minecraft:end_stone_brick_stairs", new int[] {259, 9} );
		blockDict.put("minecraft:end_stone_brick_wall", new int[] {139, 6} );
		blockDict.put("minecraft:end_stone_bricks", new int[] {206, 0} );
		blockDict.put("minecraft:ender_chest", new int[] {130, 0} );
		blockDict.put("minecraft:farmland", new int[] {60, 0} );
		blockDict.put("minecraft:fern", new int[] {31, 1} );
		blockDict.put("minecraft:fire", new int[] {51, 0} );
		blockDict.put("minecraft:fire_coral", new int[] {272, 0} );
		blockDict.put("minecraft:fire_coral_block", new int[] {272, 1} );
		blockDict.put("minecraft:fire_coral_fan", new int[] {272, 2} );
		blockDict.put("minecraft:fire_coral_wall_fan", new int[] {272, 3} );
		blockDict.put("minecraft:fletching_table", new int[] {287, 0} );
		blockDict.put("minecraft:flower_pot", new int[] {140, 0} );
		blockDict.put("minecraft:flowing_lava", new int[] {10, 0} );
		blockDict.put("minecraft:flowing_water", new int[] {8, 0} );
		blockDict.put("minecraft:frosted_ice", new int[] {212, 0} );
		blockDict.put("minecraft:furnace", new int[] {61, 0} );
		blockDict.put("minecraft:glass", new int[] {20, 0} );
		blockDict.put("minecraft:glass_pane", new int[] {102, 0} );
		blockDict.put("minecraft:glowstone", new int[] {89, 0} );
		blockDict.put("minecraft:gold_block", new int[] {41, 0} );
		blockDict.put("minecraft:gold_ore", new int[] {14, 0} );
		blockDict.put("minecraft:granite", new int[] {1, 1} );
		blockDict.put("minecraft:granite_slab", new int[] {44, 9} );
		blockDict.put("minecraft:granite_stairs", new int[] {259, 0} );
		blockDict.put("minecraft:granite_wall", new int[] {139, 2} );
		blockDict.put("minecraft:grass_block", new int[] {2, 0} );
		blockDict.put("minecraft:grass_path", new int[] {208, 0} );
		blockDict.put("minecraft:grass", new int[] {31, 0} );
		blockDict.put("minecraft:gravel", new int[] {13, 0} );
		blockDict.put("minecraft:gray_banner", new int[] {176, 7} );
		blockDict.put("minecraft:gray_bed", new int[] {26, 7} );
		blockDict.put("minecraft:gray_carpet", new int[] {171, 7} );
		blockDict.put("minecraft:gray_concrete_powder", new int[] {252, 7} );
		blockDict.put("minecraft:gray_concrete", new int[] {251, 7} );
		blockDict.put("minecraft:gray_glazed_terracotta", new int[] {242, 0} );
		blockDict.put("minecraft:gray_shulker_box", new int[] {226, 0} );
		blockDict.put("minecraft:gray_stained_glass", new int[] {95, 7} );
		blockDict.put("minecraft:gray_stained_glass_pane", new int[] {160, 7} );
		blockDict.put("minecraft:gray_terracotta", new int[] {159, 7} );
		blockDict.put("minecraft:gray_wall_banner", new int[] {177, 7} );
		blockDict.put("minecraft:gray_wool", new int[] {35, 7} );
		blockDict.put("minecraft:green_banner", new int[] {176, 13} );
		blockDict.put("minecraft:green_bed", new int[] {26, 13} );
		blockDict.put("minecraft:green_carpet", new int[] {171, 13} );
		blockDict.put("minecraft:green_concrete_powder", new int[] {252, 13} );
		blockDict.put("minecraft:green_concrete", new int[] {251, 13} );
		blockDict.put("minecraft:green_glazed_terracotta", new int[] {248, 0} );
		blockDict.put("minecraft:green_shulker_box", new int[] {232, 0} );
		blockDict.put("minecraft:green_stained_glass", new int[] {95, 13} );
		blockDict.put("minecraft:green_stained_glass_pane", new int[] {160, 13} );
		blockDict.put("minecraft:green_terracotta", new int[] {159, 13} );
		blockDict.put("minecraft:green_wall_banner", new int[] {177, 13} );
		blockDict.put("minecraft:green_wool", new int[] {35, 13} );
		blockDict.put("minecraft:grindstone", new int[] {288, 0} );
		blockDict.put("minecraft:hay_block", new int[] {170, 0} );
		blockDict.put("minecraft:heavy_weighted_pressure_plate", new int[] {148, 0} );
		blockDict.put("minecraft:hopper", new int[] {154, 0} );
		blockDict.put("minecraft:horn_coral", new int[] {273, 0} );
		blockDict.put("minecraft:horn_coral_block", new int[] {273, 1} );
		blockDict.put("minecraft:horn_coral_fan", new int[] {273, 2} );
		blockDict.put("minecraft:horn_coral_wall_fan", new int[] {273, 3} );
		blockDict.put("minecraft:ice", new int[] {79, 0} );
		blockDict.put("minecraft:infested_chiseled_stone_bricks", new int[] {97, 5} );
		blockDict.put("minecraft:infested_cobblestone", new int[] {97, 1} );
		blockDict.put("minecraft:infested_cracked_stone_bricks", new int[] {97, 4} );
		blockDict.put("minecraft:infested_mossy_stone_bricks", new int[] {97, 3} );
		blockDict.put("minecraft:infested_stone", new int[] {97, 0} );
		blockDict.put("minecraft:infested_stone_bricks", new int[] {97, 2} );
		blockDict.put("minecraft:iron_bars", new int[] {101, 0} );
		blockDict.put("minecraft:iron_door", new int[] {71, 0} );
		blockDict.put("minecraft:iron_block", new int[] {42, 0} );
		blockDict.put("minecraft:iron_ore", new int[] {15, 0} );
		blockDict.put("minecraft:iron_trapdoor", new int[] {167, 0} );
		blockDict.put("minecraft:jack_o_lantern", new int[] {91, 0} );
		blockDict.put("minecraft:jigsaw", new int[] {289, 0} );
		blockDict.put("minecraft:jukebox", new int[] {84, 0} );
		blockDict.put("minecraft:jungle_button", new int[] {143, 3} );
		blockDict.put("minecraft:jungle_door", new int[] {195, 0} );
		blockDict.put("minecraft:jungle_fence_gate", new int[] {185, 0} );
		blockDict.put("minecraft:jungle_fence", new int[] {190, 0} );
		blockDict.put("minecraft:jungle_leaves", new int[] {18, 3} );
		blockDict.put("minecraft:jungle_log", new int[] {17, 3} );
		blockDict.put("minecraft:jungle_planks", new int[] {5, 3} );
		blockDict.put("minecraft:jungle_pressure_plate", new int[] {72, 3} );
		blockDict.put("minecraft:jungle_sapling", new int[] {6, 3} );
		blockDict.put("minecraft:jungle_sign", new int[] {63, 3} );
		blockDict.put("minecraft:jungle_slab", new int[] {126, 3} );
		blockDict.put("minecraft:jungle_stairs", new int[] {136, 0} );
		blockDict.put("minecraft:jungle_trapdoor", new int[] {96, 3} );
		blockDict.put("minecraft:jungle_wall_sign", new int[] {68, 3} );
		blockDict.put("minecraft:jungle_wood", new int[] {256, 3} );
		blockDict.put("minecraft:kelp", new int[] {262, 0} );
		blockDict.put("minecraft:kelp_plant", new int[] {262, 1} );
		blockDict.put("minecraft:ladder", new int[] {65, 0} );
		blockDict.put("minecraft:lantern", new int[] {290, 0} );
		blockDict.put("minecraft:lapis_block", new int[] {22, 0} );
		blockDict.put("minecraft:lapis_ore", new int[] {21, 0} );
		blockDict.put("minecraft:large_fern", new int[] {175, 3} );
		blockDict.put("minecraft:lava", new int[] {11, 0} );
		blockDict.put("minecraft:lectern", new int[] {291, 0} );
		blockDict.put("minecraft:lever", new int[] {69, 0} );
		blockDict.put("minecraft:light_blue_banner", new int[] {176, 3} );
		blockDict.put("minecraft:light_blue_bed", new int[] {26, 3} );
		blockDict.put("minecraft:light_blue_carpet", new int[] {171, 3} );
		blockDict.put("minecraft:light_blue_concrete_powder", new int[] {252, 3} );
		blockDict.put("minecraft:light_blue_concrete", new int[] {251, 3} );
		blockDict.put("minecraft:light_blue_glazed_terracotta", new int[] {238, 0} );
		blockDict.put("minecraft:light_blue_shulker_box", new int[] {222, 0} );
		blockDict.put("minecraft:light_blue_stained_glass", new int[] {95, 3} );
		blockDict.put("minecraft:light_blue_stained_glass_pane", new int[] {190, 3} );
		blockDict.put("minecraft:light_blue_terracotta", new int[] {159, 3} );
		blockDict.put("minecraft:light_blue_wall_banner", new int[] {177, 3} );
		blockDict.put("minecraft:light_blue_wool", new int[] {35, 3} );
		blockDict.put("minecraft:light_gray_banner", new int[] {176, 8} );
		blockDict.put("minecraft:light_gray_bed", new int[] {26, 8} );
		blockDict.put("minecraft:light_gray_carpet", new int[] {171, 8} );
		blockDict.put("minecraft:light_gray_concrete_powder", new int[] {252, 8} );
		blockDict.put("minecraft:light_gray_concrete", new int[] {251, 8} );
		blockDict.put("minecraft:light_gray_glazed_terracotta", new int[] {243, 0} );
		blockDict.put("minecraft:light_gray_shulker_box", new int[] {227, 0} );
		blockDict.put("minecraft:light_gray_stained_glass", new int[] {95, 8} );
		blockDict.put("minecraft:light_gray_stained_glass_pane", new int[] {160, 8} );
		blockDict.put("minecraft:light_gray_terracotta", new int[] {159, 8} );
		blockDict.put("minecraft:light_gray_wall_banner", new int[] {177, 8} );
		blockDict.put("minecraft:light_gray_wool", new int[] {35, 8} );
		blockDict.put("minecraft:light_weighted_pressure_plate", new int[] {147, 0} );
		blockDict.put("minecraft:lilac", new int[] {175, 1} );
		blockDict.put("minecraft:lily_pad", new int[] {111, 0} );
		blockDict.put("minecraft:lily_of_the_valley", new int[] {38, 10} );
		blockDict.put("minecraft:lime_banner", new int[] {176, 5} );
		blockDict.put("minecraft:lime_bed", new int[] {26, 5} );
		blockDict.put("minecraft:lime_carpet", new int[] {171, 5} );
		blockDict.put("minecraft:lime_concrete_powder", new int[] {252, 5} );
		blockDict.put("minecraft:lime_concrete", new int[] {251, 5} );
		blockDict.put("minecraft:lime_glazed_terracotta", new int[] {240, 0} );
		blockDict.put("minecraft:lime_shulker_box", new int[] {224, 0} );
		blockDict.put("minecraft:lime_stained_glass", new int[] {95, 5} );
		blockDict.put("minecraft:lime_stained_glass_pane", new int[] {160, 5} );
		blockDict.put("minecraft:lime_terracotta", new int[] {159, 5} );
		blockDict.put("minecraft:lime_wall_banner", new int[] {177, 5} );
		blockDict.put("minecraft:lime_wool", new int[] {35, 5} );
		blockDict.put("minecraft:loom", new int[] {292, 0} );
		blockDict.put("minecraft:magenta_banner", new int[] {176, 2} );
		blockDict.put("minecraft:magenta_bed", new int[] {26, 2} );
		blockDict.put("minecraft:magenta_carpet", new int[] {171, 2} );
		blockDict.put("minecraft:magenta_concrete_powder", new int[] {252, 2} );
		blockDict.put("minecraft:magenta_concrete", new int[] {251, 2} );
		blockDict.put("minecraft:magenta_glazed_terracotta", new int[] {237, 0} );
		blockDict.put("minecraft:magenta_shulker_box", new int[] {221, 0} );
		blockDict.put("minecraft:magenta_stained_glass", new int[] {95, 2} );
		blockDict.put("minecraft:magenta_stained_glass_pane", new int[] {160, 2} );
		blockDict.put("minecraft:magenta_terracotta", new int[] {159, 2} );
		blockDict.put("minecraft:magenta_wall_banner", new int[] {177, 2} );
		blockDict.put("minecraft:magenta_wool", new int[] {35, 2} );
		blockDict.put("minecraft:magma_block", new int[] {213, 0} );
		blockDict.put("minecraft:melon", new int[] {103, 0} );
		blockDict.put("minecraft:melon_stem", new int[] {105, 0} );
		blockDict.put("minecraft:mossy_cobblestone", new int[] {48, 0} );
		blockDict.put("minecraft:mossy_cobblestone_slab", new int[] {44, 23} );
		blockDict.put("minecraft:mossy_cobblestone_stairs", new int[] {259, 10} );
		blockDict.put("minecraft:mossy_cobblestone_wall", new int[] {139, 1} );
		blockDict.put("minecraft:mossy_stone_brick_slab", new int[] {44, 24} );
		blockDict.put("minecraft:mossy_stone_brick_stairs", new int[] {259, 11} );
		blockDict.put("minecraft:mossy_stone_brick_wall", new int[] {139, 7} );
		blockDict.put("minecraft:mossy_stone_bricks", new int[] {98, 1} );
		blockDict.put("minecraft:moving_piston", new int[] {34, 0} );
		blockDict.put("minecraft:mushroom_stem", new int[] {263, 0} );
		blockDict.put("minecraft:mycelium", new int[] {110, 0} );
		blockDict.put("minecraft:nether_brick_fence", new int[] {113, 0} );
		blockDict.put("minecraft:nether_brick_slab", new int[] {44, 6} );
		blockDict.put("minecraft:nether_brick_stairs", new int[] {114, 0} );
		blockDict.put("minecraft:nether_brick_wall", new int[] {139, 8} );
		blockDict.put("minecraft:nether_bricks", new int[] {112, 0} );
		blockDict.put("minecraft:nether_portal", new int[] {90, 0} );
		blockDict.put("minecraft:nether_quartz_ore", new int[] {153, 0} );
		blockDict.put("minecraft:nether_wart_block", new int[] {214, 0} );
		blockDict.put("minecraft:nether_wart", new int[] {115, 0} );
		blockDict.put("minecraft:netherrack", new int[] {87, 0} );
		blockDict.put("minecraft:note_block", new int[] {25, 0} );
		blockDict.put("minecraft:oak_button", new int[] {143, 0} );
		blockDict.put("minecraft:oak_door", new int[] {64, 0} );
		blockDict.put("minecraft:oak_fence_gate", new int[] {107, 0} );
		blockDict.put("minecraft:oak_fence", new int[] {85, 0} );
		blockDict.put("minecraft:oak_leaves", new int[] {18, 0} );
		blockDict.put("minecraft:oak_log", new int[] {17, 0} );
		blockDict.put("minecraft:oak_planks", new int[] {5, 0} );
		blockDict.put("minecraft:oak_pressure_plate", new int[] {72, 0} );
		blockDict.put("minecraft:oak_sapling", new int[] {6, 0} );
		blockDict.put("minecraft:oak_sign", new int[] {63, 0} );
		blockDict.put("minecraft:oak_slab", new int[] {126, 0} );
		blockDict.put("minecraft:oak_stairs", new int[] {63, 0} );
		blockDict.put("minecraft:oak_trapdoor", new int[] {96, 0} );
		blockDict.put("minecraft:oak_wall_sign", new int[] {68, 0} );
		blockDict.put("minecraft:oak_wood", new int[] {256, 0} );
		blockDict.put("minecraft:observer", new int[] {264, 0} );
		blockDict.put("minecraft:obsidian", new int[] {49, 0} );
		blockDict.put("minecraft:orange_banner", new int[] {176, 1} );
		blockDict.put("minecraft:orange_bed", new int[] {26, 1} );
		blockDict.put("minecraft:orange_carpet", new int[] {171, 1} );
		blockDict.put("minecraft:orange_concrete_powder", new int[] {252, 1} );
		blockDict.put("minecraft:orange_concrete", new int[] {251, 1} );
		blockDict.put("minecraft:orange_glazed_terracotta", new int[] {236, 0} );
		blockDict.put("minecraft:orange_shulker_box", new int[] {220, 0} );
		blockDict.put("minecraft:orange_stained_glass", new int[] {95, 1} );
		blockDict.put("minecraft:orange_stained_glass_pane", new int[] {160, 1} );
		blockDict.put("minecraft:orange_terracotta", new int[] {159, 1} );
		blockDict.put("minecraft:orange_tulip", new int[] {38, 5} );
		blockDict.put("minecraft:orange_wall_banner", new int[] {177, 1} );
		blockDict.put("minecraft:orange_wool", new int[] {35, 1} );
		blockDict.put("minecraft:oxeye_daisy", new int[] {38, 8} );
		blockDict.put("minecraft:packed_ice", new int[] {174, 0} );
		blockDict.put("minecraft:peony", new int[] {175, 5} );
		blockDict.put("minecraft:petrified_oak_slab", new int[] {44, 2} );
		blockDict.put("minecraft:pink_banner", new int[] {176, 6} );
		blockDict.put("minecraft:pink_bed", new int[] {26, 6} );
		blockDict.put("minecraft:pink_carpet", new int[] {171, 6} );
		blockDict.put("minecraft:pink_concrete_powder", new int[] {252, 6} );
		blockDict.put("minecraft:pink_concrete", new int[] {251, 6} );
		blockDict.put("minecraft:pink_glazed_terracotta", new int[] {241, 0} );
		blockDict.put("minecraft:pink_shulker_box", new int[] {225, 0} );
		blockDict.put("minecraft:pink_stained_glass", new int[] {95, 6} );
		blockDict.put("minecraft:pink_stained_glass_pane", new int[] {160, 6} );
		blockDict.put("minecraft:pink_terracotta", new int[] {159, 6} );
		blockDict.put("minecraft:pink_tulip", new int[] {38, 7} );
		blockDict.put("minecraft:pink_wall_banner", new int[] {177, 6} );
		blockDict.put("minecraft:pink_wool", new int[] {35, 6} );
		blockDict.put("minecraft:piston_head", new int[] {34, 0} );
		blockDict.put("minecraft:piston", new int[] {34, 0} );
		blockDict.put("minecraft:player_head", new int[] {144, 8} );
		blockDict.put("minecraft:player_wall_head", new int[] {144, 9} );
		blockDict.put("minecraft:podzol", new int[] {3, 2} );
		blockDict.put("minecraft:polished_andesite", new int[] {1, 4} );
		blockDict.put("minecraft:polished_andesite_slab", new int[] {44, 12} );
		blockDict.put("minecraft:polished_andesite_stairs", new int[] {259, 3} );
		blockDict.put("minecraft:polished_diorite", new int[] {1, 6} );
		blockDict.put("minecraft:polished_diorite_slab", new int[] {44, 14} );
		blockDict.put("minecraft:polished_diorite_stairs", new int[] {259, 5} );
		blockDict.put("minecraft:polished_granite", new int[] {1, 2} );
		blockDict.put("minecraft:polished_granite_slab", new int[] {44, 10} );
		blockDict.put("minecraft:polished_granite_stairs", new int[] {259, 1} );
		blockDict.put("minecraft:poppy", new int[] {38, 0} );
		blockDict.put("minecraft:potatoes", new int[] {142, 0} );
		blockDict.put("minecraft:potted_acacia_sapling", new int[] {0, 0} );
		blockDict.put("minecraft:potted_allium", new int[] {0, 0} );
		blockDict.put("minecraft:potted_azure_bluet", new int[] {0, 0} );
		blockDict.put("minecraft:potted_bamboo", new int[] {0, 0} );
		blockDict.put("minecraft:potted_birch_sapling", new int[] {0, 0} );
		blockDict.put("minecraft:potted_blue_orchid", new int[] {0, 0} );
		blockDict.put("minecraft:potted_brown_mushroom", new int[] {0, 0} );
		blockDict.put("minecraft:potted_cactus", new int[] {0, 0} );
		blockDict.put("minecraft:potted_cornflower", new int[] {0, 0} );
		blockDict.put("minecraft:potted_dandelion", new int[] {0, 0} );
		blockDict.put("minecraft:potted_dark_oak_sapling", new int[] {0, 0} );
		blockDict.put("minecraft:potted_dead_bush", new int[] {0, 0} );
		blockDict.put("minecraft:potted_fern", new int[] {0, 0} );
		blockDict.put("minecraft:potted_jungle_sapling", new int[] {0, 0} );
		blockDict.put("minecraft:potted_lily_of_the_valley", new int[] {0, 0} );
		blockDict.put("minecraft:potted_oak_sapling", new int[] {0, 0} );
		blockDict.put("minecraft:potted_orange_tulip", new int[] {0, 0} );
		blockDict.put("minecraft:potted_oxeye_daisy", new int[] {0, 0} );
		blockDict.put("minecraft:potted_pink_tulip", new int[] {0, 0} );
		blockDict.put("minecraft:potted_poppy", new int[] {0, 0} );
		blockDict.put("minecraft:potted_red_mushroom", new int[] {0, 0} );
		blockDict.put("minecraft:potted_red_tulip", new int[] {0, 0} );
		blockDict.put("minecraft:potted_spruce_sapling", new int[] {0, 0} );
		blockDict.put("minecraft:potted_white_tulip", new int[] {0, 0} );
		blockDict.put("minecraft:potted_wither_rose", new int[] {0, 0} );
		blockDict.put("minecraft:powered_rail", new int[] {27, 0} );
		blockDict.put("minecraft:prismarine", new int[] {168, 0} );
		blockDict.put("minecraft:prismarine_brick_slab", new int[] {44, 20} );
		blockDict.put("minecraft:prismarine_brick_stairs", new int[] {259, 7} );
		blockDict.put("minecraft:prismarine_bricks", new int[] {168, 1} );
		blockDict.put("minecraft:prismarine_slab", new int[] {44, 19} );
		blockDict.put("minecraft:prismarine_stairs", new int[] {259, 6} );
		blockDict.put("minecraft:prismarine_wall", new int[] {139, 9} );
		blockDict.put("minecraft:pumpkin", new int[] {86, 1} );
		blockDict.put("minecraft:pumpkin_stem", new int[] {104, 0} );
		blockDict.put("minecraft:purple_banner", new int[] {176, 10} );
		blockDict.put("minecraft:purple_bed", new int[] {26, 10} );
		blockDict.put("minecraft:purple_carpet", new int[] {171, 10} );
		blockDict.put("minecraft:purple_concrete_powder", new int[] {252, 10} );
		blockDict.put("minecraft:purple_concrete", new int[] {251, 10} );
		blockDict.put("minecraft:purple_glazed_terracotta", new int[] {245, 0} );
		blockDict.put("minecraft:purple_shulker_box", new int[] {229, 0} );
		blockDict.put("minecraft:purple_stained_glass", new int[] {95, 10} );
		blockDict.put("minecraft:purple_stained_glass_pane", new int[] {160, 10} );
		blockDict.put("minecraft:purple_terracotta", new int[] {159, 10} );
		blockDict.put("minecraft:purple_wall_banner", new int[] {177, 10} );
		blockDict.put("minecraft:purple_wool", new int[] {35, 10} );
		blockDict.put("minecraft:purpur_block", new int[] {201, 0} );
		blockDict.put("minecraft:purpur_pillar", new int[] {202, 0} );
		blockDict.put("minecraft:purpur_slab", new int[] {205, 0} );
		blockDict.put("minecraft:purpur_stairs", new int[] {203, 0} );
		blockDict.put("minecraft:quartz_block", new int[] {155, 0} );
		blockDict.put("minecraft:quartz_pillar", new int[] {155, 2} );
		blockDict.put("minecraft:quartz_slab", new int[] {44, 7} );
		blockDict.put("minecraft:quartz_stairs", new int[] {156, 0} );
		blockDict.put("minecraft:rail", new int[] {66, 0} );
		blockDict.put("minecraft:red_banner", new int[] {176, 14} );
		blockDict.put("minecraft:red_bed", new int[] {26, 0} );
		blockDict.put("minecraft:red_carpet", new int[] {171, 14} );
		blockDict.put("minecraft:red_concrete_powder", new int[] {252, 14} );
		blockDict.put("minecraft:red_concrete", new int[] {251, 14} );
		blockDict.put("minecraft:red_glazed_terracotta", new int[] {249, 0} );
		blockDict.put("minecraft:red_mushroom_block", new int[] {100, 0} );
		blockDict.put("minecraft:red_mushroom", new int[] {40, 0} );
		blockDict.put("minecraft:red_nether_brick_slab", new int[] {44, 26} );
		blockDict.put("minecraft:red_nether_brick_stairs", new int[] {259, 12} );
		blockDict.put("minecraft:red_nether_brick_wall", new int[] {139, 10} );
		blockDict.put("minecraft:red_nether_bricks", new int[] {215, 0} );
		blockDict.put("minecraft:red_sand", new int[] {12, 1} );
		blockDict.put("minecraft:red_sandstone", new int[] {179, 0} );
		blockDict.put("minecraft:red_sandstone_slab", new int[] {182, 0} );
		blockDict.put("minecraft:red_sandstone_stairs", new int[] {180, 0} );
		blockDict.put("minecraft:red_sandstone_wall", new int[] {139, 10} );
		blockDict.put("minecraft:red_shulker_box", new int[] {233, 0} );
		blockDict.put("minecraft:red_stained_glass", new int[] {95, 14} );
		blockDict.put("minecraft:red_stained_glass_pane", new int[] {160, 14} );
		blockDict.put("minecraft:red_terracotta", new int[] {159, 14} );
		blockDict.put("minecraft:red_tulip", new int[] {38, 4} );
		blockDict.put("minecraft:red_wall_banner", new int[] {177, 14} );
		blockDict.put("minecraft:red_wool", new int[] {35, 14} );
		blockDict.put("minecraft:redstone_block", new int[] {152, 0} );
		blockDict.put("minecraft:redstone_lamp", new int[] {123, 0} );
		blockDict.put("minecraft:redstone_ore", new int[] {73, 0} );
		blockDict.put("minecraft:redstone_torch", new int[] {75, 0} );
		blockDict.put("minecraft:redstone_wall_torch", new int[] {75, 0} );
		blockDict.put("minecraft:redstone_wire", new int[] {55, 0} );
		blockDict.put("minecraft:repeater", new int[] {93, 0} );
		blockDict.put("minecraft:repeating_command_block", new int[] {210, 0} );
		blockDict.put("minecraft:rose_bush", new int[] {175, 4} );
		blockDict.put("minecraft:sand", new int[] {12, 0} );
		blockDict.put("minecraft:sandstone", new int[] {24, 0} );
		blockDict.put("minecraft:sandstone_slab", new int[] {44, 1} );
		blockDict.put("minecraft:sandstone_stairs", new int[] {128, 0} );
		blockDict.put("minecraft:sandstone_wall", new int[] {139, 11} );
		blockDict.put("minecraft:scaffolding", new int[] {265, 0} );
		blockDict.put("minecraft:sea_lantern", new int[] {169, 0} );
		blockDict.put("minecraft:sea_pickle", new int[] {266, 0} );
		blockDict.put("minecraft:seagrass", new int[] {260, 0} );
		blockDict.put("minecraft:shulker_box", new int[] {267, 0} );
		blockDict.put("minecraft:skeleton_skull", new int[] {144, 0} );
		blockDict.put("minecraft:skeleton_wall_skull", new int[] {144, 1} );
		blockDict.put("minecraft:slime_block", new int[] {165, 0} );
		blockDict.put("minecraft:smithing_table", new int[] {293, 0} );
		blockDict.put("minecraft:smoker", new int[] {61, 2} );
		blockDict.put("minecraft:smooth_quartz", new int[] {155, 3} );
		blockDict.put("minecraft:smooth_quartz_slab", new int[] {44, 27} );
		blockDict.put("minecraft:smooth_quartz_stairs", new int[] {259, 14} );
		blockDict.put("minecraft:smooth_red_sandstone", new int[] {179, 3} );
		blockDict.put("minecraft:smooth_red_sandstone_slab", new int[] {44, 18} );
		blockDict.put("minecraft:smooth_red_sandstone_stairs", new int[] {259, 15} );
		blockDict.put("minecraft:smooth_sandstone", new int[] {24, 3} );
		blockDict.put("minecraft:smooth_sandstone_slab", new int[] {44, 16} );
		blockDict.put("minecraft:smooth_sandstone_stairs", new int[] {259, 14} );
		blockDict.put("minecraft:smooth_stone", new int[] {1, 7} );
		blockDict.put("minecraft:smooth_stone_slab", new int[] {44, 0} );
		blockDict.put("minecraft:snow_block", new int[] {80, 0} );
		blockDict.put("minecraft:snow", new int[] {78, 0} );
		blockDict.put("minecraft:soul_sand", new int[] {88, 0} );
		blockDict.put("minecraft:spawner", new int[] {52, 0} );
		blockDict.put("minecraft:sponge", new int[] {19, 0} );
		blockDict.put("minecraft:spruce_button", new int[] {143, 1} );
		blockDict.put("minecraft:spruce_door", new int[] {193, 0} );
		blockDict.put("minecraft:spruce_fence_gate", new int[] {183, 0} );
		blockDict.put("minecraft:spruce_fence", new int[] {188, 0} );
		blockDict.put("minecraft:spruce_leaves", new int[] {18, 1} );
		blockDict.put("minecraft:spruce_log", new int[] {17, 1} );
		blockDict.put("minecraft:spruce_planks", new int[] {5, 1} );
		blockDict.put("minecraft:spruce_pressure_plate", new int[] {72, 1} );
		blockDict.put("minecraft:spruce_sapling", new int[] {6, 1} );
		blockDict.put("minecraft:spruce_sign", new int[] {63, 1} );
		blockDict.put("minecraft:spruce_slab", new int[] {126, 1} );
		blockDict.put("minecraft:spruce_stairs", new int[] {134, 0} );
		blockDict.put("minecraft:spruce_trapdoor", new int[] {96, 1} );
		blockDict.put("minecraft:spruce_wall_sign", new int[] {68, 1} );
		blockDict.put("minecraft:spruce_wood", new int[] {256, 1} );
		blockDict.put("minecraft:sticky_piston", new int[] {29, 0} );
		blockDict.put("minecraft:stone", new int[] {1, 0} );
		blockDict.put("minecraft:stone_brick_slab", new int[] {44, 5} );
		blockDict.put("minecraft:stone_brick_stairs", new int[] {109, 0} );
		blockDict.put("minecraft:stone_brick_wall", new int[] {139, 13} );
		blockDict.put("minecraft:stone_bricks", new int[] {98, 0} );
		blockDict.put("minecraft:stone_button", new int[] {77, 0} );
		blockDict.put("minecraft:stone_pressure_plate", new int[] {70, 0} );
		blockDict.put("minecraft:stone_slab", new int[] {44, 8} );
		blockDict.put("minecraft:stone_stairs", new int[] {259, 16} );
		blockDict.put("minecraft:stonecutter", new int[] {294, 0} );
		blockDict.put("minecraft:stripped_acacia_log", new int[] {257, 4} );
		blockDict.put("minecraft:stripped_acacia_wood", new int[] {258, 4} );
		blockDict.put("minecraft:stripped_birch_log", new int[] {257, 2} );
		blockDict.put("minecraft:stripped_birch_wood", new int[] {258, 2} );
		blockDict.put("minecraft:stripped_dark_oak_log", new int[] {257, 5} );
		blockDict.put("minecraft:stripped_dark_oak_wood", new int[] {258, 5} );
		blockDict.put("minecraft:stripped_jungle_log", new int[] {257, 3} );
		blockDict.put("minecraft:stripped_jungle_wood", new int[] {258, 3} );
		blockDict.put("minecraft:stripped_oak_log", new int[] {257, 0} );
		blockDict.put("minecraft:stripped_oak_wood", new int[] {258, 0} );
		blockDict.put("minecraft:stripped_spruce_log", new int[] {257, 1} );
		blockDict.put("minecraft:stripped_spruce_wood", new int[] {258, 1} );
		blockDict.put("minecraft:structure_block", new int[] {255, 0} );
		blockDict.put("minecraft:structure_void", new int[] {217, 0} );
		blockDict.put("minecraft:sugar_cane", new int[] {83, 0} );
		blockDict.put("minecraft:sunflower", new int[] {175, 0} );
		blockDict.put("minecraft:sweet_berry_bush", new int[] {295, 0} );
		blockDict.put("minecraft:tnt", new int[] {46, 0} );
		blockDict.put("minecraft:tall_grass", new int[] {175, 2} );
		blockDict.put("minecraft:tall_seagrass", new int[] {260, 0} );
		blockDict.put("minecraft:terracotta", new int[] {172, 0} );
		blockDict.put("minecraft:torch", new int[] {50, 0} );
		blockDict.put("minecraft:trapped_chest", new int[] {146, 0} );
		blockDict.put("minecraft:tripwire_hook", new int[] {131, 0} );
		blockDict.put("minecraft:tripwire", new int[] {132, 0} );
		blockDict.put("minecraft:tube_coral", new int[] {274, 0} );
		blockDict.put("minecraft:tube_coral_block", new int[] {274, 1} );
		blockDict.put("minecraft:tube_coral_fan", new int[] {274, 2} );
		blockDict.put("minecraft:tube_coral_wall_fan", new int[] {274, 3} );
		blockDict.put("minecraft:turtle_egg", new int[] {281, 0} );
		blockDict.put("minecraft:vine", new int[] {106, 0} );
		blockDict.put("minecraft:void_air", new int[] {0, 0} );
		blockDict.put("minecraft:wall_torch", new int[] {50, 0} );
		blockDict.put("minecraft:water", new int[] {9, 0} );
		blockDict.put("minecraft:wet_sponge", new int[] {19, 1} );
		blockDict.put("minecraft:wheat", new int[] {59, 0} );
		blockDict.put("minecraft:white_banner", new int[] {176, 0} );
		blockDict.put("minecraft:white_bed", new int[] {26, 14} );
		blockDict.put("minecraft:white_carpet", new int[] {171, 0} );
		blockDict.put("minecraft:white_concrete_powder", new int[] {252, 0} );
		blockDict.put("minecraft:white_concrete", new int[] {251, 0} );
		blockDict.put("minecraft:white_glazed_terracotta", new int[] {235, 0} );
		blockDict.put("minecraft:white_shulker_box", new int[] {219, 0} );
		blockDict.put("minecraft:white_stained_glass", new int[] {95, 0} );
		blockDict.put("minecraft:white_stained_glass_pane", new int[] {160, 0} );
		blockDict.put("minecraft:white_terracotta", new int[] {159, 0} );
		blockDict.put("minecraft:white_tulip", new int[] {38, 6} );
		blockDict.put("minecraft:white_wall_banner", new int[] {177, 0} );
		blockDict.put("minecraft:white_wool", new int[] {35, 0} );
		blockDict.put("minecraft:wither_rose", new int[] {38, 9} );
		blockDict.put("minecraft:wither_skeleton_skull", new int[] {144, 2} );
		blockDict.put("minecraft:wither_skeleton_wall_skull", new int[] {144, 3} );
		blockDict.put("minecraft:yellow_banner", new int[] {176, 4} );
		blockDict.put("minecraft:yellow_bed", new int[] {26, 4} );
		blockDict.put("minecraft:yellow_carpet", new int[] {171, 4} );
		blockDict.put("minecraft:yellow_concrete_powder", new int[] {252, 4} );
		blockDict.put("minecraft:yellow_concrete", new int[] {251, 4} );
		blockDict.put("minecraft:yellow_glazed_terracotta", new int[] {239, 0} );
		blockDict.put("minecraft:yellow_shulker_box", new int[] {223, 0} );
		blockDict.put("minecraft:yellow_stained_glass", new int[] {95, 4} );
		blockDict.put("minecraft:yellow_stained_glass_pane", new int[] {160, 4} );
		blockDict.put("minecraft:yellow_terracotta", new int[] {159, 4} );
		blockDict.put("minecraft:yellow_wall_banner", new int[] {177, 4} );
		blockDict.put("minecraft:yellow_wool", new int[] {35, 4} );
		blockDict.put("minecraft:zombie_head", new int[] {144, 10} );
		blockDict.put("minecraft:zombie_wall_head", new int[] {144, 11} );

	}
	
	/**
	 * Checks whether a block is changes colors depending on the biome using the 
	 *   foliage colors.
	 * @param id   ID of the block
	 * @param meta Metadata of the block
	 * @return     true iff the block/metadata combination is in biomeFoliage 
	 */
	public static boolean hasFoliageColor(int id, int meta)
	{
		for (int i = 0; i < biomeFoliage.length; i++)
			if (id == biomeFoliage[i][0] && meta == biomeFoliage[i][1])
				return true;
		
		return false;
	}

	/**
	 * Checks whether a block is changes colors depending on the biome using the
	 *   grass colors.
	 * @param id   ID of the block
	 * @param meta Metadata of the block
	 * @return     true iff the block/metadata combination is in biomeFoliage 
	 */
	public static boolean hasWaterColor(int id, int meta)
	{
		for (int i = 0; i < biomeWater.length; i++)
			if (id == biomeWater[i][0] && meta == biomeWater[i][1])
				return true;
		
		return false;
	}
	
	/**
	 * Checks whether a block is changes colors depending on the biome using the
	 *   grass colors.
	 * @param id   ID of the block
	 * @param meta Metadata of the block
	 * @return     true iff the block/metadata combination is in biomeFoliage 
	 */
	public static boolean hasGrassColor(int id, int meta)
	{
		for (int i = 0; i < biomeGrass.length; i++)
			if (id == biomeGrass[i][0] && meta == biomeGrass[i][1])
				return true;
		
		return false;
	}
	
	/**
	 * Checks if a block with a certain pre-flattening ID is false on the blockVisibility array.
	 * Invisible blocks will be skipped when determining the top block of a map.
	 * @param id Pre-flattening block id
	 * @return   true if the block should be rendered, false if the block should be skipped
	 */
	public static boolean isBlockVisible(int id)
	{
		if (id < MAX_BLOCK_ID)
			return blockVisibility[id];
		return false;
	}
	
	/**
	 * Checks if a block with a certain post-flattening ID is false on the blockVisibility array.
	 * Invisible blocks will be skipped when determining the top block of a map.
	 * @param id Post-flattening block id
	 * @return   true if the block should be rendered, false if the block should be skipped
	 */
	public static boolean isBlockVisible(String id)
	{
		//Gets the pre-flattening ID of the block
		int[] idMeta = getIdMeta(id);
		
		if (idMeta[0] <= MAX_BLOCK_ID)
			return blockVisibility[idMeta[0]];
		return false;
	}

	/**
	 * Gets the biome-specific color for foliage.
	 * @param biome biome the foliage is located in
	 * @return      int ARGB value for a color
	 */
	public static int getFoliageColor(int biome)
	{
		return foliageColors[biome];
	}
	
	/**
	 * Gets the biome-specific color for grasses.
	 * @param biome biome the grass is located in
	 * @return      int ARGB value for a color
	 */
	public static int getGrassColor(int biome)
	{
		return grassColors[biome];
	}
	
	/**
	 * Gets the biome-specific color for water.
	 * @param biome biome the water is located in
	 * @return      int ARGB value for a color
	 */
	public static int getWaterColor(int biome)
	{
		return waterColors[biome];
	}
	
	/**
	 * Gets the color of a block to be rendered using the pre-flattening block ID and metadata.
	 * @param id   Pre-flattening block ID
	 * @param meta Pre-flattening block metadata
	 * @return     int representation of an ARGB value
	 */
	public static int getBlockColor(int id, int meta, int biome)
	{
		if (id >= blockColors.length || blockColors[id].length == 0)
			return 0;
		
		int color;
		int secondColor = 0;
		boolean blend = false;
		
		color = blockColors[id][Math.min(meta, blockColors[id].length - 1)];
		
		if (hasFoliageColor(id, meta))
		{
			secondColor = getFoliageColor(biome);
			blend = true;
		}
		else if (hasGrassColor(id, meta))
		{
			secondColor = getGrassColor(biome);
			blend = true;
		}
		else if (id == 8 || id == 9 || id == 208)
		{
			secondColor = getWaterColor(biome);
			blend = true;
		}
		
		if (blend)
		{
			int a = 0;
			int r = 0;
			int g = 0;
			int b = 0;
			
			a = 0xFF000000;
			r += (color & 0x00FF0000) >>> 16;
			r += ((secondColor & 0x00FF0000) >>> 16) * 2;
			g += (color & 0x0000FF00) >>> 8;
			g += ((secondColor & 0x0000FF00) >>> 8) * 2;
			b += (color & 0x000000FF);
			b += (secondColor & 0x000000FF) * 2;
			
			r /= 3;
			g /= 3;
			b /= 3;
			
			r <<= 16;
			g <<= 8;
					
			color = a | r | g | b;
		}
		
		return color;
	}
	
	/**
	 * Gets the color of a block to be rendered using the post-flattening block ID.
	 * @param id   Post-flattening block ID
	 * @return     int representation of an ARGB value
	 */
	public static int getBlockColor(String id, int biome)
	{
		if (id == null)
			return 0;
		int[] idMeta = blockDict.get(id);
		return getBlockColor(idMeta[0], idMeta[1], biome);
	}
	
	/**
	 * Gets the program's internal ID and metadata for a block's namespace ID. 
	 * @param namespaceID a block's Minecraft namespace ID
	 * @return            int array containing the ID and metadata for a block
	 */
	public static int[] getIdMeta(String namespaceID)
	{
		return blockDict.get(namespaceID);
	}
	
	/**
	 * Creates a block with a specified blockID and metadata
	 * @param blockID  the program's internal block id
	 * @param metadata the metadata for the block
	 */
	public Block(int blockID, int metadata) 
	{
		this.blockID = blockID;
		this.metadata = metadata;
	}
	
	/**
	 * Gets the block's blockID
	 * @return the block's blockID
	 */
 	public int getBlockID() 
	{
		return blockID;
	}
	
 	/**
 	 * Gets the block's metadata
 	 * @return the block's metadata
 	 */
	public int getMetaData() 
	{
		return metadata;
	}

}
