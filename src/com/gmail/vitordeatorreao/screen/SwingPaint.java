package com.gmail.vitordeatorreao.screen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.gmail.vitordeatorreao.scene.NonConformantSceneFile;
import com.gmail.vitordeatorreao.scene.SceneController;

/**
 * <code>SwingPaint</code> implements the main loop of the program.
 * <p>
 * This code is available through the 
 * <a href="http://www.gnu.org/licenses/gpl-2.0.html">GNU GPL v2.0</a> license.
 * <br>
 * You can acess the full project at 
 * <a href="https://github.com/vitordeatorreao/bcgproject1">GitHub</a>.
 * @author	<a href="https://github.com/vitordeatorreao/">V&iacute;tor de 
 * 			Albuquerque Torre&atilde;o</a>
 * @version 1.0
 * @since 1.0
 */
public class SwingPaint {
	
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	
	private static JFrame frame;
	private static PaintablePanel paintablePanel;

	/**
	 * The main loop of the program.
	 * Reads user input and displays all loaded objects on the screen.
	 * @param args
	 */
	public static void main(String[] args) {
		
//		File file = new File("vaso.byu");
//		
//		if(args.length > 0) {
//			file = new File(args[0]);
//		}
//		
//		try {
//			SceneController.getInstance().loadScene(file);
//		} catch (IOException | NonConformantSceneFile e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				
				createAndShowGUI();
				
			}
		});

	}

	/**
	 * Creates a new JFrame with a <code>PaintablePanel</code> added to it.
	 */
	private static void createAndShowGUI() {
		
		frame = new JFrame("Vitor Torreao CG Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        
        //Added Paintable Panel
        paintablePanel = new PaintablePanel();
        frame.add(paintablePanel, BorderLayout.CENTER);
        
        //Create menu bar
        JMenuBar menuBar = new JMenuBar();
        ImageIcon openIcon;
        ImageIcon exitIcon;
        try {
        	openIcon = new ImageIcon(SwingPaint.class.getResource(
        			"/com/gmail/vitordeatorreao/images/open.png"));
        	exitIcon = new ImageIcon(SwingPaint.class.getResource(
        			"/com/gmail/vitordeatorreao/images/exit.png"));
        } catch (Exception e){
        	openIcon = new ImageIcon("open.png");
        	exitIcon = new ImageIcon("exit.png");
        }
        
        //Create Open File Menu Item
        JMenu file = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open", openIcon);
        openMenuItem.setToolTipText("Load objects from BYU files");
        openMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser openFile = new JFileChooser();
				int returnVal = openFile.showOpenDialog(null);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = openFile.getSelectedFile();
					try {
						SceneController.getInstance().loadScene(file);
						paintablePanel.repaint();
					} catch (IOException | NonConformantSceneFile e) {
						JOptionPane.showMessageDialog(frame, 
								e.getMessage(), "Error while opening file", 
								JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
        
        //Create Exit program menu item
        JMenuItem exitMenuItem = new JMenuItem("Exit", exitIcon);
        exitMenuItem.setToolTipText("Exit application");
        exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		});
        
        //Add menu items to Menu
        file.add(openMenuItem);
        file.add(exitMenuItem);
        
        //Add menu to menu bar
        menuBar.add(file);
        
        //Set the JFrame MenuBar to the menu bar created
        frame.setJMenuBar(menuBar);
        
        frame.setVisible(true);
		
	}

}
