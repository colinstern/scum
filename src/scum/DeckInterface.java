package scum;

/**
 * Defines how the Deck will work.
 * @author cost
 *
 */
public interface DeckInterface {

	/**
	 * Shuffle the deck randomly.
	 */
	public void shuffle();
	
	/**
	 * Returns one random card from the deck.
	 * @throws ScumException If deck is empty.
	 */
	public Card deal() throws ScumException;
	
	/**
	 * Prints the current state of the deck.
	 */
	public void print();
	
	
}
