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

import java.io.File;
import java.awt.event.*;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


@SuppressWarnings("serial")
public class Himeji extends JFrame implements ActionListener, ItemListener, WindowListener
{
	public static final boolean SHOW_ALL_EVENTS = true;
	public static final String SAVE_FOLDER = System.getenv("APPDATA") + "\\.minecraft\\saves\\";
	
	private static JButton btn_folder, btn_start, btn_setBounds, btn_output;
	private static JTextField txt_worldPath, txt_output;
	private static JCheckBox chk_renderUnderWater, chk_renderShadows, chk_renderBiomes;
	private static JMenuBar bar_menu;
	private static BoundsFrame boundsFrame;
	private static JPanel pnl_log;
	private static JLabel lbl_log;
	
	public static void main(String[] args) 
	{
		System.out.println(SAVE_FOLDER);
		
		Block.setBlockVisibility();
		Block.setBlockColors();
		Block.setBlockDict();
		Block.setBiomeColors();
		Block.setBiomeFoliage();
		Block.setBiomeWater();
		Block.setBiomeGrass();
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run() 
			{
				Himeji window = new Himeji();
				window.setVisible(true);
			}
		});
	}
	
	public static int getStartY()
	{
		return boundsFrame.getMaxY();
	}
	
	public static int getEndY()
	{
		return boundsFrame.getMinY();
	}
	
	public static int getMaxX()
	{
		return boundsFrame.getMaxX();
	}
	
	public static int getMinX()
	{
		return boundsFrame.getMinX();
	}
	
	public static int getMaxZ()
	{
		return boundsFrame.getMaxZ();
	}
	
	public static int getMinZ()
	{
		return boundsFrame.getMinZ();
	}
	
	public static void disableComponents()
	{
		btn_start.setEnabled(false);
		btn_folder.setEnabled(false);
		btn_setBounds.setEnabled(false);
		btn_output.setEnabled(false);
		chk_renderUnderWater.setEnabled(false);
		chk_renderShadows.setEnabled(false);
		chk_renderBiomes.setEnabled(false);
		txt_worldPath.setEnabled(false);
		txt_output.setEnabled(false);
	}
	
	public static void enableComponents()
	{
		btn_start.setEnabled(true);
		btn_folder.setEnabled(true);
		btn_setBounds.setEnabled(true);
		btn_output.setEnabled(true);
		chk_renderUnderWater.setEnabled(true);
		chk_renderShadows.setEnabled(true);
		chk_renderBiomes.setEnabled(true);
		txt_worldPath.setEnabled(true);
		txt_output.setEnabled(true);
	}
	
 	public static BoundsFrame getBoundsFrame()
	{
		return boundsFrame;
	}
	
	public static String getPath()
	{
		return txt_worldPath.getText();
	}
	
	public static String getSaveLocation()
	{
		return txt_output.getText();
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
	
	public static void displayMessage(String message)
	{
		lbl_log.setText(message);
	}
	
	public Himeji()
	{
		super("Himeji Map Viewer");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Himeji.class.getResource("/Resources/64_icon.png")));
		
		setResizable(false);
		setLocationRelativeTo(null);
		
		boundsFrame = new BoundsFrame();
		boundsFrame.addWindowListener(this);
		
		//construct preComponents
        JMenu fileMenu = new JMenu("File");

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
        btn_start = new JButton("Start");

        //adjust size and set layout
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
        chk_renderUnderWater.setBounds(15, 108, 181, 25);
        chk_renderShadows.setBounds(15, 133, 181, 25);
        chk_renderBiomes.setBounds(15, 158, 181, 20);
        bar_menu.setBounds(0, 0, 495, 25);
        btn_start.setBounds(415, 153, 65, 25);
        
        btn_setBounds = new JButton("Set Bounds");
        btn_setBounds.addActionListener(this);
        btn_setBounds.setBounds(302, 154, 107, 23);
        getContentPane().add(btn_setBounds);
        
        btn_output = new JButton("Output");
        btn_output.setBounds(15, 72, 80, 25);
        getContentPane().add(btn_output);
        
        txt_output = new JTextField();
        txt_output.setBounds(105, 73, 370, 25);
        getContentPane().add(txt_output);
        txt_output.setColumns(10);
        
        pnl_log = new JPanel();
        pnl_log.setBackground(SystemColor.activeCaptionBorder);
        pnl_log.setBorder(new LineBorder(new Color(0, 0, 0)));
        FlowLayout fl_pnl_log = (FlowLayout) pnl_log.getLayout();
        fl_pnl_log.setVgap(0);
        fl_pnl_log.setAlignment(FlowLayout.LEFT);
        pnl_log.setBounds(-1, 187, 496, 17);
        getContentPane().add(pnl_log);
        
        lbl_log = new JLabel("Welcome!");
        pnl_log.add(lbl_log);
        
        //set listeners
        btn_folder.addActionListener(this);
        btn_output.addActionListener(this);
        btn_start.addActionListener(this);
        
        setSize(500, pnl_log.getY() + pnl_log.getHeight() + bar_menu.getHeight() + 12);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if (source == btn_folder)
		{
			JFileChooser fileChooser = new JFileChooser(SAVE_FOLDER);
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				txt_worldPath.setText(fileChooser.getSelectedFile().getPath());
			}
		}
		else if (source == btn_output)
		{
			JFileChooser fileChooser = new JFileChooser(SAVE_FOLDER);
			fileChooser.setFileFilter(new FileNameExtensionFilter("Image (.png)", "png", "PNG"));
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				File file = fileChooser.getSelectedFile();
				String path = file.getPath();
				txt_output.setText(path);
			}
		}
		else if (source == btn_start)
		{
			if (!World.validateDir(new File(txt_worldPath.getText())))
			{
				JOptionPane.showMessageDialog(null,
					    "World folder was not detected. Please check that you have selected the root\n" +
					    "folder for the world (.../.minecraft/saves/<world name>) and try again.",
					    "World Load Warning",
					    JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (txt_output.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null,
					    "File output is blank. Please fill in the field before creating an image.",
					    "Output Warning",
					    JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			MapWorker worker = new MapWorker();
			try
			{
				disableComponents();
				worker.execute();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{
			setEnabled(false);
			boundsFrame.setLocationRelativeTo(this);
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
