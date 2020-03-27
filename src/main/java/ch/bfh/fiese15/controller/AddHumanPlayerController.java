package ch.bfh.fiese15.controller;

import ch.bfh.fiese15.models.HumanPlayer;
import ch.bfh.fiese15.models.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AddHumanPlayerController {
	private ObservableList<Player> playerList;
	
	@FXML
	private TextField player;
	
	@FXML
	private Button saveHumanPlayerButton;
	
	@FXML
	private Button cancelButton;
	
	public void init(ObservableList<Player> playersList) {
		this.playerList = FXCollections.observableList(playersList);
	}
	
	@FXML
	protected void saveHumanPlayerButton(ActionEvent event) {
		if(player.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Fehler: Keinen Namen gefunden");
	        alert.setHeaderText("Fehler");
	        alert.setContentText("Bitte einen Namen eingeben");
		 
		    alert.showAndWait();
		} else {
			playerList.add(new HumanPlayer(player.getText()));
			Stage stage = (Stage) saveHumanPlayerButton.getScene().getWindow();
		    stage.close();
		}	
	}
	
	
	@FXML
	protected void cancelButton(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
}