package scum_client;

import java.net.*;
import java.io.*;

public class WriteThread extends Thread {
	PrintWriter out = null;
	BufferedReader stdIn;
	String fromUser = null;
	
	public WriteThread(PrintWriter out, BufferedReader stdIn) {
		super("WriteThread");
		this.out = out;
		this.stdIn = stdIn;
	}
	
	public void run() {
		try {
		 while (true) {
			 fromUser = stdIn.readLine();
             if ((fromUser != null) && !fromUser.equals("quit")) {
             	//Send to server
                 out.println(fromUser);
             }
             else if (fromUser.equals("quit")) {
            	 System.out.println("Quitting Scum.");
            	 break;
             }
		 }
		}
		catch (IOException e) {
			 System.err.println("Couldn't write! " + e.getMessage());
		            System.exit(1);
		}
	}
}
