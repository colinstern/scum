package scum;

import java.util.Comparator;

public class Card implements CardInterface {
	
	private String number;
	private String suit;
	
	public Card(String number, String suit) throws ScumException {
		if (!((number.equals("2") || number.equals("3") ||  number.equals("4") || number.equals("5") || number.equals("6") || number.equals("7") || number.equals("8") ||
				 number.equals("9") || number.equals("10") || number.equals("jack") || number.equals("queen") || number.equals("king") || number.equals("ace"))
				&& (suit.equals("spades") || suit.equals("hearts") || suit.equals("diamonds") || suit.equals("clubs"))))
				throw new ScumException("Not a valid Card.");
		this.number = number;
		this.suit = suit;
	}

	@Override
	public String getSuitAndNumber() {
		return number + " of " + suit;
	}

	@Override
	public String getSuit() {
		return suit;
	}

	@Override
	public String getNumber() {
		return number;
	}
	
	public int getNumberAsInt() {
		int cardNumber;
		switch (number) {
		case ("jack"):
			cardNumber = 11;
			break;
		case ("queen"):
			cardNumber = 12;
			break;
		case ("king"):
			cardNumber = 13;
			break;
		case ("ace"):
			cardNumber = 14;
			break;
		default:
			cardNumber = Integer.parseInt(number);
		}
		return cardNumber;
	}
	
	@Override
	public String toString() {
		return getSuitAndNumber();
	}
	
	@Override
	public boolean equals(Object obj) {
		return compareTo((Card) obj) == 0;
	}

	/**
	 * Stores suitValue as most significant digit, while card number is stored as least two significant digits
	 * @return Unique value for each card to allow sorting. Lowest to highest: spades, hearts, diamonds, clubs, 2 through Ace
	 */
	public int hash() {
		int suitValue;
		switch (suit) {
		case ("spades") :
			suitValue = 0;
			break;
		case ("hearts") :
			suitValue = 1;
			break;
		case ("diamonds") :
			suitValue = 2;
			break;
		default :
			suitValue = 3;
			break;
		}
		int cardNumber;
		switch (number) {
		case ("jack"):
			cardNumber = 11;
			break;
		case ("queen"):
			cardNumber = 12;
			break;
		case ("king"):
			cardNumber = 13;
			break;
		case ("ace"):
			cardNumber = 14;
			break;
		case ("2") :
			cardNumber = 15;
			break;
		default:
			cardNumber = Integer.parseInt(number);
		}
		return (suitValue * 100) + cardNumber;
	}

	public int compareTo(Card b) {
		return Integer.compare(this.hash(), b.hash());
	}

}

class CardComparator implements Comparator<Card> {
	public int compare(Card a, Card b) {
		return a.compareTo(b);
	}
}
