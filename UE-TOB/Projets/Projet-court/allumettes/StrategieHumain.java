package allumettes;

import java.util.Scanner;

public class StrategieHumain implements Strategie {
	/** the stratrgy. */
	private String strategie;
	/** the class of scanner named sc. */
	private Scanner sc;
	/** the constructor of StratedieHumain.
	 * @param sc*/
	public StrategieHumain(Scanner sc) {
		this.strategie = "humain";
		this.sc = sc;
	}
	/**
	 * Get a String of strategy.
	 * @return strategy in String.
	 */
	public String getStrategie() {
		return this.strategie;
	}
	@Override
	public int prise(Jeu jeu) {
		String input = sc.nextLine();
		int number;
		boolean isNum = input.matches("^-?[0-9]\\d*$");
		if (isNum) {
			number = Integer.valueOf(input);
		} else {
			if (!input.equals("triche")) {
					throw new OperationInterditeException();
			} else {
				throw new SomeoneCheatsException(); //the number flag of "triche"
			}
		}
		return number;
	}
}
