package fi.solinor.mathathon.markka.main;

import fi.solinor.mathathon.painimatsi.fight.Arena;
import fi.solinor.mathathon.painimatsi.fighter.Andrey;
import fi.solinor.mathathon.painimatsi.fighter.OneTwoPuncher;
import fi.solinor.mathathon.painimatsi.fighter.RandomFighter;
import fi.solinor.mathathon.markka.fighter.MarkkaFighter;
import fi.solinor.mathathon.markka.fighter.TodFighter;

public class Main {

	
	public static void main(String[] args) {
		System.out.println(Arena.fight(new MarkkaFighter(), new TodFighter()).getEndResult());
		System.out.println(Arena.fight(new MarkkaFighter(), new TodFighter()).getEndResult());
		System.out.println(Arena.fight(new MarkkaFighter(), new TodFighter()).getEndResult());
	}
}
