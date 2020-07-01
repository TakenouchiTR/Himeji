package com.takenouchitr.himeji;

public interface ListChangeListener
{
	public void OnItemAddition(String name, int listID);
	
	public void OnItemUpdate(String oldName, String newName, int listID);
	
	public void OnItemRemoval(String name, int listID);
}
