package ch.bfh.fiese15.gamestate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.bfh.fiese15.GameMode;
import ch.bfh.fiese15.MultiplayerMode;
import ch.bfh.fiese15.models.Player;
import ch.bfh.fiese15.models.RankedScore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GameStateHandler {

	private static GameStateHandler instance;
	private File file;
	private ObjectMapper mapper = new ObjectMapper();
	private static final String RANKED_SCORE_FILE = "rankedScore.json";

	private GameStateHandler() {
	}

	/**
	 * Singleton weil der GameStateHandler an verschiedenen Orten benötigt wird.
	 * Wir wollen nicht die Referenz immer weitergeben.
	 * Auch der Zutand der aktuellen Datei sollte in der ganzen Applikation gebraucht werden können
	 */
	public static GameStateHandler getInstance() {
		if (instance == null) {
			instance = new GameStateHandler();
		}
		return instance;
	}

	/**
	 * Gibt ein {@code GameState} von {@code file} zurück
	 * 
	 */
	public GameState loadGame(File file) {
		this.file = file;
		GameState gameState = null;
		try {
			gameState = mapper.readValue(file, GameState.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gameState;
	}


	/**
	 * Speichert ein {@code GameState} in {@code file}
	 */
	public void saveGameUnder(File file, GameMode gameMode) {
		this.file = file;
		
		saveGame(gameMode);
	}

	/**
	 * Speichert ein {@code GameState} in das schon verwendete {@code file}
	 */
	public void saveGame(GameMode gameMode) {
		GameState gameState = new GameState();
		gameState.setRound(gameMode.getCurrentRound());
		gameState.setAufgabenstreifenList(gameMode.getAufgabenstreifenList());
		
		if (gameMode instanceof MultiplayerMode) {
			List<Player> players = new ArrayList<>(((MultiplayerMode) gameMode).getPlayers());
			
			gameState.setPlayers(players);
		}

		try {
			mapper.writeValue(file, gameState);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveRankedScores(ObservableList<RankedScore> rankedScore) {
		
		RankedScoreWrapper rankedScoreWrapper = new RankedScoreWrapper(rankedScore);
		
		try {
			mapper.writeValue(new File(RANKED_SCORE_FILE), rankedScoreWrapper);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gibt True zurück wenn GameState {@code File} wurde bereits geladen oder gespeichert
	 */
	public boolean fileHasBeenUsed() {
		return file != null;
	}

	public ObservableList<RankedScore> loadRankedScores() {
		
		RankedScoreWrapper rankedScoreWrapper = null;
		ObservableList<RankedScore> rankedScores = null;
		
		File rankedScoresFile = new File(RANKED_SCORE_FILE);
		
		if(rankedScoresFile.isFile()) {
			try {
				rankedScoreWrapper = mapper.readValue(rankedScoresFile, RankedScoreWrapper.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
			rankedScores = FXCollections.observableArrayList(rankedScoreWrapper.getRankedScores());
		} else {
			rankedScores = FXCollections.observableArrayList();
		}
		
		return rankedScores;
	}
}
