package ch.bfh.fiese15.controller;

import java.util.Comparator;
import java.util.stream.Collectors;

import ch.bfh.fiese15.models.RankedScore;
import ch.bfh.fiese15.util.RankedScoreHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class RankedController {
	private ObservableList<RankedScore> rankedScores;
	
	@FXML
	ListView<RankedScore> rankedList;
	
	public void init() {
		rankedScores = RankedScoreHelper.getRankedScores();
		showRankedScore();
	}
	
	private void showRankedScore() {
		rankedList.setItems(addPlace(sortRankedScore()));
	}
	
	/**
	 * Sortiert die Rangliste von höchste Punktzahl abw�rts
	 * 
	 * @return sortierte Liste 
	 */
	private ObservableList<RankedScore> sortRankedScore() {
		ObservableList<RankedScore> sortedScores = FXCollections.observableArrayList(		
				rankedScores.stream()
				.sorted((Comparator.comparing(RankedScore::getScore)).reversed())
				.collect(Collectors.toList()));
		return sortedScores;
	}
	
	/**
	 * Fügt die Platzierung zum RankedScore hinzu, damit man sieht welcher Spieler, welche Platzierung hat.
	 * 
	 * @param sortedScores
	 * @return Die Übergebene Liste mit den Platzierungen
	 */
	private ObservableList<RankedScore> addPlace(ObservableList<RankedScore> sortedScores) {
		int place = 1;
		for(RankedScore rankedScore : sortedScores) {
			rankedScore.setPlace(place);
			place++;
		}
		return sortedScores;
	}
	
	@FXML
	protected void backToGameModusButton(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/gameModus.fxml"));

			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
