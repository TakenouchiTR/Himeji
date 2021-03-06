package com.takenouchitr.himeji.frames;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.takenouchitr.himeji.Himeji;
import com.takenouchitr.himeji.ListChangeListener;
import com.takenouchitr.himeji.Property;
import com.takenouchitr.himeji.MCCompat.Block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class ColorPickerFrame extends JDialog
{
	private JComboBox<String> com_namespaceIds;
	private JSpinner spn_r, spn_g, spn_b;
	private JPanel pnl_colorPreview;
	private JTextField txt_hex;
	private JButton btn_update;
	private JButton btn_save;
	private JButton btn_remove;
	
	public ColorPickerFrame() 
	{
		setTitle("Color Editor");
		setSize(310,230);
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(null);
		
		com_namespaceIds = new JComboBox<String>();
		com_namespaceIds.setBounds(10, 11, 284, 20);
		getContentPane().add(com_namespaceIds);
		
		spn_r = new JSpinner();
		spn_r.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		spn_r.setBounds(76, 73, 46, 20);
		getContentPane().add(spn_r);
		
		spn_b = new JSpinner();
		spn_b.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		spn_b.setBounds(76, 135, 46, 20);
		getContentPane().add(spn_b);
		
		spn_g = new JSpinner();
		spn_g.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		spn_g.setBounds(76, 104, 46, 20);
		getContentPane().add(spn_g);
		
		pnl_colorPreview = new JPanel();
		pnl_colorPreview.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnl_colorPreview.setBounds(132, 42, 162, 113);
		getContentPane().add(pnl_colorPreview);
		
		JLabel lbl_r = new JLabel("Red");
		lbl_r.setToolTipText("The red component of a color (0 - 255).");
		lbl_r.setBounds(10, 76, 46, 14);
		getContentPane().add(lbl_r);
		
		JLabel lbl_g = new JLabel("Green");
		lbl_g.setToolTipText("The green component of a color (0 - 255).");
		lbl_g.setBounds(10, 107, 46, 14);
		getContentPane().add(lbl_g);
		
		JLabel lbl_b = new JLabel("Blue");
		lbl_b.setToolTipText("The blue component of a color (0 - 255).");
		lbl_b.setBounds(10, 138, 46, 14);
		getContentPane().add(lbl_b);
		
		JLabel lbl_hex = new JLabel("Hex");
		lbl_hex.setToolTipText("The six-digit hexadecimal value to represent a color.");
		lbl_hex.setBounds(10, 45, 46, 14);
		getContentPane().add(lbl_hex);
		
		txt_hex = new JTextField();
		txt_hex.setBounds(66, 42, 56, 20);
		getContentPane().add(txt_hex);
		txt_hex.setColumns(6);
		
		btn_update = new JButton("Apply");
		btn_update.setBounds(115, 166, 66, 23);
		getContentPane().add(btn_update);
		
		btn_save = new JButton("Save to File");
		btn_save.setBounds(191, 166, 103, 23);
		getContentPane().add(btn_save);
		
		btn_remove = new JButton("Remove");
		btn_remove.setBounds(10, 166, 95, 23);
		getContentPane().add(btn_remove);
		
		com_namespaceIds.addActionListener((e) -> loadColors());
		spn_r.addChangeListener((e) -> updatePreview());
		spn_g.addChangeListener((e) -> updatePreview());
		spn_b.addChangeListener((e) -> updatePreview());
		txt_hex.addActionListener((e) -> convertHex());
		btn_update.addActionListener((e) -> saveChanges());
		btn_save.addActionListener((e) -> saveToFile());
		btn_remove.addActionListener((e) -> removePress());
		Block.addListChangeListener(new ListChangeListener() 
		{

			@Override
			public void OnItemAddition(String name, int listID)
			{
				if (listID == Block.COLORS_LIST)
					addBlockId(name);
			}

			@Override
			public void OnItemUpdate(String oldName, String newName, int listID)
			{
			}

			@Override
			public void OnItemRemoval(String name, int listID)
			{
				if (listID == Block.COLORS_LIST)
				{
					for (int i = 0, size = com_namespaceIds.getItemCount(); i < size; i++)
					{
						if (com_namespaceIds.getItemAt(i).equals(name))
						{
							com_namespaceIds.removeItemAt(i);
							break;
						}
					}
				}
			}	
		});
		
		loadBlockIds();
	}
	
	/**
	 * Adds an ID to the combo box
	 * @param id
	 */
	public void addBlockId(String id)
	{
		com_namespaceIds.addItem(id);
	}
	
	/**
	 * When the remove button is pressed.
	 */
	public void removePress() 
	{
		if (Himeji.getProperty(Property.SHOW_BLOCK_REMOVE.key).equals("true"))
		{
			int result = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove the block?\n" + 
					"It will be immediately removed from the list, without having to press \"Accept.\"", 
					"Remove block confirmation", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			
			if (result != JOptionPane.YES_OPTION)
				return;
		}
		
		Block.removeBlock(com_namespaceIds.getSelectedItem().toString());
	}
	
	/**
	 * Saves the changes for the session, not to file.
	 */
	public void saveChanges()
	{
		int color = getColorInt();
		String id = com_namespaceIds.getSelectedItem().toString();
		Block.setBlockColor(id, color);
	}
	
	/**
	 * Saves changes for both the session and to the file.
	 */
	public void saveToFile()
	{
		if (Himeji.getProperty(Property.SHOW_BLOCK_SAVE.key).equals("true"))
		{
			int result = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to save all changes? This cannot be undone.\n" + 
					"If you wish to make changes only for this session, please press \"Apply\" instead.", 
					"Save block confirmation", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			
			if (result != JOptionPane.YES_OPTION)
				return;
		}
		
		saveChanges();
		Block.saveColorFile();
	}
	
	/*
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
		}
		catch (Exception e)
		{
			
		}
		
		updatePreview();
	}
	
 	/**
 	 * Populates the block ID combo box with a list of all the block IDs
 	 */
	private void loadBlockIds()
	{
		List<String> ids = new ArrayList<>();
		
		for (String s : Block.getColors().keySet())
			ids.add(s);
		
		ids.sort(null);
		
		for (String s : ids)
			com_namespaceIds.addItem(s);
		
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
		String id = com_namespaceIds.getSelectedItem().toString();
		
		int color = Block.getBlockColor(id);
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
