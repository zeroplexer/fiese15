package ch.bfh.fiese15.models;

import java.util.Random;


public class Dice {

	private int points;
	private String color;
	private boolean herausgelegt;
	private boolean herauslegbar;

	public Dice() {
		
	}
	
	public Dice(int points, String color) {
		this.points = points;
		this.color = color;
	}

	public void roll() {
		setHerauslegbar(false);
		Random random = new Random();
		points = random.nextInt(6) + 1;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isHerausgelegt() {
		return herausgelegt;
	}

	public void setHerausgelegt(boolean herausgelegt) {
		this.herausgelegt = herausgelegt;
	}

	public boolean isHerauslegbar() {
		return herauslegbar;
	}

	public void setHerauslegbar(boolean herauslegbar) {
		this.herauslegbar = herauslegbar;
	}

}
