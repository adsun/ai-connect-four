package connectFour.heuristics;

import connectFour.ConnectFourState;
import connectFour.ConnectFourState.FieldState;

public class BasicHeuristic extends Heuristic {

	@Override
	public double evaluate(ConnectFourState s, FieldState player) {
		
		/**
		 * First utility function.
		 * For each field calculate the amount of neighbours with the player color.
		 * 
		 */
		double value = 0;
		for(int column = 0; column < 7; column++){
			for(int row = 0; row < 6; row++){
				if(s.grid[column][row] == player){
					//Check left side
					if(column > 0){
						//Check bottom left
						if(row > 0){
							if(s.grid[column-1][row-1] == player) value += 1.0;
						}
						//Check left
						if(s.grid[column-1][row] == player ) value += 1.0;
						//Check top left
						if(row < 5){
							if(s.grid[column-1][row+1] == player) value += 1.0;
						}
					}
					//Check same column
					if(row > 0){
						if(s.grid[column][row-1] == player) value += 1.0;
					}
					if(row < 5){
						if(s.grid[column][row+1] == player) value += 1.0;
					}
					//Check right side
					if(column < 6){
						//Check bottom left
						if(row > 0){
							if(s.grid[column+1][row-1] == player) value += 1.0;
						}
						//Check left
						if(s.grid[column+1][row] == player) value += 1.0;
						//Check top left
						if(row < 5){
							if(s.grid[column+1][row+1] == player) value += 1.0;
						}
					}
				}else if(s.grid[column][row] == ConnectFourState.FieldState.EMPTY){
					//System.out.println("Empty");
					value += 0;
				}
				
			}
		}
		//System.out.println(value);
		//System.out.println(s.toString());
		return value;
	}

}
