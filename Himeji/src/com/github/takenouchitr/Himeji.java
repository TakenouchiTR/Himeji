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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Properties;
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
	public static final String SAVE_FOLDER = System.getenv("APPDATA") + "/.minecraft/saves/";
	public static final String DATA_FOLDER = "data/";
	public static final String PROPERTIES_FILE = "config.properties";
	public static final String BLOCK_COLORS_FILE = "colors.csv";
	public static final String NAMESPACED_ID_FILE = "namespace.csv";
	public static final String FOLIAGE_FILE = "foliage.csv";
	public static final String FOLIAGE_COLORS_FILE = "foliage_colors.csv";
	public static final String GRASS_FILE = "grass.csv";
	public static final String GRASS_COLORS_FILE = "grass_colors.csv";
	public static final String WATER_FILE = "water.csv";
	public static final String WATER_COLORS_FILE = "water_colors.csv";
	public static final String INVISIBLE_FILE = "invis.csv";
	
	private static JButton btn_folder, btn_start, btn_setBounds, btn_output;
	private static JTextField txt_worldPath, txt_output;
	private static JCheckBox chk_renderUnderWater, chk_renderShadows, chk_renderBiomes;
	private static JMenuBar bar_menu;
	private static BoundsFrame boundsFrame;
	private static JPanel pnl_log;
	private static JLabel lbl_log;
	private static Properties props;
	
	public static void main(String[] args) 
	{
		checkFiles();
		
		Block.loadFiles();
		
		File configFile = new File(DATA_FOLDER + PROPERTIES_FILE);
		props = new Properties();
		
		if (!configFile.exists())
			props = createDefaultProperties();
		else
			props = loadProperties(configFile);
		
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
	

	private static void checkFiles()
	{
		File dataDir = new File(DATA_FOLDER);
		if (!dataDir.exists())
			dataDir.mkdir();
		
		File blocksFile = new File(DATA_FOLDER + BLOCK_COLORS_FILE);
		if (!blocksFile.exists())
			copyFromResource(BLOCK_COLORS_FILE);
		
		File idFile = new File(DATA_FOLDER + NAMESPACED_ID_FILE);
		if (!idFile.exists())
			copyFromResource(NAMESPACED_ID_FILE);
		
		File foliageFile = new File(DATA_FOLDER + FOLIAGE_FILE);
		if (!foliageFile.exists())
			copyFromResource(FOLIAGE_FILE);
		
		File foliageColors = new File(DATA_FOLDER + FOLIAGE_COLORS_FILE);
		if (!foliageColors.exists())
			copyFromResource(FOLIAGE_COLORS_FILE);
		
		File grassFile = new File(DATA_FOLDER + GRASS_FILE);
		if (!grassFile.exists())
			copyFromResource(GRASS_FILE);
		
		File grassColors = new File(DATA_FOLDER + GRASS_COLORS_FILE);
		if (!grassColors.exists())
			copyFromResource(GRASS_COLORS_FILE);
		
		File waterFile = new File(DATA_FOLDER + WATER_FILE);
		if (!waterFile.exists())
			copyFromResource(WATER_FILE);
		
		File waterColors = new File(DATA_FOLDER + WATER_COLORS_FILE);
		if (!waterColors.exists())
			copyFromResource(WATER_COLORS_FILE);
		
		File invisFile = new File(DATA_FOLDER + INVISIBLE_FILE);
		if (!invisFile.exists())
			copyFromResource(INVISIBLE_FILE);
	}
	
	private static void copyFromResource(String file)
	{
		try
		{
			File destination = new File(DATA_FOLDER + file);
			
			String sourceString = Himeji.class.getResource("/Resources/" + file).getFile();
				
			File source = new File(sourceString);
			Files.copy(source.toPath(), destination.toPath());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
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
	
	public static String getProperty(String key)
	{
		return props.getProperty(key);
	}
	
	public static String getProperty(Property prop)
	{
		return getProperty(prop.key);
	}
	
	
	private static void setProperty(Property prop, String value)
	{
		props.setProperty(prop.key, value);
	}
	
	private static void saveProperties()
	{
		File configFile = new File(DATA_FOLDER + PROPERTIES_FILE);
		try
		{
			FileWriter writer = new FileWriter(configFile);
			props.store(writer, "config");
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void displayMessage(String message)
	{
		lbl_log.setText(message);
	}
	
	public static Properties createDefaultProperties()
	{
		Properties defaultProp = new Properties();
		
		defaultProp.setProperty("start_y", "255");
		defaultProp.setProperty("end_y", "0");
		defaultProp.setProperty("start_x", "0");
		defaultProp.setProperty("end_x", "0");
		defaultProp.setProperty("start_z", "0");
		defaultProp.setProperty("end_z", "0");
		defaultProp.setProperty("dimension", "Overworld");
		defaultProp.setProperty("use_area", "false");
		defaultProp.setProperty("render_under_water", "true");
		defaultProp.setProperty("render_shadows", "true");
		defaultProp.setProperty("render_biome_colors", "true");
		defaultProp.setProperty("world_path", "");
		defaultProp.setProperty("output_path", "");
		
		return defaultProp;
	}
	
	public static Properties loadProperties(File configFile)
	{
		Properties loadedProp = new Properties();
		
		try
		{
			FileReader reader = new FileReader(configFile);
			loadedProp.load(reader);
		} 
		catch (FileNotFoundException e) 
		{
		    System.out.println("File does not exist. Loading default properties.");
		    loadedProp = createDefaultProperties();
		} 
		catch (IOException e) 
		{
			System.out.println("IO Exception");
			e.printStackTrace();
		}
		
		return loadedProp;
	}
	
	public Himeji()
	{
		super("Himeji Map Viewer");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Himeji.class.getResource("/Resources/Icons/64_icon.png")));
		
		setResizable(false);
		setLocationRelativeTo(null);
		
		boundsFrame = new BoundsFrame(props);
		boundsFrame.addWindowListener(this);
		
		//construct preComponents
        JMenu fileMenu = new JMenu("File");

        //construct components
        btn_folder = new JButton("World");
        txt_worldPath = new JTextField(400);
        chk_renderUnderWater = new JCheckBox("Render Underwater Blocks");
        chk_renderUnderWater.setToolTipText("Show blocks that are under water. Blocks will be tinted by the water's color.");
        chk_renderUnderWater.setSelected(true);
        chk_renderShadows = new JCheckBox("Render Shadows");
        chk_renderShadows.setToolTipText("Add shadows to show changes in elevation.");
        chk_renderShadows.setSelected(true);
        chk_renderBiomes = new JCheckBox("Render Biome Colors");
        chk_renderBiomes.setToolTipText("Change the colors of grass, foliage, and water to match the biome they are in.");
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
        chk_renderUnderWater.addItemListener(this);
        chk_renderShadows.addItemListener(this);
        chk_renderBiomes.addItemListener(this);
        
        addWindowListener(this);
        
        applyProperties();
        
        setSize(500, pnl_log.getY() + pnl_log.getHeight() + bar_menu.getHeight() + 3);
	}
	
	private void applyProperties()
	{
		txt_worldPath.setText(getProperty(Property.WORLD_PATH));
		txt_output.setText(getProperty(Property.OUTPUT_PATH));
		chk_renderUnderWater.setSelected(Boolean.parseBoolean(getProperty(Property.RENDER_UNDER_WATER)));
		chk_renderShadows.setSelected(Boolean.parseBoolean(getProperty(Property.RENDER_SHADOWS)));
		chk_renderBiomes.setSelected(Boolean.parseBoolean(getProperty(Property.RENDER_BIOME_COLORS)));
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
				setProperty(Property.WORLD_PATH, txt_worldPath.getText());
				setProperty(Property.OUTPUT_PATH, txt_output.getText());
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
	public void itemStateChanged(ItemEvent e) 
	{
		Object source = e.getSource();
		
		if (chk_renderUnderWater == source)
			setProperty(Property.RENDER_UNDER_WATER, "" + chk_renderUnderWater.isSelected());
		if (chk_renderShadows == source)
			setProperty(Property.RENDER_SHADOWS, "" + chk_renderShadows.isSelected());
		if (chk_renderBiomes == source)
			setProperty(Property.RENDER_BIOME_COLORS, "" + chk_renderBiomes.isSelected());
	}
	
@Override
	public void windowActivated(WindowEvent arg0){}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent e)
	{
		Object source = e.getSource();
		if (source == this)
		{
			setProperty(Property.WORLD_PATH, txt_worldPath.getText());
			setProperty(Property.OUTPUT_PATH, txt_output.getText());
			saveProperties();
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) 
	{
		Object source = e.getSource();
		if (source == boundsFrame)
		{
			setEnabled(true);
			this.toFront();
		}
	}

	@Override
	public void windowDeiconified(WindowEvent arg0){}

	@Override
	public void windowIconified(WindowEvent arg0){}

	@Override
	public void windowOpened(WindowEvent arg0){}
}
