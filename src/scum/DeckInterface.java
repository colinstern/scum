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
	 * Distribute the cards equally to each player.
	 */
	public void deal();
	
	/**
	 * Prints the current state of the deck.
	 */
	public void print();
	
	
}
