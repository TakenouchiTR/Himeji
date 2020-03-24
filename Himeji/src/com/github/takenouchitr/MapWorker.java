//Date:           3/24/2020
//Me:             Shawn Carter

package com.github.takenouchitr;

import java.io.File;
import java.util.List;

import javax.swing.SwingWorker;

public class MapWorker extends SwingWorker<Void, String>
{
	@Override
	protected Void doInBackground() throws Exception 
	{
		File file = new File(Himeji.getPath());
		if (World.validateDir(file)) 
		{
			long start = System.currentTimeMillis();
			
			BoundsFrame boundsFrame = Himeji.getBoundsFrame();
			
			Dimension dim;
			File dimFile;
			
			// TODO Check for whether the dimension exists
			String dimensionName = boundsFrame.getDimensionName();
			switch(dimensionName)
			{
				case "Overworld":
					dimFile = new File(file.getPath() + "\\region");
					break;
				case "Nether":
					dimFile = new File(file.getPath() + "\\DIM-1\\region");
					break;
				case "End":
					dimFile = new File(file.getPath() + "\\DIM1\\region");
					break;
				default:
					dimFile = new File("");
			}
			
			int startX = Himeji.getMaxX();
			int startY = Himeji.getStartY();
			int startZ = Himeji.getMaxZ();
			int endX = Himeji.getMinX();
			int endY = Himeji.getEndY();
			int endZ = Himeji.getMinZ();
			
			publish("Getting area size...");
			if (boundsFrame.getRenderBounds())
				dim = new Dimension(dimFile, startX, endX, startZ, endZ);
			else
				dim = new Dimension(dimFile);
			
			dim.createRenderGrid();
			publish("Getting blocks...");
			if (boundsFrame.getRenderBounds())
				dim.drawBlocksToBuffer(startY, endY, startX, endX, startZ, endZ);
			else
				dim.drawBlocksToBuffer(startY, endY);
			
			publish("Rendering image...");
			dim.render();
			
			long end = System.currentTimeMillis();
			int seconds = (int)(end - start) / 1000;
			String elapsed = String.format("Time elapsed:  %1$02d:%2$02d;%3$03d", 
				seconds / 60, seconds % 60, (end - start) % 1000);
			
			publish(elapsed);
			
			done();
		}
		return null;
	}
	
	@Override
	protected void process(List<String> chunks)
	{
		Himeji.displayMessage(chunks.get(chunks.size() - 1));
	}
	
	@Override
	protected void done()
	{
		Himeji.enableComponents();
	}
}
