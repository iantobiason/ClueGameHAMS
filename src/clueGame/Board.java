package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
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
	
	public void initialize() throws FileNotFoundException{
		Scanner legendRead = new Scanner(new FileReader(roomConfigFile));
		legendRead.useDelimiter(", ");
		String symbol, name, type;
		while (legendRead.hasNext()){
			symbol = legendRead.next();
			name = legendRead.next();
			type = legendRead.next();
		}
	}
	
	public void loadRoomFile(){
		
	}
	
	public void loadBoardConfig(){
		
	}
	
	public void calcAdjacencies(){
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength){
		
	}
	
	public void setConfigFiles(String input, String legend){
		boardConfigFile = input;
		roomConfigFile = legend;
	}
	
	public Map<Character, String> getLegend(){
		return rooms;
	}
	public int getNumRows(){
		return numRows;
	}
	
	public int getNumColumns(){
		return numColumns;
	}
	
	public BoardCell getCellAt(int r, int c){
		return board[r][c];
	}
}
