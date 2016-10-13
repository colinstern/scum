package scum;

import java.util.ArrayList;

public class Hand implements HandInterface{

	private ArrayList<Card> hand;
	
	public Hand() {
		hand = new ArrayList<Card>();
		receiveCardsFromDeal(hand);
	}
	
	/**
	 * Someohow receive cards dealt from the deck.
	 */
	public void receiveCardsFromDeal(ArrayList<Card> hand) {
		//TODO work with Deck to make this
	}
	
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void sort() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}

}
