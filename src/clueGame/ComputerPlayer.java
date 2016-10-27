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
	public Card disproveSuggestion(Set<Card> suggestion){
		ArrayList<Card> cardsToReturn = new ArrayList<Card>();
		for(int i = 0; i < cardsInHand.length; i++){
			for(Card c : suggestion){
				if(cardsInHand[i].equals(c)){
					cardsToReturn.add(c);
				}
			}
		}
		if(cardsToReturn.size() == 1){
			return cardsToReturn.get(0);
		} else if(cardsToReturn.size() >= 1){
			Random rn = new Random();
			return cardsToReturn.get(rn.nextInt(cardsToReturn.size()));
		} else{
			return null;
		}
	}

}
