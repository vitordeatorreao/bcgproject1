package com.gmail.vitordeatorreao.screen;

import java.awt.Color;

/**
 * <code>ZBuffer</code> implements the Z-Buffer algorithm.
 * See more <a href="http://en.wikipedia.org/wiki/Z-buffering">here</a>.
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
public class ZBuffer {
	
	private Cell[][] buffer;
	private int width;
	private int height;
	
	/**
	 * Constructor. One need to specify the width and height of the ZBuffer
	 * in order to instantiate this class.
	 * @param width Typically, the width of the screen.
	 * @param height Typically, the height of the screen.
	 */
	public ZBuffer(int width, int height) {
		this.buffer = new Cell[width][height];
		this.width = width;
		this.height = height;
		
		//So setColor and setDeepness wont throw NullPointerException
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.buffer[i][j] = new Cell(Color.black, Double.MAX_VALUE);
			}
		}
	}
	
	/**
	 * Sets the Color and Deepness of a Cell inside the ZBuffer.
	 * @param x Width of the point
	 * @param y Height of the point
	 * @param color The color of the point
	 * @param deepness The deepness of the point
	 */
	public void set(int x, int y, Color color, double deepness) {
		if (x >= width || y >= height || 
			x < 0 || y < 0) {
			return;
		}
		double curDeep = this.buffer[x][y].getDeepness();
		if (deepness < curDeep) {
			this.buffer[x][y].setColor(color);
			this.buffer[x][y].setDeepness(deepness);
		}
	}
	
	/**
	 * Returns the Deepness associated with this point in the ZBuffer
	 * @param x Width of the point
	 * @param y Height of the point
	 * @return The Deepness (or distance to the <code>Camera</code>'s focus
	 */
	public double getDeepness(int x, int y) {
		if (x >= width || y >= height || 
			x < 0 || y < 0) {
			return Double.MIN_VALUE;
		}
		return this.buffer[x][y].getDeepness();
	}
	
	/**
	 * Returns the <code>Color</code> associated with this point in the ZBuffer
	 * @param x Width of the point
	 * @param y Height of the point
	 * @return The color of the point
	 */
	public Color getColor(int x, int y) {
		return this.buffer[x][y].getColor();
	}
	
	/**
	 * Returns the width of the ZBuffer
	 * @return the width of the ZBuffer
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of the ZBuffer
	 * @return The height of the ZBuffer
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Class that implements a cell inside the <code>ZBuffer</code>.
	 */
	private class Cell {
		
		private Color color;
		private double deepness;
		
		/**
		 * Constructor. A <code>Cell</code> is defined by a color and 
		 * a distance to the <code>Camera</code> focus.
		 * @param color
		 * @param deepness
		 */
		public Cell(Color color, double deepness) {
			this.color = color;
			this.deepness = deepness;
		}
		
		/**
		 * Returns the color associated with this <code>Cell</code>.
		 * @return The color
		 */
		public Color getColor() {
			return this.color;
		}
		
		/**
		 * Returns the distance from the <code>Vertex</code>, which is depicted
		 * at this <code>Cell</code> to the <code>Camera</code>'s focus.
		 * @return
		 */
		public double getDeepness() {
			return this.deepness;
		}
		
		/**
		 * Sets the new color for this <code>Cell</code>.
		 * @param newColor The new Color
		 */
		public void setColor(Color newColor) {
			this.color = newColor;
		}
		
		/**
		 * Sets the deepness associated with this <code>Cell</code>
		 * @param newDeepness The deepness
		 */
		public void setDeepness(double newDeepness) {
			this.deepness = newDeepness;
		}
	}

}
