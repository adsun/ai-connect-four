package search;


public abstract class State {
	
	/**
	 * Indicates if a state is equal to another.
	 * @param state to compare with.
	 * @return true if both states are equal.
	 */
	public abstract boolean equals(State other);
	
	@Override
	public boolean equals(Object other){
		if(other == null) return false;
		if(other == this) return true;
		if(!(other instanceof State)) return false;
		State otherState = (State) other;
		return equals(otherState);
	}
	
	@Override
	public abstract int hashCode();
	
	/**
	 * 
	 * @return true if state is valid.
	 */
	public abstract boolean isValid();
	
	@Override
	public abstract String toString();
	
	
}
