package ch.bfh.fiese15;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.fiese15.exception.DiceNotFoundException;
import ch.bfh.fiese15.gamestate.GameStateHandler;
import ch.bfh.fiese15.listener.GameListener;
import ch.bfh.fiese15.models.Aufgabenstreifen;
import ch.bfh.fiese15.models.Dice;
import ch.bfh.fiese15.models.Player;
import ch.bfh.fiese15.models.Round;
import ch.bfh.fiese15.util.AufgabenstreifenHelper;
import ch.bfh.fiese15.util.DiceHelper;

public abstract class GameMode {

	protected Round currentRound;
	protected List<Aufgabenstreifen> aufgabenstreifenList;
	protected boolean hasValidRolls;
	protected List<GameListener> listeners = new ArrayList<>();
	private GameStateHandler gameStateHandler = GameStateHandler.getInstance();

	public GameMode() {

	}

	public GameMode(Round round) {
		this.currentRound = round;
	}

	public void initializeAufgabenstreifen() {
		aufgabenstreifenList = AufgabenstreifenHelper.initializeAufgabenstreifen();
		currentRound.setAufgabenstreifen(aufgabenstreifenList.get(0));
	}

	public void addListener(GameListener listener) {
		listeners.add(listener);
	}

	public void rollDices() {
		getCurrentPlayer().rollDices();
	}

	public void gameStateChanged() {
		for (GameListener listener : listeners) {
			listener.onGameStateChanged();
		}
	}

	public void playerChanged() {
		for (GameListener listener : listeners) {
			listener.onPlayerChanged();
		}
	}
	
	public void gameEndedMultiplayer() {
		for(GameListener listener : listeners) {
			listener.onGameEndedMultiplayer();
		}
	}
	
	public void gameEndedSingleplayer() {
		for(GameListener listener : listeners) {
			listener.onGameEndedSingleplayer();
		}
	}

	public abstract void init();

	public abstract void nextRound();

	public boolean allowNextRound() {
		return currentRound.getRoundNumber() < Round.TOTAL_ROUNDS;
	}

	public abstract void onPassButtonPressed();

	public void onRollButtonPressed() {
		currentRound.setRollButtonEnabled(false);
		rollDices();
		checkValidDiceRolls();
		if (hasValidRolls()) {
			currentRound.setGameMessage("Bitte Würfel herauslegen");
			currentRound.setPassButtonEnabled(false);
		} else {
			currentRound.setGameMessage("Es gibt leider keine Würfel zum herauslegen");
			currentRound.setPassButtonEnabled(true);
		}
		gameStateChanged();
	}

	/**
	 * Prüft ob es herauslegbare Würfel gibt und setzt entsprechend das herauslegbar
	 * Attribut auf dem Würfel.
	 * 
	 * Setzt hasValidRolls auf true wenn es gültige Würfel gibt.
	 */
	public void checkValidDiceRolls() {
		hasValidRolls = false;

		List<Dice> notHerausgelegteDices = DiceHelper.getNotHerausgelegteDices(getCurrentPlayer().getDices());

		for (Dice dice : notHerausgelegteDices) {
			Dice aufgabenstreifenDice = null;
			try {
				aufgabenstreifenDice = DiceHelper.getDiceByColor(getCurrentAufgabenstreifen().getDices(),
						dice.getColor());
			} catch (DiceNotFoundException e) {
				e.printStackTrace();
			}

			if (diceIsHerauslegbar(dice, aufgabenstreifenDice)) {
				hasValidRolls = true;
				dice.setHerauslegbar(true);
			}
		}
	}

	private boolean diceIsHerauslegbar(Dice dice, Dice aufgabenstreifenDice) {
		return dice.getPoints() <= aufgabenstreifenDice.getPoints();
	}

	public void setNextAufgabenstreifen() {
		int indexOfNextAufgabenstreifen = aufgabenstreifenList.indexOf(getCurrentAufgabenstreifen()) + 1;
		currentRound.setAufgabenstreifen(aufgabenstreifenList.get(indexOfNextAufgabenstreifen));
	}

	public void legeDiceHeraus(Dice dice) {
		dice.setHerausgelegt(true);
		dice.setHerauslegbar(false);
		currentRound.setRollButtonEnabled(!allDicesHerausgelegt(getCurrentPlayer().getDices()));
		currentRound.setPassButtonEnabled(true);
		gameStateChanged();
	}

	private boolean allDicesHerausgelegt(List<Dice> dices) {
		for (Dice dice : dices) {
			if (!dice.isHerausgelegt()) {
				return false;
			}
		}

		return true;
	}

	public Player getCurrentPlayer() {
		return currentRound.getPlayer();
	}

	public void setCurrentPlayer(Player player) {
		this.currentRound.setPlayer(player);
	}

	public Aufgabenstreifen getCurrentAufgabenstreifen() {
		return currentRound.getAufgabenstreifen();
	}

	public int getCurrentRoundNumber() {
		return currentRound.getRoundNumber();
	}

	public boolean hasValidRolls() {
		return hasValidRolls;
	}

	public void setHasValidRolls(boolean hasValidRolls) {
		this.hasValidRolls = hasValidRolls;
	}

	public Round getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(Round currentRound) {
		this.currentRound = currentRound;
	}

	public void setPassButtonEnabled(boolean passButtonEnabled) {
		this.currentRound.setPassButtonEnabled(passButtonEnabled);
	}

	public boolean isPassButtonEnabled() {
		return this.currentRound.isPassButtonEnabled();
	}

	public void setRollButtonEnabled(boolean rollButtonEnabled) {
		this.currentRound.setRollButtonEnabled(rollButtonEnabled);
	}

	public boolean isRollButtonEnabled() {
		return this.currentRound.isRollButtonEnabled();
	}

	public void updatePlayerName(String name) {
		currentRound.getPlayer().setName(name);
		playerChanged();
	}

	public List<Aufgabenstreifen> getAufgabenstreifenList() {
		return aufgabenstreifenList;
	}

	public void setAufgabenstreifenList(List<Aufgabenstreifen> aufgabenstreifenList) {
		this.aufgabenstreifenList = aufgabenstreifenList;
	}

	public GameStateHandler getGameStatehandler() {
		return gameStateHandler;
	}
	
}
