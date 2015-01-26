package de.thetodd.simulator8085.gui.sourceviewer.syntaxhighlight;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.WordPatternRule;

public class HexNumberRule implements IRule {

	private WordPatternRule wordRule;

	public HexNumberRule(IToken token) {
		this.wordRule = new WordPatternRule(new HexNumberDetector(),"0x",null, token);
	}
	
	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		return wordRule.evaluate(scanner);
	}

}
