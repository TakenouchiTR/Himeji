package com.github.takenouchitr;

public class ListBiome implements Comparable
{
	private Biome biome;
	private String name;
	
	public ListBiome(Biome biome)
	{
		this.biome = biome;
		StringBuilder sb = new StringBuilder(biome.toString().toLowerCase());
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		
		for (int i = 1; i < sb.length(); i++)
		{
			char c = sb.charAt(i);
			if (c == '_')
			{
				sb.setCharAt(i++, ' ');
				sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
			}
		}
		
		name = sb.toString();
	}
	
	public Biome getBiome()
	{
		return biome;
	}
	
	@Override
	public int compareTo(Object arg0)
	{
		if (!(arg0 instanceof ListBiome))
			return 0;
		
		ListBiome lb2 = (ListBiome) arg0;
		return name.compareTo(lb2.name);
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
