package scum;

import java.util.ArrayList;
import java.util.Stack;

public class Pile implements PileInterface {

	private Stack<Card> pile;

	public Pile() {
		pile = new Stack<Card>();
	}
	
	/**
	 * Adds a single card to the pile.
	 * @param card The card to add.
	 * @return True if card is added successfully, false otherwise
	 */
	public boolean add(Card card) {
		return pile.add(card);
	}
	
	/**
	 * Adds an array of cards to the pile. The first cards in the array are added first, aka they end up lower on the stack than cards at the end of the array.
	 * @param cards Array of Cards to add to the pile.
	 * @return True if all cards are added to the pile, false otherwise.
	 */
	public boolean add(Card[] cards) {
		for (Card card : cards) {
			if (!pile.add(card))
				return false;
		}
		return true;
	}
	
	/**
	 * Peeks the top card on the pile.
	 * @return The top card on the pile.
	 */
	public Card peek() {
		return pile.peek();
	}
	
	/**
	 * Returns an array of the howMany most recently played cards. The most recently played cards will be at the beginning of the array.
	 * @param howMany The number of most recently played cards to show. If this number is greater than the number of cards on the pile, the entire pile will be returned.
	 * @return An array of the most recently played cards on the pile.
	 */
	public Card[] peekMany(int howMany) {
		if (howMany > pile.size())
			howMany = pile.size();
		ArrayList<Card> cardsList = new ArrayList<Card>();
		for (int i = 0; i < howMany; i++) {
			cardsList.add(pile.pop());
		}
		Card[] cards = cardsList.toArray(new Card[cardsList.size()]);
		for (Card card : cardsList)
			pile.push(card);
		return cards;
	}

	@Override
	public void print() {
		for (Card card : pile) {
			System.out.println(card);
		}

	}
	
	/**
	 * Clears the pile.
	 */
	public void clear() {
		pile.clear();
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
