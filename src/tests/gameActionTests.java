package tests;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
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
	
}
