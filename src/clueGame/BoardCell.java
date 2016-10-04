package clueGame;

public class BoardCell {
		private int row;
		private int column;
		private char initial;
		
		public BoardCell(int row, int column) {
			super();
			this.row = row;
			this.column = column;
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
			return true;
		}

}
