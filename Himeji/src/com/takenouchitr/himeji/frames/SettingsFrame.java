package com.takenouchitr.himeji.frames;

import javax.swing.JSlider;

import com.takenouchitr.himeji.Himeji;
import com.takenouchitr.himeji.ListBiome;
import com.takenouchitr.himeji.Property;
import com.takenouchitr.himeji.MCCompat.Block;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class SettingsFrame extends JDialog
{
	private boolean lockShadows;
	
	private Properties props;
	private JSlider sld_brightness;
	private JSlider sld_biome;
	private JSlider sld_water;
	private JSlider sld_shadow;
	private JSlider sld_highlight;
	private JCheckBox chk_night;
	private JCheckBox chk_biome;
	private JCheckBox chk_water;
	private JCheckBox chk_shadow;
	private JCheckBox chk_highlightLock;
	private JCheckBox chk_missingBlock;
	private JCheckBox chk_unknownBiome;
	private JRadioButton rad_worldLast;
	private JRadioButton rad_worldDefault;
	private JRadioButton rad_worldOther;
	private JRadioButton rad_outputLast;
	private JRadioButton rad_outputDefault;
	private JRadioButton rad_outputOther;
	private JRadioButton rad_imageIgnore;
	private JRadioButton rad_imageAsk;
	private JRadioButton rad_imageAlways;
	private JRadioButton rad_imageFolderAsk;
	private JRadioButton rad_imageFolderAlways;
	private JRadioButton rad_blockColor;
	private JRadioButton rad_blockIgnore;
	private JTextField txt_world;
	private JTextField txt_output;
	private JLabel lbl_defaultBiome;
	private JComboBox<ListBiome> com_biomeNames; 
	private JCheckBox chk_saveBlock;
	private JCheckBox chk_removeBlock;
	private JCheckBox chk_saveBiome;
	private JCheckBox chk_removeBiome;
	
	public SettingsFrame(Properties props) 
	{
		this.props = props;
		lockShadows = false;
		
		setTitle("Settings");
		setModal(true);
		setResizable(false);
		setSize(450, 390);
		getContentPane().setLayout(null);
		
		JTabbedPane tab_pane = new JTabbedPane(JTabbedPane.TOP);
		tab_pane.setBounds(0, 0, 444, 316);
		tab_pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		getContentPane().add(tab_pane);
		
		JScrollPane scr_preferences = new JScrollPane();
		tab_pane.addTab("Preferences", null, scr_preferences, null);
		
		JScrollPane scr_rendering = new JScrollPane();
		scr_rendering.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tab_pane.addTab("Rendering", null, scr_rendering, null);
		
		JPanel pnl_preferences = new JPanel();
		pnl_preferences.setLayout(null);
		pnl_preferences.setPreferredSize(new Dimension(pnl_preferences.getSize().width, 360));
		scr_preferences.setViewportView(pnl_preferences);
		
		rad_worldLast = new JRadioButton("Open the last folder used when selecting a world");
		rad_worldLast.setBounds(6, 7, 423, 23);
		pnl_preferences.add(rad_worldLast);
		
		rad_worldDefault = new JRadioButton("Open the default Minecraft save folder when selecting a world");
		rad_worldDefault.setBounds(6, 33, 423, 23);
		pnl_preferences.add(rad_worldDefault);
		
		rad_worldOther = new JRadioButton("Open a specific folder when selecting a world");
		rad_worldOther.setBounds(6, 59, 423, 23);
		pnl_preferences.add(rad_worldOther);
		
		JButton btn_world = new JButton("World");
		btn_world.setEnabled(false);
		btn_world.setBounds(6, 88, 89, 23);
		pnl_preferences.add(btn_world);
		
		txt_world = new JTextField();
		txt_world.setText((String) null);
		txt_world.setEnabled(false);
		txt_world.setColumns(10);
		txt_world.setBounds(105, 89, 305, 20);
		pnl_preferences.add(txt_world);
		
		rad_outputLast = new JRadioButton("Open the last folder used when selecting an output image");
		rad_outputLast.setBounds(6, 118, 423, 23);
		pnl_preferences.add(rad_outputLast);
		
		rad_outputDefault = new JRadioButton("Open the default Minecraft directory when selecting an output image");
		rad_outputDefault.setBounds(6, 144, 423, 23);
		pnl_preferences.add(rad_outputDefault);
		
		rad_outputOther = new JRadioButton("Open a specific folder when selecting an output image");
		rad_outputOther.setBounds(6, 170, 423, 23);
		pnl_preferences.add(rad_outputOther);
		
		JButton btn_output = new JButton("Output");
		btn_output.setEnabled(false);
		btn_output.setBounds(6, 199, 89, 23);
		pnl_preferences.add(btn_output);
		
		txt_output = new JTextField();
		txt_output.setText((String) null);
		txt_output.setEnabled(false);
		txt_output.setColumns(10);
		txt_output.setBounds(105, 200, 305, 20);
		pnl_preferences.add(txt_output);
		
		rad_imageIgnore = new JRadioButton("Don't open images automatically after rendering");
		rad_imageIgnore.setBounds(6, 229, 423, 23);
		pnl_preferences.add(rad_imageIgnore);
		
		rad_imageAsk = new JRadioButton("Ask to open images after rendering");
		rad_imageAsk.setBounds(6, 255, 423, 23);
		pnl_preferences.add(rad_imageAsk);
		
		rad_imageAlways = new JRadioButton("Always open images after rendering");
		rad_imageAlways.setBounds(6, 281, 423, 23);
		pnl_preferences.add(rad_imageAlways);
		
		ButtonGroup worldGroup = new ButtonGroup();
		worldGroup.add(rad_worldLast);
		worldGroup.add(rad_worldDefault);
		worldGroup.add(rad_worldOther);
		
		ButtonGroup outputGroup = new ButtonGroup();
		outputGroup.add(rad_outputLast);
		outputGroup.add(rad_outputDefault);
		outputGroup.add(rad_outputOther);
		
		JPanel pnl_render = new JPanel();
		pnl_render.setLayout(null);
		pnl_render.setPreferredSize(new Dimension(pnl_render.getMaximumSize().width, 330));
		scr_rendering.setViewportView(pnl_render);
		
		JPanel pnl_popup = new JPanel();
		tab_pane.addTab("Pop-ups", null, pnl_popup, null);
		pnl_popup.setLayout(null);
		
		sld_brightness = new JSlider();
		sld_brightness.setEnabled(false);
		sld_brightness.setBounds(146, 30, 229, 26);
		pnl_render.add(sld_brightness);
		sld_brightness.setMinorTickSpacing(1);
		
		JLabel lbl_brightness = new JLabel("Night Brightness");
		lbl_brightness.setBounds(20, 35, 98, 14);
		pnl_render.add(lbl_brightness);
		lbl_brightness.setToolTipText("Adjusts the base brightness at night. 0% is pitch black, 100% is normal daylight");
		
		JLabel lbl_brightnessPct = new JLabel("50%");
		lbl_brightnessPct.setBounds(385, 35, 35, 14);
		pnl_render.add(lbl_brightnessPct);
		
		JLabel lbl_biome = new JLabel("Biome Intensity");
		lbl_biome.setBounds(20, 82, 98, 14);
		pnl_render.add(lbl_biome);
		lbl_biome.setToolTipText("Affects how much blocks are affected by their biome colors. 0% is no effect, 100% is entirely the biome color.");
		
		sld_biome = new JSlider();
		sld_biome.setEnabled(false);
		sld_biome.setBounds(146, 77, 229, 26);
		pnl_render.add(sld_biome);
		sld_biome.setMinorTickSpacing(1);
		
		JLabel lbl_biomePct = new JLabel("50%");
		lbl_biomePct.setBounds(385, 82, 35, 14);
		pnl_render.add(lbl_biomePct);
		
		JLabel lbl_water = new JLabel("Water Transparency");
		lbl_water.setBounds(20, 131, 140, 14);
		pnl_render.add(lbl_water);
		lbl_water.setToolTipText("Affects how much water tints blocks below it. 0% is no effect, 100% is completely water colored. Shadows are still rendered at 100%.");
		
		sld_water = new JSlider();
		sld_water.setEnabled(false);
		sld_water.setBounds(146, 126, 229, 26);
		pnl_render.add(sld_water);
		sld_water.setMinorTickSpacing(1);
		
		JLabel lbl_waterPct = new JLabel("50%");
		lbl_waterPct.setBounds(385, 131, 35, 14);
		pnl_render.add(lbl_waterPct);
		
		JLabel lbl_shadow = new JLabel("Shadow Intensity");
		lbl_shadow.setBounds(20, 180, 110, 14);
		pnl_render.add(lbl_shadow);
		lbl_shadow.setToolTipText("Affects how intense shadows and highlights are. 0% removes them, 100% shades blocks pitch black and highlights them to twice the brightness.");
		
		sld_shadow = new JSlider();
		sld_shadow.setEnabled(false);
		sld_shadow.setBounds(146, 175, 229, 26);
		pnl_render.add(sld_shadow);
		sld_shadow.setMinorTickSpacing(1);
		
		JLabel lbl_shadowPct = new JLabel("50%");
		lbl_shadowPct.setBounds(385, 180, 35, 14);
		pnl_render.add(lbl_shadowPct);
		
		chk_night = new JCheckBox("Render at night");
		chk_night.setBounds(6, 7, 154, 23);
		pnl_render.add(chk_night);
		
		chk_biome = new JCheckBox("Render biome colors");
		chk_biome.setBounds(6, 56, 154, 23);
		pnl_render.add(chk_biome);
		
		chk_water = new JCheckBox("Render blocks under water");
		chk_water.setBounds(6, 103, 193, 23);
		pnl_render.add(chk_water);
		
		chk_shadow = new JCheckBox("Render shadows/highlights");
		chk_shadow.setBounds(6, 152, 223, 23);
		pnl_render.add(chk_shadow);
		
		sld_highlight = new JSlider();
		sld_highlight.setEnabled(false);
		sld_highlight.setBounds(146, 205, 229, 26);
		pnl_render.add(sld_highlight);
		
		JLabel lbl_highlight = new JLabel("Highlight Intensity");
		lbl_highlight.setBounds(20, 210, 110, 14);
		pnl_render.add(lbl_highlight);
		
		JLabel lbl_highlightPct = new JLabel("50%");
		lbl_highlightPct.setBounds(385, 210, 35, 14);
		pnl_render.add(lbl_highlightPct);
		
		chk_highlightLock = new JCheckBox("Lock shadow/highlight sliders");
		chk_highlightLock.setEnabled(false);
		chk_highlightLock.setBounds(16, 231, 217, 23);
		
		chk_missingBlock = new JCheckBox("Show missing block ID warning");
		chk_missingBlock.setBounds(6, 59, 427, 23);
		pnl_popup.add(chk_missingBlock);
		
		chk_unknownBiome = new JCheckBox("Show unknown biome ID warning");
		chk_unknownBiome.setBounds(6, 137, 427, 23);
		pnl_popup.add(chk_unknownBiome);
		
		chk_saveBlock = new JCheckBox("Show save block file confirmation");
		chk_saveBlock.setSelected(true);
		chk_saveBlock.setBounds(6, 7, 427, 23);
		pnl_popup.add(chk_saveBlock);
		
		chk_removeBlock = new JCheckBox("Show remove block ID confirmation");
		chk_removeBlock.setSelected(true);
		chk_removeBlock.setBounds(6, 33, 427, 23);
		pnl_popup.add(chk_removeBlock);
		
		chk_saveBiome = new JCheckBox("Show save biome file confirmation");
		chk_saveBiome.setSelected(true);
		chk_saveBiome.setBounds(6, 85, 427, 23);
		pnl_popup.add(chk_saveBiome);
		
		chk_removeBiome = new JCheckBox("Show remove biome ID confirmation");
		chk_removeBiome.setSelected(true);
		chk_removeBiome.setBounds(6, 111, 427, 23);
		pnl_popup.add(chk_removeBiome);
		
		JButton btn_save = new JButton("Save");
		btn_save.setBounds(367, 327, 67, 23);
		getContentPane().add(btn_save);
		
		JButton btn_apply = new JButton("Apply");
		btn_apply.setBounds(283, 327, 74, 23);
		getContentPane().add(btn_apply);
		
		JButton btn_default = new JButton("Defaults");
		btn_default.setBounds(10, 327, 81, 23);
		getContentPane().add(btn_default);
		
		rad_imageFolderAsk = new JRadioButton("Ask to open the image's folder after rendering");
		rad_imageFolderAsk.setBounds(6, 307, 404, 23);
		pnl_preferences.add(rad_imageFolderAsk);
		
		rad_imageFolderAlways = new JRadioButton("Always open the image's folder after rendering");
		rad_imageFolderAlways.setBounds(6, 333, 404, 23);
		pnl_preferences.add(rad_imageFolderAlways);
		
		ButtonGroup imageGroup = new ButtonGroup();
		imageGroup.add(rad_imageIgnore);
		imageGroup.add(rad_imageAsk);
		imageGroup.add(rad_imageAlways);
		imageGroup.add(rad_imageFolderAsk);
		imageGroup.add(rad_imageFolderAlways);
		
		pnl_render.add(chk_highlightLock);
		
		lbl_defaultBiome = new JLabel("Default Biome");
		lbl_defaultBiome.setToolTipText("The default biome colors for any unknwon biome.");
		lbl_defaultBiome.setBounds(10, 261, 90, 14);
		pnl_render.add(lbl_defaultBiome);
		
		com_biomeNames = new JComboBox<>();
		com_biomeNames.setBounds(128, 258, 282, 20);
		pnl_render.add(com_biomeNames);
		
		rad_blockColor = new JRadioButton("Render blocks with missing IDs as the default color (Magenta)");
		rad_blockColor.setBounds(6, 284, 404, 23);
		pnl_render.add(rad_blockColor);
		
		rad_blockIgnore = new JRadioButton("Ignore blocks with missing IDs");
		rad_blockIgnore.setBounds(6, 310, 404, 23);
		pnl_render.add(rad_blockIgnore);
		
		ButtonGroup blockGroup = new ButtonGroup();
		blockGroup.add(rad_blockColor);
		blockGroup.add(rad_blockIgnore);
		
		sld_water.addChangeListener((e) -> 
			lbl_waterPct.setText(sld_water.getValue() + "%"));
		sld_biome.addChangeListener((e) -> 
			lbl_biomePct.setText(sld_biome.getValue() + "%"));
		sld_brightness.addChangeListener((e) -> 
			lbl_brightnessPct.setText(sld_brightness.getValue() + "%"));
		sld_shadow.addChangeListener((e) -> shadowSlide(lbl_shadowPct));
		sld_highlight.addChangeListener((e) -> highlightSlide(lbl_highlightPct));
		chk_night.addChangeListener((e) -> sld_brightness.setEnabled(chk_night.isSelected()));
		chk_biome.addChangeListener((e) -> sld_biome.setEnabled(chk_biome.isSelected()));
		chk_water.addChangeListener((e) -> sld_water.setEnabled(chk_water.isSelected()));
		chk_shadow.addChangeListener((e) -> shadowsChecked(chk_shadow.isSelected()));
		chk_highlightLock.addChangeListener((e) -> highlightLockChecked());
		rad_worldOther.addChangeListener((e) -> 
		{
			btn_world.setEnabled(rad_worldOther.isSelected());
			txt_world.setEnabled(rad_worldOther.isSelected());
		});
		rad_outputOther.addChangeListener((e) -> 
		{
			btn_output.setEnabled(rad_outputOther.isSelected());
			txt_output.setEnabled(rad_outputOther.isSelected());
		});
		btn_world.addActionListener((e) -> 
		{
			JFileChooser fileChooser = new JFileChooser(
					props.getProperty(Property.LAST_WORLD_FOLDER.key));
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				txt_world.setText(fileChooser.getSelectedFile().getPath());
			}
		});
		btn_output.addActionListener((e) -> 
		{
			JFileChooser fileChooser = new JFileChooser(
					props.getProperty(Property.LAST_OUTPUT_FOLDER.key));
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				txt_output.setText(fileChooser.getSelectedFile().getPath());
			}
		});
		btn_default.addActionListener((e) -> defaultPress(tab_pane.getSelectedIndex()));
		btn_apply.addActionListener((e) -> applyPress());
		btn_save.addActionListener((e) -> savePress());
		
		loadBiomes();
		applyProperties();
	}

	/**
	 * Populates the list of biomes
	 */
	private void loadBiomes()
	{
		HashMap<Integer, String> biomes = Block.getBiomes();
		
		List<ListBiome> listBiomes = new ArrayList<>();
		
		for (Integer i : biomes.keySet())
			listBiomes.add(new ListBiome(i, biomes.get(i)));
		
		listBiomes.sort(null);
		
		for (ListBiome lb : listBiomes)
			com_biomeNames.addItem(lb);
	}

	/**
	 * Toggles settings when the lock highlight/shadows check box is toggled
	 */
	private void highlightLockChecked()
	{
		lockShadows = chk_highlightLock.isSelected();
		
		if (lockShadows)
			sld_highlight.setValue(sld_shadow.getValue());
	}
	
	private void shadowsChecked(boolean checked)
	{
		sld_shadow.setEnabled(checked);
		sld_highlight.setEnabled(checked);
		chk_highlightLock.setEnabled(checked);
	}

	private void shadowSlide(JLabel lbl_shadowPct)
	{
		lbl_shadowPct.setText(sld_shadow.getValue() + "%");
		
		if (lockShadows)
		{
			lockShadows = false;
			sld_highlight.setValue(sld_shadow.getValue());
			lockShadows = true;
		}
	}
	
	private void highlightSlide(JLabel lbl_highlightPct)
	{
		
		lbl_highlightPct.setText(sld_highlight.getValue() + "%");
		
		if (lockShadows)
		{
			lockShadows = false;
			sld_shadow.setValue(sld_highlight.getValue());
			lockShadows = true;
		}
	}
	
	/**
	 * Applies the properties to each of the components.
	 */
	public void applyProperties()
	{
		/////Preferences tab
		switch (props.getProperty(Property.WORLD_SETTING.key))
		{
			case "default":
				rad_worldDefault.setSelected(true);
				break;
			case "other":
				rad_worldOther.setSelected(true);
				break;
			default:
				rad_worldLast.setSelected(true);
				break;
		}
		
		switch (props.getProperty(Property.OUTPUT_SETTING.key))
		{
			case "default":
				rad_outputDefault.setSelected(true);
				break;
			case "other":
				rad_outputOther.setSelected(true);
				break;
			default:
				rad_outputLast.setSelected(true);
				break;
		}
		
		switch (props.getProperty(Property.OPEN_IMAGE_SETTING.key))
		{
			case "ask_image":
				rad_imageAsk.setSelected(true);
				break;
			case "always_image":
				rad_imageAlways.setSelected(true);
				break;
			case "ask_folder":
				rad_imageAsk.setSelected(true);
				break;
			case "always_folder":
				rad_imageAlways.setSelected(true);
				break;
			default:
				rad_imageIgnore.setSelected(true);
				break;
		}
		
		txt_world.setText(props.getProperty(Property.SPECIFIED_WORLD.key));
		txt_output.setText(props.getProperty(Property.SPECIFIED_OUTPUT.key));
		
		/////Render tab
		
		chk_night.setSelected(Boolean.parseBoolean(
				props.getProperty(Property.RENDER_LIGHT.key)));
		chk_biome.setSelected(Boolean.parseBoolean(
				props.getProperty(Property.RENDER_BIOME_COLORS.key)));
		chk_water.setSelected(Boolean.parseBoolean(
				props.getProperty(Property.RENDER_UNDER_WATER.key)));
		chk_shadow.setSelected(Boolean.parseBoolean(
				props.getProperty(Property.RENDER_SHADOWS.key)));
		chk_highlightLock.setSelected(Boolean.parseBoolean(
				props.getProperty(Property.LOCK_HIGHLIGHT.key)));
		
		sld_brightness.setValue(Integer.parseInt(
				props.getProperty(Property.NIGHT_BRIGHTNESS.key)));
		sld_biome.setValue(Integer.parseInt(
				props.getProperty(Property.BIOME_INTENSITY.key)));
		sld_water.setValue(Integer.parseInt(
				props.getProperty(Property.WATER_TRANSPARENCY.key)));
		sld_shadow.setValue(Integer.parseInt(
				props.getProperty(Property.SHADOW_INTENSITY.key)));
		sld_highlight.setValue(Integer.parseInt(
				props.getProperty(Property.HIGHLIGHT_INTENSITY.key)));
		
		setDefaultBiome(props.getProperty(Property.DEFAULT_BIOME.key));
		
		if (props.getProperty(Property.MISSING_BLOCK_SETTING.key).equals("color"))
			rad_blockColor.setSelected(true);
		else
			rad_blockIgnore.setSelected(true);
		
		/////Pop-ups tab
		
		chk_missingBlock.setSelected(
				Boolean.parseBoolean(props.getProperty(Property.SHOW_MISSING_BLOCK.key)));
		chk_unknownBiome.setSelected(
				Boolean.parseBoolean(props.getProperty(Property.SHOW_UNKNOWN_BIOME.key)));
		chk_saveBlock.setSelected(
				Boolean.parseBoolean(props.getProperty(Property.SHOW_BLOCK_SAVE.key)));
		chk_saveBiome.setSelected(
				Boolean.parseBoolean(props.getProperty(Property.SHOW_BIOME_SAVE.key)));
		chk_removeBlock.setSelected(
				Boolean.parseBoolean(props.getProperty(Property.SHOW_BLOCK_REMOVE.key)));
		chk_removeBiome.setSelected(
				Boolean.parseBoolean(props.getProperty(Property.SHOW_BIOME_REMOVE.key)));
	}
	
	/**
	 * Sets the default biome from the biome name
	 * @param biomeName Name of the biome
	 */
	private void setDefaultBiome(String biomeName)
	{
		for (int i = 0, length = com_biomeNames.getItemCount(); i < length; i++)
		{
			ListBiome biome = (ListBiome)com_biomeNames.getItemAt(i); 
			if (biome.toString().equals(biomeName))
			{
				com_biomeNames.setSelectedIndex(i);
				break;
			}
		}
	}

	private void savePress()
	{
		applyPress();
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	private void applyPress()
	{
		/////Preferences tab
		
		if (rad_worldLast.isSelected())
			props.setProperty(Property.WORLD_SETTING.key, "last");
		else if (rad_worldDefault.isSelected())
		{
			props.setProperty(Property.WORLD_SETTING.key, "default");
			props.setProperty(Property.LAST_WORLD_FOLDER.key, Himeji.SAVE_FOLDER);
		}
		else
		{
			props.setProperty(Property.WORLD_SETTING.key, "other");
			props.setProperty(Property.LAST_WORLD_FOLDER.key, txt_world.getText());
		}
		
		if (rad_outputLast.isSelected())
			props.setProperty(Property.OUTPUT_SETTING.key, "last");
		else if (rad_outputDefault.isSelected())
		{
			props.setProperty(Property.OUTPUT_SETTING.key, "default");
			props.setProperty(Property.LAST_OUTPUT_FOLDER.key, Himeji.SAVE_FOLDER);
		}
		else
		{
			props.setProperty(Property.OUTPUT_SETTING.key, "other");
			props.setProperty(Property.LAST_OUTPUT_FOLDER.key, txt_output.getText());
		}
		
		if (rad_imageIgnore.isSelected())
			props.setProperty(Property.OPEN_IMAGE_SETTING.key, "ignore");
		else if (rad_imageAsk.isSelected())
			props.setProperty(Property.OPEN_IMAGE_SETTING.key, "ask_image");
		else if (rad_imageAlways.isSelected())
			props.setProperty(Property.OPEN_IMAGE_SETTING.key, "always_image");
		else if (rad_imageFolderAsk.isSelected())
			props.setProperty(Property.OPEN_IMAGE_SETTING.key, "ask_folder");
		else
			props.setProperty(Property.OPEN_IMAGE_SETTING.key, "always_folder");
		
		props.setProperty(Property.SPECIFIED_WORLD.key, txt_world.getText());
		props.setProperty(Property.SPECIFIED_OUTPUT.key, txt_output.getText());
		
		/////Render tab
		
		props.setProperty(Property.RENDER_LIGHT.key, chk_night.isSelected() + "");
		props.setProperty(Property.RENDER_BIOME_COLORS.key, chk_biome.isSelected() + "");
		props.setProperty(Property.RENDER_UNDER_WATER.key, chk_water.isSelected() + "");
		props.setProperty(Property.RENDER_SHADOWS.key, chk_shadow.isSelected() + "");
		props.setProperty(Property.LOCK_HIGHLIGHT.key, chk_highlightLock.isSelected() + "");
		
		props.setProperty(Property.NIGHT_BRIGHTNESS.key, sld_brightness.getValue() + "");
		props.setProperty(Property.BIOME_INTENSITY.key, sld_biome.getValue() + "");
		props.setProperty(Property.WATER_TRANSPARENCY.key, sld_water.getValue() + "");
		props.setProperty(Property.SHADOW_INTENSITY.key, sld_shadow.getValue() + "");
		props.setProperty(Property.HIGHLIGHT_INTENSITY.key, sld_highlight.getValue() + "");
		
		props.setProperty(Property.DEFAULT_BIOME.key, com_biomeNames.getSelectedItem().toString());
		if (rad_blockColor.isSelected())
			props.setProperty(Property.MISSING_BLOCK_SETTING.key, "color");
		else
			props.setProperty(Property.MISSING_BLOCK_SETTING.key, "ignore");
		
		/////Pop-ups tab
		
		props.setProperty(Property.SHOW_MISSING_BLOCK.key, chk_missingBlock.isSelected() + "");
		props.setProperty(Property.SHOW_UNKNOWN_BIOME.key, chk_unknownBiome.isSelected() + "");
		props.setProperty(Property.SHOW_BLOCK_SAVE.key, chk_saveBlock.isSelected() + "");
		props.setProperty(Property.SHOW_BIOME_SAVE.key, chk_saveBiome.isSelected() + "");
		props.setProperty(Property.SHOW_BLOCK_REMOVE.key, chk_removeBlock.isSelected() + "");
		props.setProperty(Property.SHOW_BIOME_REMOVE.key, chk_removeBiome.isSelected() + "");
	}
	
	/**
	 * Applies the default values to the selected tab
	 * @param tabIndex
	 */
	private void defaultPress(int tabIndex)
	{
		switch (tabIndex)
		{
			case 0:
				rad_worldLast.setSelected(true);
				rad_outputLast.setSelected(true);
				rad_imageIgnore.setSelected(true);
				break;
				
			case 1:
				sld_brightness.setValue(Integer.parseInt(Property.NIGHT_BRIGHTNESS.defaultValue));
				sld_biome.setValue(Integer.parseInt(Property.BIOME_INTENSITY.defaultValue));
				sld_water.setValue(Integer.parseInt(Property.WATER_TRANSPARENCY.defaultValue));
				sld_shadow.setValue(Integer.parseInt(Property.SHADOW_INTENSITY.defaultValue));
				sld_highlight.setValue(Integer.parseInt(Property.HIGHLIGHT_INTENSITY.defaultValue));
				rad_blockColor.setSelected(true);
				setDefaultBiome(Property.DEFAULT_BIOME.defaultValue);
				break;
				
			case 2:
				chk_missingBlock.setSelected(true);
				chk_unknownBiome.setSelected(true);
				chk_saveBlock.setSelected(true);
				chk_saveBiome.setSelected(true);
				chk_removeBlock.setSelected(true);
				chk_removeBiome.setSelected(true);
				break;
		}
	}
}