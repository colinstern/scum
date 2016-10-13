package scum;

import java.util.ArrayList;

public class Deck implements DeckInterface {

	private ArrayList<Card> deck;
	
	/**
	 * Initializes a standard deck of 52 cards.
	 */
	public Deck() {
		deck = new ArrayList<Card>();
		for (int i = 0; i < 4; i++) {
			String suit;
			switch (i) {
			case (0) :
				suit = "spades";
				break;
			case (1) :
				suit = "hearts";
				break;
			case (2) :
				suit = "diamonds";
				break;
			default :
				suit = "clubs";
				break;
			}
			for (int j = 2; j <= 14; j++) {
				String number;
				switch (j) {
				case (11):
					number = "jack";
					break;
				case (12):
					number = "queen";
					break;
				case (13):
					number = "king";
					break;
				case (14):
					number = "ace";
					break;
				default:
					number = new Integer(j).toString();
				}
				deck.add(new Card(number, suit));
			}
		}
	}
	
	@Override
	public void shuffle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print() {
		System.out.println("Current state of the deck:");
		for (Card card : deck)
			System.out.println(card);
		System.out.println("End of deck printout.");
		
	}

}
