package ch.bfh.fiese15.gamestate;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.fiese15.models.RankedScore;

public class RankedScoreWrapper {

	private List<RankedScore> rankedScores = new ArrayList<>();
	
	public RankedScoreWrapper() {
		
	}
	
	public RankedScoreWrapper(List<RankedScore> rankedScores) {
		this.rankedScores = rankedScores;
	}

	public List<RankedScore> getRankedScores() {
		return rankedScores;
	}

	public void setRankedScores(List<RankedScore> rankedScores) {
		this.rankedScores = rankedScores;
	}
	
}
