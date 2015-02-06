package de.thetodd.simulator8085.gui.sourceviewer.syntaxhighlight;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.WordRule;

import de.thetodd.simulator8085.api.Simulator;

public class MnemonicRule implements IRule {

	private WordRule wordRule;

	public MnemonicRule(IToken token) {
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
		 * Add all usable Mnemonics from Simulator
		 */
		for(String mnemonicName : Simulator.getInstance().getUsableMnemonicNameList()) {
			this.wordRule.addWord(mnemonicName, token);
		}
		this.wordRule.addWord("org", token); //Add pseudo op org
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		return wordRule.evaluate(scanner);
	}

}
