package clueGame;

public class BadConfigFormatException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BadConfigFormatException() {
		super("Config file has illegal formatting. Symbols must be 1 character");
	}
	public BadConfigFormatException(String type) {
		
	}
}
