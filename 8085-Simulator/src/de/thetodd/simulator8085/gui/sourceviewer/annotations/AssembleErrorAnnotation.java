package de.thetodd.simulator8085.gui.sourceviewer.annotations;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.Position;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

import de.thetodd.simulator8085.gui.SimulatorMainWindow;

public class AssembleErrorAnnotation extends MyAnnotation {

	private IMarker marker;
	private String text;
	private int line;
	private Position position;
	public static final String TYPE = "assemble.type";
	private static final Image IMG = SWTResourceManager
			.getImage(SimulatorMainWindow.class,
					"/de/thetodd/simulator8085/gui/icons/compile_error.png");

	public AssembleErrorAnnotation(IMarker marker) {
		super("");
		this.marker = marker;
	}

	public AssembleErrorAnnotation(int line, String text) {
		super(TYPE);
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
		return IMG;
	}

	public int getLayer() {
		return 3;
	}

	public String getType() {
		return TYPE;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
