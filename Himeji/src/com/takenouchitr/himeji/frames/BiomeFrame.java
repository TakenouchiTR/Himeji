package com.takenouchitr.himeji.frames;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.border.LineBorder;

import com.takenouchitr.himeji.Himeji;
import com.takenouchitr.himeji.ListBiome;
import com.takenouchitr.himeji.ListChangeListener;
import com.takenouchitr.himeji.Property;
import com.takenouchitr.himeji.MCCompat.Block;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class BiomeFrame extends JDialog
{
	private int grassColor, foliageColor, waterColor;
	private JTextField txt_hex;
	private JComboBox<ListBiome> com_biomeNames;
	private JSpinner spn_r, spn_g, spn_b;
	private JPanel pnl_colorPreview;
	private JRadioButton rad_grass, rad_foliage, rad_water;
	private JButton btn_remove;
	
	public BiomeFrame(Himeji himeji) 
	{
		//I have given up on keeping this code organized. Window builder is helpful, but it's so messy
		setTitle("Biome Color Picker");
		setResizable(false);
		setSize(320, 269);
		setModal(true);
		getContentPane().setLayout(null);
		
		JLabel lbl_hex = new JLabel("Hex");
		lbl_hex.setToolTipText("The six-digit hexadecimal value to represent a color.");
		lbl_hex.setBounds(10, 45, 46, 14);
		getContentPane().add(lbl_hex);
		
		txt_hex = new JTextField();
		txt_hex.setText((String) null);
		txt_hex.setColumns(6);
		txt_hex.setBounds(66, 42, 56, 20);
		getContentPane().add(txt_hex);
		
		JLabel lbl_r = new JLabel("Red");
		lbl_r.setToolTipText("The red component of a color (0 - 255).");
		lbl_r.setBounds(10, 76, 46, 14);
		getContentPane().add(lbl_r);
		
		spn_r = new JSpinner();
		spn_r.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		spn_r.setBounds(76, 73, 46, 20);
		getContentPane().add(spn_r);
		
		JLabel lbl_g = new JLabel("Green");
		lbl_g.setToolTipText("The red component of a color (0 - 255).");
		lbl_g.setBounds(10, 107, 46, 14);
		getContentPane().add(lbl_g);
		
		spn_g = new JSpinner();
		spn_g.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		spn_g.setBounds(76, 104, 46, 20);
		getContentPane().add(spn_g);
		
		JLabel lbl_b = new JLabel("Blue");
		lbl_b.setToolTipText("The red component of a color (0 - 255).");
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
		
		JButton btn_update = new JButton("Apply");
		btn_update.setBounds(227, 185, 75, 23);
		getContentPane().add(btn_update);
		
		JButton btn_save = new JButton("Save to File");
		btn_save.setBounds(203, 211, 99, 23);
		getContentPane().add(btn_save);
		
		rad_grass = new JRadioButton("Grass");
		rad_grass.setToolTipText("Color shift for blocks that are flagged as \"Grass.\"");
		rad_grass.setSelected(true);
		rad_grass.setBounds(10, 159, 109, 23);
		getContentPane().add(rad_grass);
		
		rad_foliage = new JRadioButton("Foliage");
		rad_foliage.setToolTipText("Color shift for blocks that are flagged as \"Foliage.\"");
		rad_foliage.setBounds(10, 185, 109, 23);
		getContentPane().add(rad_foliage);
		
		rad_water = new JRadioButton("Water");
		rad_water.setToolTipText("Color shift for blocks that are flagged as \"Water.\"");
		rad_water.setBounds(10, 211, 88, 23);
		getContentPane().add(rad_water);
		
		ButtonGroup biomeRadios = new ButtonGroup();
		biomeRadios.add(rad_grass);
		biomeRadios.add(rad_foliage);
		biomeRadios.add(rad_water);
		
		com_biomeNames = new JComboBox<ListBiome>();
		com_biomeNames.setBounds(10, 11, 292, 20);
		getContentPane().add(com_biomeNames);
		
		btn_remove = new JButton("Remove");
		btn_remove.setBounds(104, 211, 89, 23);
		getContentPane().add(btn_remove);
		
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
		btn_remove.addActionListener((e) -> removePress());
		Block.addListChangeListener(new ListChangeListener() 
		{

			@Override
			public void OnItemAddition(String name, int listID)
			{
				if (listID != Block.BIOME_LIST)
					return;
				
				int id = Integer.parseInt(name);
				String biomeName = Block.getBiomes().get(id);
				
				ListBiome lb = new ListBiome(id, biomeName);
				com_biomeNames.addItem(lb);
			}

			@Override
			public void OnItemUpdate(String oldName, String newName, int listID)
			{
			}

			@Override
			public void OnItemRemoval(String name, int listID)
			{
				if (listID == Block.BIOME_LIST)
				{
					int id = Integer.parseInt(name);
					
					for (int i = 0, size = com_biomeNames.getItemCount(); i < size; i++)
					{
						ListBiome lb = (ListBiome)com_biomeNames.getItemAt(i);
						if (lb.getBiome() == id)
						{
							com_biomeNames.removeItemAt(i);
							break;
						}
					}
				}
			}
			
		});
		
		loadBiomes();
	}

	/**
	 * Saves the changes for the session, not to file.
	 */
	public void saveChanges()
	{
		ListBiome lb = (ListBiome) com_biomeNames.getSelectedItem();
		int biome = lb.getBiome();
		
		Block.setGrassColor(biome, grassColor);
		Block.setFoliageColor(biome, foliageColor);
		Block.setWaterColor(biome, waterColor);
	}
	
	/**
	 * Saves changes for both the session and to the file.
	 */
	public void saveToFile()
	{
		if (Himeji.getProperty(Property.SHOW_BIOME_SAVE.key).equals("true"))
		{
			int result = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to save all changes? This cannot be undone.\n" + 
					"If you wish to make changes only for this session, please press \"Apply\" instead.", 
					"Save biome confirmation", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			
			if (result != JOptionPane.YES_OPTION)
				return;
		}
		saveChanges();
		Block.saveBiomeColorFiles();
	}
	
	/**
	 * When the remove button is pressed.
	 */
	private void removePress()
	{
		if (Himeji.getProperty(Property.SHOW_BIOME_REMOVE.key).equals("true"))
		{
			int result = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove the biome?\n" + 
					"It will be immediately removed from the list, without having to press \"Accept.\"", 
					"Remove biome confirmation", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			
			if (result != JOptionPane.YES_OPTION)
				return;
		}
		
		ListBiome lb = (ListBiome) com_biomeNames.getSelectedItem();
		Block.removeBiome(lb.getBiome());
	}
	
	/**
	 * Converts the hexadecimal input to RGB
	 */
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
	
 	/**
 	 * Populates the biome combo box with all of the loaded biome names 
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
		
		loadColors();
	}
	
	/**
	 * Creates an integer from the spinners that represents a color's ARGB value
	 * @return ARGB integer
	 */
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
	
	/**
	 * Loads all three colors associated with a biome
	 */
	private void loadColors()
	{
		ListBiome lb = (ListBiome)com_biomeNames.getSelectedItem(); 
		int biome = lb.getBiome();
		
		grassColor = Block.getGrassColor(biome);
		foliageColor = Block.getFoliageColor(biome);
		waterColor = Block.getWaterColor(biome);
		
		changeType();
	}
	
	/**
	 * Changes which portion of the biome color is editable
	 */
	private void changeType()
	{
		if (rad_grass.isSelected())
			setColor(grassColor);
		else if (rad_foliage.isSelected())
			setColor(foliageColor);
		else
			setColor(waterColor);
	}
	
	/**
	 * Sets the color of the spinners from an integer that represents an ARGB value
	 * @param color ARGB integer
	 */
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
	
	/**
	 * Sets the color of the preview panel to the value of the RGB spinners
	 */
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
