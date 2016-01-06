package search;

import connectFour.ConnectFourState;
import connectFour.heuristics.AdvancedHeuristic;


public class AlphaBetaSearch {
	

	/*
	public static void main(String[] args) {
		ConnectFourProblem p = new ConnectFourProblem(ConnectFourState.FieldState.RED);
		AlphaBetaSearch a = new AlphaBetaSearch(p);
		State i = p.getInitialState();
		//System.out.println(p.getActions(i).get(0).execute(i).toString());
		//System.out.println(i.toString());
		System.out.println("Start serach");
		long startTime = System.currentTimeMillis();
		Action action = a.search(i);
		System.out.println("Runtime: "+ (System.currentTimeMillis()-startTime));
		System.out.println(action);
		System.out.println(action.execute(i));
	}
	*/
	private int maxDepth =5;	
	private Problem problem;
	private double[] weights;
	
	public AlphaBetaSearch(Problem p, int maxDepth){
		this.problem = p;
		this.maxDepth = maxDepth;
	}
	
	public AlphaBetaSearch(Problem p, int maxDepth, double[] weights){
		this.problem = p;
		this.maxDepth = maxDepth;
		this.weights = weights;
	}
	
	
	
	public Action search(State state){
		AdvancedHeuristic h = null;
		if(weights!= null) h = new AdvancedHeuristic(weights,true);
		Action maxAction = null;
		double maxValue = Double.NEGATIVE_INFINITY;
		//System.out.println("SEARCHING######################");
		for(Action a : problem.getActions(state)){
			double v = minV(a.execute(state), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, maxDepth);
			if(weights!=null) System.out.println("MaxV: " + v +" Eval: " +h.evaluate((ConnectFourState)a.execute(state), ((ConnectFourState)state).turn));
			//else System.out.println("MaxV: " + v);
			//h.evaluate(a.execute(state));
			//System.out.println(a.execute(state));
			if(v > maxValue){
				//System.out.println("New max Action:"+a+"  v: "+v);
				maxValue = v;
				maxAction = a;
			}
		}
		return maxAction;
	}
	
	private double maxV(State state, double alpha, double beta, int depth){
		//System.out.println("max: "+depth);
		if(problem.goalTest(state) || depth == 0) return problem.utility(state);
		double v = Double.NEGATIVE_INFINITY;
		for(Action a : problem.getActions(state)){
			v = Math.max(v, minV(a.execute(state), alpha, beta, depth-1));
			if(v >= beta) return v;
			alpha = Math.max(alpha, v);
		}
		return v;
	}
	
	private double minV(State state,  double alpha, double beta, int depth){
		//System.out.println("min: "+depth);
		if(problem.goalTest(state) || depth == 0) return problem.utility(state);
		double v = Double.POSITIVE_INFINITY;
		for(Action a : problem.getActions(state)){
			v = Math.min(v, maxV(a.execute(state), alpha, beta, depth-1));
			if(v <= alpha) return v;
			beta = Math.min(beta, v);
		}
		return v;
	}	
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 