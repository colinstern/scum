package scum;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Runs an instance of the game.
 * @author cost
 *
 */
public class GameRunner {
	

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to Scum.\n");
		int n = 0;
		while (true) {
			try {
				System.out.println("How many players?\n");
				n = scanner.nextInt();
				if ((n < 2) | (n > 10)) {
					System.out.println("Please enter a number between 2 and 10.\n");
					throw new Exception("Number is out of bounds.\n");
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Please enter a number.\n");
			} catch (Exception e) {
			}
		}
		
			Game game = new Game(n);
			game.start();
			
			scanner.close();

	}

}
