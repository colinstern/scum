package scum_client;

import java.net.*;
import java.io.*;

public class ReadThread extends Thread {
	BufferedReader in = null;
	String fromServer = null;
	
	public ReadThread(BufferedReader in) {
		super("ReadThread");
		this.in = in;
	}
	
	public void run() {
		try {
		 while (true) {
			 if((fromServer = in.readLine()) != null)
				System.out.println(fromServer);
		 }
		}
		catch (IOException e) {
			 System.err.println("Couldn't read! " + e.getMessage());
		            System.exit(1);
		}
	}
}
