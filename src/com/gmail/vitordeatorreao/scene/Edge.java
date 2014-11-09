package com.gmail.vitordeatorreao.scene;

import com.gmail.vitordeatorreao.math.Vertex;

/**
 * This class implements 
 * <a href="http://en.wikipedia.org/wiki/Edge_%28geometry%29">Edge</a> 
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
public class Edge {
	
	private Vertex[] vertices;
	private String label;
	
	/**
	 * Constructor of class <code>Edge</code>.<br />
	 * A <code>Edge</code> object is defined by 2 vertices 
	 * at each end of the <code>Edge</code>.
	 * @param v1 a <code>Vertex</code> at one end of the <code>Edge</code>
	 * @param v2 a <code>Vertex</code> at one end of the <code>Edge</code>
	 */
	public Edge(Vertex v1, Vertex v2) {
		this.vertices = new Vertex[2];
		this.vertices[0] = v1;
		this.vertices[1] = v2;
	}
	
	/**
	 * Constructor of class <code>Edge</code>.<br />
	 * Allows the use of a label to this <code>Vertex</code> instance.<br />
	 * There is no enforcement for unique labels.<br />
	 * An <code>Edge</code> object is defined by 2 vertices 
	 * at each end of the <code>Edge</code>.
	 * @param label the label given to this <code>Vertex</code> object
	 * @param v1	a <code>Vertex</code> at one end of the
	 * 				<code>Edge</code>
	 * @param v2	a <code>Vertex</code> at the other end of
	 * 				the <code>Edge</code>
	 */
	public Edge(String label, Vertex v1, Vertex v2) {
		this.vertices = new Vertex[2];
		this.vertices[0] = v1;
		this.vertices[1] = v2;
		this.label = label;
	}
	
	/**
	 * Returns the specified <code>Vertex</code> of this <code>Edge</code>.
	 * @param index either 0 or 1
	 * @return The specified <code>Vertex</code>
	 */
	public Vertex get(int index) {
		if (index > 1) {
			return null;
		}
		return this.vertices[index];
	}

	/**
	 * Returns the label given to this <code>Edge</code> instance.
	 * @return	The given label if there is one, 
	 * 			an empty <code>String</code> otherwise.
	 */
	public String getLabel() {
		return this.label == null ? "" : this.label;
	}
	
	/**
	 * Renders this <code>Edge</code> in a <code>String</code>.
	 * @return	<code>String</code> containing all information 
	 * 			from this <code>Edge</code>.
	 */
	public String toString() {
		String result = "[ " + this.vertices[0].toString();
		result += "=>" + this.vertices[1].toString() + " ]";
		return result;
	}

}
