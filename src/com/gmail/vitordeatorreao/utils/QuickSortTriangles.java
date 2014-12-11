package com.gmail.vitordeatorreao.utils;

import java.util.ArrayList;

import com.gmail.vitordeatorreao.scene.SceneController;
import com.gmail.vitordeatorreao.scene.Triangle;

/**
 * This class implements a QuickSort algorithm to sort the triangles 
 * according to their centroid's distance to the camera focus.
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
public class QuickSortTriangles {

	private ArrayList<Triangle> triangles;
	private int numTriangles;

	/**
	 * Sorts the array called by reference.
	 * @param values The array with the Triangles
	 */
	public void sort(ArrayList<Triangle> values) {
		// check for empty or null array
		if (values ==null || values.size()==0){
			return;
		}
		this.triangles = values;
		numTriangles = values.size();
		quicksort(0, numTriangles - 1);
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
		Triangle pivot = triangles.get(low + (high-low)/2);

		// Run through the left and right of the pivot
		while (i <= j) {
			while (SceneController.d(triangles.get(i)) < 
					SceneController.d(pivot)) {
				/* If the current value from the left list is smaller then the pivot
				 * element then get the next element from the left list
				 */
				i++;
			}
			while (SceneController.d(triangles.get(j)) > 
					SceneController.d(pivot)) {
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
		Triangle temp = triangles.get(i);
		triangles.set(i, triangles.get(j));
		triangles.set(j, temp);
	}

}
