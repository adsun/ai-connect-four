package webServicePlay;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class WebWrapper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
		
	public static String put(String url){
		int c = 0;
		while(c < 5){
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
				// optional default is GET
				con.setRequestMethod("PUT");
	
				//int responseCode = con.getResponseCode();
				//System.out.println("\nSending 'PUT' request to URL : " + url);
				//System.out.println("Response Code : " + responseCode);
	
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
	
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
	
				//print result
				//System.out.println(response.toString());
				return response.toString();
				
			} catch (IOException e) {
				c++;
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (Exception e){
				c++;
				System.out.println("Cought Exception");
				if(e instanceof ConnectException){
					System.out.println("Cought ConnectException: "+c);
				}
			}
		}
		return null;
	}
	
	public static String put(String url, String... parameters){
		
		int c = 0;
		while(c < 5){
		
			try {
				//String url = "http://"+HOST+":"+PORT+"/searches";
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				//add reuqest header
				con.setRequestMethod("PUT");
				con.setRequestProperty("Accept", "application/json");
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				// Send post request
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				for(String parameter: parameters){
					wr.writeBytes(parameter+"&");
				}
				wr.flush();
				wr.close();

				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				//print result
				return response.toString();
			} catch (Exception e) {
				c++;
				c++;
				System.out.println("Cought Exception. Try: "+c);
				e.printStackTrace();
			}
			
		}
			
		return "";
	}
	
	public static String put2(String url_,String key, String value){
		int c = 0;
		while(c < 5){
		   String line;
		   StringBuffer jsonString = new StringBuffer();
		    try {

		        URL url = new URL(url_);

		        //escape the double quotes in json string
		        String payload=key+"="+value;
		        //System.out.println(url_);
		        //System.out.println(payload);

		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		        connection.setDoInput(true);
		        connection.setDoOutput(true);
		        connection.setRequestMethod("PUT");
		        connection.setRequestProperty("Accept", "application/json");
		        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		        writer.write(payload);
		        writer.close();
		        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        while ((line = br.readLine()) != null) {
		                jsonString.append(line);
		        }
		        br.close();
		        connection.disconnect();
		        return jsonString.toString();
		    } catch (Exception e) {
		    	c++;
		            throw new RuntimeException(e.getMessage());
		    }
		    
		}
		return "";
	}
	
	
	public static String get(String url){
		int c = 0;
		while(c < 5){
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				// optional default is GET
				con.setRequestMethod("GET");

				//int responseCode = con.getResponseCode();
				//System.out.println("\nSending 'GET' request to URL : " + url);
				//System.out.println("Response Code : " + responseCode);

				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				//print result
				//System.out.println(response.toString());
				return response.toString();
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e){
				System.out.println("Cought Exception");
				if(e instanceof ConnectException){
					c++;
					System.out.println("Cought ConnectException: "+c);
				}
			}
		}
		
		return null;
	}

	public static String post(String url, String... parameters){
		
		int c = 0;
		while(c < 5){
		
			try {
				//String url = "http://"+HOST+":"+PORT+"/searches";
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				//add reuqest header
				con.setRequestMethod("POST");
				con.setRequestProperty("Accept", "application/json");
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				// Send post request
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				for(String parameter: parameters){
					wr.writeBytes(parameter+"&");
				}
				wr.flush();
				wr.close();

				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				//print result
				return response.toString();
			} catch (Exception e) {
				c++;
				c++;
				System.out.println("Cought Exception. Try: "+c);
				e.printStackTrace();
			}
			
		}
			
		return "";
	}

}
