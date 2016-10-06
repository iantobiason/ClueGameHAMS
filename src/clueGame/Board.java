package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import tests.InitTests;

public class Board {
	
	private int numRows = InitTests.NUM_ROWS;
	private int numColumns = InitTests.NUM_COLUMNS;
	public static final int MAX_BOARD_SIZE = 50;
	
	private static Board theInstance = new Board();
	
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	
	private Map<Character, String> rooms = new HashMap<Character, String>();
	
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	
	private String boardConfigFile;
	
	private String roomConfigFile;
	
	private Board() {}
	
	public static Board getInstance(){
		return theInstance;
	}
	
	public void initialize() {
		try {
			loadRoomFile();
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadRoomFile() throws BadConfigFormatException{
		Scanner legendRead = null;
		try {
			legendRead = new Scanner(new FileReader(roomConfigFile));
		}
		catch (FileNotFoundException e){
			e.getMessage();
		}
		String name, type;
		char symbol;
		//Read in each line of the Legend file, create entry in rooms for each
		while (legendRead.hasNext()){
			String line = legendRead.nextLine();
			String[] fields = line.split(", ");
			String tempSymbol = fields[0];
			if (tempSymbol.length() != 1){
				throw new BadConfigFormatException();	
			}
			else{
				symbol = tempSymbol.charAt(0);
			}
			name = fields[1];
			// TODO: type is being ignored for now, will be used later in development
			type = fields[2];
			if (!(type.equals("Other")) && !(type.equals("Card"))){
				throw new BadConfigFormatException(type);
			}
			else {
				rooms.put(symbol, name);
			}
		}
	}
	
	public void loadBoardConfig() throws BadConfigFormatException {
		Scanner boardRead = null;
		
		try {
			boardRead = new Scanner(new FileReader(boardConfigFile));
		}
		catch (FileNotFoundException e){
			e.getMessage();
		}
		String room;
		for (int i = 0; i < InitTests.NUM_ROWS; i++){
			String line = boardRead.nextLine();
			String[] fields = line.split(",");
			System.out.println(fields.length);
			for (int j = 0; j < InitTests.NUM_COLUMNS; j++){
				room = fields[j];
				if (room.length() != 1 && room.length() != 2){
					System.out.println("here-1");
					throw new BadConfigFormatException(room);
				}
				else if (room.length() == 1){
					char t = room.charAt(0);
					if (rooms.containsKey(t)){
						BoardCell temp = new BoardCell(i, j, t);
						board[i][j] = temp;
						//board[i][j] = new BoardCell(i, j, t);
					}
					else {
						System.out.println("here0");
						throw new BadConfigFormatException();
					}
				}
				// N will be used to display room name later, ignoring for now
				else if (room.charAt(1) == 'N'){
					char t = room.charAt(0);
					if (rooms.containsKey(t)){
						BoardCell temp = new BoardCell(i, j, t);
						board[i][j] = temp;
					}
					else {
						System.out.println("here1");
						throw new BadConfigFormatException();
					}
				}
				else if (room.charAt(1) == 'U' || room.charAt(1) == 'L' || room.charAt(1) == 'R' || room.charAt(1) == 'D'){
					char roomtype = room.charAt(0);
					char dir = room.charAt(1);
					DoorDirection direction = null;
					if (dir == 'U') {direction = DoorDirection.UP;}
					if (dir == 'D') {direction = DoorDirection.DOWN;}
					if (dir == 'L') {direction = DoorDirection.LEFT;}
					if (dir == 'R') {direction = DoorDirection.RIGHT;}
					if (rooms.containsKey(roomtype)){
						BoardCell temp = new BoardCell(i, j, roomtype, direction);
						board[i][j] = temp;
						//board[i][j] = new BoardCell(i, j, roomtype, dir);
					}
					else {
						System.out.println("here2");
						throw new BadConfigFormatException();
					}
				}
				else {
					System.out.println("here3");
					throw new BadConfigFormatException();
				}
			}
		}
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
