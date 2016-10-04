package clueGame;

public enum DoorDirection {
	UP("U"), Down("D"), LEFT("L"), RIGHT("R"), NONE("N");
	String direction;
	
	DoorDirection(String s){
		direction = s;
	}
}
