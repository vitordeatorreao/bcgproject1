package com.gmail.vitordeatorreao.screen;

import java.awt.Color;

import com.gmail.vitordeatorreao.math.Matrix;
import com.gmail.vitordeatorreao.math.Vector;
import com.gmail.vitordeatorreao.math.Vertex;
import com.gmail.vitordeatorreao.scene.Scene;
import com.gmail.vitordeatorreao.scene.SceneController;
import com.gmail.vitordeatorreao.scene.Triangle;

/**
 * This class implements functionality to paint a canvas.
 * A canvas is a two dimensional array where colors are stored.
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
public class ScreenPainter {
	
	private Color[][] canvas;
	private int width;
	private int height;
	
	/**
	 * Creates a new <code>ScreenPainter</code> instance with a canvas 
	 * of the specified width and height.
	 * @param width the width of the canvas
	 * @param height the height of the canvas
	 */
	public ScreenPainter(int width, int height) {
		this.canvas = new Color[width][height];
		this.width = width;
		this.height = height;
		clean();
	}
	
	/**
	 * Cleans this <code>ScreenPainter</code>'s canvas.
	 */
	public void clean() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.canvas[i][j] = Color.black;
			}
		}
	}
	

	/**
	 * Returns this <code>ScreenPainter</code>'s canvas. 
	 * This can be used by UI libraries to paint the canvas on the screen. 
	 * @return Painted canvas
	 */
	public Color[][] getCanvas() {
		return this.canvas;
	}
	
	/**
	 * Paints the canvas. It uses the <code>SceneController</code> 
	 * array of triangles, and the camera parameters for a perspective 
	 * projection. Triangles are rasterized.
	 */
	public void paintScene() {
		clean();
		Scene scene = SceneController.getInstance().getScene();
		for (Triangle t : scene.getTriangles()) {
			//get its vertices
			Vertex[] vs = new Vertex[3];
			for (int i = 0; i < 3; i++) {
				vs[i] = t.getEdges()[i].get(0);
			}
			//change to view base
			for (int i = 0; i < 3; i++) {
				Vector v = vs[i].subtract(scene.getCamera().getFocus());
				Matrix m = scene.getCamera()
						.getToViewBase()
						.mult(v.toMatrix());
				double[] ds = new double[m.getNumRows()];
				for (int j = 0; j < m.getNumRows(); j++) {
					ds[j] = m.get(j, 0);
				}
				vs[i] = new Vertex(ds);
			}
			//Now project them in perspective
			for (int i = 0; i < 3; i++) {
				double[] ds = new double[2];
				ds[0] = scene.getCamera().getD() * 
						(vs[i].getCoord(0)/vs[i].getCoord(2));
				ds[1] = scene.getCamera().getD() * 
						(vs[i].getCoord(1)/vs[i].getCoord(2));
				vs[i] = new Vertex(ds);
			}
			//Now normalize the coordinates
			for (int i = 0; i < 3; i++) {
				double[] ds = new double[2];
				ds[0] = vs[i].getCoord(0) / scene.getCamera().getHx();
				ds[1] = vs[i].getCoord(1) / scene.getCamera().getHy();
				vs[i] = new Vertex(ds);
			}
			//Now paint them
			for (Vertex v : vs) {
				int i, j;
				Double aux = Math.floor(
					( (v.getCoord(0)+1)/2 )*this.width + 0.5
				);
				i = aux.intValue();
				aux = Math.floor(
					height - 
					( ((v.getCoord(1)+1)/2) * height )
					+ 0.5
				);
				j = aux.intValue();
				try {
					this.canvas[i][j] = Color.white;
				} catch (ArrayIndexOutOfBoundsException aiobe) {
					
				}
			}
			
		}
	}
}
