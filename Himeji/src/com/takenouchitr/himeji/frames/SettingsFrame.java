package com.takenouchitr.himeji.frames;

import javax.swing.JFrame;
import javax.swing.JSlider;

import com.takenouchitr.himeji.Himeji;
import com.takenouchitr.himeji.Property;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Properties;

import javax.swing.JLabel;
import java.awt.Window.Type;
import javax.swing.JButton;

public class SettingsFrame extends JFrame
{
	Properties props;
	
	JSlider sld_brightness;
	JSlider sld_biome;
	JSlider sld_water;
	JSlider sld_shadow;
	
	public SettingsFrame(Properties props) 
	{
		this.props = props;
		
		setTitle("Settings");
		setResizable(false);
		setSize(302, 220);
		getContentPane().setLayout(null);
		
		sld_brightness = new JSlider();
		sld_brightness.setMinorTickSpacing(1);
		sld_brightness.setBounds(118, 11, 121, 26);
		getContentPane().add(sld_brightness);
		
		JLabel lbl_brightness = new JLabel("Night Brightness");
		lbl_brightness.setToolTipText("Adjusts the base brightness at night. 0% is pitch black, 100% is normal daylight");
		lbl_brightness.setBounds(10, 16, 98, 14);
		getContentPane().add(lbl_brightness);
		
		JLabel lbl_brightnessPct = new JLabel("50%");
		lbl_brightnessPct.setBounds(249, 16, 35, 14);
		getContentPane().add(lbl_brightnessPct);
		
		JLabel lbl_biome = new JLabel("Biome Intensity");
		lbl_biome.setToolTipText("Affects how much blocks are affected by their biome colors. 0% is no effect, 100% is entirely the biome color.");
		lbl_biome.setBounds(10, 53, 98, 14);
		getContentPane().add(lbl_biome);
		
		sld_biome = new JSlider();
		sld_biome.setMinorTickSpacing(1);
		sld_biome.setBounds(118, 48, 121, 26);
		getContentPane().add(sld_biome);
		
		JLabel lbl_biomePct = new JLabel("50%");
		lbl_biomePct.setBounds(249, 53, 35, 14);
		getContentPane().add(lbl_biomePct);
		
		JLabel lbl_water = new JLabel("Water Transparency");
		lbl_water.setToolTipText("Affects how much water tints blocks below it. 0% is no effect, 100% is completely water colored. Shadows are still rendered at 100%.");
		lbl_water.setBounds(10, 90, 110, 14);
		getContentPane().add(lbl_water);
		
		sld_water = new JSlider();
		sld_water.setMinorTickSpacing(1);
		sld_water.setBounds(118, 85, 121, 26);
		getContentPane().add(sld_water);
		
		JLabel lbl_waterPct = new JLabel("50%");
		lbl_waterPct.setBounds(249, 90, 35, 14);
		getContentPane().add(lbl_waterPct);
		
		JButton btn_save = new JButton("Save");
		btn_save.setBounds(217, 157, 67, 23);
		getContentPane().add(btn_save);
		
		JLabel lbl_shadow = new JLabel("Shadow Intensity");
		lbl_shadow.setToolTipText("Affects how intense shadows and highlights are. 0% removes them, 100% shades blocks pitch black and highlights them to twice the brightness.");
		lbl_shadow.setBounds(10, 125, 110, 14);
		getContentPane().add(lbl_shadow);
		
		sld_shadow = new JSlider();
		sld_shadow.setMinorTickSpacing(1);
		sld_shadow.setBounds(118, 120, 121, 26);
		getContentPane().add(sld_shadow);
		
		JLabel lbl_shadowPct = new JLabel("50%");
		lbl_shadowPct.setBounds(249, 125, 35, 14);
		getContentPane().add(lbl_shadowPct);
		
		JButton btn_apply = new JButton("Apply");
		btn_apply.setBounds(133, 157, 74, 23);
		getContentPane().add(btn_apply);
		
		JButton btn_default = new JButton("Defaults");
		btn_default.setBounds(10, 157, 81, 23);
		getContentPane().add(btn_default);
		
		btn_save.addActionListener((e) -> savePress());
		btn_apply.addActionListener((e) -> applyPress());
		btn_default.addActionListener((e) -> defaultPress());
		sld_brightness.addChangeListener((e) -> 
			lbl_brightnessPct.setText(sld_brightness.getValue() + "%"));
		sld_biome.addChangeListener((e) -> 
			lbl_biomePct.setText(sld_biome.getValue() + "%"));
		sld_water.addChangeListener((e) -> 
			lbl_waterPct.setText(sld_water.getValue() + "%"));
		sld_shadow.addChangeListener((e) -> 
			lbl_shadowPct.setText(sld_shadow.getValue() + "%"));
		this.addWindowListener(new WindowListener()
		{

			@Override
			public void windowActivated(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e)
			{
				Himeji.frame.setEnabled(true);
			}

			@Override
			public void windowDeactivated(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
		});
	
		applyProperties();
	}

	public void applyProperties()
	{
		sld_brightness.setValue(Integer.parseInt(
				props.getProperty(Property.NIGHT_BRIGHTNESS.key)));
		sld_biome.setValue(Integer.parseInt(
				props.getProperty(Property.BIOME_INTENSITY.key)));
		sld_water.setValue(Integer.parseInt(
				props.getProperty(Property.WATER_TRANSPARENCY.key)));
		sld_shadow.setValue(Integer.parseInt(
				props.getProperty(Property.SHADOW_INTENSITY.key)));
	}

	private void savePress()
	{
		applyPress();
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	private void applyPress()
	{
		props.setProperty(Property.NIGHT_BRIGHTNESS.key, sld_brightness.getValue() + "");
		props.setProperty(Property.BIOME_INTENSITY.key, sld_biome.getValue() + "");
		props.setProperty(Property.WATER_TRANSPARENCY.key, sld_water.getValue() + "");
		props.setProperty(Property.SHADOW_INTENSITY.key, sld_shadow.getValue() + "");
	}
	
	private void defaultPress()
	{
		sld_brightness.setValue(Integer.parseInt(Property.NIGHT_BRIGHTNESS.defaultValue));
		sld_biome.setValue(Integer.parseInt(Property.BIOME_INTENSITY.defaultValue));
		sld_water.setValue(Integer.parseInt(Property.WATER_TRANSPARENCY.defaultValue));
		sld_shadow.setValue(Integer.parseInt(Property.SHADOW_INTENSITY.defaultValue));
	}
}