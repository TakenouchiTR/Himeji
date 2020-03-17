//Program Name:   Block.java
//Date:           3/14/2020
//Programmer:     Shawn Carter
//Description:    This class represents a Block in Minecraft.

import java.util.Dictionary;
import java.util.Hashtable;

public class Block 
{
	public static final int MAX_BLOCK_ID = 256;
	private static boolean[] blockVisibility; 
	private static int[][] blockColors;
	private static Dictionary<String, int[]> blockDict;
	private int blockID;
	private int metadata;
	//private int yCoord;
	//private int sunLightLevel;
	//private int lightLevel;
	//private int biome; 
	
	public Block(int blockID, int metadata, int yCoord, int sunLightLevel, int lightLevel, int biome) 
	{
		this.blockID = blockID;
		this.metadata = metadata;
		//this.yCoord = yCoord;
		//this.sunLightLevel = sunLightLevel;
		//this.lightLevel = lightLevel;
		//this.biome = biome;
	}
	
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
									-6645094};       //P. Diorite 
		blockColors[2] = new int[] {-13256686};      //Grass Block
		blockColors[3] = new int[] {-8825542,        //Dirt
									-10994648,        //Coarse Dirt
									-11912164};       //Podzol
		blockColors[4] = new int[] {-8947849};       //Cobblestone
		blockColors[5] = new int[] {-6323123,        //Oak Planks
									-8956107,        //Spruce Planks
									-8798333,        //Birch Planks
									-4815517,        //Jungle Planks
									-4693450,        //Acacia Planks
									-12243948};      //Dark oak Planks
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
		blockColors[17] = new int[] {-11715291,      //Oak Wood
									 -14411766,      //Spruce Wood
									 -5525845,       //Birch Wood
									 -13293298};     //Jungle Wood
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
		blockColors[24] = new int[] {-8620215,       //Sandstone
									 -8620215,       //Chiseled Sandstone
									 -8620215        //Cut (old Smooth) Sandstone
									 -8620215};      //Smooth Sandstone
		blockColors[25] = new int[] {-6592949};      //Note Block
		blockColors[26] = new int[] {-7727594};      //Bed
		blockColors[27] = new int[] {-2246612};      //Powered Rails
		blockColors[28] = new int[] {-10203350};     //Detector Rail
		blockColors[29] = new int[] {-9325465};      //Sticky Piston
		blockColors[30] = new int[] {-1};            //Cobweb
		blockColors[31] = new int[] {-13085135,       //Grass
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
									 -11508196       //Green Wool
									 -6806752,       //Red Wool
									 -15921907};     //Black Wool
		blockColors[36] = new int[] {};              //???---------------------
		blockColors[37] = new int[] {-919294,};      //Dandelion
		blockColors[38] = new int[] {-4586229,       //Poppy
				 					 -14045189,      //Blue Orchid
				 					 -4692611,       //Allium
				 					 -2564378,       //Azure Bluet
				 					 -2806265,       //Red Tulip
				 					 -2198753,       //Orange Tulip
				 					 -789517,        //White Tulip
				 					 -797453         //Pink Tulip
				 					 -526345};       //Oxeye Daisy
		blockColors[39] = new int[] {};       //
		blockColors[40] = new int[] {};       //
		blockColors[41] = new int[] {};       //
		blockColors[42] = new int[] {};       //
		blockColors[43] = new int[] {};       //
		blockColors[44] = new int[] {};       //
		blockColors[45] = new int[] {};       //
		blockColors[46] = new int[] {};       //
		blockColors[47] = new int[] {};       //
		blockColors[48] = new int[] {};       //
		blockColors[49] = new int[] {};       //
		blockColors[50] = new int[] {};       //
		blockColors[51] = new int[] {};       //
		blockColors[52] = new int[] {};       //
		blockColors[53] = new int[] {};       //
		blockColors[54] = new int[] {};       //
		blockColors[55] = new int[] {};       //
		blockColors[56] = new int[] {};       //
		blockColors[57] = new int[] {};       //
		blockColors[58] = new int[] {};       //
		blockColors[59] = new int[] {};       //
		blockColors[60] = new int[] {};       //
		blockColors[61] = new int[] {};       //
		blockColors[62] = new int[] {};       //
		blockColors[63] = new int[] {};       //
		blockColors[64] = new int[] {};       //
		blockColors[65] = new int[] {};       //
		blockColors[66] = new int[] {};       //
		blockColors[67] = new int[] {};       //
		blockColors[68] = new int[] {};       //
		blockColors[69] = new int[] {};       //
		blockColors[70] = new int[] {};       //
		blockColors[71] = new int[] {};       //
		blockColors[72] = new int[] {};       //
		blockColors[73] = new int[] {};       //
		blockColors[74] = new int[] {};       //
		blockColors[75] = new int[] {};       //
		blockColors[76] = new int[] {};       //
		blockColors[77] = new int[] {};       //
		blockColors[78] = new int[] {-1};       //Snow Layer
		blockColors[79] = new int[] {};       //
		blockColors[80] = new int[] {};       //
		blockColors[81] = new int[] {-15696867};       //Cactus
		blockColors[82] = new int[] {};       //
		blockColors[83] = new int[] {};       //
		blockColors[84] = new int[] {};       //
		blockColors[85] = new int[] {};       //
		blockColors[86] = new int[] {};       //
		blockColors[87] = new int[] {};       //
		blockColors[88] = new int[] {};       //
		blockColors[89] = new int[] {};       //
		blockColors[90] = new int[] {};       //
		blockColors[91] = new int[] {};       //
		blockColors[92] = new int[] {};       //
		blockColors[93] = new int[] {};       //
		blockColors[94] = new int[] {};       //
		blockColors[95] = new int[] {};       //
		blockColors[96] = new int[] {};       //
		blockColors[97] = new int[] {};       //
		blockColors[98] = new int[] {};       //
		blockColors[99] = new int[] {};       //
		blockColors[100] = new int[] {};       //
		blockColors[101] = new int[] {};
		blockColors[102] = new int[] {};
		blockColors[103] = new int[] {};
		blockColors[104] = new int[] {};
		blockColors[105] = new int[] {};
		blockColors[106] = new int[] {};
		blockColors[107] = new int[] {};
		blockColors[108] = new int[] {};
		blockColors[109] = new int[] {};
		blockColors[110] = new int[] {};
		blockColors[111] = new int[] {-15966444}; //Lillypad
		blockColors[112] = new int[] {};
		blockColors[113] = new int[] {};
		blockColors[114] = new int[] {};
		blockColors[115] = new int[] {};
		blockColors[116] = new int[] {};
		blockColors[117] = new int[] {};
		blockColors[118] = new int[] {};
		blockColors[119] = new int[] {};
		blockColors[120] = new int[] {};
		blockColors[121] = new int[] {};
		blockColors[122] = new int[] {};
		blockColors[123] = new int[] {};
		blockColors[124] = new int[] {};
		blockColors[125] = new int[] {};
		blockColors[126] = new int[] {};
		blockColors[127] = new int[] {};
		blockColors[128] = new int[] {-8620215}; //Sandstone stairs
		blockColors[129] = new int[] {};
		blockColors[130] = new int[] {};
		blockColors[131] = new int[] {};
		blockColors[132] = new int[] {};
		blockColors[133] = new int[] {};
		blockColors[134] = new int[] {};
		blockColors[135] = new int[] {};
		blockColors[136] = new int[] {};
		blockColors[137] = new int[] {};
		blockColors[138] = new int[] {};
		blockColors[139] = new int[] {};
		blockColors[140] = new int[] {};
		blockColors[141] = new int[] {};
		blockColors[142] = new int[] {};
		blockColors[143] = new int[] {};
		blockColors[144] = new int[] {};
		blockColors[145] = new int[] {};
		blockColors[146] = new int[] {};
		blockColors[147] = new int[] {};
		blockColors[148] = new int[] {};
		blockColors[149] = new int[] {};
		blockColors[150] = new int[] {};
		blockColors[151] = new int[] {};
		blockColors[152] = new int[] {};
		blockColors[153] = new int[] {};
		blockColors[154] = new int[] {};
		blockColors[155] = new int[] {};
		blockColors[156] = new int[] {};
		blockColors[157] = new int[] {};
		blockColors[158] = new int[] {};
		blockColors[159] = new int[] {};
		blockColors[160] = new int[] {};
		blockColors[161] = new int[] {-10248388,     //Acacia Leaves
									  -10248388};    //Dark Oak Leaves
		blockColors[162] = new int[] {};
		blockColors[163] = new int[] {};
		blockColors[164] = new int[] {};
		blockColors[165] = new int[] {};
		blockColors[166] = new int[] {};
		blockColors[167] = new int[] {};
		blockColors[168] = new int[] {};
		blockColors[169] = new int[] {};
		blockColors[170] = new int[] {};
		blockColors[171] = new int[] {};
		blockColors[172] = new int[] {};
		blockColors[173] = new int[] {};
		blockColors[174] = new int[] {};
		blockColors[175] = new int[] {};
		blockColors[176] = new int[] {};
		blockColors[177] = new int[] {};
		blockColors[178] = new int[] {};
		blockColors[179] = new int[] {};
		blockColors[180] = new int[] {};
		blockColors[181] = new int[] {};
		blockColors[182] = new int[] {};
		blockColors[183] = new int[] {};
		blockColors[184] = new int[] {};
		blockColors[185] = new int[] {};
		blockColors[186] = new int[] {};
		blockColors[187] = new int[] {};
		blockColors[188] = new int[] {};
		blockColors[189] = new int[] {};
		blockColors[190] = new int[] {};
		blockColors[191] = new int[] {};
		blockColors[192] = new int[] {};
		blockColors[193] = new int[] {};
		blockColors[194] = new int[] {};
		blockColors[195] = new int[] {};
		blockColors[196] = new int[] {};
		blockColors[197] = new int[] {};
		blockColors[198] = new int[] {};
		blockColors[199] = new int[] {};
		blockColors[200] = new int[] {};
		blockColors[201] = new int[] {};
		blockColors[202] = new int[] {};
		blockColors[203] = new int[] {};
		blockColors[204] = new int[] {};
		blockColors[205] = new int[] {};
		blockColors[206] = new int[] {};
		blockColors[207] = new int[] {};
		blockColors[208] = new int[] {};
		blockColors[209] = new int[] {};
		blockColors[210] = new int[] {};
		blockColors[211] = new int[] {};
		blockColors[212] = new int[] {};
		blockColors[213] = new int[] {};
		blockColors[214] = new int[] {};
		blockColors[215] = new int[] {};
		blockColors[216] = new int[] {};
		blockColors[217] = new int[] {};
		blockColors[218] = new int[] {};
		blockColors[219] = new int[] {};
		blockColors[220] = new int[] {};
		blockColors[221] = new int[] {};
		blockColors[222] = new int[] {};
		blockColors[223] = new int[] {};
		blockColors[224] = new int[] {};
		blockColors[225] = new int[] {};
		blockColors[226] = new int[] {};
		blockColors[227] = new int[] {};
		blockColors[228] = new int[] {};
		blockColors[229] = new int[] {};
		blockColors[230] = new int[] {};
		blockColors[231] = new int[] {};
		blockColors[232] = new int[] {};
		blockColors[233] = new int[] {};
		blockColors[234] = new int[] {};
		blockColors[235] = new int[] {};
		blockColors[236] = new int[] {};
		blockColors[237] = new int[] {};
		blockColors[238] = new int[] {};
		blockColors[239] = new int[] {};
		blockColors[240] = new int[] {};
		blockColors[241] = new int[] {};
		blockColors[242] = new int[] {};
		blockColors[243] = new int[] {};
		blockColors[244] = new int[] {};
		blockColors[245] = new int[] {};
		blockColors[246] = new int[] {};
		blockColors[247] = new int[] {};
		blockColors[248] = new int[] {};
		blockColors[249] = new int[] {};
		blockColors[250] = new int[] {};
		blockColors[251] = new int[] {};
		blockColors[252] = new int[] {};
		blockColors[253] = new int[] {};
		blockColors[254] = new int[] {};
		blockColors[255] = new int[] {};
	}
	
	/**
	 * Creates the dictionary that links the new String block ids to the old int ids.
	 */
	public static void setBlockDict()
	{
		blockDict = new Hashtable<String, int[]>();
		
		blockDict.put("minecraft:acacia_button", new int[] {0, 0} );
		blockDict.put("minecraft:acacia_door", new int[] {0, 0} );
		blockDict.put("minecraft:acacia_fence_gate", new int[] {0, 0} );
		blockDict.put("minecraft:acacia_fence", new int[] {0, 0} );
		blockDict.put("minecraft:acacia_leaves", new int[] {0, 0} );
		blockDict.put("minecraft:acacia_log", new int[] {0, 0} );
		blockDict.put("minecraft:acacia_planks", new int[] {5, 4} );
		blockDict.put("minecraft:acacia_pressure_plate", new int[] {0, 0} );
		blockDict.put("minecraft:acacia_sapling", new int[] {6, 4} );
		blockDict.put("minecraft:acacia_sign", new int[] {0, 0} );
		blockDict.put("minecraft:acacia_slab", new int[] {0, 0} );
		blockDict.put("minecraft:acacia_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:acacia_trapdoor", new int[] {0, 0} );
		blockDict.put("minecraft:acacia_wall_sign", new int[] {0, 0} );
		blockDict.put("minecraft:acacia_wood", new int[] {0, 0} );
		blockDict.put("minecraft:activator_rail", new int[] {0, 0} );
		blockDict.put("minecraft:air", new int[] {0, 0} );
		blockDict.put("minecraft:allium", new int[] {38, 2} );
		blockDict.put("minecraft:andesite", new int[] {1, 3} );
		blockDict.put("minecraft:andesite_slab", new int[] {0, 0} );
		blockDict.put("minecraft:andesite_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:andesite_wall", new int[] {0, 0} );
		blockDict.put("minecraft:anvil", new int[] {0, 0} );
		blockDict.put("minecraft:attached_melon_stem", new int[] {0, 0} );
		blockDict.put("minecraft:attached_pumpkin_stem", new int[] {0, 0} );
		blockDict.put("minecraft:azure_bluet", new int[] {38, 3} );
		blockDict.put("minecraft:bamboo", new int[] {0, 0} );
		blockDict.put("minecraft:bamboo_sapling", new int[] {0, 0} );
		blockDict.put("minecraft:barrel", new int[] {0, 0} );
		blockDict.put("minecraft:barrier", new int[] {0, 0} );
		blockDict.put("minecraft:beacon", new int[] {0, 0} );
		blockDict.put("minecraft:bedrock", new int[] {7, 0} );
		blockDict.put("minecraft:beetroots", new int[] {0, 0} );
		blockDict.put("minecraft:bell", new int[] {0, 0} );
		blockDict.put("minecraft:birch_button", new int[] {0, 0} );
		blockDict.put("minecraft:birch_door", new int[] {0, 0} );
		blockDict.put("minecraft:birch_fence_gate", new int[] {0, 0} );
		blockDict.put("minecraft:birch_fence", new int[] {0, 0} );
		blockDict.put("minecraft:birch_leaves", new int[] {18, 2} );
		blockDict.put("minecraft:birch_log", new int[] {0, 0} );
		blockDict.put("minecraft:birch_planks", new int[] {5, 2} );
		blockDict.put("minecraft:birch_pressure_plate", new int[] {0, 0} );
		blockDict.put("minecraft:birch_sapling", new int[] {6, 2} );
		blockDict.put("minecraft:birch_sign", new int[] {0, 0} );
		blockDict.put("minecraft:birch_slab", new int[] {0, 0} );
		blockDict.put("minecraft:birch_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:birch_trapdoor", new int[] {0, 0} );
		blockDict.put("minecraft:birch_wall_sign", new int[] {0, 0} );
		blockDict.put("minecraft:birch_wood", new int[] {17, 2} );
		blockDict.put("minecraft:black_banner", new int[] {0, 0} );
		blockDict.put("minecraft:black_bed", new int[] {26, 0} );
		blockDict.put("minecraft:black_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:black_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:black_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:black_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:black_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:black_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:black_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:black_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:black_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:black_wool", new int[] {35, 15} );
		blockDict.put("minecraft:blast_furnace", new int[] {0, 0} );
		blockDict.put("minecraft:blue_banner", new int[] {0, 0} );
		blockDict.put("minecraft:blue_bed", new int[] {26, 0} );
		blockDict.put("minecraft:blue_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:blue_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:blue_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:blue_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:blue_ice", new int[] {0, 0} );
		blockDict.put("minecraft:blue_orchid", new int[] {38, 1} );
		blockDict.put("minecraft:blue_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:blue_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:blue_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:blue_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:blue_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:blue_wool", new int[] {35, 11} );
		blockDict.put("minecraft:bone_block", new int[] {0, 0} );
		blockDict.put("minecraft:bookshelf", new int[] {0, 0} );
		blockDict.put("minecraft:brain_coral", new int[] {0, 0} );
		blockDict.put("minecraft:brain_coral_block", new int[] {0, 0} );
		blockDict.put("minecraft:brain_coral_fan", new int[] {0, 0} );
		blockDict.put("minecraft:brain_coral_wall_fan", new int[] {0, 0} );
		blockDict.put("minecraft:brewing_stand", new int[] {0, 0} );
		blockDict.put("minecraft:brick_slab", new int[] {0, 0} );
		blockDict.put("minecraft:brick_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:brick_wall", new int[] {0, 0} );
		blockDict.put("minecraft:bricks", new int[] {0, 0} );
		blockDict.put("minecraft:brown_banner", new int[] {0, 0} );
		blockDict.put("minecraft:brown_bed", new int[] {26, 0} );
		blockDict.put("minecraft:brown_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:brown_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:brown_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:brown_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:brown_mushroom_block", new int[] {0, 0} );
		blockDict.put("minecraft:brown_mushroom", new int[] {0, 0} );
		blockDict.put("minecraft:brown_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:brown_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:brown_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:brown_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:brown_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:brown_wool", new int[] {35, 12} );
		blockDict.put("minecraft:bubble_column", new int[] {0, 0} );
		blockDict.put("minecraft:bubble_coral", new int[] {0, 0} );
		blockDict.put("minecraft:bubble_coral_block", new int[] {0, 0} );
		blockDict.put("minecraft:bubble_coral_fan", new int[] {0, 0} );
		blockDict.put("minecraft:bubble_coral_wall_fan", new int[] {0, 0} );
		blockDict.put("minecraft:cactus", new int[] {0, 0} );
		blockDict.put("minecraft:cake", new int[] {0, 0} );
		blockDict.put("minecraft:campfire", new int[] {0, 0} );
		blockDict.put("minecraft:carrots", new int[] {0, 0} );
		blockDict.put("minecraft:cartography_table", new int[] {0, 0} );
		blockDict.put("minecraft:carved_pumpkin", new int[] {0, 0} );
		blockDict.put("minecraft:cauldron", new int[] {0, 0} );
		blockDict.put("minecraft:cave_air", new int[] {0, 0} );
		blockDict.put("minecraft:chain_command_block", new int[] {0, 0} );
		blockDict.put("minecraft:chest", new int[] {0, 0} );
		blockDict.put("minecraft:chipped_anvil", new int[] {0, 0} );
		blockDict.put("minecraft:chiseled_quartz_block", new int[] {0, 0} );
		blockDict.put("minecraft:chiseled_red_sandstone", new int[] {0, 0} );
		blockDict.put("minecraft:chiseled_sandstone", new int[] {24, 1} );
		blockDict.put("minecraft:chiseled_stone_bricks", new int[] {0, 0} );
		blockDict.put("minecraft:chorus_flower", new int[] {0, 0} );
		blockDict.put("minecraft:chorus_plant", new int[] {0, 0} );
		blockDict.put("minecraft:clay", new int[] {0, 0} );
		blockDict.put("minecraft:coal_block", new int[] {0, 0} );
		blockDict.put("minecraft:coal_ore", new int[] {16, 0} );
		blockDict.put("minecraft:coarse_dirt", new int[] {3, 1} );
		blockDict.put("minecraft:cobblestone", new int[] {4, 0} );
		blockDict.put("minecraft:cobblestone_slab", new int[] {0, 0} );
		blockDict.put("minecraft:cobblestone_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:cobblestone_wall", new int[] {0, 0} );
		blockDict.put("minecraft:cobweb", new int[] {30, 0} );
		blockDict.put("minecraft:cocoa", new int[] {0, 0} );
		blockDict.put("minecraft:command_block", new int[] {0, 0} );
		blockDict.put("minecraft:comparator", new int[] {0, 0} );
		blockDict.put("minecraft:composter", new int[] {0, 0} );
		blockDict.put("minecraft:conduit", new int[] {0, 0} );
		blockDict.put("minecraft:cornflower", new int[] {0, 0} );
		blockDict.put("minecraft:cracked_stone_bricks", new int[] {0, 0} );
		blockDict.put("minecraft:crafting_table", new int[] {0, 0} );
		blockDict.put("minecraft:creeper_head", new int[] {0, 0} );
		blockDict.put("minecraft:creeper_wall_head", new int[] {0, 0} );
		blockDict.put("minecraft:cut_red_sandstone", new int[] {0, 0} );
		blockDict.put("minecraft:cut_red_sandstone_slab", new int[] {0, 0} );
		blockDict.put("minecraft:cut_sandstone", new int[] {24, 2} );
		blockDict.put("minecraft:cut_sandstone_slab", new int[] {0, 0} );
		blockDict.put("minecraft:cyan_banner", new int[] {0, 0} );
		blockDict.put("minecraft:cyan_bed", new int[] {26, 0} );
		blockDict.put("minecraft:cyan_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:cyan_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:cyan_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:cyan_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:cyan_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:cyan_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:cyan_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:cyan_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:cyan_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:cyan_wool", new int[] {35, 9} );
		blockDict.put("minecraft:damaged_anvil", new int[] {0, 0} );
		blockDict.put("minecraft:dandelion", new int[] {37, 0} );
		blockDict.put("minecraft:dark_oak_button", new int[] {0, 0} );
		blockDict.put("minecraft:dark_oak_door", new int[] {0, 0} );
		blockDict.put("minecraft:dark_oak_fence_gate", new int[] {0, 0} );
		blockDict.put("minecraft:dark_oak_fence", new int[] {0, 0} );
		blockDict.put("minecraft:dark_oak_leaves", new int[] {0, 0} );
		blockDict.put("minecraft:dark_oak_log", new int[] {0, 0} );
		blockDict.put("minecraft:dark_oak_planks", new int[] {5, 5} );
		blockDict.put("minecraft:dark_oak_pressure_plate", new int[] {0, 0} );
		blockDict.put("minecraft:dark_oak_sapling", new int[] {6, 5} );
		blockDict.put("minecraft:dark_oak_sign", new int[] {0, 0} );
		blockDict.put("minecraft:dark_oak_slab", new int[] {0, 0} );
		blockDict.put("minecraft:dark_oak_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:dark_oak_trapdoor", new int[] {0, 0} );
		blockDict.put("minecraft:dark_oak_wall_sign", new int[] {0, 0} );
		blockDict.put("minecraft:dark_oak_wood", new int[] {0, 0} );
		blockDict.put("minecraft:dark_prismarine", new int[] {0, 0} );
		blockDict.put("minecraft:dark_prismarine_slab", new int[] {0, 0} );
		blockDict.put("minecraft:dark_prismarine_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:daylight_detector", new int[] {0, 0} );
		blockDict.put("minecraft:dead_brain_coral", new int[] {0, 0} );
		blockDict.put("minecraft:dead_brain_coral_block", new int[] {0, 0} );
		blockDict.put("minecraft:dead_brain_coral_fan", new int[] {0, 0} );
		blockDict.put("minecraft:dead_brain_coral_wall_fan", new int[] {0, 0} );
		blockDict.put("minecraft:dead_bubble_coral", new int[] {0, 0} );
		blockDict.put("minecraft:dead_bubble_coral_block", new int[] {0, 0} );
		blockDict.put("minecraft:dead_bubble_coral_fan", new int[] {0, 0} );
		blockDict.put("minecraft:dead_bubble_coral_wall_fan", new int[] {0, 0} );
		blockDict.put("minecraft:dead_bush", new int[] {32, 0} );
		blockDict.put("minecraft:dead_fire_coral", new int[] {0, 0} );
		blockDict.put("minecraft:dead_fire_coral_block", new int[] {0, 0} );
		blockDict.put("minecraft:dead_fire_coral_fan", new int[] {0, 0} );
		blockDict.put("minecraft:dead_fire_coral_wall_fan", new int[] {0, 0} );
		blockDict.put("minecraft:dead_horn_coral", new int[] {0, 0} );
		blockDict.put("minecraft:dead_horn_coral_block", new int[] {0, 0} );
		blockDict.put("minecraft:dead_horn_coral_fan", new int[] {0, 0} );
		blockDict.put("minecraft:dead_horn_coral_wall_fan", new int[] {0, 0} );
		blockDict.put("minecraft:dead_tube_coral", new int[] {0, 0} );
		blockDict.put("minecraft:dead_tube_coral_block", new int[] {0, 0} );
		blockDict.put("minecraft:dead_tube_coral_fan", new int[] {0, 0} );
		blockDict.put("minecraft:dead_tube_coral_wall_fan", new int[] {0, 0} );
		blockDict.put("minecraft:detector_rail", new int[] {28, 0} );
		blockDict.put("minecraft:diamond_block", new int[] {0, 0} );
		blockDict.put("minecraft:diamond_ore", new int[] {0, 0} );
		blockDict.put("minecraft:diorite", new int[] {1, 5} );
		blockDict.put("minecraft:diorite_slab", new int[] {0, 0} );
		blockDict.put("minecraft:diorite_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:diorite_wall", new int[] {0, 0} );
		blockDict.put("minecraft:dirt", new int[] {3, 0} );
		blockDict.put("minecraft:dispenser", new int[] {23, 0} );
		blockDict.put("minecraft:dragon_egg", new int[] {0, 0} );
		blockDict.put("minecraft:dragon_head", new int[] {0, 0} );
		blockDict.put("minecraft:dragon_wall_head", new int[] {0, 0} );
		blockDict.put("minecraft:dried_kelp_block", new int[] {0, 0} );
		blockDict.put("minecraft:dropper", new int[] {0, 0} );
		blockDict.put("minecraft:emerald_block", new int[] {0, 0} );
		blockDict.put("minecraft:emerald_ore", new int[] {0, 0} );
		blockDict.put("minecraft:enchanting_table", new int[] {0, 0} );
		blockDict.put("minecraft:end_gateway", new int[] {0, 0} );
		blockDict.put("minecraft:end_portal_frame", new int[] {0, 0} );
		blockDict.put("minecraft:end_portal", new int[] {0, 0} );
		blockDict.put("minecraft:end_rod", new int[] {0, 0} );
		blockDict.put("minecraft:end_stone", new int[] {0, 0} );
		blockDict.put("minecraft:end_stone_brick_slab", new int[] {0, 0} );
		blockDict.put("minecraft:end_stone_brick_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:end_stone_brick_wall", new int[] {0, 0} );
		blockDict.put("minecraft:end_stone_bricks", new int[] {0, 0} );
		blockDict.put("minecraft:ender_chest", new int[] {0, 0} );
		blockDict.put("minecraft:farmland", new int[] {0, 0} );
		blockDict.put("minecraft:fern", new int[] {31, 1} );
		blockDict.put("minecraft:fire", new int[] {0, 0} );
		blockDict.put("minecraft:fire_coral", new int[] {0, 0} );
		blockDict.put("minecraft:fire_coral_block", new int[] {0, 0} );
		blockDict.put("minecraft:fire_coral_fan", new int[] {0, 0} );
		blockDict.put("minecraft:fire_coral_wall_fan", new int[] {0, 0} );
		blockDict.put("minecraft:fletching_table", new int[] {0, 0} );
		blockDict.put("minecraft:flower_pot", new int[] {0, 0} );
		blockDict.put("minecraft:flowing_lava", new int[] {10, 0} );
		blockDict.put("minecraft:flowing_water", new int[] {8, 0} );
		blockDict.put("minecraft:frosted_ice", new int[] {0, 0} );
		blockDict.put("minecraft:furnace", new int[] {0, 0} );
		blockDict.put("minecraft:glass", new int[] {20, 0} );
		blockDict.put("minecraft:glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:glowstone", new int[] {0, 0} );
		blockDict.put("minecraft:gold_block", new int[] {0, 0} );
		blockDict.put("minecraft:gold_ore", new int[] {14, 0} );
		blockDict.put("minecraft:granite", new int[] {1, 1} );
		blockDict.put("minecraft:granite_slab", new int[] {0, 0} );
		blockDict.put("minecraft:granite_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:granite_wall", new int[] {0, 0} );
		blockDict.put("minecraft:grass_block", new int[] {2, 0} );
		blockDict.put("minecraft:grass_path", new int[] {0, 0} );
		blockDict.put("minecraft:grass", new int[] {31, 0} );
		blockDict.put("minecraft:gravel", new int[] {13, 0} );
		blockDict.put("minecraft:gray_banner", new int[] {0, 0} );
		blockDict.put("minecraft:gray_bed", new int[] {26, 0} );
		blockDict.put("minecraft:gray_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:gray_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:gray_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:gray_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:gray_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:gray_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:gray_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:gray_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:gray_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:gray_wool", new int[] {35, 7} );
		blockDict.put("minecraft:green_banner", new int[] {0, 0} );
		blockDict.put("minecraft:green_bed", new int[] {26, 0} );
		blockDict.put("minecraft:green_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:green_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:green_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:green_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:green_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:green_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:green_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:green_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:green_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:green_wool", new int[] {35, 13} );
		blockDict.put("minecraft:grindstone", new int[] {0, 0} );
		blockDict.put("minecraft:hay_block", new int[] {0, 0} );
		blockDict.put("minecraft:heavy_weighted_pressure_plate", new int[] {0, 0} );
		blockDict.put("minecraft:hopper", new int[] {0, 0} );
		blockDict.put("minecraft:horn_coral", new int[] {0, 0} );
		blockDict.put("minecraft:horn_coral_block", new int[] {0, 0} );
		blockDict.put("minecraft:horn_coral_fan", new int[] {0, 0} );
		blockDict.put("minecraft:horn_coral_wall_fan", new int[] {0, 0} );
		blockDict.put("minecraft:ice", new int[] {0, 0} );
		blockDict.put("minecraft:infested_chiseled_stone_bricks", new int[] {0, 0} );
		blockDict.put("minecraft:infested_cobblestone", new int[] {0, 0} );
		blockDict.put("minecraft:infested_cracked_stone_bricks", new int[] {0, 0} );
		blockDict.put("minecraft:infested_mossy_stone_bricks", new int[] {0, 0} );
		blockDict.put("minecraft:infested_stone", new int[] {0, 0} );
		blockDict.put("minecraft:infested_stone_bricks", new int[] {0, 0} );
		blockDict.put("minecraft:iron_bars", new int[] {0, 0} );
		blockDict.put("minecraft:iron_door", new int[] {0, 0} );
		blockDict.put("minecraft:iron_block", new int[] {0, 0} );
		blockDict.put("minecraft:iron_ore", new int[] {15, 0} );
		blockDict.put("minecraft:iron_trapdoor", new int[] {0, 0} );
		blockDict.put("minecraft:jack_o_lantern", new int[] {0, 0} );
		blockDict.put("minecraft:jigsaw", new int[] {0, 0} );
		blockDict.put("minecraft:jukebox", new int[] {0, 0} );
		blockDict.put("minecraft:jungle_button", new int[] {0, 0} );
		blockDict.put("minecraft:jungle_door", new int[] {0, 0} );
		blockDict.put("minecraft:jungle_fence_gate", new int[] {0, 0} );
		blockDict.put("minecraft:jungle_fence", new int[] {0, 0} );
		blockDict.put("minecraft:jungle_leaves", new int[] {18, 3} );
		blockDict.put("minecraft:jungle_log", new int[] {0, 0} );
		blockDict.put("minecraft:jungle_planks", new int[] {5, 3} );
		blockDict.put("minecraft:jungle_pressure_plate", new int[] {0, 0} );
		blockDict.put("minecraft:jungle_sapling", new int[] {6, 3} );
		blockDict.put("minecraft:jungle_sign", new int[] {0, 0} );
		blockDict.put("minecraft:jungle_slab", new int[] {0, 0} );
		blockDict.put("minecraft:jungle_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:jungle_trapdoor", new int[] {0, 0} );
		blockDict.put("minecraft:jungle_wall_sign", new int[] {0, 0} );
		blockDict.put("minecraft:jungle_wood", new int[] {17, 3} );
		blockDict.put("minecraft:kelp", new int[] {0, 0} );
		blockDict.put("minecraft:kelp_plant", new int[] {0, 0} );
		blockDict.put("minecraft:ladder", new int[] {0, 0} );
		blockDict.put("minecraft:lantern", new int[] {0, 0} );
		blockDict.put("minecraft:lapis_block", new int[] {22, 0} );
		blockDict.put("minecraft:lapis_ore", new int[] {21, 0} );
		blockDict.put("minecraft:large_fern", new int[] {0, 0} );
		blockDict.put("minecraft:lava", new int[] {11, 0} );
		blockDict.put("minecraft:lectern", new int[] {0, 0} );
		blockDict.put("minecraft:lever", new int[] {0, 0} );
		blockDict.put("minecraft:light_blue_banner", new int[] {0, 0} );
		blockDict.put("minecraft:light_blue_bed", new int[] {26, 0} );
		blockDict.put("minecraft:light_blue_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:light_blue_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:light_blue_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:light_blue_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:light_blue_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:light_blue_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:light_blue_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:light_blue_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:light_blue_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:light_blue_wool", new int[] {35, 3} );
		blockDict.put("minecraft:light_gray_banner", new int[] {0, 0} );
		blockDict.put("minecraft:light_gray_bed", new int[] {26, 0} );
		blockDict.put("minecraft:light_gray_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:light_gray_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:light_gray_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:light_gray_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:light_gray_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:light_gray_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:light_gray_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:light_gray_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:light_gray_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:light_gray_wool", new int[] {35, 8} );
		blockDict.put("minecraft:light_weighted_pressure_plate", new int[] {0, 0} );
		blockDict.put("minecraft:lilac", new int[] {0, 0} );
		blockDict.put("minecraft:lily_pad", new int[] {0, 0} );
		blockDict.put("minecraft:lily_of_the_valley", new int[] {0, 0} );
		blockDict.put("minecraft:lime_banner", new int[] {0, 0} );
		blockDict.put("minecraft:lime_bed", new int[] {26, 0} );
		blockDict.put("minecraft:lime_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:lime_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:lime_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:lime_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:lime_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:lime_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:lime_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:lime_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:lime_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:lime_wool", new int[] {35, 5} );
		blockDict.put("minecraft:loom", new int[] {0, 0} );
		blockDict.put("minecraft:magenta_banner", new int[] {0, 0} );
		blockDict.put("minecraft:magenta_bed", new int[] {26, 0} );
		blockDict.put("minecraft:magenta_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:magenta_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:magenta_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:magenta_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:magenta_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:magenta_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:magenta_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:magenta_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:magenta_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:magenta_wool", new int[] {35, 2} );
		blockDict.put("minecraft:magma_block", new int[] {0, 0} );
		blockDict.put("minecraft:melon", new int[] {0, 0} );
		blockDict.put("minecraft:melon_stem", new int[] {0, 0} );
		blockDict.put("minecraft:mossy_cobblestone", new int[] {0, 0} );
		blockDict.put("minecraft:mossy_cobblestone_slab", new int[] {0, 0} );
		blockDict.put("minecraft:mossy_cobblestone_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:mossy_cobblestone_wall", new int[] {0, 0} );
		blockDict.put("minecraft:mossy_stone_brick_slab", new int[] {0, 0} );
		blockDict.put("minecraft:mossy_stone_brick_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:mossy_stone_brick_wall", new int[] {0, 0} );
		blockDict.put("minecraft:mossy_stone_bricks", new int[] {0, 0} );
		blockDict.put("minecraft:moving_piston", new int[] {34, 0} );
		blockDict.put("minecraft:mushroom_stem", new int[] {0, 0} );
		blockDict.put("minecraft:mycelium", new int[] {0, 0} );
		blockDict.put("minecraft:nether_brick_fence", new int[] {0, 0} );
		blockDict.put("minecraft:nether_brick_slab", new int[] {0, 0} );
		blockDict.put("minecraft:nether_brick_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:nether_brick_wall", new int[] {0, 0} );
		blockDict.put("minecraft:nether_bricks", new int[] {0, 0} );
		blockDict.put("minecraft:nether_portal", new int[] {0, 0} );
		blockDict.put("minecraft:nether_quartz_ore", new int[] {0, 0} );
		blockDict.put("minecraft:nether_wart_block", new int[] {0, 0} );
		blockDict.put("minecraft:nether_wart", new int[] {0, 0} );
		blockDict.put("minecraft:netherrack", new int[] {0, 0} );
		blockDict.put("minecraft:note_block", new int[] {25, 0} );
		blockDict.put("minecraft:oak_button", new int[] {0, 0} );
		blockDict.put("minecraft:oak_door", new int[] {0, 0} );
		blockDict.put("minecraft:oak_fence_gate", new int[] {0, 0} );
		blockDict.put("minecraft:oak_fence", new int[] {0, 0} );
		blockDict.put("minecraft:oak_leaves", new int[] {18, 0} );
		blockDict.put("minecraft:oak_log", new int[] {0, 0} );
		blockDict.put("minecraft:oak_planks", new int[] {5, 0} );
		blockDict.put("minecraft:oak_pressure_plate", new int[] {0, 0} );
		blockDict.put("minecraft:oak_sapling", new int[] {6, 0} );
		blockDict.put("minecraft:oak_sign", new int[] {0, 0} );
		blockDict.put("minecraft:oak_slab", new int[] {0, 0} );
		blockDict.put("minecraft:oak_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:oak_trapdoor", new int[] {0, 0} );
		blockDict.put("minecraft:oak_wall_sign", new int[] {0, 0} );
		blockDict.put("minecraft:oak_wood", new int[] {17, 0} );
		blockDict.put("minecraft:observer", new int[] {0, 0} );
		blockDict.put("minecraft:obsidian", new int[] {0, 0} );
		blockDict.put("minecraft:orange_banner", new int[] {0, 0} );
		blockDict.put("minecraft:orange_bed", new int[] {26, 0} );
		blockDict.put("minecraft:orange_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:orange_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:orange_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:orange_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:orange_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:orange_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:orange_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:orange_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:orange_tulip", new int[] {38, 5} );
		blockDict.put("minecraft:orange_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:orange_wool", new int[] {35, 1} );
		blockDict.put("minecraft:oxeye_daisy", new int[] {38, 8} );
		blockDict.put("minecraft:packed_ice", new int[] {0, 0} );
		blockDict.put("minecraft:peony", new int[] {0, 0} );
		blockDict.put("minecraft:petrified_oak_slab", new int[] {0, 0} );
		blockDict.put("minecraft:pink_banner", new int[] {0, 0} );
		blockDict.put("minecraft:pink_bed", new int[] {26, 0} );
		blockDict.put("minecraft:pink_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:pink_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:pink_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:pink_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:pink_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:pink_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:pink_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:pink_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:pink_tulip", new int[] {38, 7} );
		blockDict.put("minecraft:pink_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:pink_wool", new int[] {35, 6} );
		blockDict.put("minecraft:piston_head", new int[] {34, 0} );
		blockDict.put("minecraft:piston", new int[] {34, 0} );
		blockDict.put("minecraft:player_head", new int[] {0, 0} );
		blockDict.put("minecraft:player_wall_head", new int[] {0, 0} );
		blockDict.put("minecraft:podzol", new int[] {3, 2} );
		blockDict.put("minecraft:polished_andesite", new int[] {1, 4} );
		blockDict.put("minecraft:polished_andesite_slab", new int[] {0, 0} );
		blockDict.put("minecraft:polished_andesite_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:polished_diorite", new int[] {1, 6} );
		blockDict.put("minecraft:polished_diorite_slab", new int[] {0, 0} );
		blockDict.put("minecraft:polished_diorite_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:polished_granite", new int[] {1, 2} );
		blockDict.put("minecraft:polished_granite_slab", new int[] {0, 0} );
		blockDict.put("minecraft:polished_granite_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:poppy", new int[] {38, 0} );
		blockDict.put("minecraft:potatoes", new int[] {0, 0} );
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
		blockDict.put("minecraft:prismarine", new int[] {0, 0} );
		blockDict.put("minecraft:prismarine_brick_slab", new int[] {0, 0} );
		blockDict.put("minecraft:prismarine_brick_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:prismarine_bricks", new int[] {0, 0} );
		blockDict.put("minecraft:prismarine_slab", new int[] {0, 0} );
		blockDict.put("minecraft:prismarine_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:prismarine_wall", new int[] {0, 0} );
		blockDict.put("minecraft:pumpkin", new int[] {0, 0} );
		blockDict.put("minecraft:pumpkin_stem", new int[] {0, 0} );
		blockDict.put("minecraft:purple_banner", new int[] {0, 0} );
		blockDict.put("minecraft:purple_bed", new int[] {26, 0} );
		blockDict.put("minecraft:purple_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:purple_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:purple_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:purple_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:purple_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:purple_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:purple_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:purple_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:purple_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:purple_wool", new int[] {35, 10} );
		blockDict.put("minecraft:purpur_block", new int[] {0, 0} );
		blockDict.put("minecraft:purpur_pillar", new int[] {0, 0} );
		blockDict.put("minecraft:purpur_slab", new int[] {0, 0} );
		blockDict.put("minecraft:purpur_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:quartz_block", new int[] {0, 0} );
		blockDict.put("minecraft:quartz_pillar", new int[] {0, 0} );
		blockDict.put("minecraft:quartz_slab", new int[] {0, 0} );
		blockDict.put("minecraft:quartz_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:rail", new int[] {0, 0} );
		blockDict.put("minecraft:red_banner", new int[] {0, 0} );
		blockDict.put("minecraft:red_bed", new int[] {26, 0} );
		blockDict.put("minecraft:red_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:red_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:red_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:red_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:red_mushroom_block", new int[] {0, 0} );
		blockDict.put("minecraft:red_mushroom", new int[] {0, 0} );
		blockDict.put("minecraft:red_nether_brick_slab", new int[] {0, 0} );
		blockDict.put("minecraft:red_nether_brick_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:red_nether_brick_wall", new int[] {0, 0} );
		blockDict.put("minecraft:red_nether_bricks", new int[] {0, 0} );
		blockDict.put("minecraft:red_sand", new int[] {12, 1} );
		blockDict.put("minecraft:red_sandstone", new int[] {0, 0} );
		blockDict.put("minecraft:red_sandstone_slab", new int[] {0, 0} );
		blockDict.put("minecraft:red_sandstone_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:red_sandstone_wall", new int[] {0, 0} );
		blockDict.put("minecraft:red_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:red_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:red_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:red_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:red_tulip", new int[] {38, 4} );
		blockDict.put("minecraft:red_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:red_wool", new int[] {35, 14} );
		blockDict.put("minecraft:redstone_block", new int[] {0, 0} );
		blockDict.put("minecraft:redstone_lamp", new int[] {0, 0} );
		blockDict.put("minecraft:redstone_ore", new int[] {0, 0} );
		blockDict.put("minecraft:redstone_torch", new int[] {0, 0} );
		blockDict.put("minecraft:redstone_wall_torch", new int[] {0, 0} );
		blockDict.put("minecraft:redstone_wire", new int[] {0, 0} );
		blockDict.put("minecraft:repeater", new int[] {0, 0} );
		blockDict.put("minecraft:repeating_command_block", new int[] {0, 0} );
		blockDict.put("minecraft:rose_bush", new int[] {0, 0} );
		blockDict.put("minecraft:sand", new int[] {12, 0} );
		blockDict.put("minecraft:sandstone", new int[] {24, 0} );
		blockDict.put("minecraft:sandstone_slab", new int[] {0, 0} );
		blockDict.put("minecraft:sandstone_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:sandstone_wall", new int[] {0, 0} );
		blockDict.put("minecraft:scaffolding", new int[] {0, 0} );
		blockDict.put("minecraft:sea_lantern", new int[] {0, 0} );
		blockDict.put("minecraft:sea_pickle", new int[] {0, 0} );
		blockDict.put("minecraft:seagrass", new int[] {0, 0} );
		blockDict.put("minecraft:shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:skeleton_skull", new int[] {0, 0} );
		blockDict.put("minecraft:skeleton_wall_skull", new int[] {0, 0} );
		blockDict.put("minecraft:slime_block", new int[] {0, 0} );
		blockDict.put("minecraft:smithing_table", new int[] {0, 0} );
		blockDict.put("minecraft:smoker", new int[] {0, 0} );
		blockDict.put("minecraft:smooth_quartz", new int[] {0, 0} );
		blockDict.put("minecraft:smooth_quartz_slab", new int[] {0, 0} );
		blockDict.put("minecraft:smooth_quartz_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:smooth_red_sandstone", new int[] {0, 0} );
		blockDict.put("minecraft:smooth_red_sandstone_slab", new int[] {0, 0} );
		blockDict.put("minecraft:smooth_red_sandstone_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:smooth_sandstone", new int[] {24, 3} );
		blockDict.put("minecraft:smooth_sandstone_slab", new int[] {0, 0} );
		blockDict.put("minecraft:smooth_sandstone_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:smooth_stone", new int[] {0, 0} );
		blockDict.put("minecraft:smooth_stone_slab", new int[] {0, 0} );
		blockDict.put("minecraft:snow_block", new int[] {0, 0} );
		blockDict.put("minecraft:snow", new int[] {0, 0} );
		blockDict.put("minecraft:soul_sand", new int[] {0, 0} );
		blockDict.put("minecraft:spawner", new int[] {0, 0} );
		blockDict.put("minecraft:sponge", new int[] {19, 0} );
		blockDict.put("minecraft:spruce_button", new int[] {0, 0} );
		blockDict.put("minecraft:spruce_door", new int[] {0, 0} );
		blockDict.put("minecraft:spruce_fence_gate", new int[] {0, 0} );
		blockDict.put("minecraft:spruce_fence", new int[] {0, 0} );
		blockDict.put("minecraft:spruce_leaves", new int[] {18, 1} );
		blockDict.put("minecraft:spruce_log", new int[] {0, 0} );
		blockDict.put("minecraft:spruce_planks", new int[] {5, 1} );
		blockDict.put("minecraft:spruce_pressure_plate", new int[] {0, 0} );
		blockDict.put("minecraft:spruce_sapling", new int[] {6, 1} );
		blockDict.put("minecraft:spruce_sign", new int[] {0, 0} );
		blockDict.put("minecraft:spruce_slab", new int[] {0, 0} );
		blockDict.put("minecraft:spruce_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:spruce_trapdoor", new int[] {0, 0} );
		blockDict.put("minecraft:spruce_wall_sign", new int[] {0, 0} );
		blockDict.put("minecraft:spruce_wood", new int[] {17, 1} );
		blockDict.put("minecraft:sticky_piston", new int[] {29, 0} );
		blockDict.put("minecraft:stone", new int[] {0, 0} );
		blockDict.put("minecraft:stone_brick_slab", new int[] {0, 0} );
		blockDict.put("minecraft:stone_brick_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:stone_brick_wall", new int[] {0, 0} );
		blockDict.put("minecraft:stone_bricks", new int[] {0, 0} );
		blockDict.put("minecraft:stone_button", new int[] {0, 0} );
		blockDict.put("minecraft:stone_pressure_plate", new int[] {0, 0} );
		blockDict.put("minecraft:stone_slab", new int[] {0, 0} );
		blockDict.put("minecraft:stone_stairs", new int[] {0, 0} );
		blockDict.put("minecraft:stonecutter", new int[] {0, 0} );
		blockDict.put("minecraft:stripped_acacia_log", new int[] {0, 0} );
		blockDict.put("minecraft:stripped_acacia_wood", new int[] {0, 0} );
		blockDict.put("minecraft:stripped_birch_log", new int[] {0, 0} );
		blockDict.put("minecraft:stripped_birch_wood", new int[] {0, 0} );
		blockDict.put("minecraft:stripped_dark_oak_log", new int[] {0, 0} );
		blockDict.put("minecraft:stripped_dark_oak_wood", new int[] {0, 0} );
		blockDict.put("minecraft:stripped_jungle_log", new int[] {0, 0} );
		blockDict.put("minecraft:stripped_jungle_wood", new int[] {0, 0} );
		blockDict.put("minecraft:stripped_oak_log", new int[] {0, 0} );
		blockDict.put("minecraft:stripped_oak_wood", new int[] {0, 0} );
		blockDict.put("minecraft:stripped_spruce_log", new int[] {0, 0} );
		blockDict.put("minecraft:stripped_spruce_wood", new int[] {0, 0} );
		blockDict.put("minecraft:structure_block", new int[] {0, 0} );
		blockDict.put("minecraft:structure_void", new int[] {0, 0} );
		blockDict.put("minecraft:sugar_cane", new int[] {0, 0} );
		blockDict.put("minecraft:sunflower", new int[] {0, 0} );
		blockDict.put("minecraft:sweet_berry_bush", new int[] {0, 0} );
		blockDict.put("minecraft:tnt", new int[] {0, 0} );
		blockDict.put("minecraft:tall_grass", new int[] {0, 0} );
		blockDict.put("minecraft:tall_seagrass", new int[] {0, 0} );
		blockDict.put("minecraft:terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:torch", new int[] {0, 0} );
		blockDict.put("minecraft:trapped_chest", new int[] {0, 0} );
		blockDict.put("minecraft:tripwire_hook", new int[] {0, 0} );
		blockDict.put("minecraft:tripwire", new int[] {0, 0} );
		blockDict.put("minecraft:tube_coral", new int[] {0, 0} );
		blockDict.put("minecraft:tube_coral_block", new int[] {0, 0} );
		blockDict.put("minecraft:tube_coral_fan", new int[] {0, 0} );
		blockDict.put("minecraft:tube_coral_wall_fan", new int[] {0, 0} );
		blockDict.put("minecraft:turtle_egg", new int[] {0, 0} );
		blockDict.put("minecraft:vine", new int[] {0, 0} );
		blockDict.put("minecraft:void_air", new int[] {0, 0} );
		blockDict.put("minecraft:wall_torch", new int[] {0, 0} );
		blockDict.put("minecraft:water", new int[] {9, 0} );
		blockDict.put("minecraft:wet_sponge", new int[] {19, 1} );
		blockDict.put("minecraft:wheat", new int[] {0, 0} );
		blockDict.put("minecraft:white_banner", new int[] {0, 0} );
		blockDict.put("minecraft:white_bed", new int[] {26, 0} );
		blockDict.put("minecraft:white_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:white_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:white_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:white_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:white_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:white_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:white_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:white_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:white_tulip", new int[] {38, 6} );
		blockDict.put("minecraft:white_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:white_wool", new int[] {35, 0} );
		blockDict.put("minecraft:wither_rose", new int[] {0, 0} );
		blockDict.put("minecraft:wither_skeleton_skull", new int[] {0, 0} );
		blockDict.put("minecraft:wither_skeleton_wall_skull", new int[] {0, 0} );
		blockDict.put("minecraft:yellow_banner", new int[] {0, 0} );
		blockDict.put("minecraft:yellow_bed", new int[] {26, 0} );
		blockDict.put("minecraft:yellow_carpet", new int[] {0, 0} );
		blockDict.put("minecraft:yellow_concrete_powder", new int[] {0, 0} );
		blockDict.put("minecraft:yellow_concrete", new int[] {0, 0} );
		blockDict.put("minecraft:yellow_glazed_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:yellow_shulker_box", new int[] {0, 0} );
		blockDict.put("minecraft:yellow_stained_glass", new int[] {0, 0} );
		blockDict.put("minecraft:yellow_stained_glass_pane", new int[] {0, 0} );
		blockDict.put("minecraft:yellow_terracotta", new int[] {0, 0} );
		blockDict.put("minecraft:yellow_wall_banner", new int[] {0, 0} );
		blockDict.put("minecraft:yellow_wool", new int[] {35, 4} );
		blockDict.put("minecraft:zombie_head", new int[] {0, 0} );
		blockDict.put("minecraft:zombie_wall_head", new int[] {0, 0} );

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
		
		if (idMeta[0] >= MAX_BLOCK_ID)
			return blockVisibility[idMeta[0]];
		return false;
	}

	/**
	 * Gets the color of a block to be rendered using the pre-flattening block ID and metadata.
	 * @param id   Pre-flattening block ID
	 * @param meta Pre-flattening block metadata
	 * @return     int representation of an ARGB value
	 */
	public static int getBlockColor(int id, int meta)
	{
		if (id >= blockColors.length || blockColors[id].length == 0)
			return 0;
		
		return blockColors[id][Math.min(meta, blockColors[id].length - 1)]; 
	}
	
	/**
	 * Gets the color of a block to be rendered using the post-flattening block ID.
	 * @param id   Post-flattening block ID
	 * @return     int representation of an ARGB value
	 */
	public static int getBlockColor(String id)
	{
		if (id == null)
			return 0;
		
		int[] idMeta = blockDict.get(id);
		return getBlockColor(idMeta[0], idMeta[1]);
	}
	
	public static int[] getIdMeta(String name)
	{
		return blockDict.get(name);
	}
	
 	public int getBlockID() 
	{
		return blockID;
	}
	
	public int getMetaData() 
	{
		return metadata;
	}
	
	/*
	public int getYCoord() 
	{
		return yCoord;
	}
	
	public int getLightLevel() 
	{
		return lightLevel;
	}
	
	public int getSunLightLevel()
	{
		return sunLightLevel;
	}
	
	public int getHighestLightLevel()
	{
		return lightLevel > sunLightLevel ? lightLevel : sunLightLevel; 
	}
	
	public int getBiome() 
	{
		return biome;
	}

	*/
}
