package ch.bfh.fiese15.models;

public class HumanPlayer extends Player {

	public HumanPlayer() {
		
	}
	
	public HumanPlayer(String name) {
		this.name = name;
		this.initializeDices();
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String toStringScore() {
		return name + ": " + score;
	}
	
}
