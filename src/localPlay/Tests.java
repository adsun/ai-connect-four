package localPlay;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import connectFour.ConnectFourProblem;
import connectFour.ConnectFourState;
import connectFour.heuristics.ExperimentalHeuristic;
import connectFour.heuristics.Heuristic;
import search.Action;
import search.AlphaBetaSearch;
import search.State;


public class Tests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//State[][][][] results = new State[2][2][8][8];
		//analyseAIs();
		//AiVsPlayer(new ExperimentalHeuristic(),7);
		//System.out.println("Runnig");
		analyseHeuristics(new ExperimentalHeuristic(), new ExperimentalHeuristic(), 0, 7);
		//aiVsAi(new ExperimentalHeuristic(), new ExperimentalHeuristic(), 5, 7,true);
		//AiVsRandom(6, 5);
		
	}
	
	public static void AiVsRandom(Heuristic heuristic, int maxDepth){
		ConnectFourProblem problem = new ConnectFourProblem(ConnectFourState.FieldState.RED, heuristic);
		AlphaBetaSearch player = new AlphaBetaSearch(problem, maxDepth);
		
		Action action;
		ConnectFourState currentState = (ConnectFourState) problem.getInitialState();
		Random random = new Random();
		int i = 0;
		boolean running = true;
		while(running){
			System.out.println("Step: "+i);
			//System.out.println("Current State: ");
			//long startTime = System.currentTimeMillis();

			//RandomTurn turn
			ArrayList<Action> actions = problem.getActions(currentState);
			action = actions.get(random.nextInt(actions.size()));
			currentState = (ConnectFourState) action.execute(currentState);
			if(problem.goalTest(currentState)){
				System.out.println("Random Wins!");
				System.out.println(currentState.toString());
				return;
			}
			//System.out.println("TurnA");
			//problem.experimentingUtility(currentState, true);
			System.out.println(currentState.toString());
			
			//AI turn
			action = player.search(currentState);
			currentState = (ConnectFourState) action.execute(currentState);
			if(problem.goalTest(currentState)){
				System.out.println("AI Wins!");
				System.out.println(currentState.toString());
				return;
			}
			//System.out.println("TurnB");
			//problem.experimentingUtility(currentState, true);
			System.out.println(currentState.toString());
			
			//Check if the field is full
			boolean foundEmpty = false;
			for(int column = 0; column < 7; column++){
				for(int row = 0; row < 6; row++){
					if(currentState.grid[column][row] == ConnectFourState.FieldState.EMPTY){
						foundEmpty = true;
						break;
					}
				}
			}
			if(!foundEmpty) running = false;
			i++;
		}
		
		System.out.println("Draw");
		return;
		
	}
	
	public static void AiVsPlayer(Heuristic heuristic, int maxDepth){
		ConnectFourProblem problemA = new ConnectFourProblem(ConnectFourState.FieldState.RED, heuristic);
		AlphaBetaSearch playerA = new AlphaBetaSearch(problemA, maxDepth);
		
		ConnectFourProblem problemB = new ConnectFourProblem(ConnectFourState.FieldState.YELLOW , heuristic);
		
		Scanner scanner = new Scanner(System.in);
		
		State currentState = problemA.getInitialState();

		Action action;
		
		for(int i = 0; i < 100; i++){
			System.out.println("Step: "+i);
			//System.out.println("Current State: ");
			//long startTime = System.currentTimeMillis();

			//PlayerA turn
			action = playerA.search(currentState);
			currentState = action.execute(currentState);
			if(problemA.goalTest(currentState)) break;
			
			System.out.println("TurnA");
			//problemA.experimentingUtility(currentState, true);
			System.out.println(currentState.toString());

			
			//Player turn
			System.out.println("Choose column: ");
			action = problemB.actions.get(scanner.nextInt());
			System.out.println("Chose: "+action.toString());
			currentState = action.execute(currentState);
			if(problemB.goalTest(currentState)) break;
			
			System.out.println("TurnB");
			//problemB.experimentingUtility(currentState, true);
			System.out.println(currentState.toString());
			
		}
		scanner.close();
		
		System.out.println("Solved:");
		System.out.println(currentState.toString());
	}
	
	public static void analyseHeuristics(Heuristic a, Heuristic b, int minDepth, int maxDepth){
		char[] sym = {'a','b', '_'};
		int[][] winner = new int[maxDepth+1][maxDepth+1];
		for(int i = minDepth; i <= maxDepth; i++){
			for(int j = minDepth; j <= maxDepth; j++){
				winner[i][j] = aiVsAi(a, b, i, j);
			}
		}
		
		System.out.print("a\\b"+" ");
		for(int i = minDepth; i <= maxDepth; i++){
			System.out.print(i+" ");
		}
		System.out.println();
		
		
		for(int i = minDepth; i <= maxDepth; i++){
			System.out.print(" "+i+"  ");
			for(int j = minDepth; j <= maxDepth; j++){
				System.out.print(sym[winner[i][j]]+" ");
			}
			System.out.println();
		}
		
	}
	
	public static void analyseAIs(){
		int[][][][] winner = new int[2][2][7][7];
		
		for(int da = 0; da < winner.length; da++){
			for(int db = 0; db < winner[da].length; db++){
				for(int dea = 0; dea < winner[da][db].length; dea++){
					for(int deb = 0; deb < winner[da][db][dea].length; deb++){
						//TODO fix
						//winner[da][db][dea][deb] = aiVsAi(da, db, dea, deb);
					}
				}
			}
		}
		
		//Print result
		System.out.print("da+db\t");
		for(int dea = 0; dea < 20; dea++){
			for(int deb = 0; deb < 20; deb++){
				System.out.print(dea+"+"+deb+"\t");
			}
		}
		System.out.print("\n");
		for(int da = 0; da < winner.length; da++){
			for(int db = 0; db < winner[da].length; db++){
				System.out.print(da+"+"+db+"\t");
				for(int dea = 0; dea < winner[da][db].length; dea++){
					for(int deb = 0; deb < winner[da][db][dea].length; deb++){
						System.out.print(winner[da][db][dea][deb]+"\t");
					}
				}
				System.out.print("\n");
			}
		}
	}
	
	
	public static int aiVsAi(Heuristic heuristicA, Heuristic heuristicB, int maxDepthA, int maxDepthB){
		return aiVsAi(heuristicA, heuristicB, maxDepthA, maxDepthB, false);
	}
	/**
	 * PlayerA always begins.
	 * @param difficultyA
	 * @param difficultyB
	 * @param maxDepthA
	 * @param maxDepthB
	 * @return 0 if A wins, 1 if b wins, 3 if draw.
	 */
	public static int aiVsAi(Heuristic heuristicA, Heuristic heuristicB, int maxDepthA, int maxDepthB, boolean log){
		ConnectFourProblem problemA = new ConnectFourProblem(ConnectFourState.FieldState.RED, heuristicA);
		AlphaBetaSearch playerA = new AlphaBetaSearch(problemA, maxDepthA);
		
		ConnectFourProblem problemB = new ConnectFourProblem(ConnectFourState.FieldState.YELLOW , heuristicB);
		AlphaBetaSearch playerB = new AlphaBetaSearch(problemB, maxDepthB);
		
		ConnectFourState currentState = (ConnectFourState) problemA.getInitialState();

		Action action;
		
		boolean running = true;
		
		int i = 0;
		while(running){
			if(log) System.out.println("Step: "+i);
			//System.out.println("Current State: ");
			//long startTime = System.currentTimeMillis();

			//PlayerA turn
			action = playerA.search(currentState);
			if(action == null) break;
			currentState = (ConnectFourState) action.execute(currentState);
			if(problemA.goalTest(currentState)){
				if(log) System.out.println("A Wins!");
				if(log) System.out.println(currentState.toString());
				return 0;
			}
			//System.out.println("TurnA");
			//problemB.experimentingUtility(currentState, true);
			if(log) System.out.println(currentState.toString());
			
			if(isFull(currentState)) break;
			//PlayerB turn
			action = playerB.search(currentState);
			currentState = (ConnectFourState) action.execute(currentState);
			if(problemB.goalTest(currentState)){
				if(log) System.out.println("B Wins!");
				if(log) System.out.println(currentState.toString());
				return 1;
			}
			//System.out.println("TurnB");
			//problemB.experimentingUtility(currentState, true);
			if(log) System.out.println(currentState.toString());
			
			//Check if the field is full
			if(isFull(currentState)) break;
			i++;
		}
		
		if(log) System.out.println("Draw");
		return 2;
		
		//System.out.println("Solved:");
		
	}
	
	private static boolean isFull(ConnectFourState state){
		boolean foundEmpty = false;
		for(int column = 0; column < 7; column++){
			for(int row = 0; row < 6; row++){
				if(state.grid[column][row] == ConnectFourState.FieldState.EMPTY){
					foundEmpty = true;
					break;
				}
			}
		}
		
		return !foundEmpty;
	}

}
