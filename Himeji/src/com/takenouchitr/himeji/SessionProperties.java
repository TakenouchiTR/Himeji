package com.takenouchitr.himeji;

public class SessionProperties
{
	private static float nightBrightness;
	private static float biomeIntensity;
	private static float waterTransparency;
	private static float shadowIntensity;
	private static float highlightIntensity;
	private static boolean renderLight;
	private static boolean renderBiomes;
	private static boolean renderUnderWater;
	private static boolean renderShadows;
	private static boolean useDefaultColor;
	private static int defaultBiome;
	
	public static float getNightBrightness()
	{
		return nightBrightness;
	}

	public static void setNightBrightness(float nightBrightness)
	{
		SessionProperties.nightBrightness = nightBrightness;
	}

	public static float getBiomeIntensity()
	{
		return biomeIntensity;
	}

	public static void setBiomeIntensity(float biomeIntensity)
	{
		SessionProperties.biomeIntensity = biomeIntensity;
	}

	public static float getWaterTransparency()
	{
		return waterTransparency;
	}

	public static void setWaterTransparency(float waterTransparency)
	{
		SessionProperties.waterTransparency = waterTransparency;
	}

	public static float getShadowIntensity()
	{
		return shadowIntensity;
	}

	public static void setShadowIntensity(float shadowIntensity)
	{
		SessionProperties.shadowIntensity = shadowIntensity;
	}

	public static float getHighlightIntensity()
	{
		return highlightIntensity;
	}

	public static void setHighlightIntensity(float highlightIntensity)
	{
		SessionProperties.highlightIntensity = highlightIntensity;
	}

	public static boolean isRenderLight()
	{
		return renderLight;
	}

	public static void setRenderLight(boolean renderLight)
	{
		SessionProperties.renderLight = renderLight;
	}

	public static boolean isRenderBiomes()
	{
		return renderBiomes;
	}

	public static void setRenderBiomes(boolean renderBiomes)
	{
		SessionProperties.renderBiomes = renderBiomes;
	}

	public static boolean isRenderUnderWater()
	{
		return renderUnderWater;
	}

	public static void setRenderUnderWater(boolean renderUnderWater)
	{
		SessionProperties.renderUnderWater = renderUnderWater;
	}

	public static boolean isRenderShadows()
	{
		return renderShadows;
	}

	public static void setRenderShadows(boolean renderShadows)
	{
		SessionProperties.renderShadows = renderShadows;
	}

	public static boolean isUseDefaultColor()
	{
		return useDefaultColor;
	}

	public static void setUseDefaultColor(boolean useDefaultColor)
	{
		SessionProperties.useDefaultColor = useDefaultColor;
	}

	public static int getDefaultBiome()
	{
		return defaultBiome;
	}

	public static void setDefaultBiome(int defaultBiome)
	{
		SessionProperties.defaultBiome = defaultBiome;
	}

	
	private SessionProperties() {}
}
