package de.thetodd.simulator8085.gui.sourceviewer;

import java.util.TreeSet;

import org.eclipse.jface.text.rules.IWordDetector;

public class HexNumberDetector implements IWordDetector {

	private final TreeSet<Character> hexChars;

	public HexNumberDetector() {
		hexChars = new TreeSet<Character>();

		hexChars.add('0');
		hexChars.add('1');
		hexChars.add('2');
		hexChars.add('3');
		hexChars.add('4');
		hexChars.add('5');
		hexChars.add('6');
		hexChars.add('7');
		hexChars.add('8');
		hexChars.add('9');
		hexChars.add('a');
		hexChars.add('b');
		hexChars.add('c');
		hexChars.add('d');
		hexChars.add('e');
		hexChars.add('f');
		hexChars.add('A');
		hexChars.add('B');
		hexChars.add('C');
		hexChars.add('D');
		hexChars.add('E');
		hexChars.add('F');
	}

	public boolean isWordPart(char c) {
		return hexChars.contains(c);
	}

	public boolean isWordStart(char c) {
		return hexChars.contains(c);
	}

}
