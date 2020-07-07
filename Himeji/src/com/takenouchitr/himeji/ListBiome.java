package com.takenouchitr.himeji;

public class ListBiome implements Comparable<ListBiome>
{
	private int biome;
	private String name;
	
	public ListBiome(int biome, String name)
	{
		this.biome = biome;
		this.name = name;
	}
	
	public int getBiome()
	{
		return biome;
	}
	
	@Override
	public int compareTo(ListBiome arg0)
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
