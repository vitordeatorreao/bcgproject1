package com.gmail.vitordeatorreao.screen;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
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

	/**
	 * The main loop of the program.
	 * Reads user input and displays all loaded objects on the screen.
	 * @param args
	 */
	public static void main(String[] args) {
		
		File file = new File("vaso.byu");
		
		if(args.length > 0) {
			file = new File(args[0]);
		}
		
		try {
			SceneController.getInstance().loadScene(file);
		} catch (IOException | NonConformantSceneFile e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		JFrame f = new JFrame("Vitor Torreao CG Project");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(WIDTH, HEIGHT);
        f.add(new PaintablePanel(), BorderLayout.CENTER);
        f.setVisible(true);
		
	}

}
