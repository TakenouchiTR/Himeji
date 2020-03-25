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

package com.github.takenouchitr;

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
