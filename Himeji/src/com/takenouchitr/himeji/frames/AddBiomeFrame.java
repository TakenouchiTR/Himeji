package com.takenouchitr.himeji.frames;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.takenouchitr.himeji.Himeji;
import com.takenouchitr.himeji.MCCompat.Block;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;

@SuppressWarnings("serial")
public class AddBiomeFrame extends JDialog
{
	private JTextField txt_name;
	private JSpinner spn_id;
	
	public AddBiomeFrame() 
	{
		setResizable(false);
		setTitle("New Biome");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 100);
		setModal(true);
		getContentPane().setLayout(null);
		
		txt_name = new JTextField();
		txt_name.setColumns(10);
		txt_name.setBounds(114, 11, 219, 20);
		getContentPane().add(txt_name);
		
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.setBounds(180, 40, 75, 23);
		getContentPane().add(btn_cancel);
		
		JButton btn_add = new JButton("Add");
		btn_add.setBounds(265, 40, 68, 23);
		getContentPane().add(btn_add);
		
		JLabel lbl_id = new JLabel("ID");
		lbl_id.setToolTipText("The numeric ID for the biome.");
		lbl_id.setBounds(10, 14, 46, 14);
		getContentPane().add(lbl_id);
		
		spn_id = new JSpinner();
		spn_id.setBounds(25, 11, 46, 20);
		getContentPane().add(spn_id);
		
		JLabel lbl_name = new JLabel("Name");
		lbl_name.setToolTipText("The name associaed with the biome, used purely for easier identification.");
		lbl_name.setBounds(81, 14, 46, 14);
		getContentPane().add(lbl_name);
		
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
				Himeji.setComponentsEnabled(true);
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

	private void cancelPress()
	{
		dispose();
	}

	private void addPress()
	{
		int id = (int) spn_id.getValue();
		
		if (Block.biomeExists(id))
		{
			JOptionPane.showMessageDialog(this, "Biome with ID " + id + " already exists.");
			return;
		}
		
		String name = txt_name.getText().trim();
		
		if (name.length() == 0)
		{
			JOptionPane.showMessageDialog(this, "Please enter a biome name");
			return;
		}
		
		Block.addBiome(id, name);
	}
}
