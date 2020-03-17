//Himeji Name:   MapImage.java
//Date:           3/13/2020
//Himejimer:     Shawn Carter
//Description:    This class renders dimensions as .png images.

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

import javax.imageio.ImageIO;

public class MapImage 
{
	public static void renderDim(Dimension dim)
	{
		int width = dim.getBlockWidth();
		int height = dim.getBlockHeight();
		
		BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		
		File outputFile = new File("C:\\Users\\TOTak\\AppData\\Roaming\\.minecraft\\saves\\ping.png");
		
		Date start = new Date();
		
		for (int x = 0; x < width; x++)
		{
			for (int z = 0; z < height; z++)
			{
				if (!dim.getChunkRenderFlag(x / 16, z / 16))
					continue;
				
				int rgb = dim.getBlockColorAt(x, z);
				
				if (rgb != 0)
					output.setRGB(x, z, rgb);
			}
			
			if (Himeji.SHOW_ALL_EVENTS)
				System.out.println("rendering block row " + x);
		}
		
		try
		{
			ImageIO.write(output, "png", outputFile);
		}
		catch (Exception e) {}
		
		Date end = new Date();
		
		System.out.println((end.getTime() - start.getTime()) + "");
		
	}
}
