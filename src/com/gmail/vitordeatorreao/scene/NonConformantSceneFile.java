package com.gmail.vitordeatorreao.scene;

public class NonConformantSceneFile extends Exception {

	private static final long serialVersionUID = 4884504621755496568L;
	
	public NonConformantSceneFile() {
		super();
	}
	
	public NonConformantSceneFile(String message) {
		super(message);
	}
	
	public NonConformantSceneFile(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NonConformantSceneFile(Throwable cause) {
		super(cause);
	}

}
