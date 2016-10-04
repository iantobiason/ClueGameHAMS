package clueGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
	
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 50;
	
	private static Board theInstance = new Board();
	
	private Board() {}
	
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	
	private Map<Character, String> rooms = new HashMap<Character, String>();
	
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	
	private String boardConfigFile;
	
	private String roomConfigFile;
	
	public static Board getInstance(){
		return theInstance;
	}
	
	public void initialize(){
		
	}
	
	public void loadRoomFile(){
		
	}
	
	public void loadBoardConfig(){
		
	}
	
	public void calcAdjacencies(){
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength){
		
	}
	/*
	private Board(int numRows, int numColumns, int MAX_BOARD_SIZE) {
		super();
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.MAX_BOARD_SIZE = MAX_BOARD_SIZE;
	}
	*/
	
}
