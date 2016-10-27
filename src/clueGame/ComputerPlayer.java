package clueGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private char recentRoom;
	
	public ComputerPlayer() {
		// TODO Auto-generated constructor stub
	}
	public BoardCell pickLocation(Set<BoardCell> targets) {
		ArrayList<BoardCell> listOfCells = new ArrayList<BoardCell>(targets);
		for(BoardCell b: listOfCells){
			if(b.isDoorway() && b.getInitial() != recentRoom){
				return b;
			}
		}
		Random rn = new Random();
		return listOfCells.get(rn.nextInt(listOfCells.size()));
	}
	public void makeAccusation() {
		
	}
	public void createSuggestion() {
		
	}
	public void setRecent(char c) {
		recentRoom = c;
	}
	public Object disproveSuggestion(Set<Card> suggestion) {
		// TODO Auto-generated method stub
		return null;
	}

}
