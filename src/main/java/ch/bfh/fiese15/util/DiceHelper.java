package ch.bfh.fiese15.util;

import java.util.List;
import java.util.stream.Collectors;

import ch.bfh.fiese15.exception.DiceNotFoundException;
import ch.bfh.fiese15.models.Dice;

public class DiceHelper {
	
	public static Dice getDiceByColor(List<Dice> dices, String color) throws DiceNotFoundException {
		return dices.stream()
			.filter(dice -> dice.getColor().equals(color))
			.findFirst().orElseThrow(() -> new DiceNotFoundException(color));
	}
	
	public static List<Dice> getNotHerausgelegteDices(List<Dice> dices) {
		 return dices.stream()
			.filter(dice -> !dice.isHerausgelegt())
			.collect(Collectors.toList());
	}
	
	public static boolean areAllDicesHerausgelegt(List<Dice> dices) {
		return dices.stream()
				.filter(dice -> dice.isHerausgelegt())
				.count() == 6;
	}
	
}
