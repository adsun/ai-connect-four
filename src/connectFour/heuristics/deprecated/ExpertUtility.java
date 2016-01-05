package connectFour.heuristics.deprecated;

import java.util.Arrays;

import connectFour.ConnectFourState;
import connectFour.ConnectFourState.FieldState;
import connectFour.heuristics.Heuristic;

public class ExpertUtility extends Heuristic {

	@Override
	public double evaluate(ConnectFourState state, FieldState player) {
		if(!(state instanceof ConnectFourState)) return Double.MAX_VALUE;
		ConnectFourState s = (ConnectFourState) state;
		
		//{empty fours, ones, twos, threes, fours}
		int[] counts = new int[5];
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
			
		double value1 = 0;
		
		for(int i = 0; i < counts.length; i++){
			value1 += weights[i]*counts[i];
		} 
		System.out.println("Value: "+ value1);
		System.out.println("Counts: "+Arrays.toString(counts));
		//System.out.println("Value: "+ value+" "+"Counts:"+Arrays.toString(counts));
		//System.out.println(s.toString());
		
		counts = new int[5];
		
		//Check every field AGAIN
		for(int column = 0; column < 7; column++){
			for(int row = 0; row < 6; row++){
				ConnectFourState.FieldState field = s.grid[column][row];
				
				
				
				if(field == ConnectFourState.FieldState.EMPTY || field == opponent){
					int count = 0;
					//Check four above
					if(row <= 2){
						boolean notOccupied = true;
						for(int i = row; i < row+4; i++){
							if(s.grid[column][i] == s.turn){
								notOccupied = false;
								break;
							}else if(s.grid[column][i] == opponent){
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
							if(s.grid[i][row] == s.turn){
								notOccupied = false;
								break;
							}else if(s.grid[i][row] == opponent){
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
							if(s.grid[column+i][row+i] == s.turn){
								notOccupied = false;
								break;
							}else if(s.grid[column+i][row+i] == opponent){
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
							if(s.grid[column+i][row-i] == s.turn){
								notOccupied = false;
								break;
							}else if(s.grid[column+i][row-i] == opponent){
								count++;
							}
						}
						
						if(notOccupied) counts[count]++;
					}
					
				}
			}	
		}
		
		double value2=0;
		for(int i = 0; i < counts.length; i++){
			value2 += weights[i]*counts[i];
		} 
		System.out.println("Value2: "+ value2);
		System.out.println("Counts: "+Arrays.toString(counts));
		System.out.println(s.toString());
		
		return value1-value2;
	}

}
