package com.gmail.vitordeatorreao.math;

/**
 * This class represents a Vertex in 
 * <a href="http://en.wikipedia.org/wiki/Barycentric_coordinate_system">
 * Barycentric Coordinates</a>.
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
public class BarycentricCoordinate extends Vertex {
	
	private Vertex[] barycentricBase;
	
	/**
	 * Use this constructor if the Vertex is already in barycentric coordinates.
	 * @param ds an array containing the 3 coordinates 
	 * @param vA a <code>Vertex</code> from the triangle
	 * @param vB a <code>Vertex</code> from the triangle
	 * @param vC a <code>Vertex</code> from the triangle
	 */
	public BarycentricCoordinate(double[] ds, Vertex vA, 
			Vertex vB, Vertex vC) {
		super(ds);
		
		if(!this.checkDimensions(vA, vB, vC)) {
			throw new IllegalArgumentException(
					"For calculating baricentric coordinates,"
					+ "all vertices involved must be of dimension 2");
		}
		if(ds.length != 3) {
			throw new IllegalArgumentException(
					"The double array must be of dimension 3");
		}
		
		this.barycentricBase = new Vertex[3];
		this.barycentricBase[0] = vA;
		this.barycentricBase[1] = vB;
		this.barycentricBase[2] = vC;
	}

	/**
	 * Use this Constructor if calculations are necessary to calculate a 
	 * <code>Vertex</code> according to a triangle.
	 * @param vP	the <code>Vertex that will be converted 
	 * 				to barycentric coordinates
	 * @param vA	a <code>Vertex</code> from the triangle
	 * @param vB	a <code>Vertex</code> from the triangle
	 * @param vC	a <code>Vertex</code> from the triangle
	 */
	public BarycentricCoordinate(Vertex vP, Vertex vA, Vertex vB, Vertex vC ) {
		super(new double[3]);		
		
		//Check if they are all in 2D
		if(!this.checkDimensions(vA, vB, vC)) {
			throw new IllegalArgumentException(
					"For calculating baricentric coordinates,"
					+ "all vertices involved must be of dimension 2");
		}
		
		//Check if vA, vB and vC are collinear
		double side1 =	(vB.getCoord(1) - vA.getCoord(1)) *
						(vC.getCoord(0) - vB.getCoord(0));
		//(y2 - y1) * (x3 - x2) 
		double side2 =	(vC.getCoord(1) - vB.getCoord(1)) *
						(vB.getCoord(0) - vA.getCoord(0));
		//(y3 - y2) * (x2 - x1)
		if(side1 == side2) {
			//Necessarily collinear!
			throw new IllegalArgumentException(
					"The 3 points are collinear."
					+ " Cannot calculate baricentric coordinates");
		}
		
		this.barycentricBase = new Vertex[3];
		this.barycentricBase[0] = vA;
		this.barycentricBase[1] = vB;
		this.barycentricBase[2] = vC;
		
		//Calculate T^(-1)
		double[] t = new double[4];
		t[0] = vA.getCoord(0) - vC.getCoord(0);
		//x1 - x3
		t[1] = vB.getCoord(0) - vC.getCoord(0);
		//x2 - x3
		t[2] = vA.getCoord(1) - vC.getCoord(1);
		//y1 - y3
		t[3] = vB.getCoord(1) - vC.getCoord(1);
		//y2 - y3
		/* t = [
		 * 	(x1 - x3)		(x2 - x3)
		 * 	(y1 - y3)		(y2 - y3)
		 * ] 
		 */
		Matrix mTinv = (new Matrix(t, 2, 2)).inverse();
		
		//Calculate [x0 - x3; y0 - y3]
		double[] ma = new double[2];
		ma[0] = vP.getCoord(0) - vC.getCoord(0);
		ma[1] = vP.getCoord(1) - vC.getCoord(1);
		Matrix m = new Matrix(ma, 2, 1);
				
		/* m = [
		 * 	(x0 - x3)
		 *  (y0 - y3)
		 * ]
		 */
		Matrix alphaBeta = mTinv.mult(m);
		double alpha = alphaBeta.get(0, 0);
		double beta = alphaBeta.get(1, 0);
		double gama = 1 - alpha - beta;
		
		this.setCoord(0, alpha);
		this.setCoord(1, beta);
		this.setCoord(2, gama);		
		
	}
	
	/**
	 * Returns whether all vertices are of dimension 2
	 * @param vA a <code>Vertex</a> from the triangle
	 * @param vB a <code>Vertex</a> from the triangle
	 * @param vC a <code>Vertex</a> from the triangle
	 * @return	<code>true</code> if all three vertices are of dimension 2,
	 * 			<code>false</code> otherwise.
	 */
	private boolean checkDimensions(Vertex vA, Vertex vB, Vertex vC) {
		
		if(vA.getDimension() == vB.getDimension() &&
				vB.getDimension() == vC.getDimension() &&
				vA.getDimension() == 2) {
			return true;
		}
		return false;		
	}
	
	/**
	 * Returns the Vertex to its canonical coordinates.
	 * @return the <code>Vertex</code> in canonical coordinates.
	 */
	public Vertex backToCanonical() {
		/* count == true means the new vertex will be
		 * added to the list of all vertices
		 */
		double[] coords = new double[2];
		coords[0] = this.getCoord(0)*this.barycentricBase[0].getCoord(0) + 
				this.getCoord(1)*this.barycentricBase[1].getCoord(0) +
				this.getCoord(2)*this.barycentricBase[2].getCoord(0);
		coords[1] = this.getCoord(0)*this.barycentricBase[0].getCoord(1) +
				this.getCoord(1)*this.barycentricBase[1].getCoord(1) +
				this.getCoord(2)*this.barycentricBase[2].getCoord(1);
		Vertex v = new Vertex(coords);
		return v;
	}

	/**
	 * This function is only used for testing
	 * @param args
	 */
	public static void main(String[] args) {
		
		double[] a = {4.0, 6.0};
		double[] b = {2.0, 1.0};
		double[] c = {6.0, 3.0};
//		double[] a = {1.0, 1.0};
//		double[] b = {2.0, 2.0};
//		double[] c = {3.0, 3.0};
		double[] p = {4.0, 4.0};
		Vertex vA = new Vertex(a);
		Vertex vB = new Vertex(b);
		Vertex vC = new Vertex(c);
		Vertex vP = new Vertex(p);
		
		BarycentricCoordinate bc =
				new BarycentricCoordinate(vP, vA, vB, vC);
		
		System.out.println("Points: "+vA+"; "+vB+"; "+vC);
		
		System.out.println("The Barycentric coordinate of "+vP+
				" in relation to those points is: "+bc);
		//Should be (.5, .25, .25)
		
		System.out.println("Now back to the canonical base of R^2: "+ 
							bc.backToCanonical());

	}

}
