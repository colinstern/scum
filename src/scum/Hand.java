package scum;

import java.util.ArrayList;

public class Hand implements HandInterface{

	private ArrayList<Card> hand;
	
	public Hand() {
		hand = new ArrayList<Card>();
	}
	
	/**
	 * Somehow receive cards dealt from the deck.
	 */
	public void addCardFromDeal(Card card) {
		hand.add(card);
	}
	
	@Override
	public int getSize() {
		return hand.size();
	}

	@Override
	public void sort() {
		hand.sort(new CardComparator());

	}

	@Override
	public void print() {
		int i = 0;
		for (Card card: hand) {
			System.out.println(card);
			i++;
		}
		System.out.println(i);

	}

}
