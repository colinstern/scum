package scum;

public class Card implements CardInterface {
	
	private String number;
	private String suit;
	
	public Card(String number, String suit) {
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
	
	@Override
	public String toString() {
		return getSuitAndNumber();
	}

}
