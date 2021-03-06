package tests;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;

public class gameSetupTests {
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
	public void testLoadingThePeople(){
		ArrayList<Player> playerLegend = board.getPeople();
		assertEquals(6, playerLegend.size());
		assertEquals("King Crusader",playerLegend.get(0).getName());
		assertEquals("PartyGirl Primrose",playerLegend.get(5).getName());
	}
	@Test
	public void testLoadAndCreationOfDeck() {
		Set<Card> playerCardLegend = board.getPlayerCards();
		assertEquals(6, playerCardLegend.size());
		
		Set<Card> weaponCardLegend = board.getWeaponCards();
		assertEquals(6, weaponCardLegend.size());
		
		Set<Card> roomCardLegend = board.getRoomCards();
		assertEquals(9, roomCardLegend.size());
		
		ArrayList<Card> deck = board.getDeck();
		assertEquals(21, deck.size());
		
	}
	
	@Test
	public void testDealingHands() {
		//Checks for solution cards
		Map<String, String> solution = board.getSolution();
		assertNotNull(solution.get("Room"));
		assertNotNull(solution.get("Person"));
		assertNotNull(solution.get("Weapon"));
		
		//Checks that each player has 3 cards
		ArrayList<Player> players = board.getPeople();
		assertEquals(3, players.get(0).getCardsInHand().length);
		assertEquals(3, players.get(1).getCardsInHand().length);
		assertEquals(3, players.get(2).getCardsInHand().length);
		assertEquals(3, players.get(3).getCardsInHand().length);
		assertEquals(3, players.get(4).getCardsInHand().length);
		assertEquals(3, players.get(5).getCardsInHand().length);
	}
	
}
