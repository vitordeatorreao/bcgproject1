package com.gmail.vitordeatorreao.utils;

/**
 * This class implements a QuickSort algorithm to sort the vertices of 
 * a triangle according to the y coordinate. The vertices must already
 * be in screen coordinates and aggregated in a 2-dimensional array 
 * such that:<br> v[0][1] -> y coordinate of the first vertex, similarly, 
 * v[1][0] -> x coordinate of the second vertex.
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
public class QuickSortVertices {
	
	private int[][] vertices;
	private int numVertices;

	/**
	 * Sorts the array called by reference.
	 * @param values the 2-dimensional array with the vertices of the triangle
	 */
	public void sort(int[][] values) {
		// check for empty or null array
		if (values ==null || values.length==0){
			return;
		}
		this.vertices = values;
		numVertices = 3;
		quicksort(0, numVertices - 1);
	}

	/**
	 * Implements the recursive form of the QuickSort Algorithm.
	 * Will only compare the y coordinate of each vertex.
	 * @param low 
	 * @param high 
	 */
	private void quicksort(int low, int high) {
		int i = low, j = high;
		// Get the pivot element from the middle of the list
		int[] pivot = vertices[low + (high-low)/2];

		// Run through the left and right of the pivot
		while (i <= j) {
			while (vertices[i][1] < pivot[1]) {
				/* If the current value from the left list is smaller then the pivot
				 * element then get the next element from the left list
				 */
				i++;
			}
			while (vertices[j][1] > pivot[1]) {
				j--;
				/* If the current value from the right list is larger then the pivot
				 * element then get the next element from the right list
				 */
			}

			/* If we have found a values in the left list which is larger then
			 * the pivot element and if we have found a value in the right list
			 * which is smaller then the pivot element...
			 */
			if (i <= j) {
				exchange(i, j); //... then we exchange the values, and
				// increase i and j
				i++;
				j--;
			}
		}
		// Recursive part
		if (low < j)
			quicksort(low, j);
		if (i < high)
			quicksort(i, high);
	}

	/**
	 * Swaps the ith vertex for the jth vertex.
	 * @param i The vertex index
	 * @param j The other vertex index
	 */
	private void exchange(int i, int j) {
		int[] temp = new int[2];
		temp[0] = vertices[i][0];
		temp[1] = vertices[i][1];
		vertices[i][0] = vertices[j][0];
		vertices[i][1] = vertices[j][1];
		vertices[j][0] = temp[0];
		vertices[j][1] = temp[1];
	}
}
