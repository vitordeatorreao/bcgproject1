package com.gmail.vitordeatorreao.math;

/**
 * This class implements 
 * <a href="http://en.wikipedia.org/wiki/Matrix_%28mathematics%29">Matrix</a> 
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
public class Matrix {
	
	private double[] array;
	private int numRows;
	private int numColumns;
	
	/**
	 * A <code>Matrix</code> object must be defined by an array of 
	 * <code>double</code> numbers and its dimensions.
	 * @param	array one dimensional array on which the 
	 * 			<code>Matrix</code> will be based.
	 * @param	rows number of rows this <code>Matrix</code> will have.
	 * @param	columns number of columns this <code>Matrix</code> will have.
	 */
	public Matrix(double[] array, int rows, int columns) {
		if(array.length != rows * columns) {
			throw new IllegalArgumentException("The provided array is not "
					+ "compatible with the specified dimensions.");
		}
		this.array = array;
		this.numColumns = columns;
		this.numRows = rows;
	}
	
	/**
	 * Returns the element at position <i>(i,j)</i>.
	 * @param i row index
	 * @param j column index
	 * @return	<code>double</double> value at position <i>(i,j)</i> 
	 * 			in the <code>Matrix</code>.
	 */
	public double get(int i, int j) {
		return array[(i*this.numColumns)+j];
	}
	
	/**
	 * Sets the value at position <i>(i,j)</i>.
	 * @param i row index
	 * @param j column index
	 * @param newValue the new value at position <i>(i,j)</i>
	 */
	public void set(int i, int j, double newValue) {
		array[(i*this.numColumns)+j] = newValue;
	}
	
	/**
	 * Returns the number of rows in the Matrix.
	 * @return number of rows
	 */
	public int getNumRows() {
		return numRows;
	}

	/**
	 * Returns the number of columns in the Matrix
	 * @return number of columns
	 */
	public int getNumColumns() {
		return numColumns;
	}

	/**
	 * Adds two matrices together.
	 * @param m the <code>Matrix</code> to be added to this instance. 
	 * @return New <code>Matrix</code> resulting from the operation. 
	 */
	public Matrix add(Matrix m) {
		if (this.numRows != m.getNumRows() || 
			this.numColumns != m.getNumColumns()) {
			throw new IllegalArgumentException(
					"Matrices must be of same dimension");
		}
		Matrix result = new Matrix(new double[this.numRows * this.numColumns], 
				this.numRows, this.numColumns);
		for (int i = 0; i < this.numRows; i++) {
			for (int j = 0; j < this.numColumns; j++) {
				result.set(i, j, this.get(i, j) + m.get(i, j));
			}
		}
		return result;
	}
	
	/**
	 * Multiplies two matrices together.
	 * @param m the <code>Matrix</code> to be added to this instance. 
	 * @return New <code>Matrix</code> resulting from the operation.
	 */
	public Matrix mult(Matrix m) {
		if(this.getNumColumns() != m.getNumRows()) {
			throw new IllegalArgumentException(
					"matrix1 is "+this.getNumRows()+"x"+this.getNumColumns()+
					"while matrix 2 is "+m.getNumRows()+"x"+m.getNumColumns()
			);
		}
		double[] ds = new double[this.numRows*m.getNumColumns()];
		Matrix result = new Matrix(ds, numRows, m.getNumColumns());
		for (int i = 0; i < this.getNumRows(); i++) {
			for(int j = 0; j < m.getNumColumns(); j++) {
				double sum = 0;
				for(int k = 0; k < this.getNumColumns(); k++) {
					sum += this.get(i, k) * m.get(k, j);
				}
				result.set(i, j, sum);
			}
		}
		return result;
	}
	
	/**
	 * Multiplies a Vector by a Matrix. Use this for transformations.
	 * @param v The <code>Vector</code> to multiply by this <code>Matrix</code>
	 * @return The resulting <code>Vector</code>
	 */
	public Vector mult(Vector v) {
		double[] ds = new double[v.getDimension()];
		Matrix m = this.mult(v.toMatrix());
		for (int i = 0; i < m.getNumRows(); i++) {
			ds[i] = m.get(i, 0);
		}
		return new Vector(ds);
	}
	
	/**
	 * Transposes this <code>Matrix</code>.
	 * @return The resulting transposed <code>Matrix</code>.
	 */
	public Matrix transpose()  {
		Matrix result = new Matrix(new double[this.numColumns * this.numRows], 
				this.numColumns, this.numRows);
		for (int i = 0; i < result.getNumRows(); i++) {
			for (int j = 0; j < result.getNumColumns(); j++) {
				result.set(i, j, this.get(j, i));
			}
		}
		return result;
	}
	
	/**
	 * Inverts this <code>Matrix</code>.<br />
	 * This implementation currently only inverts 2x2 Matrices.
	 * @return The inverted <code>Matrix</code>
	 */
	public Matrix inverse() {
		if(this.getNumColumns() != this.getNumRows() &&
				this.getNumRows() != 2) {
			/* It can be computed, of course,
			 * but it is outside the scope of this program 
			 */
			throw new IllegalArgumentException("The only implemented inverse"
					+ " in this program is for 2x2 matrices");
		}
		double[] inv = new double[4];
		double determinant =	(this.get(0, 0)*this.get(1, 1)) -
								(this.get(0, 1)*this.get(1, 0));
		inv[0] = this.get(1, 1) * (1/determinant);
		inv[1] = (-1)*this.get(0, 1) * (1/determinant);
		inv[2] = (-1)*this.get(1, 0) * (1/determinant);
		inv[3] = this.get(0, 0) * (1/determinant);
		Matrix inverse = new Matrix(inv, 2, 2);
		return inverse;
	}
	
	/**
	 * Renders this <code>Matrix</code> in a <code>String</code>.
	 * @return	<code>String</code> containing all information 
	 * 			from this <code>Matrix</code>.
	 */
	@Override
	public String toString() {
		String result = "[\n";
		int i,j;
		for(i = 0; i < this.numRows; i++) {
			result += "\t";
			for(j = 0; j < this.numColumns; j++) {
				result += (this.get(i, j)+0.0)+"\t\t";
			}
			result += "\n";
		}
		result += "]";
		return result;
		
		/* We are using +0.0 when turning doubles to strings because of
		 * the Java "Negative Zero"
		 */
	}
	
	//Just for testing
	/**
	 * This method is only used to test this Matrix implementation.
	 * <p>
	 * You can also use it as an example.
	 * @param args
	 */
	public static void main(String[] args) {
		double[] d = {1.5,2.5,3.5,4.5,5.5,6.5};
		Matrix m = new Matrix(d, 2, 3);
		System.out.println("Matrix1 = "+m.toString()+"\n");
		
		double[] d2 = {7.5, 8.5, 9.5, 10.5, 11.5, 12.5};
		Matrix m2 = new Matrix(d2, 3, 2);
		System.out.println("Matrix2 = "+m2.toString());
		
		double[] d3 = {1.5,2.5,3.5,4.5,5.5,6.5};
		Matrix m3 = new Matrix(d3, 2, 3);
		System.out.println("Matrix3 = "+m3.toString());
		
		System.out.println("Matrix1 * Matrix2 = "+m.mult(m2).toString());
		
		System.out.println("Matrix1 + Matrix3 = "+m.add(m3).toString());
		
		System.out.println("Matrix1 transposed = "+m.transpose());
	}

}
