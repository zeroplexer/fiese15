package ch.bfh.fiese15.models;

public class RankedScore {
	private String name;
	private int score;
	private int place;

	public RankedScore() {
		
	}
	
	public RankedScore(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	@Override
	public String toString() {
		return place + ". " + name + ": " + score;
	}

}
