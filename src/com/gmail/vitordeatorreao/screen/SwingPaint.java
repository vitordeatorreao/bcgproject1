package com.gmail.vitordeatorreao.screen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

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
	
	private static boolean showVertices, showEdges, showFaces;

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
        ImageIcon appIcon;
        ImageIcon openIcon;
        ImageIcon saveIcon;
        ImageIcon exitIcon;
        try {
        	appIcon = new ImageIcon(SwingPaint.class.getResource(
        			"/com/gmail/vitordeatorreao/images/logo.png"));
        	openIcon = new ImageIcon(SwingPaint.class.getResource(
        			"/com/gmail/vitordeatorreao/images/open.png"));
        	saveIcon = new ImageIcon(SwingPaint.class.getResource(
        			"/com/gmail/vitordeatorreao/images/save.png"));
        	exitIcon = new ImageIcon(SwingPaint.class.getResource(
        			"/com/gmail/vitordeatorreao/images/exit.png"));
        } catch (Exception e){
        	appIcon = new ImageIcon("logo.png");
        	openIcon = new ImageIcon("open.png");
        	saveIcon = new ImageIcon("save.png");
        	exitIcon = new ImageIcon("exit.png");
        }
        
        frame.setIconImage(appIcon.getImage());
        
        JMenu file = new JMenu("File");
        
        //Create Open File Menu Item
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
        
        //Create Save menu item
        JMenuItem saveMenuItem = new JMenuItem("Save", saveIcon);
        saveMenuItem.setToolTipText("Save view into an image file");
        saveMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser saveFile = new JFileChooser();
				saveFile.addChoosableFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						return "*.png, *.PNG";
					}
					
					@Override
					public boolean accept(File arg0) {
						if (arg0.isDirectory()) {
							return false;
						}
						String filename = arg0.getName();
						return filename.endsWith(".png") || 
								filename.endsWith(".PNG");
					}
				});
				int returnVal = saveFile.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = saveFile.getSelectedFile();
					BufferedImage im = new BufferedImage(
							paintablePanel.getWidth(), 
							paintablePanel.getHeight(), 
							BufferedImage.TYPE_INT_RGB);
					paintablePanel.paint(im.getGraphics());
					
					try {
						ImageIO.write(im, "png", file);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frame, e.getMessage(), 
								"Error while saving file", 
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
        file.add(saveMenuItem);
        file.add(exitMenuItem);
        
        JMenu view = new JMenu("View");
        
        JCheckBoxMenuItem verticesCheckBox = new JCheckBoxMenuItem("Vertices");
        verticesCheckBox.setState(true);
        showVertices = true;
        verticesCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showVertices = !showVertices;
				paintablePanel.repaint();
			}
		});
        
        JCheckBoxMenuItem edgesCheckBox = new JCheckBoxMenuItem("Edges");
        edgesCheckBox.setState(true);
        showEdges = true;
        edgesCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showEdges = !showEdges;
				paintablePanel.repaint();
			}
		});
        
        JCheckBoxMenuItem facesCheckBox = new JCheckBoxMenuItem("Faces");
        facesCheckBox.setState(true);
        showFaces = true;
        facesCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showFaces = !showFaces;
				paintablePanel.repaint();
			}
		});
        
        view.add(verticesCheckBox);
        view.add(edgesCheckBox);
        view.add(facesCheckBox);
        
        //Add menu to menu bar
        menuBar.add(file);
        menuBar.add(view);
        
        //Set the JFrame MenuBar to the menu bar created
        frame.setJMenuBar(menuBar);
        
        frame.setVisible(true);
		
	}

	public static boolean getShowVertices() {
		return showVertices;
	}

	public static boolean getShowEdges() {
		return showEdges;
	}

	public static boolean getShowFaces() {
		return showFaces;
	}
	
	

}
