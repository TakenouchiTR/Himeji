package com.github.takenouchitr;

public enum Property 
{
	START_X				("start_x"),
	START_Y 			("start_y"),
	START_Z 			("start_z"),
	END_X				("end_x"),
	END_Y				("end_y"),
	END_Z				("end_z"),
	DIMENSION			("dimension"),
	USE_AREA			("use_area"),
	RENDER_UNDER_WATER	("render_under_water"),
	RENDER_SHADOWS		("render_shadows"),
	RENDER_BIOME_COLORS	("render_biome_colors"),
	WORLD_PATH			("world_path"),
	OUTPUT_PATH			("output_path");
	
	public final String key;
	
	private Property(String key)
	{
		this.key = key;
	}
}
