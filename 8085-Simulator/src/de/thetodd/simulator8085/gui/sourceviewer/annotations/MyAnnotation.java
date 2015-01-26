package de.thetodd.simulator8085.gui.sourceviewer.annotations;

import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Image;

public abstract class MyAnnotation extends Annotation {

	private int line;

	public MyAnnotation(int line, String type) {
		super(type, true, null);
		this.line = line;
	}
	
	public abstract Image getImage();

	public int getLine() {
		return line;
	}
	
}
