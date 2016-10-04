package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	// Double array grid of type BoardCell (will have row and column ints for each point)
	private BoardCell[][] grid;
	
	//Set of Boardcells for visited and Targets so that...
	private Set<BoardCell> visited = new HashSet<BoardCell>(0);
	private Set<BoardCell> targets = new HashSet<BoardCell>(0);
	// Store the adjacency lists in a HashMap of Key: BoardCell, and Value: Set<BoardCell>
	private Map<BoardCell, Set<BoardCell>> adjacentMatrix = new HashMap<BoardCell, Set<BoardCell>>(0);
	
	//CONSTRUCTOR - TO MODIFY
	public IntBoard(int rows, int columns) {
		super();
		this.grid = new BoardCell[rows][columns];
		
		for (int i = 0; i < rows; i ++){
			for (int j = 0; j < columns; j++){
				grid[i][j] = new BoardCell(i,j);
			}
		}
	}
	
	
	// Calculates the adjacency list for each grid cell, stores in a map data structure. 
	//If you don't know what this is, go back to Preparation.
	public Map<BoardCell, Set<BoardCell>> calcAdjacencies(){
		return null;
	}
	
	public BoardCell getCell(int row, int column){
		return grid[row][column];
	}
	
	//Calculates the targets that are pathLength distance from the startCell. 
	//The list of targets will be stored in an instance variable.
	// PLACE ALGORYTHM HERE
	public void calcTargets(BoardCell startCell, int pathLength){
		visited.add(startCell);
		targets.clear();
		if (pathLength == 1){
			for (BoardCell s : getAdjList(startCell, this)){
				targets.add(s);
			}
		}
		else{
			for (BoardCell s : getAdjList(startCell, this)){
				findAllTargets(s, pathLength-1);
			}
		}
	}
	
	
	// Returns the list of targets. The return type is Set<BoardCell>.
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	
	//Returns the adjacency list for one cell, type is Set<BoardCell>
	public Set<BoardCell> getAdjList(BoardCell cell, IntBoard board){
		Set<BoardCell> adj = new HashSet<BoardCell>(4);
		int x = cell.getRow();
		int y = cell.getColumn();
		if (x == 0){
			if (y == 0){
				adj.add(board.getCell(x, y+1));
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
		
		return adj;
	}
	
	//Recursively returns any possible cells that a player can move to
	private void findAllTargets(BoardCell startCell, int length){
		Set<BoardCell> adjacents = getAdjList(startCell, this);
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
			/*visited.add(s);
			if (length <= 1){
				targets.add(s);
			}
			else {
				findAllTargets(s, length-1);
			}
			visited.remove(s);
			*/
		}
	}
}
