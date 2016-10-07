package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import experiment.IntBoard;
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
	
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	
	private Set<BoardCell> adj= new HashSet<BoardCell>();
	
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
	
	public void calcTargets(int row, int column, int pathLength){
		visited.add(board[row][column]);
		targets.clear();
		if (pathLength == 1){
			for (BoardCell s : getAdjList(row, column)){
				targets.add(s);
			}
		}
		else{
			for (BoardCell s : getAdjList(row, column)){
				findAllTargets(s, pathLength-1);
			}
		}
	}
	
	private void findAllTargets(BoardCell startCell, int length){
		Set<BoardCell> adjacents = getAdjList(startCell.getRow(), startCell.getColumn());
		for (BoardCell s : adjacents){
			if (!(visited.contains(s))){
				visited.add(s);
				if (length <= 1){
					targets.add(s);
				}
				else {
					findAllTargets(s, length-1);
				}
				visited.remove(s);
			}
		}
	}
	
	//Returns the adjacency list for one cell, type is Set<BoardCell>
		public void calcAdjacencies(BoardCell cell, IntBoard board){
			//Set<BoardCell> adj = new HashSet<BoardCell>(4);
			int x = cell.getRow();
			int y = cell.getColumn();	
			//return adj;
		}
		
	private void bufferCalcAdj(BoardCell cell){
		adj.clear();
		int x = cell.getRow();
		int y = cell.getColumn();
		if (x == 0){
			if (y == 0){
				adj.add(board[x][y+1]);
				adj.add(board.getCell(x+1, y));
			}
			else if (y == 3){
				adj.add(board.getCell(x+1, y));
				adj.add(board.getCell(x, y-1));
			}
			else {
				adj.add(board.getCell(x, y+1));
				adj.add(board.getCell(x+1, y));
				adj.add(board.getCell(x, y-1));
			}
		}
		else if (x == 3){
			if (y == 0){
				adj.add(board.getCell(x, y+1));
				adj.add(board.getCell(x-1, y));
			}
			else if (y == 3){
				adj.add(board.getCell(x, y-1));
				adj.add(board.getCell(x-1, y));
			}
			else {
				adj.add(board.getCell(x, y+1));
				adj.add(board.getCell(x, y-1));
				adj.add(board.getCell(x-1, y));
			}
		}
		else {
			if (y == 0){
				adj.add(board.getCell(x, y+1));
				adj.add(board.getCell(x+1, y));
				adj.add(board.getCell(x-1, y));
			}
			else if (y == 3){
				adj.add(board.getCell(x+1, y));
				adj.add(board.getCell(x, y-1));
				adj.add(board.getCell(x-1, y));
			}
			else {
				adj.add(board.getCell(x, y+1));
				adj.add(board.getCell(x+1, y));
				adj.add(board.getCell(x, y-1));
				adj.add(board.getCell(x-1, y));
			}
		}
		
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
	}
		
	public Set<BoardCell> getAdjList (int row, int column){
		return adjMatrix.get(new BoardCell(row, column)); 
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
