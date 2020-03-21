//Program Name:   Himeji.java
//Date:           3/13/2020
//Programmer:     Shawn Carter
//Description:    This program creates a map image of a Minecraft world.

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

@SuppressWarnings("serial")
public class Himeji extends JFrame implements ActionListener
{
	public static final boolean SHOW_ALL_EVENTS = true;
	private static JButton btn_folder, btn_start;
	private static JTextField txt_worldPath;
	private static JCheckBox chk_renderUnderWater, chk_renderShadows, chk_renderBiomes;
	private static JMenuBar bar_menu;
	private static JSpinner spn_startY;
	
	public Himeji()
	{
		super("Himeji Map Viewer");
		
		//construct preComponents
        JMenu fileMenu = new JMenu("File");
        JMenuItem printItem = new JMenuItem("Print");
        fileMenu.add(printItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        JMenu helpMenu = new JMenu("Help");
        JMenuItem contentsItem = new JMenuItem("Contents");
        helpMenu.add(contentsItem);
        JMenuItem aboutItem = new JMenuItem("About");
        helpMenu.add(aboutItem);
        SpinnerModel spinnerModel = new SpinnerNumberModel(Chunk.CHUNK_HEIGHT - 1,
        												   0,
        												   Chunk.CHUNK_HEIGHT - 1,
        												   16);

        //construct components
        btn_folder = new JButton("World");
        txt_worldPath = new JTextField(400);
        chk_renderUnderWater = new JCheckBox("Render Underwater Blocks");
        chk_renderShadows = new JCheckBox("Render Shadows");
        chk_renderBiomes = new JCheckBox("Render Biome Colors");
        bar_menu = new JMenuBar();
        bar_menu.add(fileMenu);
        bar_menu.add(helpMenu);
        btn_start = new JButton("Start");
        spn_startY = new JSpinner(spinnerModel);

        //adjust size and set layout
        setSize(500, 175);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add components
        add(btn_folder);
        add(txt_worldPath);
        add(chk_renderUnderWater);
        add(chk_renderShadows);
        add(chk_renderBiomes);
        add(bar_menu);
        add(btn_start);
        add(spn_startY);

        //set component bounds (only needed by Absolute Positioning)
        btn_folder.setBounds(15, 35, 80, 25);
        txt_worldPath.setBounds(105, 35, 370, 25);
        chk_renderUnderWater.setBounds(10, 65, 180, 25);
        chk_renderShadows.setBounds(10, 85, 125, 25);
        chk_renderBiomes.setBounds(10, 110, 155, 20);
        bar_menu.setBounds(0, 0, 495, 25);
        btn_start.setBounds(410, 105, 65, 25);
        spn_startY.setBounds(405, 65, 70, 25);
        
        //set listeners
        btn_folder.addActionListener(this);
        btn_start.addActionListener(this);
	}
	
	public static void main(String[] args) 
	{
		Block.setBlockVisibility();
		Block.setBlockColors();
		Block.setBlockDict();
		Block.setBiomeColors();
		Block.setBiomeBlocks();
		Block.setBiomeGrass();
		
		Himeji window = new Himeji();
		window.setVisible(true);
	}
	
	public static int getStartY()
	{
		Integer result =(Integer) spn_startY.getValue(); 
		return result.intValue();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if (source == btn_folder)
		{
			
		}
		else
		{
			File file = new File(txt_worldPath.getText());
			if (World.validateDir(file)) 
			{
				long start = System.currentTimeMillis();
				
				World currentWorld = new World(file);
				
				currentWorld.createRenderGrids();
				currentWorld.renderWorld();
				
				long end = System.currentTimeMillis();
				
				System.out.println(end - start);
			}
		}
	}
}
