package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	// Double array grid of type BoardCell (will have row and column ints for each point)
	private BoardCell[][] grid;
	
	//Set of Boardcells for visited and Targets so that...
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	// Store the adjacency lists in a HashMap of Key: BoardCell, and Value: Set<BoardCell>
	private Map<BoardCell, Set<BoardCell>> adjacentMatrix;
	
	//CONSTRUCTOR - TO MODIFY
	public IntBoard(int rows, int columns) {
		super();
		this.grid = new BoardCell[rows][columns];

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
	public Set<BoardCell> calcTargets(BoardCell startCell, int pathLength){
		visited.add(startCell);
		targets.clear();
		for (BoardCell s : getAdjList(startCell)){
			findAllTargets(s, pathLength);
		}
		return null;
	}
	
	
	// Returns the list of targets. The return type is Set<BoardCell>.
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	
	//Returns the adjacency list for one cell, type is Set<BoardCell>
	public Set<BoardCell> getAdjList(BoardCell cell){
		
		return null;
	}
	
	//Recursively returns any possible cells that a player can move to
	private void findAllTargets(BoardCell startCell, int length){
		BoardCell target = new BoardCell(0,0);
		Set<BoardCell> adjacents = getAdjList(startCell);
		for (BoardCell s : adjacents){
			if (visited.contains(s)){
				continue;
			}
			visited.add(s);
			if (length == 1){
				targets.add(s);
			}
			else {
				findAllTargets(s, length - 1);
			}
			visited.remove(s);
		}
	}
}
