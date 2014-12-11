package com.gmail.vitordeatorreao.scene;

import com.gmail.vitordeatorreao.math.Vector;
import com.gmail.vitordeatorreao.math.Vertex;

/**
 * This class implements a
 * <a href="http://en.wikipedia.org/wiki/Triangle">Triangle</a>.
 * Every visual object in this application is going to be modeled using
 * triangle faces.
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
public class Triangle {
	
	private Edge[] edges;
	private String label;
	private Vector normal;
	private Vertex[] vertices;
	private Vertex centroid;
	
	/**
	 * Constructor of <code>Triangle</code> class. A Triangle object is defined
	 * by its three <code>Edge</code>s.
	 * @param e1 an <code>Edge</code> of the <code>Triangle</code>
	 * @param e2 an <code>Edge</code> of the <code>Triangle</code>
	 * @param e3 an <code>Edge</code> of the <code>Triangle</code>
	 */
	public Triangle(Edge e1, Edge e2, Edge e3) {
		this.edges = new Edge[3];
		this.edges[0] = e1;
		this.edges[1] = e2;
		this.edges[2] = e3;
		this.setVertices();
		this.calculateNormal();
		this.calculateCentroid();
	}
	
	/**
	 * Constructor of <code>Triangle</code> class.<br />
	 * Allows the use of a label to this <code>Vertex</code> instance.<br />
	 * There is no enforcement for unique labels.<br />
	 * A Triangle object is defined by its three <code>Edge</code>s.
	 * @param e1 an <code>Edge</code> of the <code>Triangle</code>
	 * @param e2 an <code>Edge</code> of the <code>Triangle</code>
	 * @param e3 an <code>Edge</code> of the <code>Triangle</code>
	 */
	public Triangle(String label, Edge e1, Edge e2, Edge e3) {
		this.edges = new Edge[3];
		this.edges[0] = e1;
		this.edges[1] = e2;
		this.edges[2] = e3;
		this.label = label;
		this.setVertices();
		this.calculateNormal();
		this.calculateCentroid();
	}
	
	/**
	 * Calculates this <code>Triangle</code>'s centriod.
	 */
	private void calculateCentroid() {
		double[] coords = new double[3];
		coords[0] =	(this.vertices[0].getCoord(0)+
					 this.vertices[1].getCoord(0)+
					 this.vertices[2].getCoord(0))/3.0;
		coords[1] = (this.vertices[0].getCoord(1)+
				 	 this.vertices[1].getCoord(1)+
				 	 this.vertices[2].getCoord(1))/3.0;
		coords[2] = (this.vertices[0].getCoord(2)+
			 	 	 this.vertices[1].getCoord(2)+
			 	 	 this.vertices[2].getCoord(2))/3.0;
		this.centroid = new Vertex(coords);
	}
	
	/**
	 * Sets the vertices array attribute to the list of vertices of this 
	 * <code>Triangle</code>
	 */
	private void setVertices() {
		this.vertices = new Vertex[3];
		this.vertices[0] = this.edges[0].get(0);
		this.vertices[1] = this.edges[1].get(0);
		this.vertices[2] = this.edges[2].get(0);
	}
	
	/**
	 * Calculates the normal of this <code>Triangle</code>.
	 */
	private void calculateNormal() {
		Vector v1 = this.vertices[1].subtract(this.vertices[0]);
		Vector v2 = this.vertices[2].subtract(this.vertices[0]);
		this.normal = v1.vectorProduct(v2).normalize();
	}
	
	/**
	 * Returns the edges of the <code>Triangle</code>
	 * @return the edges of the <code>Triangle</code>
	 */
	public Edge[] getEdges() {
		return edges;
	}

	/**
	 * Returns the label given to this <code>Triangle</code> instance.
	 * @return	The given label if there is one, 
	 * 			an empty <code>String</code> otherwise.
	 */
	public String getLabel() {
		return label == null ? "" : label;
	}

	/**
	 * Returns the normal of this <code>Triangle</code> instance.
	 * @return The normal of this <code>Triangle</code>
	 */
	public Vector getNormal() {
		return normal;
	}

	/**
	 * Returns the list of vertices of this <code>Triangle</code>.
	 * @return The three vertices of this <code>Triangle</code>
	 */
	public Vertex[] getVertices() {
		return vertices;
	}
	
	/**
	 * Returns the <code>Vertex</code> at the specified index.<br />
	 * There is no guaranteed order between the vertices.
	 * @param index The index of the <code>Vertex</code> to be returned
	 * @return Vertex The <code>Vertex</code> at the specified index
	 */
	public Vertex getVertex(int index) {
		return this.vertices[index];
	}
	
	/**
	 * Returns this <code>Triangle</code>'s centroid.
	 * @return The centroid
	 */
	public Vertex getCentroid() {
		return centroid;
	}

	/**
	 * Renders this <code>Triangle</code> in a <code>String</code>.
	 * @return	<code>String</code> containing all information 
	 * 			from this <code>Triangle</code>.
	 */
	public String toString() {
		String result = "";
		for(Edge e : this.edges) {
			result += e.toString() + "\t";
		}
		return result;
	}

}
