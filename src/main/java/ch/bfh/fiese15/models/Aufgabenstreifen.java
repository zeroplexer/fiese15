package ch.bfh.fiese15.models;

import java.util.List;

public class Aufgabenstreifen {

	private List<Dice> dices;
	
	public Aufgabenstreifen() {
		
	}
	
	public Aufgabenstreifen(List<Dice> dices) {
		this.dices = dices;
	}

	public List<Dice> getDices() {
		return dices;
	}

	public void setDices(List<Dice> dices) {
		this.dices = dices;
	}
	
}
