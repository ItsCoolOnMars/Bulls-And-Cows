import java.util.Scanner;

// Task Overview

// Bulls and Cows is an old code-breaking game for two players.
//
// One of the players writes a 4-digit secret number. The digits must be all different, digit zero (0) is not allowed.
// Then the other player tries to guess their opponent's number who gives the number of matches.
// If the matching digits are on their right positions, they are "bulls", if on different positions, they are "cows".
//
// Example:
// • Secret number: 4271 • Opponent's try: 1234 • Answer: 1 bull and 2 cows. (The bull is "2", the cows are "4" and "1".)
//
// The task is to figure out the secret number (“4 bulls”). Write a game where computer generates a 4 digits number
// (the digits must be all different, digit zero (0) is not allowed),
// and then repeatedly asks for the human guesses, and prints the output, until the number is found.


public class BullsAndCows {
	
	static public final String TRY_AGAIN_STRING = "Please try again: ";
	static public final String ENTER_STRING = "Please select the size of the number for the computer to generate. "
			+ "\n(From 1 to 9): ";
	static public final String EXCEPTION_STRING = "The exception.";
	static public final String OUT_OF_RANGE_STRING = "The number you entered is out of allowed range. (From 1 to 9)";
	static public final String GENERATING_RANDOM_NUMBER_STRING = "Generating  the number of size %s ...";
	
	private int secNum;

	public int getSecNum() {
		return secNum;
	}

	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}

	public BullsAndCows() {
		
		int n = 0;
		Scanner input = new Scanner(System.in);
		System.out.print(ENTER_STRING);

		while(true) {
			
			try {
				n = input.nextInt();
			} catch (Exception e) {
				System.out.println(EXCEPTION_STRING);
				System.out.print(TRY_AGAIN_STRING);
				input.next();
			}
			
			if (n<10 && n>0) break;
			else {
				System.out.println(OUT_OF_RANGE_STRING);
				System.out.print(TRY_AGAIN_STRING);
			}
		}
		
		genRanNum(n);
		
	}

	public BullsAndCows(int n) {

		genRanNum(n);
	}

	public int genRanNum(int n) {
		System.out.println(String.format(GENERATING_RANDOM_NUMBER_STRING, n));
		int num = 0;
		return num;
	}

	public static void main(String[] args) {
		BullsAndCows test = new BullsAndCows();

	}
}
