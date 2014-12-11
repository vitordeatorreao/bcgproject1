package com.gmail.vitordeatorreao.scene;

import java.util.ArrayList;
import com.gmail.vitordeatorreao.utils.QuickSortTriangles;

/**
 * This class implements a Scene.
 * A Scene contains the triangle faces of visual objects and a camera.
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
public class Scene {
	
	private Camera camera;
	private ArrayList<Triangle> triangles;
	
	/**
	 * Instantiates an empty <code>Scene</code>. 
	 * No <code>Triangles</code>, no <code>Camera</code>. 
	 * They can be added later.
	 */
	public Scene() {
		this.camera = null;
		this.triangles = new ArrayList<Triangle>();
	}
	
	/**
	 * Instantiates a <code>Scene</code> with a <code>Camera</code>. 
	 * <code>Triangles</code> can be added later. The Camera can be changed.
	 * @param c <code>Camera</code> to view the objects in the scene
	 */
	public Scene(Camera c) {
		this.camera = c;
		this.triangles = new ArrayList<Triangle>();
	}
	
	/**
	 * Instantiates a <code>Scene</code> with a <code>Camera</code> 
	 * and a list of <code>Triangles</code>.
	 * The Camera can be changed.
	 * @param c <code>Camera</code> to view the objects in the scene
	 * @param triangles An <code>ArrayList</code> of <code>Triangles</code>
	 */
	public Scene(Camera c, ArrayList<Triangle> triangles) {
		this.camera = c;
		this.triangles = triangles;
	}
	
	/**
	 * Sets the <code>Camera</code> in the <code>Scene</code>.
	 * @param c The new <code>Camera</code>
	 */
	public void setCamera(Camera c) {
		this.camera = c;
	}
	
	/**
	 * Adds a <code>Triangle</code> to the <code>Scene</code>.
	 * @param triangle A <code>Triangle</code> object
	 */
	public void addTriangle(Triangle triangle) {
		this.triangles.add(triangle);
	}

	/**
	 * Returns this <code>Scene</code>'s <code>Camera</code>
	 * @return the <code>Camera</code> on this scene
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Returns a list of <code>Triangle</code>s that are part of this scene.
	 * @return an <code>ArrayList</code> of <code>Triangle</code>s.
	 */
	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}
	
	/**
	 * Erases the list of Triangles.
	 */
	public void cleanTriangles() {
		this.triangles = new ArrayList<Triangle>();
	}
	
	/**
	 * Sorts the list of triangles by their centroids.
	 */
	public void sortTriangles() {
		QuickSortTriangles qst = new QuickSortTriangles();
		qst.sort(triangles);
	}
	
	@Override
	public String toString() {
		String s = "Scene = {";
		if (this.camera != null) {
			s += this.camera.toString() + ",";
		}
		for (Triangle t : this.triangles) {
			s += "\n\n"+t.toString();
		}
		s += "}\n";
		return s;
	}

}
