package scum;

import java.net.*;
import java.util.ArrayList;
import java.io.*;


public class MultiServerThread extends Thread {
    private Socket socket = null;
    private ArrayList<Player> players = null;

    public MultiServerThread(Socket socket, ArrayList<Player> players) {
        super("MultiServerThread");
        this.socket = socket;
        this.players = players;
    }
    
    public void run() {

        try (
        		/* Open reading and writing to the client */
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                        socket.getInputStream()));
            ) {
        	/* Initialize player */
        	 String inputLine, outputLine;
         	 Player thisPlayer = new Player(players.size() + 1);
         	 players.add(thisPlayer);
         	/* Possible concurrency bug: players may end up having the same id. This should not affect gameplay
         	 * though because giveTurn() uses pointers to each player */
        	 outputLine = "Welcome to Scum, Player " + thisPlayer.getId() + ".";
        	 out.println(outputLine);
        	 
        	 /* Continue until game is over, when only one player remains */
            while (players.size() > 1) {
            	/* Check if it is the player's turn */
            	if (thisPlayer.isTurn()) {
            		//Print that it is this player's turn once
            		outputLine = "\nPlayer " +  thisPlayer.getId()+ ", it is your turn.\n";
            		out.println(outputLine);
            		while(thisPlayer.isTurn()) {
            			//Wait for message from giveTurn
            			while (!thisPlayer.isMessageToClient());
            			
            			//Print message to client
            			outputLine = thisPlayer.getMessageToClient();
            			out.println(outputLine);
            			
            			//Wait for client's response
            			inputLine = in.readLine();
            			
            			//Send client's response to giveTurn - use flags in the player object to communicate
            			thisPlayer.sendMessageToGame(inputLine);
            			
            			//Repeat until makeMove is called, then break - makeMove should set isTurn to false
            		}
            	}
            	//Allow socials at all times while game is playing
            }
            //Print scoreboard and this player's score
        	 
        	 
        	 
             socket.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ScumException e) {
            System.err.println(e.getMessage());
        }
    }
}
