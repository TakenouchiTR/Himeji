package com.takenouchitr.himeji.frames;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;

import com.takenouchitr.himeji.Himeji;
import com.takenouchitr.himeji.ListBiome;
import com.takenouchitr.himeji.MCCompat.Biome;
import com.takenouchitr.himeji.MCCompat.Block;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.Window.Type;
import javax.swing.SpinnerNumberModel;

public class BiomeFrame extends JFrame
{
	private int grassColor, foliageColor, waterColor;
	private JTextField txt_hex;
	private JComboBox<ListBiome> com_biomeNames;
	private JSpinner spn_r, spn_g, spn_b;
	private JPanel pnl_colorPreview;
	private JRadioButton rad_grass, rad_foliage, rad_water;
	
	public BiomeFrame(Himeji himeji) 
	{
		setTitle("Biome Color Picker");
		setResizable(false);
		setSize(320, 280);
		getContentPane().setLayout(null);
		
		JLabel lbl_hex = new JLabel("Hex");
		lbl_hex.setBounds(10, 45, 46, 14);
		getContentPane().add(lbl_hex);
		
		txt_hex = new JTextField();
		txt_hex.setText((String) null);
		txt_hex.setColumns(6);
		txt_hex.setBounds(66, 42, 56, 20);
		getContentPane().add(txt_hex);
		
		JLabel lbl_r = new JLabel("Red");
		lbl_r.setBounds(10, 76, 46, 14);
		getContentPane().add(lbl_r);
		
		spn_r = new JSpinner();
		spn_r.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		spn_r.setBounds(76, 73, 46, 20);
		getContentPane().add(spn_r);
		
		JLabel lbl_g = new JLabel("Green");
		lbl_g.setBounds(10, 107, 46, 14);
		getContentPane().add(lbl_g);
		
		spn_g = new JSpinner();
		spn_g.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		spn_g.setBounds(76, 104, 46, 20);
		getContentPane().add(spn_g);
		
		JLabel lbl_b = new JLabel("Blue");
		lbl_b.setBounds(10, 138, 46, 14);
		getContentPane().add(lbl_b);
		
		spn_b = new JSpinner();
		spn_b.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		spn_b.setBounds(76, 135, 46, 20);
		getContentPane().add(spn_b);
		
		pnl_colorPreview = new JPanel();
		pnl_colorPreview.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnl_colorPreview.setBackground(Color.BLACK);
		pnl_colorPreview.setBounds(132, 42, 170, 132);
		getContentPane().add(pnl_colorPreview);
		
		JButton btn_update = new JButton("Update");
		btn_update.setBounds(227, 185, 75, 23);
		getContentPane().add(btn_update);
		
		JButton btn_save = new JButton("Save to File");
		btn_save.setBounds(203, 211, 99, 23);
		getContentPane().add(btn_save);
		
		rad_grass = new JRadioButton("Grass");
		rad_grass.setSelected(true);
		rad_grass.setBounds(10, 159, 109, 23);
		getContentPane().add(rad_grass);
		
		rad_foliage = new JRadioButton("Foliage");
		rad_foliage.setBounds(10, 185, 109, 23);
		getContentPane().add(rad_foliage);
		
		rad_water = new JRadioButton("Water");
		rad_water.setBounds(10, 211, 109, 23);
		getContentPane().add(rad_water);
		
		ButtonGroup biomeRadios = new ButtonGroup();
		biomeRadios.add(rad_grass);
		biomeRadios.add(rad_foliage);
		biomeRadios.add(rad_water);
		
		com_biomeNames = new JComboBox<ListBiome>();
		com_biomeNames.setBounds(10, 11, 292, 20);
		getContentPane().add(com_biomeNames);
		
		com_biomeNames.addActionListener((e) -> loadColors());
		spn_r.addChangeListener((e) -> updatePreview());
		spn_g.addChangeListener((e) -> updatePreview());
		spn_b.addChangeListener((e) -> updatePreview());
		rad_grass.addChangeListener((e) -> changeType());
		rad_foliage.addChangeListener((e) -> changeType());
		rad_water.addChangeListener((e) -> changeType());
		txt_hex.addActionListener((e) -> convertHex());
		btn_update.addActionListener((e) -> saveChanges());
		btn_save.addActionListener((e) -> saveToFile());
		addWindowListener(new WindowListener() 
		{

			@Override
			public void windowActivated(WindowEvent e)
			{
			}

			@Override
			public void windowClosed(WindowEvent e)
			{
			}

			@Override
			public void windowClosing(WindowEvent e)
			{
				himeji.setEnabled(true);
			}

			@Override
			public void windowDeactivated(WindowEvent e)
			{
			}

			@Override
			public void windowDeiconified(WindowEvent e)
			{
			}

			@Override
			public void windowIconified(WindowEvent e)
			{
			}

			@Override
			public void windowOpened(WindowEvent e)
			{
			}
		});
		
		loadBiomes();
	}

	public void saveChanges()
	{
		ListBiome lb = (ListBiome) com_biomeNames.getSelectedItem();
		int biome = lb.getBiome();
		
		Block.setGrassColor(biome, grassColor);
		Block.setFoliageColor(biome, foliageColor);
		Block.setWaterColor(biome, waterColor);
	}
	
	public void saveToFile()
	{
		Block.saveBiomeColorFiles();
	}
	
 	private void convertHex()
	{
		StringBuilder sb = new StringBuilder(6);
		sb.append(txt_hex.getText());
		
		while(sb.length() < 6)
			sb.insert(0, '0');
		
		try
		{
			int color = Integer.decode("0x00" + sb.toString());
			int r = ((color & 0x00FF0000) >>> 16);
			int g = ((color & 0x0000FF00) >>> 8);
			int b = (color & 0x000000FF);
			
			spn_r.setValue(r);
			spn_g.setValue(g);
			spn_b.setValue(b);
			
			if (rad_grass.isSelected())
				grassColor = color;
			else if (rad_foliage.isSelected())
				foliageColor = color;
			else
				waterColor = color;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		updatePreview();
	}
	
	private void loadBiomes()
	{
		HashMap<Integer, String> biomes = Block.getBiomes();
		
		List<ListBiome> listBiomes = new ArrayList<>();
		
		for (Integer i : biomes.keySet())
			listBiomes.add(new ListBiome(i, biomes.get(i)));
		
		listBiomes.sort(null);
		
		for (ListBiome lb : listBiomes)
			com_biomeNames.addItem(lb);
		
		loadColors();
	}
	
	private int getColorInt()
	{
		int color = 0xFF000000;
		int r = ((Integer)spn_r.getValue());
		int g = ((Integer)spn_g.getValue());
		int b = ((Integer)spn_b.getValue());
		
		color |= (r << 16);
		color |= (g << 8);
		color |= b;
		
		return color;
	}
	
	private void loadColors()
	{
		ListBiome lb = (ListBiome)com_biomeNames.getSelectedItem(); 
		int biome = lb.getBiome();
		
		grassColor = Block.getGrassColor(biome);
		foliageColor = Block.getFoliageColor(biome);
		waterColor = Block.getWaterColor(biome);
		
		changeType();
	}
	
	private void changeType()
	{
		if (rad_grass.isSelected())
			setColor(grassColor);
		else if (rad_foliage.isSelected())
			setColor(foliageColor);
		else
			setColor(waterColor);
	}
	
	private void setColor(int color)
	{
		int r = (color & 0x00FF0000) >>> 16;
		int g = ((color & 0x0000FF00) >>> 8);
		int b = (color & 0x000000FF);
		
		spn_r.setValue(r);
		spn_g.setValue(g);
		spn_b.setValue(b);
		
		updatePreview();
	}
	
	private void updatePreview()
	{
		int r = (Integer)spn_r.getValue();
		int g = (Integer)spn_g.getValue();
		int b = (Integer)spn_b.getValue();
		pnl_colorPreview.setBackground(new Color(r, g, b));
		
		int colorVal = getColorInt();
		String hex = Integer.toHexString(colorVal);
		txt_hex.setText(hex.toUpperCase().substring(2));
	}	

}
