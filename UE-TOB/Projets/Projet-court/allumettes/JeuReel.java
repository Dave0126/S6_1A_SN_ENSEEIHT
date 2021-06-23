package allumettes;
/**
 * The reality class of Jeu.
 * @author DAI Guohao
 * @version 1.1
 */
public class JeuReel implements Jeu {
	/** current number of rest matches. */
	private int numMatches;
/**
 * To realize a class of Jeu.
 * @param numMatches number of rest matches.
 */
	public JeuReel(int numMatches) {
		assert (numMatches > 0);
		this.numMatches = numMatches;
	}

	@Override
	public int getNombreAllumettes() {
		// TODO Auto-generated method stub
		return this.numMatches;
	}

	@Override
	public void retirer(int nbPrises) throws CoupInvalideException {
		if (nbPrises < 1) {
			throw new CoupInvalideException(nbPrises, "< 1");
		} else if (nbPrises > this.getNombreAllumettes()) {
				throw new CoupInvalideException(nbPrises, "> "
		+ this.getNombreAllumettes());
		} else if (nbPrises > this.PRISE_MAX) {
			throw new CoupInvalideException(nbPrises, "> "
					+ Jeu.PRISE_MAX);
		} else {
			this.numMatches = this.numMatches - nbPrises;
		}
	}
}
