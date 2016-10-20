package clueGame;

import java.awt.Color;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	public Player() {
		
	}
	public Player(String playerName, String color, String row, String column) {
		System.out.println(playerName + row + column + color );
		this.playerName = playerName;
		this.row = Integer.parseInt(row);
		this.column = Integer.parseInt(column);
		this.color = Color.getColor(color);
		
		
	}
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}

}
