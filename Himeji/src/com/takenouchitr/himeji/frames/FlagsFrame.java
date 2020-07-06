package com.takenouchitr.himeji.frames;

import com.takenouchitr.himeji.Himeji;
import com.takenouchitr.himeji.ListChangeListener;
import com.takenouchitr.himeji.MCCompat.Block;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class FlagsFrame extends JDialog implements ListChangeListener
{
	private int currentList;
	private DefaultListModel<String> model;
	private JComboBox<String> com_namespaceIDs;
	private JList<String> lst_blockNames;
	
	public FlagsFrame() 
	{
		currentList = Block.INVISIBLE_LIST;
		
		setTitle("Block Flags");
		setModal(true);
		setResizable(false);
		setSize(325, 345);
		
		Block.addListChangeListener(this);
		getContentPane().setLayout(null);
		
		com_namespaceIDs = new JComboBox<String>();
		com_namespaceIDs.setBounds(10, 180, 299, 20);
		getContentPane().add(com_namespaceIDs);
		
		JButton btn_add = new JButton("Add");
		btn_add.setBounds(134, 207, 71, 23);
		getContentPane().add(btn_add);
		
		JButton btn_remove = new JButton("Remove");
		btn_remove.setEnabled(false);
		btn_remove.setBounds(215, 207, 94, 23);
		getContentPane().add(btn_remove);
		
		JButton btn_done = new JButton("Done");
		btn_done.setBounds(244, 285, 65, 23);
		getContentPane().add(btn_done);
		
		model = new DefaultListModel<>();
		
		lst_blockNames = new JList<>(model);
		lst_blockNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lst_blockNames.setBorder(new LineBorder(new Color(0, 0, 0)));
		lst_blockNames.setBackground(Color.WHITE);
		lst_blockNames.setBounds(10, 11, 257, 158);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(lst_blockNames);
		scroll.setBounds(10, 11, 299, 158);
		getContentPane().add(scroll);
		
		JRadioButton rad_invisible = new JRadioButton("Invisible");
		rad_invisible.setSelected(true);
		rad_invisible.setBounds(10, 207, 109, 23);
		getContentPane().add(rad_invisible);
		
		JRadioButton rad_grass = new JRadioButton("Grass");
		rad_grass.setBounds(10, 233, 109, 23);
		getContentPane().add(rad_grass);
		
		JRadioButton rad_foliage = new JRadioButton("Foliage");
		rad_foliage.setBounds(10, 259, 109, 23);
		getContentPane().add(rad_foliage);
		
		JRadioButton rad_water = new JRadioButton("Water");
		rad_water.setBounds(10, 285, 109, 23);
		getContentPane().add(rad_water);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rad_invisible);
		buttonGroup.add(rad_grass);
		buttonGroup.add(rad_foliage);
		buttonGroup.add(rad_water);
		
		JButton btn_save = new JButton("Save to File");
		btn_save.setBounds(205, 233, 104, 23);
		getContentPane().add(btn_save);
		
		rad_invisible.addActionListener((e) -> loadList(Block.INVISIBLE_LIST));
		rad_grass.addActionListener((e) -> loadList(Block.GRASS_LIST));
		rad_foliage.addActionListener((e) -> loadList(Block.FOLIAGE_LIST));
		rad_water.addActionListener((e) -> loadList(Block.WATER_LIST));
		btn_add.addActionListener((e) -> addPress());
		btn_remove.addActionListener((e) -> removePress());
		btn_done.addActionListener((e) -> donePress());
		btn_save.addActionListener((e) -> Block.saveFlagFile(currentList));
		lst_blockNames.addListSelectionListener((e) ->
			btn_remove.setEnabled((lst_blockNames.getSelectedIndex()) >= 0));
		
		loadList(currentList);
		loadIDs();
	}
	
	private void loadList(int listID)
	{
		currentList = listID;
		List<String> ids = Block.getList(currentList);
		ids.sort(null);
		
		model.removeAllElements();
		for (String s : ids)
			model.addElement(s);
	}
	
	private void loadIDs()
	{
		List<String> ids = new ArrayList<>();
		
		for (String s : Block.getColors().keySet())
			ids.add(s);
		
		ids.sort(null);
		
		for (String s : ids)
			com_namespaceIDs.addItem(s);
	}
	
	private void addPress()
	{
		Block.addIdToList(com_namespaceIDs.getSelectedItem().toString(), 
			currentList);
	}
	
	private void removePress()
	{
		if (lst_blockNames.getSelectedIndex() == -1)
			return;
		
		Block.removeIdFromList(lst_blockNames.getSelectedValue(), currentList);
	}
	
	private void donePress()
	{
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	@Override
	public void OnItemAddition(String name, int listID)
	{
		if (listID == currentList)
			model.addElement(name);
		if (listID == Block.COLORS_LIST)
			com_namespaceIDs.addItem(name);
	}

	@Override
	public void OnItemUpdate(String oldName, String newName, int listID)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnItemRemoval(String name, int listID)
	{
		if (listID == currentList)
		{
			int size = model.getSize();
			for (int i = 0; i < size; i++)
			{
				if (model.elementAt(i).equals(name))
				{
					model.remove(i);
					break;
				}
			}
		}
		else if (listID == Block.COLORS_LIST)
		{
			com_namespaceIDs.addItem(name);
		}
	}
}
