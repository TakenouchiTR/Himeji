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

package com.takenouchitr.himeji.frames;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Properties;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.takenouchitr.himeji.Himeji;
import com.takenouchitr.himeji.Property;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.FlowLayout;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class BoundsFrame extends JDialog
{
	private static final String BOUNDS_FOLDER = "bounds\\";
	
	private JButton btn_confirm;
	private JCheckBox chk_chunks;
	private JSpinner spn_maxX, spn_minX, spn_maxZ, spn_minZ, spn_maxY, spn_minY;
	private JRadioButton rad_overworld, rad_nether, rad_end;
	private Properties props;
	
	public BoundsFrame(Properties props, Himeji himeji) 
	{
		this.props = props;
		
		setTitle("Set Bounds");
		setFont(new Font("Tahoma", Font.PLAIN, 11));
		setSize(440, 204);
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(null);
		
		JPanel pnl_dimension = new JPanel();
		pnl_dimension.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnl_dimension.setBounds(211, 11, 94, 116);
		getContentPane().add(pnl_dimension);
		pnl_dimension.setLayout(new BorderLayout(0, 0));
		
		JPanel pnl_dimensionTop = new JPanel();
		pnl_dimensionTop.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnl_dimension.add(pnl_dimensionTop, BorderLayout.NORTH);
		
		JLabel lbl_dimension = new JLabel("Dimension");
		pnl_dimensionTop.add(lbl_dimension);
		lbl_dimension.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JPanel pnl_dimensionCenter = new JPanel();
		pnl_dimensionCenter.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnl_dimension.add(pnl_dimensionCenter, BorderLayout.CENTER);
		pnl_dimensionCenter.setLayout(new GridLayout(3, 1, 0, 0));
		
		rad_overworld = new JRadioButton("Overworld");
		pnl_dimensionCenter.add(rad_overworld);
		
		rad_nether = new JRadioButton("Nether");
		pnl_dimensionCenter.add(rad_nether);
		
		rad_end = new JRadioButton("End");
		pnl_dimensionCenter.add(rad_end);
		
		JPanel pnl_yBounds = new JPanel();
		pnl_yBounds.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnl_yBounds.setBounds(315, 11, 94, 73);
		getContentPane().add(pnl_yBounds);
		pnl_yBounds.setLayout(new BorderLayout(0, 0));
		
		JPanel pnl_yBoundsTop = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnl_yBoundsTop.getLayout();
		flowLayout_1.setVgap(5);
		pnl_yBoundsTop.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnl_yBounds.add(pnl_yBoundsTop, BorderLayout.NORTH);
		
		JLabel lbl_yBounds = new JLabel("Y Bounds");
		lbl_yBounds.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnl_yBoundsTop.add(lbl_yBounds);
		
		JPanel pnl_yBoundsCenter = new JPanel();
		pnl_yBoundsCenter.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnl_yBounds.add(pnl_yBoundsCenter, BorderLayout.CENTER);
		pnl_yBoundsCenter.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lbl_yMax = new JLabel("  Max Y");
		lbl_yMax.setToolTipText("Highest elevation to be drawn.");
		pnl_yBoundsCenter.add(lbl_yMax);
		
		spn_maxY = new JSpinner();
		spn_maxY.setModel(new SpinnerNumberModel(255, 0, 255, 1));
		spn_maxY.addChangeListener((e) -> spinnerPairTopChange(spn_maxY, spn_minY));
		pnl_yBoundsCenter.add(spn_maxY);
		
		JLabel lbl_yMin = new JLabel("  Min Y");
		lbl_yMin.setToolTipText("Lowest elevation to be drawn.");
		pnl_yBoundsCenter.add(lbl_yMin);
		
		spn_minY = new JSpinner();
		spn_minY.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		spn_minY.addChangeListener((e) -> spinnerPairBottomChange(spn_maxY, spn_minY));
		pnl_yBoundsCenter.add(spn_minY);
		
		JPanel pnl_chunks = new JPanel();
		pnl_chunks.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnl_chunks.setBounds(10, 11, 191, 116);
		getContentPane().add(pnl_chunks);
		pnl_chunks.setLayout(new BorderLayout(0, 0));
		
		JPanel pnl_chunksTop = new JPanel();
		pnl_chunksTop.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnl_chunks.add(pnl_chunksTop, BorderLayout.NORTH);
		
		JLabel lbl_chunks = new JLabel("Render Area");
		lbl_chunks.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnl_chunksTop.add(lbl_chunks);
		
		JPanel pnl_chunksBottom = new JPanel();
		pnl_chunksBottom.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) pnl_chunksBottom.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.LEFT);
		pnl_chunks.add(pnl_chunksBottom, BorderLayout.SOUTH);
		
		chk_chunks = new JCheckBox("Limit render to area selection");
		chk_chunks.setToolTipText("Render one the area within the specified chunk bounds.");
		chk_chunks.addItemListener(new ItemListener() 
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				boolean isChecked = e.getStateChange() == ItemEvent.SELECTED;
				
				spn_maxX.setEnabled(isChecked);
				spn_minX.setEnabled(isChecked);
				spn_maxZ.setEnabled(isChecked);
				spn_minZ.setEnabled(isChecked);
			}
			
		});

		pnl_chunksBottom.add(chk_chunks);
		
		JPanel pnl_chunksCenter = new JPanel();
		pnl_chunks.add(pnl_chunksCenter, BorderLayout.CENTER);
		pnl_chunksCenter.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnl_chunksCenter.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lbl_maxX = new JLabel(" Max Chunk X");
		lbl_maxX.setToolTipText("Furthest chunk to the west.");
		pnl_chunksCenter.add(lbl_maxX);
		
		spn_maxX = new JSpinner();
		spn_maxX.setEnabled(false);
		spn_maxX.addChangeListener((e) -> spinnerPairTopChange(spn_maxX, spn_minX));
		pnl_chunksCenter.add(spn_maxX);
		
		JLabel lbl_minX = new JLabel(" Min Chunk X");
		lbl_minX.setToolTipText("Furthest chunk to the east.");
		pnl_chunksCenter.add(lbl_minX);
		
		spn_minX = new JSpinner();
		spn_minX.setEnabled(false);
		spn_minX.addChangeListener((e) -> spinnerPairBottomChange(spn_maxX, spn_minX));
		pnl_chunksCenter.add(spn_minX);
		
		JLabel lbl_maxZ = new JLabel(" Max Chunk Z");
		lbl_maxZ.setToolTipText("Furthest chunk to the south.");
		pnl_chunksCenter.add(lbl_maxZ);
		
		spn_maxZ = new JSpinner();
		spn_maxZ.setEnabled(false);
		spn_maxZ.addChangeListener((e) -> spinnerPairTopChange(spn_maxZ, spn_minZ));
		pnl_chunksCenter.add(spn_maxZ);
		
		JLabel lbl_minZ = new JLabel(" Min Chunk Z");
		lbl_minZ.setToolTipText("Furthest chunk to the North.");
		pnl_chunksCenter.add(lbl_minZ);
		
		spn_minZ = new JSpinner();
		spn_minZ.addChangeListener((e) -> spinnerPairBottomChange(spn_maxZ, spn_minZ));
		spn_minZ.setEnabled(false);
		pnl_chunksCenter.add(spn_minZ);
		
		btn_confirm = new JButton("Confirm");
		btn_confirm.addActionListener((e) -> 
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
		btn_confirm.setBounds(315, 132, 94, 23);
		getContentPane().add(btn_confirm);
		
		ButtonGroup dimensionGroup = new ButtonGroup();
		dimensionGroup.add(rad_overworld);
		dimensionGroup.add(rad_nether);
		dimensionGroup.add(rad_end);
		
		JButton btn_save = new JButton("Save Area");
		btn_save.setBounds(10, 132, 99, 23);
		getContentPane().add(btn_save);
		
		JButton btn_load = new JButton("Load Area");
		btn_load.setBounds(119, 132, 99, 23);
		getContentPane().add(btn_load);
		
		btn_save.addActionListener((e) -> savePress());
		btn_load.addActionListener((e) -> loadPress());
		this.addWindowListener(new WindowListener() 
		{

			@Override
			public void windowActivated(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent arg0)
			{
				if (rad_overworld.isSelected())
					props.setProperty(Property.DIMENSION.key, "Overworld");
				else if (rad_nether.isSelected())
					props.setProperty(Property.DIMENSION.key, "Nether");
				else
					props.setProperty(Property.DIMENSION.key, "End");
				
				
				props.setProperty(Property.START_Y.key, "" + ((int)spn_maxY.getValue()));
				props.setProperty(Property.END_Y.key, "" + ((int)spn_minY.getValue()));
				props.setProperty(Property.START_X.key, "" + ((int)spn_maxX.getValue()));
				props.setProperty(Property.END_X.key, "" + ((int)spn_minX.getValue()));
				props.setProperty(Property.START_Z.key, "" + ((int)spn_maxZ.getValue()));
				props.setProperty(Property.END_Z.key, "" + ((int)spn_minZ.getValue()));
				
				props.setProperty(Property.USE_AREA.key, "" + chk_chunks.isSelected());
			}

			@Override
			public void windowDeactivated(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}});
		
		loadProperties();
	}

	private void loadPress()
	{
		File folder = new File(Himeji.DATA_FOLDER + BOUNDS_FOLDER);
		if (!folder.exists())
			folder.mkdir();
		
		JFileChooser fileChooser = new JFileChooser(Himeji.DATA_FOLDER + BOUNDS_FOLDER);
		//fileChooser.setFileFilter(new FileNameExtensionFilter("Comma Separated Value (.csv)", 
		//		".csv", ".CSV"));
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			
			try
			{
				Object[] split = Files.readAllLines(file.toPath()).toArray(); 
				
				spn_maxX.setValue(Integer.parseInt((split[0].toString())));
				spn_minX.setValue(Integer.parseInt((split[1].toString())));
				spn_maxY.setValue(Integer.parseInt((split[2].toString())));
				spn_minY.setValue(Integer.parseInt((split[3].toString())));
				spn_maxZ.setValue(Integer.parseInt((split[4].toString())));
				spn_minZ.setValue(Integer.parseInt((split[5].toString())));
				chk_chunks.setSelected(Boolean.parseBoolean(split[6].toString()));
				switch (split[7].toString())
				{
					case "Nether":
						rad_nether.setSelected(true);
						break;
					case "End":
						rad_end.setSelected(true);
						break;
					default:
						rad_overworld.setSelected(true);
						break;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void savePress()
	{
		File folder = new File(Himeji.DATA_FOLDER + BOUNDS_FOLDER);
		if (!folder.exists())
			folder.mkdir();
		
		JFileChooser fileChooser = new JFileChooser(Himeji.DATA_FOLDER + BOUNDS_FOLDER);
		fileChooser.setFileFilter(new FileNameExtensionFilter("Comma Separated Value (.csv)", ".csv"));
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			String path = file.getPath();
			if (!path.endsWith(".csv"))
				path += ".csv";
			
			File saveFile = new File(path);
			try
			{
				FileWriter writer = new FileWriter(saveFile);
				
				writer.write(spn_maxX.getValue() + "\n");
				writer.write(spn_minX.getValue() + "\n");
				writer.write(spn_maxY.getValue() + "\n");
				writer.write(spn_minY.getValue() + "\n");
				writer.write(spn_maxZ.getValue() + "\n");
				writer.write(spn_minZ.getValue() + "\n");
				writer.write(chk_chunks.isSelected() + "\n");
				
				if (rad_overworld.isSelected())
					writer.write("Overworld");
				else if (rad_nether.isSelected())
					writer.write("Nether");
				else
					writer.write("End");
				
				writer.close();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void loadProperties()
	{
		switch (props.getProperty(Property.DIMENSION.key))
		{
			case "Overworld":
				rad_overworld.setSelected(true);
				break;
			case "Nether":
				rad_nether.setSelected(true);
				break;
			case "End":
				rad_end.setSelected(true);
				break;
		}
		
		spn_maxY.setValue(Integer.parseInt(props.getProperty(Property.START_Y.key)));
		spn_minY.setValue(Integer.parseInt(props.getProperty(Property.END_Y.key)));
		spn_maxX.setValue(Integer.parseInt(props.getProperty(Property.START_X.key)));
		spn_minX.setValue(Integer.parseInt(props.getProperty(Property.END_X.key)));
		spn_maxZ.setValue(Integer.parseInt(props.getProperty(Property.START_Z.key)));
		spn_minZ.setValue(Integer.parseInt(props.getProperty(Property.END_Z.key)));
		
		chk_chunks.setSelected(Boolean.parseBoolean(props.getProperty(Property.USE_AREA.key)));
	}
	
	private void spinnerPairTopChange(JSpinner top, JSpinner bottom)
	{
		int max = (int) top.getValue();
		int min = (int) bottom.getValue();
		
		if (max < min)
			bottom.setValue(max);
	}
	
	private void spinnerPairBottomChange(JSpinner top, JSpinner bottom)
	{
		int max = (int) top.getValue();
		int min = (int) bottom.getValue();
		
		if (min > max)
			top.setValue(min);
	}
}