package search;



import java.util.ArrayList;

public abstract class Problem {
	

	/**
	 * 
	 * @return initial State of the problem.
	 */
	public abstract State getInitialState();

	/**
	 * Tests if the passed state meets the goal requirements.
	 * @param state
	 * @return true if state is a goal.
	 */
	public abstract boolean goalTest(State state);

	/**
	 * 
	 * @param state
	 * @return ArrayList of valid actions on state.
	 */
	public abstract ArrayList<Action> getActions(State state);

	/**
	 * Performs a action on a state.
	 * @param action
	 * @param state
	 * @return resulting state.
	 */
	public State performAction(Action action, State state) {
		return action.execute(state);
	}

	/**
	 * Calculates the cost for one step from a specific state for a specific action.
	 * @param state
	 * @param action
	 * @return cost for step.
	 */
	public abstract double getStepCost(State state, Action action);

	public Object getChildren(State state) {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract double utility(State state);
	
	

}