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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Properties;

import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class BoundsFrame extends JFrame implements ActionListener, ItemListener, ChangeListener, WindowListener
{
	private JButton btn_confirm;
	private JCheckBox chk_chunks;
	private JSpinner spn_maxX, spn_minX, spn_maxZ, spn_minZ, spn_maxY, spn_minY;
	private JRadioButton rad_overworld, rad_nether, rad_end;
	private Properties props;
	private Himeji himeji;
	
	public BoundsFrame(Properties props, Himeji himeji) 
	{
		this.props = props;
		this.himeji = himeji;
		
		setType(Type.UTILITY);
		setTitle("Set Bounds");
		setFont(new Font("Tahoma", Font.PLAIN, 11));
		setSize(440, 175);
		setResizable(false);
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
		spn_maxY.addChangeListener(this);
		pnl_yBoundsCenter.add(spn_maxY);
		
		JLabel lbl_yMin = new JLabel("  Min Y");
		lbl_yMin.setToolTipText("Lowest elevation to be drawn.");
		pnl_yBoundsCenter.add(lbl_yMin);
		
		spn_minY = new JSpinner();
		spn_minY.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		spn_minY.addChangeListener(this);
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
		
		chk_chunks = new JCheckBox("Use render area");
		chk_chunks.setToolTipText("Render one the area within the specified chunk bounds.");
		chk_chunks.addItemListener(this);
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
		spn_maxX.addChangeListener(this);
		pnl_chunksCenter.add(spn_maxX);
		
		JLabel lbl_minX = new JLabel(" Min Chunk X");
		lbl_minX.setToolTipText("Furthest chunk to the east.");
		pnl_chunksCenter.add(lbl_minX);
		
		spn_minX = new JSpinner();
		spn_minX.setEnabled(false);
		spn_minX.addChangeListener(this);
		pnl_chunksCenter.add(spn_minX);
		
		JLabel lbl_maxZ = new JLabel(" Max Chunk Z");
		lbl_maxZ.setToolTipText("Furthest chunk to the south.");
		pnl_chunksCenter.add(lbl_maxZ);
		
		spn_maxZ = new JSpinner();
		spn_maxZ.setEnabled(false);
		spn_maxZ.addChangeListener(this);
		pnl_chunksCenter.add(spn_maxZ);
		
		JLabel lbl_minZ = new JLabel(" Min Chunk Z");
		lbl_minZ.setToolTipText("Furthest chunk to the North.");
		pnl_chunksCenter.add(lbl_minZ);
		
		spn_minZ = new JSpinner();
		spn_minZ.addChangeListener(this);
		spn_minZ.setEnabled(false);
		pnl_chunksCenter.add(spn_minZ);
		
		btn_confirm = new JButton("Confirm");
		btn_confirm.addActionListener(this);
		btn_confirm.setBounds(315, 104, 94, 23);
		getContentPane().add(btn_confirm);
		
		ButtonGroup dimensionGroup = new ButtonGroup();
		dimensionGroup.add(rad_overworld);
		dimensionGroup.add(rad_nether);
		dimensionGroup.add(rad_end);
		
		addWindowListener(this);
		
		loadProperties();
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
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	@Override
	public void stateChanged(ChangeEvent e) 
	{
		Object source = e.getSource();
		
		if (source == spn_maxY)
			spinnerPairTopChange(spn_maxY, spn_minY);
		else if (source == spn_minY)
			spinnerPairBottomChange(spn_maxY, spn_minY);
		else if (source == spn_maxX)
			spinnerPairTopChange(spn_maxX, spn_minX);
		else if (source == spn_minX)
			spinnerPairBottomChange(spn_maxX, spn_minX);
		else if (source == spn_maxZ)
			spinnerPairTopChange(spn_maxZ, spn_minZ);
		else if (source == spn_minZ)
			spinnerPairBottomChange(spn_maxZ, spn_minZ);
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		boolean isChecked = e.getStateChange() == ItemEvent.SELECTED;
		
		spn_maxX.setEnabled(isChecked);
		spn_minX.setEnabled(isChecked);
		spn_maxZ.setEnabled(isChecked);
		spn_minZ.setEnabled(isChecked);
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
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) 
	{
		himeji.setEnabled(true);
	}

	@Override
	public void windowDeactivated(WindowEvent e) 
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
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}