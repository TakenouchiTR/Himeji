package com.takenouchitr.himeji.frames;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.takenouchitr.himeji.MCCompat.Block;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;

public class AddBlockFrame extends JFrame
{
	private JTextField txt_id;
	private ColorPickerFrame colorFrame;
	
	public AddBlockFrame(ColorPickerFrame colorFrame) 
	{
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.colorFrame = colorFrame;
		
		setSize(275, 110);
		setTitle("New Block ID");
		setType(Type.UTILITY);
		getContentPane().setLayout(null);
		
		txt_id = new JTextField();
		txt_id.setBounds(30, 11, 219, 20);
		getContentPane().add(txt_id);
		txt_id.setColumns(10);
		
		JLabel lbl_id = new JLabel("ID");
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
		addWindowListener(new WindowListener()
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
				colorFrame.setEnabled(true);
			}

			@Override
			public void windowDeactivated(WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0)
			{
				
			}

			@Override
			public void windowIconified(WindowEvent arg0)
			{
				
			}

			@Override
			public void windowOpened(WindowEvent arg0)
			{
				
			}
			
		});
	}
	
	private void addPress()
	{
		String id = txt_id.getText().toLowerCase();
		
		if (Block.idExists(id))
		{
			JOptionPane.showMessageDialog(this, "ID already exists.");
		}
		else
		{
			Block.setBlockColor(id, 0xFF000000);
			colorFrame.setEnabled(true);
			dispose();
		}
	}
	
	private void cancelPress()
	{
		colorFrame.setEnabled(true);
		dispose();
	}
}
