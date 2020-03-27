package ch.bfh.fiese15.controller;

import ch.bfh.fiese15.models.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PlayerListController {
	private ObservableList<Player> observablePlayerList = FXCollections.observableArrayList();
	public static int computerIndex = 1;

	@FXML
	ListView<Player> playerList;

	void init() {
		playerList.setItems(observablePlayerList);
	}

	@FXML
	protected void addHumanPlayerButton(ActionEvent event) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/addHumanPlayer.fxml"));
		Parent root = null;

		try {
			root = (Parent) loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		Stage secondStage = new Stage();

		AddHumanPlayerController addHumanPlayerController = loader.getController();
		addHumanPlayerController.init(observablePlayerList);

		secondStage.initModality(Modality.APPLICATION_MODAL);
		secondStage.setTitle("Spieler hinzufügen");
		secondStage.getIcons().add(new Image(getClass().getResourceAsStream("../img/playerIcon.png")));


		secondStage.setScene(scene);
		secondStage.show();
	}

	@FXML
	protected void addComputerPlayerButton(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/addComputerPlayer.fxml"));

			Parent root = (Parent) loader.load();

			Scene scene = new Scene(root);
			Stage secondStage = new Stage();

			AddComputerPlayerController addComputerPlayerController = loader.getController();
			addComputerPlayerController.init(observablePlayerList);

			secondStage.initModality(Modality.APPLICATION_MODAL);
			secondStage.setTitle("Computer hinzufügen");
			secondStage.getIcons().add(new Image(getClass().getResourceAsStream("../img/computerIcon.png")));

			secondStage.setScene(scene);
			secondStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void playButton(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/game.fxml"));

			Parent root = (Parent) loader.load();

			GameController gameController = loader.getController();

			// Wird für Simulation benötigt
/*
			observablePlayerList = FXCollections.observableArrayList();

			for (int i = 0; i < 30; i++) {
				Player player = new Computer("Computer " + i, Strategy.Random);
				observablePlayerList.add(player);
			}

			for (int i = 0; i < 30; i++) {
				Player player = new Computer("Computer " + i, Strategy.Greedy);
				observablePlayerList.add(player);
			}

			for (int i = 0; i < 30; i++) {
				Player player = new Computer("Computer " + i, Strategy.Protective);
				observablePlayerList.add(player);
			}*/
			
			gameController.init(observablePlayerList);

			Scene scene = new Scene(root);
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					gameController.shutdown();
				}
			});

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
