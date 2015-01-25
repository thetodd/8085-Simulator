package de.thetodd.simulator8085.gui.sourceviewer;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class MyJavaCodeScanner extends RuleBasedScanner {

	private static final Color TAG_COLOR = new Color(Display.getDefault(),200,0,0);
	private static final Color NUMBER_COLOR = new Color(Display.getDefault(),0,0,200);
	private static final Color COMMENT_COLOR = new Color(Display.getDefault(),0,200,0);

	public MyJavaCodeScanner() {
		IToken tagToken = new Token(new TextAttribute(TAG_COLOR));
		IToken numToken = new Token(new TextAttribute(NUMBER_COLOR));
		IToken commentToken = new Token(new TextAttribute(COMMENT_COLOR));
		
		IRule[] rules = new IRule[3];
		rules[0] = new SingleLineRule("MVI"," ",tagToken);
		rules[1] = new EndOfLineRule(";", commentToken);
		rules[2] = new HexNumberRule(numToken);
		
		setRules(rules);
	}

}
