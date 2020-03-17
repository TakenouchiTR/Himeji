//Program Name:   Himeji.java
//Date:           3/13/2020
//Programmer:     Shawn Carter
//Description:    This program creates a map image of a Minecraft world.

import java.util.Scanner;
import java.io.*;

public class Himeji 
{
	public static final boolean SHOW_ALL_EVENTS = true;
	private static Scanner input;
	
	public static void main(String[] args) 
	{
		input = new Scanner(System.in);
		
		Block.setBlockVisibility();
		Block.setBlockColors();
		Block.setBlockDict();
		
		File dir;
		World currentWorld;
		
		dir = worldPrompt();
		
		System.out.println(dir.getName());
		currentWorld = new World(dir);
		
		currentWorld.createRenderGrids();
		currentWorld.renderWorld();
	}
	
	/**
	 * Prompts the user for a path to a world folder. If the folder is
	 * invalid, then it will continue to re-prompt the user until a
	 * valid folder is given.
	 * @return File containing the path to a world directory
	 */
	private static File worldPrompt()
	{
		boolean validFolder = false;
		File dir;
		
		do
		{
			String path;
			path = InputPrompt.stringPrompt("Please enter the world directory: ", input);
			
			dir = new File(path);
			
			validFolder = World.validateDir(dir);
		} while (!validFolder);
		
		return dir;
	}
}
