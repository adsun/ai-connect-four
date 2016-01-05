package search;

public abstract class Action {
	
	/**
	 * 
	 * @param state
	 * @return new state or null if state is invalid
	 */
	public abstract State execute(State state);
	
	@Override
	public abstract String toString();
}
