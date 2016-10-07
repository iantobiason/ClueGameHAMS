package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt");		
		board.initialize();
	}

	// Tests for adjacencies
	@Test
	public void testAdjacencies()
	{
		// THESE TESTS ARE WHITE
		// Test cell with only walkways as adjacent
		Set<BoardCell> testList = board.getAdjList(14, 5);
		assertEquals(4, testList.size());
		
		// THESE TESTS ARE ORANGE
		// Test location on left edge
		testList = board.getAdjList(9, 0);
		assertEquals(1, testList.size());
		// Test location on top edge
		testList = board.getAdjList(0, 13);
		assertEquals(2, testList.size());
		// Test location on right edge
		testList = board.getAdjList(15, 22);
		assertEquals(1, testList.size());
		// Test location on bottom edge
		testList = board.getAdjList(21, 17);
		assertEquals(2, testList.size());
		
		// THESE TESTS ARE PURPLE
		// Test cell adjacent to room, not doorway
		testList = board.getAdjList(8, 16);
		assertEquals(3, testList.size());
		// Test cell adjacent to room, not doorway
		testList = board.getAdjList(0, 4);
		assertEquals(1, testList.size());
		
		// THESE TESTS ARE GREEN
		// Test cell next to "LEFT" doorway
		testList = board.getAdjList(16, 18);
		assertEquals(4, testList.size());
		// Test cell next to "UP" doorway and "DOWN" doorway
		testList = board.getAdjList(14, 0);
		assertEquals(3, testList.size());
		// Test cell next to "RIGHT" doorway
		testList = board.getAdjList(15, 14);
		assertEquals(4, testList.size());
		// Test cell next to "DOWN" doorway
		testList = board.getAdjList(5, 17);
		assertEquals(4, testList.size());
		
		// THESE TESTS ARE LIGHT PURPLE
		// Test cell for doorway
		testList = board.getAdjList(7, 18);
		assertEquals(1, testList.size());
		// Test cell for doorway
		testList = board.getAdjList(6, 3);
		assertEquals(1, testList.size());
	}
	
	// Tests of all the targets
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargets() {
		// Targets along walkways at distance 1
		board.calcTargets(15, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(15, 6)));
		assertTrue(targets.contains(board.getCellAt(14, 7)));
		assertTrue(targets.contains(board.getCellAt(15, 8)));
		
		// Targets along walkways at distance 2
		board.calcTargets(6, 6, 2);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 5)));
		assertTrue(targets.contains(board.getCellAt(6, 4)));	
		assertTrue(targets.contains(board.getCellAt(7, 5)));
		assertTrue(targets.contains(board.getCellAt(8, 6)));
		assertTrue(targets.contains(board.getCellAt(6, 8)));
		assertTrue(targets.contains(board.getCellAt(5, 7)));
		
		// Targets along walkways at distance 3
		board.calcTargets(2, 14, 3);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(0, 13)));
		assertTrue(targets.contains(board.getCellAt(1, 14)));	
		assertTrue(targets.contains(board.getCellAt(2, 13)));
		assertTrue(targets.contains(board.getCellAt(3, 14)));
		assertTrue(targets.contains(board.getCellAt(4, 13)));
		assertTrue(targets.contains(board.getCellAt(5, 14)));
		
		// Targets along walkways at distance 5
		board.calcTargets(11, 16, 5);
		targets= board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 16)));
		assertTrue(targets.contains(board.getCellAt(8, 16)));	
		assertTrue(targets.contains(board.getCellAt(10, 16)));
		assertTrue(targets.contains(board.getCellAt(12, 16)));
		assertTrue(targets.contains(board.getCellAt(14, 16)));
		assertTrue(targets.contains(board.getCellAt(16, 16)));
		assertTrue(targets.contains(board.getCellAt(7, 15)));
		assertTrue(targets.contains(board.getCellAt(9, 15)));
		assertTrue(targets.contains(board.getCellAt(11, 15)));
		assertTrue(targets.contains(board.getCellAt(13, 15)));
		assertTrue(targets.contains(board.getCellAt(15, 15)));
		assertTrue(targets.contains(board.getCellAt(13, 13)));
		assertTrue(targets.contains(board.getCellAt(14, 14)));
		
		
		//
		// Target that allows the user to enter a room, door at (16, 3)
		board.calcTargets(21, 4, 6);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 5)));
		assertTrue(targets.contains(board.getCellAt(18, 5)));	
		assertTrue(targets.contains(board.getCellAt(16, 5)));
		assertTrue(targets.contains(board.getCellAt(15, 4)));
		assertTrue(targets.contains(board.getCellAt(17, 4)));
		assertTrue(targets.contains(board.getCellAt(19, 4)));
		assertTrue(targets.contains(board.getCellAt(21, 4)));
		assertTrue(targets.contains(board.getCellAt(16, 3)));
		
		// Target that allows the user to enter a room, doors at (21, 10) and (21, 12)
		board.calcTargets(21, 11, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(21, 10)));
		assertTrue(targets.contains(board.getCellAt(21, 12)));	
		assertTrue(targets.contains(board.getCellAt(20, 11)));	
		
		// Targets calculated when leaving a room, move 3 spaces
		board.calcTargets(4, 9, 3);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 8)));
		assertTrue(targets.contains(board.getCellAt(4, 10)));
		assertTrue(targets.contains(board.getCellAt(5, 7)));
		assertTrue(targets.contains(board.getCellAt(5, 11)));
		assertTrue(targets.contains(board.getCellAt(6, 8)));
		assertTrue(targets.contains(board.getCellAt(6, 10)));
		
		// Targets calculated when leaving a room, move 1
		board.calcTargets(4, 16, 1);
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 16)));	
	}

}
