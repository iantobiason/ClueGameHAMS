package tests;

/*
 * This program tests that config files are loaded properly.
 */

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class InitTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;

	// NOTE: I made Board static because I only want to set it up one 
	// time (using @BeforeClass), no need to do setup before each test.
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "Legend.txt");		
		// Initialize will load BOTH config files
		board.initialize();
	}
	@Test
	public void testRooms() {
		// Get the map of initial => room
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms
		// from the hash, including the first and last in the file and a few
		// others
		assertEquals("Chancelor Office", legend.get('C'));
		assertEquals("Senate", legend.get('S'));
		assertEquals("Jedi Temple", legend.get('J'));
		assertEquals("Jedi Council Tower", legend.get('T'));
		assertEquals("Corusant Underworld", legend.get('U'));
		assertEquals("Military Base", legend.get('M'));
		assertEquals("The Works", legend.get('O'));
		assertEquals("Padme Amadala Residence", legend.get('P'));
		assertEquals("Represantitive Quarters", legend.get('R'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		// RR rep quarters right
		BoardCell room = board.getCellAt(15, 13);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		// SD senate down
		room = board.getCellAt(14, 18);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		// CL chancelors left
		room = board.getCellAt(16, 19);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		//PU padame up
		room = board.getCellAt(16, 8);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// False Doorways
		// X @ 10,10 
		room = board.getCellAt(10, 10);
		assertFalse(room.isDoorway());	
		// J @ 18, 2
		BoardCell cell = board.getCellAt(18, 2);
		assertFalse(cell.isDoorway());		

	}
	
	// Test for 19 doors in total
		@Test
		public void testNumberOfDoorways() 
		{
			int numDoors = 0;
			for (int row=0; row<board.getNumRows(); row++)
				for (int col=0; col<board.getNumColumns(); col++) {
					BoardCell cell = board.getCellAt(row, col);
					System.out.println(cell.getInitial());
					if (cell.isDoorway()){
						numDoors++;
					}
				}
			Assert.assertEquals(19, numDoors);
		}

		// Test a few room cells to ensure the room initial is correct.
		@Test
		public void testRoomInitials() {
			// Test Multiple rooms for the correct label at the cell on the board

			assertEquals('C', board.getCellAt(20, 21).getInitial());
			assertEquals('S', board.getCellAt(9, 19).getInitial());
			assertEquals('J', board.getCellAt(17, 0).getInitial());
			assertEquals('T', board.getCellAt(11, 3).getInitial());
			assertEquals('U', board.getCellAt(4, 2).getInitial());
			assertEquals('M', board.getCellAt(1, 18).getInitial());
			assertEquals('O', board.getCellAt(2, 7).getInitial());
			assertEquals('P', board.getCellAt(18, 8).getInitial());
			assertEquals('R', board.getCellAt(18, 14).getInitial());

			// Test Airspeeder Access
			assertEquals('W', board.getCellAt(9, 5).getInitial());
			// Not testing a closet because we dont have one.
		}
		

	}
