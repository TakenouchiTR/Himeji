package com.takenouchitr.himeji.frames;

import javax.swing.JSlider;

import com.takenouchitr.himeji.Himeji;
import com.takenouchitr.himeji.Property;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
	private JTextField txt_world;
	private JTextField txt_output;
	
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
		
		JPanel pnl_preferences = new JPanel();
		tab_pane.addTab("Preferences", null, pnl_preferences, null);
		pnl_preferences.setLayout(null);
		
		rad_worldLast = new JRadioButton("Open the last folder used when selecting a world");
		rad_worldLast.setBounds(6, 7, 423, 23);
		pnl_preferences.add(rad_worldLast);
		
		rad_worldDefault = new JRadioButton("Open the default Minecraft save folder when selecting a world");
		rad_worldDefault.setBounds(6, 33, 423, 23);
		pnl_preferences.add(rad_worldDefault);
		
		rad_worldOther = new JRadioButton("Open a specific folder when selecting a world");
		rad_worldOther.setBounds(6, 59, 423, 23);
		pnl_preferences.add(rad_worldOther);
		
		ButtonGroup worldGroup = new ButtonGroup();
		worldGroup.add(rad_worldLast);
		worldGroup.add(rad_worldDefault);
		worldGroup.add(rad_worldOther);
		
		JButton btn_world = new JButton("World");
		btn_world.setEnabled(false);
		btn_world.setBounds(6, 88, 89, 23);
		pnl_preferences.add(btn_world);
		
		txt_world = new JTextField();
		txt_world.setEnabled(false);
		txt_world.setBounds(105, 89, 324, 20);
		pnl_preferences.add(txt_world);
		txt_world.setColumns(10);
		
		rad_outputLast = new JRadioButton("Open the last folder used when selecting an output image");
		rad_outputLast.setBounds(6, 118, 423, 23);
		pnl_preferences.add(rad_outputLast);
		
		rad_outputDefault = new JRadioButton("Open the default Minecraft directory when selecting an output image");
		rad_outputDefault.setBounds(6, 144, 423, 23);
		pnl_preferences.add(rad_outputDefault);
		
		rad_outputOther = new JRadioButton("Open a specific folder when selecting an output image");
		rad_outputOther.setBounds(6, 170, 423, 23);
		pnl_preferences.add(rad_outputOther);
		
		ButtonGroup outputGroup = new ButtonGroup();
		outputGroup.add(rad_outputLast);
		outputGroup.add(rad_outputDefault);
		outputGroup.add(rad_outputOther);
		
		JButton btn_output = new JButton("Output");
		btn_output.setEnabled(false);
		btn_output.setBounds(6, 199, 89, 23);
		pnl_preferences.add(btn_output);
		
		txt_output = new JTextField();
		txt_output.setEnabled(false);
		txt_output.setBounds(105, 200, 324, 20);
		pnl_preferences.add(txt_output);
		txt_output.setColumns(10);
		
		JPanel pnl_render = new JPanel();
		tab_pane.addTab("Rendering", null, pnl_render, null);
		pnl_render.setLayout(null);
		
		JPanel pnl_popup = new JPanel();
		tab_pane.addTab("Pop-ups", null, pnl_popup, null);
		pnl_popup.setLayout(null);
		
		sld_brightness = new JSlider();
		sld_brightness.setEnabled(false);
		sld_brightness.setBounds(128, 30, 256, 26);
		pnl_render.add(sld_brightness);
		sld_brightness.setMinorTickSpacing(1);
		
		JLabel lbl_brightness = new JLabel("Night Brightness");
		lbl_brightness.setBounds(20, 35, 98, 14);
		pnl_render.add(lbl_brightness);
		lbl_brightness.setToolTipText("Adjusts the base brightness at night. 0% is pitch black, 100% is normal daylight");
		
		JLabel lbl_brightnessPct = new JLabel("50%");
		lbl_brightnessPct.setBounds(394, 35, 35, 14);
		pnl_render.add(lbl_brightnessPct);
		
		JLabel lbl_biome = new JLabel("Biome Intensity");
		lbl_biome.setBounds(20, 82, 98, 14);
		pnl_render.add(lbl_biome);
		lbl_biome.setToolTipText("Affects how much blocks are affected by their biome colors. 0% is no effect, 100% is entirely the biome color.");
		
		sld_biome = new JSlider();
		sld_biome.setEnabled(false);
		sld_biome.setBounds(128, 77, 256, 26);
		pnl_render.add(sld_biome);
		sld_biome.setMinorTickSpacing(1);
		
		JLabel lbl_biomePct = new JLabel("50%");
		lbl_biomePct.setBounds(394, 82, 35, 14);
		pnl_render.add(lbl_biomePct);
		
		JLabel lbl_water = new JLabel("Water Transparency");
		lbl_water.setBounds(20, 131, 110, 14);
		pnl_render.add(lbl_water);
		lbl_water.setToolTipText("Affects how much water tints blocks below it. 0% is no effect, 100% is completely water colored. Shadows are still rendered at 100%.");
		
		sld_water = new JSlider();
		sld_water.setEnabled(false);
		sld_water.setBounds(128, 126, 256, 26);
		pnl_render.add(sld_water);
		sld_water.setMinorTickSpacing(1);
		
		JLabel lbl_waterPct = new JLabel("50%");
		lbl_waterPct.setBounds(394, 131, 35, 14);
		pnl_render.add(lbl_waterPct);
		
		JLabel lbl_shadow = new JLabel("Shadow Intensity");
		lbl_shadow.setBounds(20, 180, 110, 14);
		pnl_render.add(lbl_shadow);
		lbl_shadow.setToolTipText("Affects how intense shadows and highlights are. 0% removes them, 100% shades blocks pitch black and highlights them to twice the brightness.");
		
		sld_shadow = new JSlider();
		sld_shadow.setEnabled(false);
		sld_shadow.setBounds(128, 175, 256, 26);
		pnl_render.add(sld_shadow);
		sld_shadow.setMinorTickSpacing(1);
		
		JLabel lbl_shadowPct = new JLabel("50%");
		lbl_shadowPct.setBounds(394, 180, 35, 14);
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
		sld_highlight.setBounds(128, 205, 256, 26);
		pnl_render.add(sld_highlight);
		
		JLabel lbl_highlight = new JLabel("Highlight Intensity");
		lbl_highlight.setBounds(20, 210, 110, 14);
		pnl_render.add(lbl_highlight);
		
		JLabel lbl_highlightPct = new JLabel("50%");
		lbl_highlightPct.setBounds(394, 210, 35, 14);
		pnl_render.add(lbl_highlightPct);
		
		chk_highlightLock = new JCheckBox("Lock shadow/highlight sliders");
		chk_highlightLock.setEnabled(false);
		chk_highlightLock.setBounds(16, 231, 217, 23);
		
		pnl_render.add(chk_highlightLock);
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
		
		chk_missingBlock = new JCheckBox("Show missing block ID warning");
		chk_missingBlock.setBounds(6, 7, 224, 23);
		pnl_popup.add(chk_missingBlock);
		
		chk_unknownBiome = new JCheckBox("Show unknown biome ID warning");
		chk_unknownBiome.setBounds(6, 33, 224, 23);
		pnl_popup.add(chk_unknownBiome);
		
		JButton btn_save = new JButton("Save");
		btn_save.setBounds(367, 327, 67, 23);
		getContentPane().add(btn_save);
		
		JButton btn_apply = new JButton("Apply");
		btn_apply.setBounds(283, 327, 74, 23);
		getContentPane().add(btn_apply);
		
		JButton btn_default = new JButton("Defaults");
		btn_default.setBounds(10, 327, 81, 23);
		getContentPane().add(btn_default);
		
		btn_default.addActionListener((e) -> defaultPress(tab_pane.getSelectedIndex()));
		btn_apply.addActionListener((e) -> applyPress());
		btn_save.addActionListener((e) -> savePress());
		
		applyProperties();
	}

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
			sld_highlight.setValue(100 - sld_shadow.getValue());
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
		
		/////Pop-ups tab
		
		chk_missingBlock.setSelected(true);
		chk_unknownBiome.setSelected(true);
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
		
		/////Pop-ups tab
		
		props.setProperty(Property.SHOW_MISSING_BLOCK.key, chk_missingBlock.isSelected() + "");
		props.setProperty(Property.SHOW_UNKNOWN_BIOME.key, chk_unknownBiome.isSelected() + "");
	}
	
	private void defaultPress(int tabIndex)
	{
		switch (tabIndex)
		{
			case 0:
				rad_worldLast.setSelected(true);
				rad_outputLast.setSelected(true);
				break;
			case 1:
				sld_brightness.setValue(Integer.parseInt(Property.NIGHT_BRIGHTNESS.defaultValue));
				sld_biome.setValue(Integer.parseInt(Property.BIOME_INTENSITY.defaultValue));
				sld_water.setValue(Integer.parseInt(Property.WATER_TRANSPARENCY.defaultValue));
				sld_shadow.setValue(Integer.parseInt(Property.SHADOW_INTENSITY.defaultValue));
				sld_highlight.setValue(Integer.parseInt(Property.HIGHLIGHT_INTENSITY.defaultValue));
				break;
		}
	}
}