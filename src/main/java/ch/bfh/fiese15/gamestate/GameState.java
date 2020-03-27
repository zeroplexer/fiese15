package ch.bfh.fiese15.gamestate;

import java.util.List;

import ch.bfh.fiese15.models.Aufgabenstreifen;
import ch.bfh.fiese15.models.Player;
import ch.bfh.fiese15.models.Round;

public class GameState {
	
	private Round round;
	private List<Aufgabenstreifen> aufgabenstreifenList;
	private List<Player> players;
	private int playerIndex;
	
	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public List<Aufgabenstreifen> getAufgabenstreifenList() {
		return aufgabenstreifenList;
	}

	public void setAufgabenstreifenList(List<Aufgabenstreifen> aufgabenstreifenList) {
		this.aufgabenstreifenList = aufgabenstreifenList;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	
}
