package ch.bfh.fiese15;

import ch.bfh.fiese15.models.HumanPlayer;
import ch.bfh.fiese15.models.RankedScore;
import ch.bfh.fiese15.models.Round;
import ch.bfh.fiese15.util.RankedScoreHelper;

public class SingleplayerMode extends GameMode {

	public SingleplayerMode(Round round) {
		super(round);
	}
	
	@Override
	public void init() {
		initializePlayer();
		initializeAufgabenstreifen();
		currentRound.setPassButtonEnabled(false);
		currentRound.setRollButtonEnabled(true);
		currentRound.setGameMessage("Bitte würfeln");
		gameStateChanged();
	}

	/**
	 * Schliesst Spielzug ab
	 * Ruft die passende Methode auf um die Punkte zu berechnen
	 * Wenn es weitere Runde gibt, wird die n�chste Runde gestartet
	 * Wenn es die letzte Runde war, wird das Spiel beendet
	 */
	public void onPassButtonPressed() {
		if (hasValidRolls) {
			getCurrentPlayer().calculateScoreValidRoll();
		} else {
			getCurrentPlayer().calculateScoreInvalidRoll(getCurrentAufgabenstreifen());
		}

		if (allowNextRound()) {
			nextRound();
		} else {
			RankedScoreHelper.addRankedScores(new RankedScore(getCurrentPlayer().getName(), getCurrentPlayer().getScore()));
			gameStateChanged();
			gameEndedSingleplayer();
			return;
		}

		currentRound.setRollButtonEnabled(true);
		currentRound.setPassButtonEnabled(false);
		currentRound.setGameMessage("Bitte würfeln");
		gameStateChanged();
	}
	
	private void initializePlayer() {
		currentRound.setPlayer(new HumanPlayer(""));
	}

	public String getHumanPlayer() {
		return getCurrentPlayer().getName();
	}

	@Override
	public void nextRound() {
		currentRound.incrementRoundNumber();
		setNextAufgabenstreifen();
		getCurrentPlayer().resetDices();
	}

}
