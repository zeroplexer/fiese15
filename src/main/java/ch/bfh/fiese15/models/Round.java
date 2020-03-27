package ch.bfh.fiese15.models;

public class Round {

	private int roundNumber;
	private Player player;
	private Aufgabenstreifen aufgabenstreifen;
	private boolean rollButtonEnabled;
	private boolean passButtonEnabled;
	private String gameMessage;
	public static final int TOTAL_ROUNDS = 10;

	public Round(int roundNumber) {
		this.roundNumber = roundNumber;
	}
	
	public Round(int roundNumber, Player player) {
		this.roundNumber = roundNumber;
		this.player = player;
	}
	
	public Round() {
		
	}
	
	public void incrementRoundNumber() {
		roundNumber++;
	}
	
	public int getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Aufgabenstreifen getAufgabenstreifen() {
		return aufgabenstreifen;
	}

	public void setAufgabenstreifen(Aufgabenstreifen aufgabenstreifen) {
		this.aufgabenstreifen = aufgabenstreifen;
	}

	public boolean isRollButtonEnabled() {
		return rollButtonEnabled;
	}

	public void setRollButtonEnabled(boolean rollButtonEnabled) {
		this.rollButtonEnabled = rollButtonEnabled;
	}

	public boolean isPassButtonEnabled() {
		return passButtonEnabled;
	}

	public void setPassButtonEnabled(boolean passButtonEnabled) {
		this.passButtonEnabled = passButtonEnabled;
	}

	public String getGameMessage() {
		return gameMessage;
	}

	public void setGameMessage(String gameMessage) {
		this.gameMessage = gameMessage;
	}

}
