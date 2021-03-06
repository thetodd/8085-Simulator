package de.thetodd.simulator8085.gui.sourceviewer;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.AnnotationPainter;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.listener.GlobalSimulatorEvents;
import de.thetodd.simulator8085.api.listener.SimulatorEvent;
import de.thetodd.simulator8085.gui.sourceviewer.annotations.AssembleErrorAnnotation;
import de.thetodd.simulator8085.gui.sourceviewer.annotations.BreakpointAnnotation;
import de.thetodd.simulator8085.gui.sourceviewer.annotations.ErrorAnnotation;
import de.thetodd.simulator8085.gui.sourceviewer.annotations.LabelAnnotation;
import de.thetodd.simulator8085.gui.sourceviewer.annotations.WarningAnnotation;

public class AssemblerSourceViewer extends SourceViewer {

	private CompositeRuler fCompositeRuler;
	private Document document;
	private IAnnotationAccess fAnnotationAccess;
	private AnnotationModel fAnnotationModel;
	protected boolean dirty;

	public AssemblerSourceViewer(Composite parent) {
		super(parent, new CompositeRuler(), null, false, SWT.MULTI
				| SWT.V_SCROLL);
		dirty = false;
		fAnnotationAccess = new AnnotationMarkerAccess();
		fCompositeRuler = (CompositeRuler) this.getVerticalRuler();
		fAnnotationModel = new AnnotationModel();

		LineNumberRulerColumn lnrc = new LineNumberRulerColumn();
		lnrc.setForeground(new Color(Display.getDefault(), 0x66, 0x66, 0x66));
		lnrc.setFont(new Font(Display.getDefault(), "Courier New", 10,
				SWT.NORMAL));
		fCompositeRuler.addDecorator(1, lnrc);
		AnnotationRulerColumn annotationRuler = new AnnotationRulerColumn(
				fAnnotationModel, 16, fAnnotationAccess);
		fCompositeRuler.setModel(fAnnotationModel);

		// annotation ruler is decorating our composite ruler
		fCompositeRuler.addDecorator(0, annotationRuler);

		// add what types are show on the different rulers
		annotationRuler.addAnnotationType(ErrorAnnotation.TYPE);
		annotationRuler.addAnnotationType(AssembleErrorAnnotation.TYPE);
		annotationRuler.addAnnotationType(WarningAnnotation.TYPE);
		annotationRuler.addAnnotationType(BreakpointAnnotation.TYPE);
		annotationRuler.addAnnotationType(LabelAnnotation.TYPE);

		AnnotationPainter painter = new AnnotationPainter(this,
				fAnnotationAccess);
		painter.addAnnotationType(AssembleErrorAnnotation.TYPE);
		painter.setAnnotationTypeColor(AssembleErrorAnnotation.TYPE, new Color(
				Display.getDefault(), 0, 0, 200));
		painter.addAnnotationType(ErrorAnnotation.TYPE);
		painter.setAnnotationTypeColor(ErrorAnnotation.TYPE,
				new Color(Display.getDefault(), 255, 0, 0));
		painter.addAnnotationType(WarningAnnotation.TYPE);
		painter.setAnnotationTypeColor(WarningAnnotation.TYPE, new Color(
				Display.getDefault(), 200, 200, 0));
		this.getPaintManager().addPainter(painter);

		configure(new MySourceViewerConf());
		this.document = new Document();
		this.document.addDocumentListener(new IDocumentListener() {

			@Override
			public void documentChanged(DocumentEvent arg0) {
				// set dirty
				dirty = true;
				SimulatorEvent evt = new SimulatorEvent(
						GlobalSimulatorEvents.DOCUMENT_CHANGE, "",
						SimulatorEvent.TYPE.INFORMATION);
				Simulator.getInstance().fireSimulatorEvent(evt);
			}

			@Override
			public void documentAboutToBeChanged(DocumentEvent arg0) {

			}
		});
		this.setDocument(this.document, fAnnotationModel);
		this.getTextWidget().setFont(
				new Font(Display.getDefault(), "Courier New", 10, SWT.NORMAL));
	}

	public void setText(String text) {
		this.document.set(text);
	}

	public String getText() {
		return this.document.get();
	}

	public void addCompileError(int line, Position position, String message) {
		// add an annotation
		AssembleErrorAnnotation errorAnnotation = new AssembleErrorAnnotation(
				line, message);
		fAnnotationModel.addAnnotation(errorAnnotation, position);
		this.getControl().update();
	}

	public void addLabelAnnotation(int line, short address, Position pos,
			String name) {
		LabelAnnotation labelAnnotation = new LabelAnnotation(line, "Label \""
				+ name + "\" at " + String.format("0x%04X", address));
		fAnnotationModel.addAnnotation(labelAnnotation, pos);
		this.getControl().update();
	}

	public void clearAnnotations() {
		this.fAnnotationModel.removeAllAnnotations();
	}

	public void addBreakpoint(int linenumber, short adresse, Position pos) {
		// add an annotation
		BreakpointAnnotation errorAnnotation = new BreakpointAnnotation(
				linenumber, "Breakpoint at " + String.format("0x%04X", adresse));
		fAnnotationModel.addAnnotation(errorAnnotation, pos);
		this.getControl().update();
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public boolean isDirty() {
		return dirty;
	}
}
