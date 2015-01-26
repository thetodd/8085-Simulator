package de.thetodd.simulator8085.gui.sourceviewer.syntaxhighlight;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class MyASMCodeScanner extends RuleBasedScanner {

	private static final Color TAG_COLOR = new Color(Display.getDefault(), 0,
			0, 200);
	private static final Color NUMBER_COLOR = new Color(Display.getDefault(),
			255, 0, 0);
	private static final Color COMMENT_COLOR = new Color(Display.getDefault(),
			0, 150, 0);
	private static final Color BREAKPOINT_COLOR = new Color(
			Display.getDefault(), 200, 0, 0);
	private static final Color BREAKPOINT_COLOR_BG = new Color(
			Display.getDefault(), 255, 180, 180);

	public MyASMCodeScanner() {
		IToken tagToken = new Token(new TextAttribute(TAG_COLOR));
		IToken breakToken = new Token(new TextAttribute(BREAKPOINT_COLOR,
				BREAKPOINT_COLOR_BG, SWT.ITALIC));
		IToken numToken = new Token(new TextAttribute(NUMBER_COLOR));
		IToken commentToken = new Token(new TextAttribute(COMMENT_COLOR, null,
				SWT.ITALIC));

		IRule[] rules = new IRule[4];
		//Add rule for every Mnemonic
		rules[0] = new SingleLineRule("MVI", " ", tagToken);
		rules[1] = new EndOfLineRule(";", commentToken);
		rules[2] = new HexNumberRule(numToken);
		rules[3] = new EndOfLineRule("@:", breakToken);

		setRules(rules);
	}

}
