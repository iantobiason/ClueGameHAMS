package tests;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class gameActionTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "Legend.txt");
		board.setPlayerAndWeaponConfigFiles("PlayerConfig.txt", "WeaponsConfig.txt");
		board.initialize();
	}
	@Test
	public void testComputerMovement(){
		ComputerPlayer computerTest = new ComputerPlayer();
		//Chose target when door is within targets and not visited
		board.calcTargets(13, 8, 3);
		Set<BoardCell> targets = board.getTargets();
		BoardCell selectedLocation = computerTest.pickLocation(targets);
		assertTrue(selectedLocation.equals(board.getCellAt(16, 8)));
		
		//Chose target when door is within targets and recently visited
		computerTest = new ComputerPlayer();
		computerTest.setRecent('P');
		board.calcTargets(13, 8, 3);
		targets = board.getTargets();
		selectedLocation = computerTest.pickLocation(targets);
		assertTrue(targets.contains(selectedLocation));
		assertTrue(targets.contains(board.getCellAt(16, 8))); //Checks if door is within the returned targets
		
		//Chose target when no doors are within range
		board.calcTargets(14, 8, 1);
		targets = board.getTargets();
		selectedLocation = computerTest.pickLocation(targets);
		assertTrue(targets.contains(selectedLocation));
	}
	
	@Test
	public void testMakeAccusation(){
		Player testPlayer = new Player(board);
		board.setSolution("Senate", "WhiteNight", "Katana");
		//Correct Accusation
		assertTrue(testPlayer.makeAccusation("Senate", "WhiteNight", "Katana", board));
		//Room incorrect
		assertFalse(testPlayer.makeAccusation("The Works", "WhiteNight", "Katana", board));
		//Person incorrect
		assertFalse(testPlayer.makeAccusation("Senate", "NeckBeard Nikoli", "Katana", board));
		//Weapon incorrect
		assertFalse(testPlayer.makeAccusation("Senate", "WhiteNight", "Pickle Jar", board));
	}
	
	@Test
	public void computerDisproveSuggestion(){
		ComputerPlayer comp = new ComputerPlayer();
		Card[] hand = {new Card("Senate", CardType.ROOM), new Card("WhiteNight", CardType.PERSON),
		new Card("Katana", CardType.WEAPON)};
		comp.setHand(hand);
		
		//Computer only one card matches suggestions
		//makes suggestion with only one card of the players hand
		Set<Card> suggestion = new HashSet<Card>();
		suggestion.add(hand[0]);
		suggestion.add(new Card("NeckBeard Nikoli", CardType.PERSON));
		suggestion.add(new Card("Pickle Jar", CardType.WEAPON));
		
		assertEquals(hand[0], comp.disproveSuggestion(suggestion));
		
		
		//Computer multiples cards matches suggestion
		//Makes suggest with all cards in players hand
		suggestion = new HashSet<Card>();
		for(int i = 0; i < hand.length; i++){
			suggestion.add(hand[i]);
		}
		
		suggestion = new HashSet<Card>();
		suggestion.add(hand[0]);
		suggestion.add(hand[1]);
		suggestion.add(hand[2]);
		
		assertTrue(suggestion.contains(comp.disproveSuggestion(suggestion)));
		
		//Computer multiples cards matches suggestion
		//Makes suggest with all cards in players hand
		
		suggestion = new HashSet<Card>();
		suggestion.add(new Card("The Works", CardType.ROOM));
		suggestion.add(new Card("NeckBeard Nikoli", CardType.PERSON));
		suggestion.add(new Card("Pickle Jar", CardType.WEAPON));
		assertNull(comp.disproveSuggestion(suggestion));
		
	}

	
	@Test
	public void testSuggestion() {
		Set<Card> seenRoomCards = new HashSet<Card>();
		Set<Card> seenPersonCards = new HashSet<Card>();
		Set<Card> seenWeaponCards = new HashSet<Card>();
		
		Set<Card> unseenCards = new HashSet<Card>();
		
		BoardCell room = board.getCellAt(0, 0);
		
		Player testPlayer = new Player(board);
		
		testPlayer.setRow(0);
		testPlayer.setColumn(0);
		assertEquals(board.getCellAt(testPlayer.getRow(), testPlayer.getColumn()).getInitial(),testPlayer.makeRoomSuggestion());
		
		seenWeaponCards.add(new Card("Nike Sweatpants", CardType.WEAPON));
		seenWeaponCards.add(new Card("Pickle Jar", CardType.WEAPON));
		seenWeaponCards.add(new Card("Prosthetic Leg", CardType.WEAPON));
		seenWeaponCards.add(new Card("Katana", CardType.WEAPON));
		seenWeaponCards.add(new Card("Mommy's Bedroom helper", CardType.WEAPON));
		board.setSolution("Senate", "WhiteNight", "Political Influence");
		assertEquals(board.getSolution().get("Weapon"),testPlayer.makeWeaponSuggestion(seenWeaponCards).getName());

		seenPersonCards.add(new Card("King Crusader", CardType.PERSON));
		seenPersonCards.add(new Card("Mountian Man", CardType.PERSON));
		seenPersonCards.add(new Card("Sailor Samson", CardType.PERSON));
		seenPersonCards.add(new Card("NeckBeard Nikoli", CardType.PERSON));
		seenPersonCards.add(new Card("PartyGirl Primrose", CardType.PERSON));
		board.setSolution("Senate", "WhiteNight", "Political Influence");
		assertEquals(board.getSolution().get("Person"),testPlayer.makePersonSuggestion(seenPersonCards).getName());
		
		
		seenWeaponCards.clear();
		seenWeaponCards.add(new Card("Nike Sweatpants", CardType.WEAPON));
		seenWeaponCards.add(new Card("Pickle Jar", CardType.WEAPON));
		seenWeaponCards.add(new Card("Political Influence", CardType.WEAPON));
		unseenCards.add(new Card("Prosthetic Leg", CardType.WEAPON));
		unseenCards.add(new Card("Katana", CardType.WEAPON));
		unseenCards.add(new Card("Mommy's Bedroom helper", CardType.WEAPON));
		assertTrue(unseenCards.contains(testPlayer.makeWeaponSuggestion(seenWeaponCards)));
		
		
		seenPersonCards.clear();
		seenPersonCards.add(new Card("King Crusader", CardType.PERSON));
		seenPersonCards.add(new Card("Mountian Man", CardType.PERSON));
		unseenCards.clear();
		unseenCards.add(new Card("Sailor Samson", CardType.PERSON));
		unseenCards.add(new Card("NeckBeard Nikoli", CardType.PERSON));
		unseenCards.add(new Card("PartyGirl Primrose", CardType.PERSON));
		unseenCards.add(new Card("WhiteNight", CardType.PERSON));
		board.setSolution("Senate", "WhiteNight", "Political Influence");
		assertTrue(unseenCards.contains(testPlayer.makePersonSuggestion(seenPersonCards)));
	}
	
	@Test
	public void testHandleSuggestion(){
		ArrayList<Player> players = board.getPeople();
		
		Player tempPlayer = players.get(0);
		Card[] hand = {new Card("Chancelor Office", CardType.ROOM), new Card("King Crusader", CardType.PERSON),
		new Card("Nike Sweatpants", CardType.WEAPON)};
		tempPlayer.setHand(hand);
		players.set(0, tempPlayer);
		
		tempPlayer = players.get(1);
		Card[] hand1 = new Card[3];
		hand1[0] = new Card("PartyGirl Primrose", CardType.PERSON);
		hand1[1] = new Card("Corusant Underworld", CardType.ROOM);
		hand1[2] = new Card("Katana", CardType.WEAPON);
		tempPlayer.setHand(hand1);
		players.set(1, tempPlayer);
		
		tempPlayer = players.get(2);
		Card[] hand2 = new Card[3];
		hand2[0] = new Card("Mountian Man", CardType.PERSON);
		hand2[1] = new Card("Padme Amadala Residence", CardType.ROOM);
		hand2[2] = new Card("The Works", CardType.ROOM);
		tempPlayer.setHand(hand2);
		players.set(2, tempPlayer);
		
		tempPlayer = players.get(3);
		Card[] hand3 = new Card[3];
		hand3[0] = new Card("Jedi Council Tower", CardType.ROOM);
		hand3[1] = new Card("WhiteNight", CardType.PERSON);
		hand3[2] = new Card("Sailor Samson", CardType.PERSON);
		tempPlayer.setHand(hand3);
		players.set(3, tempPlayer);
		
		tempPlayer = players.get(4);
		Card[] hand4 = new Card[3];
		hand4[0] = new Card("Political Influence", CardType.WEAPON);
		hand4[1] = new Card("Mommy's Bedroom helper", CardType.WEAPON);
		hand4[2] = new Card("Represantitive Quarters", CardType.ROOM);
		tempPlayer.setHand(hand4);
		players.set(4, tempPlayer);
		
		tempPlayer = players.get(5);
		Card[] hand5 = new Card[3];
		hand5[0] = new Card("Jedi Temple", CardType.ROOM);
		hand5[1] = new Card("Senate", CardType.ROOM);
		hand5[2] = new Card("Pickle Jar", CardType.WEAPON);
		tempPlayer.setHand(hand5);
		players.set(5, tempPlayer);
		
		//Suggestion no one can disprove 
		//Accusor: Comp1 Cards: "Military Base", "Prosthetic Leg", "NeckBeard Nikoli"
		Set<Card> suggestion = new HashSet<Card>();
		suggestion.add(new Card("Military Base", CardType.ROOM));
		suggestion.add(new Card("Prosthetic Leg", CardType.WEAPON));
		suggestion.add(new Card("NeckBeard Nikoli", CardType.PERSON));
		assertNull(board.handleSuggestion(1, suggestion));
		
		//Suggestion only accusing player can disprove
		//Accusor: Comp1 Cards: "Military Base", "Prosthetic Leg", "PartyGirl Primrose"
		suggestion = new HashSet<Card>();
		suggestion.add(new Card("Military Base", CardType.ROOM));
		suggestion.add(new Card("Prosthetic Leg", CardType.WEAPON));
		suggestion.add(new Card("PartyGirl Primrose", CardType.PERSON));
		assertNull(board.handleSuggestion(1, suggestion));
		
		//Suggestion only human can disprove
		//Accusor: Comp1 Cards: "Chancelor Office", "Prosthetic Leg", "NeckBeard Nikoli"
		suggestion = new HashSet<Card>();
		suggestion.add(new Card("Chancelor Office", CardType.ROOM));
		suggestion.add(new Card("Prosthetic Leg", CardType.WEAPON));
		suggestion.add(new Card("NeckBeard Nikoli", CardType.PERSON));
		assertEquals("Chancelor Office", board.handleSuggestion(1, suggestion).getName());
		
		//Suggestion only human can disprove but human is accuser
		//Accusor: Human Cards: "Chancelor Office", "Prosthetic Leg", "NeckBeard Nikoli"
		suggestion = new HashSet<Card>();
		suggestion.add(new Card("Chancelor Office", CardType.ROOM));
		suggestion.add(new Card("Prosthetic Leg", CardType.WEAPON));
		suggestion.add(new Card("NeckBeard Nikoli", CardType.PERSON));
		assertNull(board.handleSuggestion(0, suggestion));
		
		//Suggestion that two players can disprove but correct player does
		//Accusor: Comp1 Cards: "Jedi Council Tower", "Prosthetic Leg", "Mountian Man"
		suggestion = new HashSet<Card>();
		suggestion.add(new Card("Jedi Council Tower", CardType.ROOM));
		suggestion.add(new Card("Prosthetic Leg", CardType.WEAPON));
		suggestion.add(new Card("Mountian Man", CardType.PERSON));
		assertEquals("Mountian Man", board.handleSuggestion(1, suggestion).getName());
		
		//Suggestion that human and another player can disprove
		//Accusor: Comp1 Cards: "Military Base", "Nike Sweatpants", "Sailor Samson"
		suggestion = new HashSet<Card>();
		suggestion.add(new Card("Military Base", CardType.ROOM));
		suggestion.add(new Card("Nike Sweatpants", CardType.WEAPON));
		suggestion.add(new Card("Sailor Samson", CardType.PERSON));
		assertEquals("Sailor Samson", board.handleSuggestion(1, suggestion).getName());
	}
}
	
