package scum;

public class Player implements PlayerInterface{

	/**
	 * The player's unique ID.
	 */
	private int id;
	
	private Hand hand;
	
	public Player(int id) {
		this.id = id;
		this.hand = new Hand();
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
	public void printHand() {
		hand.sort();
		System.out.println("Player " + id + "'s hand:");
		hand.print();
		
	}

	@Override
	public boolean hasWon() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
