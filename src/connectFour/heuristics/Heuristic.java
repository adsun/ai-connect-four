package connectFour.heuristics;

import connectFour.ConnectFourState;
import connectFour.ConnectFourState.FieldState;

public abstract class Heuristic {
	
	public abstract double evaluate(ConnectFourState state, FieldState player);
	
}
