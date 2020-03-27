package ch.bfh.fiese15.util;

import ch.bfh.fiese15.gamestate.GameStateHandler;
import ch.bfh.fiese15.models.RankedScore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RankedScoreHelper {
	private static ObservableList<RankedScore> rankedScores = FXCollections.observableArrayList();

	
	public static void addRankedScores(RankedScore rankedScore) {
		rankedScores = GameStateHandler.getInstance().loadRankedScores();
		rankedScores.add(rankedScore);
		GameStateHandler.getInstance().saveRankedScores(rankedScores);
	}
	
	public static ObservableList<RankedScore> getRankedScores() {
		return GameStateHandler.getInstance().loadRankedScores();
	}
}
