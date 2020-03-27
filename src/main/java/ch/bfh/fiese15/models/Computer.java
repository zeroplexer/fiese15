package ch.bfh.fiese15.models;

import java.util.List;
import java.util.Random;

import ch.bfh.fiese15.MultiplayerMode;
import ch.bfh.fiese15.util.DiceHelper;

public class Computer extends Player {

	private Strategy strategy;

	public Computer() {

	}

	public Computer(String name, Strategy strategy) {
		this.name = name;
		this.strategy = strategy;
		this.initializeDices();
	}

	public void play(MultiplayerMode mode) {
		switch (strategy) {
		case Random:
			playRandom(mode);
			break;
		case Greedy:
			playGreedy(mode);
			break;
		case Protective:
			playProtective(mode);
			break;
		}
	}

	/**
	 * Computer spielt im Random Modus.
	 * 
	 * Bei jedem herauslegbaren Würfel, besteht eine 50%ige Chance, dass er diesen
	 * herauslegt. Es wird aber immer mindestens ein Würfel herausgelegt (Ausser es
	 * gibt keine herauslegbaren Würfel. Dann Hört er sofort auf).
	 * 
	 * Nachdem er die Würfel herausgelegt hat, besteht wieder eine 50%ige Chance,
	 * dass er aufhört oder weiterwürfelt. Wenn er weiterwürfelt, fängt die
	 * Iteration wieder von vorne an.
	 * 
	 * 
	 * @param mode
	 */
	private void playRandom(MultiplayerMode mode) {
		while (!mode.stopWasRequested()) {
			mode.getCurrentRound().setGameMessage("Computer ist am spielen");
			if (!DiceHelper.areAllDicesHerausgelegt(dices)) {
				mode.onRollButtonPressed();
				mode.getCurrentRound().setGameMessage("Computer ist am spielen");
				mode.getCurrentRound().setPassButtonEnabled(false);
				mode.gameStateChanged();

				try {
					Thread.sleep(mode.getSleepTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (!mode.hasValidRolls()) {
					break;
				}

				randomHerauslegen();
				mode.gameStateChanged();

				try {
					Thread.sleep(mode.getSleepTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (DiceHelper.areAllDicesHerausgelegt(dices)) {
					break;
				}

				if (randomBoolean()) {
					break;
				}

			} else {
				break;
			}
		}

		mode.onPassButtonPressed();
		mode.gameStateChanged();
	}

	/**
	 * Computer spielt im Greedy Modus.
	 * 
	 * Er legt jeden herauslegbaren Würfel hinaus und würfelt solange weiter, bis er
	 * entweder alle herausgelegt hat oder es keine gültigen Würfel gibt.
	 * 
	 * @param mode
	 */
	private void playGreedy(MultiplayerMode mode) {

		while (!mode.stopWasRequested()) {
			mode.getCurrentRound().setGameMessage("Computer ist am spielen");
			if (!DiceHelper.areAllDicesHerausgelegt(dices)) {
				mode.onRollButtonPressed();
				mode.getCurrentRound().setGameMessage("Computer ist am spielen");
				mode.getCurrentRound().setPassButtonEnabled(false);
				mode.gameStateChanged();
				
				try {
					Thread.sleep(mode.getSleepTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (!mode.hasValidRolls()) {
					break;
				}

				alleDicesHerauslegen();
				mode.gameStateChanged();

				try {
					Thread.sleep(mode.getSleepTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (DiceHelper.areAllDicesHerausgelegt(dices)) {
					break;
				}
			} else {
				break;
			}
		}

		mode.onPassButtonPressed();
		mode.gameStateChanged();
	}

	/**
	 * Computer spielt im Protective Modus.
	 * 
	 * Er legt nur Würfel hinaus, die gleich viel Punkte oder höchstens ein Punkt
	 * weniger, wie die auf dem Aufgabenstreifen haben. Wenn diese Bedingung nicht
	 * erfüllt werden kann, legt er den bestmöglichen Würfel heraus.
	 * 
	 * Wenn die verbleibenden Würfel auf dem Aufgabenstreifen alle 3 Punkte oder
	 * weniger haben, hört er auf zu spielen.
	 * 
	 * @param mode
	 */
	private void playProtective(MultiplayerMode mode) {
		while (!mode.stopWasRequested()) {
			mode.getCurrentRound().setGameMessage("Computer ist am spielen");
			if (!DiceHelper.areAllDicesHerausgelegt(dices)) {
				mode.onRollButtonPressed();
				mode.getCurrentRound().setGameMessage("Computer ist am spielen");
				mode.getCurrentRound().setPassButtonEnabled(false);
				mode.gameStateChanged();
				
				try {
					Thread.sleep(mode.getSleepTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (!mode.hasValidRolls()) {
					break;
				}

				smartHerauslegen(mode.getCurrentAufgabenstreifen().getDices());
				mode.gameStateChanged();

				try {
					Thread.sleep(mode.getSleepTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (DiceHelper.areAllDicesHerausgelegt(dices)) {
					break;
				}

				if (dicesHave3PointsOrLess(mode.getCurrentAufgabenstreifen().getDices())) {
					break;
				}

			} else {
				break;
			}
		}

		mode.onPassButtonPressed();
		mode.gameStateChanged();
	}

	private void alleDicesHerauslegen() {
		dices.stream().filter(dice -> dice.isHerauslegbar()).forEach(dice -> {
			dice.setHerausgelegt(true);
			dice.setHerauslegbar(false);
		});
	}

	/**
	 * Überprüft ob, es auf dem Aufgabenstreifen mindestens einen nicht
	 * herausgelegten Würfel gibt, der mehr als 3 Punkte hat.
	 * 
	 * @param aufgabenstreifenDices
	 * @return true wenn alle nicht herausgelegten Würfel auf dem Aufgabenstreifen 3
	 *         Punkte oder weniger haben.
	 */
	private boolean dicesHave3PointsOrLess(List<Dice> aufgabenstreifenDices) {
		for (int i = 0; i < dices.size(); i++) {
			if (!dices.get(i).isHerausgelegt()) {
				if (aufgabenstreifenDices.get(i).getPoints() > 3) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Die Methode prüft, ob Würfel herausgelegt werden können. Wenn ein Würfel
	 * herausgelegt werden kann, wird zufällig entschieden, ob der Würfel
	 * herausgelegt wird.
	 * 
	 */
	private void randomHerauslegen() {
		int countHerausgelegt = 0;
		while (countHerausgelegt == 0) {
			for (Dice dice : dices) {
				if (dice.isHerauslegbar()) {
					if (randomBoolean()) {
						dice.setHerausgelegt(true);
						dice.setHerauslegbar(false);
						countHerausgelegt++;
					}
				}
			}
		}
	}

	/**
	 * Die Methode entscheidet zufällig über true oder false
	 * 
	 * @return Zufällig true oder false
	 */
	public boolean randomBoolean() {
		Random random = new Random();
		return random.nextBoolean();
	}

	/**
	 * Legt nur Würfel heraus, die einen Unterschied zum Aufgabenstreifen zwischen 0
	 * und 1 Punkten haben. Wenn keiner herausgelegt werden konnte, wird der
	 * "bestmögliche" Würfel herausgelegt.
	 * 
	 */
	public void smartHerauslegen(List<Dice> aufgabenstreifenDices) {
		int countHerausgelegt = 0;
		for (int i = 0; i < 6; i++) {
			if (dices.get(i).isHerauslegbar()) {
				int difference = differencePlayerDiceToAufgabenstreifenDice(dices.get(i), aufgabenstreifenDices.get(i));

				if (difference <= 1) {
					dices.get(i).setHerausgelegt(true);
					dices.get(i).setHerauslegbar(false);
					countHerausgelegt++;
				}
			}
		}

		if (countHerausgelegt == 0) {
			legeBestDiceHeraus(aufgabenstreifenDices);
		}
	}

	/**
	 * Legt einen Würfel mit dem kleinsten Unterschied zum Aufgabenstreifen heraus.
	 * 
	 * @param aufgabenstreifenDices
	 */
	private void legeBestDiceHeraus(List<Dice> aufgabenstreifenDices) {
		int pointsToBeat = 5;
		Dice bestDice = null;
		for (int i = 0; i < 6; i++) {
			if (dices.get(i).isHerauslegbar()) {
				int difference = differencePlayerDiceToAufgabenstreifenDice(dices.get(i), aufgabenstreifenDices.get(i));

				if (difference <= pointsToBeat) {
					pointsToBeat = difference;
					bestDice = dices.get(i);
				}
			}
		}

		bestDice.setHerausgelegt(true);
		bestDice.setHerauslegbar(false);
	}

	public int differencePlayerDiceToAufgabenstreifenDice(Dice playerDice, Dice aufgabenStreifenDice) {
		return aufgabenStreifenDice.getPoints() - playerDice.getPoints();
	}

	public Strategy getStrategy() {
		return strategy;
	}

	@Override
	public String toString() {
		return name + " " + strategy;
	}

	@Override
	public String toStringScore() {
		return name + " " + strategy + ": " + score;
	}

}
