package com.takenouchitr.himeji.frames;

import javax.swing.JFrame;

import com.takenouchitr.himeji.ListChangeListener;
import com.takenouchitr.himeji.MCCompat.Block;

public class FlagsFrame extends JFrame implements ListChangeListener
{
	public FlagsFrame() 
	{
		Block.addListChangeListener(this);
	}

	@Override
	public void OnItemAddition(String name)
	{
		
	}

	@Override
	public void OnItemUpdate(String oldName, String newName)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnItemRemoval(String name)
	{
		// TODO Auto-generated method stub
		
	}
	
}
