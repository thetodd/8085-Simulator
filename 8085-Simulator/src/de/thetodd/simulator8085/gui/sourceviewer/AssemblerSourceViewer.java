package de.thetodd.simulator8085.gui.sourceviewer;

import org.eclipse.jface.text.Document;
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

import de.thetodd.simulator8085.gui.sourceviewer.annotations.AssembleErrorAnnotation;
import de.thetodd.simulator8085.gui.sourceviewer.annotations.ErrorAnnotation;
import de.thetodd.simulator8085.gui.sourceviewer.annotations.WarningAnnotation;

public class AssemblerSourceViewer extends SourceViewer {

	private CompositeRuler fCompositeRuler;
	private Document document;
	private IAnnotationAccess fAnnotationAccess;
	private AnnotationModel fAnnotationModel;

	public AssemblerSourceViewer(Composite parent) {
		super(parent, new CompositeRuler(), null, false, SWT.MULTI
				| SWT.V_SCROLL);
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

		AnnotationPainter painter = new AnnotationPainter(this,
				fAnnotationAccess);
		painter.addAnnotationType(AssembleErrorAnnotation.TYPE);
		painter.setAnnotationTypeColor(AssembleErrorAnnotation.TYPE, new Color(
				Display.getDefault(), 200, 0, 0));
		this.getPaintManager().addPainter(painter);

		configure(new MySourceViewerConf());
		this.document = new Document();
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

	public void addCompileError(Position position, String message) {
		// add an annotation
		AssembleErrorAnnotation errorAnnotation = new AssembleErrorAnnotation(
				2, message);
		// lets underline the word "texst"
		fAnnotationModel.addAnnotation(errorAnnotation, position);
		this.getControl().update();
	}
	
	public void clearAnnotations() {
		this.fAnnotationModel.removeAllAnnotations();
	}
}
