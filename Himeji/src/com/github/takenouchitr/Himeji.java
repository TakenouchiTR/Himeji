package com.github.takenouchitr;
//Program Name:   Himeji.java
//Date:           3/13/2020
//Programmer:     Shawn Carter
//Description:    This program creates a map image of a Minecraft world.

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

@SuppressWarnings("serial")
public class Himeji extends JFrame implements ActionListener, ItemListener, WindowListener
{
	public static final boolean SHOW_ALL_EVENTS = true;
	
	private static final int MIN_VALUE = Integer.MIN_VALUE + 32;
	private static final int MAX_VALUE = Integer.MAX_VALUE - 32;
	
	private static JButton btn_folder, btn_start;
	private static JTextField txt_worldPath;
	private static JCheckBox chk_renderUnderWater, chk_renderShadows, chk_renderBiomes;
	private static JMenuBar bar_menu;
	private static JButton btn_setBounds;
	private static BoundsFrame boundsFrame;
	
	public static void main(String[] args) 
	{
		Block.setBlockVisibility();
		Block.setBlockColors();
		Block.setBlockDict();
		Block.setBiomeColors();
		Block.setBiomeFoliage();
		Block.setBiomeWater();
		Block.setBiomeGrass();
		
		Himeji window = new Himeji();
		window.setVisible(true);
	}
	
	public static int getStartY()
	{
		return boundsFrame.getMaxY();
	}
	
	public static int getEndY()
	{
		return boundsFrame.getMinY();
	}
	
	public static boolean renderUnderWater()
	{
		return chk_renderUnderWater.isSelected();
	}
	
	public static boolean renderShadows()
	{
		return chk_renderShadows.isSelected();
	}
	
	public static boolean renderBiomeColors()
	{
		return chk_renderBiomes.isSelected();
	}
	
	public Himeji()
	{
		super("Himeji Map Viewer");
		
		boundsFrame = new BoundsFrame();
		boundsFrame.addWindowListener(this);
		setResizable(false);
		
		//construct preComponents
        JMenu fileMenu = new JMenu("File");
        JMenuItem printItem = new JMenuItem("Print");
        fileMenu.add(printItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        JMenu helpMenu = new JMenu("Help");
        JMenuItem contentsItem = new JMenuItem("Contents");
        helpMenu.add(contentsItem);
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);

        //construct components
        btn_folder = new JButton("World");
        txt_worldPath = new JTextField(400);
        chk_renderUnderWater = new JCheckBox("Render Underwater Blocks");
        chk_renderUnderWater.setSelected(true);
        chk_renderShadows = new JCheckBox("Render Shadows");
        chk_renderShadows.setSelected(true);
        chk_renderBiomes = new JCheckBox("Render Biome Colors");
        chk_renderBiomes.setSelected(true);
        bar_menu = new JMenuBar();
        bar_menu.add(fileMenu);
        bar_menu.add(helpMenu);
        btn_start = new JButton("Start");

        //adjust size and set layout
        setSize(500, 180);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add components
        getContentPane().add(btn_folder);
        getContentPane().add(txt_worldPath);
        getContentPane().add(chk_renderUnderWater);
        getContentPane().add(chk_renderShadows);
        getContentPane().add(chk_renderBiomes);
        getContentPane().add(bar_menu);
        getContentPane().add(btn_start);

        //set component bounds (only needed by Absolute Positioning)
        btn_folder.setBounds(15, 35, 80, 25);
        txt_worldPath.setBounds(105, 35, 370, 25);
        chk_renderUnderWater.setBounds(10, 65, 181, 25);
        chk_renderShadows.setBounds(10, 90, 181, 25);
        chk_renderBiomes.setBounds(10, 115, 181, 20);
        bar_menu.setBounds(0, 0, 495, 25);
        btn_start.setBounds(410, 110, 65, 25);
        
        btn_setBounds = new JButton("Set Bounds");
        btn_setBounds.addActionListener(this);
        btn_setBounds.setBounds(297, 111, 107, 23);
        getContentPane().add(btn_setBounds);
        
        //set listeners
        btn_folder.addActionListener(this);
        btn_start.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if (source == btn_folder)
		{
			
		}
		else if (source == btn_start)
		{
			File file = new File(txt_worldPath.getText());
			if (World.validateDir(file)) 
			{
				long start = System.currentTimeMillis();
				
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
				
				int startX = boundsFrame.getMaxX();
				int startY = boundsFrame.getMaxY();
				int startZ = boundsFrame.getMaxZ();
				int endX = boundsFrame.getMinX();
				int endY = boundsFrame.getMinY();
				int endZ = boundsFrame.getMinZ();
				
				if (boundsFrame.getRenderBounds())
					dim = new Dimension(dimFile, startX, endX, startZ, endZ);
				else
					dim = new Dimension(dimFile);
				
				dim.createRenderGrid();
				if (boundsFrame.getRenderBounds())
					dim.drawBlocksToBuffer(startY, endY, startX, endX, startZ, endZ);
				else
					dim.drawBlocksToBuffer(startY, endY);
				dim.render();
				
				long end = System.currentTimeMillis();
				
				System.out.println(end - start);
			}
		}
		else
		{
			setEnabled(false);
			boundsFrame.setVisible(true);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) 
	{
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) 
	{
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		setEnabled(true);
		this.toFront();
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
