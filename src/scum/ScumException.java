package scum;

public class ScumException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9094916525458322239L;

	/**
	 * A general exception for Scum.
	 */
	public ScumException(String message) {
		super(message);
	}
	
	public ScumException() {
		super("Scum has run into an error.");
	}
}
