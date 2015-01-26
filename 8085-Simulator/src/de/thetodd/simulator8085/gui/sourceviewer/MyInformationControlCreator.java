package de.thetodd.simulator8085.gui.sourceviewer;

import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.swt.widgets.Shell;

public class MyInformationControlCreator implements IInformationControlCreator {

	@Override
	public IInformationControl createInformationControl(Shell arg0) {
		DefaultInformationControl dic = new DefaultInformationControl(arg0);
		return dic;
	}

}
