package Minesweeper;

public class InputValidator {

	public static char isChar(String input) {
		char value;

		if (input.length() == 1) {
			value = (char) input.charAt(0);
		} else {
			value = '\u0000';
		}
		return value;
	}

}
