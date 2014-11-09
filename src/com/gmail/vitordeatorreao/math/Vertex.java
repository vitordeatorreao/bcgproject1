package com.gmail.vitordeatorreao.math;

/**
 * This class implements 
 * <a href="http://en.wikipedia.org/wiki/Vertex_%28geometry%29">Vertex</a> 
 * functionality.
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
public class Vertex {
	
	private double[] coords;
	
	private String label;
	
	/**
	 * Constructor of class <code>Vertex</code>.<br />
	 * A <code>Vertex</code> object is defined by its coordinates.
	 * @param coords coordinates of the <code>Vertex</code>
	 */
	public Vertex(double[] coords) {
		this.coords = coords;
	}
	
	/**
	 * Constructor of class <code>Vertex</code>.<br />
	 * Allows the use of a label to this <code>Vertex</code> instance.<br />
	 * There is no enforcement for unique labels.<br />
	 * A <code>Vertex</code> object is defined by its coordinates.
	 * @param label the label given to this <code>Vertex</code> object
	 * @param coords coordinates of the <code>Vertex</code>
	 */
	public Vertex(String label, double[] coords) {
		this.coords = coords;
		this.label = label;
	}
	
	/**
	 * Sets the coordinate at a given dimension to a new value.
	 * @param index the dimension being changed
	 * @param coord the new value in that dimension
	 */
	protected void setCoord(int index, double coord) {
		this.coords[index] = coord;
	}
	
	/**
	 * Returns the coordinate in the given dimension.
	 * @param index the dimension
	 * @return the coordinates in the given dimension
	 */
	public double getCoord(int index) {
		return this.coords[index];
	}
	
	/**
	 * Returns the dimension of the <code>Vertex</code>.<br />
	 * You can also understand this as the length of the array associated with 
	 * this instance of <code>Vertex</code>.
	 * @return the total number of dimensions of this <code>Vertex</code>
	 */
	public int getDimension() {
		return this.coords.length;
	}
	
	/**
	 * Returns the label of this <code>Vertex</code>.
	 * @return	The label of this instance of <code>Vertex</code> 
	 * 			in case it was given one. 
	 * 			An empty <code>String</code> otherwise.
	 */
	public String getLabel() {
		return this.label == null ? "" : this.label;
	}
	
	/**
	 * Returns the <code>Vector</code> between another instance of 
	 * <code>Vertex</code> and this one.
	 * @param v another <code>Vertex</code> instance
	 * @return <code>Vector</code> between the two vertices.
	 */
	public Vector subtract(Vertex v) {
		if(this.getDimension() != v.getDimension()) {
			throw new IllegalArgumentException(
					"Vectors must be of same dimension"
			);
		}
		double[] ds = new double[this.getDimension()];
		for(int i = 0; i < v.getDimension(); i++) {
			ds[i] = this.getCoord(i) - v.getCoord(i);
		}
		return new Vector(ds);
	}
	
	/**
	 * Returns the Barycentric Coordinates of this <code>Vertex</code> 
	 * according to three other non-collinear vertices.
	 * @param vA a <code>Vertex</code>
	 * @param vB a <code>Vertex</code>
	 * @param vC a <code>Vertex</code>
	 * @return The Barycentric Coordinates
	 */
	public BarycentricCoordinate getBaricentricCoordinate(
			Vertex vA, Vertex vB, Vertex vC) {
		return new BarycentricCoordinate(this, vA, vB, vC);
	}
	
	/**
	 * Renders this <code>Vertex</code> in a <code>String</code>.
	 * @return	<code>String</code> containing all information 
	 * 			from this <code>Vertex</code>.
	 */
	@Override
	public String toString() {
		String result = "";
		result += "(";
		int i;
		for(i = 0; i < this.coords.length-1; i++) {
			result += (this.coords[i]+0.0)+", ";
		}
		result += (this.coords[i]+0.0)+")";
		return result;
		
		/* We are using +0.0 when turning doubles to strings because of
		 * the Java "Negative Zero"
		 */
	}
	
	/**
	 * Compares two vertices.
	 * @return	<code>true</code> if the vertices are equal. 
	 * 			<code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object arg0) {
		Vertex v = null;
		try {
			v = (Vertex) arg0;
		} catch (Exception e) {
			return false;
		}
		if(this.getDimension() != v.getDimension()) {
			return false;
		}
		for(int i = 0; i < this.getDimension(); i++) {
			if (this.getCoord(i) != v.getCoord(i)) {
				return false;
			}
		}
		return true;
	}

}
