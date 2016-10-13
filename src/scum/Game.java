package scum;

/**
 * Implements the main game logic.
 * @author cost
 *
 */
public class Game implements GameInterface {
	
	Deck deck;
	/**
	 * Initializes the game with n players.
	 */
	public Game(int n) {
		deck = new Deck();
	//TODO	
	}
	
	@Override
	public boolean winnerExists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Player winner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValidMove(Card[] cards) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTurn(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		deck.print(); //TODO remove
		
	}

}
