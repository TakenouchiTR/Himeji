package com.github.takenouchitr;
//Program Name:   Himeji.java
//Date:           3/13/2020
//Programmer:     Shawn Carter
//Description:    This program creates a map image of a Minecraft world.

import javax.swing.*;
import java.awt.event.*;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class Himeji extends JFrame implements ActionListener, ItemListener, WindowListener
{
	public static final boolean SHOW_ALL_EVENTS = true;
	
	private static final int MIN_VALUE = Integer.MIN_VALUE + 32;
	private static final int MAX_VALUE = Integer.MAX_VALUE - 32;
	
	private static JButton btn_folder, btn_start, btn_setBounds, btn_output;
	private static JTextField txt_worldPath, txt_output;
	private static JCheckBox chk_renderUnderWater, chk_renderShadows, chk_renderBiomes;
	private static JMenuBar bar_menu;
	private static BoundsFrame boundsFrame;
	private static JPanel pnl_log;
	private static JLabel lbl_log;
	
	public static void main(String[] args) 
	{
		Block.setBlockVisibility();
		Block.setBlockColors();
		Block.setBlockDict();
		Block.setBiomeColors();
		Block.setBiomeFoliage();
		Block.setBiomeWater();
		Block.setBiomeGrass();
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run() 
			{
				Himeji window = new Himeji();
				window.setVisible(true);
			}
		});
	}
	
	public static int getStartY()
	{
		return boundsFrame.getMaxY();
	}
	
	public static int getEndY()
	{
		return boundsFrame.getMinY();
	}
	
	public static int getMaxX()
	{
		return boundsFrame.getMaxX();
	}
	
	public static int getMinX()
	{
		return boundsFrame.getMinX();
	}
	
	public static int getMaxZ()
	{
		return boundsFrame.getMaxZ();
	}
	
	public static int getMinZ()
	{
		return boundsFrame.getMinZ();
	}
	
	public static void disableComponents()
	{
		btn_start.setEnabled(false);
		btn_folder.setEnabled(false);
		btn_setBounds.setEnabled(false);
		btn_output.setEnabled(false);
		chk_renderUnderWater.setEnabled(false);
		chk_renderShadows.setEnabled(false);
		chk_renderBiomes.setEnabled(false);
		txt_worldPath.setEnabled(false);
		txt_output.setEnabled(false);
	}
	
	public static void enableComponents()
	{
		btn_start.setEnabled(true);
		btn_folder.setEnabled(true);
		btn_setBounds.setEnabled(true);
		btn_output.setEnabled(true);
		chk_renderUnderWater.setEnabled(true);
		chk_renderShadows.setEnabled(true);
		chk_renderBiomes.setEnabled(true);
		txt_worldPath.setEnabled(true);
		txt_output.setEnabled(true);
	}
	
 	public static BoundsFrame getBoundsFrame()
	{
		return boundsFrame;
	}
	
	public static String getPath()
	{
		return txt_worldPath.getText();
	}
	
 	public static boolean renderUnderWater()
	{
		return chk_renderUnderWater.isSelected();
	}
	
	public static boolean renderShadows()
	{
		return chk_renderShadows.isSelected();
	}
	
	public static boolean renderBiomeColors()
	{
		return chk_renderBiomes.isSelected();
	}
	
	public static void displayMessage(String message)
	{
		lbl_log.setText(message);
	}
	
	public Himeji()
	{
		super("Himeji Map Viewer");
		
		setResizable(false);
		setLocationRelativeTo(null);
		
		boundsFrame = new BoundsFrame();
		boundsFrame.addWindowListener(this);
		
		//construct preComponents
        JMenu fileMenu = new JMenu("File");

        //construct components
        btn_folder = new JButton("World");
        txt_worldPath = new JTextField(400);
        chk_renderUnderWater = new JCheckBox("Render Underwater Blocks");
        chk_renderUnderWater.setSelected(true);
        chk_renderShadows = new JCheckBox("Render Shadows");
        chk_renderShadows.setSelected(true);
        chk_renderBiomes = new JCheckBox("Render Biome Colors");
        chk_renderBiomes.setSelected(true);
        bar_menu = new JMenuBar();
        bar_menu.add(fileMenu);
        btn_start = new JButton("Start");

        //adjust size and set layout
        setSize(500, 233);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add components
        getContentPane().add(btn_folder);
        getContentPane().add(txt_worldPath);
        getContentPane().add(chk_renderUnderWater);
        getContentPane().add(chk_renderShadows);
        getContentPane().add(chk_renderBiomes);
        getContentPane().add(bar_menu);
        getContentPane().add(btn_start);

        //set component bounds (only needed by Absolute Positioning)
        btn_folder.setBounds(15, 35, 80, 25);
        txt_worldPath.setBounds(105, 35, 370, 25);
        chk_renderUnderWater.setBounds(15, 108, 181, 25);
        chk_renderShadows.setBounds(15, 133, 181, 25);
        chk_renderBiomes.setBounds(15, 158, 181, 20);
        bar_menu.setBounds(0, 0, 495, 25);
        btn_start.setBounds(415, 153, 65, 25);
        
        btn_setBounds = new JButton("Set Bounds");
        btn_setBounds.addActionListener(this);
        btn_setBounds.setBounds(302, 154, 107, 23);
        getContentPane().add(btn_setBounds);
        
        btn_output = new JButton("Output");
        btn_output.setBounds(15, 72, 80, 25);
        getContentPane().add(btn_output);
        
        txt_output = new JTextField();
        txt_output.setBounds(105, 73, 370, 25);
        getContentPane().add(txt_output);
        txt_output.setColumns(10);
        
        pnl_log = new JPanel();
        pnl_log.setBackground(SystemColor.activeCaptionBorder);
        pnl_log.setBorder(new LineBorder(new Color(0, 0, 0)));
        FlowLayout fl_pnl_log = (FlowLayout) pnl_log.getLayout();
        fl_pnl_log.setVgap(0);
        fl_pnl_log.setAlignment(FlowLayout.LEFT);
        pnl_log.setBounds(-1, 187, 496, 17);
        getContentPane().add(pnl_log);
        
        lbl_log = new JLabel("Welcome!");
        pnl_log.add(lbl_log);
        
        //set listeners
        btn_folder.addActionListener(this);
        btn_start.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if (source == btn_folder)
		{
			
		}
		else if (source == btn_start)
		{
			MapWorker worker = new MapWorker();
			try
			{
				disableComponents();
				worker.execute();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{
			setEnabled(false);
			boundsFrame.setLocationRelativeTo(this);
			boundsFrame.setVisible(true);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) 
	{
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) 
	{
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		setEnabled(true);
		this.toFront();
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
