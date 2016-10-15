package scum;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Implements the main game logic.
 * @author cost
 *
 */
public class Game implements GameInterface {

	private Deck deck;

	private Pile pile;

	private int idCount;

	private ArrayList<Player> players;

	private int currentPlayer;

	private int numberOfPlayers;
	/**
	 * Initializes the game with n players.
	 */
	public Game(int n) {
		deck = new Deck();
		deck.shuffle();
		players = new ArrayList<Player>();
		idCount = 0;
		initializePlayers(n);
		initializeHands(n);
		pile = new Pile();
		currentPlayer = 0;
		numberOfPlayers = n;
		//TODO	
	}

	private void initializePlayers(int n) {
		for (int i = 0; i < n; i++) {
			players.add(i, new Player(i));
		}
	}

	/**
	 * Divy up the cards in the hand randomly and equally between players.
	 * @param n
	 */
	private void initializeHands(int n) {
		try {
			int initialSize = deck.getSize();
			for (int i = 0; i < initialSize; i++) {
				players.get(i % n).getHand().addCardFromDeal(deck.deal()); //adds a random card from the deck to the player's hand in a round-robin fashion
			}
		} catch (ScumException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public boolean winnerExists() {
		for (Player player : players) {
			if (player.getHand().getSize() == 0)
				return true;
		}

		return false;
	}

	@Override
	public Player winner() throws ScumException {
		Player winner = null;
		boolean moreThanOneWinner = false;
		for (Player player : players) {
			if ((player.getHand().getSize() == 0) && !moreThanOneWinner) {
				winner = player;
				moreThanOneWinner = true;
			}
			else if ((player.getHand().getSize() == 0) && moreThanOneWinner) {
				throw new ScumException("More than one winner?!");
			}
		}
		return winner;
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

	/**
	 * Cast an array of cards in String format to an array of cards.
	 * @param strings Cards in String format
	 * @return Array of cards
	 * @throws ScumException 
	 */
	public Card[] castStringsToCards(String[] strings) throws ScumException {
		Card[] cards = new Card[strings.length];
		for (int i = 0; i < strings.length; i++) {
			String[] tokens = strings[i].split(" "); //split on the spaces
			cards[i] = new Card(tokens[0], tokens[3]);
		}
		return cards;
	}

	/**
	 * Returns a unique ID for a new player instance.
	 * @return Unique player ID
	 */
	public int serveId() {
		int id = idCount;
		idCount++;
		return id;
	}

	/**
	 * Give player a i a turn.
	 * @param i The player who gets a turn
	 */
	public void giveTurn(int i) {
		System.out.printf("Player %d, it is your turn.\n", i);
		Scanner scanner = new Scanner(System.in);
		String input;
		String[] tokens;
		Card[] cards;
		while (true) {
			try {
				System.out.println("Enter cards to play. Cards must be comma-separated, like this: 3 of clubs,3 of diamonds");
				input = scanner.nextLine();
				tokens = input.split(",");
				if ((tokens.length < 1)) {
					System.out.println("Please play a card.\n");
					throw new ScumException("No cards played.\n");
				}
				try {
					cards = castStringsToCards(tokens);
				} catch (ScumException e) {
					System.out.println("Could not interpret the input. Please enter again.");
					break;
				}
				if (!isValidMove(cards))  {
					throw new ScumException("This is not a valid move. Please try again.");
				}
				
			} catch (ScumException e) {
				System.out.println();
			} catch (Exception e){
				
			}
			break;
		} 
	}


	@Override
	public void start() {
		int maxTurns = 5000;
		for (int i = 0; !winnerExists() && i < maxTurns; i++) {
			giveTurn(i % numberOfPlayers);
		}
	}
}


