package localPlay;
import java.util.ArrayList;

import connectFour.ConnectFourProblem;
import connectFour.ConnectFourState;
import connectFour.heuristics.AdvancedHeuristic;
import search.Action;
import search.AlphaBetaSearch;
import search.State;


public class TestManual {

	public static void main(String[] args) {

		
		
		//Test horizontal left and right
		//int actionsX[] = {1,2,3,4,1};
		//int actionsO[] = {2,3,5,4,3};
		int actionsX[] = {3};
		int actionsO[] = {4};
		playActions(actionsX, actionsO);
		//debugAlphaBeta();
		
		/*
		State s1 = actions.get(2).execute(s);
		System.out.println(s1.toString());
		System.out.println("Utility: "+p.utility(s1)+" Goal: "+p.goalTest(s1));
		s1 = actions.get(6).execute(s1);
		System.out.println(s1.toString());
		System.out.println("Utility: "+p.utility(s1)+" Goal: "+p.goalTest(s1));
		s1 = actions.get(2).execute(s1);
		System.out.println(s1.toString());
		System.out.println("Utility: "+p.utility(s1)+" Goal: "+p.goalTest(s1));
		s1 = actions.get(6).execute(s1);
		System.out.println(s1.toString());
		System.out.println("Utility: "+p.utility(s1)+" Goal: "+p.goalTest(s1));
		s1 = actions.get(2).execute(s1);
		System.out.println(s1.toString());
		System.out.println("Utility: "+p.utility(s1)+" Goal: "+p.goalTest(s1));
		s1 = actions.get(6).execute(s1);
		System.out.println(s1.toString());
		System.out.println("Utility: "+p.utility(s1)+" Goal: "+p.goalTest(s1));
		*/

	}
	
	private static void debugAlphaBeta(){
		//double 
		ConnectFourProblem p = new ConnectFourProblem(ConnectFourState.FieldState.RED, new AdvancedHeuristic());
		State s = p.getInitialState();
		ArrayList<Action> actions = p.getActions(s);
		AlphaBetaSearch search = new AlphaBetaSearch(p, 0);

		s = actions.get(3).execute(s);
		s = actions.get(0).execute(s);
		
		s = actions.get(4).execute(s);
		s = actions.get(6).execute(s);
		
		s = actions.get(5).execute(s);
		//s = actions.get(2).execute(s);
		
		System.out.println(s.toString());
		System.out.println("Start searching..");
		Action a = search.search(s);
		s = a.execute(s);
		System.out.println("Result:");
		System.out.println("Utility: "+p.utility(s));
		System.out.println(s);
	}
		
	private static void playActions(int[] actionsX, int[] actionsO){
		if(actionsX.length != actionsO.length) return;
		AdvancedHeuristic h = new AdvancedHeuristic(true);
		ConnectFourProblem p = new ConnectFourProblem(ConnectFourState.FieldState.RED, new AdvancedHeuristic());
		State s = p.getInitialState();
		ArrayList<Action> actions = p.getActions(s);
		
		for(int i = 0; i < actionsX.length; i++){
			System.out.println("==================");
			s = actions.get(actionsX[i]).execute(s);
			//System.out.println("Utility: "+h.evaluate(s)+" Goal: "+p.goalTest(s));
			System.out.println(s.toString());
			s = actions.get(actionsO[i]).execute(s);
			
			
			//System.out.println("Utility: "+h.evaluate(s)+" Goal: "+p.goalTest(s));
			System.out.println(s.toString());
			System.out.println("==================");
		}
		
	}
	

}
