package com.takenouchitr.himeji.frames;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.takenouchitr.himeji.MCCompat.Block;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;

public class AddBlockFrame extends JDialog
{
	private JTextField txt_id;
	
	public AddBlockFrame() 
	{
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setSize(275, 110);
		setTitle("New Block ID");
		getContentPane().setLayout(null);
		setModal(true);
		
		txt_id = new JTextField();
		txt_id.setBounds(30, 11, 219, 20);
		getContentPane().add(txt_id);
		txt_id.setColumns(10);
		
		JLabel lbl_id = new JLabel("ID");
		lbl_id.setToolTipText("The namespaced block ID.");
		lbl_id.setBounds(10, 14, 46, 14);
		getContentPane().add(lbl_id);
		
		JButton btn_add = new JButton("Add");
		btn_add.setBounds(181, 40, 68, 23);
		getContentPane().add(btn_add);
		
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.setBounds(96, 40, 75, 23);
		getContentPane().add(btn_cancel);
		
		btn_add.addActionListener((e) -> addPress());
		btn_cancel.addActionListener((e) -> cancelPress());
	}
	
	private void addPress()
	{
		String id = txt_id.getText().toLowerCase();
		
		if (Block.idExists(id))
			JOptionPane.showMessageDialog(this, "ID already exists.");
		else
			Block.setBlockColor(id, 0xFF000000);
	}
	
	private void cancelPress()
	{
		dispose();
	}
}
