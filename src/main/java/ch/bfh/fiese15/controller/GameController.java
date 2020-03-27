package ch.bfh.fiese15.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.sun.glass.ui.MenuItem;

import ch.bfh.fiese15.GameMode;
import ch.bfh.fiese15.MultiplayerMode;
import ch.bfh.fiese15.SingleplayerMode;
import ch.bfh.fiese15.gamestate.GameState;
import ch.bfh.fiese15.listener.GameListener;
import ch.bfh.fiese15.models.Dice;
import ch.bfh.fiese15.models.HumanPlayer;
import ch.bfh.fiese15.models.Player;
import ch.bfh.fiese15.models.Round;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameController {
	private GameMode gameMode;

	@FXML
	private MenuBar menuBar;

	/*
	 * @FXML private MenuItem saveItem, saveUnderItem, loadItem;
	 */

	@FXML
	private Button passButton;

	@FXML
	private Button rollButton;

	@FXML
	private GridPane gridPaneAufgabenstreifen, gridPaneDices;

	@FXML
	private Label currentRound, currentScore, currentPlayer, gameMessage;

	@FXML
	private ListView<String> scoreList;

	/**
	 * Initialisiert ein neues Spiel, wird benötigt für ein Neues Spiel bei
	 * Singleplayer
	 */
	public void init(String name) {
		Round round = new Round(1);
		gameMode = new SingleplayerMode(round);
		gameMode.addListener(new GameListenerImpl());
		gameMode.init();
		gameMode.updatePlayerName(name);
	}

	/**
	 * Initialisiert ein neues Spiel mit einem vorhandenen GameState von einem
	 * vorherigem Spiel.
	 *
	 * @param gameState
	 */
	public void init(GameState gameState) {
		gameMode = new SingleplayerMode(gameState.getRound());
		gameMode.setAufgabenstreifenList(gameState.getAufgabenstreifenList());
		gameMode.addListener(new GameListenerImpl());
		updateView();
	}

	/**
	 * Initialisiert ein neues Spiel mit einer Liste von Spieler f�r Multiplayer
	 *
	 * @param players
	 */
	public void init(ObservableList<Player> players) {
		Round round = new Round(1);
		gameMode = new MultiplayerMode(round, players);
		gameMode.addListener(new GameListenerImpl());
		gameMode.init();
		scoreList.setOpacity(1);
		updateView();
	}

	private class GameListenerImpl implements GameListener {

		@Override
		public void onGameStateChanged() {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					updateView();
				}
			});
		}

		@Override
		public void onPlayerChanged() {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					updateCurrentPlayerName();
				}
			});
		}

		@Override
		public void onGameEndedMultiplayer() {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					showWinAlertBoxMultiplayer();
				}

			});
		}

		@Override
		public void onGameEndedSingleplayer() {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					showWinAlertBoxSingleplayer();
				}

			});
		}
	}

	/**
	 * Aktualisiert das ganze GUI mit den Daten vom GameMode
	 */
	public void updateView() {
		updatePassButton();
		updateRollButton();
		updatePlayerDices();
		updateAufgabenstreifenDices();
		updateGameMessage();
		updateCurrentRound();
		updateCurrentScore();
		updateCurrentPlayerName();
		updateScoreList();
		updateMenuItems();
	}

	/**
	 * Erzeugt eine AlertBox, die den Gewinner evaluiert und ausgiebt �ber die
	 * Buttons kann nochmals gespielt werden oder zur Rangliste springen
	 */
	public void showWinAlertBoxSingleplayer() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Spiel beendet");
		alert.setHeaderText("Glückwunsch");
		alert.setContentText("Sie haben " + gameMode.getCurrentPlayer().getScore() + " Punkte erreicht.");

		ButtonType buttonRankedList = new ButtonType("Rangliste");
		ButtonType buttonRestart = new ButtonType("Nochmals Spielen");
		alert.getButtonTypes().setAll(buttonRankedList, buttonRestart);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonRankedList) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/ranked.fxml"));

				Parent root = (Parent) loader.load();
				Scene scene = new Scene(root);
				Stage primaryStage = (Stage) menuBar.getScene().getWindow();

				RankedController rankedController = loader.getController();
				rankedController.init();

				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (result.get() == buttonRestart) {
			gameMode.getCurrentPlayer().resetScore();
			init(gameMode.getCurrentPlayer().getName());
		}
	}

	/**
	 * Erzeugt eine AlertBox, die den Gewinner oder die Gewinners evaluiert und
	 * ausgiebt �ber die Buttons kann nochmals gespielt werden oder zum Spielmenu
	 * springen
	 */
	private void showWinAlertBoxMultiplayer() {
		List<Player> winners = ((MultiplayerMode) gameMode).evaluateWinner();
		String winnerText = null;
		if (winners.size() == 1) {
			winnerText = winners.get(0).getName() + " hat gewonnen mit " + winners.get(0).getScore() + " Punkten.";
		} else {
			String winnerNames = "";
			for (Player player : winners) {
				winnerNames += player.getName() + ", ";
			}
			winnerText = winnerNames + "haben gewonnen mit " + winners.get(0).getScore() + " Punkten.";
		}

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Spiel beendet");
		alert.setHeaderText("Glückwunsch");
		alert.setContentText(winnerText);

		ButtonType buttonBackToMenu = new ButtonType("Zurück zum Menü");
		ButtonType buttonRestart = new ButtonType("Nochmals Spielen");
		alert.getButtonTypes().setAll(buttonBackToMenu, buttonRestart);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonRestart) {
			((MultiplayerMode) gameMode).getPlayers().stream().forEach(player -> player.resetScore());
			init(((MultiplayerMode) gameMode).getPlayers());
		}
		if (result.get() == buttonBackToMenu) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/loadNewGame.fxml"));
			Parent root = null;

			try {
				root = (Parent) loader.load();
			} catch (Exception e) {
				e.printStackTrace();
			}

			Scene scene = new Scene(root);
			Stage primaryStage = (Stage) menuBar.getScene().getWindow();

			primaryStage.setScene(scene);
			primaryStage.show();
		}
	}

	private void updatePassButton() {
		passButton.setDisable(!gameMode.getCurrentRound().isPassButtonEnabled());
	}

	private void updateRollButton() {
		rollButton.setDisable(!gameMode.getCurrentRound().isRollButtonEnabled());
	}

	@FXML
	protected void rollButton(ActionEvent event) {
		gameMode.onRollButtonPressed();
	}

	@FXML
	protected void passButton(ActionEvent event) {
		gameMode.onPassButtonPressed();
	}

	@FXML
	protected void saveGame() {
		if (gameMode instanceof MultiplayerMode) {
			if (((MultiplayerMode) gameMode).isComputerIsPlaying()) {
				showAlertActionNotAllowed("Speichern nicht erlaubt, wenn der Computer am spielen ist!");
				return;
			}
		}

		if (gameMode.getGameStatehandler().fileHasBeenUsed()) {
			gameMode.getGameStatehandler().saveGame(gameMode);
		} else {
			saveGameUnder();
		}
	}
	
	@FXML
	protected void saveGameUnder() {
		if (gameMode instanceof MultiplayerMode) {
			if (((MultiplayerMode) gameMode).isComputerIsPlaying()) {
				showAlertActionNotAllowed("Speichern nicht erlaubt, wenn der Computer am spielen ist!");
				return;
			}
		}
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("json files", "*.json"));
		Stage primaryStage = (Stage) menuBar.getScene().getWindow();
		File selectedFile = fileChooser.showSaveDialog(primaryStage);

		gameMode.getGameStatehandler().saveGameUnder(selectedFile, gameMode);
	}

	@FXML
	protected void loadGame() {
		if (gameMode instanceof MultiplayerMode) {
			if (((MultiplayerMode) gameMode).isComputerIsPlaying()) {
				showAlertActionNotAllowed("Laden nicht erlaubt, wenn der Computer am spielen ist!");
				return;
			}
		}
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Fiese15 Spielstand laden");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("json files", "*.json"));
		Stage primaryStage = (Stage) menuBar.getScene().getWindow();
		File file = fileChooser.showOpenDialog(primaryStage);

		GameState gameState = gameMode.getGameStatehandler().loadGame(file);

		if (gameState.getPlayers() == null) {
			// loading singleplayerGame
			gameMode = new SingleplayerMode(gameState.getRound());
			scoreList.setOpacity(0);
		} else {
			// loading multiplayerGame
			gameMode = new MultiplayerMode(gameState.getRound());
			((MultiplayerMode) gameMode).setPlayers(FXCollections.observableArrayList(gameState.getPlayers()));
			((MultiplayerMode) gameMode).setPlayerIndex(gameState.getPlayerIndex());
			((MultiplayerMode) gameMode).setCurrentPlayerWithIndex();
			scoreList.setOpacity(1);
		}

		gameMode.addListener(new GameListenerImpl());
		gameMode.setAufgabenstreifenList(gameState.getAufgabenstreifenList());
		updateView();
	}

	private void showAlertActionNotAllowed(String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Warnung");
		alert.setHeaderText("Aktion nicht erlaubt");
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	@FXML
	protected void instruction() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/instruction.fxml"));
		Parent root = null;

		try {
			root = (Parent) loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		Stage secondStage = new Stage();

		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setTitle("Spiel Anleitung");
		secondStage.getIcons().add(new Image(getClass().getResourceAsStream("../img/instructionIcon.png")));

		secondStage.setScene(scene);
		secondStage.show();
	}

	/**
	 * Die gew�rfelte W�rfel werden im GUI angezeigt Wenn der W�rfel herauslegbar
	 * ist, erh�lt er einen gr�nen Rand Herauslegbare W�rfel k�nnen per Click
	 * herausgelegt werden Herausgelegte W�rfel werden transparent
	 */
	private void updatePlayerDices() {
		clearDices(gridPaneDices);
		for (int i = 0; i < gameMode.getCurrentPlayer().getDices().size(); i++) {
			Dice dice = gameMode.getCurrentPlayer().getDices().get(i);

			if (dice.getPoints() == 0) {
				return;
			}

			String diceFXML = Integer.toString(dice.getPoints());
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/Dice" + diceFXML + ".fxml"));

			try {
				Parent parent = (Parent) loader.load();
				parent.setStyle("");
				addStyle(parent, "-fx-background-color: " + dice.getColor() + ";");
				gridPaneDices.add(parent, i, 0);

				if (dice.isHerausgelegt()) {
					disableDice(parent);
					continue;
				}

				if (dice.isHerauslegbar()) {
					highlightDice(parent);
					if (gameMode.getCurrentPlayer() instanceof HumanPlayer) {
						parent.setOnMouseClicked(e -> gameMode.legeDiceHeraus(dice));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Der aktuelle Aufgabenstreifen wird aufs GUI projiziert
	 */
	private void updateAufgabenstreifenDices() {
		clearDices(gridPaneAufgabenstreifen);
		for (int i = 0; i < gameMode.getCurrentAufgabenstreifen().getDices().size(); i++) {
			Dice dice = gameMode.getCurrentAufgabenstreifen().getDices().get(i);
			String diceFXML = Integer.toString(dice.getPoints());
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/Dice" + diceFXML + ".fxml"));

			try {
				Parent parent = (Parent) loader.load();
				parent.setStyle("");
				addStyle(parent, "-fx-background-color: " + dice.getColor() + ";");
				gridPaneAufgabenstreifen.add(parent, i, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Fï¿½gt dem {@code parent} einen style hinzu.
	 *
	 * Der {@code style} muss in der Form "-fx-xxx: style;" angegeben werden.
	 *
	 * @param parent
	 * @param style
	 */
	private void addStyle(Parent parent, String style) {
		String currentStyle = parent.getStyle();
		parent.setStyle(currentStyle + " " + style);
	}

	private void highlightDice(Parent dice) {
		addStyle(dice, "-fx-border-color: #6ef442;");
		addStyle(dice, "-fx-border-width: 5;");
	}

	private void disableDice(Parent dice) {
		addStyle(dice, "-fx-border-width: 0;");
		addStyle(dice, "-fx-opacity: 0.6;");
	}

	private void clearDices(GridPane gridPane) {
		gridPane.getChildren().clear();
	}

	private void updateCurrentRound() {
		currentRound.setText(Integer.toString(gameMode.getCurrentRoundNumber()));
	}

	private void updateCurrentScore() {
		currentScore.setText(Integer.toString(gameMode.getCurrentPlayer().getScore()));
	}

	private void updateCurrentPlayerName() {
		currentPlayer.setText(gameMode.getCurrentPlayer().getName());
	}

	private void updateGameMessage() {
		gameMessage.setText(gameMode.getCurrentRound().getGameMessage());
	}

	private void updateScoreList() {
		if (gameMode instanceof MultiplayerMode) {
			scoreList.refresh();
			ObservableList<String> playerList = FXCollections.observableArrayList();
			for (Player player : ((MultiplayerMode) gameMode).getPlayers()) {
				playerList.add(player.toStringScore());
			}
			scoreList.setItems(playerList);
		}
	}

	private void updateMenuItems() {
		/*
		 * saveItem.setEnabled(true); saveUnderItem.setEnabled(true);
		 * loadItem.setEnabled(true);
		 * 
		 * if (gameMode instanceof MultiplayerMode) { if (((MultiplayerMode)
		 * gameMode).isComputerIsPlaying()) { saveItem.setEnabled(false);
		 * saveUnderItem.setEnabled(false); loadItem.setEnabled(false); } }
		 */
	}

	public void shutdown() {
		if (gameMode instanceof MultiplayerMode) {
			((MultiplayerMode) gameMode).shutdown();
		}
	}

}
