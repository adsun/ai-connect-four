package connectFour.heuristics;

import java.util.Arrays;

import connectFour.ConnectFourState;
import connectFour.ConnectFourState.FieldState;

public class ExpertHeuristic2 extends Heuristic {
	
	private boolean log;
	//private double[] weights =  {2000,10,10,5,5,2,0.1};
	
	/**
	 * Default weights
	 */
	private double[] weights = {2000, 100, 50, 100, 50, 5, 1};
	
	
	public ExpertHeuristic2() {
		log = false;
	}
	
	public ExpertHeuristic2(boolean log) {
		this.log = log;
	}
	
	public ExpertHeuristic2(double[] weights) {
		this.log = false;
		this.weights = weights;
	}
	
	public ExpertHeuristic2(double[] weights, boolean log) {
		this.log = log;
		this.weights = weights;
	}


	@Override
	public double evaluate(ConnectFourState s, FieldState player) {
		//ConnectFourState.FieldState player = s.turn;
		ConnectFourState.FieldState opponent;
		if(player == FieldState.RED){
			opponent = ConnectFourState.FieldState.YELLOW;
		}else{
			opponent = ConnectFourState.FieldState.RED;
		}
		
		//Features: {4,3,3,3,3,2,1
		double features[] = new double[7];
		//double weights[] =  {2000,10,10,5,5,2,0.1};
		int attacks[] = new int[7];
		int threats[] = new int[7];
		double attackWeights[] = {1,1,1,1,1,1,1};
		//double threatWeights[] = {1,1,1,1,1,1,1};
		double threatWeights[] = {1.1,1.1,1.1,1.1,1.1,0,0};
		//Iterate over all fields.
		//Count each kind of threat for the empty fields.
		ConnectFourState.FieldState color;
		for(int column = 0; column < 7; column++){
			for(int row = 0; row < 6; row++){
				
				
				/*
				 * Counting feature 0:
				 * Four in a rows. We count attacks and threats.
				 * Always assume we are in the top left field of the four.
				 */
				if(s.grid[column][row] != ConnectFourState.FieldState.EMPTY){
					color = s.grid[column][row];
					
					//Check three to the right
					if( column < 4 &&
						s.grid[column+1][row] == color &&
						s.grid[column+2][row] == color &&
						s.grid[column+3][row] == color){
						//Found 4 in a row
						if(color == player) attacks[0]++;
						else if(color == opponent) threats[0]++;
					}
					
					//Check three below, straight down, diagonal left and right
					if(row > 2){
						//Down
						if(	s.grid[column][row-1] == color &&
							s.grid[column][row-2] == color &&
							s.grid[column][row-3] == color){
							//Found 4 in a row
							if(color == player) attacks[0]++;
							else if(color == opponent) threats[0]++;
						}
					
						//Down and left
						if( column > 2 &&
							s.grid[column-1][row-1] == color &&
							s.grid[column-2][row-2] == color &&
							s.grid[column-3][row-3] == color){
							//Found 4 in a row
							if(color == player) attacks[0]++;
							else if(color == opponent) threats[0]++;
						}
						
						//Down and right
						//Check three to the right
						if( column < 4 &&
							s.grid[column+1][row-1] == color &&
							s.grid[column+2][row-2] == color &&
							s.grid[column+3][row-3] == color){
							//Found 4 in a row
							if(color == player) attacks[0]++;
							else if(color == opponent) threats[0]++;
						}
					}
				}//End of feature 0
				
				features[0] = attackWeights[0]*attacks[0] - threatWeights[0]*threats[0];
				
				/*
				 * Counting feature 1 and feature 2:
				 * Three in a rows, which can be turned into a four in a row
				 * with one stone. We look for empty fields, check if there is
				 * something below it and look in any of the 7 directions if there
				 * are three times the same color.
				 * If it is the player color, we increase attacks[0], threats[0] if
				 * it is the opponents color.
				 * Feature 2 is the same but the stone below is empty
				 */
				if(s.grid[column][row] == ConnectFourState.FieldState.EMPTY){
					//Three in a rows and a stone below
					
					//Three below
					if(row > 2){
						//Decide if potential attack or threat
						color = s.grid[column][row-1];
					
						if( s.grid[column][row-2] == color &&
							s.grid[column][row-3] == color){
							//Found 3 in a row
							if(color == player) attacks[1]++;
							else if(color == opponent) threats[1]++;
						}
					}
					
					//Three diagonal left down
					if(column > 2 && row > 2){
						//Decide if potential attack or threat
						color = s.grid[column-1][row-1];
					
						if( s.grid[column-2][row-2] == color &&
							s.grid[column-3][row-3] == color){
							//Found 3 in a row
							if(s.grid[column][row-1] != ConnectFourState.FieldState.EMPTY){
								if(color == player) attacks[1]++;
								else if(color == opponent) threats[1]++;
							}else{
								if(color == player) attacks[2]++;
								else if(color == opponent) threats[2]++;
							}
							
						}
					}
					
					//Three diagonal right down
					if(column < 4 && row > 2){
						//Decide if potential attack or threat
						color = s.grid[column+1][row-1];
					
						if( s.grid[column+2][row-2] == color &&
							s.grid[column+3][row-3] == color){
							//Found 3 in a row
							if(s.grid[column][row-1] != ConnectFourState.FieldState.EMPTY){
								if(color == player) attacks[1]++;
								else if(color == opponent) threats[1]++;
							}else{
								if(color == player) attacks[2]++;
								else if(color == opponent) threats[2]++;
							}
						}
					}
					
					//Three diagonal left up and NOT on the ground
					if(column > 2 && row < 3 && row > 0){
						//Decide if potential attack or threat
						color = s.grid[column-1][row+1];
					
						if( s.grid[column-2][row+2] == color &&
							s.grid[column-3][row+3] == color){
							//Found 3 in a row
							if(s.grid[column][row-1] != ConnectFourState.FieldState.EMPTY){
								if(color == player) attacks[1]++;
								else if(color == opponent) threats[1]++;
							}else{
								if(color == player) attacks[2]++;
								else if(color == opponent) threats[2]++;
							}
						}
					}
					
					//Three diagonal right up and NOT on the ground
					if(column < 4 && row < 3 && row > 0){
						//Decide if potential attack or threat
						color = s.grid[column+1][row+1];
					
						if( s.grid[column+2][row+2] == color &&
							s.grid[column+3][row+3] == color){
							//Found 3 in a row
							if(s.grid[column][row-1] != ConnectFourState.FieldState.EMPTY){
								if(color == player) attacks[1]++;
								else if(color == opponent) threats[1]++;
							}else{
								if(color == player) attacks[2]++;
								else if(color == opponent) threats[2]++;
							}
						}
					}
					
					//Three diagonal left up and on the ground
					if(column > 2 && row < 3 && row == 0){
						//Decide if potential attack or threat
						color = s.grid[column-1][row+1];
					
						if( s.grid[column-2][row+2] == color &&
							s.grid[column-3][row+3] == color){
							//Found 3 in a row
							if(color == player) attacks[1]++;
							else if(color == opponent) threats[1]++;
						}
					}
					
					//Three diagonal right up and on the ground
					if(column < 4 && row < 3 && row == 0){
						//Decide if potential attack or threat
						color = s.grid[column+1][row+1];
					
						if( s.grid[column+2][row+2] == color &&
							s.grid[column+3][row+3] == color){
							//Found 3 in a row
							if(color == player) attacks[1]++;
							else if(color == opponent) threats[1]++;
						}
					}
					
					
					//Three horizontal left and NOT on the ground
					if(column > 2 && row > 0){
						//Decide if potential attack or threat
						color = s.grid[column-1][row];
					
						if( s.grid[column-2][row] == color &&
							s.grid[column-3][row] == color){
							//Found 3 in a row
							if(s.grid[column][row-1] != ConnectFourState.FieldState.EMPTY){
								if(color == player) attacks[1]++;
								else if(color == opponent) threats[1]++;
							}else{
								if(color == player) attacks[2]++;
								else if(color == opponent) threats[2]++;
							}
						}
					}
					
					//Three horizontal right and NOT on the ground
					if(column < 4 && row > 0){
						//Decide if potential attack or threat
						color = s.grid[column+1][row];
					
						if( s.grid[column+2][row] == color &&
							s.grid[column+3][row] == color){
							//Found 3 in a row
							if(s.grid[column][row-1] != ConnectFourState.FieldState.EMPTY){
								if(color == player) attacks[1]++;
								else if(color == opponent) threats[1]++;
							}else{
								if(color == player) attacks[2]++;
								else if(color == opponent) threats[2]++;
							}
						}
					}
					
					//Three horizontal left and on the ground
					if(column > 2 && row == 0){
						//Decide if potential attack or threat
						color = s.grid[column-1][row];
					
						if(	s.grid[column-2][row] == color &&
							s.grid[column-3][row] == color){
							//Found 3 in a row
							if(color == player) attacks[1]++;
							else if(color == opponent) threats[1]++;
						}
					}
					
					//Three horizontal right and on the ground
					if(column < 4 && row == 0){
						//Decide if potential attack or threat
						color = s.grid[column+1][row];
					
						if(	s.grid[column+2][row] == color &&
							s.grid[column+3][row] == color){
							//Found 3 in a row
							if(color == player) attacks[1]++;
							else if(color == opponent) threats[1]++;
						}
					}
				}//End of feature 1 and 2
				
				features[1] = attackWeights[1]*attacks[1] - threatWeights[1]*threats[1];
				features[2] = attackWeights[2]*attacks[2] - threatWeights[2]*threats[2];
				
				/*
				 * Counting feature 3 and 4: XX_X X_XX
				 * Three is with piece below, four without
				 */
				if(s.grid[column][row] == ConnectFourState.FieldState.EMPTY){
					
					//Check Horizontal

					//XX_X
					if(column > 1 && column < 6){
						//Decide if potential attack or threat
						color = s.grid[column+1][row];
					
						if(	color != ConnectFourState.FieldState.EMPTY &&
							s.grid[column-1][row] == color &&
							s.grid[column-2][row] == color){
							//Found 3 in a row
							if(row == 0 || s.grid[column][row-1] != ConnectFourState.FieldState.EMPTY){
								if(color == player) attacks[3]++;
								else if(color == opponent) threats[3]++;
							}else{
								if(color == player) attacks[4]++;
								else if(color == opponent) threats[4]++;
							}
							
						}
					}
					
					//X_XX
					if(column > 0 && column < 5){
						//Decide if potential attack or threat
						color = s.grid[column-1][row];
					
						if(	color != ConnectFourState.FieldState.EMPTY &&
							s.grid[column+1][row] == color &&
							s.grid[column+2][row] == color){
							//Found 3 in a row
							if(row == 0 || s.grid[column][row-1] != ConnectFourState.FieldState.EMPTY){
								if(color == player) attacks[3]++;
								else if(color == opponent) threats[3]++;
							}else{
								if(color == player) attacks[4]++;
								else if(color == opponent) threats[4]++;
							}
						}
					}
					
					
					/*
					 * Check diagonal
					 * X
					 *  _
					 *   X
					 *    X
					 */
					if(column > 0 && column < 5 && row > 1 && row < 5){
						//Decide if potential attack or threat
						color = s.grid[column-1][row+1];
					
						if(	color != ConnectFourState.FieldState.EMPTY &&
							s.grid[column+1][row-1] == color &&
							s.grid[column+2][row-2] == color){
							//Found 3 in a row
							if(s.grid[column][row-1] != ConnectFourState.FieldState.EMPTY){
								if(color == player) attacks[3]++;
								else if(color == opponent) threats[3]++;
							}else{
								if(color == player) attacks[4]++;
								else if(color == opponent) threats[4]++;
							}
						}
					}
					
					/*
					 * Check diagonal
					 * X
					 *  X
					 *   _
					 *    X
					 */
					if(column > 1 && column < 6 && row > 0 && row < 4){
						//Decide if potential attack or threat
						color = s.grid[column+1][row-1];
					
						if(	color != ConnectFourState.FieldState.EMPTY &&
							s.grid[column-1][row+1] == color &&
							s.grid[column-2][row+2] == color){
							//Found 3 in a row
							if(s.grid[column][row-1] != ConnectFourState.FieldState.EMPTY){
								if(color == player) attacks[3]++;
								else if(color == opponent) threats[3]++;
							}else{
								if(color == player) attacks[4]++;
								else if(color == opponent) threats[4]++;
							}
						}
					}
					
					/*
					 * Check diagonal
					 *    X
					 *   _
					 *  X
					 * X
					 */
					if(column > 1 && column < 6 && row > 1 && row < 5){
						//Decide if potential attack or threat
						color = s.grid[column+1][row+1];
					
						if(	color != ConnectFourState.FieldState.EMPTY &&
							s.grid[column-1][row-1] == color &&
							s.grid[column-2][row-2] == color){
							//Found 3 in a row
							if(s.grid[column][row-1] != ConnectFourState.FieldState.EMPTY){
								if(color == player) attacks[3]++;
								else if(color == opponent) threats[3]++;
							}else{
								if(color == player) attacks[4]++;
								else if(color == opponent) threats[4]++;
							}
						}
					}
					
					/*
					 * Check diagonal
					 *    X
					 *   X
					 *  _
					 * X
					 */
					if(column > 0 && column < 5 && row > 0 && row < 4){
						//Decide if potential attack or threat
						color = s.grid[column-1][row-1];
					
						if(	color != ConnectFourState.FieldState.EMPTY &&
							s.grid[column+1][row+1] == color &&
							s.grid[column+2][row+2] == color){
							//Found 3 in a row
							if(s.grid[column][row-1] != ConnectFourState.FieldState.EMPTY){
								if(color == player) attacks[3]++;
								else if(color == opponent) threats[3]++;
							}else{
								if(color == player) attacks[4]++;
								else if(color == opponent) threats[4]++;
							}
						}
					}
					
				}//End Feature 2
				
				features[3] = attackWeights[3]*attacks[3] - threatWeights[3]*threats[3];
				features[4] = attackWeights[4]*attacks[4] - threatWeights[4]*threats[4];
				
				/*
				 * Feature 5 and6: 2 in 4 which are not blocked and 1 in 4 which are not blocked
				 * Not as precise approach as before.
				 */
				
				//Check above X X _ _
				if(row < 3){
					color = s.grid[column][row];
					if( color != ConnectFourState.FieldState.EMPTY &&
						s.grid[column][row+1] == color &&
						s.grid[column][row+2] == ConnectFourState.FieldState.EMPTY &&
						s.grid[column][row+3] == ConnectFourState.FieldState.EMPTY){
						
						if(color == player) attacks[5]++;
						else if(color == opponent) threats[5]++;
					}
				}
				
				//Check above x _ _ _
				if(row < 3){
					color = s.grid[column][row];
					if( color != ConnectFourState.FieldState.EMPTY &&
						s.grid[column][row+1] == ConnectFourState.FieldState.EMPTY &&
						s.grid[column][row+2] == ConnectFourState.FieldState.EMPTY &&
						s.grid[column][row+3] == ConnectFourState.FieldState.EMPTY){
						if(color == player) attacks[6]++;
						else if(color == opponent) threats[6]++;
					}
				}
				
				
				//Check to the right
				if(column <= 3){
					int count = 0;
					
					color = null;
					boolean notOccupied = true;
					for(int i = 0; i < 4; i++){
						if(s.grid[column+i][row] != ConnectFourState.FieldState.EMPTY){
							color = s.grid[column+i][row];
						}
						if(color != null && s.grid[column+i][row] == color){
							count++;
						}else if(s.grid[column+i][row] == ConnectFourState.FieldState.EMPTY){
							
						}else{
							notOccupied = false;
							break;
						}
					}
					
					if(notOccupied){
						if(count == 2){
							if(color == player) attacks[5]++;
							else if(color == opponent) threats[5]++;
						}else if( count == 1){
							if(color == player) attacks[6]++;
							else if(color == opponent) threats[6]++;
						}
					}
				}
				
				
				//Check diagonal top right
				if(column < 4 && row < 3){
					int count = 0;
					
					color = null;
					boolean notOccupied = true;
					for(int i = 0; i < 4; i++){
						if(s.grid[column+i][row] != ConnectFourState.FieldState.EMPTY){
							color = s.grid[column+i][row+i];
						}
						if(color != null && s.grid[column+i][row+i] == color){
							count++;
						}else if(s.grid[column+i][row+i] == ConnectFourState.FieldState.EMPTY){
							
						}else{
							notOccupied = false;
							break;
						}
					}
					
					if(notOccupied){
						if(count == 2){
							//System.out.println("2diagonalTopRight ("+column+","+row+")");
							if(color == player) attacks[5]++;
							else if(color == opponent) threats[5]++;
						}else if( count == 1){
							//System.out.println("1diagonalTopRight ("+column+","+row+")");
							if(color == player) attacks[6]++;
							else if(color == opponent) threats[6]++;
						}
					}
				}
				
				//Check diagonal bottom right
				if(column < 4 && row > 2){
					int count = 0;
					
					color = null;
					boolean notOccupied = true;
					for(int i = 0; i < 4; i++){
						if(s.grid[column+i][row] != ConnectFourState.FieldState.EMPTY){
							color = s.grid[column+i][row-i];
						}
						if(color != null && s.grid[column+i][row-i] == color){
							count++;
						}else if(s.grid[column+i][row-i] == ConnectFourState.FieldState.EMPTY){
							
						}else{
							notOccupied = false;
							break;
						}
					}
					
					if(notOccupied){
						if(count == 2){
							//System.out.println("2diagonalBottomRight ("+column+","+row+")");
							if(color == player) attacks[5]++;
							else if(color == opponent) threats[5]++;
						}else if( count == 1){
							//System.out.println("1diagonalBottomRight ("+column+","+row+")");
							if(color == player) attacks[6]++;
							else if(color == opponent) threats[6]++;
						}
					}
				}
				
				
				features[5] = attackWeights[5]*attacks[5] - threatWeights[5]*threats[5];
				features[6] = attackWeights[6]*attacks[6] - threatWeights[6]*threats[6];
				
			}
		}
				
		double result = 0;
		for(int i = 0; i < features.length; i++){
			result += weights[i]*features[i];
			//System.out.println(weights[i]+"*"+features[i] +" = "+(weights[i]*features[i]));
		}
		if(log){
			System.out.println("Attacks:\t"+Arrays.toString(attacks));
			System.out.println("Threats:\t"+Arrays.toString(threats));
			System.out.println("Features:\t"+Arrays.toString(features));
			System.out.println("Utility:\t"+result);
		}
		return result;
	}

	

}
