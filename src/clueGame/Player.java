package clueGame;

import java.awt.Color;
import java.util.Map;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private String[] suggestion;
	public Card [] cardsInHand;
	
	public Player() {
		suggestion = new String[3];
	}
	public Player(String playerName, String color, String row, String column) {
		this.playerName = playerName;
		this.row = Integer.parseInt(row);
		this.column = Integer.parseInt(column);
		this.color = Color.getColor(color);
		cardsInHand = new Card[3];
		
		
	}
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public Card [] getCardsInHand() {
		return cardsInHand;
	}
	public boolean makeAccusation(String room, String person, String weapon, Board board) {
		if(board.getSolution().get("Room").equals(room) && board.getSolution().get("Person").equals(person) && board.getSolution().get("Weapon").equals(weapon)){
			return true;
		}
		return false;
	}

}
