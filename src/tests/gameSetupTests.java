package tests;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;

public class gameSetupTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt");		
		board.initialize();
	}
	@Test
	public void testLoadingThePeople(){
		Map<String, Player> playerLegend = board.getPeople();
		assertEquals(6, playerLegend.size());
		assertEquals("King Crusader", playerLegend.containsKey("King Crusader"));
		assertEquals("PartyGirl Primrose", playerLegend.containsKey("PartyGirl Primrose"));
	}
}
