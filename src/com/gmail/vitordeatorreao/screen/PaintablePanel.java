package com.gmail.vitordeatorreao.screen;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.gmail.vitordeatorreao.math.BarycentricCoordinate;
import com.gmail.vitordeatorreao.math.Vector;
import com.gmail.vitordeatorreao.math.Vertex;
import com.gmail.vitordeatorreao.scene.Camera;
import com.gmail.vitordeatorreao.scene.Light;
import com.gmail.vitordeatorreao.scene.Scene;
import com.gmail.vitordeatorreao.scene.SceneController;
import com.gmail.vitordeatorreao.scene.Triangle;
import com.gmail.vitordeatorreao.utils.QuickSortVertices;

/**
 * This class implements a JPanel that will create a 
 * <code>ScreenPainter</code> and paint its screen with the information 
 * of the loaded Scene.
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
public class PaintablePanel extends JPanel {

	private static final long serialVersionUID = 1025412576844168978L;
	
	private Triangle curTriangle;
	private Vertex[] curVertices2D;
	private ZBuffer zBuffer;
	
	/**
	 * Creates a new <code>PaintablePanel</code> instance.
	 * By default, the background is black.
	 */
	public PaintablePanel() {
		super();
		setBackground(Color.black);
		setBorder(new EmptyBorder(10, 10, 10, 10));
	}
	
	/**
	 * Reads the canvas from its <code>ScreenPainter</code> instance and 
	 * and paints it on the screen.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Scene scene = SceneController.getInstance().getScene();
		
		zBuffer = new ZBuffer(getWidth(), getHeight());
		
		for (Triangle t : scene.getTriangles()) {
			curTriangle = t;
			int[][] vertices = new int[3][2];
			// ^- the 3 vertices in screen coordinates
			
			for (int k = 0; k < t.getEdges().length; k++) {
				//Get each vertex screen coordinates
				
				//First, get the vertex
				Vertex v = t.getEdges()[k].get(0);
				
				//Second, change to view base
				Vector w = v.subtract(scene.getCamera().getFocus());
				Vector wInViewBase = scene
									 .getCamera()
									 .getToViewBase()
									 .mult(w);
				
				//Third, project in perspective
				double xs, ys;
				xs = scene.getCamera().getD() * 
						(wInViewBase.get(0)/wInViewBase.get(2));
				ys = scene.getCamera().getD() * 
						(wInViewBase.get(1)/wInViewBase.get(2));
				
				//Forth, normalize
				xs = xs/scene.getCamera().getHx();
				ys = ys/scene.getCamera().getHy();
				
				//Fifth, get screen coordinates
				int i,j;
				double aux;
				aux = ( ((xs+1)/2)*getWidth() ) + 0.5;
				i = ((Double) Math.floor(aux)).intValue();
				aux = (getHeight() - (((ys+1)/2) * getHeight()) + 0.5);
				j = ((Double) Math.floor(aux)).intValue();
				
				//Now, fill the vertices array
				vertices[k][0] = i;
				vertices[k][1] = j;
			}
			
			if (SwingPaint.getShowVertices() && 
					(!SwingPaint.getShowEdges() || 
							!SwingPaint.getShowFaces())
				) {
				//Now we have for 3 vertices in its screen coordinates
				for (int i = 0; i < 3; i++) {
					drawPixel(g, vertices[i][0], vertices[i][1], Color.white);
					
				}
			}
			
			if (SwingPaint.getShowEdges() && !SwingPaint.getShowFaces()) {
				//Now draw edges
				for (int i = 0; i < 3; i++) {
					drawLine(g, 
							vertices[i][0], vertices[i][1], 
							vertices[(i+1)%3][0], vertices[(i+1)%3][1], 
							Color.white);
				}
			}
			
			if (SwingPaint.getShowFaces()) {
				//Now draw the entire triangle
				//This is necessary to the ZBuffer calculation
				curVertices2D = new Vertex[3];
				
				curVertices2D[0] = new Vertex(
						new double[] {vertices[0][0], vertices[0][1]}
				);
				curVertices2D[1] = new Vertex(
						new double[] {vertices[1][0], vertices[1][1]}
				);
				curVertices2D[2] = new Vertex(
						new double[] {vertices[2][0], vertices[2][1]}
				);
				
				QuickSortVertices qsv = new QuickSortVertices();
				qsv.sort(vertices);
								
				if (vertices[1][1] == vertices[2][1]) {
					//Case of bottom flat triangle
					fillBottomFlatTriangle(g, 
							vertices[0], vertices[1], vertices[2], 
							Color.white);
					
				} else if (vertices[0][1] == vertices[1][1]) {
					//Case of top flat triangle
					fillTopFlatTriangle(g, 
							vertices[0], vertices[1], vertices[2], 
							Color.white);
					
				} else {
					//General case
					int[] vertice4 = new int[2];
					vertice4[0] = (int) (vertices[0][0] + 
						( (
								(double) (vertices[1][1] - vertices[0][1]) / 
								(double) (vertices[2][1] - vertices[0][1])
						) * (vertices[2][0] - vertices[0][0]) ));
					vertice4[1] = vertices[1][1];
					
					fillBottomFlatTriangle(g, 
							vertices[0], vertices[1], vertice4, 
							Color.white);
					
					fillTopFlatTriangle(g,
							vertices[1], vertice4, vertices[2], 
							Color.white);
				}
			}
			
		}
		
		//Paint ZBuffer
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				Color c = zBuffer.getColor(i, j);
				drawPixel(g, i, j, c);
			}
		}
	}

	/**
	 * Paints a pixel on the screen.
	 * It is a project requirement that the code must only call this 
	 * function to paint the screen.
	 * @param g a <code>Graphics</code> instance
	 * @param x The x coordinate of the pixel
	 * @param y The y coordinate of the pixel
	 * @param c The <code>Color</code> for the pixel
	 */
	public void drawPixel(Graphics g, int x, int y, Color c) {
		g.setColor(c);
		g.fillRect(x, y, 1, 1);
	}
	
	/**
	 * Paints a straight line between two screen coordinates
	 * @param g a <code>Graphics</code> instance
	 * @param x1 The x coordinate of the first pixel 
	 * @param y1 The y coordinate of the first pixel
	 * @param x2 The x coordinate of the second pixel
	 * @param y2 The y coordinate of the second pixel
	 * @param c The <code>Color</code> for the line
	 */
	public void drawLine(Graphics g, int x1, int y1, int x2, int y2, Color c) {
		//Rasterize a Line using BresenhamAlgorithm for Integers
		boolean changed = false;
		int x = x1;
		int y = y1;
		
		int dx = Math.abs(x2 - x1);
		int dy = Math.abs(y2 - y1);
		
		int signx = signum(x2 - x1);
		int signy = signum(y2 - y1);
		
		if (dy > dx) {
			//Swap dx and dy
			int temp = dx;
			dx = dy;
			dy = temp;
			changed = true;
		}
		double e = (2 * dy) - dx;
		for (int i = 1; i <= dx; i++) {
			drawPixel(g, x, y, c);
			while(e >= 0) {
				if (changed) {
					x = x + signx;
				} else {
					y = y + signy;
				}
				e = e - (2 * dx);
			}
			if (changed) {
				y += signy;
			} else {
				x += signx;
			}
			e = e + (2 * dy);
		}
	}

	/**
	 * Paints a special case of a bottom-flat triangle.
	 * @param g a <code>Graphics</code> instance
	 * @param v1 Top vertex of the triangle
	 * @param v2 One of the bottom vertices of the triangle
	 * @param v3 The other bottom vertex of the triangle
	 * @param c a <code>Color</code> to paint the triangle
	 */
	public void fillBottomFlatTriangle(Graphics g, 
					int[] v1, int[] v2, int[] v3, Color c) {
		
		double invslope1 =	((double) (v2[0] - v1[0])) / 
							((double) (v2[1] - v1[1]));
		double invslope2 =	((double) (v3[0] - v1[0])) / 
							((double) (v3[1] - v1[1]));
		
		double curx1 = v1[0];
		double curx2 = v1[0];
		
		for (int scanlineY = v1[1]; scanlineY <= v2[1]; scanlineY++) {
			//Avoiding calculating for pixels outside the screen
			if (v2[1] < 0) {
				break;
			}
			if (scanlineY < 0) {
				//Update curx1 and curx2 straight to correct slope
				curx1 += invslope1 * (0 - scanlineY);
				curx2 += invslope2 * (0 - scanlineY);
				scanlineY = 0;
			}
			if (scanlineY > getHeight()) {
				break;
			}
			
			int xMin = Math.min((int) curx1, (int) curx2);
			int xMax = Math.max((int) curx1, (int) curx2);
			for (int x = xMin; x <= xMax; x++) {
				//Avoiding calculating for pixels outside the screen
				if (xMax < 0) {
					break;
				}
				if (x < 0) {
					x = 0;
				}
				if (x > getWidth()) {
					break;
				}
				calcZBuffer(x, scanlineY);
			}
			
			curx1 += invslope1;
			curx2 += invslope2;
			
		}
		
	}
	
	/**
	 * Paints a special case of a top-flat triangle.
	 * @param g a <code>Graphics</code> instance
	 * @param v1 Bottom vertex of the triangle
	 * @param v2 One of the top vertices of the triangle
	 * @param v3 The other top vertex of the triangle
	 * @param c a <code>Color</code> to paint the triangle
	 */
	public void fillTopFlatTriangle(Graphics g, 
			int[] v1, int[] v2, int v3[], Color c) {
		
		double invslope1 =	((double) (v1[0] - v3[0])) / 
							((double) (v1[1] - v3[1]));
		double invslope2 =	((double) (v2[0] - v3[0])) / 
							((double) (v2[1] - v3[1]));
		
		double curx1 = v3[0];
		double curx2 = v3[0];
		
		for (int scanlineY = v3[1]; scanlineY > v1[1]; scanlineY--) {
			//Avoiding calculating for pixels outside the screen
			if (v1[1] > getHeight()) {
				break;
			}
			if (scanlineY > getHeight()) {
				//Update curx1 and curx2 straight to correct slope
				curx1 -= invslope1 * (scanlineY - getHeight());
				curx2 -= invslope2 * (scanlineY - getHeight());
				scanlineY = getHeight();
			}
			if (scanlineY < 0) {
				break;
			}
			
			int xMin = Math.min((int) curx1, (int) curx2);
			int xMax = Math.max((int) curx1, (int) curx2);
			for (int x = xMin; x <= xMax; x++) {
				//Avoiding calculating for pixels outside the screen
				if (xMax < 0) {
					break;
				}
				if (x < 0) {
					x = 0;
				}
				if (x > getWidth()) {
					break;
				}
				calcZBuffer(x, scanlineY);
			}
			
			curx1 -= invslope1;
			curx2 -= invslope2;
		}
		
	}
	
	/**
	 * Function to evaluate the signal of an integer.
	 * @param i The integer being evaluated
	 * @return	<code>-1</code> if the integer is less than zero;
	 * 			<code>0</code> if the integer is zero;
	 * 			<code>1</code> if the integer is grater than zero.
	 */
	private int signum(int i) {
		if (i < 0) {
			return -1;
		} else if (i > 0) {
			return 1;
		} else {
			return 0;
		}
	}
	
	private void calcZBuffer(int x, int y) {
		Camera camera = SceneController.getInstance().getScene().getCamera();
		
		Vertex v = new Vertex(
				new double[] {x, y}
		);
		BarycentricCoordinate bc;
		/* It might happen that a triangle be projected into a straight line
		 * in screen coordinates. If so, it is impossible to calculate the
		 * Barycentric Coordinates, because they are collinear.
		 * In that case, ignore this one.
		 */
		try {
			bc = new BarycentricCoordinate(
					v, curVertices2D[0], curVertices2D[1], curVertices2D[2]
			);
		} catch(IllegalArgumentException e) {
			return;
		}
		double[] pCoords = new double[3];
		pCoords[0] = 
				bc.getCoord(0)*curTriangle.getVertex(0).getCoord(0)+
				bc.getCoord(1)*curTriangle.getVertex(1).getCoord(0)+
				bc.getCoord(2)*curTriangle.getVertex(2).getCoord(0);
		pCoords[1] = 
				bc.getCoord(0)*curTriangle.getVertex(0).getCoord(1)+
				bc.getCoord(1)*curTriangle.getVertex(1).getCoord(1)+
				bc.getCoord(2)*curTriangle.getVertex(2).getCoord(1);
		pCoords[2] = 
				bc.getCoord(0)*curTriangle.getVertex(0).getCoord(2)+
				bc.getCoord(1)*curTriangle.getVertex(1).getCoord(2)+
				bc.getCoord(2)*curTriangle.getVertex(2).getCoord(2);
		Vertex originalP = new Vertex(pCoords);
		
		Vector vct = camera.getFocus().subtract(originalP);
		double deepness = vct.getNorm();
		
		if (deepness < zBuffer.getDeepness(x, y)) {
			//Calculate color and update zBuffer
			Light light = SceneController.getInstance().getScene().getLight();
			Color c;
			if (light == null) {
				c = Color.black;
			} else {
				double alpha = bc.getCoord(0);
				double beta = bc.getCoord(1);
				double gama = bc.getCoord(2);
				
				Vector N1 = curTriangle.getVertex(0).getNormal();
				Vector N2 = curTriangle.getVertex(1).getNormal();
				Vector N3 = curTriangle.getVertex(2).getNormal();
				
				Vector N = N1.mult(alpha)
						.add(N2.mult(beta))
						.add(N3.mult(gama));
				
				N = N.normalize();
				
				Vector V = camera.getFocus().subtract(originalP);
				V = V.normalize();
				
				Vector L = light.getpL().subtract(originalP);
				L = L.normalize();
				
				double aux = 2.0 * N.scalarMult(L);
				Vector R = (N.mult(aux)).sub(L);
				
				boolean noSpecular = false;
				boolean noDiffuse = false;
				
				//Special Cases
				if (N.scalarMult(L) < 0.0) {
					if (V.scalarMult(N) < 0.0) {
						N = N.mult(-1.0);
					} else {
						noDiffuse = true;
						noSpecular = true;
					}
				}
				
				if (V.scalarMult(R) < 0.0) {
					noSpecular = true;
				}
				
				double[] Is = new double[] {0.0, 0.0, 0.0};
				if (!noSpecular) {
					double m =	Math.pow(R.scalarMult(V), light.getN()) *
								light.getkS();
					Is[0] = m*light.getiL().getRed();
					Is[1] = m*light.getiL().getGreen();
					Is[2] = m*light.getiL().getBlue();
				}
				double[] Id = new double[] {0.0, 0.0, 0.0};
				if (!noDiffuse) {
					Vector M = light.getkD().mult(N.scalarMult(L));
					
					Id[0] = M.get(0) *
							light.getoD().get(0) *
							light.getiL().getRed();
					Id[1] = M.get(1) *
							light.getoD().get(1) *
							light.getiL().getGreen();
					Id[2] = M.get(2) *
							light.getoD().get(2) *
							light.getiL().getBlue();	
				}
				double[] Ia = new double[3];
				Ia[0] = light.getkA()*light.getiAmb().getRed();
				Ia[1] = light.getkA()*light.getiAmb().getGreen();
				Ia[2] = light.getkA()*light.getiAmb().getBlue();
				
				int[] I = new int[3];
				I[0] = roundToColor(Math.round(Ia[0] + Is[0] + Id[0]));
				I[1] = roundToColor(Math.round(Ia[1] + Is[1] + Id[1]));
				I[2] = roundToColor(Math.round(Ia[2] + Is[2] + Id[2]));
				
				c = new Color(I[0], I[1], I[2]);
				
			}
			zBuffer.set(x, y, c, deepness);
		}
				
	}
	
	private int roundToColor(long l) {
		if (l > 255) {
			return 255;
		} else if (l < 0) {
			return 0;
		} else {
			return new Long(l).intValue();
		}
	}

}
