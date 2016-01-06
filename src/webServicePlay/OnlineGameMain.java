package webServicePlay;

import connectFour.heuristics.BasicHeuristic;
import connectFour.heuristics.AdvancedHeuristic;
import connectFour.heuristics.Heuristic;

public class OnlineGameMain {

	public static void main(String[] args) {
		if(args.length == 0){
			printUsage();
			return;
		}
		String baseURL = args[0];
		OnlinePlayer player = null;
		String gameID = null;
		boolean join = false;
		int depth = 7;
		Heuristic heuristic = new AdvancedHeuristic();
		
		
		//Parse options
		for(int i = 1; i < args.length; i++){
			
			//Load player from file
			if(args[i].equals("-l")){
				player = OnlinePlayer.loadPlayerFile(args[i+1]);
			}
			
			//Create and register player
			if(args[i].equals("-c")){
				//Register
				player = OnlinePlayer.createPlayer(args[i+1], args[i+2], args[i+3], baseURL);
				if(!player.storeFile()) System.out.println("Could not write player to file.");
			}
			
			if(args[i].equals("-g")){
				gameID = OnlinePlayer.createGame(baseURL);
				if(gameID == null) System.out.println("Could not create game");
			}
			
			if(args[i].equals("-j")){
				join = true;
				if(gameID == null) gameID = args[i+1];
			}
			
			if(args[i].equals("-d")){
				depth = Integer.parseInt(args[i+1]);
			}
			
			if(args[i].equals("-h")){
				if(args[i+1].equals("1")) heuristic = new BasicHeuristic();
			}

		}
		
		
		if(join && baseURL != null &&player != null && gameID != null){
			player.playGame(gameID, baseURL, heuristic, depth);			
		}else{
			System.out.println(join + ", "+baseURL +", "+player.toString() +", "+gameID);
			printUsage();
		}
		
	}
	
	private static void printUsage(){
		System.out.println("Usage: ");
		System.out.println("[http://ip:port] [-options]");
		System.out.println("Options: ");
		System.out.println("\t -l [playerId] | Loads player from file");
		System.out.println("\t -g | creates game");
		System.out.println("\t -c [id] [name] [author] | Creates, registers and stores player");
		System.out.println("\t -j [gameId] | joins game with given id or the created game if -g was added before!");
		System.out.println("\t -d [depth] | sets the search depth. Default 7.");
		System.out.println("\t -h [heuristic(0,1)] | 0: expert (default), 1: basic");
	}

}
