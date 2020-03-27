package ch.bfh.fiese15.exception;

public class DiceNotFoundException extends Exception {

	private static final long serialVersionUID = -8205235230852538735L;

	public DiceNotFoundException(String color) {
		super("Dice with color " + color + " not found.");
	}
	
}
