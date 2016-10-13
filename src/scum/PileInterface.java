package scum;

/**
 * Defines how the Pile will behave, the place where cards are discarded after being played.
 * @author cost
 *
 */
public interface PileInterface {

	/**
	 * Prints the current state of the pile.
	 */
	public void print();
	
	/**
	 * Prints the cards played in the last move.
	 */
	public void printLastMove();
	
	/**
	 * Checks if the pile is empty.
	 * @return True is pile is empty, false otherwise
	 */
	public boolean isEmpty();
}
