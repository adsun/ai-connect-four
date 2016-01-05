package webServicePlay;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import connectFour.ConnectFourProblem;
import connectFour.ConnectFourState;
import connectFour.heuristics.BasicHeuristic;
import connectFour.heuristics.ExperimentalHeuristic;
import connectFour.heuristics.Heuristic;
import connectFour.heuristics.deprecated.AdvancedHeuristic;
import connectFour.heuristics.deprecated.ExpertUtility;
import connectFour.heuristics.deprecated.ExpertUtility2;
import search.Action;
import search.AlphaBetaSearch;


public class OnlinePlayer {
	
	private static final String keyId = "playerID";
	private static final String keyName = "playerName";
	private static final String keyAuthor = "playerAuthor";
	private static final String keyToken = "playerToken";
	
	private String id;
	private String name;
	private String author;
	private String token;
	
	//TODO remove main for testing
	public static void main(String[] args) {
		final String baseURL = "http://134.93.175.116:6008";
		
		//p.storeFile();
		//OnlinePlayer p = loadPlayerFile("testI2d1");
		final String gameID = OnlinePlayer.createGame(baseURL);
		
		Thread t1 = new Thread(new Runnable() {
		     public void run() {
		    	 OnlinePlayer p = createPlayer("testId1", "testName1", "g08", baseURL);
		    	 //p.playGame(gameID, baseURL, new ExperimentalHeuristic(),7);
		    	 p.playGame(gameID, baseURL, new ExperimentalHeuristic(),7);
		     }
		});  
		t1.start();
		
		
		try {
			Thread.sleep(2000);
			System.out.println("Enter to add 2nd player!");
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread t2 = new Thread(new Runnable() {
		     public void run() {
		    	 OnlinePlayer p2 = createPlayer("testId2", "testName2", "g08", baseURL);
		    	 p2.playGame(gameID, baseURL, new ExperimentalHeuristic(), 0);
		     }
		});  
		t2.start();
		
	}
	

	public OnlinePlayer(String id, String name, String author, String token){
		this.id = id;
		this.name = name;
		this.author = author;
		this.token = token;
	}
	
	public void playGame(String gameID, String baseURL, Heuristic heuristic, int maxDepth){
		AlphaBetaSearch search;
		ConnectFourProblem problem;
		String responseString;
		JSONObject game;
		JSONArray board;
		ConnectFourState currentState;
		
		//Register player in game
		responseString = WebWrapper.put(baseURL+"/games/"+gameID, keyId+"="+id);
		JSONObject response = new JSONObject(responseString);
		
		if(response.get("status").equals("error")){
			System.out.println("Failed to register Player.");
			System.out.println(response.getString("return"));
			return;
		}else if(response.get("status").equals("ok")){
			System.out.println("Registered Player!");
			System.out.println(response.getString("return"));
		}
		
		//Wait for game to start
		while(true){
			//Sleep
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Check if game started
			responseString = WebWrapper.get(baseURL+"/games/"+gameID);
			game = new JSONObject(responseString);
			//Check if the game is created and the next player is set
			//if(game.get("status").equals("created") && !game.get("nextPlayer").equals("null")){
			if(game.get("nextPlayer").equals(id)){
				System.out.println(game.get("nextPlayerColor"));
				if(game.get("nextPlayerColor").equals("red")){
					problem = new ConnectFourProblem(ConnectFourState.FieldState.RED, heuristic);
				}else{
					problem = new ConnectFourProblem(ConnectFourState.FieldState.YELLOW, heuristic);
				}

				search = new AlphaBetaSearch(problem, maxDepth);
				break;
			}
		}
		
		//Game loop
		boolean running = true;
		while(running){
			
			//Convert board to ConnectFourstate
			currentState = (ConnectFourState) problem.getInitialState();
			
			board = game.getJSONArray("board");
			for(int i = 0; i < board.length(); i++){
				JSONObject column = board.getJSONObject(i);
				int x = column.getInt("x");
				JSONArray entries = column.getJSONArray("column");
				for(int j = 0; j < entries.length();j++){
					int y = entries.getJSONObject(j).getInt("y");
					String color = entries.getJSONObject(j).getString("color");
					//if()
					currentState.grid[x][y] = ConnectFourState.FieldState.fromString(color);
				}
			}
			System.out.println("Got state:");
			System.out.println(currentState.toString());
			
			//Evaluate next turn
			Action action = search.search(currentState);
			
			
			//Random random = new Random();
			//String move = random.nextInt(7)+"";
			
			//Do turn
			//-d "playerID=SomeID1" -d "playerToken=c25d85ed5c" -d "gameMove=3"
			System.out.println(id+" Performing Action: "+ action.toString());
			responseString = WebWrapper.post(baseURL+"/games/"+gameID, keyId+"="+id, keyToken+"="+token,"gameMove="+action.toString());
			System.out.println(responseString);
			
			
			//Wait for own turn
			while(true){
				//Sleep
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				responseString = WebWrapper.get(baseURL+"/games/"+gameID);
				game = new JSONObject(responseString);
				//Check for game over
				if(!game.get("status").equals("playing")){
					System.out.println(game);
					running = false;
					break;
				}
				//Check if next player is id
				if(game.get("nextPlayer").equals(id)){
					break;
				}
			}
		}
		System.out.println(id+" Game Over");
	}
	
	/**
	 * Creates a new player on the server.
	 * @param id
	 * @param name
	 * @param author
	 * @param baseURL
	 * @return created player
	 */
	public static OnlinePlayer createPlayer(String id, String name, String author, String baseURL){
		if(id == null || name == null || author == null || baseURL == null){
			throw new IllegalArgumentException("id, name, author or baseURL are null");
		}
		
		//TODO Check if player ID already exists
		
		//Send Post
		String responseString = WebWrapper.post(baseURL+"/players", keyId+"="+id, keyName+"="+name, keyAuthor+"="+author);
		//String responseString = WebWrapper.post(baseURL+"/players", "playerID="+id);
		JSONObject response = new JSONObject(responseString);

		if(response.get("status").equals("error")){
			System.out.println(response.toString());
		}else if(response.get("status").equals("ok")){
			System.out.println("Created Player!");
			
			//Create player
			String token = response.getJSONObject("return").getString("playerToken");
			System.out.println("Token: "+token);
			
			OnlinePlayer player = new OnlinePlayer(id, name, author, token);
			return player;
		}
		
		
		
		return null;
	}
	
	/**
	 * Stores player to a file in "players/"
	 * @return false if the file could not be written, else ture.
	 */
	public boolean storeFile(){
		File playerFile = new File("players/"+id+".json");
		//Create players dir if it does not exist
		if(playerFile.getParentFile() != null) playerFile.getParentFile().mkdir();
		
		JSONObject playerObject = new JSONObject();
		playerObject.put(keyId, id);
		playerObject.put(keyName, name);
		playerObject.put(keyAuthor, author);
		playerObject.put(keyToken, token);
		

		
		//Write player to file
		try(FileWriter writer = new FileWriter(playerFile)){
			writer.write(playerObject.toString());
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Loads player from file.
	 * @param id of player to load
	 * @return player with given id
	 */
	public static OnlinePlayer loadPlayerFile(String id){
		File playerFile = new File("players/"+id+".json");
		if(playerFile.getParentFile() != null) playerFile.getParentFile().mkdir();
		
		try (Scanner scanner = new Scanner(playerFile)){
			JSONObject playerObject = new JSONObject(scanner.nextLine());
			System.out.println(playerObject.toString());
			return new OnlinePlayer(playerObject.getString(keyId), playerObject.getString(keyName), playerObject.getString(keyAuthor), playerObject.getString(keyToken));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return null;
	}
	
	public static String createGame(String baseURL){
		//Send Post
		String responseString = WebWrapper.post(baseURL+"/games");
		//String responseString = WebWrapper.post(baseURL+"/players", "playerID="+id);
		JSONObject response = new JSONObject(responseString);

		if(response.get("status").equals("error")){
			System.out.println(response.toString());
		}else if(response.get("status").equals("ok")){
			System.out.println("Created Game!");
			
			//Create player
			String gameID = response.getJSONObject("return").getString("gameID");
			System.out.println("gameID: "+gameID);
			
			return gameID;
		}
		
		return null;
	}
}
