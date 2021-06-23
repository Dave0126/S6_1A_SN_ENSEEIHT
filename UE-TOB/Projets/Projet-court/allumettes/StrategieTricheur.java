package allumettes;


public class StrategieTricheur implements Strategie {
	/** the strategy. */
	private String strategie;
	/** the constructor of StrategieTricheur. */
	public StrategieTricheur() {
		this.strategie = "tricheur";
	}
	/**
	 * Get a String of strategy.
	 * @return strategy in String.
	 */
	public String getStrategie() {
		return this.strategie;
	}
	/**
	 * This method deal with the case of strategy == tricheur.
	 * @param jeu     the current game
	 * @return setPrise the number been calculated.
	 * @throws CoupInvalideException
	 */
	public int prise(Jeu jeu) throws CoupInvalideException {
		System.out.printf("[Je triche...]\n");
		while (jeu.getNombreAllumettes() > 2) {
			jeu.retirer(1);
		}
		throw new SomeoneCheatsException();
	}

}
