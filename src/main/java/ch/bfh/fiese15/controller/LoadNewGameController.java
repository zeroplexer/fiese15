package ch.bfh.fiese15.controller;

import java.io.File;
import java.io.IOException;

import ch.bfh.fiese15.gamestate.GameState;
import ch.bfh.fiese15.gamestate.GameStateHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class LoadNewGameController {

	@FXML
	protected void loadGameButton(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Fiese15 Spielstand laden");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("json files", "*.json"));
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		File file = fileChooser.showOpenDialog(primaryStage);

		GameStateHandler gameStateHandler = GameStateHandler.getInstance();
		GameState gameState = gameStateHandler.loadGame(file);

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/game.fxml"));

		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		GameController gameController = loader.getController();
		gameController.init(gameState);

		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	protected void newGameButton(ActionEvent event) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/gameModus.fxml"));

		Parent root = null;

		try {
			root = (Parent) loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
