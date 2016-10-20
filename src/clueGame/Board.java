package clueGame;

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
	//public static final char WALKWAY_CHAR = 'W'; // Initial for walkway BoardCell's, use 'A' for our board

	private static Board theInstance = new Board();

	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];

	private Map<Character, String> rooms = new HashMap<Character, String>();

	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();

	private Set<BoardCell> targets = new HashSet<BoardCell>();

	private Set<BoardCell> visited = new HashSet<BoardCell>();

	private String boardConfigFile;

	private String roomConfigFile;
	
	private Set<Card> weapons;
	
	private Set<Card> people;
	
	private Map<String,Player> computerPlayers;

	private Board() {}

	public static Board getInstance(){
		return theInstance;
	}

	public void initialize() {
		try {
			loadRoomFile();
			loadBoardConfig();
			loadPeople();
			loadWeapons();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		calcAdjacencies();
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
			//System.out.println(fields.length);
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

	public void loadPeople(){
		
	}
	public void loadWeapons(){

	}

	public void selectAnswer(){

	}

	public Card handleSugguestion(){
		return null;
	}

	public boolean checkAccusation(){
		return false;
	}

	public void calcTargets(int row, int column, int pathLength){
		visited.add(board[row][column]);
		targets.clear();
		//for (BoardCell b : getAdjList(row, column)){
		//	System.out.println(b.toString());
		//}
		if (pathLength == 1){
			for (BoardCell s : getAdjList(row, column)){
				targets.add(s);
			}
		}
		else{
			for (BoardCell s : getAdjList(row, column)){
				if (s.isDoorway()){
					targets.add(s);
					visited.add(s);
				}
				else{
					System.out.println(s.toString());
					findAllTargets(s.getRow(), s.getColumn(), pathLength-1);
				}
			}
		}
	}

	private void findAllTargets(int row, int column, int length){
		visited.add(board[row][column]);
		Set<BoardCell> adjacents = getAdjList(row, column);
		//		for (BoardCell b : adjacents){
		//			System.out.println(b.toString());
		//		}
		for (BoardCell s : adjacents){
			if (!(visited.contains(s))){
				visited.add(s);
				if (length <= 1 || s.isDoorway()){
					targets.add(s);
				}
				else {
					findAllTargets(s.getRow(), s.getColumn(), length-1);
				}
				visited.remove(s);
			}
		}
		visited.remove(board[row][column]);
	}


	// Checks to make sure that the boardcell passed in is not on the edge of the board, it does this by
	// Creating a set(boardcells) of adjacent cells which are not outside the board
	private Set<BoardCell> bufferCalcAdj(BoardCell cell){
		Set<BoardCell> bufferAdj= new HashSet<BoardCell>(0);
		int x = cell.getRow();
		int y = cell.getColumn();
		if (x == 0){
			if (y == 0){
				bufferAdj.add(board[x][y+1]);
				bufferAdj.add(board[x+1][y]);
			}
			else if (y == 22){
				bufferAdj.add(board[x+1][y]);
				bufferAdj.add(board[x][y-1]);
			}
			else {
				bufferAdj.add(board[x][y+1]);
				bufferAdj.add(board[x+1][y]);
				bufferAdj.add(board[x][y-1]);
			}
		}
		else if (x == 21){
			if (y == 0){
				bufferAdj.add(board[x][y+1]);
				bufferAdj.add(board[x-1][y]);
			}
			else if (y == 22){
				bufferAdj.add(board[x][y-1]);
				bufferAdj.add(board[x-1][y]);
			}
			else {
				bufferAdj.add(board[x][y+1]);
				bufferAdj.add(board[x][y-1]);
				bufferAdj.add(board[x-1][y]);
			}
		}
		else {
			if (y == 0){
				bufferAdj.add(board[x][y+1]);
				bufferAdj.add(board[x+1][y]);
				bufferAdj.add(board[x-1][y]);
			}
			else if (y == 22){
				bufferAdj.add(board[x+1][y]);
				bufferAdj.add(board[x][y-1]);
				bufferAdj.add(board[x-1][y]);
			}
			else {
				bufferAdj.add(board[x][y+1]);
				bufferAdj.add(board[x+1][y]);
				bufferAdj.add(board[x][y-1]);
				bufferAdj.add(board[x-1][y]);
			}
		}
		return bufferAdj;
	}


	//Returns the adjacency list for one cell, type is Set<BoardCell>
	public void calcAdjacencies(){
		for ( int row = 0; row < numRows; row++){
			for ( int column = 0; column < numColumns; column++){
				BoardCell temp = board[row][column];
				if (temp.getDoorDirection() != DoorDirection.NONE){
					Set<BoardCell> tempAdj = new HashSet<BoardCell>(1);
					// case statement returns for up down left right
					switch (temp.getDoorDirection()){
					case UP:
						tempAdj.add(board[row-1][column]);
						adjMatrix.put(temp, tempAdj);
						break;
					case DOWN:
						tempAdj.add(board[row+1][column]);
						adjMatrix.put(temp, tempAdj);
						break;
					case LEFT:
						tempAdj.add(board[row][column-1]);
						adjMatrix.put(temp, tempAdj);
						break;
					case RIGHT:
						tempAdj.add(board[row][column+1]);
						adjMatrix.put(temp, tempAdj);
						break;
					case NONE:
						break;
					}
				} else if (temp.getInitial() != 'W'){
					adjMatrix.put(temp, new HashSet<BoardCell>(0));
				} else{
					Set<BoardCell> tempAdj = bufferCalcAdj(temp);
					Set<BoardCell> newTempAdj = new HashSet<BoardCell>();
					for (BoardCell b : tempAdj){
						if (b.getInitial() == 'W'){
							newTempAdj.add(b);
						}
						else if (b.getDoorDirection() == DoorDirection.UP && b.getRow() == temp.getRow()+1){
							newTempAdj.add(b);
						}
						else if (b.getDoorDirection() == DoorDirection.DOWN && b.getRow() == temp.getRow()-1){
							newTempAdj.add(b);
						}
						else if (b.getDoorDirection() == DoorDirection.LEFT && b.getColumn() == temp.getColumn()+1){
							newTempAdj.add(b);
						}
						else if (b.getDoorDirection() == DoorDirection.RIGHT && b.getColumn() == temp.getColumn()-1){
							newTempAdj.add(b);
						}
					}
					adjMatrix.put(temp, newTempAdj);
				}
			}
		}
	}

	public Set<BoardCell> getTargets(){
		return targets;
	}

	public Set<BoardCell> getAdjList (int row, int column){
		return adjMatrix.get(board[row][column]); 
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

	public Map<String, Player> getPeople() {
		// TODO Auto-generated method stub
		return null;
	}
}
