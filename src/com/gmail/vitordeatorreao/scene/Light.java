package com.gmail.vitordeatorreao.scene;

import java.awt.Color;

import com.gmail.vitordeatorreao.math.Vector;
import com.gmail.vitordeatorreao.math.Vertex;

/**
 * This class implements a Light Element.
 * A light is composed of the following elements:
 * 
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
public class Light {
	
	/**
	 * Environment Light's Color
	 */
	private Color iAmb;
	
	/**
	 * Light's color
	 */
	private Color iL;
	
	/**
	 * Object's environmental reflection coefficient
	 */
	private double kA;
	
	/**
	 * Object's specular reflection coefficient
	 */
	private double kS;
	
	/**
	 * Featured specular size
	 * A.K.A. Eta
	 */
	private double n;
	
	/**
	 * Diffuse reflection coefficient
	 */
	private Vector kD;
	
	/**
	 * Object's surface diffuse color
	 */
	private Vector oD;
	
	/**
	 * Origin of Light
	 */
	private Vertex pL;
	
	public Light(Color iAmb, Color iLocal, double kA, 
			double kS, double n, Vector kD, Vector oD, Vertex pL) {
		this.iAmb	= iAmb;
		this.iL = iLocal;
		this.kA		= kA;
		this.kS		= kS;
		this.n		= n;
		this.kD		= kD;
		this.oD		= oD;
		this.pL		= pL;
	}

	public Color getiAmb() {
		return iAmb;
	}

	public Color getiLocal() {
		return iL;
	}

	public double getkA() {
		return kA;
	}

	public double getkS() {
		return kS;
	}

	public double getN() {
		return n;
	}

	public Vector getkD() {
		return kD;
	}

	public Vector getoD() {
		return oD;
	}

	public Vertex getpL() {
		return pL;
	}
	
	public String toString() {
		String s = "{\n";
		s += "Iamb = "+this.iAmb.toString()+"\n";
		s += "Il = "+this.iL.toString()+"\n";
		s += "Ka = "+this.kA+"\n";
		s += "Ks = "+this.kS+"\n";
		s += "N (or eta) = "+this.n+"\n";
		s += "Kd = "+this.kD.toString()+"\n";
		s += "Od = "+this.oD.toString()+"\n";
		s += "Pl = "+this.pL.toString()+"\n";
		s += "}\n";
		return s;
	}
}
