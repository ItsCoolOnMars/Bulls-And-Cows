import java.util.Arrays;
import java.util.Random;
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

	public enum InputOption {
		GAMEMODE, DIGIT, NUMBER
	}

	public enum GameMode {
		HUMANGUESS, COMPUTERGUESS
	}

	static public GameMode gameMode = GameMode.COMPUTERGUESS;
	Scanner input = new Scanner(System.in);

	static public final String TRY_AGAIN_STRING = "Please try again. ";
	static public final String WELCOME_STRING = "Welcome to the game 'Bulls and Cows'";
	static public final String GAMEMODE_SELECTED = "You have selected the %s gamemode \n";
	static public final String DIGITS_NUMBER_SELECTED = "You have selected %s number of digits";
	static public final String GUESS_NUMBER_SELECTED = "Your guess number is %s";
	static public final String CHOOSE_GAMEMODE_STRING = "Please select the gamemode."
			+ "\n(1: You guess, 2: Computer guess): ";
	static public final String ENTER_DIGITS_NUMBER_STRING = "Please select the size of the number for the computer to generate. "
			+ "\n(From 1 to 9): ";
	static public final String ENTER_NUMBER_STRING = "Please type your guess number " + "\n(%s numbers maximum): ";
	static public final String EXCEPTION_STRING = "The exception. ";
	static public final String OUT_OF_RANGE_STRING = "The number you entered is out of allowed range.";
	static public final String GENERATING_RANDOM_NUMBER_STRING = "Generating  the number of size %s ...";
	static public final String COWS_AND_BULLS_CALCULATED_STRING = "Your number has %s bulls and %d cows.";
	static public final String WIN_STRING = "Congratulation! You guessed the number";

	static public final int[] DIGITS = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	static public int[] secNum;
	static public int[] guessNum;

	static public int bulls = 0;
	static public int cows = 0;

	public BullsAndCows() {
		System.out.println(WELCOME_STRING);
		inputFromUser(InputOption.GAMEMODE);
		System.out.println("Finished 1st input");
		if (gameMode == GameMode.COMPUTERGUESS) {
			inputFromUser(InputOption.NUMBER);}
		else {
			inputFromUser(InputOption.DIGIT);}
		playGame();
	}

	private void inputFromUser(InputOption inputType) {

		int a = 0;

		switch (inputType) {
		case GAMEMODE:
			System.out.println(CHOOSE_GAMEMODE_STRING);

			while (true) {

				try {
					a = input.nextInt();
					if (a < 3 && a > 0)
						break;
					else {
						System.out.println(OUT_OF_RANGE_STRING + TRY_AGAIN_STRING);
					}
				} catch (Exception e) {
					System.out.println(EXCEPTION_STRING + TRY_AGAIN_STRING);
					input.next();
				}
			}

			if (a == 1) {
				gameMode = GameMode.HUMANGUESS;
				System.out.printf(GAMEMODE_SELECTED, "Human Guess");
			} else {
			System.out.printf(GAMEMODE_SELECTED, "Computer Guess");
			}
			
			break;
		case DIGIT:
			int n = -1;
			System.out.print(ENTER_DIGITS_NUMBER_STRING);
			while (true) {

				try {
					n = input.nextInt();
					if (n < 10 && n > 0)
						break;
					else {
						System.out.println(OUT_OF_RANGE_STRING + TRY_AGAIN_STRING);
					}
				} catch (Exception e) {
					System.out.println(EXCEPTION_STRING + TRY_AGAIN_STRING);
					input.next();
				}

			}

			secNum = genRanNum(n);
			
			break;

		case NUMBER:
			guessNum = makeUserGuess();

			break;

		}

	}

	private void playGame() {

		System.out.println("THE GAMES HAS STARTED");
		int n = secNum.length;

		if (gameMode == GameMode.COMPUTERGUESS) {
			guessNum = genRanNum(n);
			calculateBullsAndCows(guessNum, true);
			while (bulls != n) {
				while (cows != n) {

				}
			}
		} else {
			while (bulls != n) {
				guessNum = makeUserGuess();
				System.out.println("The guessNum = " + Arrays.toString(guessNum));
				calculateBullsAndCows(guessNum, true);
			}
		}
		
		System.out.println(WIN_STRING);
	}

	private int[] makeUserGuess() {
		String regex = "[1-9]+";
		String strInput = "";
		System.out.printf(ENTER_NUMBER_STRING, secNum.length);

		while (true) {
			try {
				strInput = input.next();
				if (strInput.matches(regex) && strInput.length() == secNum.length)
					break;
				else {
					System.out.println(OUT_OF_RANGE_STRING + TRY_AGAIN_STRING);
				}
			} catch (Exception e) {
				System.out.println(EXCEPTION_STRING + TRY_AGAIN_STRING);
				input.next();
			}

		}
		return stringToArray(strInput);

	}

	public static int[] stringToArray(String strInput) {
		int[] out = new int[strInput.length()];
		for (int i = 0; i < out.length; i++) {
			out[i] = strInput.charAt(i)-48;
		}
		return out;
	}

	private void calculateBullsAndCows(int[] guessNum, boolean report) {
		int n = guessNum.length;
		cows = 0;
		bulls = 0;

		calculateBulls(guessNum);
		if (bulls == n) {
			cows = bulls;
		} else {
			calculateCows(guessNum);
		}

		if (report == true)
			System.out.printf(COWS_AND_BULLS_CALCULATED_STRING, bulls, cows);
	}

	private void calculateBulls(int[] guessNum) {
		int n = guessNum.length;
		for (int i = 0; i < n; i++) {
			if (secNum[i] == guessNum[i])
				bulls++;
		}
	}
	
	private void calculateCows(int[] guessNum) {
		for (int i : guessNum) {
			for (int j = 0; j < guessNum.length; j++) {
				if (i == secNum[j])
					cows++;
			}
		}
	}

	public int[] genRanNum(int n) {
		System.out.printf(GENERATING_RANDOM_NUMBER_STRING, n);
		int[] num = new int[n];
		int[] digits = getShuffledArray(DIGITS);

		for (int i = 0; i < n; i++) {
			num[i] = digits[i];
		}

		print(num);
		return num;
	}

	private void print(int[] num) {
		String string = new String();
		for (int i : num) {
			Integer a = i;
			string = string.concat(a.toString());
		}
		System.out.println(string);
	}

	private int[] getShuffledArray(int[] arrayIn) {
		int[] arrayOut = arrayIn;
		int n = arrayOut.length;
		Random random = new Random();
		// Loop over array.
		for (int i = 0; i < arrayOut.length; i++) {
			// Get a random index of the array past the current index.
			// ... The argument is an exclusive bound.
			// It will not go past the array's end.
			int randomValue = i + random.nextInt(n - i);
			// Swap the random element with the present element.
			int randomElement = arrayOut[randomValue];
			arrayOut[randomValue] = arrayOut[i];
			arrayOut[i] = randomElement;
		}

		return arrayOut;
	}

	public static void main(String[] args) {
		BullsAndCows test = new BullsAndCows();

	}
}
