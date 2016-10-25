package clueGame;

import java.awt.Color;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	public Card [] cardsInHand;
	public Player() {
		
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

}
