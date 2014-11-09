package com.gmail.vitordeatorreao.screen;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * This class implements a JPanel that will create a 
 * <code>ScreenPainter</code> and paint its screen with the information 
 * of the loaded Scene.
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
public class PaintablePanel extends JPanel {

	private static final long serialVersionUID = 1025412576844168978L;
	
	private ScreenPainter screenPainter;
	
	/**
	 * Creates a new <code>PaintablePanel</code> instance.
	 * By default, the background is black.
	 */
	public PaintablePanel() {
		super();
		setBackground(Color.black);
		setBorder(new EmptyBorder(10, 10, 10, 10));
	}
	
	/**
	 * Reads the canvas from its <code>ScreenPainter</code> instance and 
	 * and paints it on the screen.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		screenPainter = new ScreenPainter(getWidth(), getHeight());
		
		screenPainter.paintScene();
		Color[][] canvas = screenPainter.getCanvas();
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				drawPixel(g, i, j, canvas[i][j]);
			}
		}
	}

	/**
	 * Paints a pixel on the screen.
	 * It is a project requirement that the code must only call this 
	 * function to paint the screen.
	 * @param g a <code>Graphics</code> instance
	 * @param x the x coordinate of the pixel
	 * @param y the y coordinate of the pixel
	 * @param c the <code>Color</code> for the pixel
	 */
	public void drawPixel(Graphics g, int x, int y, Color c) {
		g.setColor(c);
		g.fillRect(x, y, 1, 1);
	}

}
