package scum;

import java.util.Stack;

public class Pile implements PileInterface {

	private Stack<Card> pile;

	public Pile() {
		pile = new Stack<Card>();
	}

	@Override
	public void print() {
		for (Card card : pile) {
			System.out.println(card);
		}

	}

	@Override
	public void printLastMove() {
		// TODO Somehow remember the last move

	}

	@Override
	public boolean isEmpty() {
		return pile.empty();
	}

}
