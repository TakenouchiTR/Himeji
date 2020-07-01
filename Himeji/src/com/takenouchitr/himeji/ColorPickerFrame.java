package com.takenouchitr.himeji;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Window.Type;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class ColorPickerFrame extends JFrame 
{
	private JComboBox<String> com_namespaceIds;
	private JSpinner spn_r, spn_g, spn_b;
	private JPanel pnl_colorPreview;
	private JTextField txt_hex;
	private JButton btn_update;
	private JButton btn_save;
	private JButton btn_add;
	
	public ColorPickerFrame(Himeji himeji) 
	{
		setTitle("Color Editor");
		setType(Type.UTILITY);
		setSize(287,230);
		setResizable(false);
		getContentPane().setLayout(null);
		
		com_namespaceIds = new JComboBox<String>();
		com_namespaceIds.setBounds(10, 11, 257, 20);
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
		pnl_colorPreview.setBounds(132, 42, 135, 113);
		getContentPane().add(pnl_colorPreview);
		
		JLabel lbl_r = new JLabel("Red");
		lbl_r.setBounds(10, 76, 46, 14);
		getContentPane().add(lbl_r);
		
		JLabel lbl_g = new JLabel("Green");
		lbl_g.setBounds(10, 107, 46, 14);
		getContentPane().add(lbl_g);
		
		JLabel lbl_b = new JLabel("Blue");
		lbl_b.setBounds(10, 138, 46, 14);
		getContentPane().add(lbl_b);
		
		JLabel lbl_hex = new JLabel("Hex");
		lbl_hex.setBounds(10, 45, 46, 14);
		getContentPane().add(lbl_hex);
		
		txt_hex = new JTextField();
		txt_hex.setBounds(66, 42, 56, 20);
		getContentPane().add(txt_hex);
		txt_hex.setColumns(6);
		
		btn_update = new JButton("Update");
		btn_update.setBounds(10, 166, 75, 23);
		getContentPane().add(btn_update);
		
		btn_save = new JButton("Save to File");
		btn_save.setBounds(95, 166, 99, 23);
		getContentPane().add(btn_save);
		
		btn_add = new JButton("Add");
		btn_add.setBounds(204, 166, 63, 23);
		getContentPane().add(btn_add);
		
		com_namespaceIds.addActionListener((e) -> loadColors());
		spn_r.addChangeListener((e) -> updatePreview());
		spn_g.addChangeListener((e) -> updatePreview());
		spn_b.addChangeListener((e) -> updatePreview());
		txt_hex.addActionListener((e) -> convertHex());
		btn_update.addActionListener((e) -> saveChanges());
		btn_save.addActionListener((e) -> saveToFile());
		btn_add.addActionListener((e) -> openAddDialog());
		addWindowListener(new WindowListener() 
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
				himeji.setEnabled(true);
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
				
			}});
		
		loadBlockIds();
	}
	
	public void addBlockId(String id)
	{
		com_namespaceIds.addItem(id);
	}
	
	public void saveChanges()
	{
		int color = getColorInt();
		String id = com_namespaceIds.getSelectedItem().toString();
		Block.setBlockColor(id, color);
	}
	
	public void saveToFile()
	{
		Block.saveColorFile();
	}
	
	public void openAddDialog()
	{
		AddBlockFrame dialog = new AddBlockFrame(this);
		setEnabled(false);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
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
		}
		catch (Exception e)
		{
			
		}
		
		updatePreview();
	}
	
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
