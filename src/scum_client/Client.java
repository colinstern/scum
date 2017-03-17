package scum_client;

import java.io.*;
import java.net.*;

public class Client {

	public static void main(String[] args) throws IOException {
		
		 if (args.length != 2) {
	            System.err.println(
	                "Usage: java Client <host name> <port number>");
	            System.exit(1);
	        }
	 
	        String hostName = args[0];
	        int portNumber = Integer.parseInt(args[1]);
	        //but I have actually pre-defined the port number in the server
	        portNumber = 4444;
	 
	        try (
	        	//Connect to the server
	            Socket kkSocket = new Socket(hostName, portNumber);
	            PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
	            BufferedReader in = new BufferedReader(
	                new InputStreamReader(kkSocket.getInputStream()));
	        ) {
	            BufferedReader stdIn =
	                new BufferedReader(new InputStreamReader(System.in));

	            new ReadThread(in).start();
	            new WriteThread(out, stdIn).start();
	            
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host " + hostName);
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " +
	                hostName);
	            System.exit(1);
	        }
	    }
}
