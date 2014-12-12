package com.gmail.vitordeatorreao.scene;

import java.awt.Color;
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
	
	/**
	 * Singleton Pattern
	 */
	private static SceneController instance;
	
	/**
	 * Part of Singleton Pattern. This method is the only 
	 * way to get an instance of this class.
	 * @return the unique instance of <code>SceneController</code>
	 */
	public static SceneController getInstance() {
		if (instance == null) {
			instance = new SceneController();
		}
		return instance;
	}
	
	private Scene scene;
	private BufferedReader bfr;
	
	/**
	 * This private constructor is part of the Singleton Pattern.
	 */
	private SceneController() {
		this.scene = new Scene();
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	/**
	 * Loads scene elements from .byu files.
	 * @param	file The file to read the scene elements from
	 * @throws	IOException In case there is a problem reading the file
	 * @throws	NonConformantSceneFile In case the file doesn't respect 
	 * 			the .byu standard
	 */
	public void loadScene(File file) throws IOException, 
										NonConformantSceneFile {
		bfr = new BufferedReader(new FileReader(file));
		
		
		
		String line = null;
		while ( (line = bfr.readLine()) != null ) {
			if (line.equals("\n")) {
				continue;
			}
			String[] args = line.split(" ");
			if (args.length == 2) {
				loadObjects(line);
			} else if (args.length == 3){
				loadCamera(line);
			}
		}
		bfr.close();
		
		/* Sort the triangles only if there are triangles AND
		 * a camera has been instantiated 
		 */
		if (this.scene.getTriangles().size() > 0 && 
				this.scene.getCamera() != null) {
			this.scene.sortTriangles();
		}
	}
	
	/**
	 * From a Line string starts to load objects into memory
	 * @param line A line from a BYU file
	 * @throws IOException If <code>BufferedReader</code> stops working
	 * @throws NonConformantSceneFile If there is a syntax error with the file
	 */
	private void loadObjects(String line) throws IOException, NonConformantSceneFile {
		String[] args = line.split(" ");
		//Reading an object
		//Clean the previously loaded object
		this.scene.cleanTriangles();
		//Load the new
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
		
		//Operate over all triangles
		
		for (Vertex v : vs) {
			Vector norm = new Vector(new double[] {0.0, 0.0, 0.0});
			for (Triangle t : this.scene.getTriangles()) {
				if (v.in(t)) {
					norm = norm.add(t.getNormal());
				}
			}
			norm = norm.normalize();
			v.setNormal(norm);
		}
	}
	
	/**
	 * From a Line string starts to load the Camera and Light into memory
	 * depending on what is in the file. The program can't read light and
	 * camera separately. They need to be one after the other.
	 * @param line A line from a BYU file
	 * @throws IOException If <code>BufferedReader</code> stops working
	 * @throws NonConformantSceneFile If there is a syntax error with the file
	 */
	private void loadCamera(String line) throws IOException, NonConformantSceneFile {
		String[] args = line.split(" ");
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
		System.out.println("Camera = "+scene.getCamera().toString());
		
		line = bfr.readLine();
		if (line == null) {
			//If there is nothing else
			return;
		}
		//But if there is and
		args = line.split(" ");
		if (args.length == 3) {
			//Load Light
			//In the current line, we find the environmental light color
			Color iAmb;
			int r,g,b;
			try {
				r = Integer.parseInt(args[0]);
				g = Integer.parseInt(args[1]);
				b = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				throw new NonConformantSceneFile("Expected three integers,"
						+ " but found \""+line+"\"");
			}
			iAmb = new Color(r, g, b);
			
			//Now get iL, the light color
			line = bfr.readLine();
			if (line == null) {
				throw new NonConformantSceneFile("Expected Light Color"
						+ " specification, but found nothing");
			}
			args = line.split(" ");
			if (args.length != 3) {
				throw new NonConformantSceneFile("Expected Light Color"
						+ " specification, but found \""+line+"\"");
			}
			Color iL;
			try {
				r = Integer.parseInt(args[0]);
				g = Integer.parseInt(args[1]);
				b = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				throw new NonConformantSceneFile("Expected three integers,"
						+ " but found \""+line+"\"");
			}
			iL = new Color(r, g, b);
			
			//Now get Ka, Ks, eta in a single line
			line = bfr.readLine();
			if (line == null) {
				throw new NonConformantSceneFile("Expected the "
						+ "environmental reflection coefficient, "
						+ "specular reflection coefficient and the "
						+ "specular size"
						+ " specifications, but found nothing");
			}
			args = line.split(" ");
			double ka, ks, eta;
			try {
				ka = Double.valueOf(args[0]);
				ks = Double.valueOf(args[1]);
				eta = Double.valueOf(args[2]);
			} catch (Exception e) {
				throw new NonConformantSceneFile("Expected three double-point "
						+ "precision values for Ka, Ks and eta,"
						+ " but found \""+line+"\"");
			}
			
			//Now get Kd
			line = bfr.readLine();
			if (line == null) {
				throw new NonConformantSceneFile("Expected Diffuse "
						+ "reflection coefficient"
						+ " specification, but found nothing");
			}
			args = line.split(" ");
			double[] dds;
			try {
				dds = new double[] {
					Double.valueOf(args[0]),
					Double.valueOf(args[1]),
					Double.valueOf(args[2])
				};
			} catch (Exception e) {
				throw new NonConformantSceneFile("Expected three double-point "
						+ "precision values for Diffuse reflection coefficient"
						+ " vector,"
						+ " but found \""+line+"\"");
			}
			Vector kD = new Vector(dds);
			
			//Now get Od
			line = bfr.readLine();
			if (line == null) {
				throw new NonConformantSceneFile("Expected surface "
						+ "diffuse color"
						+ " specification, but found nothing");
			}
			args = line.split(" ");
			double[] ods;
			try {
				ods = new double[] {
					Double.valueOf(args[0]),
					Double.valueOf(args[1]),
					Double.valueOf(args[2])
				};
			} catch (Exception e) {
				throw new NonConformantSceneFile("Expected three double-point "
						+ "precision values for surface diffuse color"
						+ " vector,"
						+ " but found \""+line+"\"");
			}
			Vector oD = new Vector(ods);
			
			//Now get Pl
			line = bfr.readLine();
			if (line == null) {
				throw new NonConformantSceneFile("Expected surface "
						+ "diffuse color"
						+ " specification, but found nothing");
			}
			args = line.split(" ");
			double[] pls;
			try {
				pls = new double[] {
					Double.valueOf(args[0]),
					Double.valueOf(args[1]),
					Double.valueOf(args[2])
				};
			} catch (Exception e) {
				throw new NonConformantSceneFile("Expected three double-point "
						+ "precision values for Origin of Light"
						+ " vertex,"
						+ " but found \""+line+"\"");
			}
			Vertex pl = new Vertex(pls);
			
			this.scene.setLight(new Light(iAmb, iL, ka, ks, eta, kD, oD, pl));
			System.out.println("Light = "+this.scene.getLight().toString());
			
		} else if (args.length == 2) {
			loadObjects(line);
		}
	}
	
	/**
	 * Calculates the distance between the <code>Triangle</code>'s 
	 * centroid and the camera's focus.
	 * @param t1 The <code>Triangle</code>
	 * @return double The distance
	 */
	public static double d(Triangle t1) {
		Vector v = t1.getCentroid().subtract(
				getInstance().getScene().getCamera().getFocus()
		);
		return v.getNorm();
	}

	/**
	 * Calculates the distance between the <code>Vertex</code> 
	 * and the camera's focus.
	 * @param t1 The <code>Triangle</code>
	 * @return double The distance
	 */
	public static double d(Vertex v) {
		Vector vct = v.subtract(
				getInstance().getScene().getCamera().getFocus()
		);
		return vct.getNorm();
	}
	
	/**
	 * This function is only used for testing.
	 */
	public static void main(String[] args) {
		SceneController sc = SceneController.getInstance();
		try {
			sc.loadScene(new File("samples/camera4.byu")); //Make sure you have one
			System.out.println(sc.getScene().toString());
			System.out.println(sc.getScene().getTriangles().size());
			System.out.println(sc.getScene().getCamera().toString());
		} catch (IOException | NonConformantSceneFile e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
