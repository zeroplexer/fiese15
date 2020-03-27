package ch.bfh.fiese15.controller;

import ch.bfh.fiese15.models.Computer;
import ch.bfh.fiese15.models.Player;
import ch.bfh.fiese15.models.Strategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;


public class AddComputerPlayerController {
	private ObservableList<Player> playerList;
	
	@FXML
	private ChoiceBox<Strategy> difficulty;
	
	@FXML
	private Button saveComputerPlayerButton;
	
	@FXML
	private Button cancelButton;
	
	public void init(ObservableList<Player> playersList) {
		this.playerList = FXCollections.observableList(playersList);
		addDifficulty();
	}
	
	private void addDifficulty() {
		difficulty.getItems().addAll(Strategy.values());
	}
	
	@FXML
	protected void saveComputerPlayerButton(ActionEvent event) {
		if(difficulty.getValue() == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Fehler: Kein Schwierigkeitstufe gefunden");
	        alert.setHeaderText("HEADER");
	        alert.setContentText("Bitte Schwierigkeitstufe des Computer wählen");
		 
		    alert.showAndWait();
		} else {
			String name = "Computer";
			playerList.add(new Computer(name, difficulty.getValue()));
			Stage stage = (Stage) saveComputerPlayerButton.getScene().getWindow();
		    stage.close();
		}	
	}
	
	
	@FXML
	protected void cancelButton(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
}