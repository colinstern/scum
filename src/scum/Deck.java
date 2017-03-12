package scum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
				try {
					deck.add(new Card(number, suit));
				} catch (ScumException e) {
					System.out.println("Could not add a card to the deck!");
				}
			}
		}
	}
	
	@Override
	public void shuffle() {
		long seed = System.nanoTime();
		Collections.shuffle(deck, new Random(seed));
	}
	
	public void sort() {
		Collections.sort(deck, new CardComparator());
	}

	@Override
	public Card deal() throws ScumException {
		if (deck.size() == 0)
			throw new ScumException("Deck is empty.");
		Random rand = new Random();
		int randomNum = rand.nextInt((deck.size()));	//random int is in range of the size of the deck 
		return deck.remove(randomNum);
	}
	
	public int getSize() {
		return deck.size();
	}
	
	public boolean isEmpty() {
		return deck.size() != 0;
	}

	@Override
	public void print() {
		System.out.println("Current state of the deck:");
		for (Card card : deck)
			System.out.println(card);
		System.out.println("End of deck printout.");
		
	}

}
