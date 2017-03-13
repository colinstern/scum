package scum;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	private int sizeOfLastMove;
	
	private Card lastPlayedCard;
	
	private boolean skipNextTurnFlag;
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
		sizeOfLastMove = 0;
		lastPlayedCard = null;
		skipNextTurnFlag = false;
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
		if (winner == null)
			throw new ScumException("No winner!");
		return winner;
	}

	@Override
	public boolean isValidMove(Card[] cards, int i) {
		ArrayList<Card> cardsList = new ArrayList<Card>();
		for (Card card : cards)
			cardsList.add(card);
		/* Make sure player has cards he is playing */
		if (!(players.get(i).getHand().contains(cards)))
				return false;
		
		/* Check if player is playing a 2 as first card*/
		if (cards[0].getNumberAsInt() == 2) {
			return twoChecker(cardsList);
		}
		
		/* Normal play: Check that cards are all same number, greater or equal to last move,
		 * and are following singles, doubles, etc. */
		return checkNormalPlay(cardsList);		
	}
	
	/**
	 * A recursive checker for valid moves with 2's.
	 */
	public boolean twoChecker(ArrayList<Card> cardsList) {
		/* Inductive */
		cardsList.get
		
		/* Check if nothing, singles, doubles, triples or social is after the 2, make sure length is appropriate*/
		if ((cardsList.size() - 1) > 4)
			return false;
		/* Make sure cards are of same number */
		if (cardsList.size() > 1) {
		int firstCardNum = cardsList.get(1).getNumberAsInt();
			for (int j = 2; j < cardsList.size(); j++) {
				if (cardsList.get(j).getNumberAsInt() != firstCardNum)
					return false;
			}
		}
		
		/* Take subarray of cards after the 2 */
		ArrayList<Card> cardsSublist = new ArrayList<Card>();
		for (int k = 0; k < cardsList.size() - 1; k++) {
			cardsSublist.add(k, cardsList.get(k + 1));
		}
		/* Check that cards after the 2 follow normal play rules */
		if (!checkNormalPlay(cardsSublist))
			return false;
		else {
			/* Set to singles, doubles, triples after the 2 */
			sizeOfLastMove = cardsList.size() - 1;
			/* Null lastPlayedCard so we can play whatever we want */
			lastPlayedCard = null;
			
			return true;
		}
	}
	
	
	public boolean areCardsAllSameNumber(ArrayList<Card> cardsList) {
		/* Empty move is trivially correct */
		if (cardsList.size() < 1)
			return true;
		int firstCardNum = cardsList.get(0).getNumberAsInt();
		for (Card card : cardsList) {
			if (card.getNumberAsInt() != firstCardNum)
				return false;
		}
		return true;
	}
	
	public boolean checkNormalPlay(ArrayList<Card> cardsList) {
		/* Normal play: Check that cards are all same number, greater or equal to last move,
		 * and are following singles, doubles, etc. */
		/* Empty move is trivially correct */
		if (cardsList.size() < 1)
			return true;
		if (!areCardsAllSameNumber(cardsList))
			return false;
		if (lastPlayedCard != null && cardsList.get(0).getNumberAsInt() < lastPlayedCard.getNumberAsInt())
			return false; 
		if (sizeOfLastMove != 0 && cardsList.size() != sizeOfLastMove)
			return false;
		if (sizeOfLastMove == 0) 
			sizeOfLastMove = cardsList.size();
		
		/* Check for skips - if in singles, a card played in last turn had same number as card played in this turn */
		if (lastPlayedCard != null && ((cardsList.get(0).getNumberAsInt() == lastPlayedCard.getNumberAsInt()) && sizeOfLastMove == 1))
			skipNextTurn();
		
		return true;
	}
	
	/**
	 * Skip next player's turn.
	 */
	public void skipNextTurn() {
		skipNextTurnFlag = true;
	}
	
	
	@Override
	public boolean makeMove(Card[] cards, int i) {
		for (Card card : cards) {
			players.get(i).getHand().remove(card);
			pile.add(card);
		}
		lastPlayedCard = cards[cards.length - 1];
		return true;
	}

	@Override
	public boolean isTurn(int playerId) {
		return (currentPlayer == playerId);
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
			try {
			String[] tokens = strings[i].split(" "); //split on the spaces
			cards[i] = new Card(tokens[0], tokens[2]); //given the String "3 of clubs", feed "3" and "clubs" to the Card constructor
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new ScumException("A card has been entered incorrectly.");
			}
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
		System.out.printf("\nPlayer %d, it is your turn.\n", i);
		Scanner scanner = new Scanner(System.in);
		String input;
		String[] tokens;
		Card[] cards;
		while (true) {
			try {
				System.out.println("Enter cards to play. Cards must be comma-separated, like this: 3 of clubs, 3 of diamonds\nIf you do not wish to play a card, enter \"pass\". To see your hand, enter \"hand\".");
				input = scanner.nextLine();
				if (input.equals("pass"))
					break;
				if (input.equals("hand")) {
					players.get(i).printHand();
					continue;
				}
				if (input.equals("pile")) {
					pile.print();
					continue;
				}
				tokens = input.split(", ");
				try {
					cards = castStringsToCards(tokens);
				} catch (ScumException e) {
					System.out.println(e.getMessage() + " Could not interpret the input. Please enter again.");
					continue;
				}
				if (!isValidMove(cards, i))  {
					throw new ScumException("This is not a valid move. Please try again.");
				}
				System.out.println("You entered: ");
				for (Card card: cards)
					System.out.println(card);
				makeMove(cards, i);
				
			} catch (ScumException e) {
				System.out.println(e.getMessage());
				continue;
			}
			break;
		} 
		//scanner.close(); 
	}


	@Override
	public void start() {
		int maxTurns = 100;
		for (int i = 0; !winnerExists() && i < maxTurns; i++) {
			currentPlayer = i % numberOfPlayers;
			giveTurn(currentPlayer);
			if (skipNextTurnFlag) {
				/* Increment here to skip the next player */
				i++;
				System.out.println("\nPlayer " + i + " has been skipped!");
				skipNextTurnFlag = false;
			}
		}
		if (winnerExists()) {
			try {
				System.out.println(winner().toString() + " is President!");
			} catch (ScumException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Max turns exceeded.");
		}
		System.out.println("Game over.\n");
	}
}


