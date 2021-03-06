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

package com.takenouchitr.himeji;

import java.io.*;
import java.util.Properties;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.takenouchitr.himeji.MCCompat.*;
import com.takenouchitr.himeji.frames.*;


@SuppressWarnings("serial")
public class Himeji extends JFrame
{
	public static final String VERSION = "1.0";
	public static final boolean SHOW_ALL_EVENTS = true;
	public static final boolean CREATE_LOG = false;
	
	public static final String SAVE_FOLDER = System.getenv("APPDATA") + "/.minecraft/saves/";
	public static final String DATA_FOLDER = "data/";
	public static final String PROPERTIES_FILE = "config.properties";
	public static final String BLOCK_COLORS_FILE = "colors.csv";
	public static final String BIOMES_FILE = "biomes.csv";
	public static final String FOLIAGE_FILE = "foliage.csv";
	public static final String FOLIAGE_COLORS_FILE = "foliage_colors.csv";
	public static final String GRASS_FILE = "grass.csv";
	public static final String GRASS_COLORS_FILE = "grass_colors.csv";
	public static final String WATER_FILE = "water.csv";
	public static final String WATER_COLORS_FILE = "water_colors.csv";
	public static final String INVISIBLE_FILE = "invis.csv";
	public static final String LEGACY_ID_FILE = "legacyIds.csv";
	
	private static Himeji frame;
	
	private static JButton btn_folder, btn_start, btn_setBounds, btn_output;
	private static JTextField txt_worldPath, txt_output;
	private static JMenuBar bar_menu;
	private static JMenuItem itm_exit, itm_colors;
	private static JPanel pnl_log;
	private static JLabel lbl_log;
	private static Properties props;
	
	private static BoundsFrame boundsFrame;
	private static ColorPickerFrame colorFrame;
	private static BiomeFrame biomeFrame;
	private static FlagsFrame flagsFrame;
	private static SettingsFrame settingsFrame;
	
	private static FileWriter writer;
	private static boolean isWriting;
	private static Object key = new Object();
	
	public static void main(String[] args) 
	{
	
		checkFiles();
		
		Block.loadFiles();
		
		File configFile = new File(DATA_FOLDER + PROPERTIES_FILE);
		props = new Properties();
		
		props = loadProperties(configFile);
		fillUnloadedProperties();
		
		Himeji window = new Himeji();
		window.setVisible(true);
	}
	
	/**
	 * Checks if files are present in the program's data folder. If one is missing, the default 
	 * file will be copied to the directory from the program's resources.
	 */
	private static void checkFiles()
	{
		File dataDir = new File(DATA_FOLDER);
		if (!dataDir.exists())
			dataDir.mkdir();
		
		File blocksFile = new File(DATA_FOLDER + BLOCK_COLORS_FILE);
		if (!blocksFile.exists())
			copyFromResource(BLOCK_COLORS_FILE);
		
		File idFile = new File(DATA_FOLDER + LEGACY_ID_FILE);
		if (!idFile.exists())
			copyFromResource(LEGACY_ID_FILE);
		
		File biomeFile = new File(DATA_FOLDER + BIOMES_FILE);
		if (!biomeFile.exists())
			copyFromResource(BIOMES_FILE);
		
		File foliageFile = new File(DATA_FOLDER + FOLIAGE_FILE);
		if (!foliageFile.exists())
			copyFromResource(FOLIAGE_FILE);
		
		File grassFile = new File(DATA_FOLDER + GRASS_FILE);
		if (!grassFile.exists())
			copyFromResource(GRASS_FILE);
		
		File waterFile = new File(DATA_FOLDER + WATER_FILE);
		if (!waterFile.exists())
			copyFromResource(WATER_FILE);
		
		File invisFile = new File(DATA_FOLDER + INVISIBLE_FILE);
		if (!invisFile.exists())
			copyFromResource(INVISIBLE_FILE);
	}
	
	/**
	 * Copies a file from the program's resources to the data folder.
	 * @param file name of the file
	 */
	private static void copyFromResource(String file)
	{
		try
		{
			InputStream input = Himeji.class.getResourceAsStream("/Resources/" + file);
			OutputStream output = new FileOutputStream(DATA_FOLDER + file);
			byte[] buffer = new byte[1024];
			
			int length = 0;
			
			while ((length = input.read(buffer)) > 0)
				output.write(buffer, 0, length);;
			
			input.close();
			output.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Sets whether or not all of the components are enabled. Used to allow the window to be 
	 * repositioned while a map is rendering.
	 * @param enabled
	 */
	public static void setComponentsEnabled(boolean enabled)
	{
		for (Component c : bar_menu.getComponents())
			c.setEnabled(enabled);
		
		for (Component c : frame.getContentPane().getComponents())
			c.setEnabled(enabled);
	}
	
	/**
	 * Gets a config from the program's properties using the key.
	 * @param key property key
	 * @return    value stored at the property key
	 */
	public static String getProperty(String key)
	{
		return props.getProperty(key);
	}
	
	/**
	 * Gets a config from the program's properties using a Property enum.
	 * @param key Property enum
	 * @return    value stored at the enum's key
	 */
	public static String getProperty(Property prop)
	{
		return getProperty(prop.key);
	}
	
	/**
	 * Gets the main program frame
	 * @return Main program frame
	 */
	public static Himeji getFrame()
	{
		return frame;
	}
	
	/**
	 * Sets the value for a Property. 
	 * @param prop  Property enum of the value to update
	 * @param value new property value
	 */
	private static void setProperty(Property prop, String value)
	{
		props.setProperty(prop.key, value);
	}
	
	/**
	 * Saves the program's Properties to file.
	 */
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
	
	/**
	 * Displays a message using the label at the bottom of the frame
	 * @param message message to display
	 */
	public static void displayMessage(String message)
	{
		lbl_log.setText(message);
		log(message);
	}
	
	/**
	 * Writes a line to the log, if it's enabled
	 * @param line line to write
	 */
	@SuppressWarnings("unused")
	public static void log(String line)
	{
		if (!CREATE_LOG)
			return;
		
		synchronized(key)
		{
			try
			{
				if (!isWriting && CREATE_LOG)
				{
					isWriting = true;
					writer = new FileWriter(new File("data/log.txt"));
				}
				
				writer.write(line + '\n');
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Creates a Properties object with the default values.
	 * @return default Properties object
	 */
	public static void fillUnloadedProperties()
	{
		for (Property p : Property.values())
		{
			if (!props.containsKey(p.key))
				props.setProperty(p.key, p.defaultValue);
		}
	}
	
	/**
	 * Loads a Properties object from file.
	 * @param configFile .properties file
	 * @return           loaded Properties object
	 */
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
		} 
		catch (IOException e) 
		{
			System.out.println("IO Exception");
			e.printStackTrace();
		}
		
		return loadedProp;
	}

	private Himeji()
	{
		super("Himeji Map Viewer");
		setResizable(false);
		frame = this;
		setIconImage(Toolkit.getDefaultToolkit().getImage(Himeji.class.getResource("/Resources/Icons/64_icon.png")));
		setLocationRelativeTo(null);
		
		colorFrame = new ColorPickerFrame();
		boundsFrame = new BoundsFrame(props, this);
		biomeFrame = new BiomeFrame(this);
		
		//construct pre-components
        JMenu fileMenu = new JMenu("File");
        JMenu menu_config = new JMenu("Config");
        bar_menu = new JMenuBar();
        bar_menu.add(fileMenu);
        
        itm_exit = new JMenuItem("Exit");
        
        JMenuItem itm_settings = new JMenuItem("Settings");
        fileMenu.add(itm_settings);
        fileMenu.add(itm_exit);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));
        getContentPane().add(bar_menu, BorderLayout.NORTH);
        
        bar_menu.add(menu_config);
        
        itm_colors = new JMenuItem("Block Colors");
        menu_config.add(menu_config.add(itm_colors));
        
        JMenuItem itm_blockFlags = new JMenuItem("Block Flags");
        menu_config.add(itm_blockFlags);
        
        JMenuItem itm_biomeColors = new JMenuItem("Biome Colors");
        menu_config.add(itm_biomeColors);
        
        JMenuItem itm_setBounds = new JMenuItem("Set Bounds");
        menu_config.add(itm_setBounds);
        
        JMenu mnAdd = new JMenu("Add");
        bar_menu.add(mnAdd);
        
        JMenuItem itm_blockID = new JMenuItem("New Block ID");
        mnAdd.add(itm_blockID);
        
        JMenuItem itm_biome = new JMenuItem("New Biome");
        mnAdd.add(itm_biome);
        
        pnl_log = new JPanel();
        pnl_log.setBackground(SystemColor.activeCaptionBorder);
        pnl_log.setBorder(new LineBorder(new Color(0, 0, 0)));
        FlowLayout fl_pnl_log = (FlowLayout) pnl_log.getLayout();
        fl_pnl_log.setVgap(0);
        fl_pnl_log.setAlignment(FlowLayout.LEFT);
        getContentPane().add(pnl_log, BorderLayout.SOUTH);
        
        lbl_log = new JLabel("Welcome!");
        pnl_log.add(lbl_log);
        
        JPanel pnl_main = new JPanel();
        getContentPane().add(pnl_main);
        pnl_main.setLayout(null);
        
        btn_output = new JButton("Output");
        btn_output.setBounds(14, 47, 80, 25);
        pnl_main.add(btn_output);
        btn_folder = new JButton("World");
        btn_folder.setBounds(14, 11, 80, 25);
        pnl_main.add(btn_folder);
        txt_worldPath = new JTextField(400);
        txt_worldPath.setBounds(104, 11, 370, 25);
        pnl_main.add(txt_worldPath);
        
        txt_output = new JTextField();
        txt_output.setBounds(104, 47, 370, 25);
        pnl_main.add(txt_output);
        txt_output.setColumns(10);
        
        //construct components
        btn_setBounds = new JButton("Set Bounds");
        btn_setBounds.setBounds(292, 83, 107, 23);
        pnl_main.add(btn_setBounds);
        
        btn_start = new JButton("Start");
        btn_start.setBounds(409, 83, 65, 23);
        pnl_main.add(btn_start);
        
        //set listeners
        btn_start.addActionListener((e) ->       startRender());
        btn_setBounds.addActionListener((e) ->   openBoundsFrame());
        btn_folder.addActionListener((e) ->      folderPress());
        btn_output.addActionListener((e) ->      outputPress());
        itm_colors.addActionListener((e) ->      openColorPicker());
        itm_blockID.addActionListener((e) ->     openAddBlockFrame());
        itm_biomeColors.addActionListener((e) -> openBiomeFrame());
        itm_blockFlags.addActionListener((e) ->  openFlagsFrame());
        itm_settings.addActionListener((e) ->    openSettingsFrame());
        itm_biome.addActionListener((e) ->       openAddBiomeFrame());
        itm_exit.addActionListener((e) ->        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        itm_setBounds.addActionListener((e) ->   openBoundsFrame());
        
        this.addWindowListener(new WindowListener() 
        {

        	@Override
			public void windowActivated(WindowEvent arg0){}
		
			@Override
			public void windowClosed(WindowEvent arg0) {}
		
			@Override
			public void windowClosing(WindowEvent e)
			{
				setProperty(Property.WORLD_PATH, txt_worldPath.getText());
				setProperty(Property.OUTPUT_PATH, txt_output.getText());
				
				saveProperties();
				
				if (isWriting)
				{
					try
					{
						writer.close();
					} 
					catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		
			@Override
			public void windowDeactivated(WindowEvent e) 
			{
				
			}
		
			@Override
			public void windowDeiconified(WindowEvent arg0){}
		
			@Override
			public void windowIconified(WindowEvent arg0){}
		
			@Override
			public void windowOpened(WindowEvent arg0){}
		
        });
		   
        applyProperties();
        
        setSize(500, 192);
        setLocation(getX() - getWidth() / 2, getY() - getHeight() / 2);
	}
	
	/**
	 * Applies the values of the program's properties to the form components.
	 */
	private void applyProperties()
	{
		txt_worldPath.setText(getProperty(Property.WORLD_PATH));
		txt_output.setText(getProperty(Property.OUTPUT_PATH));
		
		String worldPath = props.getProperty(Property.LAST_WORLD_FOLDER.key);
		String outputPath = props.getProperty(Property.LAST_OUTPUT_FOLDER.key);
		
		if (worldPath.trim().length() == 0)
			props.setProperty(Property.LAST_WORLD_FOLDER.key, SAVE_FOLDER);
		
		if (outputPath.trim().length() == 0)
			props.setProperty(Property.LAST_OUTPUT_FOLDER.key, SAVE_FOLDER);
	}
	
	private void openColorPicker()
	{
		if (colorFrame == null)
			colorFrame = new ColorPickerFrame();
		
		colorFrame.setLocationRelativeTo(this);
		colorFrame.setVisible(true);
	}
	
	private void openBiomeFrame()
	{
		if (biomeFrame == null)
			biomeFrame = new BiomeFrame(this);
		
		biomeFrame.setLocationRelativeTo(this);
		biomeFrame.setVisible(true);
	}
	
	private void openBoundsFrame()
	{
		if (boundsFrame == null)
			boundsFrame = new BoundsFrame(props, this);
		
		boundsFrame.setLocationRelativeTo(this);
		boundsFrame.setVisible(true);
	}
	
	private void openFlagsFrame()
	{
		if (flagsFrame == null)
			flagsFrame = new FlagsFrame();
		
		flagsFrame.setLocationRelativeTo(this);
		flagsFrame.setVisible(true);
	}

	private void openSettingsFrame()
	{
		if (settingsFrame == null)
			settingsFrame = new SettingsFrame(props);
		
		settingsFrame.applyProperties();
		settingsFrame.setLocationRelativeTo(this);
		settingsFrame.setVisible(true);
	}

	private void openAddBiomeFrame()
	{
		AddBiomeFrame abf = new AddBiomeFrame();
		
		abf.setLocationRelativeTo(this);
		abf.setVisible(true);
	}

	private void openAddBlockFrame()
	{
		AddBlockFrame abf = new AddBlockFrame();
		
		abf.setLocationRelativeTo(this);
		abf.setVisible(true);
	}
	
	private void folderPress()
	{
		JFileChooser fileChooser = new JFileChooser(props.getProperty(Property.LAST_WORLD_FOLDER.key));
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			txt_worldPath.setText(fileChooser.getSelectedFile().getPath());
			
			File folder = new File(fileChooser.getSelectedFile().getPath());
			
			if (props.getProperty(Property.WORLD_SETTING.key).equals("last"))
				props.setProperty(Property.LAST_WORLD_FOLDER.key, folder.getParent());
		}
	}
	
	private void outputPress()
	{
		JFileChooser fileChooser = new JFileChooser(props.getProperty(Property.LAST_OUTPUT_FOLDER.key));
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image (.png)", "png", "PNG"));
		
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			String path = file.getPath();
			
			if (!path.endsWith(".png"))
				path += ".png";
			
			txt_output.setText(path);
			
			if (props.getProperty(Property.OUTPUT_SETTING.key).equals("last"))
				props.setProperty(Property.LAST_OUTPUT_FOLDER.key, file.getParent());
		}
	}
	
	private void startRender()
	{
		//Validates that the target folder is a world folder
		if (!World.validateDir(new File(txt_worldPath.getText())))
		{
			JOptionPane.showMessageDialog(null,
				    "World folder was not detected. Please check that you have selected the root\n" +
				    "folder for the world (.../.minecraft/saves/<world name>) and try again.",
				    "World Load Warning",
				    JOptionPane.WARNING_MESSAGE);
			return;
		}
		//Checks the output file to make sure that it isn't empty
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
			
			//Sets the temporary properties for the render session
			SessionProperties.setNightBrightness(
				Integer.parseInt(props.getProperty(Property.NIGHT_BRIGHTNESS.key)) / 100f);
			SessionProperties.setBiomeIntensity( 
				Integer.parseInt(props.getProperty(Property.BIOME_INTENSITY.key)) / 100f);
			SessionProperties.setWaterTransparency(
				Integer.parseInt(props.getProperty(Property.WATER_TRANSPARENCY.key)) / 100f);
			SessionProperties.setShadowIntensity( 
				Integer.parseInt(props.getProperty(Property.SHADOW_INTENSITY.key)) / 100f);
			SessionProperties.setHighlightIntensity( 
				Integer.parseInt(props.getProperty(Property.HIGHLIGHT_INTENSITY.key)) / 100f);
			
			SessionProperties.setRenderLight( 
				Boolean.parseBoolean(props.getProperty(Property.RENDER_LIGHT.key)));
			SessionProperties.setRenderBiomes( 
				Boolean.parseBoolean(props.getProperty(Property.RENDER_BIOME_COLORS.key)));
			SessionProperties.setRenderUnderWater( 
				Boolean.parseBoolean(props.getProperty(Property.RENDER_UNDER_WATER.key)));
			SessionProperties.setRenderShadows( 
				Boolean.parseBoolean(props.getProperty(Property.RENDER_SHADOWS.key)));
			SessionProperties.setUseDefaultColor(
					props.getProperty(Property.MISSING_BLOCK_SETTING.key).equals("color"));
			
			SessionProperties.setDefaultBiome(Block.getBiomeID(props.getProperty(Property.DEFAULT_BIOME.key)));
			
			setComponentsEnabled(false);
			worker.execute();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
