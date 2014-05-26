package de.thetodd.simulator8085.gui.outviews.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class LEDWidget extends Canvas {
	
	public LEDWidget(Composite parent, int style) {
		super(parent, style);
		
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				LEDWidget.this.paintControl(e);
			}
		});
	}

	protected void paintControl(PaintEvent e) {
		GC gc = e.gc;
		gc.setAntialias(SWT.ON);
		gc.setBackground(this.getForeground());
		gc.fillOval(0, 0, getSize().x-1,getSize().y-1);
	}
	
	
	
}
