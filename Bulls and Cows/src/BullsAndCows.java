import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetSerializer;

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
		GAMEMODE, DIGIT, NUMBER, FINISH
	}

	public enum GameMode {
		HUMANGUESS, COMPUTERGUESS
	}

	public GameMode gameMode = GameMode.COMPUTERGUESS;
	Scanner input = new Scanner(System.in);

	public final int[] DIGITS = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	public int[] secNum;
	public int[] guessNum;
	public int bulls;
	public int cows;
	public boolean endGame = false;
	public int steps;
	int n;

	public BullsAndCows() {
		while (endGame == false) {
			steps = 0;
			bulls = 0;
			cows = 0;
			System.out.println(STRINGS.WELCOME_STRING);
			inputFromUser(InputOption.GAMEMODE);
			if (gameMode == GameMode.COMPUTERGUESS) {
				inputFromUser(InputOption.NUMBER);
			} else {
				inputFromUser(InputOption.DIGIT);
			}
			playGame();
			inputFromUser(InputOption.FINISH);
		}
	}

	private void inputFromUser(InputOption inputType) {

		int a = 0;

		switch (inputType) {
		case GAMEMODE:
			System.out.println(STRINGS.CHOOSE_GAMEMODE_STRING);

			while (true) {

				try {
					a = input.nextInt();
					if (a < 3 && a > 0)
						break;
					else {
						System.out.println(STRINGS.OUT_OF_RANGE_STRING + STRINGS.TRY_AGAIN_STRING);
					}
				} catch (Exception e) {
					System.out.println(STRINGS.EXCEPTION_STRING + STRINGS.TRY_AGAIN_STRING);
					input.next();
				}
			}

			if (a == 1) {
				gameMode = GameMode.HUMANGUESS;
				System.out.printf(STRINGS.GAMEMODE_SELECTED, "Human Guess");
			} else {
				System.out.printf(STRINGS.GAMEMODE_SELECTED, "Computer Guess");
			}

			break;
		case DIGIT:
			int n = -1;
			System.out.print(STRINGS.ENTER_DIGITS_NUMBER_STRING);
			while (true) {

				try {
					n = input.nextInt();
					if (n < 10 && n > 0)
						break;
					else {
						System.out.println(STRINGS.OUT_OF_RANGE_STRING + STRINGS.TRY_AGAIN_STRING);
					}
				} catch (Exception e) {
					System.out.println(STRINGS.EXCEPTION_STRING + STRINGS.TRY_AGAIN_STRING);
					input.next();
				}

			}

			secNum = genRanNum(n);

			break;

		case NUMBER:
			setSecretNumber();

			break;

		case FINISH:

			System.out.println("Do you want to play again? (1 for yes, 2 for no)");
			while (true) {

				try {
					a = input.nextInt();
					if (a < 3 && a > 0) {
						if (a == 2) {
							endGame = true;
							System.out.println("Goodbye!");
						}
						break;
					} else {
						System.out.println(STRINGS.OUT_OF_RANGE_STRING + STRINGS.TRY_AGAIN_STRING);
					}
				} catch (Exception e) {
					System.out.println(STRINGS.EXCEPTION_STRING + STRINGS.TRY_AGAIN_STRING);
					input.next();
				}
			}
		}

	}

	private void playGame() {

		System.out.println("THE GAMES HAS STARTED");
		n = secNum.length;

		if (gameMode == GameMode.COMPUTERGUESS) {
			guessNum = genRanNum(n);
			calculateBullsAndCows(guessNum, true);
			computerGuess();
			System.out.printf(STRINGS.COMPUTER_WIN_STRING, steps);
			System.out.println();
		} else {
			while (bulls != n) {
				guessNum = makeUserGuess();
				System.out.println("The guessNum = " + Arrays.toString(guessNum));
				calculateBullsAndCows(guessNum, true);
			}
			System.out.println(STRINGS.WIN_STRING);
		}

	}

	private void computerGuess() {
		while (bulls != n) {
			steps++;
			guessNum = genRanNum(n);
			calculateBullsAndCows(guessNum, true);
//			while (cows != n) {
//			}
		}
		
	}

	private int[] makeUserGuess() {
		String regex = "[1-9]+";
		String strInput = "";
		System.out.printf(STRINGS.ENTER_NUMBER_STRING, secNum.length);

		while (true) {
			try {
				strInput = input.next();
				if (strInput.matches(regex) && strInput.length() == secNum.length)
					break;
				else {
					System.out.println(STRINGS.OUT_OF_RANGE_STRING + STRINGS.TRY_AGAIN_STRING);
				}
			} catch (Exception e) {
				System.out.println(STRINGS.EXCEPTION_STRING + STRINGS.TRY_AGAIN_STRING);
				input.next();
			}

		}
		return stringToArray(strInput);

	}

	public static int[] stringToArray(String strInput) {
		int[] out = new int[strInput.length()];
		for (int i = 0; i < out.length; i++) {
			out[i] = strInput.charAt(i) - 48;
		}
		return out;
	}

	private void calculateBullsAndCows(int[] guessNum, boolean report) {
		cows = 0;
		bulls = 0;

		calculateBulls(guessNum);
		if (bulls == n) {
			cows = bulls;
		} else {
			calculateCows(guessNum);
		}

		if (report == true)
			System.out.printf(STRINGS.COWS_AND_BULLS_CALCULATED_STRING, bulls, cows);
		System.out.println();
	}

	private void calculateBulls(int[] guessNum) {
		for (int i = 0; i < n; i++) {
			if (secNum[i] == guessNum[i])
				bulls++;
		}
	}

	private void calculateCows(int[] guessNum) {
		for (int i : guessNum) {
			for (int j = 0; j < n; j++) {
				if (i == secNum[j])
					cows++;
			}
		}
	}

	public int[] genRanNum(int n) {
		System.out.printf(STRINGS.GENERATING_RANDOM_NUMBER_STRING, n);
		int[] num = new int[n];
		int[] digits = getShuffledArray(DIGITS);

		for (int i = 0; i < n; i++) {
			num[i] = digits[i];
		}

//		print(num);
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

	private void setSecretNumber() {
		String regex = "[1-9]+";
		String strInput = "";
		System.out.println(STRINGS.SET_SECRET_STRING);

		while (true) {
			try {
				strInput = input.next();
				if (strInput.matches(regex) && (strInput.length() < 10 || strInput.length() > 0))
					break;
				else {
					System.out.println(STRINGS.OUT_OF_RANGE_STRING + STRINGS.TRY_AGAIN_STRING);
				}
			} catch (Exception e) {
				System.out.println(STRINGS.EXCEPTION_STRING + STRINGS.TRY_AGAIN_STRING);
				input.next();
			}

		}
		secNum = stringToArray(strInput);

	}

	public static void main(String[] args) {
		BullsAndCows test = new BullsAndCows();

	}
}
