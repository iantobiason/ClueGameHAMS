package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private String[] suggestion;
	private Set<Card> seenRooms;
	private Set<Card> seenPersons;
	private Set<Card> seenWeapons;
	private Board board;

	public Card [] cardsInHand;


	public Player() {
		suggestion = new String[3];
	}
	public Player(Board board) {
		suggestion = new String[3];
		this.board = board;
	}
	public Player(String playerName, String color, String row, String column) {
		this.playerName = playerName;
		this.row = Integer.parseInt(row);
		this.column = Integer.parseInt(column);
		this.color = Color.getColor(color);
		cardsInHand = new Card[3];
		suggestion = new String[3];


	}
	public int getRow() {
		return row;
	}
	public String getName(){
		return playerName;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setHand(Card[] cards){
		cardsInHand = cards;
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

	public char makeRoomSuggestion() {
		return board.getCellAt(row, column).getInitial();

	}
	public Card disproveSuggestion(Set<Card> suggestion){
		ArrayList<Card> cardsToReturn = new ArrayList<Card>();
		for(int i = 0; i < cardsInHand.length; i++){
			for(Card c : suggestion){
				if(cardsInHand[i].getName().equals(c.getName())){
					cardsToReturn.add(c);
				}
			}
		}
		if(cardsToReturn.size() == 1){
			return cardsToReturn.get(0);
		} else if(cardsToReturn.size() >= 1){
			Random rn = new Random();
			return cardsToReturn.get(rn.nextInt(cardsToReturn.size()));
		} else{
			return null;
		}
	}
	public Card makeWeaponSuggestion(Set<Card> seenWeaponCards) {
		Set<Card> maybeMurderWeapons = new HashSet<Card>();
		for(Card o : board.getWeaponCards()) {
			maybeMurderWeapons.add(o);
		}
		Card cardToRemove = null;
		for(Card o: seenWeaponCards) {
			for(Card j: maybeMurderWeapons) {
				if(o.getName().equals(j.getName())) {
					cardToRemove = j;
				}
			}
			maybeMurderWeapons.remove(cardToRemove);
		}
		if(seenWeaponCards.size() == 5) {
			for(Card o: maybeMurderWeapons) {
				return o;
			}
		}
		else {
			int size = maybeMurderWeapons.size();
			int item = new Random().nextInt(size);
			int i = 0;
			for(Card o: maybeMurderWeapons) {
				if (i == item) {
					return o;
				}
				i = i + 1;
			}
		}
		System.out.println("Error, didn't make weapon suggestion");
		return null;
	}
	public Card makePersonSuggestion(Set<Card> seenPersonCards) {
		Set<Card> maybeMurderer = new HashSet<Card>();
		maybeMurderer = board.getPlayerCards();
		Card cardToRemove = null;
		for(Card o: seenPersonCards) {
			for(Card j: maybeMurderer) {
				if(o.getName().equals(j.getName())) {
					cardToRemove = j;
				}
			}
			maybeMurderer.remove(cardToRemove);
		}
		if(seenPersonCards.size() == 5) {
			for(Card o: maybeMurderer) {
				return o;
			}
		}
		else {
			int size = maybeMurderer.size();
			int item = new Random().nextInt(size);
			int i = 0;
			for(Card o: maybeMurderer) {
				if (i == item) {
					return o;
				}
				i = i + 1;
			}
		}
		System.out.println("Error, didn't make person suggestion");
		return null;
	}
	public void makeSuggestion() {
		makeRoomSuggestion();
		makePersonSuggestion(seenPersons);
		makeWeaponSuggestion(seenWeapons);

	}

}
