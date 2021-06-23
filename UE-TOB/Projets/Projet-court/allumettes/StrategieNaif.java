package allumettes;

import java.util.Random;

public class StrategieNaif implements Strategie {
	/** the strategy. */
	private String strategie;
	/** the constructor of StrategieNaif. */
	public StrategieNaif() {
		this.strategie = "naif";
	}
/**
 * Get a String of strategy.
 * @return strategy in String.
 */
	public String getStrategie() {
		return this.strategie;
	}
	/**
	 * This method calculates how many matches computer can take in the mode of
	 * naif.
	 * @param jeu1     the current game
	 * @return setPrise the number been calculated.
	 * @throws CoupInvalideException Son of Exception.
	 */
	public int prise(Jeu jeu1) throws CoupInvalideException {
		Random r = new Random();
		int setPrise;
		if (jeu1.getNombreAllumettes() > (jeu1.PRISE_MAX) + 1) {
			setPrise = r.nextInt(jeu1.PRISE_MAX) + 1;
			jeu1.retirer(setPrise);
		} else if (jeu1.getNombreAllumettes() <= 1) {
			setPrise = 1;
		} else {
			setPrise = jeu1.getNombreAllumettes() - 1;
		}
		return setPrise;
	}
}
