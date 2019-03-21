import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import com.sun.xml.internal.org.jvnet.fastinfoset.FastInfosetSerializer;

/**
 * 
 * @author Maxim Musikhin
 *
 *         The class that contains all of the methods used for a game
 */
public class BullsAndCows {

	/**
	 * 
	 * @author Maxim Musikhin
	 *
	 */
	public enum InputOption {
		GAMEMODE, DIGIT, NUMBER, FINISH
	}

	/**
	 * 
	 * @author Maxim Musikhin
	 *
	 */
	public enum GameMode {
		HUMANGUESS, COMPUTERGUESS
	}

	public GameMode gameMode;
	Scanner input = new Scanner(System.in);

	public final ArrayList<Integer> DIGITS = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));;
	ArrayList<Integer> secNum = new ArrayList<Integer>();
	ArrayList<Integer> guessNum = new ArrayList<Integer>();
	public int bulls;
	public int cows;
	public boolean endGame = false;
	public int steps;
	public ArrayList<Integer> wrongNumbers;
	int n;

	/**
	 * 
	 */
	public BullsAndCows() {
		while (endGame == false) {
			wrongNumbers = new ArrayList<>();
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

	/**
	 * 
	 * @param inputType
	 */
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
				gameMode = GameMode.COMPUTERGUESS;
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

			System.out.println(STRINGS.PLAY_AGAIN_STRING);
			while (true) {

				try {
					a = input.nextInt();
					if (a < 3 && a > 0) {
						if (a == 2) {
							endGame = true;
							System.out.println(STRINGS.GOODBYE_STRING);
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

	/**
	 * 
	 */
	private void playGame() {

		System.out.println("THE GAMES HAS STARTED");
		n = secNum.size();

		if (gameMode == GameMode.COMPUTERGUESS) {
			guessNum = genRanNum(n);
			calculateBullsAndCows(true);
			computerGuess();
			System.out.printf(STRINGS.COMPUTER_WIN_STRING, steps);
			System.out.println();
		} else {
			while (bulls != n) {
				guessNum = makeUserGuess();
				System.out.println("The guessNum = " + guessNum.toString());
				calculateBullsAndCows(true);
			}
			System.out.printf(STRINGS.WIN_STRING, steps);
			System.out.println();
		}

	}

	/**
	 * 
	 */
	private void computerGuess() {
		while (bulls != n) {
			while (cows != n) {
				print(guessNum);
				if (cows == 0) {
					for (int i : guessNum) {
						wrongNumbers.add(i);
					}
					System.out.println("The wrong numbers are " + wrongNumbers.toString());
					guessNum = genRanNum(n);
					calculateBullsAndCows(true);
					if (cows == n) {
						break;
					}
				}
				if (cows > 0 && cows < n) {

					for (int i = 0; i < n; i++) {
						ArrayList<Integer> oldNum = new ArrayList<>(guessNum);
						int oldCows = cows;
						int oldBulls = bulls;
						for (Integer j : DIGITS) {
							if (!wrongNumbers.contains(j) && !guessNum.contains(j)) {
								guessNum.set(i, j);
								calculateBullsAndCows(true);
								if (oldCows > cows) {
									wrongNumbers.add(guessNum.get(i));
									guessNum = new ArrayList<>(oldNum);
								} else if (oldCows < cows) {
									wrongNumbers.add(oldNum.get(i));
								}
								break;

							}
						}
					}

				}

				if (cows == n) {
					System.out.println("ALL OF THE COW NUMEBRS HAVE BEEN FOUND");
					System.out.println("WRONG NUMBER LIST: " + wrongNumbers.toString());
				}

			}
			print(guessNum);
			guessNum = getShuffledArray(guessNum);
			calculateBullsAndCows(true);
		}

	}

	/**
	 * 
	 * @return
	 */
	private ArrayList<Integer> makeUserGuess() {
		String regex = "[1-9]+";
		String strInput = "";
		System.out.printf(STRINGS.ENTER_NUMBER_STRING, secNum.size());

		while (true) {
			try {
				strInput = input.next();
				if (strInput.matches(regex) && strInput.length() == secNum.size())
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

	/**
	 * 
	 * @param strInput
	 * @return
	 */
	public static ArrayList<Integer> stringToArray(String strInput) {
		ArrayList<Integer> out = new ArrayList<>();
		for (int i = 0; i < strInput.length(); i++) {
			out.add(strInput.charAt(i) - 48);
		}
		return out;
	}

	/**
	 * 
	 * @param report
	 */
	private void calculateBullsAndCows(boolean report) {
		steps++;
		cows = 0;
		bulls = 0;

		calculateBulls();
		if (bulls == n) {
			cows = bulls;
		} else {
			calculateCows();
		}

		if (report == true)
			System.out.printf(STRINGS.COWS_AND_BULLS_CALCULATED_STRING, bulls, cows);
		System.out.println();
	}

	/**
	 * 
	 */
	private void calculateBulls() {
		for (int i = 0; i < n; i++) {
			if (secNum.get(i) == guessNum.get(i))
				bulls++;
		}
	}

	/**
	 * 
	 */
	private void calculateCows() {
		for (int i : guessNum) {
			for (int j = 0; j < n; j++) {
				if (i == secNum.get(j))
					cows++;
			}
		}
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public ArrayList<Integer> genRanNum(int n) {
		System.out.printf(STRINGS.GENERATING_RANDOM_NUMBER_STRING, n);
		ArrayList<Integer> num = new ArrayList<>(0);
		ArrayList<Integer> digits = getShuffledArray(DIGITS);
		int i = 0;

		while (!(num.size() == n)) {
			System.out.printf("#### CHECKING... number %s", digits.get(i) + "\n");
			System.out.println(wrongNumbers.contains(new Integer(digits.get(i))));
			if (!wrongNumbers.contains(digits.get(i))) {
				num.add(digits.get(i));
			}
			i++;
		}

		// print(num);
		return num;
	}

	/**
	 * 
	 * @param guessNum2
	 */
	private void print(ArrayList<Integer> guessNum2) {
		String string = new String();
		for (int i : guessNum2) {
			Integer a = i;
			string = string.concat(a.toString());
		}
		System.out.println(string);
	}

	/**
	 * 
	 * @param guessNum2
	 * @return
	 */
	private ArrayList<Integer> getShuffledArray(ArrayList<Integer> guessNum2) {

		ArrayList<Integer> arrayOut = guessNum2;
		int n = arrayOut.size();
		Random random = new Random();
		for (int i = 0; i < arrayOut.size(); i++) {
			int randomValue = i + random.nextInt(n - i);
			int randomElement = arrayOut.get(randomValue);
			arrayOut.set(randomValue, arrayOut.get(i));
			arrayOut.set(i, randomElement);
		}

		return arrayOut;
	}

	/**
	 * 
	 */
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

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BullsAndCows test = new BullsAndCows();

	}
}
