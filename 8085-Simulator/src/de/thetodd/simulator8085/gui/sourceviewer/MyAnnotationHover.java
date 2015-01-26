package de.thetodd.simulator8085.gui.sourceviewer;

import java.util.Iterator;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;

import de.thetodd.simulator8085.gui.sourceviewer.annotations.MyAnnotation;

public class MyAnnotationHover implements  IAnnotationHover, ITextHover {

	@Override
	public String getHoverInfo(ISourceViewer sv, int lineNum) {
		Iterator<?> annoIter = sv.getAnnotationModel().getAnnotationIterator();
		
		StringBuffer hoverStr = new StringBuffer();
		while(annoIter.hasNext()) {
			MyAnnotation anno = (MyAnnotation) annoIter.next();
			if(anno.getLine() == lineNum) {
				hoverStr.append(anno.getText());
				if(annoIter.hasNext()) { //append line break
					hoverStr.append("\n");
				}
			}
		}
		
		return hoverStr.toString();
	}

	@Override
	public String getHoverInfo(ITextViewer arg0, IRegion arg1) {
		return null;
	}

	@Override
	public IRegion getHoverRegion(ITextViewer arg0, int arg1) {
		return null;
	}

}
