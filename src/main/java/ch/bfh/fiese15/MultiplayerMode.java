package ch.bfh.fiese15;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import ch.bfh.fiese15.models.Computer;
import ch.bfh.fiese15.models.Player;
import ch.bfh.fiese15.models.Round;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MultiplayerMode extends GameMode {
	private ObservableList<Player> players;
	private int playerIndex = 0;
	private boolean computerIsPlaying = false;
	private ExecutorService service = Executors.newCachedThreadPool();
	private boolean stopWasRequested = false;
	private final long sleepTime = 2000;

	public MultiplayerMode(Round round) {
		super(round);
	}

	public MultiplayerMode(Round round, ObservableList<Player> players) {
		super(round);
		this.players = FXCollections.observableArrayList(players);
	}

	@Override
	public void init() {
		loadPlayer();
		initializeAufgabenstreifen();
		currentRound.setPassButtonEnabled(false);
		currentRound.setRollButtonEnabled(true);
		gameStateChanged();
	}

	public void loadPlayer() {
		setCurrentPlayer(players.get(playerIndex));
		getCurrentPlayer().resetDices();
		computerIsPlaying = (getCurrentPlayer() instanceof Computer);
		if (computerIsPlaying) {
			doComputerLogic();
		} else {
			currentRound.setGameMessage("Bitte würfeln");
		}
	}
	
	public void setCurrentPlayerWithIndex() {
		setCurrentPlayer(players.get(playerIndex));
	}
	
	/**
	 * Schliesst Spielzug ab
	 * Ruft die passende Methode auf um die Punkte zu berechnen
	 * Wenn es weitere Spieler gibt, ist der n�chste an der Reihe,
	 * Wenn es der letzte Spieler der List war, wird eine neue Runde gestartet
	 * Wenn es der letzte Spieler ist, in der letzten Runde wird das Spiel beendet
	 */
	public void onPassButtonPressed() {
		if (hasValidRolls) {
			getCurrentPlayer().calculateScoreValidRoll();
		} else {
			getCurrentPlayer().calculateScoreInvalidRoll(getCurrentAufgabenstreifen());
		}

		if (hasMorePlayers()) {
			playerIndex++;
			loadPlayer();
			currentRound.setRollButtonEnabled(true);
			currentRound.setPassButtonEnabled(false);
			gameStateChanged();
			return;
		}

		if (allowNextRound()) {
			nextRound();
		} else {
			gameEndedMultiplayer();
		}

		currentRound.setRollButtonEnabled(true);
		currentRound.setPassButtonEnabled(false);
		gameStateChanged();
	}

	private void doComputerLogic() {
		MultiplayerMode that = this;
		service.submit(new Runnable() {

			@Override
			public void run() {
				((Computer) getCurrentPlayer()).play(that);
			}
		});
	}

	private boolean hasMorePlayers() {
		return getCurrentPlayer() != players.get(players.size() - 1);
	}

	public List<Player> evaluateWinner() {
		Player winnerPlayer = players.stream().max(Comparator.comparing(Player::getScore))
				.orElseThrow(NoSuchElementException::new);

		return players.stream().filter(player -> player.getScore() == winnerPlayer.getScore())
				.collect(Collectors.toList());
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public ObservableList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ObservableList<Player> players) {
		this.players = players;
	}

	@Override
	public void nextRound() {
		currentRound.incrementRoundNumber();
		setNextAufgabenstreifen();
		playerIndex = 0;
		loadPlayer();
	}

	public void shutdown() {
		stopWasRequested = true;
		service.shutdown();
	}
	
	public long getSleepTime() {
		return sleepTime;
	}
	
	public boolean stopWasRequested() {
		return stopWasRequested;
	}

	public boolean isComputerIsPlaying() {
		return computerIsPlaying;
	}
}
