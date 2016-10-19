package scum;

/**
 * Defines the game logic.
 * @author cost
 *
 */
public interface GameInterface {

	/**
	 * Determines if someone has won.
	 * @return True if some player has won.
	 */
	public boolean winnerExists();
	
	/**
	 * If a player has won, returns which player.
	 * @return The winning Player if a Player has won. Else, returns null.
	 * @throws ScumException If there is an error and more than one player has won.
	 */
	public Player winner() throws ScumException;
	
	/**
	 * Checks if the given move if valid. Compares the cards trying to be played with the pile. If the move is valid, add the cards to the pile.
	 * If not, tell the player to make a valid move.
	 * @return Whether the move is valid or not.
	 */
	public boolean isValidMove(Card[] cards);
	
	/**
	 * Checks if it is the given player's turn.
	 * @param playerId The player to check, denoted by his ID.
	 * @return Whether or not it is the player's turn.
	 */
	public boolean isTurn(int playerId);
	
	/**
	 * Starts the game. Manages the players turns and the game runtime.
	 */
	public void start();
}
