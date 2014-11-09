package com.gmail.vitordeatorreao.math;

/**
 * This class implements 
 * <a href="http://en.wikipedia.org/wiki/Vector_%28mathematics_and_physics%29">
 * Vector</a> functionality.
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
public class Vector {
	
	private double[] array;
	
	/**
	 * <code>Vector</code> objects must be defined by a one dimensional array.
	 * @param	ds	one dimensional array on which the 
	 * 				<code>Vector</code> will be based.
	 */
	public Vector(double[] ds) {
		this.array = ds;
	}
	
	/**
	 * Returns the value of the <code>Vector</code> at the dimension specified.
	 * @param index the dimension
	 * @return the value at the specified dimension.
	 */
	public double get(int index) {
		return this.array[index];
	}
	
	/**
	 * Returns the dimension of the <code>Vector</code>.
	 * @return the number of dimensions in this <code>Vector</code>.
	 */
	public int getDimension() {
		return this.array.length;
	}
	
	/**
	 * Returns the new <code>Vector</code> with the 
	 * scalar summed in all dimensions.
	 * @param d the scalar value
	 * @return the resulting <code>Vector</code> 
	 */
	public Vector add(double d) {
		double[] ds = new double[this.getDimension()];
		for(int i = 0; i < this.getDimension(); i++) {
			ds[i] = this.get(i) + d;
		}
		return new Vector(ds);
	}
	
	/**
	 * Returns the new <code>Vector</code> after summing this 
	 * <code>Vector</code> with another.
	 * @param v the <code>Vector</code> to be added to this one
	 * @return the resulting <code>Vector</code>
	 */
	public Vector add(Vector v) {
		if (v.getDimension() != this.getDimension()) {
			throw new IllegalArgumentException(
					"Vectors must be of same dimension for add operation"
			);			
		}
		double[] ds = new double[v.getDimension()];
		for(int i = 0; i < v.getDimension(); i++) {
			ds[i] = v.get(i) + this.get(i);
		}
		return new Vector(ds);
	}
	
	/**
	 * Returns a scalar resulting from the inner product of 
	 * this <code>Vector</code> with another.
	 * @param v the <code>Vector</code> to be part of the inner product
	 * @return the inner product of the two <code>Vector</code>s
	 */
	public double scalarMult(Vector v) {
		if(v.getDimension() != this.getDimension()) {
			throw new IllegalArgumentException(
					"Vectors must be of same dimension for scalarMult operation"
			);
		}
		double d = 0.0;
		for (int i = 0; i < this.getDimension(); i++) {
			d += v.get(i)*this.get(i);
		}
		return d;
	}
	
	/**
	 * Returns a <code>Vector</code> resulting from the 
	 * product of this <code>Vector</code> with a scalar.
	 * @param d the scalar with which the <code>Vector</code> will be multiplied
	 * @return the resulting <code>Vector</code>
	 */
	public Vector mult(double d) {
		double[] ds = new double[this.getDimension()];
		for (int i = 0; i < this.getDimension(); i++) {
			ds[i] = d*this.get(i);
		}
		return new Vector(ds);
	}
	
	/**
	 * Return a <code>Vector</code> resulting from the 
	 * subtraction of this <code>Vector</code> with another.
	 * @param v the <code>Vector</code> from which this 
	 * 			<code>Vector</code> will be subtracted.
	 * @return The resulting <code>Vector</code>
	 */
	public Vector sub(Vector v) {
		if (this.getDimension() != v.getDimension()) {
			throw new IllegalArgumentException(
					"You can only subtract vectors with same dimension");
		}
		double[] ds = new double[v.getDimension()];
		for (int i = 0; i < v.getDimension(); i++) {
			ds[i] = this.get(i) - v.get(i);
		}
		return new Vector(ds);
	}

	/**
	 * Returns a <code>Vector<code> resulting from the vector 
	 * product of this vector with another.
	 * @param v	the <code>Vector</code> to be used with this 
	 * 			for a vector product
	 * @return the resulting <code>Vector</code>
	 */
	public Vector vectorProduct(Vector v) {
		if( (v.getDimension() != this.getDimension()) &&
				(v.getDimension() != 3) ) {
			throw new IllegalArgumentException(
					"Vectors must be both of dimension 3"
			);
		}
		double[] ds = new double[3];
		ds[0] = (this.get(1)*v.get(2)) - (this.get(2)*v.get(1));
		ds[1] = (this.get(2)*v.get(0)) - (this.get(0)*v.get(2));
		ds[2] = (this.get(0)*v.get(1)) - (this.get(1)*v.get(0));
		return new Vector(ds);
	}
	
	/**
	 * Returns the norm of this <code>Vector</code>
	 * @return the norm of this <code>Vector</code>
	 */
	public double getNorm() {
		double d = 0.0;
		for(int i = 0; i < getDimension(); i++) {
			d += Math.pow(get(i), 2);
		}
		return Math.sqrt(d);
	}
	
	/**
	 * Returns the normalized version of this <code>Vector</code>
	 * @return normalized <code>Vector</code>
	 */
	public Vector normalize() {
		double[] ds = new double[getDimension()];
		double norm = getNorm();
		for(int i = 0; i < getDimension(); i++) {
			ds[i] = array[i] / norm;
		}
		return new Vector(ds);
	}
	
	/**
	 * Returns the array representation of this <code>Vector</code>
	 * @return array representing the <code>Vector</code>
	 */
	public double[] getArray() {
		return array;
	}
	
	/**
	 * Returns a <code>Matrix</code> which represents this vector.
	 * @return the <code>Vector</code> in <code>Matrix</code> form.
	 */
	public Matrix toMatrix() {
		double[] ds = new double[getDimension()];
		for (int i = 0; i < getDimension(); i++) {
			ds[i] = this.get(i);
		}
		return new Matrix(ds, getDimension(), 1);
	}
	
	/**
	 * Returns the <code>String</code> representing the <code>Vector</code>
	 * @return a <code>String</code> that represents the <code>Vector</code>
	 */
	@Override
	public String toString() {
		String result = "[";
		int i;
		for(i = 0; i < getDimension()-1; i++) {
			result += (array[i]+0.0)+", ";
		}
		result += (array[i]+0.0) + "]";
		return result;
		
		/* We are using +0.0 when turning doubles to strings because of
		 * the Java "Negative Zero"
		 */
	}

	/**
	 * This method is only for testing the Vector functionality
	 * @param args
	 */
	public static void main(String[] args) {
		
		double[] ds1 = {3.5, 1.5, 2.0};
		double[] ds2 = {1.0, 2.0, 1.5};
		
		Vector v1 = new Vector(ds1);
		Vector v2 = new Vector(ds2);
		
		System.out.println("Vector1 = "+v1.toString());
		System.out.println("Vector2 = "+v2.toString());
		
		System.out.println("Vector1 . Vector2 = "+v1.scalarMult(v2));
		System.out.println("Vector1 x Vector2 = "+v1.vectorProduct(v2));
		
		System.out.println("||Vector1|| = "+v1.getNorm());
		Vector v3 = v1.normalize();
		System.out.println("Normalizing Vector1 = "+v3);
		System.out.println("new ||Vector1|| = "+v3.getNorm());
	}

}