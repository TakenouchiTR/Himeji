//Program Name:   LongArrayTag.java
//Date:           3/15/2020
//Programmer:     Shawn Carter
//Description:    This class stores NBT data for an array of longs. It is required to support
//                  modern versions of .mca files.

package com.mojang.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class LongArrayTag extends Tag 
{
	public long[] data;
	
	public LongArrayTag(String name)
	{
		super(name);
	}
	
	public LongArrayTag(String name, long[] data)
	{
		super(name);
		this.data = data;
	}
	
	@Override
	void write(DataOutput dos) throws IOException 
	{
		dos.writeLong(data.length);
        for (int i = 0; i < data.length; i++) 
        {
            dos.writeLong(data[i]);
        }
	}

	@Override
	void load(DataInput dis) throws IOException 
	{
		int length = dis.readInt();
        data = new long[length];
        for (int i = 0; i < length; i++) 
        {
            data[i] = dis.readLong();
        }

	}

	@Override
	public String toString() 
	{
		return "[" + data.length + " bytes]";
	}

	@Override
	public byte getId() 
	{
		return TAG_Long_Array;
	}

	@Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            LongArrayTag o = (LongArrayTag) obj;
            return ((data == null && o.data == null) || (data != null && data.equals(o.data)));
        }
        return false;
    }
	
	@Override
	public Tag copy() 
	{
		long[] cp = new long[data.length];
        System.arraycopy(data, 0, cp, 0, data.length);
        return new LongArrayTag(getName(), cp);
	}

}
