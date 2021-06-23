package allumettes;

public class StrategieRapide implements Strategie {
	/** the strategy. */
	private String strategie;
	/** the constructor of StrategieRaipde. */
	public StrategieRapide() {
		this.strategie = "rapide";
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
	 * rapide.
	 * @param jeu1     the current game
	 * @return setPrise the number been calculated.
	 * @throws CoupInvalideException
	 */
	public int prise(Jeu jeu1) throws CoupInvalideException {
		int setPrise;
		if (jeu1.getNombreAllumettes() >= jeu1.PRISE_MAX) {
			setPrise = jeu1.PRISE_MAX;
		} else if (jeu1.getNombreAllumettes() <= 1) {
			setPrise = 1;
		} else {
			setPrise = jeu1.getNombreAllumettes() - 1;
		}
		return setPrise;
	}
}
