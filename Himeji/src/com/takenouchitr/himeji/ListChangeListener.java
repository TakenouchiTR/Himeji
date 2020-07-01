package com.takenouchitr.himeji;

public interface ListChangeListener
{
	public void OnItemAddition(String name);
	
	public void OnItemUpdate(String oldName, String newName);
	
	public void OnItemRemoval(String name);
}
