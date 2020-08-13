/*
   Copyright (C) 2020  Shawn Carter
   Contact: shawn.jf.carter@gmail.com
   
   This file is part of Himeji Map Viewer (HMV).

    HMV is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    HMV is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with HMV.  If not, see <https://www.gnu.org/licenses/>.
 */

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
	
	/**
	 * Gets the numerical ID for the biome
	 * @return
	 */
	public int getBiome()
	{
		return biome;
	}
	
	/**
	 * Gets the name of a biome
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Compares the biome name to another biome, sorting alphabetically
	 */
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
