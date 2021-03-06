package de.thetodd.simulator8085.api.helpers;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import de.thetodd.simulator8085.api.Simulator;

public class SyntaxHighlighter {

	private StyleRange mnemonicStyle;
	private StyleRange labelStyle;
	private StyleRange numberStyle;
	private StyleRange breakpointStyle;
	private StyleRange commentStyle;

	private Set<String> mnemonics;
	
	public SyntaxHighlighter() {
		Display display = Display.getDefault();

		mnemonicStyle = new StyleRange();
		mnemonicStyle.fontStyle = SWT.BOLD;
		mnemonicStyle.foreground = new Color(display, 0x2E, 0x2E, 0x8A);

		labelStyle = new StyleRange();
		labelStyle.fontStyle = SWT.NORMAL;
		labelStyle.foreground = new Color(display, 0x00, 0x99, 0x00);

		numberStyle = new StyleRange();
		numberStyle.fontStyle = SWT.NORMAL;
		numberStyle.foreground = new Color(display, 0xCC, 0x00, 0x00);

		breakpointStyle = new StyleRange();
		breakpointStyle.fontStyle = SWT.BOLD;
		breakpointStyle.foreground = new Color(display, 0xCC, 0x00, 0x00);
		breakpointStyle.background = new Color(display, 0xFF, 0xCC, 0xCC);

		commentStyle = new StyleRange();
		commentStyle.foreground = new Color(display, 0x1d, 0x37, 0x84);
		
		mnemonics = new HashSet<String>();
		mnemonics.addAll(Simulator.getInstance().getMnemonics().keySet());
		mnemonics.add("org"); //Add pseudo-opcode ORG
	}

	public void highlight(StyledText codeWidget) {
		String code = codeWidget.getText();
		codeWidget.replaceStyleRanges(0, code.length(), new StyleRange[0]); //Erase old styles
		

		// Highlight Mnemonics
		for (String m : mnemonics) {
			int index = 0;
			while ((index = code.toLowerCase().indexOf(m+" ", index)) > -1) {
				StyleRange styleRange = (StyleRange) mnemonicStyle.clone();
				styleRange.start = index;
				styleRange.length = m.length();
				codeWidget.setStyleRange(styleRange);
				index += m.length();
			}
		}

		// Highlight Hexnumbers
		Pattern pattern2 = Pattern.compile("0x[0-9a-fA-F]+");
		Matcher matcher2 = pattern2.matcher(code);
		// check all occurrences
		while (matcher2.find()) {
			StyleRange styleRange = (StyleRange) numberStyle.clone();
			styleRange.start = matcher2.start();
			styleRange.length = matcher2.end() - matcher2.start();
			codeWidget.setStyleRange(styleRange);
		}

		// Highlight Labels
		Pattern pattern = Pattern.compile("[a-z]+:");
		Matcher matcher = pattern.matcher(code);
		// check all occurrences
		while (matcher.find()) {
			StyleRange styleRange = (StyleRange) labelStyle.clone();
			styleRange.start = matcher.start();
			styleRange.length = matcher.end() - matcher.start();
			codeWidget.setStyleRange(styleRange);
		}

		// Highlight Breakpoints
		Pattern pattern3 = Pattern.compile("@:");
		Matcher matcher3 = pattern3.matcher(code);
		// check all occurrences
		while (matcher3.find()) {
			StyleRange styleRange = (StyleRange) breakpointStyle.clone();
			styleRange.start = matcher3.start();
			styleRange.length = matcher3.end() - matcher3.start();
			codeWidget.setStyleRange(styleRange);
		}
		
		//Highlight Comments
		Pattern pattern4 = Pattern.compile(";.*");
		Matcher matcher4 = pattern4.matcher(code);
		// check all occurrences
		while (matcher4.find()) {
			StyleRange styleRange = (StyleRange) commentStyle.clone();
			styleRange.start = matcher4.start();
			styleRange.length = matcher4.end() - matcher4.start();
			codeWidget.setStyleRange(styleRange);
		}
	}

}
