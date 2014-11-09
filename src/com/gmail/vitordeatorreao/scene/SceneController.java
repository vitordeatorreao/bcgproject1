package com.gmail.vitordeatorreao.scene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import com.gmail.vitordeatorreao.math.Vector;
import com.gmail.vitordeatorreao.math.Vertex;

/**
 * This class implements a Scene Controller.
 * The Scene Controller will be responsible for loading 
 * elements into the Scene. Such as Cameras and Triangles.
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
public class SceneController {
	
	private static SceneController instance;
	
	public static SceneController getInstance() {
		if (instance == null) {
			instance = new SceneController();
		}
		return instance;
	}
	
	private Scene scene;
	
	private SceneController() {
		this.scene = new Scene();
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	public void loadScene(File file) throws IOException, 
										NonConformantSceneFile {
		BufferedReader bfr = new BufferedReader(new FileReader(file));
		
		String line = null;
		while ( (line = bfr.readLine()) != null ) {
			if (line.equals("\n")) {
				continue;
			}
			String[] args = line.split(" ");
			if (args.length == 2) {
				//Reading an object
				int numVertices;
				int numTriangles;
				try {
					numVertices = Integer.valueOf(args[0]);
					numTriangles = Integer.valueOf(args[1]);				
				} catch (NumberFormatException nfe) {
					bfr.close();
					throw new NonConformantSceneFile("Expected two "
							+ "Integer values, "+"but found \""+args[0]+" "
							+args[1]+"\"");
				}
				
				//Read all vertices
				Vertex[] vs = new Vertex[numVertices];
				for (int i = 0; i < vs.length; i++) {
					line = bfr.readLine();
					if (line == null) {
						bfr.close();
						throw new NonConformantSceneFile("Expected "
								+numVertices+" vertices, but found only "+i);
					}
					String[] values = line.split(" ");
					if (values.length < 3) {
						bfr.close();
						throw new NonConformantSceneFile("All vertices must "
								+ "have at least 3 coordinates");
					}
					double[] coords = new double[3];
					try {
						coords[0] = Double.valueOf(values[0]);
						coords[1] = Double.valueOf(values[1]);
						coords[2] = Double.valueOf(values[2]);
					} catch (Exception e) {
						bfr.close();
						throw new NonConformantSceneFile("Expected 3 "
								+ "double-point precision values, but "
								+ "found \""+line+"\"");
					}
					vs[i] = new Vertex(coords);
				}
				
				//Form all triangles
				for (int i = 0; i < numTriangles; i++) {
					line = bfr.readLine();
					if (line == null) {
						bfr.close();
						throw new NonConformantSceneFile("Expected "+
								numTriangles+" triangles, but found only "+i);
					}
					String[] values = line.split(" ");
					if (values.length < 3) {
						bfr.close();
						throw new NonConformantSceneFile("All triangles must "+
								"have at least 3 vertices");
					}
					Vertex[] triVertices = new Vertex[3];
					int index = Integer.valueOf(values[0]) - 1;
					triVertices[0] = vs[index]; 
					index = Integer.valueOf(values[1]) - 1;
					triVertices[1] = vs[index];
					index = Integer.valueOf(values[2]) - 1;
					triVertices[2] = vs[index];
					Edge[] es = new Edge[3];
					for (int k = 0; k < 3; k++) {
						es[k] = new Edge(triVertices[k], triVertices[(k+1)%3]);
					}
					this.scene.addTriangle(new Triangle(es[0], es[1], es[2]));
				}
			} else if (args.length == 3){
				//Reading camera
				double[] ds = new double[3];
				try {
					ds[0] = Double.valueOf(args[0]);
					ds[1] = Double.valueOf(args[1]);
					ds[2] = Double.valueOf(args[2]);
				} catch (Exception e) {
					bfr.close();
					throw new NonConformantSceneFile("Expected 3 double-point "
							+ "precision values, but found \""+line+"\"");
				}
				Vertex C = new Vertex(ds);
				
				line = bfr.readLine();
				if (line == null) {
					bfr.close();
					throw new NonConformantSceneFile("Expected to find more "
							+ "camera parameters, but found only 1");
				}
				args = line.split(" ");
				double[] ds2 = new double[3];
				try {
					ds2[0] = Double.valueOf(args[0]);
					ds2[1] = Double.valueOf(args[1]);
					ds2[2] = Double.valueOf(args[2]);
				} catch (Exception e) {
					bfr.close();
					throw new NonConformantSceneFile("Expected 3 double-point "
							+ "precision values, but found \""+line+"\"");
				}
				Vector N = new Vector(ds2);
				
				line = bfr.readLine();
				if (line == null) {
					bfr.close();
					throw new NonConformantSceneFile("Expected to find more "
							+ "camera parameters, but found only 2");
				}
				args = line.split(" ");
				
				double[] ds3 = new double[3];
				try {
					ds3[0] = Double.valueOf(args[0]);
					ds3[1] = Double.valueOf(args[1]);
					ds3[2] = Double.valueOf(args[2]);
				} catch (Exception e) {
					bfr.close();
					throw new NonConformantSceneFile("Expected 3 double-point "
							+ "precision values, but found \""+line+"\"");
				}
				Vector V = new Vector(ds3);
				
				line = bfr.readLine();
				if (line == null) {
					bfr.close();
					throw new NonConformantSceneFile("Expected to find more "
							+ "camera parameters, but found only 3");
				}
				double d;
				try {
					d = Double.valueOf(line);
				} catch (Exception e) {
					bfr.close();
					throw new NonConformantSceneFile("Expected a double-point "
							+ "precision value, but found \""+line+"\"");
				}
				
				line = bfr.readLine();
				if (line == null) {
					bfr.close();
					throw new NonConformantSceneFile("Expected to find more "
							+ "camera parameters, but found only 4");
				}
				double hx;
				try {
					hx = Double.valueOf(line);
				} catch (Exception e) {
					bfr.close();
					throw new NonConformantSceneFile("Expected a double-point "
							+ "precision value, but found \""+line+"\"");
				}
				
				line = bfr.readLine();
				if (line == null) {
					bfr.close();
					throw new NonConformantSceneFile("Expected to find more "
							+ "camera parameters, but found only 5");
				}
				double hy;
				try {
					hy = Double.valueOf(line);
				} catch (Exception e) {
					bfr.close();
					throw new NonConformantSceneFile("Expected a double-point "
							+ "precision value, but found \""+line+"\"");
				}
				this.scene.setCamera(new Camera(C, N, V, d, hx, hy));
			}
		}
		bfr.close();
	}
	
	public static void main(String[] args) {
		SceneController sc = SceneController.getInstance();
		try {
			sc.loadScene(new File("vaso.byu"));
			System.out.println(sc.getScene().toString());
			System.out.println(sc.getScene().getTriangles().size());
			System.out.println(sc.getScene().getCamera().toString());
		} catch (IOException | NonConformantSceneFile e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
