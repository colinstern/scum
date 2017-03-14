package scum;

public class Player implements PlayerInterface{

	/**
	 * The player's unique ID.
	 */
	private int id;
	
	private Hand hand;
	
	private boolean passFlag;
	
	public Player(int id) {
		this.id = id;
		this.hand = new Hand();
		this.passFlag = false;
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

	@Override
	public boolean hasWon() {
		// TODO Auto-generated method stub
		return false;
	}	
	

}
