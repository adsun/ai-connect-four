package connectFour;
import search.State;


public class ConnectFourState extends State {
	
	/**
	 * Configuration:
	 * 5|				|
	 * 4|				|
	 * 3|				|
	 * 2|				|
	 * 1|				|
	 * 0|_ _ _ _ _ _ _ _|
	 * 	 0 1 2 3 4 5 6 7
	 */
	public FieldState[][] grid;
	
	//Color of the player that took the turn.
	public FieldState turn;
	
	public enum FieldState {
		EMPTY("empty"), YELLOW("yellow"), RED("red");
		
		private String name;
		
		private FieldState(String name) {
			this.name = name;
		}
		
		@Override
		public String toString(){
			return name;
		}
		
		public boolean equals(FieldState other){
			return other.name.equals(name);
		}
		
		public boolean equals(String other){
			return other.equals(name);
		}
		
		public char toChar(){
			if(this == EMPTY){
				return ' ';
			}else if(this == RED){
				return 'X';
			}else{
				return 'O';
			}
		}

		public static FieldState fromString(String name) {
			switch(name){
				case "yellow":
					return FieldState.YELLOW;
				case "red":
					return FieldState.RED;
				default:
					return FieldState.EMPTY;
				
			}
		}
		
	}
	
	public ConnectFourState(FieldState[][] grid, FieldState turn) {
		this.grid = grid;
		this.turn = turn;
	}

	@Override
	public boolean equals(State other) {
		if(!(other instanceof ConnectFourState)) return false;
		ConnectFourState o = (ConnectFourState) other;
		
		for(int column = 0; column < grid.length; column++){
			for(int row = 0; row < grid[column].length; row++){
				if(grid[column][row] != o.grid[column][row]) return false;
			}
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		//TODO
		final int prime = 31;
		int result = 1;
		
		for(int column = 0; column < grid.length; column++){
			for(int row = 0; row < grid[column].length; row++){
				
				grid[column][row].hashCode();  
				
			}
		}
		
		
		return result;
	}

	@Override
	public boolean isValid() {
		if(grid.length != 7) return false;
		for(int column = 0; column < grid.length; column++){
			if(grid[column].length != 6) return false;
		}
		
		//TODO check for "flying" pieces and color counts
		return true;
	}

	@Override
	public String toString() {
		
		String result = "";
		for(int row = 0; row < grid[0].length; row++){
			String r = "|";
			for(int column = 0; column < grid.length; column++){
				r += grid[column][row].toChar() + "|";
			}
			result = r+"\n"+result;
		}
		return  "Last turn: "+turn.toChar()+ "\n"+result;
	}

}
