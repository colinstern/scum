package scum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import jdk.management.resource.internal.TotalResourceContext;

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
	
	private int turnsWithoutPassFlagsCleared;
	
	private ArrayList<Card> previousMove;
	
	private String errorMessage;
	
	private Player[] scoreboard;
	
	private int ithWinner;
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
		turnsWithoutPassFlagsCleared = 0;
		previousMove = null;
		errorMessage = null; 
		scoreboard = new Player[n];
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

	public boolean isValidMove(Card[] cards, Player id) {
//		/* Make an ArrayList from cards */
//		ArrayList<Card> cardsList = new ArrayList<Card>();
//		for (Card card : cards)
//			cardsList.add(card);
//		
//		/* Check if move is empty, trivially return true if so */
//		if (cardsList.isEmpty())
//			return true;
//		
//		/* Make sure player has cards he is playing */
//		if (!(id.getHand().contains(cards))) {
//			errorMessage = "Some of these cards are not in your hand.";
//			return false;
//		}
//		
//		/* If player is playing a 2 as first card, feed a list without the starting 2 to twoChecker */
//		if (cardsList.get(0).getNumberAsInt() == 2) {
//			return twoChecker(new ArrayList<Card> (cardsList.subList(1, cardsList.size())), id);
//		}
//		
//		/* Check for bombs */
//		if (bombChecker(cardsList)) {
//			System.out.println("\nBomb by " + id + "!");
//			/* Feed a list without the starting bomb to twoChecker */
//			return twoChecker(new ArrayList<Card> (cardsList.subList(4, cardsList.size())), id);
//		}
//		
//		/* Check for socials */
//		if (socialChecker(cardsList) > 0) {
//			System.out.println("\nSocial by " + id + "!");
//			/* Feed a list without the starting social to twoChecker */
//			return twoChecker(new ArrayList<Card> (cardsList.subList(socialChecker(cardsList), cardsList.size())), id);
//		}
//		
//		/* Normal play: Check that cards are all same number, greater or equal to last move,
//		 * and are following singles, doubles, etc. */
//		if (!checkNormalPlay(cardsList))
//			return false;
//		
//		/* Check for skips, and if found set a flag that will skip the next player's turn */
//		checkForSkips(cardsList);
//		
//		/* Set last played card */
//		lastPlayedCard = cardsList.get(cardsList.size() - 1);
//		
//		/* If you are starting the game you can set singles, doubles or triples */
//		if (sizeOfLastMove == 0) 
//			sizeOfLastMove = cardsList.size();
//		
//		/* Store this move */
//		previousMove = cardsList;
		
		return true;
		
	}
	
	public void checkForSkips(ArrayList<Card> cardsList) {
		/* Check for skips - if in singles, a card played in last turn had same number as card played in this turn */
		if (lastPlayedCard != null && ((cardsList.get(0).getNumberAsInt() == lastPlayedCard.getNumberAsInt()) && sizeOfLastMove == 1))
			skipNextTurn();
		
	}
	
	/**
	 * A recursive checker for valid moves started by 2's.
	 * cardsList has had the starting 2 removed.
	 */
	public boolean twoChecker(ArrayList<Card> cardsList, Player id) {
		/* Check for an empty list. A player played a 2 and then essentially passed */
		if (cardsList.isEmpty()) {
			System.out.println("\nCleared by " + id + "!");
			/* Reset size of last move */
			sizeOfLastMove = 0;
			/* Null lastPlayedCard */
			lastPlayedCard = null;
			return true;
		}
		
		/* Case 1: Check if first card is a 2 */
		if (cardsList.get(0).getNumberAsInt() == 2)
			/* check if this 2 move is valid. Feed list without starting 2 to twoChecker */
			return twoChecker(new ArrayList<Card> (cardsList.subList(1, cardsList.size())), id);
		
		/* Case 2: Check if the list is started by a bomb */
		if (bombChecker(cardsList)) {
			/* Check the move after the bomb */
			twoChecker(new ArrayList<Card> (cardsList.subList(4, cardsList.size())), id);
		}
		
		/* Case 3: List is not started by a 2 or a bomb, and is not empty */

		/* Check that these cards are all the same number */
		if(!areCardsAllSameNumber(cardsList))
			return false;
		/* Use checkNormalPlay to set size of move and last played card */
		else {
			/* Reset size of last move */
			sizeOfLastMove = 0;
			/* Null lastPlayedCard so we can allow the 2 move */
			lastPlayedCard = null;
			/* Check normal play rules */
			if (!checkNormalPlay(cardsList))
				return false;
			
			System.out.println("\nCleared by " + id + "!");
			
			/* Set last played card */
			lastPlayedCard = cardsList.get(cardsList.size() - 1);
			
			/* Set singles, doubles or triples */
			sizeOfLastMove = cardsList.size();
			
			return true;
		}
	}
	
	/**
	 * Checks if player has just completed a social. bombChecker must run on cardsList first to ensure that 
	 * socialChecker does not return 4 in the case of a bomb. However, this behavior would still be correct.
	 * Essentially, bombChecker is redundant.
	 * @param cardsList Cards in the move
	 * @return 0 if player has not completed a social, or an int from 1 to 3 inclusive of how many cards the player
	 * played to complete the social
	 */
	public int socialChecker(ArrayList<Card> cardsList) {
		/* There must be a total of 4 cards to make a social */
		if ((cardsList.size() + pile.size()) < 4)
				return 0;
		
		if (cardsList.isEmpty() || pile.isEmpty())
			return 0;
		
		/* Count how many cards on the pile have same number */
		ArrayList<Card> pilePeekFour = pile.peekMany(4);
		
		/* Starting number will be used to check card numbers in both pile and hand */
		int topNumber = pilePeekFour.get(0).getNumberAsInt();
		
		/* pileSame counts how many cards in top of the pile have the same number.
		 * Initialized to 1 because first card has same number as itself */
		int pileSame = 1;
		for (int i = 1; i < pilePeekFour.size(); i++) {
			if (pilePeekFour.get(i).getNumberAsInt() != topNumber) {
				/* We have found a card with a number different than the top card. Stop counting */
				break;
			}
			pileSame++;
		}
		
		/* Count how many cards in the hand have same number */
		/* handSame counts how many cards in top of the pile have the same number.
		 * Initialized to 0 because first card may not have same number as pile's top card */
		int handSame = 0;
		for (int j = 0; j < cardsList.size(); j++) {
			if (cardsList.get(j).getNumberAsInt() != topNumber) {
				/* We have found a card with a number different than the top pile card. Stop counting */
				break;
			}
			handSame++;
		}
		
		/* If player has completed a 4 of a kind, or a social, return how many cards he played to complete the social */
		if ((pileSame + handSame) == 4)
			return handSame;
		else
			return 0;
	}
	
	/** 
	 * Checks if there is a bomb at the start in this move.
	 * @param cardsList The list of cards
	 * @return True if there is a bomb at the start of this move
	 */
	public boolean bombChecker(ArrayList<Card> cardsList) {
		/* There cannot be a bomb without at least 4 cards */
		if (cardsList.size() < 4)
			return false;
		
		/* Check if first 4 cards have same number */
		return areCardsAllSameNumber(new ArrayList<Card> (cardsList.subList(0, 4)));
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
		/* Normal play: Assuming no 2s or bombs in the move, check that cards are all same number, greater or equal to last move,
		 * and are following singles, doubles, or triples. Sets size of move played if size is unset. */
		
		/* Empty move is trivially correct */
		if (cardsList.isEmpty())
			return true;
		/* Make sure cards are all same number */
		if (!areCardsAllSameNumber(cardsList)) {
			errorMessage = "Not all cards are the same number.";
			return false;
		}
		/* Make sure cards are not less than previous move */
		if (lastPlayedCard != null && cardsList.get(0).getNumberAsInt() < lastPlayedCard.getNumberAsInt()) {
			errorMessage = "Cards in your move are less than the previous move.";
			return false; 
		}
		/* Make sure singles, doubles or triples is maintained */
		if (sizeOfLastMove != 0 && cardsList.size() != sizeOfLastMove) {
			errorMessage = "You played ";
			switch (cardsList.size()) {
			case (1) :
				errorMessage += "singles";
				break;
			case (2) :
				errorMessage += "doubles";
				break;
			case (3) :
				errorMessage += "triples";
			}
			errorMessage += "; you should have played ";
			switch (sizeOfLastMove) {
			case (1) :
				errorMessage += "singles";
				break;
			case (2) :
				errorMessage += "doubles";
				break;
			case (3) :
				errorMessage += "triples";
			}
			errorMessage += ".";
			
			return false;
		}

		return true;
	}
	
	/**
	 * Skip next player's turn.
	 */
	public void skipNextTurn() {
		skipNextTurnFlag = true;
	}
	
	
	public boolean makeMove(Card[] cards, Player id) {
		for (Card card : cards) {
			id.getHand().remove(card);
			pile.add(card);
		}
		return true;
	}
	
	/**
	 * Checks if all other players besides currentPlayer have set passFlags.
	 * If they have, returns true. If they haven't,
	 * returns false. If all players have set passFlags, returns true.
	 * @param currentPlayer
	 */
	public boolean allOthersHavePassed(int currentPlayer, int numberOfActivePlayers) {
		/* Start by checking next player. Stop before we check the current player */
		for (int i = (currentPlayer + 1) % numberOfActivePlayers; i != currentPlayer; i = (i + 1) % numberOfActivePlayers) {
			/* Check if player has passed */
			if (!players.get(i).getPassFlag())
				return false;
		}
		/* If current player has not passed, return true. If current player has passed, then all 
		 * players have passed. However, we return false */
		return (!players.get(currentPlayer).getPassFlag());
		
	}
	
	/** 
	 * Clears to let current player play whatever he wants. Resets all passFlags.
	 */
	public void autoclear() {
		sizeOfLastMove = 0;
		lastPlayedCard = null;
		clearAllPassFlags();
		System.out.println("\nAll other players have passed - clears to " + players.get(currentPlayer) + "!\n");
	}
	
	/**
	 * Clears each player's pass flag.
	 */
	public void clearAllPassFlags() {
		for (Player player : players)
			player.setPassFlag(false);
		turnsWithoutPassFlagsCleared = 0;
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
	
	public void printHelp() {
		System.out.println("\nCards must be comma-separated, like this: 3 of clubs, 3 of diamonds\nIf you do not wish to play a card, enter \"pass\". To see your hand, enter \"hand\". To see the pile, enter \"pile\".");
	}
	
	public void removeAllCardsFromHand(Player id) {
		for (int i = 0; i < id.getHand().getSize(); i++)
			id.getHand().remove(i);
	}

	/**
	 * Give player a i a turn.
	 * @param i The player who gets a turn
	 */
	public void giveTurn(Player id, int indexOfPlayersArray) {
		System.out.printf("\nPlayer %d, it is your turn.\n", id.getId());
		Scanner scanner = new Scanner(System.in);
		String input;
		String[] tokens;
		Card[] cards;
		while (true) {
			try {
				if (allOthersHavePassed(indexOfPlayersArray, players.size())) {
					autoclear();
				}
				System.out.println("Enter cards to play. For help, enter \"help\".");
				id.printHand();
				input = scanner.nextLine();
				if (input.equals("pass")) {
					id.setPassFlag(true);
					break;
				}
				if (input.equals("hand")) {
					id.printHand();
					continue;
				}
				if (input.equals("pile")) {
					pile.print();
					continue;
				}
				if (input.equals("prev")) {
					if (previousMove == null) {
						System.out.println("No previous move to display.\n");
						continue;
					}
					System.out.println("Previous move:");
					for (Card card : previousMove)
						System.out.println(card);
					System.out.println();
					continue;
				}
				if (input.equals("size")) {
					System.out.println(sizeOfLastMove);
					continue;
				}
				if (input.equals("card")) {
					if (lastPlayedCard != null)
						System.out.println(lastPlayedCard);
					continue;
				}
				if (input.equals("remove")) {
					removeAllCardsFromHand(id);
					continue;
				}
				if (input.equals("help")) {
					printHelp();
					continue;
				}
				tokens = input.split(", ");
				try {
					cards = castStringsToCards(tokens);
				} catch (ScumException e) {
					System.out.println(e.getMessage() + " Could not interpret the input. Please enter again.\n");
					continue;
				}
				if (!isValidMove(cards, id))  {
					if (errorMessage != null) {
						System.out.println(errorMessage + "\n");
						errorMessage = null;
					}
					throw new ScumException("This is not a valid move. Please try again.\n");
				}
				makeMove(cards, id);
				
			} catch (ScumException e) {
				System.out.println(e.getMessage());
				continue;
			}
			break;
		} 
		//scanner.close(); 
	}
	/** 
	 * Gives turns. Recursively plays with n-1 players once a player wins. Stops game when there is only
	 * 1 player left.
	 * @param n Number of players.
	 */
	public void playWithNPlayers(int n) {
		/* Stop play when only one player is left. */
		if (n < 2) {
			System.out.println(players.get(0) + " is Scum!");
			scoreboard[ithWinner] = players.get(0);
			return;
		}
		/* Play the game until someone has an empty hand */
		for (int i = 0; !winnerExists(); i++) {
			currentPlayer = i % n;
			giveTurn(players.get(currentPlayer), currentPlayer);
			players.get((currentPlayer + 1) % n).setPassFlag(false);
			if (skipNextTurnFlag) {
				/* Increment here to skip the next player */
				i++;
				System.out.println("\n" + players.get(currentPlayer) + " has been skipped!");
				skipNextTurnFlag = false;
			}
		}
		/* Assign the player with an empty hand to the scoreboard array and print their rank */
		if (winnerExists()) {
			try {
				/* Announce winner */
				String winnerMessage = winner().toString() + " is ";
				int viceScum = numberOfPlayers - 2;
				int scum = numberOfPlayers - 1;
				if (ithWinner == 0)
					winnerMessage += "President!";
				else if (ithWinner == 1)
					winnerMessage += "Vice President!";
				else if (ithWinner == viceScum)
					winnerMessage += "Vice Scum!";
				else if (ithWinner == scum)
					winnerMessage += "Scum!";
				else 
					winnerMessage += "Noodge!";
				
				System.out.println(winnerMessage);
				/* Assign winner to scoreboard array */
				scoreboard[ithWinner] = winner();
				/* Count how many winners there have been to aid insertion into the scoreboard */
				ithWinner++;
				/* Remove winner from active players */
				players.remove(winner());
				/* Continue playing with remaining players */
				playWithNPlayers(n-1);
			} catch (ScumException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void printScoreboard() {
		System.out.println("Scoreboard: ");
		for (int i = 0; i < scoreboard.length; i++) {
			String winnerMessage = scoreboard[i] + " is ";
			int viceScum = numberOfPlayers - 2;
			int scum = numberOfPlayers - 1;
			if (i == 0)
				winnerMessage += "President";
			/* This special condition prevents a bug when numberOfPlayers is 2 */
			else if ((numberOfPlayers > 2) && (i == 1))
				winnerMessage += "Vice President";
			else if ((numberOfPlayers > 2) && (i == viceScum))
				winnerMessage += "Vice Scum";
			else if (i == scum)
				winnerMessage += "Scum";
			else 
				winnerMessage += "Noodge";
			
			System.out.println(winnerMessage);
		}
	}

	@Override
	public void start() {
		playWithNPlayers(numberOfPlayers);
		System.out.println("Game over.\n");
		printScoreboard();
	}
}


