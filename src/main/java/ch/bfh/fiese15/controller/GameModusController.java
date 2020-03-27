package ch.bfh.fiese15.controller;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class GameModusController {

	@FXML
	protected void singleplayerModusButton(ActionEvent event) {
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Spielername eingeben");
		dialog.setHeaderText("Bitte geben Sie ihren Namen ein");
		dialog.setContentText("Name:");

		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/game.fxml"));
			Parent root = null;
			
			try {
				root = (Parent) loader.load();
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			GameController singleplayerController = loader.getController();
			singleplayerController.init(result.get());
	
			Scene scene = new Scene(root);
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	
			primaryStage.setScene(scene);
			primaryStage.show();
		}
	}

	@FXML
	protected void multiplayerModusButton(ActionEvent event) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/playerList.fxml"));
		Parent root = null;
		
		try {
			root = (Parent) loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PlayerListController playerListController = loader.getController();
		playerListController.init();

		Scene scene = new Scene(root);
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	protected void rankedButton(ActionEvent event) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/ranked.fxml"));
		Parent root = null;
		
		try {
			root = (Parent) loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		RankedController rankedController = loader.getController();
		rankedController.init();
		
		Scene scene = new Scene(root);
		Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
}
