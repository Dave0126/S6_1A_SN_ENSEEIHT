package allumettes;

import java.util.Random;

public class StrategieExpert implements Strategie {
	/** the strategy. */
	private String strategie;
	/** the constructor of StrategieExpert. */
	public StrategieExpert() {
		this.strategie = "expert";
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
	 * expert.
	 * @param jeu1     the current game
	 * @return setPrise the number been calculated.
	 * @throws CoupInvalideException Son of Exception.
	 */
	public int prise(Jeu jeu1) throws CoupInvalideException {
		Random r = new Random();
		int setPrise;
		int currentPrise = jeu1.getNombreAllumettes();
		if ((currentPrise - 1) % (jeu1.PRISE_MAX + 1) == 0) {
			setPrise = r.nextInt(Math.min(jeu1.PRISE_MAX, currentPrise)) + 1;
		} else {
			setPrise = (currentPrise - 1) % (jeu1.PRISE_MAX + 1);
		}
		return setPrise;
	}

}
