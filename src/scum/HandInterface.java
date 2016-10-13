package scum;

/**
 * Defines how a player's hand will work.
 * @author cost
 *
 */
public interface HandInterface {
	
	/**
	 * 
	 * @return The size of the player's hand.
	 */
	public int getSize();
	
	/**
	 * Sorts the hand from smallest to largest card.
	 */
	public void sort();
	
	/**
	 * Prints the current hand.
	 */
	public void print();
	

}
