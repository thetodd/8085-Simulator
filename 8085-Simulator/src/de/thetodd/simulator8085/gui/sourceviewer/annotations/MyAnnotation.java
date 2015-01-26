package de.thetodd.simulator8085.gui.sourceviewer.annotations;

import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Image;

public abstract class MyAnnotation extends Annotation {

	public MyAnnotation(String type) {
		super(type, true, null);
	}
	
	public abstract Image getImage();
	
}
