package com.github.takenouchitr;
//Program Name:   MapImage.java
//Date:           3/13/2020
//Programmer:     Shawn Carter
//Description:    This class renders dimensions as .png images.

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class MapImage 
{
	private File file;
	private BufferedImage output;
	
	public MapImage(int width, int height, File outputFile)
	{
		this.file = outputFile;
		output = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	}
	
	/**
	 * Sets the pixel of the image to a specified color.
	 * @param x     x coordinate of the pixel
	 * @param y     y coordinate of the pixel
	 * @param color ARGB int of the new color
	 */
	public void setPixel(int x, int y, int color)
	{
		output.setRGB(x, y, color);
	}
	
	/**
	 * Saves the image to file.
	 */
	public void renderImage()
	{
		try
		{
			ImageIO.write(output, "png", file);
		}
		catch (Exception e) {}
	}
	
}
