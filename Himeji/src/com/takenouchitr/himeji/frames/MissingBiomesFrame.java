package com.takenouchitr.himeji.frames;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import com.takenouchitr.himeji.MCCompat.Block;
import com.takenouchitr.himeji.MCCompat.Chunk;

@SuppressWarnings("serial")
public class MissingBiomesFrame extends JDialog
{
	public MissingBiomesFrame()
	{
		setTitle("Missing Biomes List");
		setResizable(false);
		setModal(true);
		setSize(350, 350);
		
		this.setModal(true);
		
		JLabel lbl_info = new JLabel("<html><body>Please write down the list of biome IDs "
				+ "and the first coordinate it was found at.</body></html>");
		
		JScrollPane scr_pane = new JScrollPane();
		
		getContentPane().add(lbl_info, BorderLayout.NORTH);
		getContentPane().add(scr_pane, BorderLayout.CENTER);
		
		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> lst_info = new JList<>(model);
		lst_info.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scr_pane.setViewportView(lst_info);
		
		HashMap<Integer, int[]> unknownBiomes = Block.getUnknownBiomes();
		
		for (Integer i : unknownBiomes.keySet())
		{
			int[] coords = unknownBiomes.get(i);
			String line = String.format("ID - %1$d; Coords - x:%2$d, y:%3$d, z:%4$d; Chunk: x:%5$d,z:%6$d", 
					i, coords[0], coords[1], coords[2], coords[0] / Chunk.CHUNK_SIZE, 
					coords[2] / Chunk.CHUNK_SIZE);
			
			model.addElement(line);
		}
	}
}
