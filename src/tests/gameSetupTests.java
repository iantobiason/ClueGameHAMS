package tests;
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
		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt");
		board.setPlayerAndWeaponConfigFiles("PlayerConfig.txt", "WeaponsConfig.txt");
		board.initialize();
	}
	@Test
	public void testLoadingThePeople(){
		Map<String, Player> playerLegend = board.getPeople();
		assertEquals(6, playerLegend.size());
		assertTrue(playerLegend.containsKey("King Crusader"));
		assertTrue(playerLegend.containsKey("PartyGirl Primrose"));
		
		Set<Card> playerCardLegend = board.getPlayerCards();
		assertEquals(6, playerCardLegend.size());
	}
}
