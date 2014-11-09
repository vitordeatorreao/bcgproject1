package com.gmail.vitordeatorreao.scene;

import com.gmail.vitordeatorreao.math.Matrix;
import com.gmail.vitordeatorreao.math.Vector;
import com.gmail.vitordeatorreao.math.Vertex;

/**
 * This class implements a Camera.<br>
 * A Camera will define the viewpoint of the scene.
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
public class Camera {
	
	private Vertex focus;
	private Vector U, V, N;
	private double d, hx, hy;
	
	private Matrix toViewBase;
	private Matrix toCaninocalBase;
	
	/**
	 * Constructor of the <code>Camera</code> class.
	 * @param C		A <code>Vertex</code> representing the
	 * 				focus of the <code>Camera</code> 
	 * @param N		The N <code>Vector</code> of the <code>Camera</code>
	 * @param V		The V <code>Vector</code> of the <code>Camera</code>
	 * @param d		The distance between the focus and the view plane	
	 * @param hx	Half of the view plane's width
	 * @param hy	Half of the view plane's height
	 */
	public Camera(Vertex C, Vector N, Vector V, 
			double d, double hx, double hy) {
		
		if (V.getDimension() != N.getDimension()) {
			throw new IllegalArgumentException("U, V and N vectors "
					+ "must be of same dimension");
		}
		
		this.d	=	d;
		this.hx	=	hx;
		this.hy	=	hy;
		
		this.focus = C;
		
		//Check if V is orthogonalized with N
		if (V.scalarMult(N) != 0) {
			//It is not
			double k = V.scalarMult(N)/N.scalarMult(N);
			V = V.sub(N.mult(k));
		}
		
		//Normalize both vectors
		if (V.getNorm() != 1.0) {
			V = V.normalize();
		}
		
		if (N.getNorm() != 1.0) {
			N = N.normalize();
		}
		
		//Calculate U
		// U = N x V
		this.U = N.vectorProduct(V);
		this.V = V;
		this.N = N;
		
		//Calculate change base matrices
		double[] ds = new double[this.V.getDimension()*3];
		Vector[] vs = {U, V, N};
		int i = 0;
		for (Vector v : vs) {
			for (int j = 0; j < v.getDimension(); j++) {
				ds[i] = v.get(j);
				i++;
			}
		}
		this.toViewBase = new Matrix(ds, vs.length, this.V.getDimension());
		this.toCaninocalBase = this.toViewBase.transpose();
		
	}

	/**
	 * Returns the point of focus of the <code>Camera</code>
	 * @return point of focus of the <code>Camera</code>
	 */
	public Vertex getFocus() {
		return focus;
	}

	/**
	 * Returns the U <code>Vector</code> of the <code>Camera</code>
	 * @return the U <code>Vector</code> of the <code>Camera</code>
	 */
	public Vector getU() {
		return U;
	}

	/**
	 * Returns the V <code>Vector</code> of the <code>Camera</code>
	 * @return the V <code>Vector</code> of the <code>Camera</code>
	 */
	public Vector getV() {
		return V;
	}

	/**
	 * Returns the N <code>Vector</code> of the <code>Camera</code>
	 * @return the N <code>Vector</code> of the <code>Camera</code>
	 */
	public Vector getN() {
		return N;
	}

	/**
	 * Returns the D <code>Vector</code>, which is the distance between 
	 * the point of focus and the view plane, of the <code>Camera</code>
	 * @return the distance between the view plane and the camera focus
	 */
	public double getD() {
		return d;
	}

	/**
	 * Returns the Hx of the <code>Camera</code>
	 * @return half of the view plane's width
	 */
	public double getHx() {
		return hx;
	}
	
	/**
	 * Returns the Hy of the <code>Camera</code>
	 * @return half of the view plane's height
	 */
	public double getHy() {
		return hy;
	}

	/**
	 * Returns the <code>Matrix</code> to change base from canonical to 
	 * view based on this <code>Camera</code>.
	 * @return the change base <code>Matrix</code>
	 */
	public Matrix getToViewBase() {
		return toViewBase;
	}
	
	/**
	 * Returns the <code>Matrix</code> to change base from view, 
	 * based on this <code>Camera</code>, to canonical.
	 * @return the change base <code>Matrix</code>
	 */
	public Matrix getToCaninocalBase() {
		return toCaninocalBase;
	}
	
	@Override
	public String toString() {
		String s = "{\n";
		s += "C = "+this.focus.toString()+"\n";
		s += "d = "+this.d+"\n";
		s += "hx = "+this.hx+"\n";
		s += "hy = "+this.hy+"\n";
		s += "U = "+this.U.toString()+"\n";
		s += "V = "+this.V.toString()+"\n";
		s += "N = "+this.N.toString()+"\n";
		s += "}\n";
		return s;
	}
	
	/**
	 * This function is only used for testing
	 * @param args
	 */
	public static void main(String[] args) {
		Vector V, N;
		double d, hx, hy;
		Vertex C = new Vertex(new double[]{1.5, 2.5, 3.5});
		V = new Vector(new double[]{0, 0, 1});
		N = new Vector(new double[]{-Math.sqrt(2)/2, Math.sqrt(2)/2, 0});
		
		d = 5.5;
		hx = 320;
		hy = 240;
		
		Camera c = new Camera(C, N, V, d, hx, hy);
		
		System.out.println("Camera = "+c);
		
		System.out.println("Matrix from canonical to View: "+c.getToViewBase());
		
		System.out.println("Matrix from view to canonical: "+c.getToCaninocalBase());
		
	}

}
