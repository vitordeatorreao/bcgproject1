package com.gmail.vitordeatorreao.screen;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmail.vitordeatorreao.math.Vector;
import com.gmail.vitordeatorreao.math.Vertex;
import com.gmail.vitordeatorreao.scene.Scene;
import com.gmail.vitordeatorreao.scene.SceneController;
import com.gmail.vitordeatorreao.scene.Triangle;

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
		
		Scene scene = SceneController.getInstance().getScene();
		
		for (Triangle t : scene.getTriangles()) {
			int[][] vertices = new int[2][3];
			// ^- the 3 vertices in screen coordinates
			
			for (int k = 0; k < t.getEdges().length; k++) {
				//Get each vertex screen coordinates
				
				//First, get the vertex
				Vertex v = t.getEdges()[k].get(0);
				
				//Second, change to view base
				Vector w = v.subtract(scene.getCamera().getFocus());
				Vector wInViewBase = scene
									 .getCamera()
									 .getToViewBase()
									 .mult(w);
				
				//Third, project in perspective
				double xs, ys;
				xs = scene.getCamera().getD() * 
						(wInViewBase.get(0)/wInViewBase.get(2));
				ys = scene.getCamera().getD() * 
						(wInViewBase.get(1)/wInViewBase.get(2));
				
				//Forth, normalize
				xs = xs/scene.getCamera().getHx();
				ys = ys/scene.getCamera().getHy();
				
				//Fifth, get screen coordinates
				int i,j;
				double aux;
				aux = ( ((xs+1)/2)*getWidth() ) + 0.5;
				i = ((Double) Math.floor(aux)).intValue();
				aux = (getHeight() - (((ys+1)/2) * getHeight()) + 0.5);
				j = ((Double) Math.floor(aux)).intValue();
				
				//Now, fill the vertices array
				vertices[0][k] = i;
				vertices[1][k] = j;
			}
			
			//Now we have for 3 vertices in its screen coordinates
			for (int i = 0; i < 3; i++) {
				drawPixel(g, vertices[0][i], vertices[1][i], Color.white);
				
			}
			
			//Now draw edges
			for (int i = 0; i < 3; i++) {
				drawLine(g, 
						vertices[0][i], vertices[1][i], 
						vertices[0][(i+1)%3], vertices[1][(i+1)%3], 
						Color.white);
			}
		}
		
	}

	/**
	 * Paints a pixel on the screen.
	 * It is a project requirement that the code must only call this 
	 * function to paint the screen.
	 * @param g a <code>Graphics</code> instance
	 * @param x The x coordinate of the pixel
	 * @param y The y coordinate of the pixel
	 * @param c The <code>Color</code> for the pixel
	 */
	public void drawPixel(Graphics g, int x, int y, Color c) {
		g.setColor(c);
		g.fillRect(x, y, 1, 1);
	}
	
	/**
	 * Paints a straight line between two screen coordinates
	 * @param g a <code>Graphics</code> instance
	 * @param x1 The x coordinate of the first pixel 
	 * @param y1 The y coordinate of the first pixel
	 * @param x2 The x coordinate of the second pixel
	 * @param y2 The y coordinate of the second pixel
	 * @param c The <code>Color</code> for the line
	 */
	public void drawLine(Graphics g, int x1, int y1, int x2, int y2, Color c) {
		//Rasterize a Line using BresenhamAlgorithm for Integers
		boolean changed = false;
		int x = x1;
		int y = y1;
		
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		
		int signx = signum(x2 - x1);
		int signy = signum(y2 - y1);
		
		if (dy > dx) {
			//Swap dx and dy
			int temp = dx;
			dx = dy;
			dy = temp;
			changed = true;
		}
		double e = (2 * dy) - dx;
		for (int i = 1; i <= dx; i++) {
			drawPixel(g, x, y, c);
			while(e >= 0) {
				if (changed) {
					x = x + signx;
				} else {
					y = y + signy;
				}
				e = e - (2 * dx);
			}
			if (changed) {
				y += signy;
			} else {
				x += signx;
			}
			e = e + (2 * dy);
		}
	}

	private int signum(int i) {
		if (i < 0) {
			return -1;
		} else if (i > 0) {
			return 1;
		} else {
			return 0;
		}
	}

}
