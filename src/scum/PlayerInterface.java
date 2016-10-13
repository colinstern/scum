package scum;

/**
 * Defines the behavior of a player.
 * @author cost
 *
 */
public interface PlayerInterface {

	/**
	 * Attempts to play a set of cards from the player's hand.
	 * @param cards The array of cards to play.
	 * @return If this is a valid move, returns true. Else, returns false.
	 */
	public boolean play(Card[] cards);
	
	/**
	 * Prints the last few moves on the pile.
	 */
	public void printPile();
	
	/**
	 * Prints the entire pile.
	 */
	public void printFullPile();
	
	/**
	 * Sorts the cards in the player's hand.
	 */
	public void sort();
	
	/**
	 * Prints the cards in the player's hand.
	 */
	public void printHand();
	
	/**
	 * Checks if the player has won.
	 * @return True if the player has won, false otherwise.
	 */
	public boolean hasWon();
	
}