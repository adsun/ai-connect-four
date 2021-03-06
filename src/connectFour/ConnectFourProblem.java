package connectFour;
import java.util.ArrayList;
import java.util.Arrays;

import connectFour.heuristics.Heuristic;
import search.Action;
import search.Problem;
import search.State;


public class ConnectFourProblem extends Problem{
	
	public static final int EASY = 0;
	public static final int ADVANCED = 1;
	public static final int EXPERT = 2;
	public static final int EXPERIMENTAL = 3;
	private static final int[] ACTIONSORDER = {3,2,4,1,5,0,6};
	
	int difficulty;
	
	public ArrayList<Action> actions;
	
	private ConnectFourState.FieldState player;
	
	private Heuristic heuristic;
	
	
	public ConnectFourProblem(ConnectFourState.FieldState player, Heuristic heuristic) {
		actions = new ArrayList<Action>();
		initActions();
		this.player = player;
		this.heuristic = heuristic;
	}
	

	@Override
	public State getInitialState() {
		//Initialize empty board with 7 columns and 6 rows
		ConnectFourState.FieldState[][] grid = new ConnectFourState.FieldState[7][6];
		
		for(int column = 0; column < 7; column++){
			for(int row = 0; row < 6; row++){
				grid[column][row] = ConnectFourState.FieldState.EMPTY;
			}
		}
		//grid[3][0] = ConnectFourState.FieldState.RED;
		if(player == ConnectFourState.FieldState.RED){
			return new ConnectFourState(grid, ConnectFourState.FieldState.YELLOW);
		}else{
			return new ConnectFourState(grid, ConnectFourState.FieldState.RED);
		}
		
	}

	@Override
	public boolean goalTest(State state) {
		if(!(state instanceof ConnectFourState)) return false;
		ConnectFourState s = (ConnectFourState) state;
		ConnectFourState.FieldState[][] grid = s.grid;
		
		//Check every field
		for(int column = 0; column < 7; column++){
			for(int row = 0; row < 6; row++){
				ConnectFourState.FieldState field = grid[column][row];
				
				if(field != ConnectFourState.FieldState.EMPTY){
					//Assume field is the bottom left slot of four in a row.
					
					//Check four above
					if(row <= 2){
						boolean goal = true;
						for(int i = row+1; i < row+4; i++){
							if(grid[column][i] != field){
								goal = false;
								break;
							}
						}
						
						if(goal) return true;
					}
					
					//Check to the right
					if(column <= 3){
						boolean goal = true;
						for(int i = column+1; i < column+4; i++){
							if(grid[i][row] != field){
								goal = false;
								break;
							}
						}
						
						if(goal) return true;
					}
					
					//Check diagonal up
					if(row <= 2 && column <= 3){
						boolean goal = true;
						for(int i = 1; i < 4; i++){
							if(grid[column+i][row+i] != field){
								goal = false;
								break;
							}
						}
						
						if(goal) return true;
					}
					
					//Check diagonal down
					if(row > 2 && column <= 3){
						boolean goal = true;
						for(int i = 1; i < 4; i++){
							if(grid[column+i][row-i] != field){
								goal = false;
								break;
							}
						}
						
						if(goal) return true;
					}
					
				}
			}	
		}
		
		return false;
	}
	

	@Override
	public ArrayList<Action> getActions(State state) {
		if(!(state instanceof ConnectFourState)) return null;
		ConnectFourState s = (ConnectFourState) state;
		
		
		ArrayList<Action> result = new ArrayList<Action>();
		
		for(int i = 0; i < 7; i++){
			
			if(s.grid[ACTIONSORDER[i]][5] == ConnectFourState.FieldState.EMPTY){
				result.add(actions.get(ACTIONSORDER[i]));
			}
		}
		
		//System.out.println(Arrays.toString(result.toArray()));
		//System.out.println(state);
		return result;
	}

	@Override
	public double getStepCost(State state, Action action) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public double utility(State state){
		if(!(state instanceof ConnectFourState)) throw new IllegalArgumentException("state is no ConnectFourState");
		//((ConnectFourState) state).turn = player;
		return heuristic.evaluate((ConnectFourState)state, player);
	}
	
		
	private void initActions(){
		//First column
		actions.add(new Action() {
			
			@Override
			public String toString() {
				return "0";
			}
			
			@Override
			public State execute(State state) {
				
				if(!(state instanceof ConnectFourState)) return null;
				ConnectFourState s = (ConnectFourState) state;
				
				//Copy grid
				ConnectFourState.FieldState [][] grid = new ConnectFourState.FieldState[s.grid.length][];
				for(int j = 0; j < grid.length; j++){
				    grid[j] = s.grid[j].clone();
				}
				
				//Add color
				for(int j = 0; j < 6; j++){
					if(grid[0][j] == ConnectFourState.FieldState.EMPTY){
						if(s.turn == ConnectFourState.FieldState.RED){
							grid[0][j] = ConnectFourState.FieldState.YELLOW;
							return new ConnectFourState(grid, ConnectFourState.FieldState.YELLOW);
						}else{
							grid[0][j] = ConnectFourState.FieldState.RED;
							return new ConnectFourState(grid, ConnectFourState.FieldState.RED);
						}
					}
				}
				
				return null;
			}
		});
		
		actions.add(new Action() {
			
			@Override
			public String toString() {
				return "1";
			}
			
			@Override
			public State execute(State state) {
				
				if(!(state instanceof ConnectFourState)) return null;
				ConnectFourState s = (ConnectFourState) state;
				
				//Copy grid
				ConnectFourState.FieldState [][] grid = new ConnectFourState.FieldState[s.grid.length][];
				for(int j = 0; j < grid.length; j++){
					grid[j] = s.grid[j].clone();
				}
				
				//Add color
				for(int j = 0; j < 6; j++){
					if(grid[1][j] == ConnectFourState.FieldState.EMPTY){
						if(s.turn == ConnectFourState.FieldState.RED){
							grid[1][j] = ConnectFourState.FieldState.YELLOW;
							return new ConnectFourState(grid, ConnectFourState.FieldState.YELLOW);
						}else{
							grid[1][j] = ConnectFourState.FieldState.RED;
							return new ConnectFourState(grid, ConnectFourState.FieldState.RED);
						}
					}
				}
				
				return null;
			}
		});
		
		actions.add(new Action() {
			
			@Override
			public String toString() {
				return "2";
			}
			
			@Override
			public State execute(State state) {
				
				if(!(state instanceof ConnectFourState)) return null;
				ConnectFourState s = (ConnectFourState) state;
				
				//Copy grid
				ConnectFourState.FieldState [][] grid = new ConnectFourState.FieldState[s.grid.length][];
				for(int j = 0; j < grid.length; j++){
					grid[j] = s.grid[j].clone();
				}
				
				//Add color
				for(int j = 0; j < 6; j++){
					if(grid[2][j] == ConnectFourState.FieldState.EMPTY){
						if(s.turn == ConnectFourState.FieldState.RED){
							grid[2][j] = ConnectFourState.FieldState.YELLOW;
							return new ConnectFourState(grid, ConnectFourState.FieldState.YELLOW);
						}else{
							grid[2][j] = ConnectFourState.FieldState.RED;
							return new ConnectFourState(grid, ConnectFourState.FieldState.RED);
						}
					}
				}
				
				return null;
			}
		});
		
		actions.add(new Action() {
			
			@Override
			public String toString() {
				return "3";
			}
			
			@Override
			public State execute(State state) {
				
				if(!(state instanceof ConnectFourState)) return null;
				ConnectFourState s = (ConnectFourState) state;
				
				//Copy grid
				ConnectFourState.FieldState [][] grid = new ConnectFourState.FieldState[s.grid.length][];
				for(int j = 0; j < grid.length; j++){
					grid[j] = s.grid[j].clone();
				}
				
				//Add color
				for(int j = 0; j < 6; j++){
					if(grid[3][j] == ConnectFourState.FieldState.EMPTY){
						if(s.turn == ConnectFourState.FieldState.RED){
							grid[3][j] = ConnectFourState.FieldState.YELLOW;
							return new ConnectFourState(grid, ConnectFourState.FieldState.YELLOW);
						}else{
							grid[3][j] = ConnectFourState.FieldState.RED;
							return new ConnectFourState(grid, ConnectFourState.FieldState.RED);
						}
					}
				}
				
				return null;
			}
		});
		
		actions.add(new Action() {
			
			@Override
			public String toString() {
				return "4";
			}
			
			@Override
			public State execute(State state) {
				
				if(!(state instanceof ConnectFourState)) return null;
				ConnectFourState s = (ConnectFourState) state;
				
				//Copy grid
				ConnectFourState.FieldState [][] grid = new ConnectFourState.FieldState[s.grid.length][];
				for(int j = 0; j < grid.length; j++){
					grid[j] = s.grid[j].clone();
				}
				
				//Add color
				for(int j = 0; j < 6; j++){
					if(grid[4][j] == ConnectFourState.FieldState.EMPTY){
						if(s.turn == ConnectFourState.FieldState.RED){
							grid[4][j] = ConnectFourState.FieldState.YELLOW;
							return new ConnectFourState(grid, ConnectFourState.FieldState.YELLOW);
						}else{
							grid[4][j] = ConnectFourState.FieldState.RED;
							return new ConnectFourState(grid, ConnectFourState.FieldState.RED);
						}
					}
				}
				
				return null;
			}
		});
		
		actions.add(new Action() {
			
			@Override
			public String toString() {
				return "5";
			}
			
			@Override
			public State execute(State state) {
				
				if(!(state instanceof ConnectFourState)) return null;
				ConnectFourState s = (ConnectFourState) state;
				
				//Copy grid
				ConnectFourState.FieldState [][] grid = new ConnectFourState.FieldState[s.grid.length][];
				for(int j = 0; j < grid.length; j++){
					grid[j] = s.grid[j].clone();
				}
				
				//Add color
				for(int j = 0; j < 6; j++){
					if(grid[5][j] == ConnectFourState.FieldState.EMPTY){
						if(s.turn == ConnectFourState.FieldState.RED){
							grid[5][j] = ConnectFourState.FieldState.YELLOW;
							return new ConnectFourState(grid, ConnectFourState.FieldState.YELLOW);
						}else{
							grid[5][j] = ConnectFourState.FieldState.RED;
							return new ConnectFourState(grid, ConnectFourState.FieldState.RED);
						}
					}
				}
				
				return null;
			}
		});
		
		actions.add(new Action() {
			
			@Override
			public String toString() {
				return "6";
			}
			
			@Override
			public State execute(State state) {
				
				if(!(state instanceof ConnectFourState)) return null;
				ConnectFourState s = (ConnectFourState) state;
				
				//Copy grid
				ConnectFourState.FieldState [][] grid = new ConnectFourState.FieldState[s.grid.length][];
				for(int j = 0; j < grid.length; j++){
					grid[j] = s.grid[j].clone();
				}
				
				//Add color
				for(int j = 0; j < 6; j++){
					if(grid[6][j] == ConnectFourState.FieldState.EMPTY){
						if(s.turn == ConnectFourState.FieldState.RED){
							grid[6][j] = ConnectFourState.FieldState.YELLOW;
							return new ConnectFourState(grid, ConnectFourState.FieldState.YELLOW);
						}else{
							grid[6][j] = ConnectFourState.FieldState.RED;
							return new ConnectFourState(grid, ConnectFourState.FieldState.RED);
						}
					}
				}
				
				return null;
			}
		});


		
	}
}
