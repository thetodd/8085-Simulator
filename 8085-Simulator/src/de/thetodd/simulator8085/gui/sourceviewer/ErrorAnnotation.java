package de.thetodd.simulator8085.gui.sourceviewer;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

import de.thetodd.simulator8085.gui.SimulatorMainWindow;

public class ErrorAnnotation extends Annotation {

	private IMarker marker;
	private String text;
	private int line;
	private Position position;
	public static final String ERROR_TYPE = "error.type";
	private static final Image ERROR_IMG = SWTResourceManager
			.getImage(SimulatorMainWindow.class,
					"/de/thetodd/simulator8085/gui/icons/warning.png");

	public ErrorAnnotation(IMarker marker) {
		this.marker = marker;
	}

	public ErrorAnnotation(int line, String text) {
		super(ERROR_TYPE, true, null);
		this.marker = null;
		this.line = line;
		this.text = text;
	}

	public IMarker getMarker() {
		return marker;
	}

	public int getLine() {
		return line;
	}

	public String getText() {
		return text;
	}

	public Image getImage() {
		return ERROR_IMG;
	}

	public int getLayer() {
		return 3;
	}

	public String getType() {
		return ERROR_TYPE;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
