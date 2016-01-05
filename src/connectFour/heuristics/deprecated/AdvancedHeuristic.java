package connectFour.heuristics.deprecated;

import connectFour.ConnectFourState;
import connectFour.ConnectFourState.FieldState;
import connectFour.heuristics.Heuristic;
import search.State;

public class AdvancedHeuristic extends Heuristic {

	@Override
	public double evaluate(ConnectFourState s, FieldState player) {
		
		/**
		 * Second attempt at a utility function.
		 * Counts the ammount of 0 in a rows, two in a rows, three in a rows and four in a rows,
		 * which are not blocked by the enemy and thus still expandable to a four in a row.
		 * Each of these counts has a weight assigned to it.
		 * TODO Adjust weights.
		 */
		
		//{empty fours, ones, twos, threes, fours}
		int[] counts = new int[5];
		//double[] weights =  {.0,.7,.9,10.0,20.0};
		double[] weights =  {.0,.7,10,100,1000.0};
		
		ConnectFourState.FieldState opponent;
		if(s.turn == ConnectFourState.FieldState.RED){
			opponent = ConnectFourState.FieldState.YELLOW;
		}else{
			opponent = ConnectFourState.FieldState.RED;
		}
		
		//Check every field
		for(int column = 0; column < 7; column++){
			for(int row = 0; row < 6; row++){
				ConnectFourState.FieldState field = s.grid[column][row];
				
				
				
				if(field == ConnectFourState.FieldState.EMPTY || field == s.turn){
					int count = 0;
					//Check four above
					if(row <= 2){
						boolean notOccupied = true;
						for(int i = row; i < row+4; i++){
							if(s.grid[column][i] == opponent){
								notOccupied = false;
								break;
							}else if(s.grid[column][i] == s.turn){
								count++;
							}
						}
						
						if(notOccupied) counts[count]++;
					}
					
					count = 0;
					//Check to the right
					if(column <= 3){
						boolean notOccupied = true;
						for(int i = column; i < column+4; i++){
							if(s.grid[i][row] == opponent){
								notOccupied = false;
								break;
							}else if(s.grid[i][row] == s.turn){
								count++;
							}
							
						}
						
						if(notOccupied) counts[count]++;
					}
					count = 0;
					//Check diagonal up
					if(row <= 2 && column <= 3){
						boolean notOccupied = true;
						for(int i = 0; i < 4; i++){
							if(s.grid[column+i][row+i] == opponent){
								notOccupied = false;
								break;
							}else if(s.grid[column+i][row+i] == s.turn){
								count++;
							}
						}
						
						if(notOccupied) counts[count]++;
					}
					
					count = 0;
					//Check diagonal down
					if(row > 2 && column <= 3){
						boolean notOccupied = true;
						for(int i = 0; i < 4; i++){
							if(s.grid[column+i][row-i] == opponent){
								notOccupied = false;
								break;
							}else if(s.grid[column+i][row-i] == s.turn){
								count++;
							}
						}
						
						if(notOccupied) counts[count]++;
					}
					
				}
			}	
		}
			
		double value = 0;
		
		for(int i = 0; i < counts.length; i++){
			value += weights[i]*counts[i];
		}
		//System.out.println("Value: "+ value+" "+"Counts:"+Arrays.toString(counts));
		//System.out.println(s.toString());
		return value;
	}

}
