package ch.bfh.fiese15.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME, 
		  include = JsonTypeInfo.As.PROPERTY, 
		  property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = HumanPlayer.class, name = "HumanPlayer"),

    @JsonSubTypes.Type(value = Computer.class, name = "Computer") }
)
public abstract class Player {

	protected int score;
	protected String name;
	protected List<Dice> dices = new ArrayList<Dice>();

	public Player() {
		
	}
	
	public void initializeDices() {
		Dice dice1 = new Dice(0, Color.BLUE);
		Dice dice2 = new Dice(0, Color.YELLOW);
		Dice dice3 = new Dice(0, Color.BLACK);
		Dice dice4 = new Dice(0, Color.WHITE);
		Dice dice5 = new Dice(0, Color.GREEN);
		Dice dice6 = new Dice(0, Color.RED);
		dices.add(dice1);
		dices.add(dice2);
		dices.add(dice3);
		dices.add(dice4);
		dices.add(dice5);
		dices.add(dice6);
	}

	public void resetDices() {
		for (Dice dice : dices) {
			dice.setPoints(0);
			dice.setHerausgelegt(false);
			dice.setHerauslegbar(false);
		}
	}
	
	public void resetScore() {
		setScore(0);
	}

	public void calculateScoreValidRoll() {
		int sum = 0;
		int countHerausgelegt = 0;
		for (Dice dice : dices) {
			if (dice.isHerausgelegt()) {
				sum += dice.getPoints();
				countHerausgelegt++;
			}
		}
		if (countHerausgelegt == 6) {
			sum *= 2;
		} else if (countHerausgelegt == 5) {
			sum += 5;
		}

		addScore(sum);
	}

	public void calculateScoreInvalidRoll(Aufgabenstreifen aufgabenstreifen) {
		for (int i = 0; i < aufgabenstreifen.getDices().size(); i++) {
			if (!dices.get(i).isHerausgelegt()) {
				addScore(aufgabenstreifen.getDices().get(i).getPoints());
			}
		}
	}

	public void addScore(int points) {
		score += points;
	}

	public int getScore() {
		return score;
	}

	public void rollDices() {
		for (Dice dice : dices) {
			if (!dice.isHerausgelegt()) {
				dice.roll();
			}
		}
	}

	public void herauslegen(Dice dice) {
		dice.setHerausgelegt(true);
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Dice> getDices() {
		return dices;
	}
	
	public abstract String toStringScore();
}
