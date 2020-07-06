package com.takenouchitr.himeji;

public enum Property 
{
	START_X				("start_x",             "0"),
	START_Y 			("start_y",             "255"),
	START_Z 			("start_z",             "0"),
	END_X				("end_x",               "0"),
	END_Y				("end_y",               "0"),
	END_Z				("end_z",               "0"),
	DIMENSION			("dimension",           "Overworld"),
	USE_AREA			("use_area",            "false"),
	RENDER_LIGHT        ("render_light",        "true"),
	RENDER_UNDER_WATER	("render_under_water",  "true"),
	RENDER_SHADOWS		("render_shadows",      "true"),
	RENDER_BIOME_COLORS	("render_biome_colors", "true"),
	LOCK_HIGHLIGHT      ("lock_highlight",      "true"),
	NIGHT_BRIGHTNESS    ("night_brightness",    "50"),
	BIOME_INTENSITY     ("biome_intensity",     "66"),
	WATER_TRANSPARENCY  ("water_transparency",  "25"),
	SHADOW_INTENSITY    ("shadow_intensity",    "15"),
	HIGHLIGHT_INTENSITY ("highlight_intensity", "15"),
	SHOW_MISSING_BLOCK  ("show_missing_block",  "true"),
	SHOW_UNKNOWN_BIOME  ("show_unknwon_biome",  "true"),
	WORLD_PATH			("world_path",          ""),
	OUTPUT_PATH			("output_path",         ""),
	LAST_WORLD_FOLDER   ("last_world_folder",   ""),
	LAST_OUTPUT_FOLDER  ("last_output_folder",  ""),
	WORLD_SETTING       ("world_option",        "last"),
	OUTPUT_SETTING      ("output_setting",      "last"),
	SPECIFIED_WORLD     ("specified_world",     ""),
	SPECIFIED_OUTPUT    ("specified_output",    "");
	
	
	public final String key;
	public final String defaultValue;
	
	private Property(String key, String defaultValue)
	{
		this.key = key;
		this.defaultValue = defaultValue;
	}
}
