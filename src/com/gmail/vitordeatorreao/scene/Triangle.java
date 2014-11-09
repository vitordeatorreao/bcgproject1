package com.gmail.vitordeatorreao.scene;

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
