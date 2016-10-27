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
		
		System.out.println(board.getWeaponCards().size());
		seenWeaponCards.add(new Card("Nike Sweatpants", CardType.WEAPON));
		seenWeaponCards.add(new Card("Pickle Jar", CardType.WEAPON));
		seenWeaponCards.add(new Card("Prosthetic Leg", CardType.WEAPON));
		seenWeaponCards.add(new Card("Katana", CardType.WEAPON));
		seenWeaponCards.add(new Card("Mommy's Bedroom helper", CardType.WEAPON));
		board.setSolution("Senate", "WhiteNight", "Political Influence");
		assertEquals(board.getSolution().get("Weapon"),testPlayer.makeWeaponSuggestion(seenWeaponCards).getName());
		System.out.println(board.getWeaponCards().size());

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
		System.out.println(testPlayer.makeWeaponSuggestion(seenWeaponCards).getName());
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
}
	
