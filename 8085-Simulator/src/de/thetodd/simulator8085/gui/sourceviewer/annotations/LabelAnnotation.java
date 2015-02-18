package de.thetodd.simulator8085.gui.sourceviewer.annotations;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.Position;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

import de.thetodd.simulator8085.gui.SimulatorMainWindow;

public class LabelAnnotation extends MyAnnotation {

	private IMarker marker;
	private String text;
	private Position position;
	public static final String TYPE = "label.type";
	private static final Image IMG = SWTResourceManager
			.getImage(SimulatorMainWindow.class,
					"/de/thetodd/simulator8085/gui/icons/tag_green.png");

	public LabelAnnotation(IMarker marker) {
		super(0,"");
		this.marker = marker;
	}

	public LabelAnnotation(int line, String text) {
		super(line,TYPE);
		this.marker = null;
		this.text = text;
	}

	public IMarker getMarker() {
		return marker;
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
