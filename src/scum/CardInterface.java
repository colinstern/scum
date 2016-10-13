package scum;

/**
 * Defines how the Cards will work.
 * @author cost
 *
 */
public interface CardInterface {

	/**
	 * Returns the suit and number of the card.
	 * @return Suit and number of the card.
	 */
	public String getSuitAndNumber();
	
	/**
	 * 
	 * @return The suit of the card.
	 */
	public String getSuit();
	
	/**
	 * Returns the number of the card.
	 * @return
	 */
	public String getNumber();
}
