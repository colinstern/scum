package scum;

public class Player implements PlayerInterface{

	/**
	 * The player's unique ID.
	 */
	private int id;
	
	private Hand hand;
	
	private boolean passFlag;
	
	private boolean isTurn;
	
	private boolean isMessageToClient;
	
	private boolean isMessageToGame;
	
	private String messageToClient;
	
	private String messageToGame;
	
	public Player(int id) {
		this.id = id;
		this.hand = new Hand();
		this.passFlag = false;
		this.isTurn = false;
		this.isMessageToClient = false;
		this.isMessageToGame = false;
		this.messageToClient = null;
		this.messageToGame = null;
	}
	
	@Override
	public boolean play(Card[] cards) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public void setHand(Hand hand) {
		this.hand = hand;
	}
	
	public boolean getPassFlag() {
		return passFlag;
	}
	
	public void setPassFlag(boolean status) {
		passFlag = status;
	}
	
	public boolean isTurn() {
		return isTurn;
	}
	
	public void setIsTurn(boolean set) {
		isTurn = set;
	}

	@Override
	public void printPile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printFullPile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sort() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Return this player's id.
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Player " + id;
	}

	@Override
	public void printHand() {
		hand.numberSort();
		System.out.println("\nPlayer " + id + "'s hand:");
		hand.print();
		
	}
	
	public String returnHand() {
		hand.numberSort();
		StringBuilder sb = new StringBuilder();
		sb.append("\nPlayer " + id + "'s hand:\n");
		sb.append(hand.returnPrint());
		return sb.toString();
	}
	
	public boolean isMessageToClient() {
		return isMessageToClient;
	}
	
	public void setIsMessageToClient(boolean set) {
		isMessageToClient = set;
	}
	public boolean isMessageToGame() {
		return isMessageToGame;
	}
	
	public void setIsMessageToGame(boolean set) {
		isMessageToGame = set;
	}
	
	/**
	 * Checks if there is a message message waiting for the client. If so, returns it and empties the "mailbox," 
	 * stored as messageToClient. 
	 * @return message to client
	 * @throws ScumException If there is no message waiting for the client
	 */
	public String getMessageToClient() throws ScumException {
		if (!isMessageToClient)
			throw new ScumException("No message for client.");
		isMessageToClient = false;
		String message = messageToClient;
		messageToClient = null;
		return message;
		
	}
	
	/**
	 * Checks if there is a message message waiting for the game. If so, returns it and empties the "mailbox," 
	 * stored as messageToGame. 
	 * @return message to game
	 * @throws ScumException If there is no message waiting for the game
	 */
	public String getMessageToGame() throws ScumException {
		if (!isMessageToGame)
			throw new ScumException("No message for game.");
		isMessageToGame = false;
		String message = messageToGame;
		messageToGame = null;
		return message;
	}
	
	/**
	 * Puts a message in the client's mailbox. Sets a flag and stores the message.
	 * @param message Message to send to the client
	 */
	public void sendMessageToClient(String message) {
		messageToClient = message;
		isMessageToClient = true;
	}
	
	/**
	 * Puts a message in the game's mailbox. Sets a flag and stores the message.
	 * @param message Message to send to the game
	 */
	public void sendMessageToGame(String message) {
		messageToGame = message;
		isMessageToGame = true;
	}

	@Override
	public boolean hasWon() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
