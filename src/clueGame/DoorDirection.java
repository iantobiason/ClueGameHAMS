package clueGame;

public enum DoorDirection {
	UP("U"), DOWN("D"), LEFT("L"), RIGHT("R"), NONE("N");
	String direction;
	
	DoorDirection(String s){
		direction = s;
	}
}
