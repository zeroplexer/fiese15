package ch.bfh.fiese15.util;

import static java.util.Collections.shuffle;

import java.util.ArrayList;
import java.util.List;

import ch.bfh.fiese15.models.Aufgabenstreifen;
import ch.bfh.fiese15.models.Color;
import ch.bfh.fiese15.models.Dice;

public class AufgabenstreifenHelper {
	
	public static List<Aufgabenstreifen> initializeAufgabenstreifen() {
		List<Aufgabenstreifen> aufgabenstreifenList = new ArrayList<Aufgabenstreifen>();
		
		Aufgabenstreifen aufgabenstreifen1 = new Aufgabenstreifen(getDices(1, 3, 4, 1, 4, 2));
		Aufgabenstreifen aufgabenstreifen2 = new Aufgabenstreifen(getDices(4, 4, 4, 1, 1, 1));
		Aufgabenstreifen aufgabenstreifen3 = new Aufgabenstreifen(getDices(4, 3, 3, 2, 2, 1));
		Aufgabenstreifen aufgabenstreifen4 = new Aufgabenstreifen(getDices(1, 1, 2, 3, 4, 4));
		Aufgabenstreifen aufgabenstreifen5 = new Aufgabenstreifen(getDices(4, 4, 2, 2, 2, 1));
		Aufgabenstreifen aufgabenstreifen6 = new Aufgabenstreifen(getDices(1, 1, 1, 2, 5, 5));
		Aufgabenstreifen aufgabenstreifen7 = new Aufgabenstreifen(getDices(6, 3, 3, 1, 1, 1));
		Aufgabenstreifen aufgabenstreifen8 = new Aufgabenstreifen(getDices(5, 4, 3, 1, 1, 1));
		Aufgabenstreifen aufgabenstreifen9 = new Aufgabenstreifen(getDices(5, 3, 3, 2, 1, 1));
		Aufgabenstreifen aufgabenstreifen10 = new Aufgabenstreifen(getDices(6, 4, 2, 1, 1, 1));
		Aufgabenstreifen aufgabenstreifen11 = new Aufgabenstreifen(getDices(1, 2, 2, 2, 2, 6));
		Aufgabenstreifen aufgabenstreifen12 = new Aufgabenstreifen(getDices(1, 1, 2, 2, 4, 5));
		Aufgabenstreifen aufgabenstreifen13 = new Aufgabenstreifen(getDices(5, 3, 2, 2, 2, 1));
		Aufgabenstreifen aufgabenstreifen14 = new Aufgabenstreifen(getDices(1, 1, 1, 1, 5, 6));
		Aufgabenstreifen aufgabenstreifen15 = new Aufgabenstreifen(getDices(1, 1, 3, 3, 3, 4));
		
		aufgabenstreifenList.add(aufgabenstreifen1);
		aufgabenstreifenList.add(aufgabenstreifen2);
		aufgabenstreifenList.add(aufgabenstreifen3);
		aufgabenstreifenList.add(aufgabenstreifen4);
		aufgabenstreifenList.add(aufgabenstreifen5);
		aufgabenstreifenList.add(aufgabenstreifen6);
		aufgabenstreifenList.add(aufgabenstreifen7);
		aufgabenstreifenList.add(aufgabenstreifen8);
		aufgabenstreifenList.add(aufgabenstreifen9);
		aufgabenstreifenList.add(aufgabenstreifen10);
		aufgabenstreifenList.add(aufgabenstreifen11);
		aufgabenstreifenList.add(aufgabenstreifen12);
		aufgabenstreifenList.add(aufgabenstreifen13);
		aufgabenstreifenList.add(aufgabenstreifen14);
		aufgabenstreifenList.add(aufgabenstreifen15);
				
		shuffle(aufgabenstreifenList);
		
		return aufgabenstreifenList;
	}
	
	private static List<Dice> getDices(int value1, int value2, int value3, int value4, int value5, int value6) {
		List<Dice> dices = new ArrayList<Dice>();
		
		Dice dice1 = new Dice(value1, Color.BLUE);
		Dice dice2 = new Dice(value2, Color.YELLOW);
		Dice dice3 = new Dice(value3, Color.BLACK);
		Dice dice4 = new Dice(value4, Color.WHITE);
		Dice dice5 = new Dice(value5, Color.GREEN);
		Dice dice6 = new Dice(value6, Color.RED);
		
		dices.add(dice1);
		dices.add(dice2);
		dices.add(dice3);
		dices.add(dice4);
		dices.add(dice5);
		dices.add(dice6);
		
		return dices;
	}
	
}
