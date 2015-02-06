package de.thetodd.simulator8085.gui.sourceviewer.syntaxhighlight;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.WordRule;

public class ParametersRule implements IRule {

	private WordRule wordRule;

	public ParametersRule(IToken token) {
		this.wordRule = new WordRule(new IWordDetector() {

			@Override
			public boolean isWordStart(char c) {
				return c >= 'a' && c <= 'z';
			}

			@Override
			public boolean isWordPart(char c) {
				return c >= 'a' && c <= 'z';
			}
		});
		
		/*
		 * Add all possible parameters
		 */
		this.wordRule.addWord("a", token);
		this.wordRule.addWord("b", token);
		this.wordRule.addWord("c", token);
		this.wordRule.addWord("d", token);
		this.wordRule.addWord("e", token);
		this.wordRule.addWord("h", token);
		this.wordRule.addWord("l", token);
		this.wordRule.addWord("sp", token);
		this.wordRule.addWord("psw", token);
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		return wordRule.evaluate(scanner);
	}

}
