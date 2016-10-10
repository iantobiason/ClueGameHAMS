package clueGame;

public class BoardCell {
		private int row;
		private int column;
		private char initial = '#';
		private DoorDirection direction = DoorDirection.NONE;
		
		public BoardCell(int row, int column) {
			super();
			this.row = row;
			this.column = column;
		}
		public BoardCell(int row, int column, char init) {
			super();
			this.row = row;
			this.column = column;
			this.initial = init;
		}
		public BoardCell(int row, int column, char init, DoorDirection dir) {
			super();
			this.row = row;
			this.column = column;
			this.initial = init;
			this.direction = dir;
		}
		public int getRow() {
			return row;
		}
		public void setRow(int row) {
			this.row = row;
		}
		public int getColumn() {
			return column;
		}
		public void setColumn(int column) {
			this.column = column;
		}
		
		public boolean isWalkway(){
			return true;
		}
		
		public boolean isRoom(){
			return true;
		}
		
		public boolean isDoorway(){
			if (direction.equals(DoorDirection.NONE)){
				return false;
			}else {return true;}
		}
		
		public DoorDirection getDoorDirection(){
			return direction;
		}
		
		public char getInitial(){
			return initial;
		}
		public void setInitial(char c){
			this.initial = c;
		}
		@Override
		public String toString() {
			return "BoardCell [row=" + row + ", column=" + column + ", initial=" + initial + ", direction=" + direction
					+ "]";
		}
		
		

}
